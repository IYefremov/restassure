package com.cyberiansoft.test.dataclasses.vNextBO.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VNextBOServiceData {

    @JsonProperty("serviceName")
    protected String serviceName;

    @JsonProperty("serviceType")
    protected String serviceType;

    @JsonProperty("serviceDescription")
    protected String serviceDescription;

    @JsonProperty("servicePriceType")
    protected String servicePriceType;

    @JsonProperty("servicePrice")
    protected String servicePrice;

    @JsonProperty("serviceLaborRate")
    private String serviceLaborRate;

    @JsonProperty("serviceDefaultLaborTime")
    private String serviceDefaultLaborTime;

    @JsonProperty("serviceUseSelectedLaborTimes")
    private String serviceUseSelectedLaborTimes;

    @JsonProperty("serviceCategory")
    private String serviceCategory;

    @JsonProperty("serviceSubCategory")
    private String serviceSubCategory;

    @JsonProperty("servicePartName")
    private String servicePartName;

    @JsonProperty("serviceClarification")
    private String serviceClarification;

    @JsonProperty("serviceClarificationPrefix")
    private String serviceClarificationPrefix;
}