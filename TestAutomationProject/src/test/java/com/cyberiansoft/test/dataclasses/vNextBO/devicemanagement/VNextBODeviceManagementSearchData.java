package com.cyberiansoft.test.dataclasses.vNextBO.devicemanagement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VNextBODeviceManagementSearchData {

    @JsonProperty("platform")
    private String platform;
}
