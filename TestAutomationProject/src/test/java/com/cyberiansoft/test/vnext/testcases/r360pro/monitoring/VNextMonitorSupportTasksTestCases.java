package com.cyberiansoft.test.vnext.testcases.r360pro.monitoring;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.Monitoring;
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
import com.cyberiansoft.test.vnext.steps.monitoring.MonitorSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.PhaseScreenSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.SelectTaskScreenSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.TaskDetailsScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.PhaseScreenValidations;
import com.cyberiansoft.test.vnext.validations.monitor.TaskDetailsScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class VNextMonitorSupportTasksTestCases extends BaseTestClass {

    RoleSettingsDTO roleSettings = new RoleSettingsDTO();
    VNextAPIHelper apiHelper = new VNextAPIHelper();;

    @BeforeClass(description = "Support tasks in Monitor test cases")
    public void beforeClass() throws IOException {

        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getMonitoringBaseCaseDataPath();
        updateEmployeeRoleSettings(true, false, true);
    }

    @AfterClass
    public void afterClass() throws IOException {

        updateEmployeeRoleSettings(true, false, true);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddAndEditTasks(String rowID, String description, JSONObject testData) throws IOException {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        Monitoring monitoringData = workOrderData.getMonitoring();

        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.AUTOMATION_WO_MONITOR, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServiceGroup(workOrderData.getDamageData().getDamageGroupName());
        AvailableServicesScreenSteps.selectServices(workOrderData.getDamageData().getMoneyServices());
        ScreenNavigationSteps.pressBackButton();
        final String workOrderId = WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();
        BaseUtils.waitABit(30000);

        HomeScreenSteps.openMonitor();
        SearchSteps.openSearchFilters();
        SearchSteps.clearFilters();
        TopScreenPanelSteps.goToThePreviousScreen();
        SearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorSteps.startRepairOrder(workOrderId);
        MonitorSteps.tapOnFirstOrder();
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        PhaseScreenSteps.addTask();
        SelectTaskScreenSteps.selectTestManualTask();
        TaskDetailsScreenValidations.verifyAddNewTaskPageIsOpened();
        TopScreenPanelSteps.saveChanges();

        updateEmployeeRoleSettings(false, true, false);

        TopScreenPanelSteps.saveChanges();
        MonitorSteps.tapOnFirstOrder();
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        ServiceData serviceData = monitoringData.getOrderPhasesDto().get(0).getPhaseServices().get(0).getMonitorService();
        PhaseScreenSteps.startService(serviceData);
        PhaseScreenValidations.validateServiceStatus(serviceData);
        PhaseScreenValidations.serviceShouldHaveStartDate(serviceData, true);
        PhaseScreenValidations.validateStartIconPresentForService(serviceData, true);
        PhaseScreenSteps.stopService(serviceData);
        PhaseScreenValidations.validateServiceStatus(serviceData);
        PhaseScreenValidations.serviceShouldHaveStartDate(serviceData, true);
        PhaseScreenValidations.validateStartIconPresentForService(serviceData, false);
        PhaseScreenSteps.completeService(serviceData);
        PhaseScreenValidations.validateServicePresent(serviceData, false);
        MonitorSteps.toggleFocusMode(MenuItems.FOCUS_MODE);
        PhaseScreenValidations.validateServiceStatus(monitoringData.getOrderPhasesDto().get(0).getPhaseServices().get(1).getMonitorService());
        PhaseScreenSteps.changeServiceStatusToActive(serviceData);
        PhaseScreenValidations.validateServiceStatus(serviceData);

        TopScreenPanelSteps.saveChanges();
        MonitorSteps.tapOnFirstOrder();
        MenuSteps.selectMenuItem(MenuItems.EDIT);

        PhaseScreenSteps.changeTaskTeamAndTechnician(serviceData, "01_TimeRep_team", "Aleksandr Filimonov");
        TaskDetailsScreenValidations.verifyVendorTeamIsCorrect("01_TimeRep_team");
        TaskDetailsScreenValidations.verifyTechnicianIsCorrect("Aleksandr Filimonov");
        TaskDetailsScreenSteps.addNote("test note");
        TopScreenPanelSteps.goToThePreviousScreen();
        TopScreenPanelSteps.saveChanges();
        TopScreenPanelSteps.resetSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    private void updateEmployeeRoleSettings(boolean canAdd, boolean canEdit, boolean canRemove) throws IOException {

        roleSettings.setMonitorCanAddService(canAdd);
        roleSettings.setMonitorCanEditService(canEdit);
        roleSettings.setMonitorCanRemoveService(canRemove);
        apiHelper.updateEmployeeRoleSettings(MonitorRole.EMPLOYEE, roleSettings);
    }
}
