package com.cyberiansoft.test.vnext.testcases.r360pro.monitoring.timetracking;

import com.cyberiansoft.test.baseutils.MonitoringDataUtils;
import com.cyberiansoft.test.dataclasses.Employee;
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
import com.cyberiansoft.test.vnext.steps.monitoring.ProblemReportingSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import com.cyberiansoft.test.vnext.validations.MenuValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamMonitoringTimetrackingVisibility extends BaseTestCaseTeamEditionRegistration {
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
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void anotherUserCanSeeStartOnStartedService(String rowID,
                                                       String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        ServiceData serviceDto = workOrderData.getServiceData();
        Employee secondEmployee = new Employee();
        secondEmployee.setEmployeeFirstName("Test");
        secondEmployee.setEmployeeLastName("Test");

        MonitorSteps.editOrder(workOrderId);
        EditOrderSteps.openElementMenu(serviceDto.getServiceName());
        MenuSteps.selectMenuItem(MenuItems.START);
        GeneralSteps.confirmDialog();
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
        HomeScreenSteps.logOut();
        GeneralSteps.logIn(secondEmployee);
        MonitorSteps.editOrder(workOrderId);
        EditOrderSteps.openElementMenu(serviceDto.getServiceName());
        MenuValidations.menuItemShouldBeEnabled(MenuItems.START, true);
        MenuSteps.closeMenu();
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
        HomeScreenSteps.logOut();
        GeneralSteps.logIn(employee);
        MonitorSteps.editOrder(workOrderId);
        EditOrderSteps.openElementMenu(serviceDto.getServiceName());
        MenuSteps.selectMenuItem(MenuItems.STOP);
        GeneralSteps.confirmDialog();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, dependsOnMethods = "anotherUserCanSeeStartOnStartedService")
    public void verifyStartStopVisibleOnlyInActiveState(String rowID,
                                                        String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        ServiceData serviceDto = workOrderData.getServiceData();
        OrderPhaseDto phaseDto = workOrderData.getMonitoring().getOrderPhaseDto();

        EditOrderSteps.openElementMenu(serviceDto.getServiceName());
        MenuSteps.selectMenuItem(MenuItems.REPORT_PROBLEM);
        ProblemReportingSteps.setProblemReason(phaseDto.getProblemReason());
        EditOrderSteps.openElementMenu(serviceDto.getServiceName());
        MenuValidations.menuItemShouldBeEnabled(MenuItems.START, false);
        MenuValidations.menuItemShouldBeEnabled(MenuItems.STOP, false);
        MenuSteps.selectMenuItem(MenuItems.COMPLETE);
        GeneralSteps.confirmDialog();
        MonitorSteps.toggleFocusMode(MenuItems.FOCUS_MODE_ON);
        EditOrderSteps.openElementMenu(serviceDto.getServiceName());
        MenuValidations.menuItemShouldBeEnabled(MenuItems.START, false);
        MenuValidations.menuItemShouldBeEnabled(MenuItems.STOP, false);
        MenuSteps.closeMenu();
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
    }
}
