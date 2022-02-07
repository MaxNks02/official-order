package uz.davrbank.officialorder.entity.lov;

import lombok.*;
import uz.davrbank.officialorder.entity._OfficialOrder;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "s_mfo")
public class _Smfo extends LOVEntity {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "corMfo", cascade = CascadeType.ALL)
    private List<_OfficialOrder> officialOrders;
}
