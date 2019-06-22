package com.cyberiansoft.test.vnext.dto;

import com.cyberiansoft.test.vnext.enums.PhaseName;
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

    public void setPhaseName(PhaseName pahse) {
        this.phaseName = pahse.getValue();
    }
}
