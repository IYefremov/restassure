package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class MonitorServiceData {

    @JsonProperty("monitorService")
    ServiceData monitorService;

    @JsonProperty("monitorServiceStatus")
    String monitorServiceStatus;

    @JsonProperty("serviceVendor")
    String serviceVendor;

    @JsonProperty("startService")
    boolean startService;

    @JsonProperty("completeService")
    boolean completeService;
}
