package uz.davrbank.officialorder.entity.lov;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.davrbank.officialorder.entity._OfficialOrder;

import javax.persistence.*;
import java.util.List;


@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "doc_type")
public class _DocType extends LOVEntity{
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "docType", cascade = CascadeType.ALL)
    private List<_OfficialOrder> officialOrders;
}
