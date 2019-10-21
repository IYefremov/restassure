package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.screens.*;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBOROWebPage;
import com.cyberiansoft.test.vnextbo.steps.HomePageSteps;
import com.cyberiansoft.test.vnextbo.steps.VNextBOHeaderPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.repairOrders.VNextBOROSimpleSearchSteps;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOMonitorSimpleSearchTestCases extends BaseTestCase {
    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/VNextBOMonitorSimpleSearchData.json";

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

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
    }

    @AfterMethod
    public void BackOfficeLogout() {
        VNextBOHeaderPanelSteps.logout();

        if (DriverBuilder.getInstance().getDriver() != null)
            DriverBuilder.getInstance().quitDriver();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByVIN(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getVinNum());
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getVinNum()),
                "The work order is not displayed after search by VIN after clicking the 'Search' icon");
        repairOrdersPage.clickCancelSearchIcon();
        VNextBOROSimpleSearchSteps.searchByTextWithEnter(data.getVinNum());

        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getVinNum()),
                "The work order is not displayed after search by VIN after clicking the 'Enter' key");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByOrderNum(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getOrderNumber());
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByOrderNumber(data.getOrderNumber()),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");
        repairOrdersPage.clickCancelSearchIcon();
        VNextBOROSimpleSearchSteps.searchByTextWithEnter(data.getOrderNumber());

        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByOrderNumber(data.getOrderNumber()),
                "The work order is not displayed after search by order number after clicking the 'Enter' key");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRoNum(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getRoNumber());
        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByRoNumber(data.getRoNumber()),
                "The work order is not displayed after search by RO number after clicking the 'Search' icon");
        repairOrdersPage.clickCancelSearchIcon();
        VNextBOROSimpleSearchSteps.searchByTextWithEnter(data.getRoNumber());

        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByRoNumber(data.getRoNumber()),
                "The work order is not displayed after search by RO number after clicking the 'Enter' key");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByFirstName(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getFirstName());

        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByFirstName(data.getFirstName()),
                "The work order is not displayed after search by first name after clicking the 'Search' icon");
        repairOrdersPage.clickCancelSearchIcon();
        VNextBOROSimpleSearchSteps.searchByTextWithEnter(data.getFirstName());

        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByFirstName(data.getFirstName()),
                "The work order is not displayed after search by first name after clicking the 'Enter' key");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByLastName(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        VNextBOROSimpleSearchSteps.searchByText(data.getLastName());

        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByLastName(data.getLastName()),
                "The work order is not displayed after search by last name after clicking the 'Search' icon");
        repairOrdersPage.clickCancelSearchIcon();
        new VNextBOROSimpleSearchSteps().searchByTextWithEnter(data.getLastName());

        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByLastName(data.getLastName()),
                "The work order is not displayed after search by last name after clicking the 'Enter' key");
    }
}