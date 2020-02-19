package com.cyberiansoft.test.vnextbo.testcases.login;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOForgotPasswordData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.email.getnada.NadaEMailService;
import com.cyberiansoft.test.enums.ErrorMessages;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.VNextBOLoginInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOForgotPasswordWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOHomeWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOResetPasswordPage;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOHeaderPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.login.VNextBOLoginSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.dialogs.VNextBOModalDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.homepage.VNextBOHomeWebPageValidations;
import com.cyberiansoft.test.vnextbo.validations.login.VNextBOLoginValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOForgotPasswordTests extends BaseTestCase {

    private static final String NOT_REGISTERED_USER_EMAIL = "cyberiansoft.test22@nada.ltd";
    private String userName;
    private String userPassword;
    private VNextBOForgotPasswordWebPage forgotPasswordPage;

    @Override
    @BeforeClass
    public void login() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getLoginForgotPasswordTD();
        webdriverGotoWebPage(BaseTestCase.getBackOfficeURL());
        userName = VNextBOConfigInfo.getInstance().getVNextBONadaTestMail();
        userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();
        VNextBOLoginInteractions.clickForgotPasswordLink();
        forgotPasswordPage = new VNextBOForgotPasswordWebPage();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserIsRedirectedToForgotPasswordPage(String rowID, String description, JSONObject testData) {

        Assert.assertTrue(forgotPasswordPage.isConfirmationMailFieldDisplayed(), "Email field hasn't been displayed");
        Assert.assertTrue(forgotPasswordPage.isLoginLinkDisplayed(), "Login link hasn't been displayed");
        Assert.assertTrue(forgotPasswordPage.isSubmitButtonDisplayed(), "Submit button hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyUserIsReturnedToLoginPage(String rowID, String description, JSONObject testData) {

        forgotPasswordPage.clickLoginLink();
        Assert.assertTrue(VNextBOLoginValidations.isLoginFormDisplayed(), "Login form hasn't been displayed");
        Assert.assertTrue(VNextBOLoginValidations.isEmailFieldDisplayed(), "Email field hasn't been displayed");
        Assert.assertTrue(VNextBOLoginValidations.isPasswordFieldDisplayed(), "Passford field hasn't been displayed");
        Assert.assertTrue(VNextBOLoginValidations.isForgotPasswordLinkDisplayed(), "Forgot password link hasn't been displayed");
        Assert.assertTrue(VNextBOLoginValidations.isLoginButtonDisplayed(), "Login button hasn't been displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
    public void verifyErrorWithEmptyEmailField(String rowID, String description, JSONObject testData) {

        VNextBOLoginInteractions.clickForgotPasswordLink();
        forgotPasswordPage.clickSubmitButton();
        Assert.assertEquals(forgotPasswordPage.getErrorMessageValue(), ErrorMessages.EMAIL_IS_INVALID.getErrorMessage(),
                "Error message hasn't been correct or not displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
    public void verifyEmailIsNotSentToNotRegisteredUser(String rowID, String description, JSONObject testData) throws Exception {

        forgotPasswordPage.setConfirmationMailFieldValue(NOT_REGISTERED_USER_EMAIL);
        forgotPasswordPage.clickSubmitButton();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogValidations.verifyOkButtonIsDisplayed();
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogHeader(), "Warning",
                "Dialog header hasn't been correct");
        Assert.assertEquals(VNextBOModalDialogSteps.getDialogInformationMessage(),
                "This email is not found", "Dialog message hasn't been correct");
        VNextBOModalDialogSteps.clickOkButton();
        Utils.refreshPage();
        VNextBOLoginInteractions.clickForgotPasswordLink();
    }

    @Test(enabled = false, dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyUserCanResetPassword(String rowID, String description, JSONObject testData) throws Exception {

        NadaEMailService nada = new NadaEMailService();
        nada.setEmailId(userName);
        nada.deleteAllMessages();
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

        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
                new NadaEMailService.MailSearchParametersBuilder()
                        .withSubject("PASSWORD RESET");
        String mailMessage = nada.getMailMessageBySubjectKeywords(searchParametersBuilder);
        String resetPasswordUrl = nada.getUrlsFromMessage(mailMessage, "reset your password").get(0);

        webdriverGotoWebPage(resetPasswordUrl);
        Utils.refreshPage();

        VNextBOResetPasswordPage vNextBOResetPasswordPage = new VNextBOResetPasswordPage();
        vNextBOResetPasswordPage.waitUntilPageIsLoaded();
        Assert.assertEquals(vNextBOResetPasswordPage.getUserEmail(), userName,
                "User's email hasn't been correct");

        vNextBOResetPasswordPage.setNewPassword(userPassword);
        VNextBOLoginInteractions.waitUntilPageIsLoaded();
        Assert.assertEquals(VNextBOLoginInteractions.getValueFromEmailField(), userName,
                "Email field hasn't been correct");
        Utils.refreshPage();
        VNextBOLoginInteractions.clickForgotPasswordLink();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyUserCantResetPasswordAfterLogin(String rowID, String description, JSONObject testData) throws Exception {

        NadaEMailService nada = new NadaEMailService();
        nada.setEmailId(userName);
        nada.deleteAllMessages();
        forgotPasswordPage.setConfirmationMailFieldValue(userName);
        forgotPasswordPage.clickSubmitButton();

        VNextBOModalDialogSteps.clickOkButton();
        VNextBOLoginSteps.userLogin(userName, userPassword);

        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
                new NadaEMailService.MailSearchParametersBuilder()
                        .withSubject("PASSWORD RESET");
        String mailMessage = nada.getMailMessageBySubjectKeywords(searchParametersBuilder);
        String resetPasswordUrl = nada.getUrlsFromMessage(mailMessage, "reset your password").get(0);

        webdriverGotoWebPage(resetPasswordUrl);

        VNextBOHomeWebPage vNextBOHomeWebPage = new VNextBOHomeWebPage();
        VNextBOHomeWebPageValidations.verifySupportForBOButtonIsDisplayed();
        VNextBOHeaderPanelSteps.logout();
        VNextBOLoginInteractions.clickForgotPasswordLink();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 6)
    public void verifyUserReceivePasswordResetLinkToDifferentEmails(String rowID, String description, JSONObject testData) throws Exception {

        VNextBOForgotPasswordData data = JSonDataParser.getTestDataFromJson(testData, VNextBOForgotPasswordData.class);

        NadaEMailService nada = new NadaEMailService();
        nada.setEmailId(userName);
        nada.deleteAllMessages();
        forgotPasswordPage.setConfirmationMailFieldValue(data.getEmail());
        forgotPasswordPage.clickSubmitButton();

        VNextBOModalDialogSteps.clickOkButton();

        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
                new NadaEMailService.MailSearchParametersBuilder()
                        .withSubject("PASSWORD RESET");
        String mailMessage = nada.getMailMessageBySubjectKeywords(searchParametersBuilder);
        String resetPasswordUrl = nada.getUrlsFromMessage(mailMessage, "reset your password").get(0);

        Assert.assertTrue(resetPasswordUrl.contains("resetPassword"), "User hasn't got link for a password reset");
        VNextBOLoginInteractions.clickForgotPasswordLink();
    }
}