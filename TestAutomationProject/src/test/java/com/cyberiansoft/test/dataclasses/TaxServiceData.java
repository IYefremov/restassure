package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TaxServiceData {

    @JsonProperty("taxServiceName")
    private String taxServiceName;

    @JsonProperty("serviceRateData")
    private ServiceRateData serviceRateData;

    @JsonProperty("serviceRatesData")
    private List<ServiceRateData> serviceRatesData;

    @JsonProperty("taxServiceTotal")
    private String taxServiceTotal;

}


