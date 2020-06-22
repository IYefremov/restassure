package com.cyberiansoft.test.vnext.steps.questionform;

import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.vnext.interactions.ListSelectPageInteractions;
import com.cyberiansoft.test.vnext.interactions.services.QuestionScreenInteractions;
import com.cyberiansoft.test.vnext.screens.wizardscreens.questions.QuestionScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.GeneralQuestion;

public class QuestionFormSteps {

    public static void answerGeneralSlideQuestion(QuestionsData questionData) {
        QuestionScreenInteractions.clickQuestionAnswer(questionData.getQuestionName(), questionData.getQuestionAnswer());
    }

    public static void answerGeneralQuestion(QuestionsData questionData) {
        QuestionScreenInteractions.clickQuestionAnswer(questionData.getQuestionName(), questionData.getQuestionAnswer());
    }


    public static void answerGeneralPredefinedQuestion(QuestionsData questionData, Boolean isMultiSelect) {
        if (isMultiSelect) {
            questionData.getQuestionAnswers().forEach(answer ->
                    QuestionScreenInteractions.clickQuestionAnswer(questionData.getQuestionName(), answer)
            );
            QuestionScreenInteractions.saveMulitAnswerQuestion(questionData.getQuestionName());
        } else
            QuestionScreenInteractions.clickQuestionAnswer(questionData.getQuestionName(), questionData.getQuestionAnswer());
    }

    public static void answerListOptionQuestion(SelectListOptionQuestion selectListOptionQuestion) {
        if (selectListOptionQuestion.isMultiselect()) {
            selectListOptionQuestion.getQuestionAnswers().forEach(answer ->
                    QuestionScreenInteractions.clickQuestionAnswer(selectListOptionQuestion.getQuestionName(), answer)
            );
            QuestionScreenInteractions.saveMulitAnswerQuestion(selectListOptionQuestion.getQuestionName());
        } else
            QuestionScreenInteractions.clickQuestionAnswer(selectListOptionQuestion.getQuestionName(), selectListOptionQuestion.getQuestionAnswer());
    }

    public static void answerGeneralTextQuestion(QuestionsData textQuestion) {
        QuestionScreenInteractions.fillQuestionTextBox(textQuestion.getQuestionName(), textQuestion.getQuestionAnswer());
    }

    public static void answerTextQuestion(TextQuestionData textQuestion) {
        QuestionScreenInteractions.fillQuestionTextBox(textQuestion.getQuestionName(), textQuestion.getQuestionAnswerText());
    }

    public static void answerLogicalQuestion(LogicalQuestionData logicalQuestion) {
        QuestionScreenInteractions.clickQuestionAnswer(logicalQuestion.getQuestionName(), logicalQuestion.getLogicalAnswer());
    }

    public static void saveQuestionForm() {
        ListSelectPageInteractions.saveListPage();
    }

    public static void answerImageQuestion(QuestionsData questionData) {
        QuestionScreenInteractions.clickQuestionCamera(questionData.getQuestionName());
    }

    public static void answerImageQuestion(ImageQuestion imageQuestion) {
        if (imageQuestion.getNumberOFImages() > 0) {
            for (int i = 0; i < imageQuestion.getNumberOFImages(); i++)
                QuestionScreenInteractions.clickQuestionCamera(imageQuestion.getQuestionName());
        }
        else
            QuestionScreenInteractions.clickQuestionCamera(imageQuestion.getQuestionName());
    }

    public static void clearSelectedQuestion(String questionName) {
        QuestionScreen questionScreen = new QuestionScreen();
        questionScreen.getGeneralQuestionByText(questionName).clearQuestion();
    }

    public static void answerDateQuestion(DatePickerQuestion datePickerQuestion, int month, int day, int year) {
        QuestionScreen questionScreen = new QuestionScreen();
        WaitUtils.waitUntilElementIsClickable(questionScreen.getNotAnsweredQuestionByText(datePickerQuestion.getQuestionName()).getRootElement());
        questionScreen.getNotAnsweredQuestionByText(datePickerQuestion.getQuestionName()).getSelectDateElement().click();
        questionScreen.selectDatePickerValue(month, day, year);
        questionScreen.getNotAnsweredQuestionByText(datePickerQuestion.getQuestionName()).getQuestionNameElement().click();
    }

    public static void answerTimeQuestion(TimePickerQuestion timePickerQuestion, int hours, int minutes, int seconds) {
        QuestionScreen questionScreen = new QuestionScreen();
        WaitUtils.waitUntilElementIsClickable(questionScreen.getNotAnsweredQuestionByText(timePickerQuestion.getQuestionName()).getRootElement());
        questionScreen.getNotAnsweredQuestionByText(timePickerQuestion.getQuestionName()).getSelectTimeElement().click();
        questionScreen.selectDatePickerValue(hours, minutes, seconds);
        questionScreen.getNotAnsweredQuestionByText(timePickerQuestion.getQuestionName()).getQuestionNameElement().click();
    }

    public static void answerSignatureQuestion(SignatureQuestion signatureQuestion) {
        QuestionScreen questionScreen = new QuestionScreen();
        WaitUtils.waitUntilElementIsClickable(questionScreen.getNotAnsweredQuestionByText(signatureQuestion.getQuestionName()).getRootElement());
        questionScreen.getNotAnsweredQuestionByText(signatureQuestion.getQuestionName()).getSignatureElement().click();
        questionScreen.drawSignature();
    }

    public static void clickQuestionNotes(String questionName) {
        QuestionScreen questionScreen = new QuestionScreen();
        WaitUtils.waitUntilElementIsClickable(questionScreen.getAnsweredQuestionByText(questionName).getRootElement());
        questionScreen.getAnsweredQuestionByText(questionName).getQuestionNotesIcon().click();
    }

    public static void removeAddedImageFromImageQuestion(String questionName) {
        GeneralQuestion imageQuestion = new QuestionScreen().getGeneralQuestionByText(questionName);
        WaitUtils.click(imageQuestion.getAddedImages().get(0));
        ImageScreenSteps.removeImage();
    }
}
