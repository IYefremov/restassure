package com.cyberiansoft.test.vnext.testcases.r360pro.monitoring.timetracking;

import com.cyberiansoft.test.baseutils.MonitoringDataUtils;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.ServiceStatus;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.dto.OrderPhaseDto;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.monitoring.EditOrderSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.MonitorSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamMonitoringTimetracking extends BaseTestCaseTeamEditionRegistration {
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanStartTimeTrackingOnService(String rowID,
                                                  String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        ServiceData serviceDto = workOrderData.getServiceData();

        MonitorSteps.editOrder(workOrderId);
        EditOrderSteps.openElementMenu(serviceDto.getServiceName());
        MenuSteps.selectMenuItem(MenuItems.START);
        GeneralSteps.confirmDialog();
        serviceDto.setServiceStatus(ServiceStatus.STARTED);
        EditOrderSteps.verifyElementStatus(serviceDto);
        EditOrderSteps.verifyTimeTrackingShouldBeStarted(serviceDto.getServiceName(), true);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class
            , dependsOnMethods = "userCanStartTimeTrackingOnService")
    public void userCanStopTimeTrackingOnService(String rowID,
                                                 String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        ServiceData serviceDto = workOrderData.getServiceData();

        EditOrderSteps.openElementMenu(serviceDto.getServiceName());
        MenuSteps.selectMenuItem(MenuItems.STOP);
        GeneralSteps.confirmDialog();
        serviceDto.setServiceStatus(ServiceStatus.STARTED);
        EditOrderSteps.verifyElementStatus(serviceDto);
        EditOrderSteps.verifyTimeTrackingShouldBeStarted(serviceDto.getServiceName(), false);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class,dependsOnMethods = "userCanStopTimeTrackingOnService")
    public void userCanStartTimeTrackingOnPhase(String rowID,
                                                String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        OrderPhaseDto phaseDto = workOrderData.getMonitoring().getOrderPhaseDto();

        EditOrderSteps.openElementMenu(phaseDto);
        MenuSteps.selectMenuItem(MenuItems.START);
        GeneralSteps.confirmDialog();
        EditOrderSteps.verifyTimeTrackingShouldBeStarted(phaseDto.getPhaseName(), true);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class
            , dependsOnMethods = "userCanStartTimeTrackingOnPhase")
    public void userCanStopTimeTrackingOnPhase(String rowID,
                                               String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        OrderPhaseDto phaseDto = workOrderData.getMonitoring().getOrderPhaseDto();

        EditOrderSteps.openElementMenu(phaseDto);
        MenuSteps.selectMenuItem(MenuItems.STOP);
        GeneralSteps.confirmDialog();
        EditOrderSteps.verifyTimeTrackingShouldBeStarted(phaseDto.getPhaseName(), false);
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
    }
}
