package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class MonitorServiceData {

    @JsonProperty("serviceName")
    String serviceName;

    @JsonProperty("serviceStatus")
    String serviceStatus;

    @JsonProperty("startService")
    boolean startService;

    @JsonProperty("completeService")
    boolean completeService;
}
