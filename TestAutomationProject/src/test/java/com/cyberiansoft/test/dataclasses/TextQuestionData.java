package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class TextQuestionData {
    @JsonProperty("questionName")
    private String questionName;
    @JsonProperty("questionAnswerText")
    private String questionAnswerText;
}
