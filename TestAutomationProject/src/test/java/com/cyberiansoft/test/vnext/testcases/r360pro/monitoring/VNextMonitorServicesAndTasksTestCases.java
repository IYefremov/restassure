package com.cyberiansoft.test.vnext.testcases.r360pro.monitoring;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataclasses.partservice.PartServiceData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.MonitorRole;
import com.cyberiansoft.test.vnext.enums.RepairOrderStatus;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.enums.ServiceOrTaskStatus;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.interactions.PhaseScreenInteractions;
import com.cyberiansoft.test.vnext.interactions.services.BundleServiceScreenInteractions;
import com.cyberiansoft.test.vnext.restclient.VNextAPIHelper;
import com.cyberiansoft.test.vnext.restclient.monitorrolessettings.RoleSettingsDTO;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.commonobjects.TopScreenPanelSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.*;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.BundleServiceSteps;
import com.cyberiansoft.test.vnext.steps.services.LaborServiceSteps;
import com.cyberiansoft.test.vnext.steps.services.ServiceDetailsScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.HomeScreenValidation;
import com.cyberiansoft.test.vnext.validations.MenuValidations;
import com.cyberiansoft.test.vnext.validations.NotesValidations;
import com.cyberiansoft.test.vnext.validations.PhaseScreenValidations;
import com.cyberiansoft.test.vnext.validations.monitor.SelectStatusScreenValidations;
import com.cyberiansoft.test.vnext.validations.monitor.SelectTechnicianScreenValidations;
import com.cyberiansoft.test.vnext.validations.monitor.TaskDetailsScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class VNextMonitorServicesAndTasksTestCases extends BaseTestClass {

    RoleSettingsDTO roleSettings = new RoleSettingsDTO();
    VNextAPIHelper apiHelper = new VNextAPIHelper();

    @BeforeClass(description = "Monitor services and tasks test cases")
    public void beforeClass() {

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
        PhaseScreenSteps.openSelectStatusScreen(serviceData.getServiceName());
        SelectStatusScreenValidations.verifySelectStatusScreenIsOpened();
        SelectStatusScreenSteps.selectStatus(ServiceOrTaskStatus.ACTIVE);
        PhaseScreenValidations.validateServiceStatus(serviceData);
        PhaseScreenSteps.reopenOrderPhaseScreen();
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

    //@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
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
        PhaseScreenSteps.reopenOrderPhaseScreen();
        ServiceData serviceData = monitoringData.getOrderPhasesDto().get(0).getPhaseServices().get(0).getMonitorService();
        PhaseScreenSteps.startService(serviceData);
        PhaseScreenSteps.completeService(serviceData);
        MonitorSteps.toggleFocusMode(MenuItems.FOCUS_MODE);
        //TODO: Below verification fails due to the Bug 133518
        PhaseScreenValidations.validateServiceTechnician(serviceData);
        PhaseScreenSteps.changeServiceOrTaskStatus(serviceData.getServiceName(), ServiceOrTaskStatus.ACTIVE);
        MonitorSteps.tapOnFirstOrder();
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        PhaseScreenInteractions.openTaskForEdit(serviceData);
        TaskDetailsScreenSteps.changeTechnician("1111 2222");
        PhaseScreenInteractions.selectService(serviceData);
        PhaseScreenSteps.completeServices();
        MonitorSteps.toggleFocusMode(MenuItems.FOCUS_MODE);
        PhaseScreenValidations.validateServiceTechnician(serviceData);
        PhaseScreenSteps.changeServiceOrTaskStatus(serviceData.getServiceName(), ServiceOrTaskStatus.ACTIVE);
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

    //@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
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
        PhaseScreenSteps.reopenOrderPhaseScreen();
        PhaseScreenSteps.deleteService(serviceData);
        PhaseScreenSteps.reopenOrderPhaseScreen();
        BaseUtils.waitABit(10000);
        PhaseScreenValidations.validateServicePresent(serviceData, false);
        PhaseScreenSteps.addServices();
        AvailableServicesScreenSteps.clickAddServiceButton(workOrderData.getDamageData().getMoneyServices().get(0).getServiceName());
        TopScreenPanelSteps.saveChanges();
        updateEmployeeRoleSettings(MonitorRole.EMPLOYEE, false, true, false);
        PhaseScreenSteps.reopenOrderPhaseScreen();
        PhaseScreenValidations.verifyPlusButtonIsDisplayed(false);
        PhaseScreenInteractions.openServiceDetails(PhaseScreenInteractions.getServiceElements(serviceData.getServiceName()));
        ServiceDetailsScreenSteps.changeServicePrice("4");
        ServiceDetailsScreenSteps.changeServiceQuantity("8");
        TopScreenPanelSteps.saveChanges();
        PhaseScreenSteps.reopenOrderPhaseScreen();
        //TODO: Below verification fails due to the bug 133519
        PhaseScreenValidations.verifyServicePriceAndQuantityAreCorrect(serviceData.getServiceName(), "4.00", "8");
        TopScreenPanelSteps.saveChanges();
        TopScreenPanelSteps.resetSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
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
        PhaseScreenSteps.reopenOrderPhaseScreen();
        PhaseScreenSteps.deleteService(serviceData);
        PhaseScreenSteps.reopenOrderPhaseScreen();
        BaseUtils.waitABit(10000);
        PhaseScreenValidations.validateServicePresent(serviceData, false);
        PhaseScreenSteps.addServices();
        AvailableServicesScreenSteps.clickAddServiceButton(workOrderData.getDamageData().getMoneyServices().get(0).getServiceName());
        TopScreenPanelSteps.saveChanges();
        updateEmployeeRoleSettings(MonitorRole.INSPECTOR, false, true, false);
        PhaseScreenSteps.reopenOrderPhaseScreen();
        PhaseScreenValidations.verifyPlusButtonIsDisplayed(false);
        PhaseScreenInteractions.openServiceDetails(PhaseScreenInteractions.getServiceElements(serviceData.getServiceName()));
        ServiceDetailsScreenSteps.changeServicePrice("4");
        ServiceDetailsScreenSteps.changeServiceQuantity("8");
        TopScreenPanelSteps.saveChanges();
        PhaseScreenSteps.reopenOrderPhaseScreen();
        //TODO: Below verification fails due to the bug 133519
        PhaseScreenValidations.verifyServicePriceAndQuantityAreCorrect(serviceData.getServiceName(), "4.00", "8");
        TopScreenPanelSteps.saveChanges();
        TopScreenPanelSteps.resetSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
        HomeScreenSteps.reLoginWithAnotherUser(employee);
    }

    //@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
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
        PhaseScreenSteps.reopenOrderPhaseScreen();
        PhaseScreenSteps.deleteService(serviceData);
        PhaseScreenSteps.reopenOrderPhaseScreen();
        BaseUtils.waitABit(10000);
        PhaseScreenValidations.validateServicePresent(serviceData, false);
        PhaseScreenSteps.addServices();
        AvailableServicesScreenSteps.clickAddServiceButton(workOrderData.getDamageData().getMoneyServices().get(0).getServiceName());
        TopScreenPanelSteps.saveChanges();
        updateEmployeeRoleSettings(MonitorRole.MANAGER, false, true, false);
        PhaseScreenSteps.reopenOrderPhaseScreen();
        PhaseScreenValidations.verifyPlusButtonIsDisplayed(false);
        PhaseScreenInteractions.openServiceDetails(PhaseScreenInteractions.getServiceElements(serviceData.getServiceName()));
        ServiceDetailsScreenSteps.changeServicePrice("4");
        ServiceDetailsScreenSteps.changeServiceQuantity("8");
        TopScreenPanelSteps.saveChanges();
        PhaseScreenSteps.reopenOrderPhaseScreen();
        //TODO: Below verification fails due to the bug 133519
        PhaseScreenValidations.verifyServicePriceAndQuantityAreCorrect(serviceData.getServiceName(), "4.00", "8");
        TopScreenPanelSteps.saveChanges();
        TopScreenPanelSteps.resetSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
        HomeScreenSteps.reLoginWithAnotherUser(employee);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddServicesUsesPlusButtonAndDelete(String rowID, String description, JSONObject testData) throws IOException {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        List<MonitorServiceData> servicesList = testCaseData.getWorkOrderData().getMonitoring().getOrderPhasesDto().get(0).getPhaseServices();

        HomeScreenSteps.reLoginWithAnotherUser(manager);
        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.AUTOMATION_WO_MONITOR, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServiceGroup(workOrderData.getDamageData().getDamageGroupName());
        AvailableServicesScreenSteps.selectService(servicesList.get(0).getMonitorService().getServiceName());
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

        ServiceData moneyServiceData = servicesList.get(1).getMonitorService();
        PhaseScreenSteps.addServices();
        AvailableServicesScreenSteps.clickAddServiceButton(moneyServiceData.getServiceName());
        TopScreenPanelSteps.saveChanges();
        PhaseScreenValidations.validateServicePresent(moneyServiceData, true);
        PhaseScreenSteps.deleteService(moneyServiceData);
        PhaseScreenValidations.validateServicePresent(moneyServiceData, false);

        ServiceData laborServiceData = servicesList.get(2).getMonitorService();
        PhaseScreenSteps.addServices();
        AvailableServicesScreenSteps.clickAddServiceButton(laborServiceData.getServiceName());
        TopScreenPanelSteps.saveChanges();
        PhaseScreenValidations.validateServicePresent(laborServiceData, true);
        PhaseScreenSteps.deleteService(laborServiceData);
        PhaseScreenValidations.validateServicePresent(laborServiceData, false);

        PartServiceData partServiceData = workOrderData.getDamageData().getPartServiceData();
        String parName = partServiceData.getPartName().getPartNameList().get(0);
        PhaseScreenSteps.addPartService(partServiceData);
        EditOrderSteps.switchToParts();
        PhaseScreenValidations.verifyServiceIsPresent(parName, true);
        PhaseScreenSteps.deleteService(parName);
        PhaseScreenSteps.reopenOrderPhaseScreen();
        PhaseScreenValidations.verifyPartsScreenButtonIsDisplayed(false);

        String bundleServiceName = workOrderData.getBundleService().getBundleServiceName();
        PhaseScreenSteps.addServices();
        AvailableServicesScreenSteps.clickAddServiceButton(servicesList.get(4).getMonitorService().getServiceName());
        BaseUtils.waitABit(2000);
        AvailableServicesScreenSteps.clickAddServiceButton(bundleServiceName);
        BaseUtils.waitABit(2000);
        TopScreenPanelSteps.saveChanges();
        BundleServiceSteps.setBundlePrice(BundleServiceScreenInteractions.getBundleServiceSelectedAmount());
        TopScreenPanelSteps.saveChanges();
        PhaseScreenValidations.verifyServiceIsPresent(bundleServiceName, true);
        PhaseScreenSteps.deleteService(bundleServiceName);
        PhaseScreenValidations.verifyServiceIsPresent(bundleServiceName, false);

        ServiceData taskData = servicesList.get(5).getMonitorService();
        PhaseScreenSteps.addTask();
        SelectTaskScreenSteps.selectTestManualTask();
        TopScreenPanelSteps.saveChanges();
        PhaseScreenValidations.validateServicePresent(taskData, true);

        TopScreenPanelSteps.saveChanges();
        TopScreenPanelSteps.resetSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    //@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifySystemIndicatesReceivedPartForLaborService(String rowID, String description, JSONObject testData) throws IOException {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        List<MonitorServiceData> servicesList = testCaseData.getWorkOrderData().getMonitoring().getOrderPhasesDto().get(0).getPhaseServices();

        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.AUTOMATION_WO_MONITOR, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServiceGroup(workOrderData.getDamageData().getDamageGroupName());
        AvailableServicesScreenSteps.selectService(servicesList.get(0).getMonitorService().getServiceName());
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

        ServiceData laborServiceData = servicesList.get(1).getMonitorService();
        PartServiceData partServiceData = workOrderData.getDamageData().getPartServiceData();
        PhaseScreenSteps.addServices();
        AvailableServicesScreenSteps.clickAddServiceButton(laborServiceData.getServiceName());
        LaborServiceSteps.addPartService(partServiceData);

        updateEmployeeRoleSettings(MonitorRole.EMPLOYEE, false, true, false);
        PhaseScreenSteps.reopenOrderPhaseScreen();

        EditOrderSteps.switchToParts();
        //TODO: below step fails because there is no RECEIVED status on Select status screen
        PhaseScreenSteps.changeServiceOrTaskStatus(partServiceData.getPartName().getPartNameList().get(0), ServiceOrTaskStatus.RECEIVED);


        TopScreenPanelSteps.saveChanges();
        TopScreenPanelSteps.resetSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    //@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifySupportTheBundleServiceItem(String rowID, String description, JSONObject testData) throws IOException {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        List<MonitorServiceData> servicesList = testCaseData.getWorkOrderData().getMonitoring().getOrderPhasesDto().get(0).getPhaseServices();
        PartServiceData partServiceData1 = workOrderData.getDamagesData().get(0).getPartServiceData();
        PartServiceData partServiceData2 = workOrderData.getDamagesData().get(1).getPartServiceData();

        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.AUTOMATION_WO_MONITOR, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServiceGroup(workOrderData.getDamagesData().get(0).getDamageGroupName());
        AvailableServicesScreenSteps.selectService(servicesList.get(0).getMonitorService().getServiceName());
        BundleServiceSteps.addServiceWithPlusButton(servicesList.get(1).getMonitorService().getServiceName());
        BundleServiceSteps.addServiceWithPlusButton(servicesList.get(2).getMonitorService().getServiceName());
        BundleServiceSteps.addPartService(partServiceData1);
        AvailableServicesScreenSteps.selectService(servicesList.get(3).getMonitorService().getServiceName());
        BundleServiceSteps.addServiceWithPlusButton(servicesList.get(4).getMonitorService().getServiceName());
        BundleServiceSteps.addServiceWithPlusButton(servicesList.get(5).getMonitorService().getServiceName());
        BundleServiceSteps.addPartService(partServiceData2);
        ScreenNavigationSteps.pressBackButton();
        final String workOrderId = WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();

        updateEmployeeRoleSettings(MonitorRole.EMPLOYEE, false, true, false);

        HomeScreenSteps.openMonitor();
        SearchSteps.openSearchFilters();
        SearchSteps.clearFilters();
        TopScreenPanelSteps.goToThePreviousScreen();
        SearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorSteps.startRepairOrder(workOrderId);
        MonitorSteps.tapOnFirstOrder();
        MenuSteps.selectMenuItem(MenuItems.EDIT);

        PhaseScreenSteps.waitUntilPhaseScreenIsLoaded();
        PhaseScreenValidations.verifyPhaseScreenIsDisplayed();
        PhaseScreenValidations.verifyServiceIsPresent(servicesList.get(1).getMonitorService().getServiceName(), true);
        PhaseScreenValidations.verifyServiceIsPresent(servicesList.get(2).getMonitorService().getServiceName(), true);
        PhaseScreenValidations.verifyServiceIsPresent(servicesList.get(4).getMonitorService().getServiceName(), true);
        PhaseScreenValidations.verifyServiceIsPresent(servicesList.get(5).getMonitorService().getServiceName(), true);

        verifyServiceMenuContainsAllButtons(servicesList.get(5).getMonitorService().getServiceName());
        verifyServiceMenuContainsAllButtons(servicesList.get(1).getMonitorService().getServiceName());

        PhaseScreenInteractions.selectService(servicesList.get(1).getMonitorService().getServiceName());
        PhaseScreenInteractions.selectService(servicesList.get(2).getMonitorService().getServiceName());
        PhaseScreenInteractions.selectService(servicesList.get(4).getMonitorService().getServiceName());
        PhaseScreenInteractions.selectService(servicesList.get(5).getMonitorService().getServiceName());
        PhaseScreenInteractions.clickStartServices();

        //TODO: add verifier that services were started - after manual test case is updated

        PhaseScreenInteractions.selectService(servicesList.get(1).getMonitorService().getServiceName());
        PhaseScreenInteractions.selectService(servicesList.get(2).getMonitorService().getServiceName());
        PhaseScreenInteractions.selectService(servicesList.get(4).getMonitorService().getServiceName());
        PhaseScreenInteractions.selectService(servicesList.get(5).getMonitorService().getServiceName());
        PhaseScreenInteractions.clickStopServices();

        //TODO: add verifier that services were started - after manual test case is updated

        EditOrderSteps.switchToParts();
        PhaseScreenValidations.verifyServiceIsPresent(partServiceData1.getServiceName(), true);
        PhaseScreenValidations.verifyServiceIsPresent(partServiceData2.getServiceName(), true);

        TopScreenPanelSteps.saveChanges();
        TopScreenPanelSteps.resetSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    private void updateEmployeeRoleSettings(MonitorRole role, boolean canAdd, boolean canEdit, boolean canRemove) throws IOException {

        roleSettings.setMonitorCanAddService(canAdd);
        roleSettings.setMonitorCanEditService(canEdit);
        roleSettings.setMonitorCanRemoveService(canRemove);
        apiHelper.updateEmployeeRoleSettings(role, roleSettings);
    }

    private void navigateToHomeScreenAndReturnBackToMonitor() {

        MonitorSteps.clickQuickActionsButton();
        MenuSteps.selectMenuItem(MenuItems.HOME);
        HomeScreenValidation.verifyHomeScreenIsOpened();

        HomeScreenSteps.openMonitor();
        MonitorSteps.tapOnFirstOrder();
        MenuSteps.selectMenuItem(MenuItems.EDIT);
    }

    private static void verifyServiceMenuContainsAllButtons(String serviceName) {

        EditOrderSteps.openServiceMenu(serviceName);
        MenuValidations.verifyMenuItemIsVisible(MenuItems.RESET_START_DATE.getMenuItemDataName());
        MenuValidations.verifyMenuItemIsVisible(MenuItems.ASSIGN_TECH.getMenuItemDataName());
        MenuValidations.verifyMenuItemIsVisible(MenuItems.CHANGE_STATUS.getMenuItemDataName());
        MenuValidations.verifyMenuItemIsVisible(MenuItems.START.getMenuItemDataName());
        MenuValidations.verifyMenuItemIsVisible(MenuItems.STOP.getMenuItemDataName());
        MenuValidations.verifyMenuItemIsVisible(MenuItems.COMPLETE.getMenuItemDataName());
        MenuValidations.verifyMenuItemIsVisible(MenuItems.REPORT_PROBLEM.getMenuItemDataName());
        MenuValidations.verifyMenuItemIsVisible(MenuItems.RESOLVE_PROBLEM.getMenuItemDataName());
        MenuValidations.verifyMenuItemIsVisible(MenuItems.NOTES.getMenuItemDataName());
        MenuValidations.verifyMenuItemIsVisible(MenuItems.TIME_REPORT.getMenuItemDataName());
        MenuSteps.closeMenu();
    }
}
