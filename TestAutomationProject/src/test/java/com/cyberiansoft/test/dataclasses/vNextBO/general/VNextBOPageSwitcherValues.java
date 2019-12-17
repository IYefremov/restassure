package com.cyberiansoft.test.dataclasses.vNextBO.general;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VNextBOPageSwitcherValues {

    @JsonProperty("ten")
    private String ten;

    @JsonProperty("twenty")
    private String twenty;

    @JsonProperty("fifty")
    private String fifty;

    @JsonProperty("hundred")
    private String hundred;
}
