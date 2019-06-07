package com.cyberiansoft.test.vnext.testcases.r360pro.monitoring;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.dto.OrderPhaseDto;
import com.cyberiansoft.test.vnext.enums.MenuItems;
import com.cyberiansoft.test.vnext.enums.PhaseName;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.monitoring.EditOrderSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.MonitorSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.ProblemReportingSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class VNextTeamMonitoringProblemReporting extends BaseTestCaseTeamEditionRegistration {
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
        AvailableServicesScreenSteps.selectServices(this.getTestSerivceData());
        workOrderId = WorkOrderSteps.saveWorkOrder();
        GeneralSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 10)
    public void userCanReportProblemOnPhaseLevel(String rowID,
                                                 String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        OrderPhaseDto phaseDto = workOrderData.getMonitoring().getOrderPhaseDto();

        MonitorSteps.editOrder(workOrderId);
        EditOrderSteps.openPhaseMenu(phaseDto);
        MenuSteps.selectMenuItem(MenuItems.REPORT_PROBLEM);
        ProblemReportingSteps.setProblemReason("PROBLEM_REASON");
        phaseDto.setStatus(PhaseName.PROBLEM);
        EditOrderSteps.verifyPhaseStatus(phaseDto);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, dependsOnMethods = "userCanReportProblemOnPhaseLevel", priority = 20)
    public void userCanResolveProblemOnPhaseLevel(String rowID,
                                                  String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        OrderPhaseDto phaseDto = workOrderData.getMonitoring().getOrderPhaseDto();

        EditOrderSteps.openPhaseMenu(phaseDto);
        MenuSteps.selectMenuItem(MenuItems.RESOLVE_PROBLEM);
        ProblemReportingSteps.resolveProblem();
        phaseDto.setStatus(PhaseName.ACTIVE);
        EditOrderSteps.verifyPhaseStatus(phaseDto);
        GeneralSteps.pressBackButton();
        GeneralSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 30)
    public void userCanReportProblemOnServiceLevel(String rowID,
                                                   String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        OrderPhaseDto phaseDto = workOrderData.getMonitoring().getOrderPhaseDto();
        ServiceData serviceDto = workOrderData.getServiceData();

        MonitorSteps.editOrder(workOrderId);
        EditOrderSteps.expandPhase(phaseDto);
        EditOrderSteps.openServiceMenu(serviceDto);
        MenuSteps.selectMenuItem(MenuItems.REPORT_PROBLEM);
        ProblemReportingSteps.setProblemReason("PROBLEM_REASON");
        serviceDto.setServiceStatus("PROBLEM");
        EditOrderSteps.verifyServiceStatus(serviceDto);
        GeneralSteps.pressBackButton();
        phaseDto.setStatus(PhaseName.PROBLEM);
        EditOrderSteps.verifyPhaseStatus(phaseDto);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class
            , dependsOnMethods = "userCanReportProblemOnServiceLevel", priority = 40)
    public void userCanResolveProblemOnServiceLevel(String rowID,
                                                    String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        OrderPhaseDto phaseDto = workOrderData.getMonitoring().getOrderPhaseDto();
        ServiceData serviceDto = workOrderData.getServiceData();

        EditOrderSteps.expandPhase(phaseDto);
        EditOrderSteps.openServiceMenu(serviceDto);
        MenuSteps.selectMenuItem(MenuItems.RESOLVE_PROBLEM);
        ProblemReportingSteps.resolveProblem();
        serviceDto.setServiceStatus("ACTIVE");
        EditOrderSteps.verifyServiceStatus(serviceDto);
        GeneralSteps.pressBackButton();
        phaseDto.setStatus(PhaseName.ACTIVE);
        EditOrderSteps.verifyPhaseStatus(phaseDto);
        GeneralSteps.pressBackButton();
        GeneralSteps.pressBackButton();
    }

    //TODO: Temp solution to provide test data to @BeforeMethod method
    private List<ServiceData> getTestSerivceData() {
        List<ServiceData> serviceData = new ArrayList<>();
        ServiceData service = new ServiceData();
        service.setServiceName("Expenses_money (AM)");
        serviceData.add(service);
        service = new ServiceData();
        service.setServiceName("Labor AM");
        serviceData.add(service);
        return serviceData;
    }
}
