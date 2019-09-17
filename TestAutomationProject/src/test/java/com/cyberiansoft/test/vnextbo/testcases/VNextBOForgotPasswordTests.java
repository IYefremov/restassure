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

public class VNextBOForgotPasswordTests extends BaseTestCase {

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
    VNextBOForgotPasswordWebPage forgotPasswordPage;

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
    public void checkUserIsRedirectedToForgotPasswordPage(String rowID, String description, JSONObject testData) {
        VNextBOHomePageData data = JSonDataParser.getTestDataFromJson(testData, VNextBOHomePageData.class);

        forgotPasswordPage = loginPage.clickForgotPasswordLink();

        Assert.assertTrue(forgotPasswordPage.isEnterEmailLLabelDisplayed(), "Enter your Email address label hasn't been displayed");
        Assert.assertTrue(forgotPasswordPage.isConfirmationMailFieldDisplayed(), "Email field hasn't been displayed");
        Assert.assertTrue(forgotPasswordPage.isLoginLinkDisplayed(), "Login link hasn't been displayed");
        Assert.assertTrue(forgotPasswordPage.isSubmitButtonDisplayed(), "Submit button hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void checkUserIsReturnedToLoginPage(String rowID, String description, JSONObject testData) {
        VNextBOHomePageData data = JSonDataParser.getTestDataFromJson(testData, VNextBOHomePageData.class);

        forgotPasswordPage = loginPage.clickForgotPasswordLink();
        loginPage = forgotPasswordPage.clickLoginLink();

        Assert.assertTrue(loginPage.isLoginFormDisplayed(), "Login form hasn't been displayed");
        Assert.assertTrue(loginPage.isEmailFieldDisplayed(), "Email field hasn't been displayed");
        Assert.assertTrue(loginPage.isPasswordFieldDisplayed(), "Passford field hasn't been displayed");
        Assert.assertTrue(loginPage.isForgotPasswordLinkDisplayed(), "Forgot password link hasn't been displayed");
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button hasn't been displayed");
    }
}
