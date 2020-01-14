package com.cyberiansoft.test.vnext.testcases.r360pro.monitoring;

import com.cyberiansoft.test.baseutils.MonitoringDataUtils;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.enums.OrderPriority;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.dto.OrderPhaseDto;
import com.cyberiansoft.test.vnext.enums.RepairOrderFlag;
import com.cyberiansoft.test.vnext.enums.RepairOrderStatus;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.monitoring.EditOrderSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.MonitorSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class VNextTeamMonitoringCommonFilters extends BaseTestClass {
    private String inspectionId = "";
    private String workOrderId = "";

    @BeforeClass(description = "Team Monitoring Basic Flow Test")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getMonitoringBaseCaseDataPath();

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.approveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServices(MonitoringDataUtils.getTestSerivceData());
        workOrderId = WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();
    }

    @BeforeMethod
    public void beforeMethod() {
        HomeScreenSteps.openMonitor();
        MonitorSteps.changeLocation("automationMonitoring");
        SearchSteps.clearAllFilters();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanUseOrderFlagFilter(String rowID,
                                          String description, JSONObject testData) {
        SearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorSteps.setRepairOrderFlag(workOrderId, RepairOrderFlag.GREEN);
        SearchSteps.searchByFlag(RepairOrderFlag.GREEN);
        MonitorSteps.verifyOrderFlag(workOrderId, RepairOrderFlag.GREEN);
        MonitorSteps.setRepairOrderFlag(workOrderId, RepairOrderFlag.YELLOW);
        SearchSteps.searchByFlag(RepairOrderFlag.YELLOW);
        MonitorSteps.verifyOrderFlag(workOrderId, RepairOrderFlag.YELLOW);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanUsePriorityFilter(String rowID,
                                         String description, JSONObject testData) {
        SearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        EditOrderSteps.switchToInfo();
        EditOrderSteps.setOrderPriority(OrderPriority.HIGH);
        ScreenNavigationSteps.pressBackButton();
        SearchSteps.clearAllFilters();
        SearchSteps.searchByPriority(OrderPriority.HIGH);
        MonitorSteps.verifyRepairOrderPresentInList(workOrderId);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanSearchByDepartment(String rowID,
                                          String description, JSONObject testData) {
        SearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        SearchSteps.searchByDepartment("Default");
        MonitorSteps.verifyRepairOrderPresentInList(workOrderId);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanSearchByPhase(String rowID,
                                     String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        OrderPhaseDto expectedOrderInfo = workOrderData.getMonitoring().getOrderPhaseDto();

        SearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        SearchSteps.searchByPhase(expectedOrderInfo.getPhaseName());
        MonitorSteps.verifyRepairOrderPresentInList(workOrderId);
        ScreenNavigationSteps.pressBackButton();
    }
}
