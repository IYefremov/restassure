package com.cyberiansoft.test.vnext.testcases.r360pro.monitoring;

import com.cyberiansoft.test.baseutils.MonitoringDataUtils;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.ServiceStatus;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.dto.OrderPhaseDto;
import com.cyberiansoft.test.vnext.enums.PhaseName;
import com.cyberiansoft.test.vnext.enums.ScreenType;
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
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServices(MonitoringDataUtils.getTestSerivceData());
        workOrderId = WorkOrderSteps.saveWorkOrder();
        GeneralSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanReportProblemOnPhaseLevel(String rowID,
                                                 String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        OrderPhaseDto phaseDto = workOrderData.getMonitoring().getOrderPhaseDto();

        MonitorSteps.editOrder(workOrderId);
        EditOrderSteps.openElementMenu(phaseDto);
        MenuSteps.selectMenuItem(MenuItems.START);
        GeneralSteps.confirmDialog();
        EditOrderSteps.openElementMenu(phaseDto);
        MenuSteps.selectMenuItem(MenuItems.REPORT_PROBLEM);
        ProblemReportingSteps.setProblemReason(phaseDto.getProblemReason());
        phaseDto.setStatus(PhaseName.PROBLEM);
        EditOrderSteps.verifyElementStatus(phaseDto);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, dependsOnMethods = "userCanReportProblemOnPhaseLevel")
    public void userCanResolveProblemOnPhaseLevel(String rowID,
                                                  String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        OrderPhaseDto phaseDto = workOrderData.getMonitoring().getOrderPhaseDto();

        EditOrderSteps.openElementMenu(phaseDto);
        MenuSteps.selectMenuItem(MenuItems.RESOLVE_PROBLEM);
        ProblemReportingSteps.resolveProblem();
        phaseDto.setStatus(PhaseName.ACTIVE);
        EditOrderSteps.verifyElementStatus(phaseDto);
        GeneralSteps.pressBackButton();
        GeneralSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, dependsOnMethods = "userCanResolveProblemOnPhaseLevel")
    public void userCanReportProblemOnServiceLevel(String rowID,
                                                   String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        ServiceData serviceDto = workOrderData.getServiceData();

        MonitorSteps.editOrder(workOrderId);
        EditOrderSteps.openElementMenu(serviceDto.getServiceName());
        MenuSteps.selectMenuItem(MenuItems.REPORT_PROBLEM);
        ProblemReportingSteps.setProblemReason(serviceDto.getProblemReason());
        serviceDto.setServiceStatus(ServiceStatus.PROBLEM);
        EditOrderSteps.verifyElementStatus(serviceDto);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class
            , dependsOnMethods = "userCanReportProblemOnServiceLevel")
    public void userCanResolveProblemOnServiceLevel(String rowID,
                                                    String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        ServiceData serviceDto = workOrderData.getServiceData();

        EditOrderSteps.openElementMenu(serviceDto.getServiceName());
        MenuSteps.selectMenuItem(MenuItems.RESOLVE_PROBLEM);
        ProblemReportingSteps.resolveProblem();
        serviceDto.setServiceStatus(ServiceStatus.STARTED);
        EditOrderSteps.verifyElementStatus(serviceDto);
        GeneralSteps.pressBackButton();
        GeneralSteps.pressBackButton();
    }
}
