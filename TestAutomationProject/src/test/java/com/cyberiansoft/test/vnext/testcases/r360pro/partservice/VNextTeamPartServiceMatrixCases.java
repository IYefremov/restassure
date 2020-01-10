package com.cyberiansoft.test.vnext.testcases.r360pro.partservice;

import com.cyberiansoft.test.dataclasses.LaborServiceData;
import com.cyberiansoft.test.dataclasses.MatrixServiceData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataclasses.partservice.PartServiceData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.enums.partservice.PartInfoScreenField;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.services.LaborServiceSteps;
import com.cyberiansoft.test.vnext.steps.services.SelectedServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.ServiceDetailsScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.MatrixServiceDetailsValidations;
import com.cyberiansoft.test.vnext.validations.PartInfoScreenValidations;
import com.cyberiansoft.test.vnext.validations.ServiceDetailsValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class VNextTeamPartServiceMatrixCases extends BaseTestClass {
    private String inspectionId = "";

    @BeforeClass(description = "Team Monitoring Matrix Flow Test")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getPartServiceMatrixCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanCreateInspectionWithPartsMatrixService(String rowID,
                                                              String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        List<MatrixServiceData> matrixServiceData = workOrderData.getMatrixServiceDataList();
        MatrixServiceData basicPartsServiceMatrixService = matrixServiceData.get(0);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SearchSteps.textSearch(basicPartsServiceMatrixService.getMatrixServiceName());
        MatrixServiceSteps.selectMatrixService(basicPartsServiceMatrixService);
        basicPartsServiceMatrixService.getVehiclePartData().getPartServicesList().forEach(MatrixServiceSteps::selectPartServiceInsideMatrixService);
        MatrixServiceSteps.switchToSelectedServices();
        basicPartsServiceMatrixService.getVehiclePartData().getPartServicesList().forEach(serviceData -> MatrixServiceDetailsValidations.validateServiceSelected(serviceData.getServiceName()));
        MatrixServiceDetailsValidations.validateMatrixServiceDetails(basicPartsServiceMatrixService);
        ScreenNavigationSteps.pressBackButton();
        MatrixServiceSteps.acceptDetailsScreen();
        inspectionId = InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, dependsOnMethods = "userCanCreateInspectionWithPartsMatrixService")
    public void userCanEditPartsInMatrixService(String rowID,
                                                String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<MatrixServiceData> matrixServiceData = workOrderData.getMatrixServiceDataList();
        MatrixServiceData basicPartsServiceMatrixService = matrixServiceData.get(0);

        HomeScreenSteps.openInspections();
        SearchSteps.searchByText(inspectionId);
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SelectedServicesScreenSteps.openServiceDetails(basicPartsServiceMatrixService.getMatrixServiceName());
        MatrixServiceSteps.selectVehicle(basicPartsServiceMatrixService);
        MatrixServiceSteps.selectSize(basicPartsServiceMatrixService);
        MatrixServiceSteps.selectSeverity(basicPartsServiceMatrixService);
        MatrixServiceSteps.switchToSelectedServices();
        MatrixServiceSteps.openPartServiceDetails(basicPartsServiceMatrixService.getVehiclePartData().getPartServicesList().get(0));
        ServiceDetailsScreenSteps.openPartServiceDetails();
        PartInfoScreenValidations.fieldShouldBeReadonly(true, PartInfoScreenField.CATEGORY);
        PartInfoScreenValidations.fieldShouldBeReadonly(true, PartInfoScreenField.SUB_CATEGORY);
        PartInfoScreenValidations.fieldShouldBeReadonly(true, PartInfoScreenField.PART_NAME);
        PartInfoScreenValidations.fieldShouldBeReadonly(false, PartInfoScreenField.PART_POSITION);
        PartServiceSteps.acceptDetailsScreen();
        PartServiceSteps.confirmPartInfo();
        MatrixServiceSteps.openPartServiceDetails(basicPartsServiceMatrixService.getVehiclePartData().getPartServicesList().get(1));
        ServiceDetailsScreenSteps.openPartServiceDetails();
        PartServiceSteps.changeCategory(basicPartsServiceMatrixService.getVehiclePartData().getPartServicesList().get(2));
        PartServiceSteps.acceptDetailsScreen();
        PartServiceSteps.confirmPartInfo();
        ScreenNavigationSteps.pressBackButton();
        MatrixServiceSteps.acceptDetailsScreen();
        inspectionId = InspectionSteps.saveInspection();

        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SelectedServicesScreenSteps.openServiceDetails(basicPartsServiceMatrixService.getMatrixServiceName());
        MatrixServiceSteps.selectVehicle(basicPartsServiceMatrixService);
        MatrixServiceDetailsValidations.validateMatrixServiceDetails(basicPartsServiceMatrixService);
        MatrixServiceSteps.switchToSelectedServices();
        MatrixServiceSteps.openPartServiceDetails(basicPartsServiceMatrixService.getVehiclePartData().getPartServicesList().get(2));
        ServiceDetailsScreenSteps.openPartServiceDetails();
        PartInfoScreenValidations.validatePartInfo(basicPartsServiceMatrixService.getVehiclePartData().getPartServicesList().get(2));
        PartServiceSteps.acceptDetailsScreen();
        PartServiceSteps.confirmPartInfo();
        ScreenNavigationSteps.pressBackButton();
        MatrixServiceSteps.acceptDetailsScreen();
        inspectionId = InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanSelectAndLinkMultipleLaborServicesToPartService(String rowID,
                                                                       String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<MatrixServiceData> matrixServiceData = workOrderData.getMatrixServiceDataList();
        MatrixServiceData basicPartsServiceMatrixService = matrixServiceData.get(0);
        PartServiceData partServiceInsideMatrix = basicPartsServiceMatrixService.getVehiclePartData().getPartServicesList().get(0);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SearchSteps.textSearch(basicPartsServiceMatrixService.getMatrixServiceName());
        MatrixServiceSteps.selectMatrixService(basicPartsServiceMatrixService);
        MatrixServiceSteps.openPartServiceDetailsInsideMatrixService(partServiceInsideMatrix);
        PartServiceSteps.addLaborService();
        partServiceInsideMatrix.getLaborServiceDataList().stream().map(LaborServiceData::getServiceName).forEach(LaborServiceSteps::selectService);
        WizardScreenSteps.saveAction();
        PartServiceSteps.confirmPartInfo();
        MatrixServiceSteps.switchToSelectedServices();
        partServiceInsideMatrix.getLaborServiceDataList().stream().map(LaborServiceData::getServiceName).forEach(MatrixServiceDetailsValidations::validateServiceSelected);
        MatrixServiceDetailsValidations.validateServiceSelected(partServiceInsideMatrix.getServiceName());
        ScreenNavigationSteps.pressBackButton();
        WizardScreenSteps.saveAction();
        inspectionId = InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanSelectAndLinkMultiplePartsServicesToLaborService(String rowID,
                                                                        String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<MatrixServiceData> matrixServiceData = workOrderData.getMatrixServiceDataList();
        MatrixServiceData basicPartsServiceMatrixService = matrixServiceData.get(0);
        LaborServiceData laborServiceInsideMatrix = basicPartsServiceMatrixService.getVehiclePartData().getLaborService();
        List<PartServiceData> partsServicesInsideLaborService = laborServiceInsideMatrix.getPartServiceDataList();

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SearchSteps.textSearch(basicPartsServiceMatrixService.getMatrixServiceName());
        MatrixServiceSteps.selectMatrixService(basicPartsServiceMatrixService);
        MatrixServiceSteps.openServiceDetailsInsideMatrixService(laborServiceInsideMatrix.getServiceName());
        LaborServiceSteps.addPartService();
        partsServicesInsideLaborService.forEach((service -> {
            PartServiceSteps.selectPartService(service);
            PartServiceSteps.acceptDetailsScreen();
        }));
        WizardScreenSteps.saveAction();
        WizardScreenSteps.saveAction();
        MatrixServiceSteps.switchToSelectedServices();
        partsServicesInsideLaborService.stream().map(PartServiceData::getServiceName).forEach(MatrixServiceDetailsValidations::validateServiceSelected);
        MatrixServiceDetailsValidations.validateServiceSelected(laborServiceInsideMatrix.getServiceName());
        ScreenNavigationSteps.pressBackButton();
        WizardScreenSteps.saveAction();
        inspectionId = InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanAddPartsServicesAndSelectCategorySubcategoryPartNamePartPositionAndEdit(String rowID,
                                                String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<MatrixServiceData> matrixServiceData = workOrderData.getMatrixServiceDataList();
        MatrixServiceData basicPartsServiceMatrixService = matrixServiceData.get(0);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SearchSteps.textSearch(basicPartsServiceMatrixService.getMatrixServiceName());
        MatrixServiceSteps.selectMatrixService(basicPartsServiceMatrixService);
        basicPartsServiceMatrixService.getVehiclePartData().getPartServicesList().forEach(MatrixServiceSteps::selectPartServiceInsideMatrixService);
        MatrixServiceSteps.switchToSelectedServices();
       // basicPartsServiceMatrixService.getVehiclePartData().getPartServicesList().forEach(serviceData -> MatrixServiceDetailsValidations.validateServiceSelected(serviceData.getServiceName()));
        //MatrixServiceDetailsValidations.validateMatrixServiceDetails(basicPartsServiceMatrixService);
        ScreenNavigationSteps.pressBackButton();
        MatrixServiceSteps.acceptDetailsScreen();
        SelectedServicesScreenSteps.openLaborServiceDetails(basicPartsServiceMatrixService.getMatrixServiceName());
        SelectedServicesScreenSteps.openMatrixServiceVehiclePartDetails(basicPartsServiceMatrixService.getVehiclePartData());
        MatrixServiceSteps.switchToSelectedServices();
        final PartServiceData partServiceData = basicPartsServiceMatrixService.getVehiclePartData().getPartServicesList().get(0);
        MatrixServiceSteps.openServiceDetailsInsideMatrixService(partServiceData.getServiceName());
        ServiceDetailsScreenSteps.changeServicePrice(partServiceData.getServicePrice());
        ServiceDetailsScreenSteps.changeServiceQuantity(partServiceData.getServiceQuantity());
        ServiceDetailsScreenSteps.setServiceTextNotes("text note");
        ServiceDetailsScreenSteps.saveServiceDetails();
        ScreenNavigationSteps.pressBackButton();
        MatrixServiceSteps.acceptDetailsScreen();

        inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SelectedServicesScreenSteps.openLaborServiceDetails(basicPartsServiceMatrixService.getMatrixServiceName());
        SelectedServicesScreenSteps.openMatrixServiceVehiclePartDetails(basicPartsServiceMatrixService.getVehiclePartData());
        MatrixServiceSteps.switchToSelectedServices();
        MatrixServiceSteps.openServiceDetailsInsideMatrixService(partServiceData.getServiceName());
        ServiceDetailsValidations.verifyServicePrice(partServiceData.getServicePrice());
        ServiceDetailsValidations.verifyServiceQuantity(partServiceData.getServiceQuantity());
        ServiceDetailsValidations.verifyServiceNotesValue("text note");
        ServiceDetailsScreenSteps.saveServiceDetails();
        ScreenNavigationSteps.pressBackButton();
        MatrixServiceSteps.acceptDetailsScreen();

        inspectionId = InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }
}

