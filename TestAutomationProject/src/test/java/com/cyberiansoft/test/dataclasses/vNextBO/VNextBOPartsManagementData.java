package com.cyberiansoft.test.dataclasses.vNextBO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class VNextBOPartsManagementData {

    @JsonProperty("location")
    private String location;

    @JsonProperty("dashboardItemsNames")
    private String[] dashboardItemsNames;

    @JsonProperty("statusesList")
    private String[] statusesList;

    public String getLocation() {
        return location;
    }

    public String getCurrentDate() {
        LocalDate localDate = LocalDate.now(ZoneId.of("US/Pacific"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return localDate.format(formatter);
    }

    public String[] getDashboardItemsNames() {
        return dashboardItemsNames;
    }

    public String[] getStatusesList() {
        return statusesList;
    }
}
