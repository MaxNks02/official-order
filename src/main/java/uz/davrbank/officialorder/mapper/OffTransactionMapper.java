package uz.davrbank.officialorder.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.davrbank.officialorder.dto.OffTransactionDto;
import uz.davrbank.officialorder.entity._OffTransaction;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
@Component
public interface OffTransactionMapper extends BaseMapper<_OffTransaction, OffTransactionDto>{
}
