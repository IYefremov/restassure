package com.cyberiansoft.test.vnextbo.testcases.login;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOHomePageData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.general.VNextBOFooterPanelInteractions;
import com.cyberiansoft.test.vnextbo.interactions.VNextBOLoginInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOHeaderPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOHomeWebPage;
import com.cyberiansoft.test.vnextbo.steps.VNextBOHeaderPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.login.VNextBOLoginSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.dialogs.VNextBOModalDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.login.VNextBOLoginValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOLoginTests extends BaseTestCase {

    private String userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
    private String userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();


    @Override
    @BeforeClass
    public void login() {

        browserType = BaseUtils.getBrowserType(VNextBOConfigInfo.getInstance().getDefaultBrowser());
        DriverBuilder.getInstance().setDriver(browserType);
        webdriver = DriverBuilder.getInstance().getDriver();
        webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOCompanionappURL());
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getLoginTD();
    }

    @BeforeMethod
    public void setUp() {
        webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOCompanionappURL());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserCanReadTermsAndConditions(String rowID, String description, JSONObject testData) {

        VNextBOFooterPanelInteractions.clickTermsAndConditionsLink();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogValidations.verifyOkButtonIsDisplayed();
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogHeader(),
                "AMT Service Agreement Terms and Conditions",
                "Dialog header hasn't been correct");
        VNextBOModalDialogSteps.clickOkButton();
        Assert.assertTrue(VNextBOLoginValidations.isLoginFormDisplayed(),
                "Terms and Conditions dialog hasn't been closed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyUserCanNotLoginWithoutEmail(String rowID, String description, JSONObject testData) {

        VNextBOLoginSteps.userLoginWithInvalidUserData("", userPassword);
        Assert.assertEquals(VNextBOLoginInteractions.getEmailErrorMessage(), "Email is required!",
                "Email error message hasn't been correct or not displayed");
        VNextBOLoginSteps.clearLoginFormData();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
    public void verifyUserCanNotLoginWithoutPassword(String rowID, String description, JSONObject testData) {

        Utils.refreshPage();
        VNextBOLoginSteps.userLoginWithInvalidUserData(userName, "");
        Assert.assertEquals(VNextBOLoginInteractions.getPasswordErrorMessage(), "Password is required!",
                "Password error message hasn't been correct or not displayed");
        VNextBOLoginSteps.clearLoginFormData();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
    public void verifyUserCanNotLoginWithoutEmailAndPassword(String rowID, String description, JSONObject testData) {

        Utils.refreshPage();
        VNextBOLoginSteps.userLoginWithInvalidUserData("", "");
        Assert.assertEquals(VNextBOLoginInteractions.getEmailErrorMessage(), "Email is required!",
                "Email error message hasn't been correct or not displayed");
        Assert.assertEquals(VNextBOLoginInteractions.getPasswordErrorMessage(), "Password is required!",
                "Password error message hasn't been correct or not displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyUserCanNotLoginWithInvalidUserData(String rowID, String description, JSONObject testData) {

        VNextBOHomePageData data = JSonDataParser.getTestDataFromJson(testData, VNextBOHomePageData.class);
        VNextBOLoginSteps.userLoginWithInvalidUserData(data.getLogin(), data.getPassword());
        Assert.assertEquals(VNextBOLoginInteractions.getPasswordErrorMessage(),
                "Your login attempt was not successful. Please try again.",
                "Error message hasn't been correct or not displayed");
        VNextBOLoginSteps.clearLoginFormData();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyUserCanNotLoginWithPasswordInsteadOfEmail(String rowID, String description, JSONObject testData) {

        VNextBOLoginSteps.userLoginWithInvalidUserData(userPassword, userName);
        Assert.assertEquals(VNextBOLoginInteractions.getEmailErrorMessage(), "Email is invalid!",
                "Email error message hasn't been correct or not displayed");
        VNextBOLoginSteps.clearLoginFormData();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 6)
    public void verifyUserCanLoginWithCorrectUserData(String rowID, String description, JSONObject testData) {

        VNextBOHomePageData data = JSonDataParser.getTestDataFromJson(testData, VNextBOHomePageData.class);
        VNextBOLoginSteps.userLogin(data.getLogin(), data.getPassword());
        VNextBOHomeWebPage vNextBOHomeWebPage = new VNextBOHomeWebPage();
        Assert.assertTrue(vNextBOHomeWebPage.isSupportForBOButtonDisplayed(), "Home page hasn't been displayed");
        VNextBOHeaderPanelSteps.logout();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 7)
    public void verifyUserCanLoginWhenClickBackBtnAndReturn(String rowID, String description, JSONObject testData) {

        VNextBOLoginInteractions.setEmailField(userName);
        VNextBOLoginInteractions.setPasswordField(userPassword);
        webdriverGotoWebPage("https://www.google.com");
        webdriver.navigate().back();
        Assert.assertEquals(VNextBOLoginInteractions.getValueFromEmailField(), "",
                "Email field hasn't been empty");
        Assert.assertEquals(VNextBOLoginInteractions.getValueFromPasswordField(), "",
                "Password field hasn't been empty");
        VNextBOLoginInteractions.waitUntilPageIsLoaded();
        VNextBOLoginSteps.userLogin(userName, userPassword);
        VNextBOHomeWebPage vNextBOHomeWebPage = new VNextBOHomeWebPage();
        Assert.assertTrue(vNextBOHomeWebPage.isSupportForBOButtonDisplayed(), "Home page hasn't been displayed");
    }
}