package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class LogicalQuestionData {

    @JsonProperty("questionName")
    private String questionName;
    @JsonProperty("logicalAnswer")
    private String logicalAnswer;
}
