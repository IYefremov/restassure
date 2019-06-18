package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionsData {
    @JsonProperty("questionName")
    private String questionName;
    @JsonProperty("questionSetionName")
    private String questionSetionName;
    @JsonProperty("questionAnswer")
    private String questionAnswer;
    @JsonProperty("screenIndex")
    private int screenIndex;
    @JsonProperty("signatureQuestion")
    private boolean signatureQuestion;
}
