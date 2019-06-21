package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class MonitorServiceData {

    @JsonProperty("monitorService")
    ServiceData monitorService;

    @JsonProperty("monitorServiceStatus")
    private String monitorServiceStatus;

    @JsonProperty("startService")
    boolean startService;

    @JsonProperty("completeService")
    boolean completeService;
}
