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
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.monitoring.EditOrderSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.MonitorSearchSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.MonitorSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class VNextTeamMonitoringCommonFilters extends BaseTestCaseTeamEditionRegistration {
    private String inspectionId = "";
    private String workOrderId = "";

    @BeforeClass(description = "Team Monitoring Basic Flow Test")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getMonitoringBaseCaseDataPath();

        HomeScreenSteps.openCreateNewInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.approveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_MONITORING);
        WorkOrderSteps.openServiceScreen();
        AvailableServicesScreenSteps.selectServices(MonitoringDataUtils.getTestSerivceData());
        workOrderId = WorkOrderSteps.saveWorkOrder();
        GeneralSteps.pressBackButton();
    }

    @BeforeMethod
    public void beforeMethod() {
        HomeScreenSteps.openWorkQueue();
        MonitorSteps.changeLocation("automationMonitoring");
        MonitorSteps.clearAllFilters();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserSeesWarningMessageIfChosenLocationIsEmpty(String rowID,
                                                                    String description, JSONObject testData) {
        MonitorSteps.changeLocation("Another Location");
        MonitorSteps.verifyRepairOrderListIsEmpty();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanUseSearchFilter(String rowID,
                                             String description, JSONObject testData) {
        MonitorSteps.openSearchFilters();
        MonitorSearchSteps.searchByText("NON_EXISTING_REPAIR_ORDER");
        MonitorSearchSteps.search();
        MonitorSearchSteps.verifySearchResultsAreEmpty();
        GeneralSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanUseOrderFlagFilter(String rowID,
                                          String description, JSONObject testData) {
        MonitorSearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorSteps.setRepairOrderFlag(workOrderId, RepairOrderFlag.GREEN);
        MonitorSearchSteps.searchByFlag(RepairOrderFlag.GREEN);
        MonitorSteps.verifyOrderFlag(workOrderId, RepairOrderFlag.GREEN);
        MonitorSteps.setRepairOrderFlag(workOrderId, RepairOrderFlag.YELLOW);
        MonitorSearchSteps.searchByFlag(RepairOrderFlag.YELLOW);
        MonitorSteps.verifyOrderFlag(workOrderId, RepairOrderFlag.YELLOW);
        GeneralSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanUsePriorityFilter(String rowID,
                                         String description, JSONObject testData) {
        MonitorSearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorSteps.openMenu(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        EditOrderSteps.switchToInfo();
        EditOrderSteps.setOrderPriority(OrderPriority.HIGH);
        GeneralSteps.pressBackButton();
        MonitorSteps.clearAllFilters();
        MonitorSearchSteps.searchByPriority(OrderPriority.HIGH);
        MonitorSteps.verifyRepairOrderPresentInList(workOrderId);
        GeneralSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanSearchByDepartment(String rowID,
                                          String description, JSONObject testData) {
        MonitorSearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorSearchSteps.searchByDepartment("Default");
        MonitorSteps.verifyRepairOrderPresentInList(workOrderId);
        GeneralSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanSearchByPhase(String rowID,
                                     String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        OrderPhaseDto expectedOrderInfo = workOrderData.getMonitoring().getOrderPhaseDto();

        MonitorSearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorSearchSteps.searchByPhase(expectedOrderInfo.getPhaseName());
        MonitorSteps.verifyRepairOrderPresentInList(workOrderId);
        GeneralSteps.pressBackButton();
    }
}
