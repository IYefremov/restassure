package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.screens.*;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBORODetailsPage;
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

public class VNextBOBreadCrumbsTestCases extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/VNextBOMonitorData.json";

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    private VNextBOBreadCrumbInteractions breadCrumbInteractions;
    private HomePageSteps homePageSteps;
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

        VNextBOLoginScreenWebPage loginPage = PageFactory.initElements(DriverBuilder.getInstance().getDriver(),
                VNextBOLoginScreenWebPage.class);
        loginPage.userLogin(userName, userPassword);

        repairOrdersPage = PageFactory.initElements(DriverBuilder.getInstance().getDriver(), VNextBOROWebPage.class);
        breadCrumbInteractions = new VNextBOBreadCrumbInteractions();
        homePageSteps = new HomePageSteps();
    }

    @AfterMethod
    public void BackOfficeLogout() {
        new VNextBOHeaderPanelSteps().logout();

        if (DriverBuilder.getInstance().getDriver() != null) {
            DriverBuilder.getInstance().quitDriver();
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeBreadCrumb(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        Assert.assertTrue(breadCrumbInteractions.isBreadCrumbClickable(), "The breadCrumb is not clickable");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeWoInBreadCrumb(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        Assert.assertTrue(breadCrumbInteractions.isBreadCrumbClickable(), "The breadCrumb is not clickable");
        new VNextBOROSimpleSearchSteps().searchByText(data.getOrderNumber());

        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");

        final VNextBORODetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        Assert.assertTrue(breadCrumbInteractions.isBreadCrumbClickable(), "The breadCrumb is not clickable");
        Assert.assertTrue(breadCrumbInteractions.isLocationSet(data.getLocation()),
                "The location hasn't been displayed on the RO details page");
        Assert.assertTrue(breadCrumbInteractions.isLastBreadCrumbDisplayed(),
                "The RO# hasn't been displayed on the RO details page");
        Assert.assertEquals(breadCrumbInteractions.getLastBreadCrumbText(), data.getOrderNumber(),
                "The RO details page breadCrumb with RO# hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeLocationOnDetailsPage(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        Assert.assertTrue(breadCrumbInteractions.isBreadCrumbClickable(), "The breadCrumb is not clickable");
        new VNextBOROSimpleSearchSteps().searchByText(data.getOrderNumber());

        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");

        final VNextBORODetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        Assert.assertTrue(breadCrumbInteractions.isBreadCrumbClickable(), "The breadCrumb is not clickable");
        Assert.assertTrue(breadCrumbInteractions.isLocationSet(data.getLocation()),
                "The location hasn't been displayed on the RO details page");
        Assert.assertTrue(breadCrumbInteractions.isLastBreadCrumbDisplayed(),
                "The RO# hasn't been displayed on the RO details page");
        Assert.assertEquals(breadCrumbInteractions.getLastBreadCrumbText(), data.getOrderNumber(),
                "The RO details page breadCrumb with RO# hasn't been displayed");

        breadCrumbInteractions.setLocation(data.getLocationChanged());
        Assert.assertTrue(breadCrumbInteractions.isLocationSet(data.getLocationChanged()),
                "The location hasn't been changed on the Order Details page");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanReturnToMainPageOfWo(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        homePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
        Assert.assertTrue(breadCrumbInteractions.isBreadCrumbClickable(), "The breadCrumb is not clickable");
        new VNextBOROSimpleSearchSteps().searchByText(data.getOrderNumber());

        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");

        final VNextBORODetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        Assert.assertTrue(breadCrumbInteractions.isBreadCrumbClickable(), "The breadCrumb is not clickable");
        Assert.assertTrue(breadCrumbInteractions.isLocationSet(data.getLocation()),
                "The location hasn't been displayed on the RO details page");
        Assert.assertTrue(breadCrumbInteractions.isLastBreadCrumbDisplayed(),
                "The RO# hasn't been displayed on the RO details page");
        Assert.assertEquals(breadCrumbInteractions.getLastBreadCrumbText(), data.getOrderNumber(),
                "The RO details page breadCrumb with RO# hasn't been displayed");

        detailsPage.waitForLoading();
        detailsPage.clickRepairOrdersBackwardsLink();
        Assert.assertTrue(breadCrumbInteractions.isLocationSet(data.getLocation()), "The location hasn't been set");
        Assert.assertTrue(breadCrumbInteractions.isBreadCrumbClickable(), "The breadCrumb is not clickable");
    }
}