package com.niknastacy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponse {

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("fio")
    private String fio;

    @JsonProperty("snils")
    private String snils;
}
