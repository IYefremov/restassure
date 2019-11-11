package com.cyberiansoft.test.vnextbo.testcases.login;

import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOHomePageData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.VNextBOFooterPanelInteractions;
import com.cyberiansoft.test.vnextbo.interactions.VNextBOLoginInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOHomeWebPage;
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

    private String userName;
    private String userPassword;

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getLoginTD();
    }

    @Override
    @BeforeMethod
    public void login() {
        webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOCompanionappURL());
        userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
        userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanNotLoginWithoutEmail(String rowID, String description, JSONObject testData) {

        VNextBOLoginSteps.userLogin("", userPassword);
        Assert.assertEquals(VNextBOLoginInteractions.getEmailErrorMessage(), "Email is required!",
                "Email error message hasn't been correct or not displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanNotLoginWithoutPassword(String rowID, String description, JSONObject testData) {

        VNextBOLoginSteps.userLogin(userName, "");
        Assert.assertEquals(VNextBOLoginInteractions.getPasswordErrorMessage(), "Password is required!",
                "Password error message hasn't been correct or not displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanNotLoginWithoutEmailAndPassword(String rowID, String description, JSONObject testData) {

        VNextBOLoginSteps.userLogin("", "");
        Assert.assertEquals(VNextBOLoginInteractions.getEmailErrorMessage(), "Email is required!",
                "Email error message hasn't been correct or not displayed");
        Assert.assertEquals(VNextBOLoginInteractions.getPasswordErrorMessage(), "Password is required!",
                "Password error message hasn't been correct or not displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanNotLoginWithInvalidUserData(String rowID, String description, JSONObject testData) {

        VNextBOHomePageData data = JSonDataParser.getTestDataFromJson(testData, VNextBOHomePageData.class);
        VNextBOLoginSteps.userLogin(data.getLogin(), data.getPassword());
        Assert.assertEquals(VNextBOLoginInteractions.getPasswordErrorMessage(),
                "Your login attempt was not successful. Please try again.",
                "Error message hasn't been correct or not displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanNotLoginWithPasswordInsteadOfEmail(String rowID, String description, JSONObject testData) {

        VNextBOLoginSteps.userLogin(userPassword, userName);
        Assert.assertEquals(VNextBOLoginInteractions.getEmailErrorMessage(), "Email is invalid!",
                "Email error message hasn't been correct or not displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanLoginWithCorrectUserData(String rowID, String description, JSONObject testData) {

        VNextBOHomePageData data = JSonDataParser.getTestDataFromJson(testData, VNextBOHomePageData.class);
        VNextBOLoginSteps.userLogin(data.getLogin(), data.getPassword());
        VNextBOHomeWebPage vNextBOHomeWebPage = new VNextBOHomeWebPage();
        Assert.assertTrue(vNextBOHomeWebPage.isSupportForBOButtonDisplayed(), "Home page hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanLoginWhenClickBackBtnAndReturn(String rowID, String description, JSONObject testData) {

        VNextBOLoginInteractions.setEmailField(userName);
        VNextBOLoginInteractions.setPasswordField(userPassword);
        webdriverGotoWebPage("https://www.google.com");
        webdriver.navigate().back();
        Assert.assertEquals(VNextBOLoginInteractions.getValueFromEmailField(), "",
                "Email field hasn't been empty");
        Assert.assertEquals(VNextBOLoginInteractions.getValueFromPasswordField(), "",
                "Password field hasn't been empty");
        VNextBOLoginSteps.userLogin(userName, userPassword);
        VNextBOHomeWebPage vNextBOHomeWebPage = new VNextBOHomeWebPage();
        Assert.assertTrue(vNextBOHomeWebPage.isSupportForBOButtonDisplayed(), "Home page hasn't been displayed");
    }
}