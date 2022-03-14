package uz.davrbank.officialorder.entity;


import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
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
    String typeDC;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "official_order", referencedColumnName = "id")
    _OfficialOrder officialOrder;
}
