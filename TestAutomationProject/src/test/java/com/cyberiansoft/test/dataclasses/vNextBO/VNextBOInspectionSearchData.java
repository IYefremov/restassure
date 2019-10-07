package com.cyberiansoft.test.dataclasses.vNextBO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VNextBOInspectionSearchData {

    @JsonProperty("valueForSearch")
    private String valueForSearch;
}
