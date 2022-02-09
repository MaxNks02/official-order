package uz.davrbank.officialorder.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDbRes {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
}
