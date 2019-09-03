package com.cyberiansoft.test.vnext.testcases.r360pro.questionforms;

import com.cyberiansoft.test.dataclasses.QuestionsData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.questionform.QuestionFormSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import com.cyberiansoft.test.vnext.validations.GeneralValidations;
import com.cyberiansoft.test.vnext.validations.QuestionFormValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class BasicQuestionFormTests extends BaseTestCaseTeamEditionRegistration {

    @BeforeClass(description = "Team Monitoring Basic Flow Test")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getQuestionFormBasicCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAnswerQuestionsSeparatley(String rowID,
                                                       String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<QuestionsData> questionsDataList = workOrderData.getQuestionScreenData().getQuestionsData();

        QuestionsData expectedTrafficLightQuestionValuesAfterFirstSwipe = questionsDataList.get(0);
        QuestionsData expectedTrafficLightQuestionValuesAfterSecondSwipe = questionsDataList.get(1);
        QuestionsData expectedTrafficLightQuestionValuesAfterThirdSwipe = questionsDataList.get(2);
        QuestionsData expectedTrafficLightQuestionValuesAfterClear = questionsDataList.get(3);

        QuestionsData expectedLogicalQuestionValuesAfterFirstSwipe = questionsDataList.get(4);
        QuestionsData expectedLogicalQuestionValuesAfterSecondSwipe = questionsDataList.get(5);
        QuestionsData expectedLogicalQuestionValuesAfterClear = questionsDataList.get(6);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.ROZ_QUESTIONS_IT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS);

        QuestionFormSteps.answerGeneralSlideQuestion(expectedTrafficLightQuestionValuesAfterFirstSwipe);
        QuestionFormValidations.validateGeneralQuestionAnswer(expectedTrafficLightQuestionValuesAfterFirstSwipe);

        QuestionFormSteps.answerGeneralSlideQuestion(expectedTrafficLightQuestionValuesAfterSecondSwipe);
        QuestionFormValidations.validateGeneralQuestionAnswer(expectedTrafficLightQuestionValuesAfterSecondSwipe);

        QuestionFormSteps.answerGeneralSlideQuestion(expectedTrafficLightQuestionValuesAfterThirdSwipe);
        QuestionFormValidations.validateGeneralQuestionAnswer(expectedTrafficLightQuestionValuesAfterThirdSwipe);

        QuestionFormSteps.clearQuestion(expectedTrafficLightQuestionValuesAfterClear);
        QuestionFormValidations.validateGeneralQuestionAnswer(expectedTrafficLightQuestionValuesAfterClear);

        QuestionFormSteps.answerGeneralSlideQuestion(expectedLogicalQuestionValuesAfterFirstSwipe);
        QuestionFormValidations.validateGeneralQuestionAnswer(expectedLogicalQuestionValuesAfterFirstSwipe);

        QuestionFormSteps.answerGeneralSlideQuestion(expectedLogicalQuestionValuesAfterSecondSwipe);
        QuestionFormValidations.validateGeneralQuestionAnswer(expectedLogicalQuestionValuesAfterSecondSwipe);

        QuestionFormSteps.clearQuestion(expectedLogicalQuestionValuesAfterClear);
        QuestionFormValidations.validateGeneralQuestionAnswer(expectedLogicalQuestionValuesAfterClear);

        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyValidationOfRequiredQuestionsOnSaveInspection(String rowID,
                                                                    String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<QuestionsData> questionsDataList = workOrderData.getQuestionScreenData().getQuestionsData();

        QuestionsData firstRequiredQuestion = questionsDataList.get(0);
        QuestionsData secondRequiredQuestion = questionsDataList.get(1);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.ROZ_QUESTIONS_IT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS);

        InspectionSteps.trySaveInspection();
        GeneralValidations.errorDialogShouldBePresent(true, "Please answer all necessary questions");
        GeneralSteps.closeErrorDialog();

        QuestionFormSteps.answerGeneralSlideQuestion(firstRequiredQuestion);
        QuestionFormValidations.validateGeneralQuestionAnswer(firstRequiredQuestion);
        InspectionSteps.trySaveInspection();
        GeneralValidations.errorDialogShouldBePresent(true, "Please answer all necessary questions");
        GeneralSteps.closeErrorDialog();

        QuestionFormSteps.answerGeneralSlideQuestion(secondRequiredQuestion);
        QuestionFormValidations.validateGeneralQuestionAnswer(secondRequiredQuestion);

        InspectionSteps.trySaveInspection();
        GeneralValidations.errorDialogShouldBePresent(false, "Please answer all necessary questions");
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAnswerTextQuestionsSeparatley(String rowID,
                                                           String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<QuestionsData> questionsDataList = workOrderData.getQuestionScreenData().getQuestionsData();

        QuestionsData multiAnswerPredefinedQuestion = questionsDataList.get(0);
        QuestionsData singleAnswerPredefinedQuestion = questionsDataList.get(1);
        QuestionsData textQuestion = questionsDataList.get(2);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.ROZ_TEXT_QUESTION);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS);

        QuestionFormSteps.answerGeneralPredefinedQuestion(multiAnswerPredefinedQuestion, true);
        QuestionFormValidations.validateGeneralQuestionAnswer(multiAnswerPredefinedQuestion);


        QuestionFormSteps.answerGeneralPredefinedQuestion(singleAnswerPredefinedQuestion, false);
        QuestionFormValidations.validateGeneralQuestionAnswer(singleAnswerPredefinedQuestion);

        QuestionFormSteps.answerGeneralTextQuestion(textQuestion);
        QuestionFormValidations.validateTextQuestionAnswer(textQuestion);

        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyValidationOfRequiredTextQuestionsOnSaveInspection(String rowID,
                                                                        String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<QuestionsData> questionsDataList = workOrderData.getQuestionScreenData().getQuestionsData();

        QuestionsData firstRequiredQuestion = questionsDataList.get(0);
        QuestionsData secondRequiredQuestion = questionsDataList.get(1);
        QuestionsData thirdRequiredQuestion = questionsDataList.get(2);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.ROZ_TEXT_QUESTION);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS);

        InspectionSteps.trySaveInspection();
        GeneralValidations.errorDialogShouldBePresent(true, "Please answer all necessary questions");
        GeneralSteps.closeErrorDialog();

        QuestionFormSteps.answerGeneralPredefinedQuestion(firstRequiredQuestion, true);

        InspectionSteps.trySaveInspection();
        GeneralValidations.errorDialogShouldBePresent(true, "Please answer all necessary questions");
        GeneralSteps.closeErrorDialog();

        QuestionFormSteps.answerGeneralPredefinedQuestion(secondRequiredQuestion, false);

        InspectionSteps.trySaveInspection();
        GeneralValidations.errorDialogShouldBePresent(true, "Please answer all necessary questions");
        GeneralSteps.closeErrorDialog();

        QuestionFormSteps.answerGeneralTextQuestion(thirdRequiredQuestion);

        InspectionSteps.trySaveInspection();
        GeneralValidations.errorDialogShouldBePresent(false, "Please answer all necessary questions");
        ScreenNavigationSteps.pressBackButton();
    }
}

