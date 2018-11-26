package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOHeaderPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBORepairOrdersWebPage;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOMonitorTestCases extends BaseTestCase {
    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/VNextBOMonitorData.json";

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    private String userName;
    private String userPassword;
    private VNextBOLoginScreenWebPage loginPage;
    private VNexBOLeftMenuPanel leftMenu;

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
        userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
        userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();

        loginPage = PageFactory.initElements(webdriver, VNextBOLoginScreenWebPage.class);
        loginPage.userLogin(userName, userPassword);
        leftMenu = PageFactory.initElements(webdriver, VNexBOLeftMenuPanel.class);
    }

    @AfterMethod
    public void BackOfficeLogout() {
        VNextBOHeaderPanel headerpanel = PageFactory.initElements(webdriver,
                VNextBOHeaderPanel.class);
        if (headerpanel.logOutLinkExists())
            headerpanel.userLogout();

        if (DriverBuilder.getInstance().getDriver() != null)
            DriverBuilder.getInstance().quitDriver();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenMonitorWithFullSetOfElements(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBORepairOrdersWebPage repairOrdersPage = leftMenu.selectRepairOrdersMenu();
        repairOrdersPage.setLocation(data.getLocation());

        Assert.assertTrue(repairOrdersPage.isSavedSearchContainerDisplayed(),
                "The search container isn't displayed");

//        repairOrdersPage.makeDepartmentsTabActive(); todo add verifies for narrow screen, if necessary
//        Assert.assertTrue(repairOrdersPage.isDepartmentsTabDisplayed(),
//                "The department tab isn't displayed");

        Assert.assertTrue(repairOrdersPage.isDepartmentDropdownDisplayed(), "The department dropdown is not displayed");

        repairOrdersPage.clickPhasesWide();
        Assert.assertTrue(repairOrdersPage.isPhasesDropdownDisplayed(), "The phases dropdown is not displayed");

//        repairOrdersPage.makePhasesTabActive();
//        Assert.assertTrue(repairOrdersPage.isPhasesTabDisplayed(),
//                "The phases tab isn't displayed");

        Assert.assertTrue(repairOrdersPage.isSearchInputFieldDisplayed(), "The search input field isn't displayed");
        Assert.assertTrue(repairOrdersPage.isAdvancedSearchCaretDisplayed(), "The advanced search caret isn't displayed");
        Assert.assertTrue(repairOrdersPage.areTableHeaderTitlesDisplayed(data.getTitles(), data.getTitlesRepeater()),
                "The table header titles aren't displayed");
    }
}