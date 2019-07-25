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
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import com.cyberiansoft.test.vnext.validations.EditOrderScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamMonitoringResetStartDate extends BaseTestCaseTeamEditionRegistration {
    private String inspectionId = "";
    private String workOrderId = "";

    @BeforeClass(description = "Team Monitoring Basic Flow Test")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getMonitoringTimeReportDataPath();
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
    public void userCanResetStartDateOnServiceLevel(String rowID,
                                                    String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        ServiceData serviceDto = workOrderData.getServiceData();

        MonitorSteps.editOrder(workOrderId);
        EditOrderScreenValidations.elementShouldHaveStartDate(serviceDto.getServiceName(), false);//add open
        EditOrderSteps.openElementMenu(serviceDto.getServiceName());
        MenuSteps.selectMenuItem(MenuItems.START);
        GeneralSteps.confirmDialog();
        EditOrderScreenValidations.elementShouldHaveStartDate(serviceDto.getServiceName(), true);
        EditOrderScreenValidations.validateElementState(serviceDto.getServiceName(), ServiceStatus.STARTED);
        EditOrderSteps.openElementMenu(serviceDto.getServiceName());
        MenuSteps.selectMenuItem(MenuItems.RESET_START_DATE);
        GeneralSteps.confirmDialog();
        EditOrderScreenValidations.elementShouldHaveStartDate(serviceDto.getServiceName(), false);
        EditOrderScreenValidations.validateElementState(serviceDto.getServiceName(), ServiceStatus.ACTIVE);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanResetStartDateOnPhaseLevel(String rowID,
                                                  String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        OrderPhaseDto phaseDto = workOrderData.getMonitoring().getOrderPhaseDto();

        MonitorSteps.editOrder(workOrderId);
        EditOrderScreenValidations.elementShouldHaveStartDate(phaseDto.getPhaseName(), false);
        EditOrderSteps.openElementMenu(phaseDto);
        MenuSteps.selectMenuItem(MenuItems.START);
        GeneralSteps.confirmDialog();
        EditOrderScreenValidations.elementShouldHaveStartDate(phaseDto.getPhaseName(), true);
        EditOrderScreenValidations.validateElementState(phaseDto.getPhaseName(), ServiceStatus.STARTED);
        EditOrderSteps.openElementMenu(phaseDto.getPhaseName());
        MenuSteps.selectMenuItem(MenuItems.RESET_START_DATE);
        GeneralSteps.confirmDialog();
        EditOrderScreenValidations.elementShouldHaveStartDate(phaseDto.getPhaseName(), false);
        EditOrderScreenValidations.validateElementState(phaseDto.getPhaseName(), ServiceStatus.ACTIVE);
    }
}