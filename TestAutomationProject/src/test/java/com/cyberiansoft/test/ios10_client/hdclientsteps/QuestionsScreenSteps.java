package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.dataclasses.QuestionScreenData;
import com.cyberiansoft.test.dataclasses.QuestionsData;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.BaseWizardScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.QuestionsScreen;
import com.cyberiansoft.test.ios10_client.types.wizardscreens.WizardScreenTypes;

import java.util.List;

public class QuestionsScreenSteps {

    public static void goToQuestionsScreenAndAnswerQuestions(QuestionScreenData questionScreenData) {
      NavigationSteps.navigateToScreen(questionScreenData.getScreenName());
        if (questionScreenData.getQuestionsData() != null)
            answerQuestions(questionScreenData.getQuestionsData());
        if (questionScreenData.getQuestionData() != null)
            answerQuestion(questionScreenData.getQuestionData());
    }

    public static void answerQuestions(List<QuestionsData> questionsData) {
        for (QuestionsData questionData : questionsData) {
            answerQuestion(questionData);
        }
    }

    public static void answerQuestion(QuestionsData questionData) {
        QuestionsScreen questionsScreen = new QuestionsScreen();
        if (questionData.getScreenHDIndex() > 0) {
            for (int i = 0; i < questionData.getScreenHDIndex(); i++)
                questionsScreen.swipeScreenRight();
        }
        if (questionData.getQuestionSetionName() != null)
            questionsScreen.waitQuestionsScreenLoaded();
        if (questionData.getQuestionAnswer() != null)
            questionsScreen.selectAnswerForQuestion(questionData);
        if (questionData.isSignatureQuestion())
            questionsScreen.drawSignature();

    }
}
