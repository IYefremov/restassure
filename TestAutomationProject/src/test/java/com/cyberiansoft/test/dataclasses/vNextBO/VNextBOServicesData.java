package com.cyberiansoft.test.dataclasses.vNextBO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VNextBOServicesData {

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("userPassword")
    private String userPassword;

    @JsonProperty("userMail")
    private String userMail;

    @JsonProperty("priceServiceName")
    private String priceServiceName;

    @JsonProperty("serviceType")
    private String serviceType;

    @JsonProperty("serviceName")
    private String serviceName;

    @JsonProperty("serviceDescription")
    private String serviceDescription;

    @JsonProperty("servicePriceType")
    private String servicePriceType;

    @JsonProperty("servicePrice")
    private String servicePrice;

    @JsonProperty("serviceTypeEdited")
    private String serviceTypeEdited;

    @JsonProperty("servicePriceEdited")
    private String servicePriceEdited;

    @JsonProperty("serviceEdited")
    private String serviceEdited;

    @JsonProperty("percentageServiceName")
    private String percentageServiceName;

    @JsonProperty("percentageServiceDescription")
    private String percentageServiceDescription;

    @JsonProperty("servicePercentageType")
    private String servicePercentageType;

    @JsonProperty("emptyServiceName")
    private String emptyServiceName;

    @JsonProperty("matrixServiceType")
    private String matrixServiceType;

    @JsonProperty("newMatrixServiceName")
    private String newMatrixServiceName;
}