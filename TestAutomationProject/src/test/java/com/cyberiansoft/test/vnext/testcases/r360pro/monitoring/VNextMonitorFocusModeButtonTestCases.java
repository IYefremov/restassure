package com.cyberiansoft.test.vnext.testcases.r360pro.monitoring;

import com.cyberiansoft.test.baseutils.MonitoringDataUtils;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.ServiceStatus;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.dto.OrderPhaseDto;
import com.cyberiansoft.test.vnext.enums.MonitorRole;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
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

public class VNextMonitorFocusModeButtonTestCases extends BaseTestClass {
    private String workOrderId = "";

    @BeforeClass(description = "Team Monitor Focus Mode Button Test Cases")
    public void beforeClass() throws IOException {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getMonitoringFocusModeButtonTestCasesDataPath();
        final String additionalMoneyService = "A new demo service";

        RoleSettingsDTO roleSettingsDTO = new RoleSettingsDTO();
        roleSettingsDTO.setMonitorCanAddService(false);
        roleSettingsDTO.setMonitorCanEditService(true);
        roleSettingsDTO.setMonitorCanRemoveService(false);
        VNextAPIHelper.updateEmployeeRoleSettings(MonitorRole.EMPLOYEE, roleSettingsDTO);
        VNextAPIHelper.updateEmployeeRoleSettings(MonitorRole.MANAGER, roleSettingsDTO);
        VNextAPIHelper.updateEmployeeRoleSettings(MonitorRole.INSPECTOR, roleSettingsDTO);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        final String inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.approveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServices(MonitoringDataUtils.getTestSerivceData());
        AvailableServicesScreenSteps.selectService(additionalMoneyService);
        workOrderId = WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyTheFocusModeButtonAllowToSeeAndHideSkippedAndCompletedServices_ServiceAndPhaseLevels(String rowID,
                                               String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        ServiceData serviceDto = workOrderData.getServiceData();

        //createWorkOrder();
        MonitorSteps.editOrder(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        EditOrderSteps.openServiceMenu(serviceDto);
        MenuSteps.selectMenuItem(MenuItems.START);
        GeneralSteps.confirmDialog();
        serviceDto.setServiceStatus(ServiceStatus.STARTED);
        PhaseScreenValidations.validateServiceStatus(serviceDto);

        EditOrderSteps.openServiceMenu(serviceDto);
        MenuSteps.selectMenuItem(MenuItems.COMPLETE);
        GeneralSteps.confirmDialog();

        PhaseScreenValidations.validateServicePresent(serviceDto, false);
        MonitorSteps.toggleFocusMode(MenuItems.FOCUS_MODE_ON);
        PhaseScreenValidations.validateServicePresent(serviceDto, true);
        serviceDto.setServiceStatus(ServiceStatus.COMPLETED);
        PhaseScreenValidations.validateServiceStatus(serviceDto);

        EditOrderSteps.openServiceMenu(serviceDto);
        MenuSteps.selectMenuItem(MenuItems.CHANGE_STATUS);
        MenuSteps.selectStatus(ServiceStatus.ACTIVE);

        EditOrderSteps.openServiceMenu(serviceDto);
        MenuSteps.selectMenuItem(MenuItems.CHANGE_STATUS);
        MenuSteps.selectStatus(ServiceStatus.SKIPPED);
        PhaseScreenValidations.validateServicePresent(serviceDto, true);


        WizardScreenSteps.saveAction();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyTheFocusModeButtonAllowToSeeAndHideSkippedAndCompletedServices_ServiceLevelOnly(String rowID,
                                                                                                               String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        ServiceData serviceDto = workOrderData.getServiceData();

        //createWorkOrder();
        MonitorSteps.editOrder(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        EditOrderSteps.openServiceMenu(serviceDto);
        MenuSteps.selectMenuItem(MenuItems.START);
        GeneralSteps.confirmDialog();
        serviceDto.setServiceStatus(ServiceStatus.STARTED);
        PhaseScreenValidations.validateServiceStatus(serviceDto);

        EditOrderSteps.openServiceMenu(serviceDto);
        MenuSteps.selectMenuItem(MenuItems.COMPLETE);
        GeneralSteps.confirmDialog();

        PhaseScreenValidations.validateServicePresent(serviceDto, false);
        MonitorSteps.toggleFocusMode(MenuItems.FOCUS_MODE_ON);
        PhaseScreenValidations.validateServicePresent(serviceDto, true);
        serviceDto.setServiceStatus(ServiceStatus.COMPLETED);
        PhaseScreenValidations.validateServiceStatus(serviceDto);

        EditOrderSteps.openServiceMenu(serviceDto);
        MenuSteps.selectMenuItem(MenuItems.CHANGE_STATUS);
        MenuSteps.selectStatus(ServiceStatus.ACTIVE);

        EditOrderSteps.openServiceMenu(serviceDto);
        MenuSteps.selectMenuItem(MenuItems.CHANGE_STATUS);
        MenuSteps.selectStatus(ServiceStatus.SKIPPED);
        PhaseScreenValidations.validateServicePresent(serviceDto, true);


        WizardScreenSteps.saveAction();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyTheFocusModeButtonAllowToSeeAndHideSkippedAndCompletedServices_PhaseLevelOnly(String rowID,
                                                                                                          String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        OrderPhaseDto orderPhaseDto = workOrderData.getMonitoring().getOrderPhaseDto();

        //createWorkOrder();
        MonitorSteps.editOrder(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        EditOrderSteps.openPhaseMenu(orderPhaseDto);
        MenuSteps.selectMenuItem(MenuItems.COMPLETE);
        GeneralSteps.confirmDialog();

        PhaseScreenValidations.validatePhasePresent(orderPhaseDto, false);
        MonitorSteps.toggleFocusMode(MenuItems.FOCUS_MODE_ON);
        PhaseScreenValidations.validatePhasePresent(orderPhaseDto, true);


        EditOrderSteps.openPhaseMenu(orderPhaseDto);
        MenuSteps.selectMenuItem(MenuItems.CHANGE_STATUS);
        MenuSteps.selectStatus(ServiceStatus.ACTIVE);

        EditOrderSteps.openPhaseMenu(orderPhaseDto);
        MenuSteps.selectMenuItem(MenuItems.CHANGE_STATUS);
        MenuSteps.selectStatus(ServiceStatus.SKIPPED);
        PhaseScreenValidations.validatePhasePresent(orderPhaseDto, true);


        WizardScreenSteps.saveAction();
        ScreenNavigationSteps.pressBackButton();
    }
}
