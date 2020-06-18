package com.cyberiansoft.test.vnext.testcases.r360pro.questionforms;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.steps.*;
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

public class WorkOrdersQuestionFormTestCases extends BaseTestClass {

    @BeforeClass(description = "Work Orders Question Forms Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getQuestionFormWorkOrdersCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyWorkOrdersQuestionFormsImageQuestion(String rowID, String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        QuestionsData imageQuestion = workOrderData.getQuestionScreenData().getQuestionsData().get(0);

        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.AT_ENCH_QF_WO, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS);
        QuestionFormValidations.verifyQuestionsScreenIsDisplayed();
        QuestionFormValidations.verifyQuestionIsDisplayed(imageQuestion.getQuestionName());
        WorkOrderSteps.trySaveWorkOrder();
        InformationDialogValidations.clickOKAndVerifyMessage("Please answer all necessary questions");
        QuestionFormValidations.verifyQuestionBackGroundIsRed(imageQuestion.getQuestionName());
        QuestionFormSteps.answerImageQuestion(imageQuestion);
        QuestionFormValidations.verifyImageQuestionIsAnswered(imageQuestion, 1);
        QuestionFormSteps.clearSelectedQuestion(imageQuestion.getQuestionName());
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
    public void testVerifyWorkOrdersQuestionFormsHandwritingQuestion(String rowID, String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        SignatureQuestion signatureQuestion = workOrderData.getQuestionScreenData().getQuestionData().getSignatureQuestion();
        QuestionsData questionsData = new QuestionsData();
        questionsData.setQuestionName(signatureQuestion.getQuestionName());

        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.AT_ENCH_QF_WO, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS);
        QuestionFormValidations.verifyQuestionsScreenIsDisplayed();
        QuestionFormValidations.verifyQuestionIsDisplayed(signatureQuestion.getQuestionName());
        WorkOrderSteps.trySaveWorkOrder();
        InformationDialogValidations.clickOKAndVerifyMessage("Please answer all necessary questions");
        QuestionFormValidations.verifyQuestionBackGroundIsRed(signatureQuestion.getQuestionName());
        QuestionFormSteps.answerSignatureQuestion(signatureQuestion);
        QuestionFormValidations.validateQuestionAnswered(questionsData, true);
        QuestionFormValidations.verifyQuestionBackGroundIsGreen(signatureQuestion.getQuestionName());
        QuestionFormSteps.clearSelectedQuestion(questionsData.getQuestionName());
        QuestionFormValidations.validateQuestionAnswered(questionsData, false);
        QuestionFormValidations.verifyQuestionBackGroundIsWhite(signatureQuestion.getQuestionName());
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyWorkOrdersQuestionFormsDateQuestion(String rowID, String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        DatePickerQuestion datePickerQuestion = workOrderData.getQuestionScreenData().getQuestionData().getDatePickerQuestion();
        QuestionsData questionsData = new QuestionsData();
        questionsData.setQuestionName(datePickerQuestion.getQuestionName());
        LocalDate currentDate = LocalDate.now();

        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.AT_ENCH_QF_WO, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS);
        QuestionFormValidations.verifyQuestionsScreenIsDisplayed();
        QuestionFormValidations.verifyQuestionIsDisplayed(datePickerQuestion.getQuestionName());
        WorkOrderSteps.trySaveWorkOrder();
        InformationDialogValidations.clickOKAndVerifyMessage("Please answer all necessary questions");
        QuestionFormValidations.verifyQuestionBackGroundIsRed(datePickerQuestion.getQuestionName());
        QuestionFormSteps.answerDateQuestion(datePickerQuestion, currentDate.getMonthValue(), currentDate.getDayOfMonth(), currentDate.getYear());
        QuestionFormValidations.validateQuestionAnswered(questionsData, true);
        QuestionFormValidations.verifyQuestionBackGroundIsGreen(datePickerQuestion.getQuestionName());
        QuestionFormValidations.validateGeneralQuestionHasAnswer(datePickerQuestion.getQuestionName(), currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        QuestionFormSteps.clearSelectedQuestion(questionsData.getQuestionName());
        QuestionFormValidations.validateQuestionAnswered(questionsData, false);
        QuestionFormValidations.verifyQuestionBackGroundIsWhite(datePickerQuestion.getQuestionName());
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyWorkOrdersQuestionFormsTimeQuestion(String rowID, String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        TimePickerQuestion timePickerQuestion = workOrderData.getQuestionScreenData().getQuestionData().getTimePickerQuestion();
        QuestionsData questionsData = new QuestionsData();
        questionsData.setQuestionName(timePickerQuestion.getQuestionName());
        LocalTime currentTime = LocalTime.now();

        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.AT_ENCH_QF_WO, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS);
        QuestionFormValidations.verifyQuestionsScreenIsDisplayed();
        QuestionFormValidations.verifyQuestionIsDisplayed(timePickerQuestion.getQuestionName());
        WorkOrderSteps.trySaveWorkOrder();
        InformationDialogValidations.clickOKAndVerifyMessage("Please answer all necessary questions");
        QuestionFormValidations.verifyQuestionBackGroundIsRed(timePickerQuestion.getQuestionName());
        QuestionFormSteps.answerTimeQuestion(timePickerQuestion, currentTime.getHour(), currentTime.getMinute(), currentTime.getSecond());
        QuestionFormValidations.validateQuestionAnswered(questionsData, true);
        QuestionFormValidations.verifyQuestionBackGroundIsGreen(timePickerQuestion.getQuestionName());
        QuestionFormSteps.clearSelectedQuestion(questionsData.getQuestionName());
        QuestionFormValidations.validateQuestionAnswered(questionsData, false);
        QuestionFormValidations.verifyQuestionBackGroundIsWhite(timePickerQuestion.getQuestionName());
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyWorkOrdersQuestionFormsLogicalQuestion(String rowID, String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        LogicalQuestionData logicalQuestionData = workOrderData.getQuestionScreenData().getQuestionData().getLogicalQuestionData();

        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.OLROM, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS);
        QuestionFormValidations.verifyQuestionsScreenIsDisplayed();
        QuestionFormValidations.verifyQuestionIsDisplayed(logicalQuestionData.getQuestionName());
        QuestionFormSteps.answerLogicalQuestion(logicalQuestionData);
        QuestionFormSteps.clearSelectedQuestion(logicalQuestionData.getQuestionName());
        QuestionFormSteps.answerLogicalQuestion(logicalQuestionData);

        WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();
    }
}

