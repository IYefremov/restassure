package com.cyberiansoft.test.vnext.testcases.r360pro.questionforms;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.steps.HomeScreenSteps;
import com.cyberiansoft.test.vnext.steps.InspectionSteps;
import com.cyberiansoft.test.vnext.steps.ScreenNavigationSteps;
import com.cyberiansoft.test.vnext.steps.WizardScreenSteps;
import com.cyberiansoft.test.vnext.steps.questionform.QuestionFormSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.InformationDialogValidations;
import com.cyberiansoft.test.vnext.validations.questionforms.QuestionFormValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class InspectionsQuestionFormTestCases extends BaseTestClass {

    @BeforeClass(description = "Inspections Question Forms Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getQuestionFormInspectionsCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyInspectionsQuestionFormsImageQuestion(String rowID, String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        QuestionsData imageQuestion = inspectionData.getQuestionScreenData().getQuestionsData().get(0);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.AT_QF_INSPECTIONS);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS);
        QuestionFormValidations.verifyQuestionsScreenIsDisplayed();
        QuestionFormValidations.verifyQuestionIsDisplayed(imageQuestion.getQuestionName());
        InspectionSteps.trySaveInspectionsFinalViaMenu();
        InformationDialogValidations.clickOKAndVerifyMessage("Please answer all necessary questions");
        QuestionFormValidations.verifyQuestionBackGroundIsRed(imageQuestion.getQuestionName());
        QuestionFormSteps.answerImageQuestion(imageQuestion);
        QuestionFormValidations.verifyImageQuestionIsAnswered(imageQuestion, 1);
        QuestionFormSteps.clearSelectedQuestion(imageQuestion);
        QuestionFormValidations.verifyImageQuestionIsNotAnswered(imageQuestion);
        QuestionFormValidations.verifyQuestionBackGroundIsWhite(imageQuestion.getQuestionName());
        for (int i = 0; i < 4; i++) {
            BaseUtils.waitABit(1000);
            QuestionFormSteps.answerImageQuestion(imageQuestion);

        }
        QuestionFormValidations.verifyImageQuestionIsAnswered(imageQuestion, 4);
        QuestionFormSteps.removeAddedImageFromImageQuestion(imageQuestion.getQuestionName());
        QuestionFormValidations.verifyImageQuestionIsAnswered(imageQuestion, 3);
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyInspectionsQuestionFormsHandwritingQuestion(String rowID, String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        SignatureQuestion signatureQuestion = inspectionData.getQuestionScreenData().getQuestionData().getSignatureQuestion();
        QuestionsData questionsData = new QuestionsData();
        questionsData.setQuestionName(signatureQuestion.getQuestionName());

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.AT_QF_INSPECTIONS);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS);
        QuestionFormValidations.verifyQuestionsScreenIsDisplayed();
        QuestionFormValidations.verifyQuestionIsDisplayed(signatureQuestion.getQuestionName());
        InspectionSteps.trySaveInspectionsFinalViaMenu();
        InformationDialogValidations.clickOKAndVerifyMessage("Please answer all necessary questions");
        QuestionFormValidations.verifyQuestionBackGroundIsRed(signatureQuestion.getQuestionName());
        QuestionFormSteps.answerSignatureQuestion(signatureQuestion);
        QuestionFormValidations.validateQuestionAnswered(questionsData, true);
        QuestionFormValidations.verifyQuestionBackGroundIsGreen(signatureQuestion.getQuestionName());
        QuestionFormSteps.clearSelectedQuestion(questionsData);
        QuestionFormValidations.validateQuestionAnswered(questionsData, false);
        QuestionFormValidations.verifyQuestionBackGroundIsWhite(signatureQuestion.getQuestionName());
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyInspectionsQuestionFormsDateQuestion(String rowID, String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        DatePickerQuestion datePickerQuestion = inspectionData.getQuestionScreenData().getQuestionData().getDatePickerQuestion();
        QuestionsData questionsData = new QuestionsData();
        questionsData.setQuestionName(datePickerQuestion.getQuestionName());
        LocalDate currentDate = LocalDate.now();

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.AT_QF_INSPECTIONS);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS);
        QuestionFormValidations.verifyQuestionsScreenIsDisplayed();
        QuestionFormValidations.verifyQuestionIsDisplayed(datePickerQuestion.getQuestionName());
        InspectionSteps.trySaveInspectionsFinalViaMenu();
        InformationDialogValidations.clickOKAndVerifyMessage("Please answer all necessary questions");
        QuestionFormValidations.verifyQuestionBackGroundIsRed(datePickerQuestion.getQuestionName());
        QuestionFormSteps.answerDateQuestion(datePickerQuestion, currentDate.getMonthValue(), currentDate.getDayOfMonth(), currentDate.getYear());
        QuestionFormValidations.validateQuestionAnswered(questionsData, true);
        QuestionFormValidations.verifyQuestionBackGroundIsGreen(datePickerQuestion.getQuestionName());
        QuestionFormValidations.validateGeneralQuestionHasAnswer(datePickerQuestion.getQuestionName(), currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        QuestionFormSteps.clearSelectedQuestion(questionsData);
        QuestionFormValidations.validateQuestionAnswered(questionsData, false);
        QuestionFormValidations.verifyQuestionBackGroundIsWhite(datePickerQuestion.getQuestionName());
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyInspectionsQuestionFormsTimeQuestion(String rowID, String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        TimePickerQuestion timePickerQuestion = inspectionData.getQuestionScreenData().getQuestionData().getTimePickerQuestion();
        QuestionsData questionsData = new QuestionsData();
        questionsData.setQuestionName(timePickerQuestion.getQuestionName());
        LocalTime currentTime = LocalTime.now();

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.AT_QF_INSPECTIONS);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS);
        QuestionFormValidations.verifyQuestionsScreenIsDisplayed();
        QuestionFormValidations.verifyQuestionIsDisplayed(timePickerQuestion.getQuestionName());
        InspectionSteps.trySaveInspectionsFinalViaMenu();
        InformationDialogValidations.clickOKAndVerifyMessage("Please answer all necessary questions");
        QuestionFormValidations.verifyQuestionBackGroundIsRed(timePickerQuestion.getQuestionName());
        QuestionFormSteps.answerTimeQuestion(timePickerQuestion, currentTime.getHour(), currentTime.getMinute(), currentTime.getSecond());
        QuestionFormValidations.validateQuestionAnswered(questionsData, true);
        QuestionFormValidations.verifyQuestionBackGroundIsGreen(timePickerQuestion.getQuestionName());
        QuestionFormSteps.clearSelectedQuestion(questionsData);
        QuestionFormValidations.validateQuestionAnswered(questionsData, false);
        QuestionFormValidations.verifyQuestionBackGroundIsWhite(timePickerQuestion.getQuestionName());
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }
}

