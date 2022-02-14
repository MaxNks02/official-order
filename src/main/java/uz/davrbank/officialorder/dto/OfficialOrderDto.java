package uz.davrbank.officialorder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.time.LocalDate;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfficialOrderDto {
    @JsonProperty(value = "DOC_TYPE")
    String docType;
    @JsonProperty(value = "DOC_ID")
    Integer docNumber;
    @JsonProperty(value = "DOC_DATE")
    LocalDate docDate;
    @JsonProperty(value = "DEBTOR_ACCOUNT")
    String debtorAccount;
    @JsonProperty(value = "C_BRANCH")
    String corMfo;
    @JsonProperty(value = "CREDITOR_ACCOUNT")
    String creditAccount;
    @JsonProperty(value = "C_NAME")
    String corName;
    @JsonProperty(value = "TYPE_DC")
    String dOrC;
    @JsonProperty(value = "SUMMA")
    Double summa;
    @JsonProperty(value = "KOD")
    String code;
    @JsonProperty(value = "PAYMENT_PURPOSE")
    String purpose;
}
