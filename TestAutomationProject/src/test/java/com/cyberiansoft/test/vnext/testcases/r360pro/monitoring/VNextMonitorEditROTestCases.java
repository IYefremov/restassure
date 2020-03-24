package com.cyberiansoft.test.vnext.testcases.r360pro.monitoring;

import com.cyberiansoft.test.baseutils.MonitoringDataUtils;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.enums.OrderPriority;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.dto.OrderInfoDto;
import com.cyberiansoft.test.vnext.enums.RepairOrderStatus;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.monitoring.EditOrderSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.MonitorSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.MenuValidations;
import com.cyberiansoft.test.vnext.validations.PhaseScreenValidations;
import com.cyberiansoft.test.vnext.validations.monitor.RepairOrderInfoValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextMonitorEditROTestCases extends BaseTestClass {
    private String workOrderId = "";

    @BeforeClass(description = "Team Monitor Edit RO Test")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getMonitoringEditRODataPath();

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
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanSwitchToInfoMode(String rowID,
                                            String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        OrderInfoDto expectedOrderInfo = workOrderData.getMonitoring().getOrderInfoDto();

        HomeScreenSteps.openMonitor();
        MonitorSteps.changeLocation("automationMonitoring");
        SearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        EditOrderSteps.switchToInfo();
        RepairOrderInfoValidations.verifyOrderInfo(expectedOrderInfo);
        EditOrderSteps.setOrderPriority(OrderPriority.HIGH);
        RepairOrderInfoValidations.validateRepairOrderPriority(OrderPriority.HIGH, true);
        RepairOrderInfoValidations.validateRepairOrderPriority(OrderPriority.LOW, false);
        RepairOrderInfoValidations.validateRepairOrderPriority(OrderPriority.NORMAL, false);

        EditOrderSteps.setOrderPriority(OrderPriority.LOW);
        RepairOrderInfoValidations.validateRepairOrderPriority(OrderPriority.HIGH, false);
        RepairOrderInfoValidations.validateRepairOrderPriority(OrderPriority.LOW, true);
        RepairOrderInfoValidations.validateRepairOrderPriority(OrderPriority.NORMAL, false);

        EditOrderSteps.setOrderPriority(OrderPriority.NORMAL);
        RepairOrderInfoValidations.validateRepairOrderPriority(OrderPriority.HIGH, false);
        RepairOrderInfoValidations.validateRepairOrderPriority(OrderPriority.LOW, false);
        RepairOrderInfoValidations.validateRepairOrderPriority(OrderPriority.NORMAL, true);

        WizardScreenSteps.saveAction();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanStartTheService(String rowID,
                                                  String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        ServiceData serviceData = workOrderData.getServiceData();

        HomeScreenSteps.openMonitor();
        MonitorSteps.changeLocation("automationMonitoring");
        SearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        EditOrderSteps.openServiceMenu(serviceData);
        MenuValidations.menuItemShouldBeEnabled(MenuItems.START, true);
        MenuValidations.menuItemShouldBeEnabled(MenuItems.ASSIGN_TECH, true);
        MenuValidations.menuItemShouldBeEnabled(MenuItems.CHANGE_STATUS, false);
        MenuSteps.selectMenuItem(MenuItems.START);
        GeneralSteps.confirmDialog();
        PhaseScreenValidations.validateServiceStatus(serviceData);

        WizardScreenSteps.saveAction();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanTypeTheNoteForService(String rowID,
                                                 String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        ServiceData serviceData = workOrderData.getServiceData();
        final String notesText = "Test note 1";

        HomeScreenSteps.openMonitor();
        MonitorSteps.changeLocation("automationMonitoring");
        SearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        EditOrderSteps.openServiceMenu(serviceData);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.addRepairOrderNote();
        NotesSteps.setRepairOrderNoteText(notesText);
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();

        PhaseScreenValidations.validateServiceNotes(serviceData, notesText);

        WizardScreenSteps.saveAction();
        ScreenNavigationSteps.pressBackButton();
    }
}
