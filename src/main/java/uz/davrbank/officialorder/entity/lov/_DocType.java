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
@Table(name = "doc_type")
public class _DocType extends LOVEntity{
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "docType", cascade = CascadeType.ALL)
    private List<_OfficialOrder> officialOrders;
}
