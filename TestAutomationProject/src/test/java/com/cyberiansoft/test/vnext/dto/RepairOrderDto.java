package com.cyberiansoft.test.vnext.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RepairOrderDto {
    @JsonProperty("phaseName")
    private String phaseName;
    @JsonProperty("vin")
    private String vin;
    @JsonProperty("completePercentage")
    private String completePercentage;
}
