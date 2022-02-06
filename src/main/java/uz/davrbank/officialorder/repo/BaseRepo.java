package uz.davrbank.officialorder.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.davrbank.officialorder.entity.BaseEntity;

public interface BaseRepo<E extends BaseEntity> extends JpaRepository<E, Long> {
}
