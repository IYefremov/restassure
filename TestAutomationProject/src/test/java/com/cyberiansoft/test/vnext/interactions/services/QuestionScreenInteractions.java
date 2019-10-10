package com.cyberiansoft.test.vnext.interactions.services;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.vnext.screens.wizardscreens.questions.QuestionScreen;
import com.cyberiansoft.test.vnext.steps.ScreenNavigationSteps;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class QuestionScreenInteractions {
    public static void clickQuestionAnswerBox(String questionName) {
        QuestionScreen questionScreen = new QuestionScreen();
        WaitUtils.waitUntilElementIsClickable(questionScreen.getGeneralQuestionByText(questionName).getRootElement());
        questionScreen.getGeneralQuestionByText(questionName).getAnswerElement().getText();
        WaitUtils.click(questionScreen.getGeneralQuestionByText(questionName).getAnswerElement());
        BaseUtils.waitABit(1000);
    }

    public static void clearQuestion(String questionName) {
        QuestionScreen questionScreen = new QuestionScreen();
        questionScreen.getGeneralQuestionByText(questionName).clearQuestion();
    }

    public static void fillQuestionTextBox(String questionName, String answerText) {
        QuestionScreen questionScreen = new QuestionScreen();
        questionScreen.getTextQuestionByText(questionName).getTextInputElement().sendKeys(answerText);
        ScreenNavigationSteps.pressHardwareBackButton();
    }
}
