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
    private static final String REGISTERED_USER_EMAIL = "test.mail.cyberiansoft@getnada.com";
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
        userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
        userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();

        loginPage = new VNextBOLoginScreenWebPage(webdriver);
        loginPage.clickForgotPasswordLink();
        forgotPasswordPage = new VNextBOForgotPasswordWebPage(webdriver);
    }

    @AfterMethod
    public void BackOfficeLogout() {
        try {
            VNextBOHeaderPanel headerPanel = new VNextBOHeaderPanel(webdriver);
            if (headerPanel.logOutLinkExists()) {
                headerPanel.userLogout();
            }
        } catch (RuntimeException ignored) {}

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
        loginPage = new VNextBOLoginScreenWebPage(webdriver);

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

        VNextBOModalDialog vNextBOWarningModalDialog = new VNextBOModalDialog(webdriver);

        Assert.assertTrue(vNextBOWarningModalDialog.isDialogDisplayed(), "Warning dialog hasn't been displayed");
        Assert.assertTrue(vNextBOWarningModalDialog.isOkButtonDisplayed(), "OK button hasn't been displayed");
        Assert.assertEquals(vNextBOWarningModalDialog.getDialogHeader(), "Warning",
                "Dialog header hasn't been correct");
        Assert.assertEquals(vNextBOWarningModalDialog.getDialogInformationMessage(),
                "This email is not found", "Dialog message hasn't been correct");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanResetPassword(String rowID, String description, JSONObject testData) throws Exception {

        NadaEMailService nada = new NadaEMailService();
        nada.setEmailId(REGISTERED_USER_EMAIL);
        nada.deleteMessageWithSubject("PASSWORD RESET");

        forgotPasswordPage.setConfirmationMailFieldValue(REGISTERED_USER_EMAIL);
        forgotPasswordPage.clickSubmitButton();

        VNextBOModalDialog vNextBOInformationModalDialog = new VNextBOModalDialog(webdriver);

        Assert.assertTrue(vNextBOInformationModalDialog.isDialogDisplayed(), "Information dialog hasn't been displayed");
        Assert.assertTrue(vNextBOInformationModalDialog.isOkButtonDisplayed(), "OK button hasn't been displayed");
        Assert.assertEquals(vNextBOInformationModalDialog.getDialogHeader(), "Information",
                "Dialog header hasn't been correct");
        Assert.assertEquals(vNextBOInformationModalDialog.getDialogInformationMessage(),
                "Please check your mailbox. You will receive an email with a link for resetting password within a few minutes.",
                "Dialog message hasn't been correct");
        vNextBOInformationModalDialog.clickOkButton();
        loginPage = new VNextBOLoginScreenWebPage(webdriver);

        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
                new NadaEMailService.MailSearchParametersBuilder()
                        .withSubject("PASSWORD RESET");
        String mailMessage = nada.getMailMessageBySubjectKeywords(searchParametersBuilder);
        String resetPasswordUrl = nada.getUrlsFromMessage(mailMessage, "reset your password").get(0);

        webdriverGotoWebPage(resetPasswordUrl);
        Utils.refreshPage();

        VNextBOResetPasswordPage vNextBOResetPasswordPage = new VNextBOResetPasswordPage(webdriver);
        Assert.assertEquals(vNextBOResetPasswordPage.getUserEmail(), REGISTERED_USER_EMAIL,
                "User's email hasn't been correct");

        vNextBOResetPasswordPage.setNewPassword(userPassword);
        loginPage = new VNextBOLoginScreenWebPage(webdriver);
        Assert.assertEquals(loginPage.getValueFromEmailField(), REGISTERED_USER_EMAIL,
                "Email field hasn't been correct");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCantResetPasswordAfterLogin(String rowID, String description, JSONObject testData) throws Exception {

        NadaEMailService nada = new NadaEMailService();
        nada.setEmailId(REGISTERED_USER_EMAIL);
        nada.deleteMessageWithSubject("PASSWORD RESET");

        forgotPasswordPage.setConfirmationMailFieldValue(REGISTERED_USER_EMAIL);
        forgotPasswordPage.clickSubmitButton();

        VNextBOModalDialog vNextBOInformationModalDialog = new VNextBOModalDialog(webdriver);
        vNextBOInformationModalDialog.clickOkButton();
        loginPage = new VNextBOLoginScreenWebPage(webdriver);
        loginPage.userLogin(userName, userPassword);

        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
                new NadaEMailService.MailSearchParametersBuilder()
                        .withSubject("PASSWORD RESET");
        String mailMessage = nada.getMailMessageBySubjectKeywords(searchParametersBuilder);
        String resetPasswordUrl = nada.getUrlsFromMessage(mailMessage, "reset your password").get(0);

        webdriverGotoWebPage(resetPasswordUrl);
        Utils.refreshPage();

        VNextBOHomeWebPage vNextBOHomeWebPage = new VNextBOHomeWebPage(webdriver);
        Assert.assertTrue(vNextBOHomeWebPage.isSupportForBOButtonDisplayed(), "Home page hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserReceivePasswordResetLinkToDifferentEmails(String rowID, String description, JSONObject testData) throws Exception {

        VNextBOForgotPasswordData data = JSonDataParser.getTestDataFromJson(testData, VNextBOForgotPasswordData.class);

        NadaEMailService nada = new NadaEMailService();
        nada.setEmailId(REGISTERED_USER_EMAIL);
        nada.deleteMessageWithSubject("PASSWORD RESET");

        forgotPasswordPage.setConfirmationMailFieldValue(data.getEmail());
        forgotPasswordPage.clickSubmitButton();

        VNextBOModalDialog vNextBOInformationModalDialog = new VNextBOModalDialog(webdriver);

        vNextBOInformationModalDialog.clickOkButton();

        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
                new NadaEMailService.MailSearchParametersBuilder()
                        .withSubject("PASSWORD RESET");
        String mailMessage = nada.getMailMessageBySubjectKeywords(searchParametersBuilder);
        String resetPasswordUrl = nada.getUrlsFromMessage(mailMessage, "reset your password").get(0);

        Assert.assertTrue(resetPasswordUrl.contains("resetPassword"), "User hasn't got link for a password reset");
    }
}
