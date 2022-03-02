package uz.davrbank.officialorder.repo;

import org.springframework.stereotype.Repository;
import uz.davrbank.officialorder.entity._OfficialOrder;
import uz.davrbank.officialorder.entity.lov.OffState;

import java.util.List;

@Repository
public interface OfficialOrderRepo extends BaseRepo<_OfficialOrder>{
    List<_OfficialOrder> findAllByStateAndFileName(OffState state, String fileName);

    List<_OfficialOrder> findAllByState(OffState state);
}
