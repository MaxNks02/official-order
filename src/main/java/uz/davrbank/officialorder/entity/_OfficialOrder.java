package uz.davrbank.officialorder.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import uz.davrbank.officialorder.entity.lov.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Builder
@Entity
@Table(name = "official_order")
public class _OfficialOrder extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "doc_type", nullable = false)
    @ToString.Exclude
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


    @ManyToOne
    @JoinColumn(name = "cor_mfo", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    _Smfo corMfo;

    @NotNull(message = "Creditor account cannot be null!")
    @Column(nullable = false, length = 20)
    String creditAccount;

    @NotBlank(message = "Correspondent name cannot be null or empty!")
    @Column(nullable = false, length = 80)
    String corName;

    @NotBlank(message = "Must be D or C!")
    @Column(nullable = false, length = 1, name = "type_DC")
    String typeDC;

    @NotNull(message = "Summa cannot be null or empty!")
    @Column(nullable = false, length = 20)
    Double summa;

    @ManyToOne
    @JoinColumn(name = "code", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    _Snazn code;

    @NotBlank(message = "Purpose cannot be null or empty!")
    @Column(nullable = false, length = 500)
    String purpose;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate valueDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "Download date cannot be null")
    @Column(nullable = false)
    LocalDate downloadDate;

    @Column(name = "file_name", nullable = false)
    @NotBlank(message = "File name cannot be null or empty!")
    String fileName;

    @Column(name = "file_id", nullable = false)
    @NotNull(message = "File id cannot be null")
    Long fileId;

    @Convert(converter = OffState.StateConverter.class)
    @Column(nullable = false, name = "state")
    @NotNull(message = "State cannot be null or empty!")
    OffState state;

    @ManyToOne
    @JoinColumn(name = "branch", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    _Dbranch branch;

    @ManyToOne
    @JoinColumn(name = "employee", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    _Employee employee;

    @OneToOne(mappedBy = "officialOrder")
    _OffTransaction offTransaction;
}
