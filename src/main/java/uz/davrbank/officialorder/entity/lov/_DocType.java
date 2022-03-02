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
@Table(name = "doc_type")
public class _DocType extends LOVEntity{
}
