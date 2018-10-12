package com.cyberiansoft.test.dataclasses.inHouseTeamPortal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TeamPortalClientsData {

    @JsonProperty("category")
    private String category;

    @JsonProperty("attributeName")
    private String attributeName;

    @JsonProperty("dataType")
    private String dataType;

    public String getCategory() {
        return category;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public String getDataType() {
        return dataType;
    }
}
