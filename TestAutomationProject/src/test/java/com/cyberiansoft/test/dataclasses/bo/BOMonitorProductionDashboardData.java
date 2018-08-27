package com.cyberiansoft.test.dataclasses.bo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BOMonitorProductionDashboardData {

    @JsonProperty("location")
    private String location;

    @JsonProperty("customer")
    private String customer;

    public String getLocation() {
        return location;
    }

    public String getCustomer() {
        return customer;
    }
}