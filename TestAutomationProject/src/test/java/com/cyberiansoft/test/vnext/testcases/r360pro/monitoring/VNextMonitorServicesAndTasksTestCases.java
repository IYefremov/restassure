package com.cyberiansoft.test.vnext.testcases.r360pro.monitoring;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.Monitoring;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataclasses.partservice.PartServiceData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.MonitorRole;
import com.cyberiansoft.test.vnext.enums.RepairOrderStatus;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.enums.TaskStatus;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.interactions.PhaseScreenInteractions;
import com.cyberiansoft.test.vnext.restclient.VNextAPIHelper;
import com.cyberiansoft.test.vnext.restclient.monitorrolessettings.RoleSettingsDTO;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.commonobjects.TopScreenPanelSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.*;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.ServiceDetailsScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.validations.HomeScreenValidation;
import com.cyberiansoft.test.vnext.validations.NotesValidations;
import com.cyberiansoft.test.vnext.validations.PhaseScreenValidations;
import com.cyberiansoft.test.vnext.validations.monitor.SelectStatusScreenValidations;
import com.cyberiansoft.test.vnext.validations.monitor.SelectTechnicianScreenValidations;
import com.cyberiansoft.test.vnext.validations.monitor.TaskDetailsScreenValidations;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class VNextMonitorServicesAndTasksTestCases extends BaseTestClass {

    RoleSettingsDTO roleSettings = new RoleSettingsDTO();
    VNextAPIHelper apiHelper = new VNextAPIHelper();

    @BeforeClass(description = "Monitor services and tasks test cases")
    public void beforeClass() throws IOException {

        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getMonitoringServicesTasksTestCasesDataPath();
    }

    @BeforeMethod()
    public void beforeTest() throws IOException {

        updateEmployeeRoleSettings(MonitorRole.EMPLOYEE, true, false, true);
        updateEmployeeRoleSettings(MonitorRole.INSPECTOR, true, false, true);
        updateEmployeeRoleSettings(MonitorRole.MANAGER, true, false, true);
    }

    @AfterClass
    public void afterClass() throws IOException {

        updateEmployeeRoleSettings(MonitorRole.EMPLOYEE, true, false, true);
        updateEmployeeRoleSettings(MonitorRole.INSPECTOR, true, false, true);
        updateEmployeeRoleSettings(MonitorRole.MANAGER, true, false, true);
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
        updateEmployeeRoleSettings(MonitorRole.EMPLOYEE, false, true, false);
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
        PhaseScreenSteps.openSelectStatusScreen(serviceData);
        SelectStatusScreenValidations.verifySelectStatusScreenIsOpened();
        SelectStatusScreenSteps.selectStatus(TaskStatus.ACTIVE);
        PhaseScreenValidations.validateServiceStatus(serviceData);
        reopenOrderPhaseScreen();
        PhaseScreenInteractions.openTaskForEdit(serviceData);
        TaskDetailsScreenSteps.changeVendorTeam("01_TimeRep_team");
        SelectTechnicianScreenValidations.verifySelectTechnicianScreenIsOpened();
        SelectTechnicianScreenSteps.selectTechnician("Aleksandr Filimonov");
        TaskDetailsScreenValidations.verifyVendorTeamIsCorrect("01_TimeRep_team");
        TaskDetailsScreenValidations.verifyTechnicianIsCorrect("Aleksandr Filimonov");
        TaskDetailsScreenSteps.addNote("test note");
        NotesValidations.verifyNotePresentInList("test note");
        TopScreenPanelSteps.goToThePreviousScreen();
        TopScreenPanelSteps.goToThePreviousScreen();
        TopScreenPanelSteps.saveChanges();
        TopScreenPanelSteps.resetSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyAutoAssignTechToATask(String rowID, String description, JSONObject testData) throws IOException {

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
        TaskDetailsScreenSteps.changeTechnician("1111 2222");
        updateEmployeeRoleSettings(MonitorRole.EMPLOYEE, false, true, false);
        reopenOrderPhaseScreen();
        ServiceData serviceData = monitoringData.getOrderPhasesDto().get(0).getPhaseServices().get(0).getMonitorService();
        PhaseScreenSteps.startService(serviceData);
        PhaseScreenSteps.completeService(serviceData);
        MonitorSteps.toggleFocusMode(MenuItems.FOCUS_MODE);
        //TODO: Below verification fails due to the Bug 133518
        PhaseScreenValidations.validateServiceTechnician(serviceData);
        PhaseScreenSteps.changeTaskStatus(serviceData, TaskStatus.ACTIVE);
        MonitorSteps.tapOnFirstOrder();
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        PhaseScreenInteractions.openTaskForEdit(serviceData);
        TaskDetailsScreenSteps.changeTechnician("1111 2222");
        PhaseScreenInteractions.selectService(serviceData);
        PhaseScreenSteps.completeServices();
        MonitorSteps.toggleFocusMode(MenuItems.FOCUS_MODE);
        PhaseScreenValidations.validateServiceTechnician(serviceData);
        PhaseScreenSteps.changeTaskStatus(serviceData, TaskStatus.ACTIVE);
        //below steps should be changed
        MonitorSteps.tapOnFirstOrder();
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        PhaseScreenInteractions.openTaskForEdit(serviceData);
        TaskDetailsScreenSteps.changeTechnician("1111 2222");
        //TODO: implement task completion on the Task edit screen after bug 133520 is fixed
        MonitorSteps.toggleFocusMode(MenuItems.FOCUS_MODE);
        PhaseScreenValidations.validateServiceStatus(monitoringData.getOrderPhasesDto().get(0).getPhaseServices().get(1).getMonitorService());
        //TODO: Below verification fails due to the Bug 133518
        PhaseScreenValidations.validateServiceTechnician(serviceData);
        TopScreenPanelSteps.saveChanges();
        TopScreenPanelSteps.resetSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
    public void verifyEmployeeCanCanNotAddDeleteEditServicesDependsOnBoSettings(String rowID, String description, JSONObject testData) throws IOException {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        Monitoring monitoringData = workOrderData.getMonitoring();

        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.AUTOMATION_WO_MONITOR, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServiceGroup(workOrderData.getDamageData().getDamageGroupName());
        AvailableServicesScreenSteps.selectService(workOrderData.getDamageData().getMoneyServices().get(1).getServiceName());
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
        PhaseScreenSteps.addServices();
        AvailableServicesScreenSteps.clickAddServiceButton(workOrderData.getDamageData().getMoneyServices().get(0).getServiceName());
        TopScreenPanelSteps.saveChanges();
        ServiceData serviceData = monitoringData.getOrderPhasesDto().get(0).getPhaseServices().get(0).getMonitorService();
        PhaseScreenValidations.validateServicePresent(serviceData, true);
        reopenOrderPhaseScreen();
        PhaseScreenSteps.deleteService(serviceData);
        reopenOrderPhaseScreen();
        BaseUtils.waitABit(10000);
        PhaseScreenValidations.validateServicePresent(serviceData, false);
        PhaseScreenSteps.addServices();
        AvailableServicesScreenSteps.clickAddServiceButton(workOrderData.getDamageData().getMoneyServices().get(0).getServiceName());
        TopScreenPanelSteps.saveChanges();
        updateEmployeeRoleSettings(MonitorRole.EMPLOYEE, false, true, false);
        reopenOrderPhaseScreen();
        PhaseScreenValidations.verifyPlusButtonIsDisplayed(false);
        PhaseScreenInteractions.openServiceDetails(PhaseScreenInteractions.getServiceElements(serviceData.getServiceName()));
        ServiceDetailsScreenSteps.changeServicePrice("4");
        ServiceDetailsScreenSteps.changeServiceQuantity("8");
        TopScreenPanelSteps.saveChanges();
        reopenOrderPhaseScreen();
        //TODO: Below verification fails due to the bug 133519
        PhaseScreenValidations.verifyServicePriceAndQuantityAreCorrect(serviceData.getServiceName(), "4.00", "8");
        TopScreenPanelSteps.saveChanges();
        TopScreenPanelSteps.resetSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
    public void verifyHomeButtonIsAvailableFromPhasePartsInfoPages(String rowID, String description, JSONObject testData) throws IOException {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        PartServiceData partServiceData = workOrderData.getDamageData().getPartServiceData();

        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.AUTOMATION_WO_MONITOR, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServiceGroup(workOrderData.getDamageData().getDamageGroupName());
        AvailableServicesScreenSteps.selectService(workOrderData.getDamageData().getMoneyServices().get(0).getServiceName());
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

        PhaseScreenSteps.addPartService(partServiceData);
        navigateToHomeScreenAndReturnBackToMonitor();

        EditOrderSteps.switchToInfo();
        navigateToHomeScreenAndReturnBackToMonitor();

        EditOrderSteps.switchToParts();
        navigateToHomeScreenAndReturnBackToMonitor();

        TopScreenPanelSteps.saveChanges();
        TopScreenPanelSteps.resetSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
    public void verifyInspectorCanCanNotAddDeleteEditServicesDependsOnBoSettings(String rowID, String description, JSONObject testData) throws IOException {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        Monitoring monitoringData = workOrderData.getMonitoring();

        HomeScreenSteps.reLoginWithAnotherUser(inspector);
        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.AUTOMATION_WO_MONITOR, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServiceGroup(workOrderData.getDamageData().getDamageGroupName());
        AvailableServicesScreenSteps.selectService(workOrderData.getDamageData().getMoneyServices().get(1).getServiceName());
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
        PhaseScreenSteps.addServices();
        AvailableServicesScreenSteps.clickAddServiceButton(workOrderData.getDamageData().getMoneyServices().get(0).getServiceName());
        TopScreenPanelSteps.saveChanges();
        ServiceData serviceData = monitoringData.getOrderPhasesDto().get(0).getPhaseServices().get(0).getMonitorService();
        PhaseScreenValidations.validateServicePresent(serviceData, true);
        reopenOrderPhaseScreen();
        PhaseScreenSteps.deleteService(serviceData);
        reopenOrderPhaseScreen();
        BaseUtils.waitABit(10000);
        PhaseScreenValidations.validateServicePresent(serviceData, false);
        PhaseScreenSteps.addServices();
        AvailableServicesScreenSteps.clickAddServiceButton(workOrderData.getDamageData().getMoneyServices().get(0).getServiceName());
        TopScreenPanelSteps.saveChanges();
        updateEmployeeRoleSettings(MonitorRole.INSPECTOR, false, true, false);
        reopenOrderPhaseScreen();
        PhaseScreenValidations.verifyPlusButtonIsDisplayed(false);
        PhaseScreenInteractions.openServiceDetails(PhaseScreenInteractions.getServiceElements(serviceData.getServiceName()));
        ServiceDetailsScreenSteps.changeServicePrice("4");
        ServiceDetailsScreenSteps.changeServiceQuantity("8");
        TopScreenPanelSteps.saveChanges();
        reopenOrderPhaseScreen();
        //TODO: Below verification fails due to the bug 133519
        PhaseScreenValidations.verifyServicePriceAndQuantityAreCorrect(serviceData.getServiceName(), "4.00", "8");
        TopScreenPanelSteps.saveChanges();
        TopScreenPanelSteps.resetSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
        HomeScreenSteps.reLoginWithAnotherUser(employee);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyManagerCanCanNotAddDeleteEditServicesDependsOnBoSettings(String rowID, String description, JSONObject testData) throws IOException {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        Monitoring monitoringData = workOrderData.getMonitoring();

        HomeScreenSteps.reLoginWithAnotherUser(manager);
        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.AUTOMATION_WO_MONITOR, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServiceGroup(workOrderData.getDamageData().getDamageGroupName());
        AvailableServicesScreenSteps.selectService(workOrderData.getDamageData().getMoneyServices().get(1).getServiceName());
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
        PhaseScreenSteps.addServices();
        AvailableServicesScreenSteps.clickAddServiceButton(workOrderData.getDamageData().getMoneyServices().get(0).getServiceName());
        TopScreenPanelSteps.saveChanges();
        ServiceData serviceData = monitoringData.getOrderPhasesDto().get(0).getPhaseServices().get(0).getMonitorService();
        PhaseScreenValidations.validateServicePresent(serviceData, true);
        reopenOrderPhaseScreen();
        PhaseScreenSteps.deleteService(serviceData);
        reopenOrderPhaseScreen();
        BaseUtils.waitABit(10000);
        PhaseScreenValidations.validateServicePresent(serviceData, false);
        PhaseScreenSteps.addServices();
        AvailableServicesScreenSteps.clickAddServiceButton(workOrderData.getDamageData().getMoneyServices().get(0).getServiceName());
        TopScreenPanelSteps.saveChanges();
        updateEmployeeRoleSettings(MonitorRole.MANAGER, false, true, false);
        reopenOrderPhaseScreen();
        PhaseScreenValidations.verifyPlusButtonIsDisplayed(false);
        PhaseScreenInteractions.openServiceDetails(PhaseScreenInteractions.getServiceElements(serviceData.getServiceName()));
        ServiceDetailsScreenSteps.changeServicePrice("4");
        ServiceDetailsScreenSteps.changeServiceQuantity("8");
        TopScreenPanelSteps.saveChanges();
        reopenOrderPhaseScreen();
        //TODO: Below verification fails due to the bug 133519
        PhaseScreenValidations.verifyServicePriceAndQuantityAreCorrect(serviceData.getServiceName(), "4.00", "8");
        TopScreenPanelSteps.saveChanges();
        TopScreenPanelSteps.resetSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
        HomeScreenSteps.reLoginWithAnotherUser(employee);
    }

    private void updateEmployeeRoleSettings(MonitorRole role, boolean canAdd, boolean canEdit, boolean canRemove) throws IOException {

        roleSettings.setMonitorCanAddService(canAdd);
        roleSettings.setMonitorCanEditService(canEdit);
        roleSettings.setMonitorCanRemoveService(canRemove);
        apiHelper.updateEmployeeRoleSettings(role, roleSettings);
    }

    private void reopenOrderPhaseScreen() {

        WaitUtils.waitUntilElementInvisible(By.xpath("//*[@data-autotests-id='preloader']"));
        TopScreenPanelSteps.saveChanges();
        MonitorSteps.tapOnFirstOrder();
        MenuSteps.selectMenuItem(MenuItems.EDIT);
    }

    private void navigateToHomeScreenAndReturnBackToMonitor() {

        MonitorSteps.clickQuickActionsButton();
        MenuSteps.selectMenuItem(MenuItems.HOME);
        HomeScreenValidation.verifyHomeScreenIsOpened();

        HomeScreenSteps.openMonitor();
        MonitorSteps.tapOnFirstOrder();
        MenuSteps.selectMenuItem(MenuItems.EDIT);
    }
}
