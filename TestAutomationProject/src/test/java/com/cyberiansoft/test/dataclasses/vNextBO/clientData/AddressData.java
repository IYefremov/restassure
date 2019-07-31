package com.cyberiansoft.test.dataclasses.vNextBO.clientData;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AddressData {

    @JsonProperty("address1")
    private String address1;

    @JsonProperty("address2")
    private String address2;

    @JsonProperty("city")
    private String city;

    @JsonProperty("country")
    private String country;

    @JsonProperty("stateProvince")
    private String stateProvince;

    @JsonProperty("zip")
    private String zip;

    @JsonProperty("address11")
    private String address11;

    @JsonProperty("address22")
    private String address22;

    @JsonProperty("city2")
    private String city2;

    @JsonProperty("country2")
    private String country2;

    @JsonProperty("stateProvince2")
    private String stateProvince2;

    @JsonProperty("zip2")
    private String zip2;
}