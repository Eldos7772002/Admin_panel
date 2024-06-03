package kz.kazpost.dp.core.storage.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileMetadataDTO {
    private String result;
    private String message;
    private String code;
    private String fileId;
    private String filename;
    private String downloadUrl;
    private String downloadBase64Url;
    private long size;
    private String contentType;
    private LocalDateTime lastModified;
    private String data;

}
