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
import com.cyberiansoft.test.vnext.steps.services.ServiceDetailsScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.ListServicesValidations;
import com.cyberiansoft.test.vnext.validations.QuestionServiceListValidations;
import com.cyberiansoft.test.vnext.validations.VisualScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SingleSelectionServiceTests extends BaseTestClass {
    @BeforeClass(description = "Team Monitoring Basic Flow Test")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getServiceMultipleOptionDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCantAddSingleServiceMultipleTimesCreatingInspection(String rowID,
                                                                String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        ServiceData moneyService = inspectionData.getServicesList().get(0);
        ServiceData laborService = inspectionData.getServicesList().get(1);
        PartServiceData partService = inspectionData.getPartServiceDataList().get(0);
        MatrixServiceData matrixService = inspectionData.getMatrixServiceData();

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.ROZ_WITHOUT_QUESTIONS);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(moneyService);
        AvailableServicesScreenSteps.clickAddServiceButton(moneyService.getServiceName());
        ListServicesValidations.validateMessagePresent(true, "Service can be added once");

        AvailableServicesScreenSteps.selectService(laborService);
        AvailableServicesScreenSteps.clickAddServiceButton(laborService.getServiceName());
        ListServicesValidations.validateMessagePresent(true, "Service can be added once");

        SearchSteps.textSearch(partService.getServiceName());
        PartServiceSteps.selectPartService(partService);
        PartServiceSteps.confirmPartInfo();
        AvailableServicesScreenSteps.clickAddServiceButton(partService.getServiceName());
        ListServicesValidations.validateMessagePresent(true, "Service can be added once");

        SearchSteps.textSearch(matrixService.getMatrixServiceName());
        MatrixServiceSteps.selectMatrixService(matrixService);
        ScreenNavigationSteps.pressBackButton();
        MatrixServiceSteps.acceptDetailsScreen();
        AvailableServicesScreenSteps.clickAddServiceButton(matrixService.getMatrixServiceName());
        ListServicesValidations.validateMessagePresent(true, "Service can be added once");
        ListServicesValidations.validateAvailableServiceCount(matrixService.getMatrixServiceName(), 1);

        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCantAddSingleServiceMultipleTimesEditingInspection(String rowID,
                                                               String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        ServiceData moneyService = inspectionData.getServicesList().get(0);
        ServiceData laborService = inspectionData.getServicesList().get(1);
        PartServiceData partService = inspectionData.getPartServiceDataList().get(0);
        MatrixServiceData matrixService = inspectionData.getMatrixServiceData();

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

        AvailableServicesScreenSteps.clickAddServiceButton(moneyService.getServiceName());
        ListServicesValidations.validateMessagePresent(true, "Service can be added once");

        AvailableServicesScreenSteps.clickAddServiceButton(laborService.getServiceName());
        ListServicesValidations.validateMessagePresent(true, "Service can be added once");

        AvailableServicesScreenSteps.clickAddServiceButton(partService.getServiceName());
        ListServicesValidations.validateMessagePresent(true, "Service can be added once");

        AvailableServicesScreenSteps.clickAddServiceButton(matrixService.getMatrixServiceName());
        ListServicesValidations.validateMessagePresent(true, "Service can be added once");
        ListServicesValidations.validateAvailableServiceCount(matrixService.getMatrixServiceName(), 1);

        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCANAddSingleServiceMultipleTimesCreatingInspection(String rowID,
                                                               String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        ServiceData moneyService = inspectionData.getServicesList().get(0);
        ServiceData laborService = inspectionData.getServicesList().get(1);
        PartServiceData partService = inspectionData.getPartServiceDataList().get(0);
        MatrixServiceData matrixService = inspectionData.getMatrixServiceData();

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
    public void userCANAddSingleServiceMultipleTimesEditingInspection(String rowID,
                                                              String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        ServiceData moneyService = inspectionData.getServicesList().get(0);
        ServiceData laborService = inspectionData.getServicesList().get(1);
        PartServiceData partService = inspectionData.getPartServiceDataList().get(0);
        MatrixServiceData matrixService = inspectionData.getMatrixServiceData();

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
    public void userCantAddSingleServiceMultipleTimesCreatingInspectionFromVisualForm(String rowID,
                                                                              String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        DamageData moneyServiceDamageData = inspectionData.getDamagesData().get(0);
        DamageData laborServiceDamageData = inspectionData.getDamagesData().get(1);
        DamageData partServiceDamageData = inspectionData.getDamagesData().get(2);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.ROZSTALNOY_IT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.VISUAL);
        VisualScreenSteps.selectDamage(moneyServiceDamageData);
        VisualScreenSteps.addServiceToPicture();
        VisualScreenSteps.addServiceToPicture();
        ListServicesValidations.validateMessagePresent(true, "Service can be added once");
        VisualScreenSteps.clearAllMarks();

        VisualScreenSteps.selectDamage(laborServiceDamageData);
        VisualScreenSteps.addServiceToPicture();
        VisualScreenSteps.addServiceToPicture();
        ListServicesValidations.validateMessagePresent(true, "Service can be added once");
        VisualScreenSteps.clearAllMarks();

        VisualScreenSteps.addNonDefaultDamage(partServiceDamageData, partServiceDamageData.getPartServiceData().getServiceName());
        PartServiceSteps.selectPartServiceDetails(partServiceDamageData.getPartServiceData());
        PartServiceSteps.confirmPartInfo();
        ServiceDetailsScreenSteps.saveServiceDetails();
        VisualScreenSteps.addServiceToPicture();
        ListServicesValidations.validateMessagePresent(true, "Service can be added once");

        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCantAddSingleServiceMultipleTimesEditingInspectionFromVisualForm(String rowID,
                                                                             String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        DamageData moneyServiceDamageData = inspectionData.getDamagesData().get(0);
        DamageData laborServiceDamageData = inspectionData.getDamagesData().get(1);
        DamageData partServiceDamageData = inspectionData.getDamagesData().get(2);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.ROZSTALNOY_IT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.VISUAL);
        VisualScreenSteps.selectDamage(moneyServiceDamageData);
        VisualScreenSteps.addServiceToPicture();
        VisualScreenSteps.selectDamage(laborServiceDamageData);
        VisualScreenSteps.addServiceToPicture();
        VisualScreenSteps.addNonDefaultDamage(partServiceDamageData, partServiceDamageData.getPartServiceData().getServiceName());
        PartServiceSteps.selectPartServiceDetails(partServiceDamageData.getPartServiceData());
        PartServiceSteps.confirmPartInfo();
        ServiceDetailsScreenSteps.saveServiceDetails();
        String inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionToEdit(inspectionId);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.VISUAL);

        VisualScreenSteps.selectDamage(moneyServiceDamageData);
        VisualScreenSteps.addServiceToPicture();
        ListServicesValidations.validateMessagePresent(true, "Service can be added once");

        VisualScreenSteps.selectDamage(laborServiceDamageData);
        VisualScreenSteps.addServiceToPicture();
        ListServicesValidations.validateMessagePresent(true, "Service can be added once");

        VisualScreenSteps.addNonDefaultDamage(partServiceDamageData, partServiceDamageData.getPartServiceData().getServiceName());
        ListServicesValidations.validateMessagePresent(true, "Service can be added once");

        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCANAddSingleServiceMultipleTimesCreatingInspectionFromVisualForm(String rowID,
                                                                             String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        DamageData moneyServiceDamageData = inspectionData.getDamagesData().get(0);
        DamageData laborServiceDamageData = inspectionData.getDamagesData().get(1);
        DamageData partServiceDamageData = inspectionData.getDamagesData().get(2);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.ROZSTALNOY_IT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.VISUAL);
        VisualScreenSteps.selectDamage(moneyServiceDamageData);
        VisualScreenSteps.addServiceToPicture();
        VisualScreenSteps.addServiceToPicture();
        VisualScreenValidations.numberOfMarksShouldBeEqualTo(2);
        VisualScreenSteps.clearAllMarks();

        VisualScreenSteps.selectDamage(laborServiceDamageData);
        VisualScreenSteps.addServiceToPicture();
        VisualScreenSteps.addServiceToPicture();
        VisualScreenValidations.numberOfMarksShouldBeEqualTo(2);
        VisualScreenSteps.clearAllMarks();

        VisualScreenSteps.addNonDefaultDamage(partServiceDamageData, partServiceDamageData.getPartServiceData().getServiceName());
        PartServiceSteps.selectPartServiceDetails(partServiceDamageData.getPartServiceData());
        PartServiceSteps.confirmPartInfo();
        ServiceDetailsScreenSteps.saveServiceDetails();
        VisualScreenSteps.addServiceToPicture();
        PartServiceSteps.selectPartServiceDetails(partServiceDamageData.getPartServiceData());
        PartServiceSteps.confirmPartInfo();
        ServiceDetailsScreenSteps.saveServiceDetails();
        VisualScreenValidations.numberOfMarksShouldBeEqualTo(2);
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCANAddSingleServiceMultipleTimesEditingInspectionFromVisualForm(String rowID,
                                                                            String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        DamageData moneyServiceDamageData = inspectionData.getDamagesData().get(0);
        DamageData laborServiceDamageData = inspectionData.getDamagesData().get(1);
        DamageData partServiceDamageData = inspectionData.getDamagesData().get(2);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.ROZSTALNOY_IT);
        String inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionToEdit(inspectionId);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.VISUAL);

        VisualScreenSteps.selectDamage(moneyServiceDamageData);
        VisualScreenSteps.addServiceToPicture();
        VisualScreenValidations.numberOfMarksShouldBeEqualTo(1);
        VisualScreenSteps.clearAllMarks();

        VisualScreenSteps.selectDamage(laborServiceDamageData);
        VisualScreenSteps.addServiceToPicture();
        VisualScreenValidations.numberOfMarksShouldBeEqualTo(1);
        VisualScreenSteps.clearAllMarks();

        VisualScreenSteps.addNonDefaultDamage(partServiceDamageData, partServiceDamageData.getPartServiceData().getServiceName());
        PartServiceSteps.selectPartServiceDetails(partServiceDamageData.getPartServiceData());
        PartServiceSteps.confirmPartInfo();
        ServiceDetailsScreenSteps.saveServiceDetails();
        VisualScreenValidations.numberOfMarksShouldBeEqualTo(1);
        InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanAddServicesUsingQuestionForm(String rowID,
                                                    String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        ServiceData singleMoneyService = inspectionData.getServicesList().get(0);
        ServiceData singleLaborService = inspectionData.getServicesList().get(1);
        ServiceData multipleMoneyService = inspectionData.getServicesList().get(2);
        ServiceData multipleLaborService = inspectionData.getServicesList().get(3);
        PartServiceData singlePartService = inspectionData.getPartServiceDataList().get(0);
        PartServiceData multiplePartService = inspectionData.getPartServiceDataList().get(1);
        MatrixServiceData singleMatrixService = inspectionData.getMatrixServiceDataList().get(0);
        MatrixServiceData multipleMatrixService = inspectionData.getMatrixServiceDataList().get(1);

        QuestionsData generateMoneyService = inspectionData.getQuestionScreenData().getQuestionsData().get(0);
        QuestionsData generateLaborService = inspectionData.getQuestionScreenData().getQuestionsData().get(1);
        QuestionsData generateMatrixService = inspectionData.getQuestionScreenData().getQuestionsData().get(2);
        QuestionsData generatePartService = inspectionData.getQuestionScreenData().getQuestionsData().get(3);

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

        MatrixServiceSteps.selectMatrixService(singleMatrixService);
        ScreenNavigationSteps.pressBackButton();
        MatrixServiceSteps.acceptDetailsScreen();
        SearchSteps.textSearch(multipleMatrixService.getMatrixServiceName());
        MatrixServiceSteps.selectMatrixService(multipleMatrixService);
        ScreenNavigationSteps.pressBackButton();
        MatrixServiceSteps.acceptDetailsScreen();

        WizardScreenSteps.navigateToWizardScreen(ScreenType.QUESTIONS);

        QuestionFormSteps.answerGeneralQuestion(generateMoneyService);
        QuestionFormSteps.answerGeneralQuestion(generateLaborService);
        QuestionFormSteps.answerGeneralQuestion(generateMatrixService);
        QuestionFormSteps.answerGeneralQuestion(generatePartService);
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

