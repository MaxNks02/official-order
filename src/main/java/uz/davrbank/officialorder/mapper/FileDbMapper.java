package uz.davrbank.officialorder.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.davrbank.officialorder.dto.FileDbDto;
import uz.davrbank.officialorder.entity._FileDb;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
@Component
public interface FileDbMapper extends BaseMapper<_FileDb, FileDbDto>{
}
