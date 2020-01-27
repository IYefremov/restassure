package com.cyberiansoft.test.vnextbo.testcases.repairorders;

import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.OrderMonitorFlags;
import com.cyberiansoft.test.enums.OrderMonitorRepairStatuses;
import com.cyberiansoft.test.enums.TimeFrameValues;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.VNextBOConfirmationDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOCalendarWidgetDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROAdvancedSearchDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROPageInteractions;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOPageSwitcherSteps;
import com.cyberiansoft.test.vnextbo.steps.homepage.VNextBOHomeWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROAdvancedSearchDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBORODetailsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROPageSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOROAdvancedSearchDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBORODetailsPageValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOROPageValidations;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VNextBOMonitorAdvancedSearchTestCases extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getMonitorAdvancedSearchTD();
    }

    @AfterMethod
    public void refreshPage() {
        Utils.refreshPage();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserCanSelectCustomer(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.typeCustomerName(data.getCustomer());
        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isCustomerDisplayed(data.getCustomer()));
        VNextBOROAdvancedSearchDialogInteractions.selectCustomerNameFromBoxList(data.getCustomer());
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyUserCanSearchByEmployee(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setCustomer(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR.getName());
        VNextBOROAdvancedSearchDialogInteractions.typeEmployeeName(data.getEmployee());

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isEmployeeDisplayed(data.getEmployee()));
        VNextBOROAdvancedSearchDialogInteractions.selectEmployeeNameFromBoxList(data.getEmployee());
        VNextBOROAdvancedSearchDialogSteps.search();
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(data.getCustomer(), true),
                "The work order hasn't been displayed");
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
    public void verifyUserCanSearchByPhase(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setCustomer(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.setPhase(data.getPhase());
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR.getName());
        VNextBOROAdvancedSearchDialogSteps.search();
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(data.getCustomer(), true),
                "The work order hasn't been displayed");
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
    public void verifyUserCanSearchByDepartment(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setCustomer(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.setDepartment(data.getDepartment());
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR.getName());
        VNextBOROAdvancedSearchDialogSteps.search();
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(data.getCustomer(), true),
                "The work order hasn't been displayed");
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyUserCanSearchByWoType(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setCustomer(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.setWoType(data.getWoType());
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR.getName());
        VNextBOROAdvancedSearchDialogSteps.search();
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(data.getCustomer(), true),
                "The work order hasn't been displayed");
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyUserCanSearchByWoNumber(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setCustomer(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.setWoNum(data.getWoNum());
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR.getName());
        VNextBOROAdvancedSearchDialogSteps.search();
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(data.getCustomer(), true),
                "The work order hasn't been displayed");
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 6)
    public void verifyUserCanSearchByRoNumber(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setCustomer(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.setRoNum(data.getRoNum());
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR.getName());
        VNextBOROAdvancedSearchDialogSteps.search();
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(data.getCustomer(), true),
                "The work order hasn't been displayed");
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 7)
    public void verifyUserCanSearchByStockNumber(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setCustomer(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.setStockNum(data.getStockNum());
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR.getName());
        VNextBOROAdvancedSearchDialogSteps.search();
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(data.getCustomer(), true),
                "The work order hasn't been displayed");
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 8)
    public void verifyUserCanSearchByVinNumber(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setCustomer(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.setVinNum(data.getVinNum());
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR.getName());
        VNextBOROAdvancedSearchDialogSteps.search();
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(data.getCustomer(), true),
                "The work order hasn't been displayed");
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 9)
    public void verifyUserCanSearchByDaysInPhaseLess(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setDaysInPhase(data.getDaysInPhase());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInPhaseFromInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 10)
    public void verifyUserCanSearchByWhiteFlag(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setFlag(data.getFlag());
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOROPageValidations.verifyOrdersAreDisplayed();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 11)
    public void verifyUserCanSearchByCustomer(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.typeCustomerName(data.getCustomer());
        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isCustomerDisplayed(data.getCustomer()));
        VNextBOROAdvancedSearchDialogInteractions.selectCustomerNameFromBoxList(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR.getName());
        VNextBOROAdvancedSearchDialogSteps.search();
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(data.getCustomer(), true),
                "The work order hasn't been displayed");
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 12)
    public void verifyUserCanSearchByRedFlag(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setFlag(OrderMonitorFlags.RED.getFlag());
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOROPageValidations.verifyOrdersAreDisplayed();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 13)
    public void verifyUserCanSearchByOrangeFlag(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setFlag(OrderMonitorFlags.ORANGE.getFlag());
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOROPageValidations.verifyOrdersAreDisplayed();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 14)
    public void verifyUserCanSearchByYellowFlag(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setFlag(data.getFlag());
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOROPageValidations.verifyOrdersAreDisplayed();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 15)
    public void verifyUserCanSearchByGreenFlag(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setFlag(data.getFlag());
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOROPageValidations.verifyOrdersAreDisplayed();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 16)
    public void verifyUserCanSearchByBlueFlag(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setFlag(data.getFlag());
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOROPageValidations.verifyOrdersAreDisplayed();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 17)
    public void verifyUserCanSearchByVioletFlag(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setFlag(OrderMonitorFlags.PURPLE.getFlag());
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOROPageValidations.verifyOrdersAreDisplayed();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 18)
    public void verifyUserCanSearchByDaysInPhaseLessOrEqual(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setDaysInPhase(data.getDaysInPhase());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInPhaseFromInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 19)
    public void verifyUserCanSearchByDaysInPhaseEqual(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setDaysInPhase(data.getDaysInPhase());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInPhaseFromInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 20)
    public void verifyUserCanSearchByDaysInPhaseMoreOrEqual(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setDaysInPhase(data.getDaysInPhase());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInPhaseFromInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 21)
    public void verifyUserCanSearchByDaysInPhaseMore(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setDaysInPhase(data.getDaysInPhase());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInPhaseFromInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 22)
    public void verifyUserCanSearchByDaysInPhaseBetween(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setDaysInPhase(data.getDaysInPhase());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInPhaseFromInput(data.getDaysNumStart());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInPhaseToInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 23)
    public void verifyUserCanSearchByDaysInProcessLess(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setDaysInProcess(data.getDaysInProcess());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInProcessFromInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 24)
    public void verifyUserCanSearchByDaysInProcessLessOrEqual(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setDaysInProcess(data.getDaysInProcess());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInProcessFromInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 25)
    public void verifyUserCanSearchByDaysInProcessEqual(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setDaysInProcess(data.getDaysInProcess());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInProcessFromInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 26)
    public void verifyUserCanSearchByDaysInProcessMoreOrEqual(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setDaysInProcess(data.getDaysInProcess());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInProcessFromInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 27)
    public void verifyUserCanSearchByDaysInProcessMore(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setDaysInProcess(data.getDaysInProcess());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInProcessFromInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 28)
    public void verifyUserCanSearchByDaysInProcessBetween(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setDaysInProcess(data.getDaysInProcess());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInProcessFromInput(data.getDaysNumStart());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInProcessToInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 29)
    public void verifyUserCanSearchByCustomTimeFrame(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/M/d");

        LocalDateTime toDate = LocalDateTime.now(ZoneId.of("US/Pacific")).minusMonths(1);
        LocalDateTime fromDate = toDate.minusDays(2);
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_CUSTOM.getName());
        VNextBOROAdvancedSearchDialogInteractions.clickFromDateButton();
        VNextBOCalendarWidgetDialogInteractions.selectFromDate(fromDate.format(format));
        VNextBOROAdvancedSearchDialogInteractions.clickToDateButton();
        VNextBOCalendarWidgetDialogInteractions.selectToDate(toDate.format(format));
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 30)
    public void verifyUserCanSearchByLast90DaysTimeFrame(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_90_DAYS.getName());
        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(
                OrderMonitorRepairStatuses.IN_PROGRESS_ACTIVE.getValue());
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOROPageValidations.verifyOrdersAfterSearchByTimeFrame(
                CustomDateProvider.getThreeMonthsBeforeCurrentDate());
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 31)
    public void verifyUserCanSearchByRepairStatusInProgressRework(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getRepairStatusOption(), data.getRepairStatus());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 32)
    public void verifyUserCanSearchByRepairStatusInProgressActive(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 33)
    public void verifyUserCanSearchByRepairStatusInProgressQueued(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 34)
    public void verifyUserCanSearchByRepairStatusInProgressOnHold(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 35)
    public void verifyUserCanSearchByRepairStatusInProgressNotOnHold(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 36)
    public void verifyUserCanSearchByRepairStatusInProgressQAReady(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 37)
    public void verifyUserCanSearchByRepairStatusCompletedQAReady(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 38)
    public void verifyUserCanSearchByRepairStatusCompletedNotBilled(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 39)
    public void verifyUserCanSearchByRepairStatusCompletedOnSite(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 40)
    public void verifyUserCanSearchByRepairStatusCompletedAll(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 41)
    public void verifyUserCanSearchByRepairStatusInProgressAll(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 42)
    public void verifyUserCanFillInAdvancedSearchWindow(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogSteps.setAdvancedSearchDialogData(data, TimeFrameValues.TIMEFRAME_30_DAYS, OrderMonitorFlags.RED);
        VNextBOROAdvancedSearchDialogSteps.search();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 43)
    public void verifyUserCanSaveAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        final String searchName = data.getSearchName() + RandomStringUtils.randomAlphanumeric(3);
        VNextBOROAdvancedSearchDialogInteractions.setFlag(data.getFlag());
        VNextBOROAdvancedSearchDialogInteractions.setSearchName(searchName);
        VNextBOROAdvancedSearchDialogInteractions.clickSaveButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 44)
    public void verifyUserCanEditSavedAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocations()[0]);
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        final String searchName = data.getSearchName() + RandomStringUtils.randomAlphanumeric(3);
        VNextBOROAdvancedSearchDialogInteractions.setFlag(OrderMonitorFlags.WHITE.getFlag());
        VNextBOROAdvancedSearchDialogInteractions.setSearchName(searchName);
        VNextBOROAdvancedSearchDialogInteractions.clickSaveButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        VNextBOBreadCrumbInteractions.setLocation(data.getLocations()[1]);
        VNextBOBreadCrumbInteractions.setLocation(data.getLocations()[0]);
        VNextBOROPageSteps.setSavedSearchOption(searchName);
        VNextBOROPageInteractions.clickEditIconForSavedSearch();
        VNextBOROAdvancedSearchDialogInteractions.setFlag(OrderMonitorFlags.YELLOW.getFlag());
        VNextBOROAdvancedSearchDialogInteractions.clickSaveButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        VNextBOROPageInteractions.clickEditIconForSavedSearch();
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getFlagSelected(),
                OrderMonitorFlags.YELLOW.getFlag(), "The flag hasn't been selected");
        VNextBOROAdvancedSearchDialogInteractions.clickDeleteButton();
        VNextBOConfirmationDialogInteractions.clickConfirmButton();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 45)
    public void verifyUserCanUseStoredAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        final String searchName = data.getSearchName() + RandomStringUtils.randomAlphanumeric(3);
        VNextBOROAdvancedSearchDialogInteractions.setFlag(OrderMonitorFlags.WHITE.getFlag());
        VNextBOROAdvancedSearchDialogInteractions.setSearchName(searchName);
        VNextBOROAdvancedSearchDialogInteractions.clickSaveButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        VNextBOROPageSteps.setSavedSearchOption(searchName);
        VNextBOROPageInteractions.clickAdvancedSearchCaret();
        VNextBOROAdvancedSearchDialogInteractions.setFlag(OrderMonitorFlags.WHITE.getFlag());
        VNextBOROAdvancedSearchDialogInteractions.setSearchName(searchName);
        VNextBOROAdvancedSearchDialogInteractions.clickClearButton();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 46)
    public void verifyUserCanCloseAdvancedSearchWindow(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.clickAdvancedSearchCloseButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 47)
    public void verifyUserCanClearAllFieldsInAdvancedSearchWindow(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogSteps.setAdvancedSearchDialogData(data, TimeFrameValues.TIMEFRAME_LASTMONTH, OrderMonitorFlags.RED);

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
        data.getAdvancedSearchDialogDefaultTextList().forEach(System.out::println);
        Assert.assertTrue(VNextBOROAdvancedSearchDialogInteractions
                .getAdvancedSearchDialogElementsValues()
                .containsAll(data.getAdvancedSearchDialogDefaultTextList()), "The data hasn't been cleared");
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 48)
    public void verifyUserSeesSearchMaskWithAllEnteredInfo(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
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
        final List<String> searchResultsList = VNextBOROPageInteractions.getSearchResultsList();
        searchResultsList.forEach(System.out::println);

        System.out.println();
        System.out.println("Displayed values");
        final List<String> advancedSearchDialogElements = data.getFullAdvancedSearchElementsList();
        advancedSearchDialogElements.forEach(System.out::println);

        Assert.assertTrue(searchResultsList.containsAll(advancedSearchDialogElements), "The data hasn't been inserted");
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 49)
    public void verifyUserCanSearchByCurrentPhaseStatus(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogSteps.searchByActivePhase(
                data.getPhase(), data.getPhaseStatus(), TimeFrameValues.TIMEFRAME_LASTYEAR.getName());
        VNextBOROPageValidations.verifyOrdersAfterSearchByPhase(data.getPhase());
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 50)
    public void verifyUserCanSearchByActivePhaseStatus(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogSteps.searchByActivePhase(
                data.getPhase(), data.getPhaseStatus(), TimeFrameValues.TIMEFRAME_LASTYEAR.getName());
        VNextBOROPageInteractions.clickWoLink();
        VNextBORODetailsPageValidations.verifyPhaseStatusOrPartPhaseStatusIsDisplayed(
                data.getPhase(), data.getPhaseStatus(), data.getServiceStatuses());
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 51)
    public void verifyUserCanSearchByNotCompletedPhaseStatus(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogSteps.searchByActivePhase(
                data.getPhase(), data.getPhaseStatus(), TimeFrameValues.TIMEFRAME_LASTYEAR.getName());
        VNextBOROPageInteractions.clickWoLink();
        VNextBORODetailsPageValidations.verifyPhaseStatus(data.getPhase(), data.getServiceStatuses());
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 52)
    public void verifyUserCanSearchByCompletedPhaseStatus(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogSteps.searchByActivePhase(
                data.getPhase(), data.getPhaseStatus(), TimeFrameValues.TIMEFRAME_LASTYEAR.getName());
        VNextBOROPageInteractions.clickWoLink();
        VNextBORODetailsPageValidations.verifyPhaseStatus(data.getPhase(), data.getServiceStatuses());
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 53)
    public void verifyUserCanSearchByHasProblemsOption(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_YEARTODATE.getName());
        VNextBOROAdvancedSearchDialogInteractions.clickHasProblemsCheckbox();
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOROPageValidations.verifyAllOrdersHaveProblemIndicators();
    }

    // todo fails, bug #97510
    // https://cyb.tpondemand.com/restui/board.aspx?#page=bug/97510&appConfig=eyJhY2lkIjoiRTU0NTRFNkE4OEZBODJDQTIzRjZFQzI0Q0NBQUEyNEYifQ==
    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 54)
    public void verifyUserCanSearchBySortByRoNumOption(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
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

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 55)
    public void verifyUserCanSearchBySortByPriorityOldestToNewestOption(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_YEARTODATE.getName());
        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogInteractions.setSortBy(data.getSortByOptions()[0]);
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOPageSwitcherSteps.changeItemsPerPage(data.getPages().getHundred());
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
    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 56)
    public void verifyUserCanSearchBySortByPriorityNewestToOldest(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_YEARTODATE.getName());
        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogInteractions.setSortBy(data.getSortByOptions()[0]);
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOPageSwitcherSteps.changeItemsPerPage(data.getPages().getHundred());
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
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 57)
    public void verifyUserCanSearchBySortByArbitrationDateOption(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR.getName());
        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogInteractions.setSortBy(data.getSortByOptions()[0]);
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOPageSwitcherSteps.changeItemsPerPage(data.getPages().getHundred());

        final List<String> arbitrationDatesList = VNextBOROPageInteractions.getArbitrationDatesList();
        arbitrationDatesList.forEach(System.out::println);

        Assert.assertTrue(VNextBOROPageValidations.isArbitrationDateMoreThanCurrentDate(arbitrationDatesList.get(0)),
                "The arbitration date of the first order is not after the current date");
        Assert.assertTrue(VNextBOROPageValidations.areOrdersWithArbitrationDatesDisplayedBeforeAnotherOrders(
                arbitrationDatesList), "The orders with arbitration dates are not displayed before the orders without them");
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 58)
    public void verifyUserCanSearchByTimeFrameLast30Days(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_30_DAYS.getName());
        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(
                OrderMonitorRepairStatuses.IN_PROGRESS_ACTIVE.getValue());
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOROPageValidations.verifyOrdersAfterSearchByTimeFrame(
                CustomDateProvider.getLastThirtyDaysStartDate());
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 59)
    public void verifyUserCanSearchByTimeFrameWeekToDate(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_WEEKTODATE.getName());
        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(
                OrderMonitorRepairStatuses.IN_PROGRESS_ACTIVE.getValue());
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOROPageValidations.verifyOrdersAfterSearchByTimeFrame(
                CustomDateProvider.getWeekToDateStartDate());
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 60)
    public void verifyUserCanSearchByTimeFrameLastWeek(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTWEEK.getName());
        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(
                OrderMonitorRepairStatuses.IN_PROGRESS_ACTIVE.getValue());
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOROPageValidations.verifyOrdersAfterSearchByTimeFrame(
                CustomDateProvider.getLastWeekStartDate());
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 61)
    public void verifyUserCanSearchByTimeFrameToday(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_TODAY.getName());
        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(
                OrderMonitorRepairStatuses.IN_PROGRESS_ACTIVE.getValue());
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOROPageValidations.verifyOrdersAfterSearchByTimeFrame(
                CustomDateProvider.getCurrentDateLocalized());
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 62)
    public void verifyUserCanSearchByTimeFrameMonthToDate(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_MONTHTODATE.getName());
        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(
                OrderMonitorRepairStatuses.IN_PROGRESS_ACTIVE.getValue());
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOROPageValidations.verifyOrdersAfterSearchByTimeFrame(
                CustomDateProvider.getLastMonthStartDate(), CustomDateProvider.getMonthStartDate());
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 63)
    public void verifyUserCanSearchByTimeFrameYearToDate(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_YEARTODATE.getName());
        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(
                OrderMonitorRepairStatuses.IN_PROGRESS_ACTIVE.getValue());
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOROPageValidations.verifyOrdersAfterSearchByTimeFrame(
                CustomDateProvider.getYearToDateStartDate());
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 64)
    public void verifyUserCanSearchByTimeFrameLastYear(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR.getName());
        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(
                OrderMonitorRepairStatuses.IN_PROGRESS_ACTIVE.getValue());
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOROPageValidations.verifyOrdersAfterSearchByTimeFrame(
                CustomDateProvider.getYearToDateStartDate(), CustomDateProvider.getYearStartDate());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 65)
    public void verifyUserCanSeeDefaultSearchSettings(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final String defaultSearchFilterText = VNextBOROPageInteractions.getSearchFilterText();

        Assert.assertTrue(defaultSearchFilterText.contains(TimeFrameValues.TIMEFRAME_30_DAYS.getName()),
                "The search filter doesn't contain the timeFrame " + TimeFrameValues.TIMEFRAME_30_DAYS.getName());
        Assert.assertTrue(defaultSearchFilterText.contains(OrderMonitorRepairStatuses.IN_PROGRESS_ALL.getValue()),
                "The search filter doesn't contain the repair status "
                        + OrderMonitorRepairStatuses.IN_PROGRESS_ALL.getValue());

        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_90_DAYS.getName());
        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(
                OrderMonitorRepairStatuses.IN_PROGRESS_ACTIVE.getValue());
        VNextBOROAdvancedSearchDialogSteps.search();

        String searchFilterText = VNextBOROPageInteractions.getSearchFilterText();
        Assert.assertTrue(searchFilterText.contains(TimeFrameValues.TIMEFRAME_90_DAYS.getName()),
                "The search filter doesn't contain the timeFrame " + TimeFrameValues.TIMEFRAME_90_DAYS.getName());
        Assert.assertTrue(searchFilterText.contains(OrderMonitorRepairStatuses.IN_PROGRESS_ACTIVE.getValue()),
                "The search filter doesn't contain the repair status "
                        + OrderMonitorRepairStatuses.IN_PROGRESS_ACTIVE.getValue());

        VNextBOROPageSteps.openRODetailsPage();
        VNextBORODetailsPageSteps.openRoPageByClickingBreadCrumbRo();
        searchFilterText = VNextBOROPageInteractions.getSearchFilterText();
        Assert.assertTrue(searchFilterText.contains(TimeFrameValues.TIMEFRAME_90_DAYS.getName()),
                "The search filter doesn't contain the timeFrame " + TimeFrameValues.TIMEFRAME_90_DAYS.getName());
        Assert.assertTrue(searchFilterText.contains(OrderMonitorRepairStatuses.IN_PROGRESS_ACTIVE.getValue()),
                "The search filter doesn't contain the repair status "
                        + OrderMonitorRepairStatuses.IN_PROGRESS_ACTIVE.getValue());
    }
}