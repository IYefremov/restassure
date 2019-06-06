package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceRateData {

    @JsonProperty("serviceRateName")
    private String serviceRateName;

    @JsonProperty("serviceRateValue")
    private String serviceRateValue;

    @JsonProperty("isServiceRateEditable")
    private boolean isServiceRateEditable;
}
