package com.cyberiansoft.test.vnext.testcases.r360pro.monitoring;

import com.cyberiansoft.test.baseutils.MonitoringDataUtils;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.ServiceStatus;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.dto.RepairOrderDto;
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
import org.testng.annotations.Test;

public class VNextTeamMonitoringCalculations extends BaseTestCaseTeamEditionRegistration {
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
        AvailableServicesScreenSteps.selectPartService("Engine part", "Filters", "Engine Oil Filter", "Main");
        AvailableServicesScreenSteps.selectServices(MonitoringDataUtils.getTestSerivceData());
        workOrderId = WorkOrderSteps.saveWorkOrder();
        GeneralSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void partServiceShouldNotAffectROCompleteness(String rowID,
                                                         String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        RepairOrderDto repairOrderDto = workOrderData.getMonitoring().getRepairOrderData();
        ServiceData serviceData = workOrderData.getServiceData();

        HomeScreenSteps.openWorkQueue();
        MonitorSteps.changeLocation("automationMonitoring");
        MonitorSearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorSteps.verifyRepairOrderValues(workOrderId, repairOrderDto);
        MonitorSteps.openMenu(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        EditOrderSteps.switchToParts();
        EditOrderSteps.openPhaseMenu(serviceData.getServiceName());
        MenuSteps.selectMenuItem(MenuItems.CHANGE_STATUS);
        MenuSteps.selectStatus(ServiceStatus.ORDERED);
        EditOrderSteps.openPhaseMenu(serviceData.getServiceName());
        MenuSteps.selectMenuItem(MenuItems.CHANGE_STATUS);
        MenuSteps.selectStatus(ServiceStatus.RECEIVED);
        GeneralSteps.pressBackButton();
        MonitorSteps.verifyRepairOrderValues(workOrderId, repairOrderDto);
    }
}
