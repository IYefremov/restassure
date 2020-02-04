package com.cyberiansoft.test.vnext.testcases.r360pro.monitoring;

import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.baseutils.MonitoringDataUtils;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.enums.DateUtils;
import com.cyberiansoft.test.enums.TimeFrameValues;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.monitoring.MonitorSearchSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.MonitorSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.MonitorSearchValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;


public class VNextMonitoringSearchFiltersTestCases extends BaseTestClass {

    @BeforeClass(description = "Team Monitoring Basic Flow Test")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getMonitoringSearchFiltersDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanClearCommonFilters(String rowID,
                                            String description, JSONObject testData) {

        LocalDate previousDay = LocalDate.now().minusDays(1);
        LocalDate nextDay = LocalDate.now().plusDays(1);

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
        final String workOrderId = WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();

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
}
