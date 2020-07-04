package com.cyberiansoft.test.vnext.testcases.r360pro.monitoring;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.MonitoringDataUtils;
import com.cyberiansoft.test.dataclasses.ServiceStatus;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.dto.OrderPhaseDto;
import com.cyberiansoft.test.vnext.enums.MonitorRole;
import com.cyberiansoft.test.vnext.enums.RepairOrderStatus;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.interactions.PhaseScreenInteractions;
import com.cyberiansoft.test.vnext.restclient.VNextAPIHelper;
import com.cyberiansoft.test.vnext.restclient.monitorrolessettings.RoleSettingsDTO;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.monitoring.EditOrderSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.MonitorSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.PhaseScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class VNextMonitorStartPhaseServicesTestCases extends BaseTestClass {
    private String workOrderId = "";

    @BeforeClass(description = "Team Monitor Start Phase/Services Test")
    public void beforeClass() throws IOException {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getMonitoringStartPhaseServicesDataPath();
        RoleSettingsDTO roleSettingsDTO = new RoleSettingsDTO();
        roleSettingsDTO.setMonitorCanAddService(false);
        roleSettingsDTO.setMonitorCanEditService(true);
        roleSettingsDTO.setMonitorCanRemoveService(false);
        VNextAPIHelper.updateEmployeeRoleSettings(MonitorRole.EMPLOYEE, roleSettingsDTO);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanChangeStatusForPhase(String rowID,
                                                           String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        OrderPhaseDto orderPhaseDto = workOrderData.getMonitoring().getOrderPhaseDto();

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        final String inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.approveInspection();
        ScreenNavigationSteps.pressBackButton();

        HomeScreenSteps.openInspections();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServices(MonitoringDataUtils.getTestSerivceData());
        workOrderId = WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();

        HomeScreenSteps.openMonitor();
        MonitorSteps.changeLocation("automationMonitoring");
        SearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        EditOrderSteps.openPhaseMenu(orderPhaseDto);
        MenuSteps.selectMenuItem(MenuItems.START);
        GeneralSteps.confirmDialog();
        EditOrderSteps.openPhaseMenu(orderPhaseDto);
        MenuSteps.selectMenuItem(MenuItems.CHANGE_STATUS);
        MenuSteps.selectStatus(ServiceStatus.COMPLETED);
        MonitorSteps.toggleFocusMode(MenuItems.FOCUS_MODE_ON);
        PhaseScreenValidations.validatePhaseStatus(orderPhaseDto);
        orderPhaseDto.getPhaseServices().forEach(service -> PhaseScreenValidations.validateServiceStatus(service.getMonitorService(), ServiceStatus.COMPLETED));

        WizardScreenSteps.saveAction();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanChangeServicesStatus(String rowID,
                                                      String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        OrderPhaseDto orderPhaseDto = workOrderData.getMonitoring().getOrderPhaseDto();

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        final String inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.approveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_WO_MONITOR);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServiceGroup(workOrderData.getDamageData().getDamageGroupName());
        AvailableServicesScreenSteps.selectServices(workOrderData.getDamageData().getMoneyServices());
        ScreenNavigationSteps.pressBackButton();
        BaseUtils.waitABit(1000);
        final String workOrderId = WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();

        HomeScreenSteps.openMonitor();
        MonitorSteps.changeLocation("automationMonitoring");
        SearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorSteps.startRepairOrder(workOrderId);
        MonitorSteps.openItem(workOrderId);

        MenuSteps.selectMenuItem(MenuItems.EDIT);
        orderPhaseDto.getPhaseServices().forEach(service -> {
            EditOrderSteps.openServiceMenu(service.getMonitorService());
            MenuSteps.selectMenuItem(MenuItems.START);
            GeneralSteps.confirmDialog();
            EditOrderSteps.openServiceMenu(service.getMonitorService());
            MenuSteps.selectMenuItem(MenuItems.CHANGE_STATUS);
            MenuSteps.selectStatus(ServiceStatus.SKIPPED);
        });
        MonitorSteps.toggleFocusMode(MenuItems.FOCUS_MODE_ON);
        orderPhaseDto.getPhaseServices().forEach(service -> PhaseScreenValidations.validateServiceStatus(service.getMonitorService(), ServiceStatus.SKIPPED));

        WizardScreenSteps.saveAction();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanStartStopAndCompleteServicesUsingMultiSelects(String rowID,
                                                      String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        OrderPhaseDto orderPhaseDto = workOrderData.getMonitoring().getOrderPhaseDto();

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        final String inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.approveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_WO_MONITOR);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServiceGroup(workOrderData.getDamageData().getDamageGroupName());
        AvailableServicesScreenSteps.selectServices(workOrderData.getDamageData().getMoneyServices());
        ScreenNavigationSteps.pressBackButton();
        BaseUtils.waitABit(1000);
        final String workOrderId = WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();

        HomeScreenSteps.openMonitor();
        MonitorSteps.changeLocation("automationMonitoring");
        SearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.START_RO);
        GeneralSteps.confirmDialog();
        MonitorSteps.openItem(workOrderId);

        MenuSteps.selectMenuItem(MenuItems.EDIT);
        orderPhaseDto.getPhaseServices().forEach(service -> {
                    PhaseScreenInteractions.selectService(service.getMonitorService());
                });
        PhaseScreenInteractions.clickStartServices();
        orderPhaseDto.getPhaseServices().forEach(service -> {
            PhaseScreenValidations.validateServiceStatus(service.getMonitorService(), ServiceStatus.STARTED);
            PhaseScreenValidations.validateStartIconPresentForService(service.getMonitorService(), true);
        });
        orderPhaseDto.getPhaseServices().forEach(service -> {
            PhaseScreenInteractions.selectService(service.getMonitorService());
        });
        PhaseScreenInteractions.clickStopServices();
        orderPhaseDto.getPhaseServices().forEach(service -> {
            PhaseScreenValidations.validateServiceStatus(service.getMonitorService(), ServiceStatus.STARTED);
            PhaseScreenValidations.validateStartIconPresentForService(service.getMonitorService(), false);
        });

        orderPhaseDto.getPhaseServices().forEach(service -> {
            PhaseScreenInteractions.selectService(service.getMonitorService());
        });
        PhaseScreenInteractions.clickCompleteServices();

        MonitorSteps.toggleFocusMode(MenuItems.FOCUS_MODE_ON);
        orderPhaseDto.getPhaseServices().forEach(service -> PhaseScreenValidations.validateServiceStatus(service.getMonitorService(), ServiceStatus.COMPLETED));

        WizardScreenSteps.saveAction();
        ScreenNavigationSteps.pressBackButton();
    }
}
