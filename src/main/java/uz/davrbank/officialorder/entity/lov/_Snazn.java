package uz.davrbank.officialorder.entity.lov;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Table;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Getter
@Setter
@NoArgsConstructor
@Builder
@Entity
@Table(name = "s_nazn")
public class _Snazn extends LOVEntity{
}
