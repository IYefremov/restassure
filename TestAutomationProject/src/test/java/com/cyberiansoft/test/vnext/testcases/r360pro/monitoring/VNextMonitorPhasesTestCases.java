package com.cyberiansoft.test.vnext.testcases.r360pro.monitoring;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.MonitorRole;
import com.cyberiansoft.test.vnext.enums.RepairOrderStatus;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.restclient.VNextAPIHelper;
import com.cyberiansoft.test.vnext.restclient.monitorrolessettings.RoleSettingsDTO;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.commonobjects.TopScreenPanelSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.*;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.PhaseScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class VNextMonitorPhasesTestCases extends BaseTestClass {

    RoleSettingsDTO roleSettings = new RoleSettingsDTO();
    VNextAPIHelper apiHelper = new VNextAPIHelper();
    WorkOrderData workOrderData;
    ServiceData serviceData;
    private static final String PRECONDITIONS_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/r360pro/monitoring/monitoring-phases-testcases-base-data.json";

    @BeforeClass(description = "Monitor phases test cases")
    public void beforeClass() throws Exception {

        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getMonitoringPhasesTestCasesDataPath();
        workOrderData = JSonDataParser.getTestDataFromJson(JSONDataProvider.extractData_JSON(PRECONDITIONS_FILE), TestCaseData.class).getWorkOrderData();
        serviceData = workOrderData.getMonitoring().getOrderPhaseDto().getPhaseServices().get(0).getMonitorService();


        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.AUTOMATION_WO_MONITOR, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServiceGroup(workOrderData.getDamageData().getDamageGroupName());
        AvailableServicesScreenSteps.selectService(serviceData.getServiceName());
        ScreenNavigationSteps.pressBackButton();
        final String workOrderId = WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();

        HomeScreenSteps.openMonitor();
        SearchSteps.openSearchFilters();
        SearchSteps.clearFilters();
        TopScreenPanelSteps.goToThePreviousScreen();
        SearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorSteps.startRepairOrder(workOrderId);
        MonitorSteps.tapOnFirstOrder();
        MenuSteps.selectMenuItem(MenuItems.EDIT);
    }

    @AfterClass
    public void afterClass() {

        TopScreenPanelSteps.saveChanges();
        TopScreenPanelSteps.resetSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @BeforeMethod()
    public void beforeTest() throws IOException {

        updateEmployeeRoleSettings(MonitorRole.EMPLOYEE, true, false, true);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAssignTechOnPhaseLevel(String rowID, String description, JSONObject testData) throws IOException {

        PhaseScreenSteps.changePhaseTechnician(workOrderData.getMonitoring().getOrderPhaseDto());
        PhaseScreenValidations.validateServiceTechnician(serviceData);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanClosePhaseEditPage(String rowID, String description, JSONObject testData) throws IOException {

        EditOrderSteps.openPhaseMenu(workOrderData.getMonitoring().getOrderPhaseDto());
        MenuSteps.closeMenu();
        PhaseScreenValidations.verifyPhaseScreenIsDisplayed();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSelectTheVendorTeam(String rowID, String description, JSONObject testData) throws IOException {

        TopScreenPanelSteps.saveChanges();
        MonitorSteps.tapOnFirstOrder();
        MenuSteps.selectMenuItem(MenuItems.ASSIGN_TECH);
        SelectTechnicianScreenSteps.selectTechnician(serviceData.getServiceDefaultTechnician().getTechnicianFullName());
        AssignTechScreenSteps.changeVendorTeam("Test Team");
        TopScreenPanelSteps.saveChanges();
        MonitorSteps.tapOnFirstOrder();
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        PhaseScreenValidations.validateServiceTechnician(serviceData);
    }

    private void updateEmployeeRoleSettings(MonitorRole role, boolean canAdd, boolean canEdit, boolean canRemove) throws IOException {

        roleSettings.setMonitorCanAddService(canAdd);
        roleSettings.setMonitorCanEditService(canEdit);
        roleSettings.setMonitorCanRemoveService(canRemove);
        apiHelper.updateEmployeeRoleSettings(role, roleSettings);
    }
}
