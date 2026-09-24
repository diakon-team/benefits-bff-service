package com.niknastacy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitApplicationRequest {

    @JsonProperty("address")
    private String address;

    @JsonProperty("documents")
    private List<DocumentAttachmentDto> documents;

    @JsonProperty("volunteerRequested")
    private Boolean volunteerRequested;


}
