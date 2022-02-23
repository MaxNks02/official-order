package uz.davrbank.officialorder.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.davrbank.officialorder.dto.FileInfoDto;
import uz.davrbank.officialorder.entity._FileInfo;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
@Component
public interface FileInfoMapper extends BaseMapper<_FileInfo, FileInfoDto>{
}
