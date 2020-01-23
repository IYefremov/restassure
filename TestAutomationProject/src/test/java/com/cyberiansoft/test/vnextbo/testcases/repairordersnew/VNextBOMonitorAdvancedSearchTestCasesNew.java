package com.cyberiansoft.test.vnextbo.testcases.repairordersnew;

import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBOROAdvancedSearchDialogStepsNew;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBOROPageStepsNew;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.commonobjects.VNextBOSearchPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.repairordersnew.VNextBOROAdvancedSearchDialogValidationsNew;
import com.cyberiansoft.test.vnextbo.validations.repairordersnew.VNextBOROWebPageValidationsNew;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBOMonitorAdvancedSearchTestCasesNew extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getMonitorAdvancedSearchNewTD();
        VNextBOLeftMenuInteractions.selectRepairOrdersMenu();
        VNextBOBreadCrumbInteractions.setLocation("Best Location Automation");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSelectCustomer(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setCustomerField("Alex1 Zakaulov1");
        VNextBOROAdvancedSearchDialogValidationsNew.verifyCustomerFieldHasCorrectValue("Alex1 Zakaulov1");
        VNextBOROAdvancedSearchDialogStepsNew.closeDialog();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCloseAdvancedSearchWindow(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.closeDialog();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByCustomer(String rowID, String description, JSONObject testData) {

        VNextBOROPageStepsNew.searchOrdersByCustomer("Mike's Techs");
        VNextBOROWebPageValidationsNew.verifyCustomersAreCorrectInTheTable("Mike's Techs");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByEmployee(String rowID, String description, JSONObject testData) {

        VNextBOROPageStepsNew.searchOrdersByEmployee("Yurii Tolkunov");
        VNextBOROWebPageValidationsNew.verifyEmployeesAreCorrectInTheTable("Yurii Tolkunov");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByPhase(String rowID, String description, JSONObject testData) {

        VNextBOROPageStepsNew.searchOrdersByPhase("PDR Station");
        VNextBOROWebPageValidationsNew.verifyPhasesAreCorrectInTheTable("PDR Station");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDepartment(String rowID, String description, JSONObject testData) {

        VNextBOROPageStepsNew.searchOrdersByDepartment("TestDep1");
        VNextBOROWebPageValidationsNew.verifyDepartmentsAreCorrectInTheTable("TestDep1");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByWoType(String rowID, String description, JSONObject testData) {

        VNextBOROPageStepsNew.searchOrdersByWoType("01ZalexWO_tp");
        VNextBOROWebPageValidationsNew.verifyWoTypesAreCorrectInTheTable("01ZalexWO_tp");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByWoNumber(String rowID, String description, JSONObject testData) {

        VNextBOROPageStepsNew.searchOrdersByWoNumber("O-361-00004");
        VNextBOROWebPageValidationsNew.verifyWoNumbersAreCorrectInTheTable("O-361-00004");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRoNumber(String rowID, String description, JSONObject testData) {

        VNextBOROPageStepsNew.searchOrdersByRoNumber("RO#ForAuto");
        VNextBOROWebPageValidationsNew.verifyRoNumbersAreCorrectInTheTable("RO#ForAuto");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByStockNumber(String rowID, String description, JSONObject testData) {

        VNextBOROPageStepsNew.searchOrdersByStockNumber("S00054");
        VNextBOROWebPageValidationsNew.verifyStockNumbersAreCorrectInTheTable("S00054");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByVinNumber(String rowID, String description, JSONObject testData) {

        VNextBOROPageStepsNew.searchOrdersByVinNumber("1ESFCVGDE34ES2589");
        VNextBOROWebPageValidationsNew.verifyVinNumbersAreCorrectInTheTable("1ESFCVGDE34ES2589");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRepairStatus(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOROPageStepsNew.searchOrdersByRepairStatus(data.getRepairStatus());
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Repair Status: " + data.getRepairStatus());
        VNextBOROWebPageValidationsNew.verifyOrdersTableAfterSearch();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByPhaseStatus(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOROPageStepsNew.searchOrdersByPhaseStatus(data.getPhase(), data.getPhaseStatus());
        VNextBOROWebPageValidationsNew.verifyPhasesAreCorrectInTheTable(data.getPhase());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByFlag(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOROPageStepsNew.searchOrdersByFlag(data.getFlag());
        VNextBOROWebPageValidationsNew.verifyOrdersTableAfterSearch();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByHasProblemsFlag(String rowID, String description, JSONObject testData) {

        VNextBOROPageStepsNew.searchOrdersWithHasProblemsFlag();
        VNextBOROWebPageValidationsNew.verifyProblemIndicatorIsDisplayedForEachRecord();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInProcess(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOROPageStepsNew.searchOrdersDaysInProcess(data.getDaysInProcess(), data.getDaysNumStart(), java.util.Optional.ofNullable(data.getDaysNum()));
        VNextBOROWebPageValidationsNew.verifyOrdersTableAfterSearch();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInPhase(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOROPageStepsNew.searchOrdersDaysInPhase(data.getDaysInProcess(), data.getDaysNumStart(), java.util.Optional.ofNullable(data.getDaysNum()));
        VNextBOROWebPageValidationsNew.verifyOrdersTableAfterSearch();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByCustomTimeFrame(String rowID, String description, JSONObject testData) {

        VNextBOROPageStepsNew.searchOrdersByCustomTimeFrame();
        VNextBOROWebPageValidationsNew.verifyOrdersTableAfterSearch();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByTimeFrameLast30Days(String rowID, String description, JSONObject testData) {

        VNextBOROPageStepsNew.searchOrdersByTimeFrame("Last 30 days");
        VNextBOROWebPageValidationsNew.verifyOrdersAfterSearchByTimeFrame(CustomDateProvider.getLastThirtyDaysStartDate());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByTimeFrameWeekToDate(String rowID, String description, JSONObject testData) {

        VNextBOROPageStepsNew.searchOrdersByTimeFrame("Week To Date");
        VNextBOROWebPageValidationsNew.verifyOrdersAfterSearchByTimeFrame(CustomDateProvider.getWeekToDateStartDate());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByTimeFrameLastWeek(String rowID, String description, JSONObject testData) {

        VNextBOROPageStepsNew.searchOrdersByTimeFrame("Last Week");
        VNextBOROWebPageValidationsNew.verifyOrdersAfterSearchByTimeFrame(CustomDateProvider.getLastWeekStartDate());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByTimeFrameToday(String rowID, String description, JSONObject testData) {

        VNextBOROPageStepsNew.searchOrdersByTimeFrame("Today");
        VNextBOROWebPageValidationsNew.verifyOrdersAfterSearchByTimeFrame(CustomDateProvider.getCurrentDateLocalized());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByTimeFrameMonthToDate(String rowID, String description, JSONObject testData) {

        VNextBOROPageStepsNew.searchOrdersByTimeFrame("Month to Date");
        VNextBOROWebPageValidationsNew.verifyOrdersAfterSearchByTimeFrame(
                CustomDateProvider.getLastMonthStartDate(), CustomDateProvider.getMonthStartDate());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByTimeFrameYearToDate(String rowID, String description, JSONObject testData) {

        VNextBOROPageStepsNew.searchOrdersByTimeFrame("Year To Date");
        VNextBOROWebPageValidationsNew.verifyOrdersAfterSearchByTimeFrame(CustomDateProvider.getYearToDateStartDate());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByTimeFrameLastYear(String rowID, String description, JSONObject testData) {

        VNextBOROPageStepsNew.searchOrdersByTimeFrame("Last Year");
        VNextBOROWebPageValidationsNew.verifyOrdersAfterSearchByTimeFrame(
                CustomDateProvider.getYearToDateStartDate(), CustomDateProvider.getYearStartDate());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByLast90DaysTimeFrame(String rowID, String description, JSONObject testData) {

        VNextBOROPageStepsNew.searchOrdersByTimeFrame("Last 90 days");
        VNextBOROWebPageValidationsNew.verifyOrdersAfterSearchByTimeFrame(
                CustomDateProvider.getThreeMonthsBeforeCurrentDate());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }
}
