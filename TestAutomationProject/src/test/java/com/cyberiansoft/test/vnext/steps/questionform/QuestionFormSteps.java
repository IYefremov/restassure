package com.cyberiansoft.test.vnext.steps.questionform;

import com.cyberiansoft.test.dataclasses.LogicalQuestionData;
import com.cyberiansoft.test.dataclasses.QuestionsData;
import com.cyberiansoft.test.dataclasses.TextQuestionData;
import com.cyberiansoft.test.vnext.interactions.ListSelectPageInteractions;
import com.cyberiansoft.test.vnext.interactions.services.QuestionScreenInteractions;

public class QuestionFormSteps {

    public static void answerGeneralSlideQuestion(QuestionsData questionData) {
        QuestionScreenInteractions.clickQuestionAnswer(questionData.getQuestionName(), questionData.getQuestionAnswer());
    }

    public static void answerGeneralQuestion(QuestionsData questionData) {
        QuestionScreenInteractions.clickQuestionAnswer(questionData.getQuestionName(), questionData.getQuestionAnswer());
    }


    public static void answerGeneralPredefinedQuestion(QuestionsData questionData, Boolean isMultiSelect) {
        if (isMultiSelect) {
            questionData.getQuestionAnswers().forEach(answer ->
                    QuestionScreenInteractions.clickQuestionAnswer(questionData.getQuestionName(), answer)
            );
            QuestionScreenInteractions.saveMulitAnswerQuestion(questionData.getQuestionName());
        } else
            QuestionScreenInteractions.clickQuestionAnswer(questionData.getQuestionName(), questionData.getQuestionAnswer());
    }

    public static void answerGeneralTextQuestion(QuestionsData textQuestion) {
        QuestionScreenInteractions.fillQuestionTextBox(textQuestion.getQuestionName(), textQuestion.getQuestionAnswer());
    }

    public static void answerTextQuestion(TextQuestionData textQuestion) {
        QuestionScreenInteractions.fillQuestionTextBox(textQuestion.getQuestionName(), textQuestion.getQuestionAnswerText());
    }

    public static void answerLogicalQuestion(LogicalQuestionData logicalQuestion) {
        QuestionScreenInteractions.clickQuestionAnswer(logicalQuestion.getQuestionName(), logicalQuestion.getLogicalAnswer());
    }

    public static void saveQuestionForm() {
        ListSelectPageInteractions.saveListPage();
    }
}
