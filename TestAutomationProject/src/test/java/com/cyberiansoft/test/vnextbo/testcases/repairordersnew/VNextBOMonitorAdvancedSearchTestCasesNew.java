package com.cyberiansoft.test.vnextbo.testcases.repairordersnew;

import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBOROWebPageNew;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOPageSwitcherSteps;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBOROAdvancedSearchDialogStepsNew;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBOROPageStepsNew;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.commonobjects.VNextBOSearchPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.repairordersnew.VNextBOROAdvancedSearchDialogValidationsNew;
import com.cyberiansoft.test.vnextbo.validations.repairordersnew.VNextBOROWebPageValidationsNew;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.util.List;

public class VNextBOMonitorAdvancedSearchTestCasesNew extends BaseTestCase {

    private VNextBOMonitorData baseSearchData;
    private static final String BASE_DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/repairordersnew/VNextBOMonitorAdvancedSearchBaseData.json";

    @BeforeClass
    public void settingUp() throws Exception {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getMonitorAdvancedSearchNewTD();
        baseSearchData = JSonDataParser.getTestDataFromJson(JSONDataProvider.extractData_JSON(BASE_DATA_FILE), VNextBOMonitorData.class);
        VNextBOLeftMenuInteractions.selectRepairOrdersMenu();
        VNextBOBreadCrumbInteractions.setLocation("Best Location Automation");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSelectCustomer(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setCustomerField("Alex1 Zakaulov1");
        VNextBOROAdvancedSearchDialogValidationsNew.verifyCustomerFieldContainsCorrectValue("Alex1 Zakaulov1");
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanFillInAllAdvancedSearchFields(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOROPageStepsNew.setAllAdvancedSearchFields(data);
        VNextBOROAdvancedSearchDialogValidationsNew.verifyAllFieldsContainCorrectValues(data, false);
        VNextBOROAdvancedSearchDialogStepsNew.clearAllEnteredValues();
        VNextBOROAdvancedSearchDialogStepsNew.closeDialog();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSaveAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setFlagField(data.getFlag());
        VNextBOROAdvancedSearchDialogStepsNew.setSearchNameField(data.getSearchName());
        VNextBOROAdvancedSearchDialogStepsNew.saveSearch();
        VNextBOROWebPageValidationsNew.verifySavedSearchDropDownFieldContainsCorrectValue(data.getSearchName());
        VNextBOROPageStepsNew.openSavedAdvancedSearch(data.getSearchName());
        VNextBOROAdvancedSearchDialogValidationsNew.verifyFlagFieldContainsCorrectValue(data.getFlag());
        VNextBOROAdvancedSearchDialogValidationsNew.verifySearchNameFieldContainsCorrectValue(data.getSearchName());
        VNextBOROAdvancedSearchDialogStepsNew.deleteSavedSearch();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanUseStoredAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setFlagField(data.getFlag());
        VNextBOROAdvancedSearchDialogStepsNew.setSearchNameField(data.getSearchName());
        VNextBOROAdvancedSearchDialogStepsNew.saveSearch();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOROPageStepsNew.searchBySavedAdvancedSearch(data.getSearchName());
        VNextBOROWebPageValidationsNew.verifyOrdersTableAfterSearch();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOROPageStepsNew.openSavedAdvancedSearch(data.getSearchName());
        VNextBOROAdvancedSearchDialogStepsNew.deleteSavedSearch();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanEditSavedAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setFlagField(data.getFlag());
        VNextBOROAdvancedSearchDialogStepsNew.setSearchNameField(data.getSearchName());
        VNextBOROAdvancedSearchDialogStepsNew.saveSearch();
        VNextBOROPageStepsNew.openSavedAdvancedSearch(data.getSearchName());
        VNextBOROAdvancedSearchDialogStepsNew.setFlagField("Yellow");
        VNextBOROAdvancedSearchDialogStepsNew.setSearchNameField("Edited" + data.getSearchName());
        VNextBOROAdvancedSearchDialogStepsNew.saveSearch();
        VNextBOROWebPageValidationsNew.verifySavedSearchDropDownFieldContainsCorrectValue("Edited" + data.getSearchName());
        VNextBOROPageStepsNew.openSavedAdvancedSearch("Edited" + data.getSearchName());
        VNextBOROAdvancedSearchDialogValidationsNew.verifyFlagFieldContainsCorrectValue("Yellow");
        VNextBOROAdvancedSearchDialogValidationsNew.verifySearchNameFieldContainsCorrectValue("Edited" + data.getSearchName());
        VNextBOROAdvancedSearchDialogStepsNew.deleteSavedSearch();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanClearAllAdvancedSearchFields(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOROPageStepsNew.setAllAdvancedSearchFields(baseSearchData);
        VNextBOROAdvancedSearchDialogStepsNew.clearAllEnteredValues();
        VNextBOROAdvancedSearchDialogValidationsNew.verifyAllFieldsContainCorrectValues(data, true);
        VNextBOROAdvancedSearchDialogStepsNew.closeDialog();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeSearchMaskWithAllEnteredInfo(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOROPageStepsNew.searchByAllAdvancedSearchFields(data);
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Customer:" + data.getCustomer());
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Employee:" + data.getEmployee());
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Phase:" + data.getPhase());
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Task:" + data.getTask());
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Task Status:" + data.getTaskStatus());
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Department:" + data.getDepartment());
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("WO Type:" + data.getWoType());
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("WO#:" + data.getWoNum());
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("RO#:" + data.getRoNum());
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Stock#:" + data.getStockNum());
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("VIN:" + data.getVinNum());
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Timeframe:" + data.getTimeFrame());
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Repair Status:" + data.getRepairStatus());
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Days in Process:" + data.getDaysInProcess());
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Days in Phase:" + data.getDaysInPhase());
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Flag:" + data.getFlag());
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Sort by:" + data.getSortBy());
        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.clearAllEnteredValues();
        VNextBOROAdvancedSearchDialogStepsNew.closeDialog();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchBySortByRoNewestToOldest(String rowID, String description, JSONObject testData) throws ParseException {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOROPageStepsNew.searchOrdersByCustomTimeFrameWithSorting(data.getSortBy());
        List<WebElement> actualStartDatesElementsList = new VNextBOROWebPageNew().getStartDatesList();
        VNextBOROWebPageValidationsNew.verifySortingByStartDateIsCorrect(actualStartDatesElementsList,
                VNextBOROPageStepsNew.getDescSortedStartDatesListValues(actualStartDatesElementsList));
        VNextBOPageSwitcherSteps.clickHeaderLastPageButton();
        actualStartDatesElementsList = new VNextBOROWebPageNew().getStartDatesList();
        VNextBOROWebPageValidationsNew.verifySortingByStartDateIsCorrect(actualStartDatesElementsList,
                VNextBOROPageStepsNew.getDescSortedStartDatesListValues(actualStartDatesElementsList));
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOPageSwitcherSteps.clickHeaderFirstPageButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchBySortByRoOldestToNewest(String rowID, String description, JSONObject testData) throws ParseException {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOROPageStepsNew.searchOrdersByCustomTimeFrameWithSorting(data.getSortBy());
        List<WebElement> actualStartDatesElementsList = new VNextBOROWebPageNew().getStartDatesList();
        VNextBOROWebPageValidationsNew.verifySortingByStartDateIsCorrect(actualStartDatesElementsList,
                VNextBOROPageStepsNew.getAscSortedStartDatesListValues(actualStartDatesElementsList));
        VNextBOPageSwitcherSteps.clickHeaderLastPageButton();
        actualStartDatesElementsList = new VNextBOROWebPageNew().getStartDatesList();
        VNextBOROWebPageValidationsNew.verifySortingByStartDateIsCorrect(actualStartDatesElementsList,
                VNextBOROPageStepsNew.getAscSortedStartDatesListValues(actualStartDatesElementsList));
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOPageSwitcherSteps.clickHeaderFirstPageButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchBySortByPriorityOldestToNewest(String rowID, String description, JSONObject testData) throws ParseException {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOROPageStepsNew.searchOrdersByCustomTimeFrameWithSorting(data.getSortBy());
        VNextBOROWebPageValidationsNew.verifyOrdersAreSortedByPriorityAndStartDateFromOldestToNewest();
        VNextBOPageSwitcherSteps.clickHeaderLastPageButton();
        VNextBOROWebPageValidationsNew.verifyOrdersAreSortedByPriorityAndStartDateFromOldestToNewest();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOPageSwitcherSteps.clickHeaderFirstPageButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchBySortByPriorityNewestToOldest(String rowID, String description, JSONObject testData) throws ParseException {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOROPageStepsNew.searchOrdersByCustomTimeFrameWithSorting(data.getSortBy());
        VNextBOROWebPageValidationsNew.verifyOrdersAreSortedByPriorityAndStartDateFromNewestToOldest();
        VNextBOPageSwitcherSteps.clickHeaderLastPageButton();
        VNextBOROWebPageValidationsNew.verifyOrdersAreSortedByPriorityAndStartDateFromNewestToOldest();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOPageSwitcherSteps.clickHeaderFirstPageButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeDefaultSearchSettings(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Repair Status: In Progress - All; Timeframe: Last 30 days");
        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setRepairStatusField(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogStepsNew.setTimeFrameField(data.getTimeFrame());
        VNextBOROAdvancedSearchDialogStepsNew.clickSearchButton();
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Repair Status: " + data.getRepairStatus() + "; Timeframe: " + data.getTimeFrame());
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    //Comment from previous test - //todo bug - the orders are not sorted properly. Needs clarifications/fixes from V. Dubinenko
    //test should be refactored, disabled for now
    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchBySortByArbitrationDatePriority(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOROAdvancedSearchDialogStepsNew.setRepairStatusField(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogStepsNew.setTimeFrameField(data.getTimeFrame());
        VNextBOROAdvancedSearchDialogStepsNew.setSortByField(data.getSortBy());
        VNextBOROAdvancedSearchDialogStepsNew.clickSearchButton();
        VNextBOPageSwitcherSteps.changeItemsPerPage("100");
        VNextBOROWebPageValidationsNew.verifyFirstOrderArbitrationDateIsMoreThanCurrentDate();
        VNextBOROWebPageValidationsNew.verifyOrdersWithArbitrationDatesAreDisplayedBeforeOtherOrders();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }
}
