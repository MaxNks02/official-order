package uz.davrbank.officialorder.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uz.davrbank.officialorder.ResponseHandler;
import uz.davrbank.officialorder.dto.OfficialOrderDto;
import uz.davrbank.officialorder.entity._OfficialOrder;
import uz.davrbank.officialorder.entity.lov.OffState;
import uz.davrbank.officialorder.entity.lov._Dbranch;
import uz.davrbank.officialorder.entity.lov._Employee;
import uz.davrbank.officialorder.exception.BadRequestException;
import uz.davrbank.officialorder.exception.CustomNotFoundException;
import uz.davrbank.officialorder.exception.DatabaseException;
import uz.davrbank.officialorder.exception.FileException;
import uz.davrbank.officialorder.exception.handler.ApiErrorMessages;
import uz.davrbank.officialorder.mapper.OfficialOrderMapper;
import uz.davrbank.officialorder.repo.OfficialOrderRepo;
import uz.davrbank.officialorder.repo.lov.DbranchRepo;
import uz.davrbank.officialorder.repo.lov.EmployeeRepo;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
public class OfficialOrderService extends BaseService<OfficialOrderRepo, _OfficialOrder, OfficialOrderDto, OfficialOrderMapper> {
    private final EmployeeRepo employeeRepo;
    private final DbranchRepo dbranchRepo;
    private final ExcelHelper excelHelper;

    public OfficialOrderService(OfficialOrderRepo repository, @Qualifier("officialOrderMapperImpl") OfficialOrderMapper mapper, EmployeeRepo employeeRepo, DbranchRepo dbranchRepo, ExcelHelper excelHelper) {
        super(repository, mapper);
        this.employeeRepo = employeeRepo;
        this.dbranchRepo = dbranchRepo;
        this.excelHelper = excelHelper;
    }

    public ResponseEntity<?> downloadFileFromLocal() {
        String file = "src/main/resources/assets/Slujebka.xlsx";
        Path path = Paths.get(file);
        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new FileException(String.format(ApiErrorMessages.INTERNAL_SERVER_ERROR + "%s", e.getMessage()));
        }
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }

    @Transactional
    public ResponseEntity<?> uploadFile(MultipartFile files) {
        List<OfficialOrderDto> dtoList = excelHelper.excelToDtoList(files);
        List<_OfficialOrder> entityList = dtoListToEntityList(dtoList, files.getOriginalFilename(), System.currentTimeMillis());
        List<OfficialOrderDto> responseDtoList = createAll(entityList);
        return ResponseHandler.generateResponse("All excel file data", HttpStatus.OK, responseDtoList);
    }

    private List<_OfficialOrder> dtoListToEntityList(List<OfficialOrderDto> dtoList, String fileName, long fileId) {
        if (!dtoList.isEmpty()) {
            List<_OfficialOrder> entityList = new LinkedList<>();
            for (OfficialOrderDto dto : dtoList) {
                _OfficialOrder entity = getMapper().convertFromDto(dto);
                _Employee employee = employeeRepo.findById("1").orElseThrow(() -> new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "%s", "Employee not found")));
                _Dbranch dbranch = dbranchRepo.findById(employee.getBranch().getId()).orElseThrow(() -> new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "%s", "Branch not found")));
                employee.setBranch(dbranch);
                entity.setDownloadDate(LocalDate.now());
                entity.setFileName(fileName);
                entity.setFileId(fileId);
                entity.setState(OffState.ENTERED);
                entity.setBranch(employee.getBranch());
                entity.setEmployee(employee);
                entityList.add(entity);
            }
            return entityList;
        }
        throw new BadRequestException(String.format(ApiErrorMessages.BAD_REQUEST + " %s", "Excel file cannot be empty!"));
    }

    public ResponseEntity<?> deleteRows(List<Long> idList) {
        for (Long id : idList) {
            _OfficialOrder entity = getRepository().findById(id).orElseThrow(() -> new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "%s", "Employee not found")));
            entity.setState(OffState.DELETED);
            entity.setValueDate(LocalDate.now());
            try {
                getRepository().save(entity);
            } catch (RuntimeException exception) {
                throw new DatabaseException(String.format(ApiErrorMessages.INTERNAL_SERVER_ERROR + "%s", exception.getMessage()));
            }
        }
        return ResponseHandler.generateResponse("Successfully deleted rows!", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> getAllByState() {
        List<_OfficialOrder> officialOrderList = getRepository().findAllByState(OffState.ENTERED);
        if (!officialOrderList.isEmpty()) {
            List<OfficialOrderDto> officialOrderDtoList = getMapper().convertFromEntityList(officialOrderList);
            return ResponseHandler.generateResponse("All active official orders!", HttpStatus.OK, officialOrderDtoList);
        }
        throw new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "%s", "Empty list!"));
    }


    @Override
    public OfficialOrderDto update(OfficialOrderDto dto, long id) {
        return null;
    }
}
