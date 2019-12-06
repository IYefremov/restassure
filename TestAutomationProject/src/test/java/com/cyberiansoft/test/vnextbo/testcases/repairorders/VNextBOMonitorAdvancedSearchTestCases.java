package com.cyberiansoft.test.vnextbo.testcases.repairorders;

import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.VNextBOConfirmationDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOCalendarWidgetDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROAdvancedSearchDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROPageInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOCalendarWidgetDialog;
import com.cyberiansoft.test.vnextbo.steps.HomePageSteps;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROAdvancedSearchDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROPageSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.utils.WebConstants;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOROAdvancedSearchDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBORODetailsPageValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOROPageValidations;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class VNextBOMonitorAdvancedSearchTestCases extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getMonitorAdvancedSearchTD();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserCanSelectCustomer(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.typeCustomerName(data.getCustomer());
        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isCustomerDisplayed(data.getCustomer()));
        VNextBOROAdvancedSearchDialogInteractions.selectCustomerNameFromBoxList(data.getCustomer());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyUserCanSearchByEmployee(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setCustomer(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(data.getTimeFrame());
        VNextBOROAdvancedSearchDialogInteractions.typeEmployeeName(data.getEmployee());

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isEmployeeDisplayed(data.getEmployee()));
        VNextBOROAdvancedSearchDialogInteractions.selectEmployeeNameFromBoxList(data.getEmployee());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
    public void verifyUserCanSearchByPhase(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setCustomer(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.setPhase(data.getPhase());
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_YEARTODATE.getName());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
    public void verifyUserCanSearchByDepartment(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setCustomer(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.setDepartment(data.getDepartment());
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_YEARTODATE.getName());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyUserCanSearchByWoType(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setCustomer(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.setWoType(data.getWoType());
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_YEARTODATE.getName());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyUserCanSearchByWoNumber(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setCustomer(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.setWoNum(data.getWoNum());
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_YEARTODATE.getName());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 6)
    public void verifyUserCanSearchByRoNumber(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setCustomer(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.setRoNum(data.getRoNum());
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_YEARTODATE.getName());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 7)
    public void verifyUserCanSearchByStockNumber(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setCustomer(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.setStockNum(data.getStockNum());
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_YEARTODATE.getName());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 8)
    public void verifyUserCanSearchByVinNumber(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setCustomer(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.setVinNum(data.getVinNum());
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_YEARTODATE.getName());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 9)
    public void verifyUserCanSearchByDaysInPhaseLess(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setDaysInPhase(data.getDaysInPhase());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInPhaseFromInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 10)
    public void verifyUserCanSearchByWhiteFlag(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setFlag(data.getFlag());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        if (!VNextBOROPageValidations.isTableDisplayed()) {
            Assert.assertTrue(VNextBOROPageValidations.isTextNoRecordsDisplayed(), "The text notification is not displayed");
        } else {
            Assert.assertTrue(VNextBOROPageInteractions.getNumOfOrdersOnPage() > 0, "The orders are not displayed");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 11)
    public void verifyUserCanSearchByCustomer(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.typeCustomerName(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.selectCustomerNameFromBoxList(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(data.getTimeFrame());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 12)
    public void verifyUserCanSearchByRedFlag(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setFlag(data.getFlag());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        if (!VNextBOROPageValidations.isTableDisplayed()) {
            Assert.assertTrue(VNextBOROPageValidations.isTextNoRecordsDisplayed(), "The text notification is not displayed");
        } else {
            Assert.assertTrue(VNextBOROPageInteractions.getNumOfOrdersOnPage() > 0, "The orders are not displayed");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 13)
    public void verifyUserCanSearchByOrangeFlag(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setFlag(data.getFlag());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        if (!VNextBOROPageValidations.isTableDisplayed()) {
            Assert.assertTrue(VNextBOROPageValidations.isTextNoRecordsDisplayed(), "The text notification is not displayed");
        } else {
            Assert.assertTrue(VNextBOROPageInteractions.getNumOfOrdersOnPage() > 0, "The orders are not displayed");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 14)
    public void verifyUserCanSearchByYellowFlag(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setFlag(data.getFlag());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        if (!VNextBOROPageValidations.isTableDisplayed()) {
            Assert.assertTrue(VNextBOROPageValidations.isTextNoRecordsDisplayed(), "The text notification is not displayed");
        } else {
            Assert.assertTrue(VNextBOROPageInteractions.getNumOfOrdersOnPage() > 0, "The orders are not displayed");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 15)
    public void verifyUserCanSearchByGreenFlag(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setFlag(data.getFlag());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        if (!VNextBOROPageValidations.isTableDisplayed()) {
            Assert.assertTrue(VNextBOROPageValidations.isTextNoRecordsDisplayed(), "The text notification is not displayed");
        } else {
            Assert.assertTrue(VNextBOROPageInteractions.getNumOfOrdersOnPage() > 0, "The orders are not displayed");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 16)
    public void verifyUserCanSearchByBlueFlag(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setFlag(data.getFlag());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        if (!VNextBOROPageValidations.isTableDisplayed()) {
            Assert.assertTrue(VNextBOROPageValidations.isTextNoRecordsDisplayed(), "The text notification is not displayed");
        } else {
            Assert.assertTrue(VNextBOROPageInteractions.getNumOfOrdersOnPage() > 0, "The orders are not displayed");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 17)
    public void verifyUserCanSearchByPurpleFlag(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setFlag(data.getFlag());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        if (!VNextBOROPageValidations.isTableDisplayed()) {
            Assert.assertTrue(VNextBOROPageValidations.isTextNoRecordsDisplayed(), "The text notification is not displayed");
        } else {
            Assert.assertTrue(VNextBOROPageInteractions.getNumOfOrdersOnPage() > 0, "The orders are not displayed");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 18)
    public void verifyUserCanSearchByDaysInPhaseLessOrEqual(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setDaysInPhase(data.getDaysInPhase());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInPhaseFromInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 19)
    public void verifyUserCanSearchByDaysInPhaseEqual(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setDaysInPhase(data.getDaysInPhase());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInPhaseFromInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 20)
    public void verifyUserCanSearchByDaysInPhaseMoreOrEqual(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setDaysInPhase(data.getDaysInPhase());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInPhaseFromInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 21)
    public void verifyUserCanSearchByDaysInPhaseMore(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setDaysInPhase(data.getDaysInPhase());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInPhaseFromInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 22)
    public void verifyUserCanSearchByDaysInPhaseBetween(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setDaysInPhase(data.getDaysInPhase());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInPhaseFromInput(data.getDaysNumStart());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInPhaseToInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 23)
    public void verifyUserCanSearchByDaysInProcessLess(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setDaysInProcess(data.getDaysInProcess());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInProcessFromInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 24)
    public void verifyUserCanSearchByDaysInProcessLessOrEqual(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setDaysInProcess(data.getDaysInProcess());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInProcessFromInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 25)
    public void verifyUserCanSearchByDaysInProcessEqual(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setDaysInProcess(data.getDaysInProcess());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInProcessFromInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 26)
    public void verifyUserCanSearchByDaysInProcessMoreOrEqual(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setDaysInProcess(data.getDaysInProcess());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInProcessFromInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 27)
    public void verifyUserCanSearchByDaysInProcessMore(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setDaysInProcess(data.getDaysInProcess());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInProcessFromInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 28)
    public void verifyUserCanSearchByDaysInProcessBetween(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setDaysInProcess(data.getDaysInProcess());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInProcessFromInput(data.getDaysNumStart());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInProcessToInput(data.getDaysNum());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 29)
    public void verifyUserCanSearchByCustomTimeFrame(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        final VNextBOCalendarWidgetDialog calendarWidgetDialog = PageFactory
                .initElements(webdriver, VNextBOCalendarWidgetDialog.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/M/d");

        LocalDateTime toDate = LocalDateTime.now(ZoneId.of("US/Pacific")).minusMonths(1);
        LocalDateTime fromDate = toDate.minusDays(2);
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(data.getTimeFrame());
        VNextBOROAdvancedSearchDialogInteractions.clickFromDateButton();
        VNextBOCalendarWidgetDialogInteractions.selectFromDate(fromDate.format(format));
        VNextBOROAdvancedSearchDialogInteractions.clickToDateButton();
        VNextBOCalendarWidgetDialogInteractions.selectToDate(toDate.format(format));
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 30)
    public void verifyUserCanSearchByLast90DaysTimeFrame(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS.getName());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        VNextBOROPageValidations.verifyOrdersAfterSearchByDate(CustomDateProvider.getThreeMonthsBeforeCurrentDate());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 31)
    public void verifyUserCanSearchByRepairStatusInProgressRework(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        Assert.assertEquals(VNextBOROAdvancedSearchDialogInteractions.getRepairStatusOption(), data.getRepairStatus());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 32)
    public void verifyUserCanSearchByRepairStatusInProgressActive(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 33)
    public void verifyUserCanSearchByRepairStatusInProgressQueued(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 34)
    public void verifyUserCanSearchByRepairStatusInProgressOnHold(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 35)
    public void verifyUserCanSearchByRepairStatusInProgressNotOnHold(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 36)
    public void verifyUserCanSearchByRepairStatusInProgressQAReady(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 37)
    public void verifyUserCanSearchByRepairStatusCompletedQAReady(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 38)
    public void verifyUserCanSearchByRepairStatusCompletedNotBilled(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 39)
    public void verifyUserCanSearchByRepairStatusCompletedOnSite(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 40)
    public void verifyUserCanSearchByRepairStatusCompletedAll(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 41)
    public void verifyUserCanSearchByRepairStatusInProgressAll(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 42)
    public void verifyUserCanFillInAdvancedSearchWindow(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        VNextBOROAdvancedSearchDialogInteractions.setCustomer(data.getCustomer());
        VNextBOROAdvancedSearchDialogInteractions.setEmployee(data.getEmployee());
        VNextBOROAdvancedSearchDialogInteractions.setPhase(data.getPhase());
        VNextBOROAdvancedSearchDialogInteractions.setDepartment(data.getDepartment());
        VNextBOROAdvancedSearchDialogInteractions.setWoType(data.getWoType());
        VNextBOROAdvancedSearchDialogInteractions.setWoNum(data.getWoNum());
        VNextBOROAdvancedSearchDialogInteractions.setRoNum(data.getRoNum());
        VNextBOROAdvancedSearchDialogInteractions.setStockNum(data.getStockNum());
        VNextBOROAdvancedSearchDialogInteractions.setVinNum(data.getVinNum());
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_30_DAYS.getName());
        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogInteractions.setDaysInProcess(data.getDaysInProcess());
        VNextBOROAdvancedSearchDialogInteractions.setDaysInPhase(data.getDaysInPhase());
        VNextBOROAdvancedSearchDialogInteractions.setFlag(data.getFlag());
        VNextBOROAdvancedSearchDialogInteractions.setSearchName(data.getSearchName());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 43)
    public void verifyUserCanSaveAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        final String searchName = data.getSearchName() + RandomStringUtils.randomAlphanumeric(3);
        VNextBOROAdvancedSearchDialogInteractions.setFlag(data.getFlag());
        VNextBOROAdvancedSearchDialogInteractions.setSearchName(searchName);
        VNextBOROAdvancedSearchDialogInteractions.clickSaveButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 44)
    public void verifyUserCanEditSavedAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocations()[0]);
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 45)
    public void verifyUserCanUseStoredAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROPageInteractions.clickAdvancedSearchCaret();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 46)
    public void verifyUserCanCloseAdvancedSearchWindow(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.clickAdvancedSearchCloseButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 47)
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 48)
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
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_90_DAYS.getName());
        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(data.getRepairStatus());
        VNextBOROAdvancedSearchDialogInteractions.setDaysInProcess(data.getDaysInProcess());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInProcessFromInput(data.getDaysNumList()[0]);
        VNextBOROAdvancedSearchDialogInteractions.setDaysInPhase(data.getDaysInPhase());
        VNextBOROAdvancedSearchDialogInteractions.typeNumberOfDaysForDaysInPhaseFromInput(data.getDaysNumList()[1]);
        VNextBOROAdvancedSearchDialogInteractions.setFlag(data.getFlag());
        VNextBOROAdvancedSearchDialogInteractions.setSearchName(data.getSearchName());
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
        final List<String> advancedSearchDialogElements = data.getFullAdvancedSearchElementsList();
        advancedSearchDialogElements.forEach(System.out::println);

        Assert.assertTrue(new ArrayList<String>(Arrays.asList(VNextBOROPageInteractions.getSearchFilterText().split("; "))).containsAll(advancedSearchDialogElements),
                "The data hasn't been inserted");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 49)
    public void verifyUserCanSearchByCurrentPhaseStatus(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogSteps.searchByActivePhase(
                data.getPhase(), data.getPhaseStatus(), WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR.getName());

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        VNextBOROPageValidations.verifyOrdersAfterSearchByPhase(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 50)
    public void verifyUserCanSearchByActivePhaseStatus(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogSteps.searchByActivePhase(
                data.getPhase(), data.getPhaseStatus(), WebConstants.TimeFrameValues.TIMEFRAME_LASTYEAR.getName());

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        VNextBOROPageInteractions.clickWoLink();
        WaitUtilsWebDriver.waitForLoading();
        VNextBORODetailsPageValidations.verifyPhaseStatusOrPartPhaseStatusIsDisplayed(
                data.getPhase(), data.getPhaseStatus(), data.getServiceStatuses());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 51)
    public void verifyUserCanSearchByNotCompletedPhaseStatus(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogSteps.searchByActivePhase(
                data.getPhase(), data.getPhaseStatus(), WebConstants.TimeFrameValues.TIMEFRAME_YEARTODATE.getName());

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
        WaitUtilsWebDriver.waitForLoading();

        VNextBOROPageInteractions.clickWoLink();
        VNextBORODetailsPageValidations.verifyPhaseStatus(data.getPhase(), data.getServiceStatuses());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 52)
    public void verifyUserCanSearchByCompletedPhaseStatus(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogSteps.searchByActivePhase(
                data.getPhase(), data.getPhaseStatus(), WebConstants.TimeFrameValues.TIMEFRAME_YEARTODATE.getName());

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        VNextBOROPageInteractions.clickWoLink();
        VNextBORODetailsPageValidations.verifyPhaseStatus(data.getPhase(), data.getServiceStatuses());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 53)
    public void verifyUserCanSearchByHasProblemsOption(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();

        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_YEARTODATE.getName());
        VNextBOROAdvancedSearchDialogInteractions.clickHasProblemsCheckbox();
        VNextBOROAdvancedSearchDialogInteractions.clickSearchButton();

        Assert.assertTrue(VNextBOROAdvancedSearchDialogValidations.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        VNextBOROPageValidations.verifyAllOrdersHaveProblemIndicators();
    }
}