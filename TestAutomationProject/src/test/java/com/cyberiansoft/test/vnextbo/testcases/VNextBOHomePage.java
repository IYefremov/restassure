package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOHomePageData;
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

public class VNextBOHomePage extends BaseTestCase {
    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/VNextBOHomePageData.json";

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    private String userName;
    private String userPassword;
    private VNextBOLoginScreenWebPage loginPage;
    private VNextBOHomeWebPage homePage;
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
        homePage = PageFactory.initElements(webdriver, VNextBOHomeWebPage.class);
        leftMenu = PageFactory.initElements(webdriver, VNexBOLeftMenuPanel.class);
    }

    @AfterMethod
    public void BackOfficeLogout() {
        try {
            VNextBOHeaderPanel headerpanel = PageFactory.initElements(webdriver, VNextBOHeaderPanel.class);
            if (headerpanel.logOutLinkExists()) {
                headerpanel.userLogout();
            }
        } catch (RuntimeException ignored) {}

        if (DriverBuilder.getInstance().getDriver() != null) {
            DriverBuilder.getInstance().quitDriver();
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeHomePage(String rowID, String description, JSONObject testData) {
        VNextBOHomePageData data = JSonDataParser.getTestDataFromJson(testData, VNextBOHomePageData.class);

        Assert.assertTrue(leftMenu.isMenuButtonDisplayed(), "The Menu button hasn't been displayed");
        Assert.assertTrue(homePage.isLogoDisplayed(), "The logo hasn't been displayed");
        Assert.assertTrue(homePage.isUserEmailDisplayed(), "The email hasn't been displayed");
        Assert.assertTrue(homePage.isLogoutButtonDisplayed(), "The logout button hasn't been displayed");
        Assert.assertTrue(homePage.isHelpButtonDisplayed(), "The help button hasn't been displayed");
        Assert.assertTrue(homePage.isAccessClientPortalLinkDisplayed(),
                "The access client portal link hasn't been displayed");
        Assert.assertTrue(homePage.isAccessReconProBOLinkDisplayed(),
                "The access ReconPro BO link hasn't been displayed");
        Assert.assertTrue(homePage.isSupportForBOButtonDisplayed(),
                "The support for BO button hasn't been displayed");
        Assert.assertTrue(homePage.isSupportForMobileAppButtonDisplayed(),
                "The support for Mobile App button hasn't been displayed");
        Assert.assertTrue(homePage.isTermsAndConditionsLinkDisplayed(),
                "The Terms And Conditions Link hasn't been displayed");
        Assert.assertTrue(homePage.isPrivacyPolicyLinkDisplayed(),
                "The Privacy Policy Link hasn't been displayed");
        Assert.assertTrue(homePage.isIntercomDisplayed(),
                "The Intercom Link hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanMaximizeMinimizeMenu(String rowID, String description, JSONObject testData) {
        VNextBOHomePageData data = JSonDataParser.getTestDataFromJson(testData, VNextBOHomePageData.class);

        Assert.assertTrue(leftMenu.isMenuButtonDisplayed(), "The Menu button hasn't been displayed");
        Assert.assertTrue(homePage.isLogoDisplayed(), "The logo hasn't been displayed");
        Assert.assertTrue(homePage.isUserEmailDisplayed(), "The email hasn't been displayed");
        Assert.assertTrue(homePage.isLogoutButtonDisplayed(), "The logout button hasn't been displayed");
        Assert.assertTrue(homePage.isHelpButtonDisplayed(), "The help button hasn't been displayed");
        Assert.assertTrue(homePage.isAccessClientPortalLinkDisplayed(),
                "The access client portal link hasn't been displayed");
        Assert.assertTrue(homePage.isAccessReconProBOLinkDisplayed(),
                "The access ReconPro BO link hasn't been displayed");
        Assert.assertTrue(homePage.isSupportForBOButtonDisplayed(),
                "The support for BO button hasn't been displayed");
        Assert.assertTrue(homePage.isSupportForMobileAppButtonDisplayed(),
                "The support for Mobile App button hasn't been displayed");
        Assert.assertTrue(homePage.isTermsAndConditionsLinkDisplayed(),
                "The Terms And Conditions Link hasn't been displayed");
        Assert.assertTrue(homePage.isPrivacyPolicyLinkDisplayed(),
                "The Privacy Policy Link hasn't been displayed");
        Assert.assertTrue(homePage.isIntercomDisplayed(),
                "The Intercom Link hasn't been displayed");

        leftMenu.expandMainMenu();
        Assert.assertTrue(leftMenu.isMainMenuExpanded(), "The main menu hasn't been expanded");
        leftMenu.collapseMainMenu();
        Assert.assertFalse(leftMenu.isMainMenuExpanded(), "The main menu hasn't been collapsed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanMinimizeMaximizeMenu(String rowID, String description, JSONObject testData) {
        VNextBOHomePageData data = JSonDataParser.getTestDataFromJson(testData, VNextBOHomePageData.class);

        leftMenu.collapseMainMenu();
        Assert.assertFalse(leftMenu.isMainMenuExpanded(), "The main menu hasn't been collapsed");
        leftMenu.expandMainMenu();
        Assert.assertTrue(leftMenu.isMainMenuExpanded(), "The main menu hasn't been expanded");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeHomePageAfterClickingOnAMTLogo(String rowID, String description, JSONObject testData) {
        VNextBOHomePageData data = JSonDataParser.getTestDataFromJson(testData, VNextBOHomePageData.class);

        homePage.clickLogo();
        Assert.assertTrue(leftMenu.isMenuButtonDisplayed(), "The Menu button hasn't been displayed");
        Assert.assertTrue(homePage.isLogoDisplayed(), "The logo hasn't been displayed");
        Assert.assertTrue(homePage.isUserEmailDisplayed(), "The email hasn't been displayed");
        Assert.assertTrue(homePage.isLogoutButtonDisplayed(), "The logout button hasn't been displayed");
        Assert.assertTrue(homePage.isHelpButtonDisplayed(), "The help button hasn't been displayed");
        Assert.assertTrue(homePage.isAccessClientPortalLinkDisplayed(),
                "The access client portal link hasn't been displayed");
        Assert.assertTrue(homePage.isAccessReconProBOLinkDisplayed(),
                "The access ReconPro BO link hasn't been displayed");
        Assert.assertTrue(homePage.isSupportForBOButtonDisplayed(),
                "The support for BO button hasn't been displayed");
        Assert.assertTrue(homePage.isSupportForMobileAppButtonDisplayed(),
                "The support for Mobile App button hasn't been displayed");
        Assert.assertTrue(homePage.isTermsAndConditionsLinkDisplayed(),
                "The Terms And Conditions Link hasn't been displayed");
        Assert.assertTrue(homePage.isPrivacyPolicyLinkDisplayed(),
                "The Privacy Policy Link hasn't been displayed");
        Assert.assertTrue(homePage.isIntercomDisplayed(),
                "The Intercom Link hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeEditWindowOfHisProfile(String rowID, String description, JSONObject testData) {
        VNextBOHomePageData data = JSonDataParser.getTestDataFromJson(testData, VNextBOHomePageData.class);

        final VNextBOUserProfileDialog profileDialog = homePage.clickUserEmail();

        Assert.assertTrue(profileDialog.isUserProfileDialogDisplayed(),
                "The user's profile dialog hasn't been displayed");

        Assert.assertTrue(profileDialog.isEmailInputFieldDisplayed(),
                "The email input field hasn't been displayed");
        Assert.assertTrue(profileDialog.isPasswordInputFieldDisplayed(),
                "The password input field hasn't been displayed");
        Assert.assertTrue(profileDialog.isConfirmPasswordInputFieldDisplayed(),
                "The confirm password input field hasn't been displayed");
        Assert.assertTrue(profileDialog.isFirstNameInputFieldDisplayed(),
                "The first name input field hasn't been displayed");
        Assert.assertTrue(profileDialog.isLastNameInputFieldDisplayed(),
                "The last name input field hasn't been displayed");
        Assert.assertTrue(profileDialog.isPhoneInputFieldDisplayed(),
                "The phone input field hasn't been displayed");
        Assert.assertTrue(profileDialog.isSaveButtonDisplayed(),
                "The 'Save' button hasn't been displayed");
        Assert.assertTrue(profileDialog.isXButtonDisplayed(),
                "The 'X' button hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanLogoutFromHisProfile(String rowID, String description, JSONObject testData) {
        VNextBOHomePageData data = JSonDataParser.getTestDataFromJson(testData, VNextBOHomePageData.class);

        loginPage = homePage.clickLogoutButton();
        Assert.assertTrue(loginPage.isLoginFormDisplayed(), "The login form hasn't been displayed");
        Assert.assertTrue(loginPage.isEmailFieldDisplayed(), "The email input field hasn't been displayed");
        Assert.assertTrue(loginPage.isPasswordFieldDisplayed(), "The password input field hasn't been displayed");
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "The login button hasn't been displayed");
        Assert.assertTrue(loginPage.isForgotPasswordLinkDisplayed(), "The forgot password link hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenHelpPage(String rowID, String description, JSONObject testData) {
        VNextBOHomePageData data = JSonDataParser.getTestDataFromJson(testData, VNextBOHomePageData.class);

        final String mainWindow = webdriver.getWindowHandle();
        final String helpWindow = homePage.openHelpWindow(mainWindow);
        final String currentUrl = webdriver.getCurrentUrl();
        System.out.println(mainWindow);
        System.out.println(helpWindow);

        System.out.println("actual Help Url: " + currentUrl);
        System.out.println("expected Help Url: " + data.getUrl());

        Assert.assertTrue(currentUrl.contains(data.getUrl()), "The \"Help\" page hasn't been opened");

        homePage.closeWindows();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenLearnPage(String rowID, String description, JSONObject testData) {
        VNextBOHomePageData data = JSonDataParser.getTestDataFromJson(testData, VNextBOHomePageData.class);

        final String mainWindow = webdriver.getWindowHandle();
        final String learnWindow = homePage.openLearnWindow(mainWindow);
        String currentUrl;
        try {
            currentUrl = webdriver.getCurrentUrl();
        } catch (Exception e) {
            e.printStackTrace();
            webdriver.switchTo().window(learnWindow);
            currentUrl = webdriver.getCurrentUrl();
        }
        System.out.println(mainWindow);
        System.out.println(learnWindow);

        System.out.println("actual Learn Url: " + currentUrl);
        System.out.println("expected Learn Url: " + data.getUrl());

        Assert.assertTrue(currentUrl.contains(data.getUrl()), "The \"Learn\" page hasn't been opened");

        homePage.closeWindows();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanClickOnAccessClientPortalLink(String rowID, String description, JSONObject testData) {
        VNextBOHomePageData data = JSonDataParser.getTestDataFromJson(testData, VNextBOHomePageData.class);

        final String mainWindow = webdriver.getWindowHandle();
        final String AccessClientPortalWindow = homePage.openAccessClientPortalWindow(mainWindow);
        final String currentUrl = webdriver.getCurrentUrl();
        System.out.println(mainWindow);
        System.out.println(AccessClientPortalWindow);

        System.out.println("actual Access Client Portal Url: " + currentUrl);
        System.out.println("expected Access Client Portal Url: " + data.getUrl());

        Assert.assertTrue(currentUrl.contains(data.getUrl()), "The \"Access Client Portal\" page hasn't been opened");

        homePage.closeWindows();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanClickOnAccessReconProBOLink(String rowID, String description, JSONObject testData) {
        VNextBOHomePageData data = JSonDataParser.getTestDataFromJson(testData, VNextBOHomePageData.class);

        final String mainWindow = webdriver.getWindowHandle();
        homePage.openAccessReconProBOWindow(mainWindow);
        final String currentUrl = webdriver.getCurrentUrl();
        System.out.println(mainWindow);

        System.out.println("actual Access Client Portal Url: " + currentUrl);
        System.out.println("expected Access Client Portal Url: " + data.getUrl());

        Assert.assertTrue(currentUrl.contains(data.getUrl()), "The \"Access Client Portal\" page hasn't been opened");

        homePage.closeWindows();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenSupportForBOPage(String rowID, String description, JSONObject testData) {
        VNextBOHomePageData data = JSonDataParser.getTestDataFromJson(testData, VNextBOHomePageData.class);

        final String mainWindow = webdriver.getWindowHandle();
        final String AccessClientPortalWindow = homePage.openSupportForBOWindow(mainWindow);
        final String currentUrl = webdriver.getCurrentUrl();
        System.out.println(mainWindow);
        System.out.println(AccessClientPortalWindow);

        System.out.println("actual Support For BO Url: " + currentUrl);
        System.out.println("expected Support For BO Url: " + data.getUrl());

        Assert.assertEquals(currentUrl, data.getUrl(), "The \"Support For BO\" page hasn't been opened");

        homePage.closeWindows();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenSupportForMobileAppPage(String rowID, String description, JSONObject testData) {
        VNextBOHomePageData data = JSonDataParser.getTestDataFromJson(testData, VNextBOHomePageData.class);

        final String mainWindow = webdriver.getWindowHandle();
        final String AccessClientPortalWindow = homePage.openSupportForMobileAppWindow(mainWindow);
        final String currentUrl = webdriver.getCurrentUrl();
        System.out.println(mainWindow);
        System.out.println(AccessClientPortalWindow);

        System.out.println("actual Support For BO Url: " + currentUrl);
        System.out.println("expected Support For BO Url: " + data.getUrl());

        Assert.assertEquals(currentUrl, data.getUrl(), "The \"Support For Mobile App\" page hasn't been opened");

        homePage.closeWindows();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenAndCloseIntercom(String rowID, String description, JSONObject testData) {
        VNextBOHomePageData data = JSonDataParser.getTestDataFromJson(testData, VNextBOHomePageData.class);

        homePage.openIntercom();
        Assert.assertTrue(homePage.isIntercomOpened(), "The Intercom hasn't been opened");
        homePage.closeIntercom();
        Assert.assertFalse(homePage.isIntercomOpened(), "The Intercom hasn't been closed");
    }
}