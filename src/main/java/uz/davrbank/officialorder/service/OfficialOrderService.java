package uz.davrbank.officialorder.service;

import com.google.common.base.Strings;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataIntegrityViolationException;
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
import uz.davrbank.officialorder.exception.DatabaseException;
import uz.davrbank.officialorder.exception.FileException;
import uz.davrbank.officialorder.exception.handler.ApiErrorMessages;
import uz.davrbank.officialorder.mapper.OfficialOrderMapper;
import uz.davrbank.officialorder.repo.OfficialOrderRepo;
import uz.davrbank.officialorder.repo.lov.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
public class OfficialOrderService extends BaseService<OfficialOrderRepo, _OfficialOrder, OfficialOrderDto, OfficialOrderMapper> {
    private final DocTypeRepo docTypeRepo;
    private final SmfoRepo smfoRepo;
    private final SnaznRepo snaznRepo;
    private final EmployeeRepo employeeRepo;
    private final DbranchRepo dbranchRepo;

    public OfficialOrderService(OfficialOrderRepo repository, OfficialOrderMapper mapper, DocTypeRepo docTypeRepo, SmfoRepo smfoRepo, SnaznRepo snaznRepo, EmployeeRepo employeeRepo, DbranchRepo dbranchRepo) {
        super(repository, mapper);
        this.docTypeRepo = docTypeRepo;
        this.smfoRepo = smfoRepo;
        this.snaznRepo = snaznRepo;
        this.employeeRepo = employeeRepo;
        this.dbranchRepo = dbranchRepo;
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
        List<OfficialOrderDto> dtoList = excelToDtoList(files);
        List<_OfficialOrder> entityList = dtoListToEntityList(dtoList);
        List<_OfficialOrder> savedEntity;
        try {
            savedEntity = getRepository().saveAll(entityList);
        } catch (DataIntegrityViolationException exception) {
            throw new DatabaseException(String.format(ApiErrorMessages.INTERNAL_SERVER_ERROR + " %s", exception.getMessage()));
        }
        List<OfficialOrderDto> responseDtoList = entityListToDtoList(savedEntity);
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
                entity.setState(OffState.ENTERED);
                entity.setDOrC(dto.getDOrC());
                entity.setBranch(employee.getBranch());
                entity.setEmployee(employee);
                entityList.add(entity);
            }
            return entityList;
        }
        throw new BadRequestException(String.format(ApiErrorMessages.BAD_REQUEST + " %s", "Excel file cannot be empty!"));
    }

    private List<OfficialOrderDto> entityListToDtoList(List<_OfficialOrder> entityList) {
        if (!entityList.isEmpty()) {
            List<OfficialOrderDto> dtoList = new LinkedList<>();
            for (_OfficialOrder entity : entityList) {
                OfficialOrderDto dto = getMapper().convertFromEntity(entity);
                dto.setDOrC(entity.getDOrC());
                dto.setDocType(entity.getDocType());
                dtoList.add(dto);
            }
            return dtoList;
        }
        throw new BadRequestException(String.format(ApiErrorMessages.BAD_REQUEST + " %s", "Excel file cannot be empty!"));
    }

    private List<OfficialOrderDto> excelToDtoList(MultipartFile files) {
        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        List<OfficialOrderDto> dtoList = new LinkedList<>();
        XSSFWorkbook workbook;
        try {
            workbook = new XSSFWorkbook(files.getInputStream());
        } catch (IOException e) {
            throw new FileException(String.format(ApiErrorMessages.INTERNAL_SERVER_ERROR + "%s", e.getMessage()));
        }
        XSSFSheet worksheet = workbook.getSheetAt(0);
        for (int index = 3; index <= worksheet.getPhysicalNumberOfRows(); index++) {
            XSSFRow row = worksheet.getRow(index);

            if (Strings.isNullOrEmpty(getCellValue(row, 0))) {
                break;
            }
            OfficialOrderDto dto = new OfficialOrderDto();

            _DocType docType = docTypeRepo.findById(getCellValue(row, 0)).orElseThrow(() -> new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "%s", "Document type not found")));
            dto.setDocType(docType);

            dto.setDocNumber(Integer.parseInt(getCellValue(row, 1)));
            dto.setDocDate(LocalDate.parse(getCellValue(row, 2)));
            dto.setDebtorAccount(getCellValue(row, 3));

            _Smfo smfo = smfoRepo.findById(getCellValue(row, 4)).orElseThrow(() -> new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "%s", "Correspondent bank not found")));
            dto.setCorMfo(smfo);

            dto.setCreditAccount(getCellValue(row, 5));
            dto.setCorName(getCellValue(row, 6));
            String typeDC = getCellValue(row, 7);
            if (typeDC.equals("D") || typeDC.equals("C")) {
                dto.setDOrC(typeDC);
            } else {
                throw new BadRequestException(String.format(ApiErrorMessages.BAD_REQUEST + " %s", "Type_DC must be D or C"));
            }
            dto.setSumma(Double.parseDouble(getCellValue(row, 8)));

            _Snazn snazn = snaznRepo.findById(getCellValue(row, 9)).orElseThrow(() -> new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "%s", "S_nazn not found")));
            dto.setCode(snazn);
            dto.setPurpose(getCellValue(row, 10));

            dtoList.add(dto);
        }
        return dtoList;
    }

    private String getCellValue(Row row, int cellNo) {
        DataFormatter formatter = new DataFormatter();

        Cell cell = row.getCell(cellNo);

        return formatter.formatCellValue(cell);
    }

    @Override
    public OfficialOrderDto update(OfficialOrderDto dto, long id) {
        return null;
    }
}
