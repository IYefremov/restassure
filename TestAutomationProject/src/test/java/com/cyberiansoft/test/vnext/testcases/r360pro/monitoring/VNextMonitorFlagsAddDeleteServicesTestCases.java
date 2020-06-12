package com.cyberiansoft.test.vnext.testcases.r360pro.monitoring;

import com.cyberiansoft.test.baseutils.MonitoringDataUtils;
import com.cyberiansoft.test.dataclasses.ServiceStatus;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.MonitorRole;
import com.cyberiansoft.test.vnext.enums.RepairOrderStatus;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.interactions.PhaseScreenInteractions;
import com.cyberiansoft.test.vnext.interactions.services.AvailableServiceScreenInteractions;
import com.cyberiansoft.test.vnext.restclient.VNextAPIHelper;
import com.cyberiansoft.test.vnext.restclient.monitorrolessettings.RoleSettingsDTO;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.monitoring.MonitorSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.PhaseScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.ServiceDetailsScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.PhaseScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class VNextMonitorFlagsAddDeleteServicesTestCases extends BaseTestClass {
    String workOrderId;
    String inspectionId;

    @BeforeClass(description = "Monitor Flags Add Delete Services Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getMonitoringAddDeleteServicesTestCasesDataPath();
        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.approveInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCanAddServiceInMonitorAddDeleteServices(String rowID,
                                                                                 String description, JSONObject testData) throws IOException {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        RoleSettingsDTO roleSettingsDTO = new RoleSettingsDTO();
        roleSettingsDTO.setMonitorCanAddService(true);
        roleSettingsDTO.setMonitorCanEditService(false);
        roleSettingsDTO.setMonitorCanRemoveService(true);
        VNextAPIHelper.updateEmployeeRoleSettings(MonitorRole.EMPLOYEE, roleSettingsDTO);
        VNextAPIHelper.updateEmployeeRoleSettings(MonitorRole.INSPECTOR, roleSettingsDTO);
        VNextAPIHelper.updateEmployeeRoleSettings(MonitorRole.MANAGER, roleSettingsDTO);

        HomeScreenSteps.openInspections();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServices(MonitoringDataUtils.getTestSerivceData());
        workOrderId = WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();

        HomeScreenSteps.openMonitor();
        SearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        PhaseScreenSteps.addServices();
        SearchSteps.textSearch(workOrderData.getMoneyServiceData().getServiceName());
        AvailableServiceScreenInteractions.openServiceDetails(workOrderData.getMoneyServiceData().getServiceName());
        ServiceDetailsScreenSteps.saveServiceDetails();

        PhaseScreenSteps.deleteService(workOrderData.getMoneyServiceData());

        PhaseScreenValidations.validateServicePresent(workOrderData.getMoneyServiceData(), false);
        WizardScreenSteps.saveAction();
        SearchSteps.searchByText("");
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCanAddServiceInMonitorStartStopRO(String rowID,
                                                            String description, JSONObject testData) throws IOException {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        RoleSettingsDTO roleSettingsDTO = new RoleSettingsDTO();
        roleSettingsDTO.setMonitorCanAddService(false);
        roleSettingsDTO.setMonitorCanEditService(true);
        roleSettingsDTO.setMonitorCanRemoveService(false);
        VNextAPIHelper.updateEmployeeRoleSettings(MonitorRole.EMPLOYEE, roleSettingsDTO);
        VNextAPIHelper.updateEmployeeRoleSettings(MonitorRole.INSPECTOR, roleSettingsDTO);
        VNextAPIHelper.updateEmployeeRoleSettings(MonitorRole.MANAGER, roleSettingsDTO);

        HomeScreenSteps.openInspections();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServices(MonitoringDataUtils.getTestSerivceData());
        workOrderId = WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();

        HomeScreenSteps.openMonitor();
        SearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);

        workOrderData.getServicesList().forEach(PhaseScreenInteractions::selectService);
        PhaseScreenSteps.startServices();

        workOrderData.getServicesList().forEach(serviceData -> PhaseScreenValidations.validateServiceStatus(serviceData, ServiceStatus.STARTED));

        workOrderData.getServicesList().forEach(PhaseScreenInteractions::selectService);
        PhaseScreenSteps.stopServices();
        workOrderData.getServicesList().forEach(PhaseScreenInteractions::selectService);
        PhaseScreenSteps.startServices();

        workOrderData.getServicesList().forEach(PhaseScreenInteractions::selectService);
        PhaseScreenSteps.completeServices();
        MonitorSteps.toggleFocusMode(MenuItems.FOCUS_MODE_ON);
        workOrderData.getServicesList().forEach(serviceData -> PhaseScreenValidations.validateServiceStatus(serviceData, ServiceStatus.COMPLETED));

        WizardScreenSteps.saveAction();
        SearchSteps.searchByText("");
        ScreenNavigationSteps.pressBackButton();
    }
}
