package com.cyberiansoft.test.vnext.testcases.r360pro.monitoring;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.ServiceStatus;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.enums.monitor.OrderMonitorServiceStatuses;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.MonitorRole;
import com.cyberiansoft.test.vnext.enums.RepairOrderStatus;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.interactions.PhaseScreenInteractions;
import com.cyberiansoft.test.vnext.restclient.VNextAPIHelper;
import com.cyberiansoft.test.vnext.restclient.monitorrolessettings.RoleSettingsDTO;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.commonobjects.TopScreenPanelSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.EditOrderSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.MonitorServiceDetailsScreenSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.MonitorSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.PhaseScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.BundleServiceSteps;
import com.cyberiansoft.test.vnext.steps.services.SelectedServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.ServiceDetailsScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.PhaseScreenValidations;
import com.cyberiansoft.test.vnext.validations.monitor.MonitorServiceDetailsScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class AutoAssignTechCases extends BaseTestClass {

    @BeforeClass
    public void beforeClass() throws IOException {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getAutoAssignTech();
        RoleSettingsDTO roleSettingsDTO = new RoleSettingsDTO();
        roleSettingsDTO.setMonitorCanAddService(false);
        roleSettingsDTO.setMonitorCanEditService(true);
        roleSettingsDTO.setMonitorCanRemoveService(false);
        VNextAPIHelper.updateEmployeeRoleSettings(MonitorRole.EMPLOYEE, roleSettingsDTO);
        VNextAPIHelper.updateEmployeeRoleSettings(MonitorRole.INSPECTOR, roleSettingsDTO);
        VNextAPIHelper.updateEmployeeRoleSettings(MonitorRole.MANAGER, roleSettingsDTO);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void autoAssignTechToAMoneyService(String rowID,
                                              String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<ServiceData> serviceDataList = workOrderData.getServicesList();
        ServiceData serviceWithNonDefaultTechnician = serviceDataList.get(0);
        ServiceData serviceWithLoggedInTechnician = serviceDataList.get(1);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        final String inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.approveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(serviceWithNonDefaultTechnician);
        final String workOrderId = WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();

        HomeScreenSteps.openMonitor();
        SearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        EditOrderSteps.openServiceMenu(serviceWithNonDefaultTechnician);
        MenuSteps.selectMenuItem(MenuItems.ASSIGN_TECH);
        GeneralListSteps.selectListItem(serviceWithNonDefaultTechnician.getServiceDefaultTechnician().getTechnicianFullName());
        PhaseScreenValidations.validateServiceTechnician(serviceWithNonDefaultTechnician);

        EditOrderSteps.openServiceMenu(serviceWithNonDefaultTechnician);
        MenuSteps.selectMenuItem(MenuItems.START);
        GeneralSteps.confirmDialog();
        EditOrderSteps.openServiceMenu(serviceWithNonDefaultTechnician);
        MenuSteps.selectMenuItem(MenuItems.COMPLETE);
        GeneralSteps.confirmDialog();

        MonitorSteps.toggleFocusMode(MenuItems.FOCUS_MODE_ON);
        PhaseScreenValidations.validateServiceTechnician(serviceWithLoggedInTechnician);

        EditOrderSteps.openServiceMenu(serviceWithLoggedInTechnician);
        MenuSteps.selectMenuItem(MenuItems.CHANGE_STATUS);
        MenuSteps.selectStatus(ServiceStatus.ACTIVE);
        PhaseScreenValidations.validateServiceTechnician(serviceWithLoggedInTechnician);
        EditOrderSteps.openServiceMenu(serviceWithLoggedInTechnician);
        MenuSteps.selectMenuItem(MenuItems.ASSIGN_TECH);
        GeneralListSteps.selectListItem(serviceWithNonDefaultTechnician.getServiceDefaultTechnician().getTechnicianFullName());

        EditOrderSteps.openServiceMenu(serviceWithNonDefaultTechnician);
        MenuSteps.selectMenuItem(MenuItems.COMPLETE);
        GeneralSteps.confirmDialog();
        PhaseScreenValidations.validateServiceTechnician(serviceWithLoggedInTechnician);
        WizardScreenSteps.saveAction();
        SearchSteps.searchByText("");
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void autoAssignTechToALaborService(String rowID,
                                              String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<ServiceData> serviceDataList = workOrderData.getServicesList();
        ServiceData serviceWithNonDefaultTechnician = serviceDataList.get(0);
        ServiceData serviceWithLoggedInTechnician = serviceDataList.get(1);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        final String inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.approveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.openServiceDetails(serviceWithNonDefaultTechnician);
        ServiceDetailsScreenSteps.openTechniciansScreen();
        TechnicianScreenSteps.searchAndSelectTechnician(serviceWithNonDefaultTechnician.getServiceDefaultTechnician().getTechnicianFullName());
        TechnicianScreenSteps.searchAndUnSelectTechnician(serviceWithLoggedInTechnician.getServiceDefaultTechnician().getTechnicianFullName());
        TopScreenPanelSteps.saveChanges();

        WizardScreenSteps.saveAction();
        SelectedServicesScreenSteps.switchToSelectedService();
        final String workOrderId = WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();

        HomeScreenSteps.openMonitor();
        SearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        PhaseScreenValidations.validateServiceTechnician(serviceWithNonDefaultTechnician);
        EditOrderSteps.openServiceMenu(serviceWithNonDefaultTechnician);
        MenuSteps.selectMenuItem(MenuItems.START);
        GeneralSteps.confirmDialog();
        EditOrderSteps.openServiceMenu(serviceWithNonDefaultTechnician);
        MenuSteps.selectMenuItem(MenuItems.COMPLETE);
        GeneralSteps.confirmDialog();
        MonitorSteps.toggleFocusMode(MenuItems.FOCUS_MODE_ON);
        PhaseScreenValidations.validateServiceTechnician(serviceWithLoggedInTechnician);

        EditOrderSteps.openServiceDetails(serviceWithLoggedInTechnician);
        MonitorServiceDetailsScreenValidations.verifyServiceTechnicianValue(serviceWithLoggedInTechnician.getServiceDefaultTechnician().getTechnicianFullName());
        MonitorServiceDetailsScreenValidations.verifyServiceStatus(OrderMonitorServiceStatuses.COMPLETED);

        MonitorServiceDetailsScreenSteps.changeServiceStatus(OrderMonitorServiceStatuses.ACTIVE);
        MonitorServiceDetailsScreenSteps.changeServiceTechnician(serviceWithNonDefaultTechnician.getServiceDefaultTechnician());
        MonitorServiceDetailsScreenValidations.verifyServiceTechnicianValue(serviceWithNonDefaultTechnician.getServiceDefaultTechnician().getTechnicianFullName());
        MonitorServiceDetailsScreenValidations.verifyServiceStatus(OrderMonitorServiceStatuses.ACTIVE);
        TopScreenPanelSteps.saveChanges();
        PhaseScreenInteractions.selectService(serviceWithNonDefaultTechnician);
        PhaseScreenSteps.completeServices();

        PhaseScreenValidations.validateServiceTechnician(serviceWithLoggedInTechnician);
        PhaseScreenValidations.validateServiceStatus(serviceWithLoggedInTechnician, ServiceStatus.COMPLETED);

        EditOrderSteps.openServiceDetails(serviceWithLoggedInTechnician);
        MonitorServiceDetailsScreenSteps.changeServiceStatus(OrderMonitorServiceStatuses.ACTIVE);
        MonitorServiceDetailsScreenSteps.changeServiceTechnician(serviceWithNonDefaultTechnician.getServiceDefaultTechnician());
        MonitorServiceDetailsScreenValidations.verifyServiceTechnicianValue(serviceWithNonDefaultTechnician.getServiceDefaultTechnician().getTechnicianFullName());
        MonitorServiceDetailsScreenValidations.verifyServiceStatus(OrderMonitorServiceStatuses.ACTIVE);

        MonitorServiceDetailsScreenSteps.completeService();
        MonitorServiceDetailsScreenValidations.verifyServiceTechnicianValue(serviceWithLoggedInTechnician.getServiceDefaultTechnician().getTechnicianFullName());
        MonitorServiceDetailsScreenValidations.verifyServiceStatus(OrderMonitorServiceStatuses.COMPLETED);
        TopScreenPanelSteps.saveChanges();
        PhaseScreenValidations.validateServiceTechnician(serviceWithLoggedInTechnician);
        PhaseScreenValidations.validateServiceStatus(serviceWithLoggedInTechnician, ServiceStatus.COMPLETED);


        WizardScreenSteps.saveAction();
        SearchSteps.searchByText("");
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void autoAssignTechToBundleServices(String rowID,
                                               String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<ServiceData> serviceDataList = workOrderData.getServicesList();
        ServiceData serviceWithNonDefaultTechnician = serviceDataList.get(0);
        ServiceData serviceWithLoggedInTechnician = serviceDataList.get(1);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        final String inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.approveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SearchSteps.textSearch("Georgian_detail");
        AvailableServicesScreenSteps.clickAddServiceButton("Georgian_detail");
        BundleServiceSteps.setBundlePrice("200");
        BundleServiceSteps.openServiceDetails("Rock Fun Service");
        ServiceDetailsScreenSteps.openTechniciansScreen();
        TechnicianScreenSteps.searchAndSelectTechnician(serviceWithNonDefaultTechnician.getServiceDefaultTechnician().getTechnicianFullName());
        TechnicianScreenSteps.searchAndUnSelectTechnician(serviceWithLoggedInTechnician.getServiceDefaultTechnician().getTechnicianFullName());
        TopScreenPanelSteps.saveChanges();
        TopScreenPanelSteps.saveChanges();
        WizardScreenSteps.saveAction();
        SelectedServicesScreenSteps.switchToSelectedService();
        final String workOrderId = WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();

        HomeScreenSteps.openMonitor();
        SearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        PhaseScreenValidations.validateServiceTechnician(serviceWithNonDefaultTechnician);
        EditOrderSteps.openServiceMenu(serviceWithNonDefaultTechnician);
        MenuSteps.selectMenuItem(MenuItems.START);
        GeneralSteps.confirmDialog();
        EditOrderSteps.openServiceMenu(serviceWithNonDefaultTechnician);
        MenuSteps.selectMenuItem(MenuItems.COMPLETE);
        GeneralSteps.confirmDialog();
        MonitorSteps.toggleFocusMode(MenuItems.FOCUS_MODE_ON);
        PhaseScreenValidations.validateServiceTechnician(serviceWithLoggedInTechnician);

        EditOrderSteps.openServiceDetails(serviceWithLoggedInTechnician);
        MonitorServiceDetailsScreenValidations.verifyServiceTechnicianValue(serviceWithLoggedInTechnician.getServiceDefaultTechnician().getTechnicianFullName());
        MonitorServiceDetailsScreenValidations.verifyServiceStatus(OrderMonitorServiceStatuses.COMPLETED);

        MonitorServiceDetailsScreenSteps.changeServiceStatus(OrderMonitorServiceStatuses.ACTIVE);
        MonitorServiceDetailsScreenSteps.changeServiceTechnician(serviceWithNonDefaultTechnician.getServiceDefaultTechnician());
        MonitorServiceDetailsScreenValidations.verifyServiceTechnicianValue(serviceWithNonDefaultTechnician.getServiceDefaultTechnician().getTechnicianFullName());
        MonitorServiceDetailsScreenValidations.verifyServiceStatus(OrderMonitorServiceStatuses.ACTIVE);
        TopScreenPanelSteps.saveChanges();
        PhaseScreenInteractions.selectService(serviceWithNonDefaultTechnician);
        PhaseScreenSteps.completeServices();

        PhaseScreenValidations.validateServiceTechnician(serviceWithLoggedInTechnician);
        PhaseScreenValidations.validateServiceStatus(serviceWithLoggedInTechnician, ServiceStatus.COMPLETED);

        EditOrderSteps.openServiceDetails(serviceWithLoggedInTechnician);
        MonitorServiceDetailsScreenSteps.changeServiceStatus(OrderMonitorServiceStatuses.ACTIVE);
        MonitorServiceDetailsScreenSteps.changeServiceTechnician(serviceWithNonDefaultTechnician.getServiceDefaultTechnician());
        MonitorServiceDetailsScreenValidations.verifyServiceTechnicianValue(serviceWithNonDefaultTechnician.getServiceDefaultTechnician().getTechnicianFullName());
        MonitorServiceDetailsScreenValidations.verifyServiceStatus(OrderMonitorServiceStatuses.ACTIVE);

        MonitorServiceDetailsScreenSteps.completeService();
        MonitorServiceDetailsScreenValidations.verifyServiceTechnicianValue(serviceWithLoggedInTechnician.getServiceDefaultTechnician().getTechnicianFullName());
        MonitorServiceDetailsScreenValidations.verifyServiceStatus(OrderMonitorServiceStatuses.COMPLETED);
        TopScreenPanelSteps.saveChanges();
        PhaseScreenValidations.validateServiceTechnician(serviceWithLoggedInTechnician);
        PhaseScreenValidations.validateServiceStatus(serviceWithLoggedInTechnician, ServiceStatus.COMPLETED);

        WizardScreenSteps.saveAction();
        SearchSteps.searchByText("");
        ScreenNavigationSteps.pressBackButton();
    }
}
