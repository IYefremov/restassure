package com.cyberiansoft.test.vnext.testcases.r360pro.partservice;

import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataclasses.partservice.PartServiceData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.RepairOrderStatus;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.monitoring.MonitorSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.SearchSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import com.cyberiansoft.test.vnext.validations.PartInfoScreenValidations;
import com.cyberiansoft.test.vnext.validations.ServiceDetailsValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class VNextTeamPartServiceBaseCase extends BaseTestCaseTeamEditionRegistration {
    private String inspectionId = "";

    @BeforeClass(description = "Team Monitoring Basic Flow Test")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getPartServiceBasicCasesDataPath();
        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanChangePartServiceValuesAndSaveThem(String rowID,
                                                          String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<PartServiceData> partServiceData = workOrderData.getPartServiceList();
        PartServiceData basicPartService = partServiceData.get(0);
        PartServiceData editedPartService = partServiceData.get(1);

        PartServiceSteps.selectPartService(basicPartService);
        PartServiceSteps.confirmPartInfo();
        SelectedServicesScreenSteps.openServiceDetails("PREF:  " + basicPartService.getPartName());
        ServiceDetailsScreenSteps.changeServicePrice(editedPartService.getServicePrice());
        ServiceDetailsScreenSteps.openPartServiceDetails();
        PartServiceSteps.changeCategory(editedPartService);
        PartServiceSteps.confirmPartInfo();
        ServiceDetailsScreenSteps.closeServiceDetailsScreen();
        inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SelectedServicesScreenSteps.openServiceDetails("PREF:  Parking Brake Cable Lever");
        ServiceDetailsValidations.verifyServicePrice(editedPartService.getServicePrice());
        ServiceDetailsScreenSteps.openPartServiceDetails();
        PartInfoScreenValidations.validatePartInfo(editedPartService);
        PartServiceSteps.confirmPartInfo();
        ServiceDetailsScreenSteps.closeServiceDetailsScreen();
        InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, dependsOnMethods = "userCanChangePartServiceValuesAndSaveThem")
    public void userCanEditPartService(String rowID,
                                       String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<PartServiceData> partServiceData = workOrderData.getPartServiceList();
        PartServiceData basicPartService = partServiceData.get(0);
        PartServiceData editedPartService = partServiceData.get(1);

        SearchSteps.searchByTextAndStatus(inspectionId, RepairOrderStatus.All);
        MonitorSteps.openMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SelectedServicesScreenSteps.openServiceDetails("PREF:  Parking Brake Cable Lever");
        ServiceDetailsScreenSteps.openPartServiceDetails();
        PartServiceSteps.changeCategory(editedPartService);
        PartServiceSteps.confirmPartInfo();
        ServiceDetailsScreenSteps.closeServiceDetailsScreen();
        inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SelectedServicesScreenSteps.openServiceDetails("PREF:  Parking Brake Cable Lever");
        ServiceDetailsScreenSteps.openPartServiceDetails();
        PartInfoScreenValidations.validatePartInfo(editedPartService);
        PartServiceSteps.confirmPartInfo();
        ServiceDetailsScreenSteps.closeServiceDetailsScreen();
        InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }
}
