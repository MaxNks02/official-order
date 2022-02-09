package uz.davrbank.officialorder.dto;


import lombok.*;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileDbDto extends BaseDto{
    @NotBlank(message = "File name cannot be null or empty!")
    private String name;
    @NotBlank(message = "File")
    private String type;
    @NotNull(message = "Date cannot be null or empty!")
    private byte[] data;
}
