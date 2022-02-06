package uz.davrbank.officialorder.mapper;

import uz.davrbank.officialorder.dto.BaseDto;
import uz.davrbank.officialorder.entity.BaseEntity;

import java.util.List;

public interface BaseMapper<E extends BaseEntity, D extends BaseDto> {
    D convertFromEntity(E entity);
    E convertFromDto(D dto);

    List<D> convertFromEntityList(List<E> entityList);
    List<E> convertFromDtoList(List<D> dtoList);
}
