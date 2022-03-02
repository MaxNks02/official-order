package uz.davrbank.officialorder.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.davrbank.officialorder.ResponseHandler;
import uz.davrbank.officialorder.dto.OffTransactionDto;
import uz.davrbank.officialorder.entity._OffTransaction;
import uz.davrbank.officialorder.entity._OfficialOrder;
import uz.davrbank.officialorder.entity.lov.OffState;
import uz.davrbank.officialorder.exception.CustomNotFoundException;
import uz.davrbank.officialorder.exception.DatabaseException;
import uz.davrbank.officialorder.exception.handler.ApiErrorMessages;
import uz.davrbank.officialorder.mapper.OffTransactionMapper;
import uz.davrbank.officialorder.repo.OffTransactionRepo;
import uz.davrbank.officialorder.repo.OfficialOrderRepo;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
public class OffTransactionService extends BaseService<OffTransactionRepo, _OffTransaction, OffTransactionDto, OffTransactionMapper> {
    private final OfficialOrderRepo officialOrderRepo;

    public OffTransactionService(OffTransactionRepo repository, @Qualifier("offTransactionMapperImpl") OffTransactionMapper mapper, OfficialOrderRepo officialOrderRepo) {
        super(repository, mapper);
        this.officialOrderRepo = officialOrderRepo;
    }

    public ResponseEntity<?> createTransaction(List<Long> idList) {
        List<OffTransactionDto> dtoList = new LinkedList<>();
        for (Long id : idList) {
            _OfficialOrder entity = officialOrderRepo.findById(id).orElseThrow(() -> new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "%s", "Employee not found")));
            _OffTransaction transaction = saveTransaction(entity);
            entity.setState(OffState.VALIDATED);
            entity.setValueDate(LocalDate.now());
            try {
                officialOrderRepo.save(entity);
            }catch (RuntimeException exception){
                throw new DatabaseException(String.format(ApiErrorMessages.INTERNAL_SERVER_ERROR + " %s", exception.getMessage()));
            }
            OffTransactionDto dto = getMapper().convertFromEntity(transaction);
            dtoList.add(dto);
        }
        if (!dtoList.isEmpty()) {
            return ResponseHandler.generateResponse("Transaction successfully created!", HttpStatus.OK, dtoList);
        }
        throw new DatabaseException(String.format(ApiErrorMessages.INTERNAL_SERVER_ERROR + " %s", "Transactions failed!"));
    }

    @Transactional
    _OffTransaction saveTransaction(_OfficialOrder officialOrder) {
        _OffTransaction offTransaction = new _OffTransaction();
        if (officialOrder.getTypeDC().equals("C")) {
            offTransaction.setDebit(officialOrder.getCreditAccount());
            offTransaction.setCredit(officialOrder.getDebtorAccount());
        }
        offTransaction.setDebit(officialOrder.getDebtorAccount());
        offTransaction.setCredit(officialOrder.getCreditAccount());
        offTransaction.setSumma(officialOrder.getSumma());
        offTransaction.setTypeDC(officialOrder.getTypeDC());
        offTransaction.setOfficialOrder(officialOrder);
        try {
            return getRepository().save(offTransaction);
        } catch (DataIntegrityViolationException exception) {
            throw new DatabaseException(String.format(ApiErrorMessages.INTERNAL_SERVER_ERROR + " %s", exception.getMessage()));
        }
    }

    @Override
    public OffTransactionDto update(OffTransactionDto dto, long id) {
        return null;
    }
}
