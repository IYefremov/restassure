package com.cyberiansoft.test.dataclasses.vNextBO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VNextBOPartsManagementOrderDetailsData extends VNextBOBaseData {

    @JsonProperty("location")
    private String location;

    @JsonProperty("woNum")
    private String woNum;

    @JsonProperty("labor")
    private String labor;

    @JsonProperty("laborStart")
    private String laborStart;

    @JsonProperty("oemNum")
    private String oemNum;

    public String getLocation() {
        return location;
    }

    public String getWoNum() {
        return woNum;
    }

    public String getLabor() {
        return labor;
    }

    public String getLaborStart() {
        return laborStart;
    }

    public String getOemNum() {
        return oemNum;
    }
}