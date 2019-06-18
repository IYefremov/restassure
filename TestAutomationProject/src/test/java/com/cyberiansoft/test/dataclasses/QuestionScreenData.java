package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionScreenData {

    @JsonProperty("screenName")
    String screenName;

    @JsonProperty("questionData")
    QuestionsData questionData;

    @JsonProperty("questionsData")
    List<QuestionsData> questionsData;
}
