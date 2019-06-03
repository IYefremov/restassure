package com.cyberiansoft.test.vnext.testcases.r360pro.monitoring;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.HomeScreen;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.dto.OrderInfoDto;
import com.cyberiansoft.test.vnext.dto.OrderPhaseDto;
import com.cyberiansoft.test.vnext.dto.RepairOrderDto;
import com.cyberiansoft.test.vnext.enums.MenuItems;
import com.cyberiansoft.test.vnext.enums.PhaseName;
import com.cyberiansoft.test.vnext.enums.RepairOrderFlag;
import com.cyberiansoft.test.vnext.enums.RepairOrderStatus;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.monitoring.EditOrderSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.MonitorMenuSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.MonitorSearchSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.MonitorSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VNextTeamMonitoringBaseCase extends BaseTestCaseTeamEditionRegistration {
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
        GeneralSteps.pressBackButton();
        GeneralSteps.pressBackButton();
    }

    @BeforeMethod
    public void beforeEachMethod() {
        HomeScreenSteps.openInspections();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_MONITORING);
        WorkOrderSteps.openServiceScreen();
        AvailableServicesScreenSteps.selectServices(this.getTestSerivceData());
        workOrderId = WorkOrderSteps.saveWorkOrder();
        GeneralSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanCompleteBasicROFlow(String rowID,
                                           String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        RepairOrderDto repairOrderDto = workOrderData.getMonitoring().getRepairOrderData();

        HomeScreenSteps.openMonitor();
        MonitorSearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
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
        MonitorSearchSteps.selectStatus(RepairOrderStatus.COMPLETED_ALL);
        MonitorSearchSteps.search();
        repairOrderDto.setCompletePercentage("100%");
        repairOrderDto.setPhaseName(PhaseName.COMPLETED);
        MonitorSteps.verifyRepairOrderValues(workOrderId,
                repairOrderDto);
        GeneralSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanChangeFlagsOfWorkOrder(String rowID,
                                              String description, JSONObject testData) {
        HomeScreenSteps.openMonitor();
        MonitorSearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorSteps.setRepairOrderFlag(workOrderId, RepairOrderFlag.GREEN);
        MonitorSteps.verifyOrderFlag(workOrderId, RepairOrderFlag.GREEN);
        MonitorSteps.setRepairOrderFlag(workOrderId, RepairOrderFlag.YELLOW);
        MonitorSteps.verifyOrderFlag(workOrderId, RepairOrderFlag.YELLOW);
        GeneralSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void  userCanAddNotesToRepairOrder(String rowID,
                                             String description, JSONObject testData) {
        String noteText = UUID.randomUUID().toString();
        HomeScreenSteps.openMonitor();
        MonitorSearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorSteps.openMenu(workOrderId);
        MonitorMenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.addNewNote(noteText);
        NotesSteps.verifyNoteIsPresent(noteText);
        GeneralSteps.pressBackButton();
        GeneralSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanCompleteOrderViaEdit(String rowID,
                                            String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        OrderPhaseDto phaseDto = workOrderData.getMonitoring().getOrderPhaseDto();
        RepairOrderDto repairOrderDto = workOrderData.getMonitoring().getRepairOrderData();

        HomeScreenSteps.openMonitor();
        MonitorSearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorSteps.openMenu(workOrderId);
        MonitorMenuSteps.selectMenuItem(MenuItems.EDIT);

        EditOrderSteps.verifyPhaseSelected(phaseDto);
        EditOrderSteps.completePhase(phaseDto);
        phaseDto.setStatus(PhaseName.COMPLETED);
        EditOrderSteps.verifyPhaseSelected(phaseDto);
        GeneralSteps.pressBackButton();
        MonitorSearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.COMPLETED_ALL);
        repairOrderDto.setPhaseName(PhaseName.COMPLETED);
        MonitorSteps.verifyRepairOrderValues(workOrderId,
                repairOrderDto);
        GeneralSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanViewOrderInfoViaEdit(String rowID,
                                            String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        OrderInfoDto expectedOrderInfo = workOrderData.getMonitoring().getOrderInfoDto();

        HomeScreenSteps.openMonitor();
        MonitorSearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorSteps.openMenu(workOrderId);
        MonitorMenuSteps.selectMenuItem(MenuItems.EDIT);
        EditOrderSteps.switchToInfo();
        expectedOrderInfo.setStartDate(LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM d, yyyy")));
        EditOrderSteps.verifyOrderInfo(expectedOrderInfo);
        GeneralSteps.pressBackButton();
        GeneralSteps.pressBackButton();
    }

    //TODO: Temp solution to provide test data to @BeforeMethod method
    private List<ServiceData> getTestSerivceData() {
        List<ServiceData> serviceData = new ArrayList<>();
        ServiceData service = new ServiceData();
        service.setServiceName("Expenses_money (AM)");
        serviceData.add(service);
        return serviceData;
    }
}
