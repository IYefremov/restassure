package com.cyberiansoft.test.vnext.dto;

import com.cyberiansoft.test.dataclasses.MonitorServiceData;
import com.cyberiansoft.test.vnext.enums.PhaseName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrderPhaseDto {
    @JsonProperty("status")
    private String status;
    @JsonProperty("phaseName")
    private String phaseName;
    @JsonProperty("problemReason")
    private String problemReason;
    @JsonProperty("phaseServices")
    private List<MonitorServiceData> phaseServices;

    public void setStatus(PhaseName phase) {
        this.status = phase.getValue();
    }
}
