package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderMonitorData {

    @JsonProperty("phaseName")
    String phaseName;

    @JsonProperty("monitorServiceData")
    MonitorServiceData monitorServiceData;

    @JsonProperty("monitorServicesData")
    List<MonitorServiceData> monitorServicesData;

    @JsonProperty("defaultTechnician")
    ServiceTechnician defaultTechnician;

    @JsonProperty("defaultTechnicians")
    List<ServiceTechnician> defaultTechnicians;

    @JsonProperty("newTechnician")
    ServiceTechnician newTechnician;

    @JsonProperty("newTechnicians")
    List<ServiceTechnician> newTechnicians;
}
