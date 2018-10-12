package com.cyberiansoft.test.dataclasses.vNextBO;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public String getCompanyName() {
        return companyName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCompanyMail() {
        return companyMail;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public String getShipToAddress() {
        return shipToAddress;
    }

    public String getShipToAddress2() {
        return shipToAddress2;
    }

    public String getShipToCity() {
        return shipToCity;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getShipToZip() {
        return shipToZip;
    }

    public String getTechFirstName() {
        return techFirstName;
    }

    public String getTechLastName() {
        return techLastName;
    }

    public String getUserMailPrefix() {
        return userMailPrefix;
    }

    public String getUserMailPostbox() {
        return userMailPostbox;
    }

    public String getTechUserPhone() {
        return techUserPhone;
    }
}
