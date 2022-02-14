package uz.davrbank.officialorder.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.davrbank.officialorder.config.FileDbConfig;
import uz.davrbank.officialorder.dto.FileDbDto;
import uz.davrbank.officialorder.entity._FileDb;
import uz.davrbank.officialorder.mapper.FileDbMapper;
import uz.davrbank.officialorder.repo.FileDbRepo;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileDbService extends BaseService<FileDbRepo, _FileDb, FileDbDto, FileDbMapper> {

    public FileDbService(FileDbRepo repository, FileDbMapper mapper) {
        super(repository, mapper);
    }

    public ResponseEntity<?> downloadFile() throws MalformedURLException {
        String file = "src/main/resources/assets/Slujebka.xlsx";
        Path path = Paths.get(file);
        Resource resource = new UrlResource(path.toUri());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }

    @Override
    public FileDbDto update(FileDbDto dto, long id) {
        return null;
    }
}
