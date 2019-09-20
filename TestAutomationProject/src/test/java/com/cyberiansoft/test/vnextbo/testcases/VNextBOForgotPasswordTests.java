package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOForgotPasswordData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.email.getnada.NadaEMailService;
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

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/VNextBOForgotPasswordPageData.json";
    private String registeredUserEmail = "test.mail.cyberiansoft@getnada.com";
    private String notRegisteredUserEmail = "cyberiansoft.test22@nada.ltd";
    private String userName;
    private String userPassword;
    private VNextBOLoginScreenWebPage loginPage;
    private VNextBOHomeWebPage homePage;
    private VNexBOLeftMenuPanel leftMenu;
    VNextBOForgotPasswordWebPage forgotPasswordPage;

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

        loginPage = PageFactory.initElements(webdriver, VNextBOLoginScreenWebPage.class);
        forgotPasswordPage = loginPage.clickForgotPasswordLink();
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
    public void verifyUserIsRedirectedToForgotPasswordPage(String rowID, String description, JSONObject testData) {

        Assert.assertTrue(forgotPasswordPage.isConfirmationMailFieldDisplayed(), "Email field hasn't been displayed");
        Assert.assertTrue(forgotPasswordPage.isLoginLinkDisplayed(), "Login link hasn't been displayed");
        Assert.assertTrue(forgotPasswordPage.isSubmitButtonDisplayed(), "Submit button hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserIsReturnedToLoginPage(String rowID, String description, JSONObject testData) {

        loginPage = forgotPasswordPage.clickLoginLink();

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
                "Error message hasb't been correct or not displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyEmailIsNotSentToNotRegisteredUser(String rowID, String description, JSONObject testData) throws Exception {

        forgotPasswordPage.setConfirmationMailFieldValue(notRegisteredUserEmail);
        forgotPasswordPage.clickSubmitButton();

        VNextBOModalDialog vNextBOWarningModalDialog =
                PageFactory.initElements(webdriver, VNextBOModalDialog.class);

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
        nada.setEmailId(registeredUserEmail);
        nada.deleteMessageWithSubject("PASSWORD RESET");

        forgotPasswordPage.setConfirmationMailFieldValue(registeredUserEmail);
        forgotPasswordPage.clickSubmitButton();

        VNextBOModalDialog vNextBOInformationModalDialog =
                PageFactory.initElements(webdriver, VNextBOModalDialog.class);

        Assert.assertTrue(vNextBOInformationModalDialog.isDialogDisplayed(), "Information dialog hasn't been displayed");
        Assert.assertTrue(vNextBOInformationModalDialog.isOkButtonDisplayed(), "OK button hasn't been displayed");
        Assert.assertEquals(vNextBOInformationModalDialog.getDialogHeader(), "Information",
                "Dialog header hasn't been correct");
        Assert.assertEquals(vNextBOInformationModalDialog.getDialogInformationMessage(),
                "Please check your mailbox. You will receive an email with a link for resetting password within a few minutes.",
                "Dialog message hasn't been correct");
        loginPage = vNextBOInformationModalDialog.clickOkButton();

        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
                new NadaEMailService.MailSearchParametersBuilder()
                        .withSubject("PASSWORD RESET");
        String mailmessage = nada.getMailMessageBySybjectKeywords(searchParametersBuilder);
        String resetPasswordUrl = nada.getUrlsFromMessage(mailmessage, "reset your password").get(0);

        webdriverGotoWebPage(resetPasswordUrl);
        webdriver.navigate().refresh();

        VNextBOResetPasswordPage vNextBOResetPasswordPage =
                PageFactory.initElements(webdriver, VNextBOResetPasswordPage.class);
        Assert.assertEquals(vNextBOResetPasswordPage.getUserEmail(), registeredUserEmail,
                "User's email hasn't been correct");

        loginPage = vNextBOResetPasswordPage.setNewPassword("ZZzz11!!");
        Assert.assertEquals(loginPage.getValueFromEmailField(), registeredUserEmail,
                "Email field hasn't been correct");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCantResetPasswordAfterLogin(String rowID, String description, JSONObject testData) throws Exception {

        NadaEMailService nada = new NadaEMailService();
        nada.setEmailId(registeredUserEmail);
        nada.deleteMessageWithSubject("PASSWORD RESET");

        forgotPasswordPage.setConfirmationMailFieldValue(registeredUserEmail);
        forgotPasswordPage.clickSubmitButton();

        VNextBOModalDialog vNextBOInformationModalDialog =
                PageFactory.initElements(webdriver, VNextBOModalDialog.class);
        loginPage = vNextBOInformationModalDialog.clickOkButton();
        loginPage.userLogin(userName, userPassword);

        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
                new NadaEMailService.MailSearchParametersBuilder()
                        .withSubject("PASSWORD RESET");
        String mailmessage = nada.getMailMessageBySybjectKeywords(searchParametersBuilder);
        String resetPasswordUrl = nada.getUrlsFromMessage(mailmessage, "reset your password").get(0);

        webdriverGotoWebPage(resetPasswordUrl);
        webdriver.navigate().refresh();

        VNextBOHomeWebPage vNextBOHomeWebPage =
                PageFactory.initElements(webdriver, VNextBOHomeWebPage.class);
        Assert.assertTrue(vNextBOHomeWebPage.isSupportForBOButtonDisplayed(), "Home page hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserReceivePasswordResetLinkToDifferentEmails(String rowID, String description, JSONObject testData) throws Exception {

        VNextBOForgotPasswordData data = JSonDataParser.getTestDataFromJson(testData, VNextBOForgotPasswordData.class);

        NadaEMailService nada = new NadaEMailService();
        nada.setEmailId(registeredUserEmail);
        nada.deleteMessageWithSubject("PASSWORD RESET");

        forgotPasswordPage.setConfirmationMailFieldValue(data.getEmail());
        forgotPasswordPage.clickSubmitButton();

        VNextBOModalDialog vNextBOInformationModalDialog =
                PageFactory.initElements(webdriver, VNextBOModalDialog.class);

        vNextBOInformationModalDialog.clickOkButton();

        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
                new NadaEMailService.MailSearchParametersBuilder()
                        .withSubject("PASSWORD RESET");
        String mailmessage = nada.getMailMessageBySybjectKeywords(searchParametersBuilder);
        String resetPasswordUrl = nada.getUrlsFromMessage(mailmessage, "reset your password").get(0);

        Assert.assertTrue(resetPasswordUrl.contains("resetPassword"), "User hasn't got link for a password reset");
    }
}
