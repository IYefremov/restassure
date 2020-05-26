package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SelectListOptionQuestion {
    @JsonProperty("questionName")
    private String questionName;
    @JsonProperty("questionAnswer")
    private String questionAnswer;
    @JsonProperty("questionAnswers")
    private List<String> questionAnswers;
    @JsonProperty("isMultiselect")
    private boolean isMultiselect;
}
