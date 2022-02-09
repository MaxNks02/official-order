package uz.davrbank.officialorder.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uz.davrbank.officialorder.exception.FileException;
import uz.davrbank.officialorder.exception.handler.ApiErrorMessages;
import uz.davrbank.officialorder.service.ExcelHelper;

@RestController
@RequestMapping(name = "/api/v1/oo/file")
public class FileDbController {
    private static final Logger logger = LoggerFactory.getLogger(FileDbController.class);

    @PostMapping("/file")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        if (ExcelHelper.hasExcelFormat(file)){

        }
        throw new FileException(String.format(ApiErrorMessages.BAD_REQUEST + "%s", "The file type must be in excel"));
    }

}
