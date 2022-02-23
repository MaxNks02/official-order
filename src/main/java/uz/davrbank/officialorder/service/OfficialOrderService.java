package uz.davrbank.officialorder.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.davrbank.officialorder.ResponseHandler;
import uz.davrbank.officialorder.dto.OfficialOrderDto;
import uz.davrbank.officialorder.entity._OfficialOrder;
import uz.davrbank.officialorder.entity.lov.*;
import uz.davrbank.officialorder.exception.BadRequestException;
import uz.davrbank.officialorder.exception.CustomNotFoundException;
import uz.davrbank.officialorder.exception.FileException;
import uz.davrbank.officialorder.exception.handler.ApiErrorMessages;
import uz.davrbank.officialorder.mapper.OfficialOrderMapper;
import uz.davrbank.officialorder.repo.FileInfoRepo;
import uz.davrbank.officialorder.repo.OfficialOrderRepo;
import uz.davrbank.officialorder.repo.lov.*;

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
    private final FileInfoRepo fileInfoRepo;

    public OfficialOrderService(OfficialOrderRepo repository, OfficialOrderMapper mapper, EmployeeRepo employeeRepo, DbranchRepo dbranchRepo, ExcelHelper excelHelper, FileInfoRepo fileInfoRepo) {
        super(repository, mapper);
        this.employeeRepo = employeeRepo;
        this.dbranchRepo = dbranchRepo;
        this.excelHelper = excelHelper;
        this.fileInfoRepo = fileInfoRepo;
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

    public ResponseEntity<?> uploadFile(MultipartFile files) {
        List<OfficialOrderDto> dtoList = excelHelper.excelToDtoList(files);
        List<_OfficialOrder> entityList = dtoListToEntityList(dtoList);
        List<OfficialOrderDto> responseDtoList = createAll(entityList);
        return ResponseHandler.generateResponse("All excel file data", HttpStatus.OK, responseDtoList);
    }

    private List<_OfficialOrder> dtoListToEntityList(List<OfficialOrderDto> dtoList) {
        if (!dtoList.isEmpty()) {
            List<_OfficialOrder> entityList = new LinkedList<>();
            for (OfficialOrderDto dto : dtoList) {
                _OfficialOrder entity = getMapper().convertFromDto(dto);
                _Dbranch dbranch = dbranchRepo.findById("00981").orElseThrow(() -> new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "%s", "Branch not found")));
                _Employee employee = employeeRepo.findById("1").orElseThrow(() -> new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "%s", "Employee not found")));
                employee.setBranch(dbranch);
                entity.setValueDate(LocalDate.now());
                entity.setDownloadDate(LocalDate.now());
                entity.setState(OffState.ENTERED);
                entity.setBranch(employee.getBranch());
                entity.setEmployee(employee);
                entityList.add(entity);
            }
            return entityList;
        }
        throw new BadRequestException(String.format(ApiErrorMessages.BAD_REQUEST + " %s", "Excel file cannot be empty!"));
    }

    @Override
    public OfficialOrderDto update(OfficialOrderDto dto, long id) {
        return null;
    }
}
