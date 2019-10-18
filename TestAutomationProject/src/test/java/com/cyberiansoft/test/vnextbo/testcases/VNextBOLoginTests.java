package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOHomePageData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.screens.*;
import com.cyberiansoft.test.vnextbo.steps.VNextBOHeaderPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import com.cyberiansoft.test.vnextbo.verifications.dialogs.VNextBOModalDialogValidations;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOLoginTests extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/VNextBOLoginPageData.json";
    private String userName;
    private String userPassword;
    private VNextBOLoginScreenWebPage loginPage;

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

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

        loginPage = new VNextBOLoginScreenWebPage();
    }

    @AfterMethod
    public void BackOfficeLogout() {
        new VNextBOHeaderPanelSteps().logout();

        if (DriverBuilder.getInstance().getDriver() != null) {
            DriverBuilder.getInstance().quitDriver();
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanReadTermsAndConditions(String rowID, String description, JSONObject testData) {

        loginPage.clickTermsAndConditionsLink();
        VNextBOModalDialogValidations.isDialogDisplayed();
        VNextBOModalDialogValidations.isOkButtonDisplayed();
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogHeader(),
                "AMT Service Agreement Terms and Conditions",
                "Dialog header hasn't been correct");
        VNextBOModalDialogSteps.clickOkButton();
        Assert.assertTrue(loginPage.isLoginFormDisplayed(),
                "Terms and Conditions dialog hasn't been closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanNotLoginWithoutEmail(String rowID, String description, JSONObject testData) {

        loginPage.userLogin("", userPassword);
        Assert.assertEquals(loginPage.getEmailErrorMessage(), "Email is required!",
                "Email error message hasn't been correct or not displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanNotLoginWithoutPassword(String rowID, String description, JSONObject testData) {

        loginPage.userLogin(userName, "");
        Assert.assertEquals(loginPage.getPasswordErrorMessage(), "Password is required!",
                "Password error message hasn't been correct or not displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanNotLoginWithoutEmailAndPassword(String rowID, String description, JSONObject testData) {

        loginPage.userLogin("", "");
        Assert.assertEquals(loginPage.getEmailErrorMessage(), "Email is required!",
                "Email error message hasn't been correct or not displayed");
        Assert.assertEquals(loginPage.getPasswordErrorMessage(), "Password is required!",
                "Password error message hasn't been correct or not displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanNotLoginWithInvalidUserData(String rowID, String description, JSONObject testData) {

        VNextBOHomePageData data = JSonDataParser.getTestDataFromJson(testData, VNextBOHomePageData.class);
        loginPage.userLogin(data.getLogin(), data.getPassword());
        Assert.assertEquals(loginPage.getPasswordErrorMessage(),
                "Your login attempt was not successful. Please try again.",
                "Error message hasn't been correct or not displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanNotLoginWithPasswordInsteadOfEmail(String rowID, String description, JSONObject testData) {

        loginPage.userLogin(userPassword, userName);
        Assert.assertEquals(loginPage.getEmailErrorMessage(), "Email is invalid!",
                "Email error message hasn't been correct or not displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanLoginWithCorrectUserData(String rowID, String description, JSONObject testData) {

        VNextBOHomePageData data = JSonDataParser.getTestDataFromJson(testData, VNextBOHomePageData.class);
        loginPage.userLogin(data.getLogin(), data.getPassword());
        VNextBOHomeWebPage vNextBOHomeWebPage = new VNextBOHomeWebPage(webdriver);
        Assert.assertTrue(vNextBOHomeWebPage.isSupportForBOButtonDisplayed(), "Home page hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanLoginWhenClickBackBtnAndReturn(String rowID, String description, JSONObject testData) {

        loginPage.setEmailField(userName);
        loginPage.setPasswordField(userPassword);
        webdriverGotoWebPage("https://www.google.com");
        webdriver.navigate().back();
        Assert.assertEquals(loginPage.getValueFromEmailField(), "",
                "Email field hasn't been empty");
        Assert.assertEquals(loginPage.getValueFromPasswordField(), "",
                "Password field hasn't been empty");
        loginPage.userLogin(userName, userPassword);
        VNextBOHomeWebPage vNextBOHomeWebPage = new VNextBOHomeWebPage(webdriver);
        Assert.assertTrue(vNextBOHomeWebPage.isSupportForBOButtonDisplayed(), "Home page hasn't been displayed");
    }
}