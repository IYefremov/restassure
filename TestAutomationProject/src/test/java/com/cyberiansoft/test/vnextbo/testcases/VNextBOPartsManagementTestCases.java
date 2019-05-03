package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOPartsManagementData;
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

import java.util.Arrays;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOPartsManagementTestCases extends BaseTestCase {
    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/VNextBOPartsManagementData.json";

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    private VNexBOLeftMenuPanel leftMenu;
    private VNextBOLocationComponent locationComponent;
    private VNextBODashboardPanel dashboardPanel;
    private VNextBOPartsManagementSearchPanel partsManagementSearch;
    private VNextBOPartsManagementROListPanel roListPanel;
    private VNextBOFooterPanel footerPanel;

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
        locationComponent = PageFactory.initElements(webdriver, VNextBOLocationComponent.class);
        dashboardPanel = PageFactory.initElements(webdriver, VNextBODashboardPanel.class);
        partsManagementSearch = PageFactory.initElements(webdriver, VNextBOPartsManagementSearchPanel.class);
        partsManagementSearch = PageFactory.initElements(webdriver, VNextBOPartsManagementSearchPanel.class);
        roListPanel = PageFactory.initElements(webdriver, VNextBOPartsManagementROListPanel.class);
        footerPanel = PageFactory.initElements(webdriver, VNextBOFooterPanel.class);
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
    public void verifyUserCanOpenOperationsPartsManagementWithFullSetOfElements(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        leftMenu.selectPartsManagementMenu();
        locationComponent.setLocation(data.getLocation());

        Assert.assertTrue(dashboardPanel
                .getDashboardItemsNames()
                .containsAll(Arrays.asList(data.getDashboardItemsNames())),
                "The dashboard items names haven't been displayed");
        Assert.assertTrue(partsManagementSearch.isPartsManagementSearchPanelDisplayed(),
                "The Parts Management search panel hasn't been displayed");
        Assert.assertTrue(roListPanel.isPartsManagementROListDisplayed(),
                "The Parts Management RO list panel hasn't been displayed");
        Assert.assertTrue(footerPanel.isFooterPanelDisplayed(),
                "The Parts Management footer panel hasn't been displayed");
        Assert.assertTrue(footerPanel.isTermsAndConditionsLinkDisplayed(),
                "The Parts Management Terms and Conditions link hasn't been displayed");
        Assert.assertTrue(footerPanel.isPrivacyPolicyLinkDisplayed(),
                "The Parts Management Privacy Policy link hasn't been displayed");
        Assert.assertTrue(footerPanel.isIntercomDisplayed(),
                "The Intercom link hasn't been displayed on the Parts Management page");
    }
}