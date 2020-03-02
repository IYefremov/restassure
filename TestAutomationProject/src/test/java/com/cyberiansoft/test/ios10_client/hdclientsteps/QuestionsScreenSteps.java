package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.dataclasses.QuestionScreenData;
import com.cyberiansoft.test.dataclasses.QuestionsData;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.QuestionsScreen;

import java.util.List;

public class QuestionsScreenSteps {

    public static void waitQuestionsScreenLoaded() {
        QuestionsScreen questionsScreen = new QuestionsScreen();
        questionsScreen.waitQuestionsScreenLoaded();
    }

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
        if (questionData.getQuestionSetionName() != null) {
            questionsScreen.waitQuestionsScreenLoaded();
        }
        if (questionData.isListQuestion())
            questionsScreen.selectListQuestion(questionData);
        else if (questionData.isTextQuestion())
            answerTextQuestion(questionData);
        else if (questionData.getQuestionAnswer() != null)
            questionsScreen.selectAnswerForQuestion(questionData);
        else if (questionData.getSignatureQuestion() != null)
            questionsScreen.drawSignature();
        else
            questionsScreen.selectAnswerForQuestion(questionData);

    }

    public static void answerTextQuestion(QuestionsData questionData) {
        QuestionsScreen questionsScreen = new QuestionsScreen();
        questionsScreen.answerTextQuestion(questionData);
    }

    public static void clearTextQuestion() {
        QuestionsScreen questionsScreen = new QuestionsScreen();
        questionsScreen.clearTextQuestion();
    }
}
