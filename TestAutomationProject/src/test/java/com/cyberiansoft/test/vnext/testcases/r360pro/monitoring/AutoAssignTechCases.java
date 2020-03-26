package com.cyberiansoft.test.vnext.testcases.r360pro.monitoring;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.ServiceStatus;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.RepairOrderStatus;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.monitoring.EditOrderSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.MonitorSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.BundleServiceSteps;
import com.cyberiansoft.test.vnext.steps.services.SelectedServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.PhaseScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class AutoAssignTechCases extends BaseTestClass {

    @BeforeClass
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getAutoAssignTech();
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
        WizardScreenSteps.saveAction();
        SelectedServicesScreenSteps.switchToSelectedService();
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
        SearchSteps.textSearch("rozstalnoy_enable_bundle");
        AvailableServicesScreenSteps.clickAddServiceButton("rozstalnoy_enable_bundle");
        BundleServiceSteps.setBundlePrice("1450");
        BundleServiceSteps.openServiceDetails("rozstalnoy_disable_labor");
        WizardScreenSteps.saveAction();
        BundleServiceSteps.openServiceDetails("rozstalnoy_disable_money");
        WizardScreenSteps.saveAction();
        WizardScreenSteps.saveAction();
        SelectedServicesScreenSteps.switchToSelectedService();
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
}
