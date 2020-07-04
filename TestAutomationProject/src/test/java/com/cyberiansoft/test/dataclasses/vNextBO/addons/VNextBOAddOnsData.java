package com.cyberiansoft.test.dataclasses.vNextBO.addons;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VNextBOAddOnsData {

    @JsonProperty("addOn")
    private String addOn;
}
