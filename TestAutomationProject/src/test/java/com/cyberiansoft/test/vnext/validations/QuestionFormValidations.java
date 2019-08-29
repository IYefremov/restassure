package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.dataclasses.QuestionsData;
import com.cyberiansoft.test.vnext.screens.wizardscreens.questions.QuestionScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.testng.Assert;

public class QuestionFormValidations {
    public static void validateSlideQuestionHasAnswer(String questionName, String expectedAnswer) {
        QuestionScreen questionScreen = new QuestionScreen();
        WaitUtils.getGeneralFluentWait().until(driver -> {
            Assert.assertEquals(
                    questionScreen.getGeneralQuestionByText(questionName).getSelectedAnswer().getText(),
                    expectedAnswer
            );
            return true;
        });
    }

    public static void validateSlideQuestionAnswer(QuestionsData questionData) {
        QuestionFormValidations.validateSlideQuestionHasAnswer(questionData.getQuestionName(), questionData.getQuestionAnswer());
    }
}
