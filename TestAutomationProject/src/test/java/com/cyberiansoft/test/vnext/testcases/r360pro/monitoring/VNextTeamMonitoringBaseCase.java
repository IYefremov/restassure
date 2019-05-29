package com.cyberiansoft.test.vnext.testcases.r360pro.monitoring;

import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.dto.RepairOrderDto;
import com.cyberiansoft.test.vnext.enums.MenuItems;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.monitoring.MonitorMenuSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.MonitorSearchSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.MonitorSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamMonitoringBaseCase extends BaseTestCaseTeamEditionRegistration {
    private String inspectionId = "";
    private String workOrderId = "";

    @BeforeClass(description = "Team Monitoring Basic Flow Test")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getMonitoringBaseCaseDataPath();
        HomeScreenSteps.openCreateNewInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        inspectionId = InspectionSteps.saveInspection();
        GeneralSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanCompleteBasicROFlow(String rowID,
                                           String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        RepairOrderDto repairOrderDto =  workOrderData.getMonitoring().getRepairOrderData();

        HomeScreenSteps.openInspections();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.approveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_MONITORING);
        WorkOrderSteps.openServiceScreen();
        AvailableServicesScreenSteps.selectServices(workOrderData.getServicesList());
        workOrderId = WorkOrderSteps.saveWorkOrder();
        GeneralSteps.pressBackButton();

        HomeScreenSteps.openMonitor();
        MonitorSteps.changeLocation(workOrderData.getMonitoring().getLocation());
        MonitorSteps.openSearchFilters();
        MonitorSearchSteps.searchByText(workOrderId);
        MonitorSearchSteps.selectStatus("All");
        MonitorSearchSteps.search();
        MonitorSteps.verifyRepairOrderPresentInList(workOrderId);
        MonitorSteps.verifyRepairOrderValues(workOrderId,
                repairOrderDto);
        MonitorSteps.openMenu(workOrderId);
        MonitorMenuSteps.selectMenuItem(MenuItems.START);
        MonitorMenuSteps.selectServices(workOrderData.getServicesList());
        MonitorSteps.openMenu(workOrderId);
        MonitorMenuSteps.selectMenuItem(MenuItems.COMPLETE);
        MonitorMenuSteps.selectServices(workOrderData.getServicesList());
        MonitorSteps.openSearchFilters();
        MonitorSearchSteps.selectStatus("Completed - All");
        MonitorSearchSteps.search();
        repairOrderDto.setCompletePercentage("100%");
        repairOrderDto.setPhaseName("Completed");
        MonitorSteps.verifyRepairOrderValues(workOrderId,
                repairOrderDto);
    }
}
