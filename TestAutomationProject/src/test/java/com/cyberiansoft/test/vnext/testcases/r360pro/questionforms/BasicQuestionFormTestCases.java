package com.cyberiansoft.test.vnext.testcases.r360pro.questionforms;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.QuestionsData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataclasses.partservice.PartServiceData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.questionform.QuestionFormSteps;
import com.cyberiansoft.test.vnext.steps.services.*;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.GeneralValidations;
import com.cyberiansoft.test.vnext.validations.PartServiceListValidations;
import com.cyberiansoft.test.vnext.validations.QuestionServiceListValidations;
import com.cyberiansoft.test.vnext.validations.ServiceDetailsValidations;
import com.cyberiansoft.test.vnext.validations.questionforms.QuestionFormValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class BasicQuestionFormTestCases extends BaseTestClass {

    @BeforeClass(description = "Team Basic Question Form Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getQuestionFormBasicCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanAnswerQuestionsSeparatley(String rowID,
                                                           String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        List<QuestionsData> questionsDataList = inspectionData.getQuestionScreenData().getQuestionsData();

        QuestionsData expectedTrafficLightQuestionValuesAfterFirstSwipe = questionsDataList.get(0);

        QuestionsData expectedLogicalQuestionValuesAfterFirstSwipe = questionsDataList.get(4);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.ROZ_QUESTIONS_IT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS);

        QuestionFormSteps.answerGeneralQuestion(expectedTrafficLightQuestionValuesAfterFirstSwipe);
        QuestionFormValidations.validateGeneralQuestionAnswer(expectedTrafficLightQuestionValuesAfterFirstSwipe);

        QuestionFormSteps.answerGeneralQuestion(expectedLogicalQuestionValuesAfterFirstSwipe);
        QuestionFormValidations.validateGeneralQuestionAnswer(expectedLogicalQuestionValuesAfterFirstSwipe);

        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyValidationOfRequiredQuestionsOnSaveInspection(String rowID,
                                                                        String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        List<QuestionsData> questionsDataList = inspectionData.getQuestionScreenData().getQuestionsData();

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
        QuestionFormSteps.answerImageQuestion(questionsDataList.get(2));
        QuestionFormValidations.validateGeneralQuestionAnswer(secondRequiredQuestion);

        InspectionSteps.trySaveInspection();
        GeneralValidations.errorDialogShouldBePresent(false, "Please answer all necessary questions");
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanAnswerTextQuestionsSeparatley(String rowID,
                                                               String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        List<QuestionsData> questionsDataList = inspectionData.getQuestionScreenData().getQuestionsData();

        QuestionsData multiAnswerPredefinedQuestion = questionsDataList.get(0);
        QuestionsData singleAnswerPredefinedQuestion = questionsDataList.get(1);
        QuestionsData textQuestion = questionsDataList.get(2);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.ROZSTALNOY_TEXT_QUESTIONS_IT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS);

        QuestionFormSteps.answerGeneralPredefinedQuestion(multiAnswerPredefinedQuestion, true);
        QuestionFormValidations.validateGeneralQuestionAnswer(multiAnswerPredefinedQuestion);
        QuestionFormSteps.clearSelectedQuestion(multiAnswerPredefinedQuestion.getQuestionName());
        QuestionFormValidations.validateQuestionAnswered(multiAnswerPredefinedQuestion, false);

        QuestionFormSteps.answerGeneralPredefinedQuestion(singleAnswerPredefinedQuestion, false);
        QuestionFormValidations.validateGeneralQuestionAnswer(singleAnswerPredefinedQuestion);
        QuestionFormSteps.clearSelectedQuestion(singleAnswerPredefinedQuestion.getQuestionName());
        QuestionFormValidations.validateQuestionAnswered(singleAnswerPredefinedQuestion, false);

        QuestionFormSteps.answerGeneralTextQuestion(textQuestion);
        QuestionFormValidations.validateTextQuestionAnswer(textQuestion);
        QuestionFormSteps.clearSelectedQuestion(textQuestion.getQuestionName());
        QuestionFormValidations.validateQuestionAnswered(textQuestion, false);

        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyValidationForRequiredQuestionsGoingForwardBackFromQuestionSection(String rowID,
                                                                                            String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        List<QuestionsData> questionsDataList = inspectionData.getQuestionScreenData().getQuestionsData();

        QuestionsData multiAnswerPredefinedQuestion = questionsDataList.get(0);
        QuestionsData singleAnswerPredefinedQuestion = questionsDataList.get(1);
        QuestionsData textQuestion = questionsDataList.get(2);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.ROZSTALNOY_TEXT_QUESTIONS_IT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS);
        InspectionSteps.trySaveInspection();
        GeneralValidations.errorDialogShouldBePresent(true, "Please answer all necessary questions");
        GeneralSteps.acceptDialog();

        QuestionFormSteps.answerGeneralPredefinedQuestion(multiAnswerPredefinedQuestion, true);
        QuestionFormValidations.validateGeneralQuestionAnswer(multiAnswerPredefinedQuestion);
        InspectionSteps.trySaveInspection();
        GeneralValidations.errorDialogShouldBePresent(true, "Please answer all necessary questions");
        GeneralSteps.acceptDialog();

        QuestionFormSteps.answerGeneralPredefinedQuestion(singleAnswerPredefinedQuestion, false);
        QuestionFormValidations.validateGeneralQuestionAnswer(singleAnswerPredefinedQuestion);
        InspectionSteps.trySaveInspection();
        GeneralValidations.errorDialogShouldBePresent(true, "Please answer all necessary questions");
        GeneralSteps.acceptDialog();

        QuestionFormSteps.answerGeneralTextQuestion(textQuestion);
        QuestionFormValidations.validateTextQuestionAnswer(textQuestion);

        final String inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS);
        questionsDataList.forEach(questionsData -> {
            QuestionFormValidations.validateQuestionAnswered(questionsData, true);
        });
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyValidationOfRequiredTextQuestionsOnSaveInspection(String rowID,
                                                                            String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        List<QuestionsData> questionsDataList = inspectionData.getQuestionScreenData().getQuestionsData();

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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyQuestionsHasDefaultAnswers(String rowID,
                                                     String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        List<QuestionsData> questionsDataList = inspectionData.getQuestionScreenData().getQuestionsData();

        QuestionsData questionWithPredefinedAnswer = questionsDataList.get(0);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.WITH_QUESTIONS_NOT_REQUIRED);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS);

        QuestionFormValidations.validateGeneralQuestionAnswer(questionWithPredefinedAnswer);

        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyQFShownForServiceAndEntityLevels(String rowID,
                                                           String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        List<QuestionsData> questionsDataList = inspectionData.getQuestionScreenData().getQuestionsData();

        QuestionsData questionWithPredefinedAnswer = questionsDataList.get(0);
        ServiceData serviceData = inspectionData.getServiceData();

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.WITH_QUESTIONS_NOT_REQUIRED);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.openServiceDetails(serviceData);
        ServiceDetailsScreenSteps.openQuestionForm("QS with def answer");

        QuestionFormValidations.validateGeneralQuestionAnswer(questionWithPredefinedAnswer);

        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }


    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyPartServiceIsAddedAndLinkedWithLaborAfterQuestionIsAnswered(String rowID,
                                                                                  String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<QuestionsData> questionsDataList = workOrderData.getQuestionScreenData().getQuestionsData();
        QuestionsData serviceQuestion = questionsDataList.get(0);
        List<ServiceData> expectedServices = workOrderData.getServicesList();
        ServiceData expectedNeedToSetupService = expectedServices.get(0);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.WITH_QUESTIONS_ANSWER_SERVICES);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS);

        QuestionFormSteps.answerGeneralQuestion(serviceQuestion);
        QuestionFormValidations.validateGeneralQuestionAnswer(serviceQuestion);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES, 1);
        SelectedServicesScreenSteps.openServiceDetails(expectedNeedToSetupService.getServiceName());
        ServiceDetailsValidations.verifyLaborServicesButtonPresent(false);
        ServiceDetailsScreenSteps.setPartInfo();
        ServiceDetailsValidations.verifyLaborServicesButtonPresent(true);
        ScreenNavigationSteps.acceptScreen();
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyPartServiceIsAddedAndNotLinkedWithLaborAfterQuestionIsAnswered(String rowID,
                                                                                         String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        List<QuestionsData> questionsDataList = inspectionData.getQuestionScreenData().getQuestionsData();
        QuestionsData serviceQuestion = questionsDataList.get(0);
        List<ServiceData> expectedServices = inspectionData.getServicesList();
        ServiceData firstExpectedNeedToSetupService = expectedServices.get(0);
        ServiceData secondExpectedNeedToSetupService = expectedServices.get(1);
        ServiceData expectedSelectedService = expectedServices.get(2);
        PartServiceData partServiceData = inspectionData.getPartServiceDataList().get(0);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.WITH_QUESTIONS_ANSWER_SERVICES);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS);

        QuestionFormSteps.answerGeneralQuestion(serviceQuestion);
        QuestionFormValidations.validateGeneralQuestionAnswer(serviceQuestion);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES, 1);

        QuestionServiceListSteps.switchToNeedToSetupServiceView();
        QuestionServiceListValidations.validateServicePresent(firstExpectedNeedToSetupService.getServiceName());
        QuestionServiceListValidations.validateServicePresent(secondExpectedNeedToSetupService.getServiceName());
        QuestionServiceListSteps.switchToSelectedServiceView();
        QuestionServiceListValidations.validateServicePresent(expectedSelectedService.getServiceName());
        QuestionServiceListSteps.openServiceDetails(expectedSelectedService.getServiceName());
        ServiceDetailsValidations.verifyPartsServicePresent(false);
        LaborServiceSteps.addPartService();
        PartServiceSteps.switchToSelectedView();
        PartServiceListValidations.validateNoServicePresent();
        ScreenNavigationSteps.pressBackButton();
        LaborServiceSteps.confirmServiceDetails();
        QuestionServiceListSteps.switchToNeedToSetupServiceView();
        QuestionServiceListSteps.openServiceDetails(firstExpectedNeedToSetupService.getServiceName());
        PartServiceSteps.selectPartServiceDetailsWithOpenedDetails(partServiceData);
        PartServiceSteps.confirmPartInfo();
        PartServiceSteps.confirmPartInfo();
        QuestionServiceListSteps.switchToSelectedServiceView();
        QuestionServiceListValidations.validateServicePresent(expectedSelectedService.getServiceName());
        SelectedServicesScreenSteps.openServiceDetails(expectedSelectedService.getServiceName());
        ServiceDetailsValidations.verifyPartsServicePresent(false);
        LaborServiceSteps.addPartService();
        PartServiceSteps.switchToSelectedView();
        PartServiceListValidations.validateNoServicePresent();
        ScreenNavigationSteps.pressBackButton();
        LaborServiceSteps.confirmServiceDetails();

        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatItIsPossibleToSaveEntityWithoutAnswerToNotRequiredQuestion(String rowID,
                                                                                         String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.WITH_QUESTIONS_NOT_REQUIRED);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServices(inspectionData.getServicesList());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS);

        InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatItIsPossibleToSetDefaultAnswerForQuestion(String rowID,
                                                                        String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.WITH_QUESTIONS_NOT_REQUIRED);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServices(inspectionData.getServicesList());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS);
        QuestionFormValidations.validateQuestionAnswered(inspectionData.getQuestionScreenData().getQuestionsData().get(0), false);
        QuestionFormValidations.validateQuestionAnswered(inspectionData.getQuestionScreenData().getQuestionsData().get(1), true);

        InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatWhenPartServiceIsAddedWithLaborAsAnswerServicesLaborIsLinkedToPartAfterConfigurationPart(String rowID,
                                                                                                                       String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        List<QuestionsData> questionsDataList = inspectionData.getQuestionScreenData().getQuestionsData();
        QuestionsData serviceQuestion = questionsDataList.get(0);
        List<ServiceData> expectedServices = inspectionData.getServicesList();
        ServiceData expectedSelectedService = expectedServices.get(0);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.WITH_QUESTIONS_ANSWER_SERVICES);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS);

        QuestionFormSteps.answerGeneralQuestion(serviceQuestion);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES, 1);

        QuestionServiceListSteps.switchToSelectedServiceView();
        inspectionData.getServicesList().forEach(serviceData -> {
            QuestionServiceListValidations.validateServicePresent(serviceData.getServiceName());
        });
        SelectedServicesScreenSteps.openServiceDetails(expectedSelectedService.getServiceName());
        ServiceDetailsValidations.verifyPartsServicePresent(true);
        ServiceDetailsValidations.verifyLaborServicesButtonPresent(false);
        ServiceDetailsScreenSteps.openPartServiceDetails();
        PartServiceSteps.confirmPartInfo();
        ServiceDetailsValidations.verifyPartsServicePresent(true);
        ServiceDetailsValidations.verifyLaborServicesButtonPresent(true);

        ServiceDetailsScreenSteps.closeServiceDetailsScreen();

        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }
}

