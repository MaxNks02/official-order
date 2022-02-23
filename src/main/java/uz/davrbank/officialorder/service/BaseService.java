package uz.davrbank.officialorder.service;

import org.springframework.transaction.annotation.Transactional;
import uz.davrbank.officialorder.dto.BaseDto;
import uz.davrbank.officialorder.entity.BaseEntity;
import uz.davrbank.officialorder.exception.CustomNotFoundException;
import uz.davrbank.officialorder.exception.DatabaseException;
import uz.davrbank.officialorder.exception.handler.ApiErrorMessages;
import uz.davrbank.officialorder.mapper.BaseMapper;
import uz.davrbank.officialorder.repo.BaseRepo;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseService<R extends BaseRepo<E>, E extends BaseEntity, D extends BaseDto, M extends BaseMapper<E, D>> {
    private final R repository;
    private final M mapper;

    public BaseService(R repository, M mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public R getRepository() {
        return repository;
    }

    public M getMapper() {
        return mapper;
    }

    public List<D> getAll() {
        List<E> eList = repository.findAll();
        return entityListToDtoList(eList);
    }

    public D getById(long id) {
        E entity = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return mapper.convertFromEntity(entity);
    }

    @Transactional
    public D create(D dto) {
        E entity = mapper.convertFromDto(dto);
        try {
            repository.save(entity);
        } catch (RuntimeException exception) {
            throw new DatabaseException(String.format(ApiErrorMessages.INTERNAL_SERVER_ERROR + " %s", exception.getMessage()));
        }
        return mapper.convertFromEntity(entity);
    }

    @Transactional
    public List<D> createAll(List<E> entityList) {
        try {
            return entityListToDtoList(repository.saveAll(entityList));
        } catch (RuntimeException exception) {
            throw new DatabaseException(String.format(ApiErrorMessages.INTERNAL_SERVER_ERROR + " %s", exception.getMessage()));
        }
    }

    public void deleteById(long id) {
        E entity = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        repository.deleteById(id);
    }

    public abstract D update(D dto, long id);

    private List<D> entityListToDtoList(List<E> eList) {
        if (eList.isEmpty()) {
            throw new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + " %s", "List empty!"));
        }
        return mapper.convertFromEntityList(eList);
    }
}
