package com.cyberiansoft.test.dataclasses.vNextBO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VNextBOInvoiceDetailsData {

    @JsonProperty("companyName")
    private String companyName;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("companyMail")
    private String companyMail;

    @JsonProperty("companyPhone")
    private String companyPhone;

    @JsonProperty("shipToAddress")
    private String shipToAddress;

    @JsonProperty("shipToAddress2")
    private String shipToAddress2;

    @JsonProperty("shipToCity")
    private String shipToCity;

    @JsonProperty("country")
    private String country;

    @JsonProperty("state")
    private String state;

    @JsonProperty("shipToZip")
    private String shipToZip;

    @JsonProperty("techFirstName")
    private String techFirstName;

    @JsonProperty("techLastName")
    private String techLastName;

    @JsonProperty("userMailPrefix")
    private String userMailPrefix;

    @JsonProperty("userMailPostbox")
    private String userMailPostbox;

    @JsonProperty("techUserPhone")
    private String techUserPhone;
}
