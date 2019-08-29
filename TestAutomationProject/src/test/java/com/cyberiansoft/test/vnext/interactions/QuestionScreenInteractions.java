package com.cyberiansoft.test.vnext.interactions;

import com.cyberiansoft.test.vnext.screens.wizardscreens.questions.QuestionScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class QuestionScreenInteractions {
    public static void slideQuestion(String questionName) {
        QuestionScreen questionScreen = new QuestionScreen();
        WaitUtils.click(questionScreen.getGeneralQuestionByText(questionName).getSelectedAnswer());
    }

    public static void clearQuestion(String questionName) {
        QuestionScreen questionScreen = new QuestionScreen();
        questionScreen.getGeneralQuestionByText(questionName).clearQuestion();
    }
}
