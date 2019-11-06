package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOForgotPasswordData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.email.getnada.NadaEMailService;
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

public class VNextBOForgotPasswordTests extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/VNextBOForgotPasswordPageData.json";
    private static final String NOT_REGISTERED_USER_EMAIL = "cyberiansoft.test22@nada.ltd";
    private String userName;
    private String userPassword;
    private VNextBOLoginScreenWebPage loginPage;
    private VNextBOForgotPasswordWebPage forgotPasswordPage;

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
        userName = VNextBOConfigInfo.getInstance().getVNextBONadaTestMail();
        userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();

        loginPage = new VNextBOLoginScreenWebPage();
        loginPage.clickForgotPasswordLink();
        forgotPasswordPage = new VNextBOForgotPasswordWebPage(webdriver);
    }

    @AfterMethod
    public void BackOfficeLogout() {
        VNextBOHeaderPanelSteps.logout();

        if (DriverBuilder.getInstance().getDriver() != null) {
            DriverBuilder.getInstance().quitDriver();
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserIsRedirectedToForgotPasswordPage(String rowID, String description, JSONObject testData) {

        Assert.assertTrue(forgotPasswordPage.isConfirmationMailFieldDisplayed(), "Email field hasn't been displayed");
        Assert.assertTrue(forgotPasswordPage.isLoginLinkDisplayed(), "Login link hasn't been displayed");
        Assert.assertTrue(forgotPasswordPage.isSubmitButtonDisplayed(), "Submit button hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserIsReturnedToLoginPage(String rowID, String description, JSONObject testData) {

        forgotPasswordPage.clickLoginLink();
        loginPage = new VNextBOLoginScreenWebPage();

        Assert.assertTrue(loginPage.isLoginFormDisplayed(), "Login form hasn't been displayed");
        Assert.assertTrue(loginPage.isEmailFieldDisplayed(), "Email field hasn't been displayed");
        Assert.assertTrue(loginPage.isPasswordFieldDisplayed(), "Passford field hasn't been displayed");
        Assert.assertTrue(loginPage.isForgotPasswordLinkDisplayed(), "Forgot password link hasn't been displayed");
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyErrorWithEmptyEmailField(String rowID, String description, JSONObject testData) {

        forgotPasswordPage.clickSubmitButton();

        Assert.assertEquals(forgotPasswordPage.getErrorMessageValue(), "Email is not valid!",
                "Error message hasn't been correct or not displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyEmailIsNotSentToNotRegisteredUser(String rowID, String description, JSONObject testData) throws Exception {

        forgotPasswordPage.setConfirmationMailFieldValue(NOT_REGISTERED_USER_EMAIL);
        forgotPasswordPage.clickSubmitButton();

        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogValidations.verifyOkButtonIsDisplayed();
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogHeader(), "Warning",
                "Dialog header hasn't been correct");
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogInformationMessage(),
                "This email is not found", "Dialog message hasn't been correct");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanResetPassword(String rowID, String description, JSONObject testData) throws Exception {

        NadaEMailService nada = new NadaEMailService();
        nada.setEmailId(userName);
        nada.deleteMessageWithSubject("PASSWORD RESET");

        forgotPasswordPage.setConfirmationMailFieldValue(userName);
        forgotPasswordPage.clickSubmitButton();

        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogValidations.verifyOkButtonIsDisplayed();
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogHeader(), "Information",
                "Dialog header hasn't been correct");
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogInformationMessage(),
                "Please check your mailbox. You will receive an email with a link for resetting password within a few minutes.",
                "Dialog message hasn't been correct");
        VNextBOModalDialogSteps.clickOkButton();
        loginPage = new VNextBOLoginScreenWebPage();

        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
                new NadaEMailService.MailSearchParametersBuilder()
                        .withSubject("PASSWORD RESET");
        String mailMessage = nada.getMailMessageBySubjectKeywords(searchParametersBuilder);
        String resetPasswordUrl = nada.getUrlsFromMessage(mailMessage, "reset your password").get(0);

        webdriverGotoWebPage(resetPasswordUrl);
        Utils.refreshPage();

        VNextBOResetPasswordPage vNextBOResetPasswordPage = new VNextBOResetPasswordPage(webdriver);
        Assert.assertEquals(vNextBOResetPasswordPage.getUserEmail(), userName,
                "User's email hasn't been correct");

        vNextBOResetPasswordPage.setNewPassword(userPassword);
        loginPage = new VNextBOLoginScreenWebPage();
        Assert.assertEquals(loginPage.getValueFromEmailField(), userName,
                "Email field hasn't been correct");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCantResetPasswordAfterLogin(String rowID, String description, JSONObject testData) throws Exception {

        NadaEMailService nada = new NadaEMailService();
        nada.setEmailId(userName);
        nada.deleteMessageWithSubject("PASSWORD RESET");

        forgotPasswordPage.setConfirmationMailFieldValue(userName);
        forgotPasswordPage.clickSubmitButton();

        VNextBOModalDialogSteps.clickOkButton();
        loginPage = new VNextBOLoginScreenWebPage();
        loginPage.userLogin(userName, userPassword);

        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
                new NadaEMailService.MailSearchParametersBuilder()
                        .withSubject("PASSWORD RESET");
        String mailMessage = nada.getMailMessageBySubjectKeywords(searchParametersBuilder);
        String resetPasswordUrl = nada.getUrlsFromMessage(mailMessage, "reset your password").get(0);

        webdriverGotoWebPage(resetPasswordUrl);

        VNextBOHomeWebPage vNextBOHomeWebPage = new VNextBOHomeWebPage();
        Assert.assertTrue(vNextBOHomeWebPage.isSupportForBOButtonDisplayed(), "Home page hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserReceivePasswordResetLinkToDifferentEmails(String rowID, String description, JSONObject testData) throws Exception {

        VNextBOForgotPasswordData data = JSonDataParser.getTestDataFromJson(testData, VNextBOForgotPasswordData.class);

        NadaEMailService nada = new NadaEMailService();
        nada.setEmailId(userName);
        nada.deleteMessageWithSubject("PASSWORD RESET");

        forgotPasswordPage.setConfirmationMailFieldValue(data.getEmail());
        forgotPasswordPage.clickSubmitButton();

        VNextBOModalDialogSteps.clickOkButton();

        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
                new NadaEMailService.MailSearchParametersBuilder()
                        .withSubject("PASSWORD RESET");
        String mailMessage = nada.getMailMessageBySubjectKeywords(searchParametersBuilder);
        String resetPasswordUrl = nada.getUrlsFromMessage(mailMessage, "reset your password").get(0);

        Assert.assertTrue(resetPasswordUrl.contains("resetPassword"), "User hasn't got link for a password reset");
    }
}
