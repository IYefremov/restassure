package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ImageQuestion {

    @JsonProperty("questionName")
    private String questionName;

    @JsonProperty("numberOFImages")
    private int numberOFImages;
}
