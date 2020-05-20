package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class TimePickerQuestion {

    @JsonProperty("questionName")
    private String questionName;
}
