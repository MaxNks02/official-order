package uz.davrbank.officialorder.controller;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping(name = "/api/v1/oo/file")
public class FileDbController {
    private static final Logger logger = LoggerFactory.getLogger(FileDbController.class);

    @GetMapping(
            value = "/download",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public @ResponseBody
    byte[] getFile() throws IOException {
        InputStream in = getClass()
                .getResourceAsStream("assets/Slujebka.xlsx");
        return IOUtils.toByteArray(in);
    }
}
