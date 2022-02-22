package uz.davrbank.officialorder.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.davrbank.officialorder.dto.OfficialOrderDto;
import uz.davrbank.officialorder.entity._OfficialOrder;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
@Component
public interface OfficialOrderMapper extends BaseMapper<_OfficialOrder, OfficialOrderDto>{
}
