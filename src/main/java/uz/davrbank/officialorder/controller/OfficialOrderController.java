package uz.davrbank.officialorder.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.davrbank.officialorder.exception.BadRequestException;
import uz.davrbank.officialorder.exception.handler.ApiErrorMessages;
import uz.davrbank.officialorder.service.ExcelHelper;
import uz.davrbank.officialorder.service.OfficialOrderService;

import java.time.LocalDateTime;
import java.util.Objects;

import static uz.davrbank.officialorder.controller.BaseController.*;

@RequestMapping(value = FILE)
@RestController
public class OfficialOrderController extends BaseController<OfficialOrderService> {
    private final Logger logger = LoggerFactory.getLogger(OfficialOrderController.class);

    public OfficialOrderController(OfficialOrderService service) {
        super(service);
    }

    @GetMapping(value = DOWNLOAD)
    public ResponseEntity<?> downloadFileFromLocal() {
        logger.info("Request to download file. Time: " + LocalDateTime.now());
        return service.downloadFileFromLocal();
    }

    @PostMapping(value = UPLOAD)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile files) {
        if (!files.isEmpty()) {
            String fileType = Objects.requireNonNull(files.getOriginalFilename()).substring(files.getOriginalFilename().length() - 4);
            if (fileType.equals(ExcelHelper.mimeType)) {
                return service.uploadFile(files);
            }
            throw new BadRequestException(String.format(ApiErrorMessages.BAD_REQUEST + "%s", "File type must be \"xlsx\"!"));
        }
        throw new BadRequestException(String.format(ApiErrorMessages.BAD_REQUEST + "%s", "File cannot be null!"));
    }
}