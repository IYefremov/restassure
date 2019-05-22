package com.cyberiansoft.test.dataclasses.vNextBO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VNextBOPartsManagementSearchData extends VNextBOBaseData{

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

    @JsonProperty("stockNum")
    private String stockNum;

    @JsonProperty("OEMNum")
    private String OEMNum;

    @JsonProperty("VINNum")
    private String VINNum;

    @JsonProperty("woType")
    private String woType;

    @JsonProperty("notes")
    private String notes;

    @JsonProperty("orderedFrom")
    private String orderedFrom;

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

    public String getStockNum() {
        return stockNum;
    }

    public String getOEMNum() {
        return OEMNum;
    }

    public String getVINNum() {
        return VINNum;
    }

    public String getNotes() {
        return notes;
    }

    public String getWoType() {
        return woType;
    }

    public String getOrderedFrom() {
        return orderedFrom;
    }
}