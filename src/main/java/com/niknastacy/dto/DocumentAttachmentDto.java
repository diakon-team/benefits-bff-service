package com.niknastacy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentAttachmentDto {

    @JsonProperty("documentType")
    private String documentType;

    @JsonProperty("fileId")
    private String fileId;
}
