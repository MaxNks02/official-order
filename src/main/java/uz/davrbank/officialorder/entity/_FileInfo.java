package uz.davrbank.officialorder.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "file_info")
public class _FileInfo extends BaseEntity{
    @NotBlank(message = "File name cannot be null or empty!")
    @Column(nullable = false, name = "file_name")
    String fileName;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fileInfo", cascade = CascadeType.ALL)
    private List<_OfficialOrder> officialOrders;
}
