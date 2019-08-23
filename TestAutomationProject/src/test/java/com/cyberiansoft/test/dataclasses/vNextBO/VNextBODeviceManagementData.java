package com.cyberiansoft.test.dataclasses.vNextBO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;

@Getter
public class VNextBODeviceManagementData {

    @JsonProperty("minutes")
    private String minutes;

    @JsonProperty("hours")
    private String hours;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("team")
    private String team;

    @JsonProperty("timeZone")
    private String timeZone;

    @JsonProperty("technician")
    private String technician;

    @JsonProperty("deviceName")
    private String deviceName;
}