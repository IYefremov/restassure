package com.cyberiansoft.test.vnext.steps.questionform;

import com.cyberiansoft.test.dataclasses.QuestionsData;
import com.cyberiansoft.test.vnext.interactions.QuestionScreenInteractions;

public class QuestionFormSteps {
    public static void answerGeneralSlideQuestion(String questionName) {
        QuestionScreenInteractions.slideQuestion(questionName);
    }

    public static void answerGeneralSlideQuestion(QuestionsData questionData) {
        QuestionScreenInteractions.slideQuestion(questionData.getQuestionName());
    }

    public static void clearQuestion(String questionName) {
        QuestionScreenInteractions.clearQuestion(questionName);
    }

    public static void clearQuestion(QuestionsData questionData) {
        QuestionFormSteps.clearQuestion(questionData.getQuestionName());
    }
}
