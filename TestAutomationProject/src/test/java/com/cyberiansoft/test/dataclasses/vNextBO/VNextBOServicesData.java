package com.cyberiansoft.test.dataclasses.vNextBO;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserMail() {
        return userMail;
    }

    public String getPriceServiceName() {
        return priceServiceName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public String getServicePriceType() {
        return servicePriceType;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public String getServiceTypeEdited() {
        return serviceTypeEdited;
    }

    public String getServicePriceEdited() {
        return servicePriceEdited;
    }

    public String getServiceEdited() {
        return serviceEdited;
    }

    public String getPercentageServiceName() {
        return percentageServiceName;
    }

    public String getPercentageServiceDescription() {
        return percentageServiceDescription;
    }

    public String getServicePercentageType() {
        return servicePercentageType;
    }

    public String getEmptyServiceName() {
        return emptyServiceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getMatrixServiceType() {
        return matrixServiceType;
    }

    public String getNewMatrixServiceName() {
        return newMatrixServiceName;
    }
}