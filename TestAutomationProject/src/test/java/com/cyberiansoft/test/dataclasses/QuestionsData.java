package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionsData {
    @JsonProperty("questionName")
    private String questionName;
    @JsonProperty("questionAnswer")
    private String questionAnswer;
}
