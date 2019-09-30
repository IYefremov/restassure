package com.cyberiansoft.test.vnext.testcases.r360pro.services.option.multiple;

import com.cyberiansoft.test.dataclasses.DamageData;
import com.cyberiansoft.test.dataclasses.MatrixServiceData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataclasses.partservice.PartServiceData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import com.cyberiansoft.test.vnext.validations.ListServicesValidations;
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

    }
}
