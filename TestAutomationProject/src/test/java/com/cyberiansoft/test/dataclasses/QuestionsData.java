package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionsData {
    @JsonProperty("questionName")
    private String questionName;
    @JsonProperty("questionSetionName")
    private String questionSetionName;
    @JsonProperty("questionAnswer")
    private String questionAnswer;
    @JsonProperty("questionAnswers")
    private List<String> questionAnswers;
    @JsonProperty("questionAnswerIndex")
    private int questionAnswerIndex;
    @JsonProperty("screenIndex")
    private int screenIndex;
    @JsonProperty("screenHDIndex")
    private int screenHDIndex;
    //@JsonProperty("signatureQuestion")
    //private boolean signatureQuestion;
    @JsonProperty("logicalQuestion")
    private boolean logicalQuestion;
    @JsonProperty("logicalQuestionValue")
    private boolean logicalQuestionValue;
    @JsonProperty("textQuestion")
    private boolean textQuestion;
    @JsonProperty("listQuestion")
    private boolean listQuestion;
    @JsonProperty("imageQuestion")
    private ImageQuestion imageQuestion;
    @JsonProperty("datePickerQuestion")
    private DatePickerQuestion datePickerQuestion;
    @JsonProperty("timePickerQuestion")
    private TimePickerQuestion timePickerQuestion;
    @JsonProperty("signatureQuestion")
    private SignatureQuestion signatureQuestion;
    @JsonProperty("textQuestionData")
    private TextQuestionData textQuestionData;
    @JsonProperty("logicalQuestionData")
    private LogicalQuestionData logicalQuestionData;
}
