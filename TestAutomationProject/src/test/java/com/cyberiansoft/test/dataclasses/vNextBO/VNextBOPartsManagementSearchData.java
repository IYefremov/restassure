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

    @JsonProperty("oemNum")
    private String oemNum;

    @JsonProperty("vinNum")
    private String vinNum;

    @JsonProperty("woType")
    private String woType;

    @JsonProperty("notes")
    private String notes;

    @JsonProperty("orderedFrom")
    private String orderedFrom;

    @JsonProperty("searchName")
    private String searchName;

    @JsonProperty("defaultValue")
    private String defaultValue;

    @JsonProperty("emptyPartsListMessage")
    private String emptyPartsListMessage;

    @JsonProperty("typeChanged")
    private String typeChanged;

    @JsonProperty("phaseChanged")
    private String phaseChanged;

    @JsonProperty("woTypeChanged")
    private String woTypeChanged;

    @JsonProperty("searchNameChanged")
    private String searchNameChanged;

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

    public String getOemNum() {
        return oemNum;
    }

    public String getVinNum() {
        return vinNum;
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

    public String getSearchName() {
        return searchName;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getEmptyPartsListMessage() {
        return emptyPartsListMessage;
    }

    public String getTypeChanged() {
        return typeChanged;
    }

    public String getPhaseChanged() {
        return phaseChanged;
    }

    public String getWoTypeChanged() {
        return woTypeChanged;
    }

    public String getSearchNameChanged() {
        return searchNameChanged;
    }
}