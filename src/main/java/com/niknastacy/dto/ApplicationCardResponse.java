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
public class ApplicationCardResponse {

    @JsonProperty("applicationId")
    private String applicationId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("statusTitle")
    private String statusTitle;

    @JsonProperty("statusDescription")
    private String statusDescription;

    @JsonProperty("progressPercent")
    private Integer progressPercent;

    @JsonProperty("canFillDocs")
    private Boolean canFillDocs;

    @JsonProperty("createdAt")
    private String createdAt;
}
