package com.cyberiansoft.test.vnext.interactions.services;

import com.cyberiansoft.test.vnext.screens.wizardscreens.questions.QuestionScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class QuestionScreenInteractions {
    public static void clickQuestionAnswer(String questionName, String questionAnswer) {
        QuestionScreen questionScreen = new QuestionScreen();
        WaitUtils.waitUntilElementIsClickable(questionScreen.getGeneralQuestionByText(questionName).getRootElement());
        WaitUtils.click(questionScreen.getGeneralQuestionByText(questionName).getAnswerElement(questionAnswer));
    }


    public static void fillQuestionTextBox(String questionName, String answerText) {
        QuestionScreen questionScreen = new QuestionScreen();
        questionScreen.getTextQuestionByText(questionName).getTextInputElement().sendKeys(answerText);
    }

    public static void saveMulitAnswerQuestion(String questionName) {
        QuestionScreen questionScreen = new QuestionScreen();
        WaitUtils.click(questionScreen.getGeneralQuestionByText(questionName).getSaveButton());
    }
}
