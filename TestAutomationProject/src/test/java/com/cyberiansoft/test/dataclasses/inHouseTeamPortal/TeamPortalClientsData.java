package com.cyberiansoft.test.dataclasses.inHouseTeamPortal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TeamPortalClientsData {

    @JsonProperty("category")
    private String category;

    @JsonProperty("attributeName")
    private String attributeName;

    @JsonProperty("dataType")
    private String dataType;

    @JsonProperty("isAutomatedStatus")
    private String isAutomatedStatus;

    @JsonProperty("procedureName")
    private String procedureName;

    public String getCategory() {
        return category;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public String getDataType() {
        return dataType;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public String getIsAutomatedStatus() {
        return isAutomatedStatus;
    }
}
