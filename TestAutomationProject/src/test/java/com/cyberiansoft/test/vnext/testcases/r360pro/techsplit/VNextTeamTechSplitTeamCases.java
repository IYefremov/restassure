package com.cyberiansoft.test.vnext.testcases.r360pro.techsplit;

import com.cyberiansoft.test.baseutils.MonitoringDataUtils;
import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.monitoring.SearchSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import com.cyberiansoft.test.vnext.validations.TechScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VNextTeamTechSplitTeamCases extends BaseTestCaseTeamEditionRegistration {
    private String inspectionId = "";
    private String workOrderId = "";

    @BeforeClass(description = "Tech split team test cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getTechSplitDataPath();
        HomeScreenSteps.openCreateTeamInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.approveInspection();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanSeparateAmountOfWOForTechniciansManually(String rowID,
                                                                String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<Employee> technicianList = workOrderData.getTechnicianList();
        Map<String, String> technicianSplitData = workOrderData.getTechnicianSplitData();

        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_MONITORING);
        VehicleInfoScreenSteps.selectTechnicians(technicianList);
        VehicleInfoScreenSteps.selectTechniciansPercentage(technicianSplitData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServices(MonitoringDataUtils.getTestSerivceData());
        workOrderId = WorkOrderSteps.saveWorkOrder();
        SearchSteps.searchByText(workOrderId);
        WorkOrderSteps.openMenu(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VehicleInfoScreenSteps.openTechnicianMenu();
        TechScreenValidations.validateTechniciansPercentage(technicianSplitData);
        VehicleInfoScreenSteps.closeTechnicianMenu();
        GeneralSteps.confirmDialog();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, dependsOnMethods = "userCanSeparateAmountOfWOForTechniciansManually")
    public void userCanSeparateAmountOfWOForTechniciansManuallyEditingWO(String rowID,
                                                                         String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        Map<String, String> technicianSplitData = workOrderData.getTechnicianSplitData();

        VehicleInfoScreenSteps.selectTechniciansPercentage(technicianSplitData);
        GeneralSteps.confirmDialog();
        workOrderId = WorkOrderSteps.saveWorkOrder();
        SearchSteps.searchByText(workOrderId);
        WorkOrderSteps.openMenu(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VehicleInfoScreenSteps.openTechnicianMenu();
        TechScreenValidations.validateTechniciansPercentage(technicianSplitData);
        VehicleInfoScreenSteps.closeTechnicianMenu();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class
            , dependsOnMethods = "userCanSeparateAmountOfWOForTechniciansManuallyEditingWO")
    public void userCantSpecifyValueNotEqualTo100PercentOnSplit(String rowID,
                                                                String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        Map<String, String> technicianSplitData = workOrderData.getTechnicianSplitData();

        VehicleInfoScreenSteps.selectTechniciansPercentage(technicianSplitData);
        TechScreenValidations.validatePercentageErrorMessageIsDisplayed();
        GeneralSteps.closeErrorDialog();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class
            , dependsOnMethods = "userCantSpecifyValueNotEqualTo100PercentOnSplit")
    public void userCanSeparateAmountOfWOForTechniciansManuallyEditingWOAndCancelIt(String rowID,
                                                                                    String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        Map<String, String> initialTechnicianSplitData = workOrderData.getTechnicianSplitData();

        Map<String, String> changedTechnicianSplitData = new HashMap<>(workOrderData.getTechnicianSplitData());
        changedTechnicianSplitData.forEach((key, val) -> changedTechnicianSplitData.put(key, "50"));

        VehicleInfoScreenSteps.selectTechniciansPercentage(changedTechnicianSplitData);
        GeneralSteps.declineDialog();
        TechScreenValidations.validateTechniciansPercentage(changedTechnicianSplitData);
        VehicleInfoScreenSteps.closeTechnicianMenu();
        GeneralSteps.confirmDialog();
        ScreenNavigationSteps.pressBackButton();
        GeneralSteps.confirmDialog();
        WorkOrderSteps.openMenu(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VehicleInfoScreenSteps.openTechnicianMenu();
        TechScreenValidations.validateTechniciansPercentage(initialTechnicianSplitData);
        GeneralSteps.confirmDialog();
        WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();
    }
}

