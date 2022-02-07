package uz.davrbank.officialorder.repo.lov;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.davrbank.officialorder.entity.lov.LOVEntity;

public interface LOVRepo<L extends LOVEntity> extends JpaRepository<L, String> {
}
