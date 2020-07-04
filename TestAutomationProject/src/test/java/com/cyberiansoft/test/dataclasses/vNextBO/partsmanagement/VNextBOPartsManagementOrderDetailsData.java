package com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement;

import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOBaseData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
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

    @JsonProperty("status")
    private String status;
}