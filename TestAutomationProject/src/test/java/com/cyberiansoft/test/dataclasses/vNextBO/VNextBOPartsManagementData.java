package com.cyberiansoft.test.dataclasses.vNextBO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VNextBOPartsManagementData {

    @JsonProperty("location")
    private String location;

    @JsonProperty("dashboardItemsNames")
    private String[] dashboardItemsNames;

    public String getLocation() {
        return location;
    }

    public String[] getDashboardItemsNames() {
        return dashboardItemsNames;
    }
}
