package com.niknastacy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitApplicationResponse {

    @JsonProperty("applicationId")
    private String applicationId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("success")
    private Boolean success;
}
