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
@Table(name = "s_nazn")
public class _Snazn extends LOVEntity{
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "code", cascade = CascadeType.ALL)
    private List<_OfficialOrder> officialOrders;
}
