package com.cyberiansoft.test.dataclasses.vNextBO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VNextBOPartsManagementSearchData {

    @JsonProperty("location")
    private String location;

    @JsonProperty("type")
    private String type;

    @JsonProperty("customer")
    private String customer;

    @JsonProperty("phase")
    private String phase;

    @JsonProperty("woNum")
    private String woNum;

    @JsonProperty("woType")
    private String woType;

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public String getCustomer() {
        return customer;
    }

    public String getPhase() {
        return phase;
    }

    public String getWoNum() {
        return woNum;
    }

    public String getWoType() {
        return woType;
    }
}