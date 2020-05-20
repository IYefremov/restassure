package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.dataclasses.QuestionsData;
import com.cyberiansoft.test.vnext.screens.wizardscreens.questions.QuestionScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.testng.Assert;

public class QuestionFormValidations {
    public static void validateGeneralQuestionHasAnswer(String questionName, String expectedAnswer) {
        QuestionScreen questionScreen = new QuestionScreen();
        WaitUtils.getGeneralFluentWait().until(driver -> {
            Assert.assertEquals(
                    questionScreen.getAnsweredQuestionByText(questionName).getSelectedAnswer().getText(),
                    expectedAnswer
            );
            return true;
        });
    }

    public static void validateGeneralQuestionAnswer(QuestionsData questionData) {
        QuestionFormValidations.validateGeneralQuestionHasAnswer(questionData.getQuestionName(), questionData.getQuestionAnswer());
    }

    public static void validateTextQuestionAnswer(QuestionsData questionData) {
        QuestionScreen questionScreen = new QuestionScreen();
        WaitUtils.getGeneralFluentWait().until(driver -> {
            Assert.assertEquals(
                    questionScreen.getTextQuestionByText(questionData.getQuestionName()).getTextInputElement().getAttribute("value"),
                    questionData.getQuestionAnswer()
            );
            return true;
        });
    }

    public static void validateQuestionAnswered(QuestionsData questionData, boolean answered) {
        QuestionScreen questionScreen = new QuestionScreen();
        if (answered)
            Assert.assertTrue(questionScreen.getTextQuestionByText(questionData.getQuestionName()).getRootElement().getAttribute("class").contains("answered-question"));
        else
            Assert.assertFalse(questionScreen.getTextQuestionByText(questionData.getQuestionName()).getRootElement().getAttribute("class").contains("answered-question"));
    }

    public static void validateQuestionHasNotes(String questionName, boolean hasNotes) {
        QuestionScreen questionScreen = new QuestionScreen();
        if (hasNotes)
            Assert.assertTrue(questionScreen.getAnsweredQuestionByText(questionName).getQuestionNotesIcon().getAttribute("class").contains("has-notes"));
        else
            Assert.assertFalse(questionScreen.getAnsweredQuestionByText(questionName).getQuestionNotesIcon().getAttribute("class").contains("has-notes"));
    }

}
