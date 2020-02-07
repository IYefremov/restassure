package com.cyberiansoft.test.vnext.testcases.r360pro.monitoring;

import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.baseutils.MonitoringDataUtils;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.DateUtils;
import com.cyberiansoft.test.enums.OrderMonitorStatuses;
import com.cyberiansoft.test.enums.OrderPriority;
import com.cyberiansoft.test.enums.TimeFrameValues;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.dto.OrderPhaseDto;
import com.cyberiansoft.test.vnext.enums.RepairOrderStatus;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.monitoring.MonitorSearchSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.MonitorSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.MonitorSearchValidations;
import com.cyberiansoft.test.vnext.validations.MonitorValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;


public class VNextMonitoringSearchFiltersTestCases extends BaseTestClass {

    private String workOrderId = "";

    @BeforeClass(description = "Team Monitoring Basic Flow Test")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getMonitoringSearchFiltersDataPath();
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
    public void verifyUserCanClearCommonFilters(String rowID,
                                            String description, JSONObject testData) {

        LocalDate previousDay = LocalDate.now().minusDays(1);
        LocalDate nextDay = LocalDate.now().plusDays(1);

        HomeScreenSteps.openMonitor();
        MonitorSteps.changeLocation("automationMonitoring");
        SearchSteps.openSearchFilters();
        MonitorSearchSteps.selectTimeFrame(TimeFrameValues.TIMEFRAME_CUSTOM);
        MonitorSearchSteps.selectDateFromAndDateTo(previousDay, nextDay);
        SearchSteps.fillTextSearch(workOrderId);
        MonitorSearchValidations.validateTimeFrameFromValue(CustomDateProvider.getFormattedLocalizedDate(previousDay, DateUtils.SHORT_DATE_FORMAT_WITH_COMMA));
        MonitorSearchValidations.validateTimeFrameToValue(CustomDateProvider.getFormattedLocalizedDate(nextDay, DateUtils.SHORT_DATE_FORMAT_WITH_COMMA));
        SearchSteps.clearFilters();
        MonitorSearchValidations.validateTimeFrameFromValue("");
        MonitorSearchValidations.validateTimeFrameToValue("");
        SearchSteps.search();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserSearchByFromAndTo(String rowID,
                                                String description, JSONObject testData) {

        LocalDate previousDay = LocalDate.now().minusDays(1);
        LocalDate nextDay = LocalDate.now().plusDays(1);

        HomeScreenSteps.openMonitor();
        MonitorSteps.changeLocation("automationMonitoring");
        SearchSteps.openSearchFilters();
        MonitorSearchSteps.selectTimeFrame(TimeFrameValues.TIMEFRAME_CUSTOM);
        MonitorSearchSteps.selectDateFromAndDateTo(previousDay, nextDay);
        SearchSteps.fillTextSearch(workOrderId);
        SearchSteps.search();
        MonitorValidations.verifyRepairOrderPresentInList(workOrderId);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCloseCommonFiltersPopDownWindow(String rowID,
                                            String description, JSONObject testData) {

        HomeScreenSteps.openMonitor();
        MonitorSteps.changeLocation("automationMonitoring");
        SearchSteps.openSearchFilters();
        MonitorSearchValidations.validateCommonFilterPanelVisible(true);
        SearchSteps.clickCommonFiltersToggle();
        MonitorSearchValidations.validateCommonFilterPanelVisible(false);
        SearchSteps.cancelSearch();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanFindTheNecessaryPriority(String rowID,
                                            String description, JSONObject testData) {

        HomeScreenSteps.openMonitor();
        MonitorSteps.changeLocation("automationMonitoring");
        SearchSteps.openSearchFilters();
        SearchSteps.clickPriorityFilter();
        SearchSteps.openSearchMenu();
        SearchSteps.fillTextSearch(OrderPriority.NORMAL.getValue());
        MonitorSearchValidations.validatePriorityListElementNumber(1);
        SearchSteps.selectPriority(OrderPriority.NORMAL);
        MonitorSearchValidations.validatePriorityValue(OrderPriority.NORMAL);
        SearchSteps.fillTextSearch(workOrderId);
        SearchSteps.search();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanFindTheNecessaryStatus(String rowID,
                                                String description, JSONObject testData) {

        HomeScreenSteps.openMonitor();
        MonitorSteps.changeLocation("automationMonitoring");
        SearchSteps.openSearchFilters();
        SearchSteps.clickStatusFilter();
        SearchSteps.openSearchMenu();
        SearchSteps.fillTextSearch(RepairOrderStatus.IN_PROGRESS_ACTIVE.getStatusString());
        MonitorSearchValidations.validatePriorityListElementNumber(1);
        SearchSteps.selectStatus(RepairOrderStatus.IN_PROGRESS_ACTIVE);
        MonitorSearchValidations.validateStatusValue(RepairOrderStatus.IN_PROGRESS_ACTIVE);
        SearchSteps.fillTextSearch(workOrderId);
        SearchSteps.search();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanFindTheNecessaryPhase(String rowID,
                                              String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        OrderPhaseDto expectedOrderInfo = workOrderData.getMonitoring().getOrderPhaseDto();

        HomeScreenSteps.openMonitor();
        MonitorSteps.changeLocation("automationMonitoring");
        SearchSteps.openSearchFilters();
        SearchSteps.clickPhaseFilter();
        SearchSteps.openSearchMenu();
        SearchSteps.fillTextSearch(expectedOrderInfo.getPhaseName());
        MonitorSearchValidations.validatePriorityListElementNumber(1);
        SearchSteps.selectPhase(expectedOrderInfo.getPhaseName());
        MonitorSearchValidations.validatePhaseValue(expectedOrderInfo.getPhaseName());
        SearchSteps.fillTextSearch(workOrderId);
        SearchSteps.search();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanFindTheNecessaryDepartment(String rowID,
                                             String description, JSONObject testData) {

        final String departmentName = "Default";
        HomeScreenSteps.openMonitor();
        MonitorSteps.changeLocation("automationMonitoring");
        SearchSteps.openSearchFilters();
        SearchSteps.clickDepartmentFilter();
        SearchSteps.openSearchMenu();
        SearchSteps.fillTextSearch(departmentName);
        SearchSteps.selectDepartment(departmentName);
        MonitorSearchValidations.validateDepartmentValue(departmentName);
        SearchSteps.fillTextSearch(workOrderId);
        SearchSteps.search();
        ScreenNavigationSteps.pressBackButton();
    }
}
