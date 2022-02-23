package uz.davrbank.officialorder.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.davrbank.officialorder.exception.BadRequestException;
import uz.davrbank.officialorder.exception.handler.ApiErrorMessages;
import uz.davrbank.officialorder.service.OffTransactionService;

import java.util.List;

import static uz.davrbank.officialorder.controller.BaseController.TRANSACTION;

@RequestMapping(value = TRANSACTION)
@RestController
public class TransactionController extends BaseController<OffTransactionService>{
    public TransactionController(OffTransactionService service) {
        super(service);
    }

    @PutMapping(value = ONE)
    public ResponseEntity<?> oneTransaction(@PathVariable("id") Long id){
        if (id == null) {
            throw new BadRequestException(String.format(ApiErrorMessages.BAD_REQUEST + "%s", "Id cannot be null!"));
        }
        return service.oneTransaction(id);
    }

    @PostMapping(value = LIST)
    public ResponseEntity<?> listTransaction(@RequestBody List<Long> idList){
        if (idList.isEmpty()) {
            throw new BadRequestException(String.format(ApiErrorMessages.BAD_REQUEST + "%s", "Id cannot be null!"));
        }
        return service.listTransaction(idList);
    }
}
