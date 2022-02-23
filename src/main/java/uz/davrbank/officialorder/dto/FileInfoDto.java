package uz.davrbank.officialorder.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileInfoDto extends BaseDto{
    @NotBlank(message = "File name cannot be null or empty!")
    String name;
}
