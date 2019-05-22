package com.cyberiansoft.test.dataclasses.vNextBO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VNextBOPartsManagementData extends VNextBOBaseData {

    @JsonProperty("location")
    private String location;

    @JsonProperty("dashboardItemsNames")
    private String[] dashboardItemsNames;

    @JsonProperty("statusesList")
    private String[] statusesList;

    public String getLocation() {
        return location;
    }

    public String[] getDashboardItemsNames() {
        return dashboardItemsNames;
    }

    public String[] getStatusesList() {
        return statusesList;
    }
}
