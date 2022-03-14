package uz.davrbank.officialorder.service;

import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import uz.davrbank.officialorder.dto.OfficialOrderDto;
import uz.davrbank.officialorder.entity.lov._DocType;
import uz.davrbank.officialorder.entity.lov._Smfo;
import uz.davrbank.officialorder.entity.lov._Snazn;
import uz.davrbank.officialorder.exception.BadRequestException;
import uz.davrbank.officialorder.exception.CustomNotFoundException;
import uz.davrbank.officialorder.exception.FileException;
import uz.davrbank.officialorder.exception.handler.ApiErrorMessages;
import uz.davrbank.officialorder.repo.lov.DocTypeRepo;
import uz.davrbank.officialorder.repo.lov.SmfoRepo;
import uz.davrbank.officialorder.repo.lov.SnaznRepo;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ExcelHelper {
    private final DocTypeRepo docTypeRepo;
    private final SmfoRepo smfoRepo;
    private final SnaznRepo snaznRepo;

    public static final String mimeType = "xlsx";

    public List<OfficialOrderDto> excelToDtoList(MultipartFile files) {
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

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//            dto.setDocDate(LocalDate.parse(getCellValue(row, 2)));
            dto.setDocDate(LocalDate.parse(getCellValue(row, 2), formatter));

            dto.setDebtorAccount(getCellValue(row, 3));

            _Smfo smfo = smfoRepo.findById(getCellValue(row, 4)).orElseThrow(() -> new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "%s", "Correspondent bank not found")));
            dto.setCorMfo(smfo);

            dto.setCreditAccount(getCellValue(row, 5));
            dto.setCorName(getCellValue(row, 6));
            String typeDC = getCellValue(row, 7);
            if (typeDC.equals("D") || typeDC.equals("C")) {
                dto.setTypeDC(typeDC);
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
}
