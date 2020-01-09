package com.cyberiansoft.test.vnext.testcases.r360pro.monitoring.problemreporting;

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
import com.cyberiansoft.test.vnext.steps.monitoring.PhaseDetailsSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.ProblemReportingSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.PhaseScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamProblemEnforcement extends BaseTestClass {
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
        AvailableServicesScreenSteps.selectService("A new demo service");
        workOrderId = WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifySystemEnforcesUserToResolveProblemBeforeServiceCompleteServiceLevelOnly(String rowID,
                                                                                              String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        ServiceData serviceDto = workOrderData.getServiceData();

        MonitorSteps.editOrder(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        EditOrderSteps.openServiceMenu(serviceDto);
        MenuSteps.selectMenuItem(MenuItems.START);
        GeneralSteps.confirmDialog();
        EditOrderSteps.openServiceMenu(serviceDto);
        MenuSteps.selectMenuItem(MenuItems.REPORT_PROBLEM);
        ProblemReportingSteps.setProblemReason(serviceDto.getProblemReason());
        EditOrderSteps.openServiceMenu(serviceDto);
        MenuSteps.selectMenuItem(MenuItems.COMPLETE);
        GeneralSteps.confirmDialog();
        ProblemReportingSteps.resolveProblem();
        MonitorSteps.toggleFocusMode(MenuItems.FOCUS_MODE_ON);
        serviceDto.setServiceStatus(ServiceStatus.COMPLETED);
        PhaseScreenValidations.validateServiceStatus(serviceDto);
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifySystemEnforcesUserToResolveProblemBeforeServiceCompletePhaseLevelOnly(String rowID,
                                                                                            String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        OrderPhaseDto phaseDto = workOrderData.getMonitoring().getOrderPhaseDto();

        MonitorSteps.editOrder(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        EditOrderSteps.openPhaseMenu(phaseDto);
        MenuSteps.selectMenuItem(MenuItems.START);
        GeneralSteps.confirmDialog();
        EditOrderSteps.openPhaseMenu(phaseDto);
        MenuSteps.selectMenuItem(MenuItems.REPORT_PROBLEM);
        ProblemReportingSteps.setProblemReason(phaseDto.getProblemReason());
        EditOrderSteps.openPhaseMenu(phaseDto);
        MenuSteps.selectMenuItem(MenuItems.COMPLETE);
        GeneralSteps.confirmDialog();
        ProblemReportingSteps.resolveProblem();
        MonitorSteps.toggleFocusMode(MenuItems.FOCUS_MODE_ON);
        phaseDto.setStatus(PhaseName.COMPLETED);
        PhaseScreenValidations.validatePhaseStatus(phaseDto);
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifySystemEnforcesUserToResolveServiceProblemBeforeServiceOrPhaseCompletePhaseAndServiceLevel(String rowID,
                                                                                                                String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        OrderPhaseDto phaseDto = workOrderData.getMonitoring().getOrderPhaseDto();
        ServiceData serviceDto = workOrderData.getServiceData();

        MonitorSteps.editOrder(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        EditOrderSteps.openServiceMenu(serviceDto);
        MenuSteps.selectMenuItem(MenuItems.START);
        GeneralSteps.confirmDialog();
        EditOrderSteps.openServiceMenu(serviceDto);
        MenuSteps.selectMenuItem(MenuItems.REPORT_PROBLEM);
        ProblemReportingSteps.setProblemReason(serviceDto.getProblemReason());
        EditOrderSteps.openPhaseMenu(phaseDto);
        MenuSteps.selectMenuItem(MenuItems.COMPLETE);
        GeneralSteps.confirmDialog();
        PhaseDetailsSteps.selectProblem(serviceDto.getServiceName());
        ProblemReportingSteps.resolveProblem();
        PhaseDetailsSteps.completePhase();
        MonitorSteps.toggleFocusMode(MenuItems.FOCUS_MODE_ON);
        serviceDto.setServiceStatus(ServiceStatus.COMPLETED);
        phaseDto.setStatus(PhaseName.COMPLETED);
        PhaseScreenValidations.validatePhaseStatus(phaseDto);
        PhaseScreenValidations.validateServiceStatus(serviceDto);
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
    }
}
