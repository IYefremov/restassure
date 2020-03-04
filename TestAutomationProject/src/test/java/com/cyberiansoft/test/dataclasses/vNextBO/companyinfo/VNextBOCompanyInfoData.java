package com.cyberiansoft.test.dataclasses.vNextBO.companyinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VNextBOCompanyInfoData {

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
}
