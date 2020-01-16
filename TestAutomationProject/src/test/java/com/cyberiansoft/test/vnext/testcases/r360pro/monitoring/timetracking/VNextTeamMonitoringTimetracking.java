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
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.PhaseScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamMonitoringTimetracking extends BaseTestClass {
    private String workOrderId = "";

    @BeforeClass(description = "Team Monitoring Time Tracking")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getMonitoringBaseCaseDataPath();
    }

    public void createWorkOrder() {
        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        final String inspectionId = InspectionSteps.saveInspection();
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
    public void userCanStartAndStopServiceTime(String rowID,
                                                  String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        ServiceData serviceDto = workOrderData.getServiceData();

        createWorkOrder();
        MonitorSteps.editOrder(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        EditOrderSteps.openServiceMenu(serviceDto);
        MenuSteps.selectMenuItem(MenuItems.START);
        GeneralSteps.confirmDialog();
        serviceDto.setServiceStatus(ServiceStatus.STARTED);
        PhaseScreenValidations.validateServiceStatus(serviceDto);
        PhaseScreenValidations.verifyTimetrachingShoudBeStartedOnSerivce(serviceDto, true);

        EditOrderSteps.openServiceMenu(serviceDto);
        MenuSteps.selectMenuItem(MenuItems.STOP);
        GeneralSteps.confirmDialog();
        serviceDto.setServiceStatus(ServiceStatus.STARTED);
        PhaseScreenValidations.validateServiceStatus(serviceDto);
        PhaseScreenValidations.verifyTimetrachingShoudBeStartedOnSerivce(serviceDto, false);
        WizardScreenSteps.saveAction();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanStartAndStopPhaseTime(String rowID,
                                                String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        OrderPhaseDto phaseDto = workOrderData.getMonitoring().getOrderPhaseDto();

        createWorkOrder();
        MonitorSteps.editOrder(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        EditOrderSteps.openPhaseMenu(phaseDto);
        MenuSteps.selectMenuItem(MenuItems.START);
        GeneralSteps.confirmDialog();
        PhaseScreenValidations.verifyTimetrachingShoudBeStartedOnPhase(phaseDto, true);

        EditOrderSteps.openPhaseMenu(phaseDto);
        MenuSteps.selectMenuItem(MenuItems.STOP);
        GeneralSteps.confirmDialog();
        PhaseScreenValidations.verifyTimetrachingShoudBeStartedOnPhase(phaseDto, false);
        WizardScreenSteps.saveAction();
        ScreenNavigationSteps.pressBackButton();
    }
}
