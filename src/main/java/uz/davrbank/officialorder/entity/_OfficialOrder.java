package uz.davrbank.officialorder.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.davrbank.officialorder.entity.lov.*;
import uz.davrbank.officialorder.entity.lov.OffState;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "official_order")
public class _OfficialOrder extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "branch", referencedColumnName = "code", nullable = false)
    _Dbranch branch;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doc_type", referencedColumnName = "code", nullable = false)
    _DocType docType;

    @NotNull(message = "Document number cannot be null or empty!")
    @Column(nullable = false, length = 10)
    Integer docNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @NotNull(message = "Document date cannot be null")
    @Column(nullable = false)
    LocalDate docDate;

    @NotNull(message = "Debtor account cannot be null!")
    @Column(nullable = false, length = 20)
    String debtorAccount;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cor_mfo", referencedColumnName = "code", nullable = false)
    _Smfo corMfo;

    @NotNull(message = "Creditor account cannot be null!")
    @Column(nullable = false, length = 20)
    String creditAccount;

    @NotBlank(message = "Correspondent name cannot be null or empty!")
    @Column(nullable = false, length = 80)
    String corName;

    @NotBlank(message = "Debit or Credit!")
    @Column(nullable = false, length = 1)
    String dOrC;

    @NotNull(message = "Summa cannot be null or empty!")
    @Column(nullable = false, length = 20)
    Double summa;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "code", referencedColumnName = "code", nullable = false)
    _Snazn code;

    @NotBlank(message = "Purpose cannot be null or empty!")
    @Column(nullable = false, length = 500)
    String purpose;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @NotNull(message = "Document date cannot be null")
    @Column(nullable = false)
    LocalDate valueDate;


    @Convert(converter = OffState.StateConverter.class)
    @Column(nullable = false, name = "state")
    @NotNull(message = "State cannot be null or empty!")
    OffState state;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull(message = "Excel file ID filed mandatory")
    _FileDb fileDb;
}
