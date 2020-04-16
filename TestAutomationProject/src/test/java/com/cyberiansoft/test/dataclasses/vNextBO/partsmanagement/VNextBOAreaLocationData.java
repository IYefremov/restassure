package com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VNextBOAreaLocationData {

    @JsonProperty("area")
    private String area;

    @JsonProperty("provider")
    private String provider;
}
