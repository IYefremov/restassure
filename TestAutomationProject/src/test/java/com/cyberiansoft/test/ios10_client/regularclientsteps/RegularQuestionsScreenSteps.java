package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.dataclasses.QuestionScreenData;
import com.cyberiansoft.test.dataclasses.QuestionsData;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularQuestionsScreen;

import java.util.List;

public class RegularQuestionsScreenSteps {

    public static void waitQuestionsScreenLoaded() {
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.waitQuestionsScreenLoaded();
    }


    public static void goToQuestionsScreenAndAnswerQuestions(QuestionScreenData questionScreenData) {
        RegularNavigationSteps.navigateToScreen(questionScreenData.getScreenName());
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
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        if (questionData.getScreenIndex() > 0) {
            for (int i = 0; i < questionData.getScreenIndex(); i++)
                questionsScreen.swipeScreenUp();
        }
        if (questionData.getQuestionSetionName() != null)
            questionsScreen.waitForQuestionSectionLoad(questionData.getQuestionSetionName());
        if (questionData.isLogicalQuestion())
            questionsScreen.answerLogicalQuestion(questionData);
        else if (questionData.isTextQuestion())
            answerTextQuestion(questionData);
        else if (questionData.isListQuestion())
            questionsScreen.selectListQuestion(questionData);
        else if (questionData.getQuestionAnswer() != null)
            questionsScreen.answerQuestion(questionData);
        else if (questionData.getImageQuestion() != null) {
            if (questionData.getImageQuestion().getQuestionName() != null)
                questionsScreen.clickQuestionCell(questionData.getImageQuestion().getQuestionName());
            RegularNotesScreenSteps.addImegesFromLibrary(questionData.getImageQuestion().getNumberOFImages());
            RegularNotesScreenSteps.clickBackButton();
        } else if (questionData.getDatePickerQuestion() != null) {
            if (questionData.getDatePickerQuestion().getQuestionName() != null)
                questionsScreen.clickQuestionCell(questionData.getDatePickerQuestion().getQuestionName());
            questionsScreen.clickDoneButton();
        }
        else if (questionData.getSignatureQuestion() != null) {
            if (questionData.getSignatureQuestion().getQuestionName() != null) {
                questionsScreen.clickQuestionCell(questionData.getSignatureQuestion().getQuestionName());
                questionsScreen.drawQuestionFormSignature();
                questionsScreen.clickSave();
            } else
                questionsScreen.drawSignature();
        } else
            questionsScreen.answerQuestion(questionData);
    }

    public static void clearTextQuestion() {
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.clearTextQuestion();
    }

    public static void answerTextQuestion(QuestionsData questionData) {
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.answerTextQuestion(questionData);
    }
}
