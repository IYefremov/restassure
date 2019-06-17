package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderMonitorData {

    @JsonProperty("monitorServiceData")
    MonitorServiceData monitorServiceData;

    @JsonProperty("monitorServicesData")
    List<MonitorServiceData> monitorServicesData;
}
