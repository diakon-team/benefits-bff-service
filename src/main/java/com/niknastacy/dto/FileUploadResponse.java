package com.niknastacy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadResponse {

    @JsonProperty("fileId")
    private String fileId;

    @JsonProperty("fileUr")
    private String fileUrl;

    @JsonProperty("originalName")
    private String originalName;
}
