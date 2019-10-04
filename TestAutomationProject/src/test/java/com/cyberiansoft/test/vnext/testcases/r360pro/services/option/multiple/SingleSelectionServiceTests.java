package com.cyberiansoft.test.vnext.testcases.r360pro.services.option.multiple;

import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataclasses.partservice.PartServiceData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.questionform.QuestionFormSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.QuestionServiceListSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import com.cyberiansoft.test.vnext.validations.ListServicesValidations;
import com.cyberiansoft.test.vnext.validations.QuestionServiceListValidations;
import com.cyberiansoft.test.vnext.validations.VisualScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SingleSelectionServiceTests extends BaseTestCaseTeamEditionRegistration {
    @BeforeClass(description = "Team Monitoring Basic Flow Test")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getServiceMultipleOptionDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCantAddSingleServiceMultipleTimesCreatingWO(String rowID,
                                                                String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        ServiceData moneyService = workOrderData.getServicesList().get(0);
        ServiceData laborService = workOrderData.getServicesList().get(1);
        PartServiceData partService = workOrderData.getPartServiceDataList().get(0);
        MatrixServiceData matrixService = workOrderData.getMatrixServiceData();

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.ROZ_WITHOUT_QUESTIONS);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(moneyService);
        AvailableServicesScreenSteps.selectService(moneyService);
        ListServicesValidations.validateMessagePresent(true, "Service can be added once");

        AvailableServicesScreenSteps.selectService(laborService);
        AvailableServicesScreenSteps.selectService(laborService);
        ListServicesValidations.validateMessagePresent(true, "Service can be added once");

        SearchSteps.textSearch(partService.getServiceName());
        PartServiceSteps.selectPartService(partService);
        PartServiceSteps.confirmPartInfo();
        AvailableServicesScreenSteps.selectService(partService.getServiceName());
        ListServicesValidations.validateMessagePresent(true, "Service can be added once");

        SearchSteps.textSearch(matrixService.getMatrixServiceName());
        MatrixServiceSteps.selectMatrixService(matrixService);
        ScreenNavigationSteps.pressBackButton();
        MatrixServiceSteps.acceptDetailsScreen();
        AvailableServicesScreenSteps.selectService(matrixService.getMatrixServiceName());
        ListServicesValidations.validateMessagePresent(true, "Service can be added once");
        ListServicesValidations.validateAvailableServiceCount(matrixService.getMatrixServiceName(), 1);

        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCantAddSingleServiceMultipleTimesEditingWO(String rowID,
                                                               String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        ServiceData moneyService = workOrderData.getServicesList().get(0);
        ServiceData laborService = workOrderData.getServicesList().get(1);
        PartServiceData partService = workOrderData.getPartServiceDataList().get(0);
        MatrixServiceData matrixService = workOrderData.getMatrixServiceData();

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.ROZ_WITHOUT_QUESTIONS);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(moneyService);
        AvailableServicesScreenSteps.selectService(laborService);
        SearchSteps.textSearch(partService.getServiceName());
        PartServiceSteps.selectPartService(partService);
        PartServiceSteps.confirmPartInfo();
        SearchSteps.textSearch(matrixService.getMatrixServiceName());
        MatrixServiceSteps.selectMatrixService(matrixService);
        ScreenNavigationSteps.pressBackButton();
        MatrixServiceSteps.acceptDetailsScreen();
        String inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionToEdit(inspectionId);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

        AvailableServicesScreenSteps.selectService(moneyService);
        ListServicesValidations.validateMessagePresent(true, "Service can be added once");

        AvailableServicesScreenSteps.selectService(laborService);
        ListServicesValidations.validateMessagePresent(true, "Service can be added once");

        AvailableServicesScreenSteps.selectService(partService.getServiceName());
        ListServicesValidations.validateMessagePresent(true, "Service can be added once");

        AvailableServicesScreenSteps.selectService(matrixService.getMatrixServiceName());
        ListServicesValidations.validateMessagePresent(true, "Service can be added once");
        ListServicesValidations.validateAvailableServiceCount(matrixService.getMatrixServiceName(), 1);

        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCANAddSingleServiceMultipleTimesCreatingWO(String rowID,
                                                               String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        ServiceData moneyService = workOrderData.getServicesList().get(0);
        ServiceData laborService = workOrderData.getServicesList().get(1);
        PartServiceData partService = workOrderData.getPartServiceDataList().get(0);
        MatrixServiceData matrixService = workOrderData.getMatrixServiceData();

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.ROZ_WITHOUT_QUESTIONS);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

        AvailableServicesScreenSteps.selectService(moneyService);
        AvailableServicesScreenSteps.selectService(moneyService);
        ListServicesValidations.validateAvailableServiceCount(moneyService.getServiceName(), 2);

        AvailableServicesScreenSteps.selectService(laborService);
        AvailableServicesScreenSteps.selectService(laborService);
        ListServicesValidations.validateAvailableServiceCount(laborService.getServiceName(), 2);

        SearchSteps.textSearch(partService.getServiceName());
        PartServiceSteps.selectPartService(partService);
        PartServiceSteps.confirmPartInfo();
        PartServiceSteps.selectPartService(partService);
        PartServiceSteps.confirmPartInfo();
        ListServicesValidations.validateAvailableServiceCount(partService.getServiceName(), 2);

        SearchSteps.textSearch(matrixService.getMatrixServiceName());
        MatrixServiceSteps.selectMatrixService(matrixService);
        ScreenNavigationSteps.pressBackButton();
        MatrixServiceSteps.acceptDetailsScreen();
        MatrixServiceSteps.selectMatrixService(matrixService);
        ScreenNavigationSteps.pressBackButton();
        MatrixServiceSteps.acceptDetailsScreen();
        ListServicesValidations.validateAvailableServiceCount(matrixService.getMatrixServiceName(), 2);

        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCANAddSingleServiceMultipleTimesEditingWO(String rowID,
                                                              String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        ServiceData moneyService = workOrderData.getServicesList().get(0);
        ServiceData laborService = workOrderData.getServicesList().get(1);
        PartServiceData partService = workOrderData.getPartServiceDataList().get(0);
        MatrixServiceData matrixService = workOrderData.getMatrixServiceData();

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.ROZ_WITHOUT_QUESTIONS);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(moneyService);
        AvailableServicesScreenSteps.selectService(laborService);
        SearchSteps.textSearch(partService.getServiceName());
        PartServiceSteps.selectPartService(partService);
        PartServiceSteps.confirmPartInfo();
        SearchSteps.textSearch(matrixService.getMatrixServiceName());
        MatrixServiceSteps.selectMatrixService(matrixService);
        ScreenNavigationSteps.pressBackButton();
        MatrixServiceSteps.acceptDetailsScreen();
        String inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionToEdit(inspectionId);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

        AvailableServicesScreenSteps.selectService(moneyService);
        ListServicesValidations.validateAvailableServiceCount(moneyService.getServiceName(), 2);

        AvailableServicesScreenSteps.selectService(laborService);
        ListServicesValidations.validateAvailableServiceCount(laborService.getServiceName(), 2);

        SearchSteps.textSearch(partService.getServiceName());
        PartServiceSteps.selectPartService(partService);
        PartServiceSteps.confirmPartInfo();
        ListServicesValidations.validateAvailableServiceCount(partService.getServiceName(), 2);

        SearchSteps.textSearch(matrixService.getMatrixServiceName());
        MatrixServiceSteps.selectMatrixService(matrixService);
        ScreenNavigationSteps.pressBackButton();
        MatrixServiceSteps.acceptDetailsScreen();
        ListServicesValidations.validateAvailableServiceCount(matrixService.getMatrixServiceName(), 2);

        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCantAddSingleServiceMultipleTimesCreatingWOFromVisualForm(String rowID,
                                                                              String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        DamageData moneyServiceDamageData = workOrderData.getDamagesData().get(0);
        DamageData laborServiceDamageDate = workOrderData.getDamagesData().get(1);
        DamageData partServiceDamageDate = workOrderData.getDamagesData().get(2);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.ROZSTALNOY_IT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.VISUAL);
        VisualScreenSteps.selectMoneyServiceDamage(moneyServiceDamageData);
        VisualScreenSteps.addServiceToPicture();
        VisualScreenSteps.addServiceToPicture();
        ListServicesValidations.validateMessagePresent(true, "Service can be added once");

        VisualScreenSteps.selectMoneyServiceDamage(laborServiceDamageDate);
        VisualScreenSteps.addServiceToPicture();
        VisualScreenSteps.addServiceToPicture();
        ListServicesValidations.validateMessagePresent(true, "Service can be added once");

        VisualScreenSteps.selectPartServiceDamage(partServiceDamageDate);
        VisualScreenSteps.addServiceToPicture();
        PartServiceSteps.selectpartServiceDetails(partServiceDamageDate.getPartServiceData());
        PartServiceSteps.confirmPartInfo();
        VisualScreenSteps.addServiceToPicture();
        ListServicesValidations.validateMessagePresent(true, "Service can be added once");

        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCantAddSingleServiceMultipleTimesEditingWOFromVisualForm(String rowID,
                                                                             String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        DamageData moneyServiceDamageData = workOrderData.getDamagesData().get(0);
        DamageData laborServiceDamageDate = workOrderData.getDamagesData().get(1);
        DamageData partServiceDamageDate = workOrderData.getDamagesData().get(2);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.ROZSTALNOY_IT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.VISUAL);
        VisualScreenSteps.selectMoneyServiceDamage(moneyServiceDamageData);
        VisualScreenSteps.addServiceToPicture();
        VisualScreenSteps.selectMoneyServiceDamage(laborServiceDamageDate);
        VisualScreenSteps.addServiceToPicture();
        VisualScreenSteps.selectPartServiceDamage(partServiceDamageDate);
        VisualScreenSteps.addServiceToPicture();
        String inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionToEdit(inspectionId);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.VISUAL);

        VisualScreenSteps.selectMoneyServiceDamage(moneyServiceDamageData);
        VisualScreenSteps.addServiceToPicture();
        ListServicesValidations.validateMessagePresent(true, "Service can be added once");

        VisualScreenSteps.selectMoneyServiceDamage(laborServiceDamageDate);
        VisualScreenSteps.addServiceToPicture();
        ListServicesValidations.validateMessagePresent(true, "Service can be added once");

        VisualScreenSteps.selectPartServiceDamage(partServiceDamageDate);
        VisualScreenSteps.addServiceToPicture();
        ListServicesValidations.validateMessagePresent(true, "Service can be added once");

        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCANAddSingleServiceMultipleTimesCreatingWOFromVisualForm(String rowID,
                                                                             String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        DamageData moneyServiceDamageData = workOrderData.getDamagesData().get(0);
        DamageData laborServiceDamageDate = workOrderData.getDamagesData().get(1);
        DamageData partServiceDamageDate = workOrderData.getDamagesData().get(2);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.ROZSTALNOY_IT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.VISUAL);
        VisualScreenSteps.selectMoneyServiceDamage(moneyServiceDamageData);
        VisualScreenSteps.addServiceToPicture();
        VisualScreenSteps.addServiceToPicture();
        VisualScreenValidations.numberOfMarksShouldBeEqualTo(2);
        VisualScreenSteps.clearAllMarks();

        VisualScreenSteps.selectMoneyServiceDamage(laborServiceDamageDate);
        VisualScreenSteps.addServiceToPicture();
        VisualScreenSteps.addServiceToPicture();
        VisualScreenValidations.numberOfMarksShouldBeEqualTo(2);
        VisualScreenSteps.clearAllMarks();

        VisualScreenSteps.selectPartServiceDamage(partServiceDamageDate);
        VisualScreenSteps.addServiceToPicture();
        PartServiceSteps.selectpartServiceDetails(partServiceDamageDate.getPartServiceData());
        PartServiceSteps.confirmPartInfo();
        VisualScreenSteps.addServiceToPicture();
        PartServiceSteps.selectpartServiceDetails(partServiceDamageDate.getPartServiceData());
        PartServiceSteps.confirmPartInfo();
        VisualScreenValidations.numberOfMarksShouldBeEqualTo(2);
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCANAddSingleServiceMultipleTimesEditingWOFromVisualForm(String rowID,
                                                                            String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        DamageData moneyServiceDamageData = workOrderData.getDamagesData().get(0);
        DamageData laborServiceDamageDate = workOrderData.getDamagesData().get(1);
        DamageData partServiceDamageDate = workOrderData.getDamagesData().get(2);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.ROZSTALNOY_IT);
        String inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionToEdit(inspectionId);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.VISUAL);

        VisualScreenSteps.selectMoneyServiceDamage(moneyServiceDamageData);
        VisualScreenSteps.addServiceToPicture();
        VisualScreenValidations.numberOfMarksShouldBeEqualTo(1);
        VisualScreenSteps.clearAllMarks();

        VisualScreenSteps.selectMoneyServiceDamage(laborServiceDamageDate);
        VisualScreenSteps.addServiceToPicture();
        VisualScreenValidations.numberOfMarksShouldBeEqualTo(1);
        VisualScreenSteps.clearAllMarks();

        VisualScreenSteps.selectPartServiceDamage(partServiceDamageDate);
        VisualScreenSteps.addServiceToPicture();
        PartServiceSteps.selectpartServiceDetails(partServiceDamageDate.getPartServiceData());
        PartServiceSteps.confirmPartInfo();
        VisualScreenValidations.numberOfMarksShouldBeEqualTo(1);
        InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanAddServicesUsingQuestionForm(String rowID,
                                                    String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        ServiceData singleMoneyService = workOrderData.getServicesList().get(0);
        ServiceData singleLaborService = workOrderData.getServicesList().get(1);
        ServiceData multipleMoneyService = workOrderData.getServicesList().get(2);
        ServiceData multipleLaborService = workOrderData.getServicesList().get(3);
        PartServiceData singlePartService = workOrderData.getPartServiceDataList().get(0);
        PartServiceData multiplePartService = workOrderData.getPartServiceDataList().get(1);
        MatrixServiceData singleMatrixService = workOrderData.getMatrixServiceDataList().get(0);
        MatrixServiceData multipleMatrixService = workOrderData.getMatrixServiceDataList().get(1);

        QuestionsData generateMoneyService = workOrderData.getQuestionScreenData().getQuestionsData().get(0);
        QuestionsData generateLaborService = workOrderData.getQuestionScreenData().getQuestionsData().get(1);
        QuestionsData generateMatrixService = workOrderData.getQuestionScreenData().getQuestionsData().get(2);
        QuestionsData generatePartService = workOrderData.getQuestionScreenData().getQuestionsData().get(3);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.ROZSTALNOY_IT);
        String inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionToEdit(inspectionId);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

        AvailableServicesScreenSteps.selectService(singleMoneyService);
        AvailableServicesScreenSteps.selectService(singleLaborService);
        AvailableServicesScreenSteps.selectService(multipleMoneyService);
        AvailableServicesScreenSteps.selectService(multipleLaborService);

        SearchSteps.textSearch(singlePartService.getServiceName());
        PartServiceSteps.selectPartService(singlePartService);
        PartServiceSteps.confirmPartInfo();
        SearchSteps.textSearch(multiplePartService.getServiceName());
        PartServiceSteps.selectPartService(multiplePartService);
        PartServiceSteps.confirmPartInfo();

        SearchSteps.textSearch(singleMatrixService.getMatrixServiceName());
        MatrixServiceSteps.selectMatrixService(singleMatrixService);
        ScreenNavigationSteps.pressBackButton();
        MatrixServiceSteps.acceptDetailsScreen();
        SearchSteps.textSearch(multipleMatrixService.getMatrixServiceName());
        MatrixServiceSteps.selectMatrixService(multipleMatrixService);
        ScreenNavigationSteps.pressBackButton();
        MatrixServiceSteps.acceptDetailsScreen();

        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS);

        QuestionFormSteps.answerGeneralSlideQuestion(generateMoneyService);
        QuestionFormSteps.answerGeneralSlideQuestion(generateLaborService);
        QuestionFormSteps.answerGeneralSlideQuestion(generateLaborService);
        QuestionFormSteps.answerGeneralSlideQuestion(generateMatrixService);
        QuestionFormSteps.answerGeneralSlideQuestion(generateMatrixService);
        QuestionFormSteps.answerGeneralSlideQuestion(generatePartService);
        QuestionFormSteps.answerGeneralSlideQuestion(generatePartService);

        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES, 1);

        QuestionServiceListSteps.switchToNeedToSetupServiceView();
        QuestionServiceListValidations.validateServicePresent(multiplePartService.getServiceName());
        QuestionServiceListValidations.validateServicePresent(multipleMatrixService.getMatrixServiceName());
        QuestionServiceListSteps.switchToSelectedServiceView();
        QuestionServiceListValidations.validateServicePresent(multipleMoneyService.getServiceName());
        QuestionServiceListValidations.validateServicePresent(multipleLaborService.getServiceName());

        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }
}

