package com.cyberiansoft.test.vnextbo.testcases.repairorders;

import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.OrderMonitorRepairStatuses;
import com.cyberiansoft.test.enums.TimeFrameValues;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.VNextBOConfirmationDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOCalendarWidgetDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROAdvancedSearchDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROPageInteractions;
import com.cyberiansoft.test.vnextbo.steps.HomePageSteps;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOPageSwitcherSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROAdvancedSearchDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROPageSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOROAdvancedSearchDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBORODetailsPageValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOROPageValidations;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class VNextBOMonitorAdvancedSearchTestCases extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getMonitorAdvancedSearchTD();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSelectCustomer(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.typeCustomerName(data.getCustomer());
        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isCustomerDisplayed(data.getCustomer()));
        VNextBOROAdvancedSearchDialogInteractions.selectCustomerNameFromBoxList(data.getCustomer());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByEmployee(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setCustomer(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(data.getTimeFrame());
        VNextBOROAdvancedSearchDialogInteractions.typeEmployeeName(data.getEmployee());

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isEmployeeDisplayed(data.getEmployee()));
        VNextBOROAdvancedSearchDialogInteractions.selectEmployeeNameFromBoxList(data.getEmployee());
        VNextBOROAdvancedSearchDialogSteps.search();
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByPhase(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setCustomer(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.setPhase(data.getPhase());
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_YEARTODATE.getName());
        VNextBOROAdvancedSearchDialogSteps.search();
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDepartment(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setCustomer(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.setDepartment(data.getDepartment());
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_YEARTODATE.getName());
        VNextBOROAdvancedSearchDialogSteps.search();
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByWoType(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setCustomer(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.setWoType(data.getWoType());
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_YEARTODATE.getName());
        VNextBOROAdvancedSearchDialogSteps.search();
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByWoNumber(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setCustomer(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.setWoNum(data.getWoNum());
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_YEARTODATE.getName());
        VNextBOROAdvancedSearchDialogSteps.search();
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRoNumber(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setCustomer(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.setRoNum(data.getRoNum());
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_YEARTODATE.getName());
        VNextBOROAdvancedSearchDialogSteps.search();
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByStockNumber(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setCustomer(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.setStockNum(data.getStockNum());
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_YEARTODATE.getName());
        VNextBOROAdvancedSearchDialogSteps.search();
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByVinNumber(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setCustomer(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.setVinNum(data.getVinNum());
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_YEARTODATE.getName());
        VNextBOROAdvancedSearchDialogSteps.search();
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInPhaseLess(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setDaysInPhase(data.getDaysInPhase());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInPhaseFromInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByWhiteFlag(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setFlag(data.getFlag());
        VNextBOROAdvancedSearchDialogSteps.search();

        if (!VNextBOROPageValidations.isTableDisplayed()) {
            Assert.assertTrue(VNextBOROPageValidations.isTextNoRecordsDisplayed(), "The text notification is not displayed");
        } else {
            Assert.assertTrue(VNextBOROPageInteractions.getNumOfOrdersOnPage() > 0, "The orders are not displayed");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByCustomer(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.typeCustomerName(data.getCustomer());
        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isCustomerDisplayed(data.getCustomer()));
        VNextBOROAdvancedSearchDialogInteractions.selectCustomerNameFromBoxList(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(data.getTimeFrame());
        VNextBOROAdvancedSearchDialogSteps.search();
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRedFlag(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setFlag(data.getFlag());
        VNextBOROAdvancedSearchDialogSteps.search();

        if (!VNextBOROPageValidations.isTableDisplayed()) {
            Assert.assertTrue(VNextBOROPageValidations.isTextNoRecordsDisplayed(), "The text notification is not displayed");
        } else {
            Assert.assertTrue(VNextBOROPageInteractions.getNumOfOrdersOnPage() > 0, "The orders are not displayed");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByOrangeFlag(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setFlag(data.getFlag());
        VNextBOROAdvancedSearchDialogSteps.search();

        if (!VNextBOROPageValidations.isTableDisplayed()) {
            Assert.assertTrue(VNextBOROPageValidations.isTextNoRecordsDisplayed(), "The text notification is not displayed");
        } else {
            Assert.assertTrue(VNextBOROPageInteractions.getNumOfOrdersOnPage() > 0, "The orders are not displayed");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByYellowFlag(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setFlag(data.getFlag());
        VNextBOROAdvancedSearchDialogSteps.search();

        if (!VNextBOROPageValidations.isTableDisplayed()) {
            Assert.assertTrue(VNextBOROPageValidations.isTextNoRecordsDisplayed(), "The text notification is not displayed");
        } else {
            Assert.assertTrue(VNextBOROPageInteractions.getNumOfOrdersOnPage() > 0, "The orders are not displayed");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByGreenFlag(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setFlag(data.getFlag());
        VNextBOROAdvancedSearchDialogSteps.search();

        if (!VNextBOROPageValidations.isTableDisplayed()) {
            Assert.assertTrue(VNextBOROPageValidations.isTextNoRecordsDisplayed(), "The text notification is not displayed");
        } else {
            Assert.assertTrue(VNextBOROPageInteractions.getNumOfOrdersOnPage() > 0, "The orders are not displayed");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByBlueFlag(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setFlag(data.getFlag());
        VNextBOROAdvancedSearchDialogSteps.search();

        if (!VNextBOROPageValidations.isTableDisplayed()) {
            Assert.assertTrue(VNextBOROPageValidations.isTextNoRecordsDisplayed(), "The text notification is not displayed");
        } else {
            Assert.assertTrue(VNextBOROPageInteractions.getNumOfOrdersOnPage() > 0, "The orders are not displayed");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByPurpleFlag(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setFlag(data.getFlag());
        VNextBOROAdvancedSearchDialogSteps.search();

        if (!VNextBOROPageValidations.isTableDisplayed()) {
            Assert.assertTrue(VNextBOROPageValidations.isTextNoRecordsDisplayed(), "The text notification is not displayed");
        } else {
            Assert.assertTrue(VNextBOROPageInteractions.getNumOfOrdersOnPage() > 0, "The orders are not displayed");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInPhaseLessOrEqual(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setDaysInPhase(data.getDaysInPhase());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInPhaseFromInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInPhaseEqual(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setDaysInPhase(data.getDaysInPhase());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInPhaseFromInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInPhaseMoreOrEqual(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setDaysInPhase(data.getDaysInPhase());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInPhaseFromInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInPhaseMore(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setDaysInPhase(data.getDaysInPhase());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInPhaseFromInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInPhaseBetween(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setDaysInPhase(data.getDaysInPhase());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInPhaseFromInput(data.getDaysNumStart());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInPhaseToInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInProcessLess(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setDaysInProcess(data.getDaysInProcess());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInProcessFromInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInProcessLessOrEqual(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setDaysInProcess(data.getDaysInProcess());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInProcessFromInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInProcessEqual(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setDaysInProcess(data.getDaysInProcess());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInProcessFromInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInProcessMoreOrEqual(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setDaysInProcess(data.getDaysInProcess());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInProcessFromInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInProcessMore(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setDaysInProcess(data.getDaysInProcess());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInProcessFromInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInProcessBetween(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setDaysInProcess(data.getDaysInProcess());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInProcessFromInput(data.getDaysNumStart());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInProcessToInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByCustomTimeFrame(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("M/d/yyyy");

        LocalDateTime now = LocalDateTime.now(ZoneId.of("US/Pacific"));
        final int monthValue = now.getMonthValue();
        final int prevMonthValue = monthValue - 1;
        LocalDateTime before = now.minusDays(7);

        String beforeMinusMonth = VNextBOCalendarWidgetDialogInteractions.getMonthReplace(monthValue, prevMonthValue, before);
        String nowMinusMonth = VNextBOCalendarWidgetDialogInteractions.getMonthReplace(monthValue, prevMonthValue, now);
        System.out.println("beforeMinusMonth: " + beforeMinusMonth);
        System.out.println("nowMinusMonth: " + nowMinusMonth);
        String beforeFormatted = before.format(format);
        String nowFormatted = now.format(format);
        System.out.println("beforeFormatted: " + beforeFormatted);
        System.out.println("nowFormatted: " + nowFormatted);

        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(data.getTimeFrame());
        VNextBOROAdvancedSearchDialogInteractions.clickFromDateButton();
        VNextBOCalendarWidgetDialogInteractions.selectFromDate(beforeMinusMonth);
        VNextBOROAdvancedSearchDialogInteractions.clickToDateButton();
        VNextBOCalendarWidgetDialogInteractions.selectToDate(nowMinusMonth);
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByLast90DaysTimeFrame(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_90_DAYS.getName());
        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(
                OrderMonitorRepairStatuses.IN_PROGRESS_ACTIVE.getValue());
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOROPageValidations.verifyOrdersAfterSearchByTimeFrame(
                CustomDateProvider.getThreeMonthsBeforeCurrentDate());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRepairStatusInProgressRework(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getRepairStatusOption(), data.getRepairStatus());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRepairStatusInProgressActive(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRepairStatusInProgressQueued(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRepairStatusInProgressOnHold(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRepairStatusInProgressNotOnHold(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRepairStatusInProgressQAReady(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRepairStatusCompletedQAReady(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRepairStatusCompletedNotBilled(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRepairStatusCompletedOnSite(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRepairStatusCompletedAll(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRepairStatusInProgressAll(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanFillInAdvancedSearchWindow(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setCustomer(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.setEmployee(data.getEmployee());
        VNextBOROAdvancedSearchDialogInteractions.setPhase(data.getPhase());
        VNextBOROAdvancedSearchDialogInteractions.setDepartment(data.getDepartment());
        VNextBOROAdvancedSearchDialogInteractions.setWoType(data.getWoType());
        VNextBOROAdvancedSearchDialogInteractions.setWoNum(data.getWoNum());
        VNextBOROAdvancedSearchDialogInteractions.setRoNum(data.getRoNum());
        VNextBOROAdvancedSearchDialogInteractions.setStockNum(data.getStockNum());
        VNextBOROAdvancedSearchDialogInteractions.setVinNum(data.getVinNum());
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_30_DAYS.getName());
        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogInteractions.setDaysInProcess(data.getDaysInProcess());
        VNextBOROAdvancedSearchDialogInteractions.setDaysInPhase(data.getDaysInPhase());
        VNextBOROAdvancedSearchDialogInteractions.setFlag(data.getFlag());
        VNextBOROAdvancedSearchDialogInteractions.setSearchName(data.getSearchName());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSaveAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        final String searchName = data.getSearchName() + RandomStringUtils.randomAlphanumeric(3);
        VNextBOROAdvancedSearchDialogInteractions.setFlag(data.getFlag());
        VNextBOROAdvancedSearchDialogInteractions.setSearchName(searchName);
        VNextBOROAdvancedSearchDialogInteractions.clickSaveButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanEditSavedAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocations()[0]);
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        final String searchName = data.getSearchName() + RandomStringUtils.randomAlphanumeric(3);
        VNextBOROAdvancedSearchDialogInteractions.setFlag(data.getFlags()[0]);
        VNextBOROAdvancedSearchDialogInteractions.setSearchName(searchName);
        VNextBOROAdvancedSearchDialogInteractions.clickSaveButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        VNextBOBreadCrumbInteractions.setLocation(data.getLocations()[1]);
        VNextBOBreadCrumbInteractions.setLocation(data.getLocations()[0]);
        VNextBOROPageSteps.setSavedSearchOption(searchName);
        VNextBOROPageInteractions.clickEditIconForSavedSearch();
        VNextBOROAdvancedSearchDialogInteractions.setFlag(data.getFlags()[1]);
        VNextBOROAdvancedSearchDialogInteractions.clickSaveButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        VNextBOROPageInteractions.clickEditIconForSavedSearch();
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getFlagSelected(), data.getFlags()[1],
                "The flag hasn't been selected");
        VNextBOROAdvancedSearchDialogInteractions.clickDeleteButton();
        VNextBOConfirmationDialogInteractions.clickConfirmButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanUseStoredAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        final String searchName = data.getSearchName() + RandomStringUtils.randomAlphanumeric(3);
        VNextBOROAdvancedSearchDialogInteractions.setFlag(data.getFlag());
        VNextBOROAdvancedSearchDialogInteractions.setSearchName(searchName);
        VNextBOROAdvancedSearchDialogInteractions.clickSaveButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

        VNextBOROPageSteps.setSavedSearchOption(searchName);
        VNextBOROPageInteractions.clickAdvancedSearchCaret();
        VNextBOROAdvancedSearchDialogInteractions.setFlag(data.getFlag());
        VNextBOROAdvancedSearchDialogInteractions.setSearchName(searchName);
        VNextBOROAdvancedSearchDialogInteractions.clickClearButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCloseAdvancedSearchWindow(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.clickAdvancedSearchCloseButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanClearAllFieldsInAdvancedSearchWindow(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setCustomer(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.setEmployee(data.getEmployee());
        VNextBOROAdvancedSearchDialogInteractions.setPhase(data.getPhase());
        VNextBOROAdvancedSearchDialogInteractions.setDepartment(data.getDepartment());
        VNextBOROAdvancedSearchDialogInteractions.setWoType(data.getWoType());
        VNextBOROAdvancedSearchDialogInteractions.setWoNum(data.getWoNum());
        VNextBOROAdvancedSearchDialogInteractions.setRoNum(data.getRoNum());
        VNextBOROAdvancedSearchDialogInteractions.setStockNum(data.getStockNum());
        VNextBOROAdvancedSearchDialogInteractions.setVinNum(data.getVinNum());
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(data.getTimeFrame());
        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogInteractions.setDaysInProcess(data.getDaysInProcess());
        VNextBOROAdvancedSearchDialogInteractions.setDaysInPhase(data.getDaysInPhase());
        VNextBOROAdvancedSearchDialogInteractions.setFlag(data.getFlag());
        VNextBOROAdvancedSearchDialogInteractions.setSearchName(data.getSearchName());

        System.out.println("Before clear");
        VNextBOROAdvancedSearchDialogInteractions.getAdvancedSearchDialogElementsValues().forEach(System.out::println);
        System.out.println("\nData before clear");
        data.getAdvancedSearchDialogElements().forEach(System.out::println);

        Assert.assertTrue(VNextBOROAdvancedSearchDialogInteractions
                .getAdvancedSearchDialogElementsValues()
                .containsAll(data.getAdvancedSearchDialogElements()), "The data hasn't been inserted");

        VNextBOROAdvancedSearchDialogInteractions.clickClearButton();
        System.out.println("After clear");
        VNextBOROAdvancedSearchDialogInteractions.getAdvancedSearchDialogElementsValues().forEach(System.out::println);
        System.out.println("\nData after clear");
        data.getAdvancedSearchDialogElements().forEach(System.out::println);
        Assert.assertTrue(VNextBOROAdvancedSearchDialogInteractions
                .getAdvancedSearchDialogElementsValues()
                .containsAll(data.getAdvancedSearchDialogDefaultTextList()), "The data hasn't been cleared");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserSeesSearchMaskWithAllEnteredInfo(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setCustomer(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.setEmployee(data.getEmployee());
        VNextBOROAdvancedSearchDialogInteractions.setPhase(data.getPhase());
        VNextBOROAdvancedSearchDialogInteractions.setDepartment(data.getDepartment());
        VNextBOROAdvancedSearchDialogInteractions.setWoType(data.getWoType());
        VNextBOROAdvancedSearchDialogInteractions.setWoNum(data.getWoNum());
        VNextBOROAdvancedSearchDialogInteractions.setRoNum(data.getRoNum());
        VNextBOROAdvancedSearchDialogInteractions.setStockNum(data.getStockNum());
        VNextBOROAdvancedSearchDialogInteractions.setVinNum(data.getVinNum());
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_90_DAYS.getName());
        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogInteractions.setDaysInProcess(data.getDaysInProcess());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInProcessFromInput(data.getDaysNumList()[0]);
        VNextBOROAdvancedSearchDialogInteractions.setDaysInPhase(data.getDaysInPhase());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInPhaseFromInput(data.getDaysNumList()[1]);
        VNextBOROAdvancedSearchDialogInteractions.setFlag(data.getFlag());
        VNextBOROAdvancedSearchDialogInteractions.setSearchName(data.getSearchName());
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOROPageInteractions.movePointerToSearchResultsField();

        System.out.println("Inserted values");
        final List<String> searchResultsList = VNextBOROPageInteractions
                .getSearchResultsList()
                .stream()
                .map(String::trim)
                .collect(Collectors.toList());
        searchResultsList.forEach(System.out::println);

        System.out.println();
        System.out.println("Displayed values");
        final List<String> advancedSearchDialogElements = data.getFullAdvancedSearchElementsList();
        advancedSearchDialogElements.forEach(System.out::println);

        Assert.assertTrue(searchResultsList.containsAll(advancedSearchDialogElements),
                "The data hasn't been inserted");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByCurrentPhaseStatus(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogSteps.searchByActivePhase(
                data.getPhase(), data.getPhaseStatus(), TimeFrameValues.TIMEFRAME_LASTYEAR.getName());

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        VNextBOROPageValidations.verifyOrdersAfterSearchByPhase(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByActivePhaseStatus(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogSteps.searchByActivePhase(
                data.getPhase(), data.getPhaseStatus(), TimeFrameValues.TIMEFRAME_LASTYEAR.getName());

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        VNextBOROPageInteractions.clickWoLink();
        VNextBORODetailsPageValidations.verifyPhaseStatusOrPartPhaseStatusIsDisplayed(
                data.getPhase(), data.getPhaseStatus(), data.getServiceStatuses());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByNotCompletedPhaseStatus(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogSteps.searchByActivePhase(
                data.getPhase(), data.getPhaseStatus(), TimeFrameValues.TIMEFRAME_YEARTODATE.getName());

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        VNextBOROPageInteractions.clickWoLink();
        VNextBORODetailsPageValidations.verifyPhaseStatus(data.getPhase(), data.getServiceStatuses());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByCompletedPhaseStatus(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogSteps.searchByActivePhase(
                data.getPhase(), data.getPhaseStatus(), TimeFrameValues.TIMEFRAME_YEARTODATE.getName());

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        VNextBOROPageInteractions.clickWoLink();
        VNextBORODetailsPageValidations.verifyPhaseStatus(data.getPhase(), data.getServiceStatuses());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByHasProblemsOption(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_YEARTODATE.getName());
        VNextBOROAdvancedSearchDialogInteractions.clickHasProblemsCheckbox();
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOROPageValidations.verifyAllOrdersHaveProblemIndicators();
    }

    // todo fails, bug #97510
    // https://cyb.tpondemand.com/restui/board.aspx?#page=bug/97510&appConfig=eyJhY2lkIjoiRTU0NTRFNkE4OEZBODJDQTIzRjZFQzI0Q0NBQUEyNEYifQ==
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchBySortByRoNumOption(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setSortBy(data.getSortByOptions()[0]);
        VNextBOROAdvancedSearchDialogSteps.search();

        Assert.assertEquals(VNextBOROPageInteractions.getRoNumbersListValues(),
                VNextBOROPageInteractions.getDescSortedRoNumbersListValues(),
                "The repair orders are not sorted properly by RO#");

        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setSortBy(data.getSortByOptions()[1]);
        VNextBOROAdvancedSearchDialogSteps.search();
        Assert.assertEquals(VNextBOROPageInteractions.getRoNumbersListValues(),
                VNextBOROPageInteractions.getAscSortedRoNumbersListValues(),
                "The repair orders are not sorted properly by RO#");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchBySortByPriorityOldestToNewestOption(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_YEARTODATE.getName());
        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogInteractions.setSortBy(data.getSortByOptions()[0]);
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOPageSwitcherSteps.changeItemsPerPage(data.getPages()[0]);
        final List<String> ordersPriorityValues = VNextBOROPageInteractions.getOrdersPriorityValues();
        VNextBOROPageValidations.verifyOrdersAreDisplayedByPriority(ordersPriorityValues);

        VNextBOROPageValidations.verifyHighPriorityOrdersAreSortedByDateInAscendingOrder();
        VNextBOROPageValidations.verifyNormalPriorityOrdersAreSortedByDateInAscendingOrder();
        VNextBOROPageValidations.verifyLowPriorityOrdersAreSortedByDateInAscendingOrder();

        VNextBOROPageValidations.verifyOrdersAreDisplayedByPriorityOnTheLastPage();
        VNextBOROPageValidations.verifyHighPriorityOrdersAreSortedByDateInAscendingOrder();
        VNextBOROPageValidations.verifyNormalPriorityOrdersAreSortedByDateInAscendingOrder();
        VNextBOROPageValidations.verifyLowPriorityOrdersAreSortedByDateInAscendingOrder();
    }

    //todo bug - the orders are not sorted properly. Needs clarifications/fixes from V. Dubinenko
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchBySortByPriorityNewestToOldest(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_YEARTODATE.getName());
        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogInteractions.setSortBy(data.getSortByOptions()[0]);
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOPageSwitcherSteps.changeItemsPerPage(data.getPages()[0]);
        final List<String> ordersPriorityValues = VNextBOROPageInteractions.getOrdersPriorityValues();
        VNextBOROPageValidations.verifyOrdersAreDisplayedByPriority(ordersPriorityValues);

        VNextBOROPageValidations.verifyHighPriorityOrdersAreSortedByDateInDescendingOrder();
        VNextBOROPageValidations.verifyNormalPriorityOrdersAreSortedByDateInDescendingOrder();
        VNextBOROPageValidations.verifyLowPriorityOrdersAreSortedByDateInDescendingOrder();

        VNextBOROPageValidations.verifyOrdersAreDisplayedByPriorityOnTheLastPage();
        VNextBOROPageValidations.verifyHighPriorityOrdersAreSortedByDateInDescendingOrder();
        VNextBOROPageValidations.verifyNormalPriorityOrdersAreSortedByDateInDescendingOrder();
        VNextBOROPageValidations.verifyLowPriorityOrdersAreSortedByDateInDescendingOrder();
    }

    //todo bug - the orders are not sorted properly. Needs clarifications/fixes from V. Dubinenko
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchBySortByArbitrationDateOption(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_YEARTODATE.getName());
        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogInteractions.setSortBy(data.getSortByOptions()[0]);
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOPageSwitcherSteps.changeItemsPerPage(data.getPages()[0]);

        final List<String> arbitrationDatesList = VNextBOROPageInteractions.getArbitrationDatesList();
        arbitrationDatesList.forEach(System.out::println);

        Assert.assertTrue(VNextBOROPageValidations.isArbitrationDateMoreThanCurrentDate(arbitrationDatesList.get(0)),
                "The arbitration date of the first order is not after the current date");
        Assert.assertTrue(VNextBOROPageValidations.areOrdersWithArbitrationDatesDisplayedBeforeAnotherOrders(
                arbitrationDatesList), "The orders with arbitration dates are not displayed before the orders without them");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByTimeFrameLast30Days(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_30_DAYS.getName());
        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(
                OrderMonitorRepairStatuses.IN_PROGRESS_ACTIVE.getValue());
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOROPageValidations.verifyOrdersAfterSearchByTimeFrame(
                CustomDateProvider.getLastThirtyDaysStartDate());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByTimeFrameWeekToDate(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_WEEKTODATE.getName());
        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(
                OrderMonitorRepairStatuses.IN_PROGRESS_ACTIVE.getValue());
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOROPageValidations.verifyOrdersAfterSearchByTimeFrame(
                CustomDateProvider.getWeekToDateStartDate());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByTimeFrameLastWeek(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTWEEK.getName());
        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(
                OrderMonitorRepairStatuses.IN_PROGRESS_ACTIVE.getValue());
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOROPageValidations.verifyOrdersAfterSearchByTimeFrame(
                CustomDateProvider.getLastWeekStartDate());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByTimeFrameToday(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_TODAY.getName());
        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(
                OrderMonitorRepairStatuses.IN_PROGRESS_ACTIVE.getValue());
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOROPageValidations.verifyOrdersAfterSearchByTimeFrame(
                CustomDateProvider.getCurrentDateLocalized());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByTimeFrameMonthToDate(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_MONTHTODATE.getName());
        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(
                OrderMonitorRepairStatuses.IN_PROGRESS_ACTIVE.getValue());
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOROPageValidations.verifyOrdersAfterSearchByTimeFrame(
                CustomDateProvider.getLastMonthStartDate(), CustomDateProvider.getMonthStartDate());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByTimeFrameYearToDate(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_YEARTODATE.getName());
        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(
                OrderMonitorRepairStatuses.IN_PROGRESS_ACTIVE.getValue());
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOROPageValidations.verifyOrdersAfterSearchByTimeFrame(
                CustomDateProvider.getYearToDateStartDate());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByTimeFrameLastYear(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR.getName());
        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(
                OrderMonitorRepairStatuses.IN_PROGRESS_ACTIVE.getValue());
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOROPageValidations.verifyOrdersAfterSearchByTimeFrame(
                CustomDateProvider.getYearToDateStartDate(), CustomDateProvider.getYearStartDate());
    }
}