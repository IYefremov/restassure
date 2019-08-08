package com.cyberiansoft.test.vnext.testcases.r360pro.partservice;

import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataclasses.partservice.MatrixServiceData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.enums.partservice.PartInfoScreenField;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import com.cyberiansoft.test.vnext.validations.MatrixServiceDetailsValidations;
import com.cyberiansoft.test.vnext.validations.PartInfoScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class VNextTeamPartServiceMatrixCases extends BaseTestCaseTeamEditionRegistration {
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
        SearchSteps.textSearch(basicPartsServiceMatrixService.getServiceName());
        MatrixServiceSteps.selectMatrixService(basicPartsServiceMatrixService);
        basicPartsServiceMatrixService.getPartServiceDataList().forEach(MatrixServiceSteps::selectPartServiceInsideMatrixService);
        MatrixServiceSteps.switchToSelectedServices();
        basicPartsServiceMatrixService.getPartServiceDataList().forEach(serviceData -> MatrixServiceDetailsValidations.validateServiceSelected(serviceData.getServiceName()));
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
        SelectedServicesScreenSteps.openServiceDetails(basicPartsServiceMatrixService.getServiceName());
        MatrixServiceSteps.selectVehicle(basicPartsServiceMatrixService);
        MatrixServiceSteps.selectSize(basicPartsServiceMatrixService);
        MatrixServiceSteps.selectSeverity(basicPartsServiceMatrixService);
        MatrixServiceSteps.switchToSelectedServices();
        MatrixServiceSteps.openPartServiceDetails(basicPartsServiceMatrixService.getPartServiceDataList().get(0));
        ServiceDetailsScreenSteps.openPartServiceDetails();
        PartInfoScreenValidations.fieldShouldBeReadonly(true, PartInfoScreenField.CATEGORY);
        PartInfoScreenValidations.fieldShouldBeReadonly(true, PartInfoScreenField.SUB_CATEGORY);
        PartInfoScreenValidations.fieldShouldBeReadonly(true, PartInfoScreenField.PART_NAME);
        PartInfoScreenValidations.fieldShouldBeReadonly(false, PartInfoScreenField.PART_POSITION);
        PartServiceSteps.acceptDetailsScreen();
        PartServiceSteps.confirmPartInfo();
        MatrixServiceSteps.openPartServiceDetails(basicPartsServiceMatrixService.getPartServiceDataList().get(1));
        ServiceDetailsScreenSteps.openPartServiceDetails();
        PartServiceSteps.changeCategory(basicPartsServiceMatrixService.getPartServiceDataList().get(2));
        PartServiceSteps.acceptDetailsScreen();
        PartServiceSteps.confirmPartInfo();
        ScreenNavigationSteps.pressBackButton();
        MatrixServiceSteps.acceptDetailsScreen();
        inspectionId = InspectionSteps.saveInspection();

        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SelectedServicesScreenSteps.openServiceDetails(basicPartsServiceMatrixService.getServiceName());
        MatrixServiceSteps.selectVehicle(basicPartsServiceMatrixService);
        MatrixServiceDetailsValidations.validateMatrixServiceDetails(basicPartsServiceMatrixService);
        MatrixServiceSteps.switchToSelectedServices();
        MatrixServiceSteps.openPartServiceDetails(basicPartsServiceMatrixService.getPartServiceDataList().get(2));
        ServiceDetailsScreenSteps.openPartServiceDetails();
        PartInfoScreenValidations.validatePartInfo(basicPartsServiceMatrixService.getPartServiceDataList().get(2));
        PartServiceSteps.acceptDetailsScreen();
        PartServiceSteps.confirmPartInfo();
        ScreenNavigationSteps.pressBackButton();
        MatrixServiceSteps.acceptDetailsScreen();
        inspectionId = InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }
}
