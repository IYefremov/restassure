package com.cyberiansoft.test.vnext.steps.questionform;

import com.cyberiansoft.test.dataclasses.QuestionsData;
import com.cyberiansoft.test.vnext.interactions.ListSelectPageInteractions;
import com.cyberiansoft.test.vnext.interactions.QuestionScreenInteractions;

public class QuestionFormSteps {
    public static void answerGeneralSlideQuestion(String questionName) {
        QuestionScreenInteractions.clickQuestionAnswerBox(questionName);
    }

    public static void answerGeneralSlideQuestion(QuestionsData questionData) {
        QuestionScreenInteractions.clickQuestionAnswerBox(questionData.getQuestionName());
    }

    public static void clearQuestion(String questionName) {
        QuestionScreenInteractions.clearQuestion(questionName);
    }

    public static void clearQuestion(QuestionsData questionData) {
        QuestionFormSteps.clearQuestion(questionData.getQuestionName());
    }

    public static void answerGeneralPredefinedQuestion(QuestionsData questionData, Boolean isMultiSelect) {

        QuestionScreenInteractions.clickQuestionAnswerBox(questionData.getQuestionName());
        if (isMultiSelect) {
            questionData.getQuestionAnswers().forEach(ListSelectPageInteractions::selectItem);
            ListSelectPageInteractions.saveListPage();
        } else
            ListSelectPageInteractions.selectItem(questionData.getQuestionAnswers().get(0));
    }

    public static void answerGeneralTextQuestion(QuestionsData textQuestion) {
        QuestionScreenInteractions.clickQuestionAnswerBox(textQuestion.getQuestionName());
        QuestionScreenInteractions.fillQuestionTextBox(textQuestion.getQuestionName(), textQuestion.getQuestionAnswer());
    }
}
