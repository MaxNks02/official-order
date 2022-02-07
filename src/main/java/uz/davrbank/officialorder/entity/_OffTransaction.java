package uz.davrbank.officialorder.entity;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "official_order_transactions")
public class _OffTransaction extends BaseEntity {
    @NotBlank(message = "Debit account cannot be null or empty!")
    @Column(nullable = false, length = 20)
    String debit;
    @NotBlank(message = "Credit account cannot be null or empty!")
    @Column(nullable = false, length = 20)
    String credit;
    @NotNull(message = "Summa cannot be null or empty!")
    @Column(nullable = false, length = 20)
    Double summa;

    @NotNull
    @Column(nullable = false, length = 1)
    String transactionType;
}
