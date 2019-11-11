package com.cyberiansoft.test.dataclasses.vNextBO.deviceManagement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VNextBODeviceManagementSearchData {

    @JsonProperty("platformTitle")
    private String platformTitle;

    @JsonProperty("deviceIconClass")
    private String deviceIconClass;
}
