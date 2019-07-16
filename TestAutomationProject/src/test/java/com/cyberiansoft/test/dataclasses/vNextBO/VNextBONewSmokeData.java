package com.cyberiansoft.test.dataclasses.vNextBO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VNextBONewSmokeData {

    @JsonProperty("company")
    private String[] company;

    @JsonProperty("addressLine1")
    private String[] addressLine1;

    @JsonProperty("addressLine2")
    private String[] addressLine2;

    @JsonProperty("city")
    private String[] city;

    @JsonProperty("country")
    private String[] country;

    @JsonProperty("stateProvince")
    private String[] stateProvince;

    @JsonProperty("zip")
    private String[] zip;

    @JsonProperty("email")
    private String[] email;

    @JsonProperty("phoneCode")
    private String[] phoneCode;

    @JsonProperty("phone")
    private String[] phone;

    @JsonProperty("location")
    private String location;

    @JsonProperty("woNum")
    private String woNum;

    @JsonProperty("service")
    private String service;

    @JsonProperty("serviceDescription")
    private String serviceDescription;

    @JsonProperty("serviceCategory")
    private String serviceCategory;

    @JsonProperty("serviceSubcategory")
    private String serviceSubcategory;

    @JsonProperty("status")
    private String status;

    @JsonProperty("labor")
    private String labor;

    @JsonProperty("note")
    private String note;

    @JsonProperty("partItems")
    private String[] partItems;

    @JsonProperty("statuses")
    private String[] statuses;
}