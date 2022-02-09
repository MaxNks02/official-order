package uz.davrbank.officialorder.service;

import org.springframework.stereotype.Service;
import uz.davrbank.officialorder.config.FileDbConfig;
import uz.davrbank.officialorder.dto.FileDbDto;
import uz.davrbank.officialorder.entity._FileDb;
import uz.davrbank.officialorder.mapper.FileDbMapper;
import uz.davrbank.officialorder.repo.FileDbRepo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileDbService extends BaseService<FileDbRepo, _FileDb, FileDbDto, FileDbMapper> {
    private final Path fileLocation;

    public FileDbService(FileDbRepo repository, FileDbMapper mapper, FileDbConfig fileDbConfig) {
        super(repository, mapper);
        this.fileLocation = Paths.get(fileDbConfig.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public FileDbDto update(FileDbDto dto, long id) {
        return null;
    }
}
