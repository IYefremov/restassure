package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class TargetProcessTestCaseData {

    @JsonProperty("testCaseID")
    String testCaseID;

    @JsonProperty("testCaseTitle")
    String testCaseTitle;
}
