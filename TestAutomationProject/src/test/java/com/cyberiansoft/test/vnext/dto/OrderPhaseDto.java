package com.cyberiansoft.test.vnext.dto;

import com.cyberiansoft.test.vnext.enums.PhaseName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderPhaseDto {
    @JsonProperty("status")
    private String status;
    @JsonProperty("phaseName")
    private String phaseName;

    public void setStatus(PhaseName phase) {
        this.status = phase.getValue();
    }
}
