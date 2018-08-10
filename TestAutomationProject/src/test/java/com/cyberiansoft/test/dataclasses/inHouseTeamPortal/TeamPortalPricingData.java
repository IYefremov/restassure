package com.cyberiansoft.test.dataclasses.inHouseTeamPortal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TeamPortalPricingData {

    @JsonProperty("editionName")
    private String editionName;

    @JsonProperty("price")
    private String price;

    @JsonProperty("minCommitment")
    private String minCommitment;

    @JsonProperty("newPrice")
    private String newPrice;

    @JsonProperty("minCommitment2")
    private String minCommitment2;

    @JsonProperty("newPrice2")
    private String newPrice2;

    public String getEditionName() {
        return editionName;
    }

    public String getPrice() {
        return price;
    }

    public String getMinCommitment() {
        return minCommitment;
    }

    public String getNewPrice() {
        return newPrice;
    }

    public String getMinCommitment2() {
        return minCommitment2;
    }

    public String getNewPrice2() {
        return newPrice2;
    }
}
