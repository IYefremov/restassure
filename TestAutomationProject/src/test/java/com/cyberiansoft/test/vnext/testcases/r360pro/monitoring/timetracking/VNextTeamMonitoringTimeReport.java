package com.cyberiansoft.test.vnext.testcases.r360pro.monitoring.timetracking;

import com.cyberiansoft.test.baseutils.MonitoringDataUtils;
import com.cyberiansoft.test.dataclasses.ServiceData;
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
import com.cyberiansoft.test.vnext.validations.MenuValidations;
import com.cyberiansoft.test.vnext.validations.TimeReportScreenVerifications;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamMonitoringTimeReport extends BaseTestClass {
    private String workOrderId = "";

    @BeforeClass(description = "Team Monitoring Time Report")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getMonitoringTimeReportDataPath();
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
    public void userCanSeeTimeReportOnServiceLevel(String rowID,
                                                   String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        ServiceData serviceDto = workOrderData.getServiceData();

        MonitorSteps.editOrder(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        EditOrderSteps.openServiceMenu(serviceDto);
        MenuValidations.menuItemShouldBeEnabled(MenuItems.TIME_REPORT, false);
        MenuSteps.selectMenuItem(MenuItems.START);
        GeneralSteps.confirmDialog();
        EditOrderSteps.openServiceMenu(serviceDto);
        MenuSteps.selectMenuItem(MenuItems.TIME_REPORT);
        TimeReportScreenVerifications.startDateShouldBePresent(true);
        TimeReportScreenVerifications.endDateShouldBePresent(false);
        ScreenNavigationSteps.pressBackButton();
        EditOrderSteps.openServiceMenu(serviceDto);
        MenuSteps.selectMenuItem(MenuItems.STOP);
        GeneralSteps.confirmDialog();
        EditOrderSteps.openServiceMenu(serviceDto);
        MenuSteps.selectMenuItem(MenuItems.TIME_REPORT);
        TimeReportScreenVerifications.startDateShouldBePresent(true);
        TimeReportScreenVerifications.endDateShouldBePresent(true);
        ScreenNavigationSteps.pressBackButton();
        WizardScreenSteps.saveAction();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanSeeTimeReportOnPhaseLevel(String rowID,
                                                 String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        OrderPhaseDto phaseDto = workOrderData.getMonitoring().getOrderPhaseDto();

        MonitorSteps.editOrder(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        EditOrderSteps.openPhaseMenu(phaseDto);
        MenuValidations.menuItemShouldBeEnabled(MenuItems.TIME_REPORT, false);
        MenuSteps.selectMenuItem(MenuItems.START);
        GeneralSteps.confirmDialog();
        EditOrderSteps.openPhaseMenu(phaseDto);
        MenuSteps.selectMenuItem(MenuItems.TIME_REPORT);
        TimeReportScreenVerifications.startDateShouldBePresent(true);
        TimeReportScreenVerifications.endDateShouldBePresent(false);
        ScreenNavigationSteps.pressBackButton();
        EditOrderSteps.openPhaseMenu(phaseDto);
        MenuSteps.selectMenuItem(MenuItems.STOP);
        GeneralSteps.confirmDialog();
        EditOrderSteps.openPhaseMenu(phaseDto);
        MenuSteps.selectMenuItem(MenuItems.TIME_REPORT);
        TimeReportScreenVerifications.startDateShouldBePresent(true);
        TimeReportScreenVerifications.endDateShouldBePresent(true);
        ScreenNavigationSteps.pressBackButton();
        WizardScreenSteps.saveAction();
        ScreenNavigationSteps.pressBackButton();
    }
}
