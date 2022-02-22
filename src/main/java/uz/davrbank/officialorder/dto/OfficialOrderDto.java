package uz.davrbank.officialorder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import uz.davrbank.officialorder.entity.lov._DocType;
import uz.davrbank.officialorder.entity.lov._Smfo;
import uz.davrbank.officialorder.entity.lov._Snazn;

import javax.validation.constraints.Size;
import java.time.LocalDate;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfficialOrderDto extends BaseDto{
    @JsonProperty(value = "DOC_TYPE")
    _DocType docType;
    @JsonProperty(value = "DOC_ID")
    Integer docNumber;
    @JsonProperty(value = "DOC_DATE")
    LocalDate docDate;
    @JsonProperty(value = "DEBTOR_ACCOUNT")
    String debtorAccount;
    @JsonProperty(value = "C_BRANCH")
    _Smfo corMfo;
    @JsonProperty(value = "CREDITOR_ACCOUNT")
    String creditAccount;
    @JsonProperty(value = "C_NAME")
    String corName;
    @JsonProperty(value = "TYPE_DC")
    @Size(min = 1, max = 1)
    String dOrC;
    @JsonProperty(value = "SUMMA")
    Double summa;
    @JsonProperty(value = "KOD")
    _Snazn code;
    @JsonProperty(value = "PAYMENT_PURPOSE")
    String purpose;
}
