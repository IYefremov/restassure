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
                    questionScreen.getGeneralQuestionByText(questionName).getAnswerElement().getText(),
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
}
