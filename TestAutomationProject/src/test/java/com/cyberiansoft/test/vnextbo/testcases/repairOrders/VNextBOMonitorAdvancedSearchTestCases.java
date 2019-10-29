package com.cyberiansoft.test.vnextbo.testcases.repairOrders;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.interactions.VNextBOConfirmationDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.screens.*;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBOROAdvancedSearchDialog;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBOROWebPage;
import com.cyberiansoft.test.vnextbo.steps.HomePageSteps;
import com.cyberiansoft.test.vnextbo.steps.VNextBOHeaderPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.repairOrders.VNextBORepairOrdersPageSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOMonitorAdvancedSearchTestCases extends BaseTestCase {
    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/VNextBOMonitorAdvancedSearchData.json";

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    private VNextBOBreadCrumbInteractions breadCrumbInteractions;
    private VNextBOROWebPage repairOrdersPage;

    @BeforeMethod
    public void BackOfficeLogin() {
        browserType = BaseUtils.getBrowserType(VNextBOConfigInfo.getInstance().getDefaultBrowser());
        try {
            DriverBuilder.getInstance().setDriver(browserType);
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
        webdriver = DriverBuilder.getInstance().getDriver();

        webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOCompanionappURL());
        String userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
        String userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();

        VNextBOLoginScreenWebPage loginPage = PageFactory.initElements(webdriver, VNextBOLoginScreenWebPage.class);
        loginPage.userLogin(userName, userPassword);

        repairOrdersPage = PageFactory.initElements(webdriver, VNextBOROWebPage.class);
        breadCrumbInteractions = new VNextBOBreadCrumbInteractions();
    }

    @AfterMethod
    public void BackOfficeLogout() {
        VNextBOHeaderPanelSteps.logout();

        if (DriverBuilder.getInstance().getDriver() != null)
            DriverBuilder.getInstance().quitDriver();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSelectCustomer(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        
        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog.typeCustomerName(data.getCustomer());
        Assert.assertTrue(advancedSearchDialog.isCustomerDisplayed(data.getCustomer()));
        advancedSearchDialog.selectCustomerNameFromBoxList(data.getCustomer());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByEmployee(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setCustomer(data.getCustomer())
                .setTimeFrame(data.getTimeFrame())
                .typeEmployeeName(data.getEmployee());

        Assert.assertTrue(advancedSearchDialog.isEmployeeDisplayed(data.getEmployee()));
        advancedSearchDialog
                .selectEmployeeNameFromBoxList(data.getEmployee())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByPhase(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setCustomer(data.getCustomer())
                .setPhase(data.getPhase())
                .setTimeFrame(data.getTimeFrame())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDepartment(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setCustomer(data.getCustomer())
                .setDepartment(data.getDepartment())
                .setTimeFrame(data.getTimeFrame())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByWoType(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setCustomer(data.getCustomer())
                .setWoType(data.getWoType())
                .setTimeFrame(data.getTimeFrame())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByWoNumber(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setCustomer(data.getCustomer())
                .setWoNum(data.getWoNum())
                .setTimeFrame(data.getTimeFrame())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRoNumber(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setCustomer(data.getCustomer())
                .setRoNum(data.getRoNum())
                .setTimeFrame(data.getTimeFrame())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByStockNumber(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setCustomer(data.getCustomer())
                .setStockNum(data.getStockNum())
                .setTimeFrame(data.getTimeFrame())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByVinNumber(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setCustomer(data.getCustomer())
                .setVinNum(data.getVinNum())
                .setTimeFrame(data.getTimeFrame())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInPhaseLess(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setDaysInPhase(data.getDaysInPhase())
                .typeNumberOfDaysForDaysInPhaseFromInput(data.getDaysNum())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByWhiteFlag(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setFlag(data.getFlag())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        if (!repairOrdersPage.isTableDisplayed()) {
            Assert.assertTrue(repairOrdersPage.isTextNoRecordsDisplayed(), "The text notification is not displayed");
        } else {
            Assert.assertTrue(repairOrdersPage.getNumOfOrdersOnPage() > 0, "The orders are not displayed");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByCustomer(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog.typeCustomerName(data.getCustomer());
        Assert.assertTrue(advancedSearchDialog.isCustomerDisplayed(data.getCustomer()));
        advancedSearchDialog
                .selectCustomerNameFromBoxList(data.getCustomer())
                .setTimeFrame(data.getTimeFrame())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByName(data.getCustomer()),
                "The work order hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRedFlag(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setFlag(data.getFlag())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        if (!repairOrdersPage.isTableDisplayed()) {
            Assert.assertTrue(repairOrdersPage.isTextNoRecordsDisplayed(), "The text notification is not displayed");
        } else {
            Assert.assertTrue(repairOrdersPage.getNumOfOrdersOnPage() > 0, "The orders are not displayed");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByOrangeFlag(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setFlag(data.getFlag())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        if (!repairOrdersPage.isTableDisplayed()) {
            Assert.assertTrue(repairOrdersPage.isTextNoRecordsDisplayed(), "The text notification is not displayed");
        } else {
            Assert.assertTrue(repairOrdersPage.getNumOfOrdersOnPage() > 0, "The orders are not displayed");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByYellowFlag(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setFlag(data.getFlag())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        if (!repairOrdersPage.isTableDisplayed()) {
            Assert.assertTrue(repairOrdersPage.isTextNoRecordsDisplayed(), "The text notification is not displayed");
        } else {
            Assert.assertTrue(repairOrdersPage.getNumOfOrdersOnPage() > 0, "The orders are not displayed");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByGreenFlag(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setFlag(data.getFlag())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        if (!repairOrdersPage.isTableDisplayed()) {
            Assert.assertTrue(repairOrdersPage.isTextNoRecordsDisplayed(), "The text notification is not displayed");
        } else {
            Assert.assertTrue(repairOrdersPage.getNumOfOrdersOnPage() > 0, "The orders are not displayed");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByBlueFlag(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setFlag(data.getFlag())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        if (!repairOrdersPage.isTableDisplayed()) {
            Assert.assertTrue(repairOrdersPage.isTextNoRecordsDisplayed(), "The text notification is not displayed");
        } else {
            Assert.assertTrue(repairOrdersPage.getNumOfOrdersOnPage() > 0, "The orders are not displayed");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByPurpleFlag(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setFlag(data.getFlag())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        if (!repairOrdersPage.isTableDisplayed()) {
            Assert.assertTrue(repairOrdersPage.isTextNoRecordsDisplayed(), "The text notification is not displayed");
        } else {
            Assert.assertTrue(repairOrdersPage.getNumOfOrdersOnPage() > 0, "The orders are not displayed");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInPhaseLessOrEqual(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setDaysInPhase(data.getDaysInPhase())
                .typeNumberOfDaysForDaysInPhaseFromInput(data.getDaysNum())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInPhaseEqual(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setDaysInPhase(data.getDaysInPhase())
                .typeNumberOfDaysForDaysInPhaseFromInput(data.getDaysNum())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInPhaseMoreOrEqual(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setDaysInPhase(data.getDaysInPhase())
                .typeNumberOfDaysForDaysInPhaseFromInput(data.getDaysNum())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInPhaseMore(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setDaysInPhase(data.getDaysInPhase())
                .typeNumberOfDaysForDaysInPhaseFromInput(data.getDaysNum())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInPhaseBetween(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setDaysInPhase(data.getDaysInPhase())
                .typeNumberOfDaysForDaysInPhaseFromInput(data.getDaysNumStart())
                .typeNumberOfDaysForDaysInPhaseToInput(data.getDaysNum())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInProcessLess(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setDaysInProcess(data.getDaysInProcess())
                .typeNumberOfDaysForDaysInProcessFromInput(data.getDaysNum())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInProcessLessOrEqual(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setDaysInProcess(data.getDaysInProcess())
                .typeNumberOfDaysForDaysInProcessFromInput(data.getDaysNum())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInProcessEqual(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setDaysInProcess(data.getDaysInProcess())
                .typeNumberOfDaysForDaysInProcessFromInput(data.getDaysNum())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInProcessMoreOrEqual(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setDaysInProcess(data.getDaysInProcess())
                .typeNumberOfDaysForDaysInProcessFromInput(data.getDaysNum())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInProcessMore(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setDaysInProcess(data.getDaysInProcess())
                .typeNumberOfDaysForDaysInProcessFromInput(data.getDaysNum())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByDaysInProcessBetween(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setDaysInProcess(data.getDaysInProcess())
                .typeNumberOfDaysForDaysInProcessFromInput(data.getDaysNumStart())
                .typeNumberOfDaysForDaysInProcessToInput(data.getDaysNum())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByCustomTimeFrame(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        final VNextBOCalendarWidgetDialog calendarWidgetDialog = PageFactory
                .initElements(webdriver, VNextBOCalendarWidgetDialog.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        DateTimeFormatter format = DateTimeFormatter.ofPattern("M/d/yyyy");

        LocalDateTime now = LocalDateTime.now(ZoneId.of("US/Pacific"));
        final int monthValue = now.getMonthValue();
        final int prevMonthValue = monthValue - 1;
        LocalDateTime before = now.minusDays(7);

        String beforeMinusMonth = calendarWidgetDialog.getMonthReplace(monthValue, prevMonthValue, before);
        String nowMinusMonth = calendarWidgetDialog.getMonthReplace(monthValue, prevMonthValue, now);
        System.out.println("beforeMinusMonth: " + beforeMinusMonth);
        System.out.println("nowMinusMonth: " + nowMinusMonth);
        String beforeFormatted = before.format(format);
        String nowFormatted = now.format(format);
        System.out.println("beforeFormatted: " + beforeFormatted);
        System.out.println("nowFormatted: " + nowFormatted);

        advancedSearchDialog
                .setTimeFrame(data.getTimeFrame())
                .clickFromDateButton()
                .selectFromDate(beforeMinusMonth, advancedSearchDialog)
                .clickToDateButton()
                .selectToDate(nowMinusMonth, advancedSearchDialog)
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByLast3WeeksTimeFrame(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setTimeFrame(data.getTimeFrame())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        if (!repairOrdersPage.isTableDisplayed()) {
            Assert.assertTrue(repairOrdersPage.isTextNoRecordsDisplayed(), "The text notification is not displayed");
        } else {
            Assert.assertTrue(repairOrdersPage.getNumOfOrdersOnPage() > 0, "The orders are not displayed");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRepairStatusInProgressRework(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog.setRepairStatus(data.getRepairStatus());
        Assert.assertEquals(advancedSearchDialog.getRepairStatusOption(), data.getRepairStatus());
        advancedSearchDialog.clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRepairStatusInProgressActive(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setRepairStatus(data.getRepairStatus())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRepairStatusInProgressQueued(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setRepairStatus(data.getRepairStatus())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRepairStatusInProgressOnHold(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setRepairStatus(data.getRepairStatus())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRepairStatusInProgressNotOnHold(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setRepairStatus(data.getRepairStatus())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRepairStatusInProgressQAReady(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setRepairStatus(data.getRepairStatus())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRepairStatusCompletedQAReady(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setRepairStatus(data.getRepairStatus())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRepairStatusCompletedNotBilled(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setRepairStatus(data.getRepairStatus())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRepairStatusCompletedOnSite(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setRepairStatus(data.getRepairStatus())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRepairStatusCompletedAll(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setRepairStatus(data.getRepairStatus())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRepairStatusInProgressAll(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setRepairStatus(data.getRepairStatus())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanFillInAdvancedSearchWindow(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setCustomer(data.getCustomer())
                .setEmployee(data.getEmployee())
                .setPhase(data.getPhase())
                .setDepartment(data.getDepartment())
                .setWoType(data.getWoType())
                .setWoNum(data.getWoNum())
                .setRoNum(data.getRoNum())
                .setStockNum(data.getStockNum())
                .setVinNum(data.getVinNum())
                .setTimeFrame(data.getTimeFrame())
                .setRepairStatus(data.getRepairStatus())
                .setDaysInProcess(data.getDaysInProcess())
                .setDaysInPhase(data.getDaysInPhase())
                .setFlag(data.getFlag())
                .setSearchName(data.getSearchName())
                .clickSearchButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSaveAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        final String searchName = data.getSearchName() + RandomStringUtils.randomAlphanumeric(3);
        advancedSearchDialog
                .setFlag(data.getFlag())
                .setSearchName(searchName)
                .clickSaveButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanEditSavedAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocations()[0]);
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        final String searchName = data.getSearchName() + RandomStringUtils.randomAlphanumeric(3);
        advancedSearchDialog
                .setFlag(data.getFlags()[0])
                .setSearchName(searchName)
                .clickSaveButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        breadCrumbInteractions.setLocation(data.getLocations()[1]);
        breadCrumbInteractions.setLocation(data.getLocations()[0]);
        new VNextBORepairOrdersPageSteps().setSavedSearchOption(searchName);
        repairOrdersPage
                .clickEditIconForSavedSearch()
                .setFlag(data.getFlags()[1])
                .clickSaveButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        repairOrdersPage.clickEditIconForSavedSearch();
        Assert.assertEquals(advancedSearchDialog.getFlagSelected(), data.getFlags()[1],
                "The flag hasn't been selected");
        advancedSearchDialog.clickDeleteButton();
        new VNextBOConfirmationDialogInteractions().clickConfirmButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanUseStoredAdvancedSearch(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        final String searchName = data.getSearchName() + RandomStringUtils.randomAlphanumeric(3);
        advancedSearchDialog
                .setFlag(data.getFlag())
                .setSearchName(searchName)
                .clickSaveButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());

        new VNextBORepairOrdersPageSteps().setSavedSearchOption(searchName);
        repairOrdersPage
                .clickAdvancedSearchCaret()
                .setFlag(data.getFlag())
                .setSearchName(searchName)
                .clickClearButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCloseAdvancedSearchWindow(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog.clickAdvancedSearchCloseButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanClearAllFieldsInAdvancedSearchWindow(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setCustomer(data.getCustomer())
                .setEmployee(data.getEmployee())
                .setPhase(data.getPhase())
                .setDepartment(data.getDepartment())
                .setWoType(data.getWoType())
                .setWoNum(data.getWoNum())
                .setRoNum(data.getRoNum())
                .setStockNum(data.getStockNum())
                .setVinNum(data.getVinNum())
                .setTimeFrame(data.getTimeFrame())
                .setRepairStatus(data.getRepairStatus())
                .setDaysInProcess(data.getDaysInProcess())
                .setDaysInPhase(data.getDaysInPhase())
                .setFlag(data.getFlag())
                .setSearchName(data.getSearchName());

        System.out.println("Before clear");
        advancedSearchDialog.getAdvancedSearchDialogElements().forEach(System.out::println);
        System.out.println("\nData before clear");
        data.getAdvancedSearchDialogElements().forEach(System.out::println);

        Assert.assertTrue(advancedSearchDialog
                .getAdvancedSearchDialogElements()
                .containsAll(data.getAdvancedSearchDialogElements()), "The data hasn't been inserted");

        advancedSearchDialog.clickClearButton();
        System.out.println("After clear");
        advancedSearchDialog.getAdvancedSearchDialogElements().forEach(System.out::println);
        System.out.println("\nData after clear");
        data.getAdvancedSearchDialogElements().forEach(System.out::println);
        Assert.assertTrue(advancedSearchDialog
                .getAdvancedSearchDialogElements()
                .containsAll(data.getAdvancedSearchDialogDefaultTextList()), "The data hasn't been cleared");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserSeesSearchMaskWithAllEnteredInfo(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        final VNextBOROAdvancedSearchDialog advancedSearchDialog = repairOrdersPage.clickAdvancedSearchCaret();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogDisplayed(),
                "The advanced search dialog is not opened");

        advancedSearchDialog
                .setCustomer(data.getCustomer())
                .setEmployee(data.getEmployee())
                .setPhase(data.getPhase())
                .setDepartment(data.getDepartment())
                .setWoType(data.getWoType())
                .setWoNum(data.getWoNum())
                .setRoNum(data.getRoNum())
                .setStockNum(data.getStockNum())
                .setVinNum(data.getVinNum())
                .setTimeFrame(data.getTimeFrame())
                .setRepairStatus(data.getRepairStatus())
                .setDaysInProcess(data.getDaysInProcess())
                .typeNumberOfDaysForDaysInProcessFromInput(data.getDaysNumList()[0])
                .setDaysInPhase(data.getDaysInPhase())
                .typeNumberOfDaysForDaysInPhaseFromInput(data.getDaysNumList()[1])
                .setFlag(data.getFlag())
                .setSearchName(data.getSearchName())
                .clickSearchButton();

        Assert.assertTrue(advancedSearchDialog.isAdvancedSearchDialogNotDisplayed(),
                "The advanced search dialog is not closed");

        repairOrdersPage.movePointerToSearchResultsField();

        System.out.println("Inserted values");
        final List<String> searchResultsList = repairOrdersPage
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
}