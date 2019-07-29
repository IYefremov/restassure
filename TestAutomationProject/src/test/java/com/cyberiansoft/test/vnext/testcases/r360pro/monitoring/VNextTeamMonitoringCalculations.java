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
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.monitoring.EditOrderSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.MonitorSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.SearchSteps;
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

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.approveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        PartServiceSteps.selectPartService("Engine part", null, "Filters", "Engine Oil Filter", "Main");
        PartServiceSteps.confirmPartInfo();
        AvailableServicesScreenSteps.selectServices(MonitoringDataUtils.getTestSerivceData());
        workOrderId = WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void partServiceShouldNotAffectROCompleteness(String rowID,
                                                         String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        RepairOrderDto repairOrderDto = workOrderData.getMonitoring().getRepairOrderData();
        ServiceData serviceData = workOrderData.getServiceData();

        HomeScreenSteps.openWorkQueue();
        MonitorSteps.changeLocation("automationMonitoring");
        SearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorSteps.verifyRepairOrderValues(workOrderId, repairOrderDto);
        MonitorSteps.openMenu(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        EditOrderSteps.switchToParts();
        EditOrderSteps.openElementMenu(serviceData.getServiceName());
        MenuSteps.selectMenuItem(MenuItems.CHANGE_STATUS);
        MenuSteps.selectStatus(ServiceStatus.ORDERED);
        EditOrderSteps.openElementMenu(serviceData.getServiceName());
        MenuSteps.selectMenuItem(MenuItems.CHANGE_STATUS);
        MenuSteps.selectStatus(ServiceStatus.RECEIVED);
        ScreenNavigationSteps.pressBackButton();
        MonitorSteps.verifyRepairOrderValues(workOrderId, repairOrderDto);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class
            , dependsOnMethods = "partServiceShouldNotAffectROCompleteness")
    public void skippedServicesShouldNotAffectCalculation(String rowID,
                                                          String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        RepairOrderDto repairOrderDto = workOrderData.getMonitoring().getRepairOrderData();
        ServiceData serviceData = workOrderData.getServiceData();

        MonitorSteps.openMenu(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        EditOrderSteps.openElementMenu(serviceData.getServiceName());
        MenuSteps.selectMenuItem(MenuItems.START);
        GeneralSteps.confirmDialog();
        EditOrderSteps.openElementMenu(serviceData.getServiceName());
        MenuSteps.selectMenuItem(MenuItems.CHANGE_STATUS);
        MenuSteps.selectStatus(ServiceStatus.SKIPPED);
        ScreenNavigationSteps.pressBackButton();
        MonitorSteps.verifyRepairOrderValues(workOrderId, repairOrderDto);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class
            , dependsOnMethods = "skippedServicesShouldNotAffectCalculation")
    public void refusedServicesShouldNotAffectCalculation(String rowID,
                                                          String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        RepairOrderDto repairOrderDto = workOrderData.getMonitoring().getRepairOrderData();
        ServiceData serviceData = workOrderData.getServiceData();

        MonitorSteps.openMenu(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        MonitorSteps.toggleFocusMode(MenuItems.FOCUS_MODE_ON);
        EditOrderSteps.openElementMenu(serviceData.getServiceName());
        MenuSteps.selectMenuItem(MenuItems.CHANGE_STATUS);
        MenuSteps.selectStatus(ServiceStatus.REFUSED);
        ScreenNavigationSteps.pressBackButton();
        MonitorSteps.verifyRepairOrderValues(workOrderId, repairOrderDto);
        ScreenNavigationSteps.pressBackButton();
    }
}
