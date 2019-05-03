package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.screens.*;
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

    private VNexBOLeftMenuPanel leftMenu;
    private VNextBOBreadCrumbPanel breadCrumbPanel;

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

        leftMenu = PageFactory.initElements(webdriver, VNexBOLeftMenuPanel.class);
        breadCrumbPanel = PageFactory.initElements(webdriver, VNextBOBreadCrumbPanel.class);
    }

    @AfterMethod
    public void BackOfficeLogout() {
        try {
            VNextBOHeaderPanel headerpanel = PageFactory.initElements(webdriver, VNextBOHeaderPanel.class);
            if (headerpanel.logOutLinkExists()) {
                headerpanel.userLogout();
            }
        } catch (RuntimeException ignored) {
        }

        if (DriverBuilder.getInstance().getDriver() != null) {
            DriverBuilder.getInstance().quitDriver();
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeBreadCrumb(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        leftMenu.selectRepairOrdersMenu();
        breadCrumbPanel.setLocation(data.getLocation());
        Assert.assertTrue(breadCrumbPanel.isLocationSet(data.getLocation()), "The location hasn't been set");
        Assert.assertTrue(breadCrumbPanel.isMainBreadCrumbClickable(), "The breadCrumb is not clickable");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeWoInBreadCrumb(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        breadCrumbPanel.setLocation(data.getLocation());
        Assert.assertTrue(breadCrumbPanel.isLocationSet(data.getLocation()), "The location hasn't been set");
        Assert.assertTrue(breadCrumbPanel.isMainBreadCrumbClickable(), "The breadCrumb is not clickable");
        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();

        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");

        final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        Assert.assertTrue(breadCrumbPanel.isBreadCrumbClickable(), "The breadCrumb is not clickable");
        Assert.assertTrue(breadCrumbPanel.isLocationSet(data.getLocation()),
                "The location hasn't been displayed on the RO details page");
        Assert.assertTrue(breadCrumbPanel.isLastBreadCrumbDisplayed(),
                "The RO# hasn't been displayed on the RO details page");
        Assert.assertEquals(breadCrumbPanel.getLastBreadCrumbText(), data.getOrderNumber(),
                "The RO details page breadCrumb with RO# hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeLocationOnDetailsPage(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        breadCrumbPanel.setLocation(data.getLocation());
        Assert.assertTrue(breadCrumbPanel.isLocationSet(data.getLocation()), "The location hasn't been set");
        Assert.assertTrue(breadCrumbPanel.isMainBreadCrumbClickable(), "The breadCrumb is not clickable");
        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();

        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");

        final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        Assert.assertTrue(breadCrumbPanel.isBreadCrumbClickable(), "The breadCrumb is not clickable");
        Assert.assertTrue(breadCrumbPanel.isLocationSet(data.getLocation()),
                "The location hasn't been displayed on the RO details page");
        Assert.assertTrue(breadCrumbPanel.isLastBreadCrumbDisplayed(),
                "The RO# hasn't been displayed on the RO details page");
        Assert.assertEquals(breadCrumbPanel.getLastBreadCrumbText(), data.getOrderNumber(),
                "The RO details page breadCrumb with RO# hasn't been displayed");

        breadCrumbPanel.setLocation(data.getLocationChanged());
        Assert.assertTrue(breadCrumbPanel.isLocationSet(data.getLocationChanged()),
                "The location hasn't been changed on the Order Details page");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanReturnToMainPageOfWo(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        breadCrumbPanel.setLocation(data.getLocation());
        Assert.assertTrue(breadCrumbPanel.isLocationSet(data.getLocation()), "The location hasn't been set");
        Assert.assertTrue(breadCrumbPanel.isMainBreadCrumbClickable(), "The breadCrumb is not clickable");
        repairOrdersPage
                .setRepairOrdersSearchText(data.getOrderNumber())
                .clickSearchIcon();

        Assert.assertTrue(repairOrdersPage.isWorkOrderDisplayedByVin(data.getOrderNumber()),
                "The work order is not displayed after search by order number after clicking the 'Search' icon");

        final VNextBORepairOrderDetailsPage detailsPage = repairOrdersPage.clickWoLink(data.getOrderNumber());
        Assert.assertTrue(detailsPage.isRoDetailsSectionDisplayed(), "The RO details section hasn't been displayed");

        Assert.assertTrue(breadCrumbPanel.isBreadCrumbClickable(), "The breadCrumb is not clickable");
        Assert.assertTrue(breadCrumbPanel.isLocationSet(data.getLocation()),
                "The location hasn't been displayed on the RO details page");
        Assert.assertTrue(breadCrumbPanel.isLastBreadCrumbDisplayed(),
                "The RO# hasn't been displayed on the RO details page");
        Assert.assertEquals(breadCrumbPanel.getLastBreadCrumbText(), data.getOrderNumber(),
                "The RO details page breadCrumb with RO# hasn't been displayed");

        detailsPage.waitForLoading();
        detailsPage.clickRepairOrdersBackwardsLink();
        Assert.assertTrue(breadCrumbPanel.isLocationSet(data.getLocation()), "The location hasn't been set");
        Assert.assertTrue(breadCrumbPanel.isMainBreadCrumbClickable(), "The breadCrumb is not clickable");
    }
}