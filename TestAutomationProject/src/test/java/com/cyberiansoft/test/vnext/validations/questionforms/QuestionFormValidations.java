package com.cyberiansoft.test.vnext.validations.questionforms;

import com.cyberiansoft.test.baseutils.ConditionWaiter;
import com.cyberiansoft.test.dataclasses.QuestionsData;
import com.cyberiansoft.test.vnext.screens.wizardscreens.questions.QuestionScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.GeneralQuestion;
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

    public static void verifyQuestionsScreenIsDisplayed() {

        Assert.assertTrue(new QuestionScreen().getRootElement().isDisplayed(), "Questions screen hasn't been opened");
    }

    public static void verifyQuestionIsDisplayed(String questionText) {

        Assert.assertTrue(new QuestionScreen().getGeneralQuestionByText(questionText).getRootElement().isDisplayed(),
                questionText + " question hasn't been displayed");
    }

    public static void verifyQuestionBackGroundIsRed(String questionText) {

        ConditionWaiter.create(__ -> new QuestionScreen().getGeneralQuestionByText(questionText).getRootElement().getCssValue("background").contains("rgba(239, 83, 78, 0.15)")).execute();
        Assert.assertTrue(new QuestionScreen().getGeneralQuestionByText(questionText).getRootElement().getCssValue("background").contains("rgba(239, 83, 78, 0.15)"),
                "Question background hasn't been red");
    }

    public static void verifyQuestionBackGroundIsGreen(String questionText) {

        ConditionWaiter.create(__ -> new QuestionScreen().getGeneralQuestionByText(questionText).getRootElement().getCssValue("background").contains("rgba(88, 189, 0, 0.15)")).execute();
        Assert.assertTrue(new QuestionScreen().getGeneralQuestionByText(questionText).getRootElement().getCssValue("background").contains("rgba(88, 189, 0, 0.15)"),
                "Question background hasn't been red");
    }

    public static void verifyQuestionBackGroundIsWhite(String questionText) {

        ConditionWaiter.create(__ -> new QuestionScreen().getGeneralQuestionByText(questionText).getRootElement().getCssValue("background").contains("rgba(0, 0, 0, 0)")).execute();
        Assert.assertTrue(new QuestionScreen().getGeneralQuestionByText(questionText).getRootElement().getCssValue("background").contains("rgba(0, 0, 0, 0)"),
                "Question background hasn't been red");
    }

    public static void verifyImageQuestionIsAnswered(QuestionsData questionData, int imagesCount) {

        GeneralQuestion imageQuestion = new QuestionScreen().getGeneralQuestionByText(questionData.getQuestionName());
        validateQuestionAnswered(questionData, true);
        verifyQuestionBackGroundIsGreen(questionData.getQuestionName());
        if (imagesCount > 3) {
            Assert.assertTrue(imageQuestion.getAddedImages().size() == 3, "Added images hasn't been displayed");
            Assert.assertEquals(imageQuestion.getHiddenImagesCount().getText(), "+" + (imagesCount - 3),
                    "Hidden images counter hasn't been correct");
        } else Assert.assertTrue(imageQuestion.getAddedImages().size() == imagesCount, "Added images hasn't been displayed");

        Assert.assertTrue(imageQuestion.getClearIcon().isDisplayed(), "Clear icon hasn't been displayed");
    }

    public static void verifyImageQuestionIsNotAnswered(QuestionsData questionData) {

        GeneralQuestion imageQuestion = new QuestionScreen().getGeneralQuestionByText(questionData.getQuestionName());
        validateQuestionAnswered(questionData, false);
        Assert.assertTrue(imageQuestion.getAddedImages().size() == 0, "Images hasn't been cleared");
        Assert.assertTrue(!imageQuestion.getClearIcon().isDisplayed(), "Clear icon has been displayed");
    }

    public static void clickSelectedAnswerServicesIcon(String questionName) {
        QuestionScreen questionScreen = new QuestionScreen();
        WaitUtils.waitUntilElementIsClickable(questionScreen.getGeneralQuestionByText(questionName).getRootElement());
        WaitUtils.click(questionScreen.getGeneralQuestionByText(questionName).getSelectedAnswerServicesIcon());
    }

    public static void verifySelectedAnswerServicesHaveUnfilledServices(String questionName, boolean haveUnfilled) {
        QuestionScreen questionScreen = new QuestionScreen();
        WaitUtils.waitUntilElementIsClickable(questionScreen.getGeneralQuestionByText(questionName).getRootElement());
        if (haveUnfilled)
            Assert.assertTrue(questionScreen.getGeneralQuestionByText(questionName).
                    getSelectedAnswerServicesIcon().getAttribute("class").contains("unfillFilled-services"));
        else
            Assert.assertFalse(questionScreen.getGeneralQuestionByText(questionName).
                    getSelectedAnswerServicesIcon().getAttribute("class").contains("unfillFilled-services"));

    }

}
