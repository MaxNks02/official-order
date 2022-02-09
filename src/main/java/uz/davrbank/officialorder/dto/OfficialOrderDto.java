package uz.davrbank.officialorder.dto;

import lombok.*;
import java.time.LocalDate;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfficialOrderDto {
    String branch;
    String docType;
    Integer docNumber;
    LocalDate docDate;
    String debtorAccount;
    String corMfo;
    String creditAccount;
    String corName;
    String dOrC;
    Double summa;
    String code;
    String purpose;
}
