package com.cyberiansoft.test.dataclasses.vNextBO.servicerequests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VNextBOSRData {

    @JsonProperty("searchData")
    private VNextBOSRSearchData searchData;
}
