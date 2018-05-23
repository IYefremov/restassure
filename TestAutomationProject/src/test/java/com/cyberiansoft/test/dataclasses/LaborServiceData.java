package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LaborServiceData {

    @JsonProperty("serviceName")
    String serviceName;

    @JsonProperty("laborServicePanel")
    String laborServicePanel;

    @JsonProperty("laborServicePart")
    String laborServicePart;

    @JsonProperty("laborServiceRate")
    String laborServiceRate;

    @JsonProperty("laborServiceTime")
    String laborServiceTime;

    public String getServiceName() {
        return serviceName;
    }

    public String getLaborServicePanel() {
        return laborServicePanel;
    }

    public String getLaborServicePart() {
        return laborServicePart;
    }

    public String getLaborServiceRate() {
        return laborServiceRate;
    }

    public String getLaborServiceTime() {
        return laborServiceTime;
    }

    public String getLaborServicePrice() {
        return laborServiceRate + " x " + laborServiceTime;
    }
}
