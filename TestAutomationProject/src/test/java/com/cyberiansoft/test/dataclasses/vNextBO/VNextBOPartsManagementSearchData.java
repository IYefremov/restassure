package com.cyberiansoft.test.dataclasses.vNextBO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VNextBOPartsManagementSearchData extends VNextBOBaseData {

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

    @JsonProperty("part")
    private String part;
}