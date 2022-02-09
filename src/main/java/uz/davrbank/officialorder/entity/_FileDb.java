package uz.davrbank.officialorder.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@Builder
@Entity
@Table(name = "files")
public class _FileDb extends BaseEntity{
    @NotBlank(message = "File name cannot be null or empty!")
    private String name;
    @NotBlank(message = "File")
    private String type;
    @Lob
    private byte[] data;

    public _FileDb(String name, String type) {
        this.name = name;
        this.type = type;

    }
    public _FileDb(String name, String type, byte[] data)     {
        this.name = name;
        this.type = type;
        this.data = data;
    }

    public _FileDb(Long id, String createdDate, String name, String type, byte[] data) {
        super(id, createdDate);
        this.name = name;
        this.type = type;
        this.data = data;
    }
}
