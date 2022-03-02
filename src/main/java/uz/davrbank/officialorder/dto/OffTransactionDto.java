package uz.davrbank.officialorder.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import uz.davrbank.officialorder.entity._OfficialOrder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ToString
@SuperBuilder
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OffTransactionDto extends BaseDto {
    @NotBlank(message = "Debit account cannot be null or empty!")
    @Size(min = 20, max = 20, message = "Debit account must be 20 characters!")
    String debit;
    @NotBlank(message = "Credit account cannot be null or empty!")
    @Size(min = 20, max = 20, message = "Credit account must be 20 characters!")
    String credit;
    @NotNull(message = "Summa cannot be null or empty!")
    Double summa;

    @Size(min = 1, max = 1, message = "Transaction type must be 1 characters!")
    @NotBlank(message = "Transaction type must be D or C!")
    String typeDC;

    @NotNull(message = "Official order field not null!")
    _OfficialOrder officialOrder;
}
