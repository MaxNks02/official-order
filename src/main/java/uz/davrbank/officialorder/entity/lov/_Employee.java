package uz.davrbank.officialorder.entity.lov;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "employee")
public class _Employee extends LOVEntity {
    @ManyToOne
    @JoinColumn(name = "branch", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    _Dbranch branch;
}
