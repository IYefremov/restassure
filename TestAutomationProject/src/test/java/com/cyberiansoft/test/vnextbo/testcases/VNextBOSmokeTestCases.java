package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOSmokeData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.email.EmailUtils;
import com.cyberiansoft.test.email.emaildata.EmailFolder;
import com.cyberiansoft.test.email.emaildata.EmailHost;
import com.cyberiansoft.test.email.getnada.NadaEMailService;
import com.cyberiansoft.test.vnext.config.VNextFreeRegistrationInfo;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.screens.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.cyberiansoft.test.vnextbo.utils.WebConstants.VNextBOErrorMessages.*;
import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOSmokeTestCases extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/VNextBOSmokeData.json";

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

	private String userMail = "";
	private final String confirmpsw = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
	private ArrayList<String> userslist = new ArrayList<>();

    final String userFromEmail = "ReconPro@cyberiansoft.com";
//	final String userFromEmail = "Repair360-qc@cyberianconcepts.com";
	
	@BeforeMethod
	public void BackOfficeLogin() {
        BrowserType browserType = BaseUtils.getBrowserType(VNextBOConfigInfo.getInstance().getDefaultBrowser());
        try {
            DriverBuilder.getInstance().setDriver(browserType);
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
        webdriver = DriverBuilder.getInstance().getDriver();
        webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOURL());
//        BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);
//        loginpage.userLogin(BOConfigInfo.getInstance().getUserName(), BOConfigInfo.getInstance().getUserPassword());
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
    public void testCreateUserWithoutWebAccess(String rowID, String description, JSONObject testData) {

        VNextBOSmokeData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSmokeData.class);

		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextBOConfigInfo.getInstance().getVNextBOMail(), VNextBOConfigInfo.getInstance().getVNextBOPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOUsersWebPage usersWebPage = leftmenu.selectUsersMenu();
		userMail = data.getUserMailPrefix() + RandomStringUtils.randomAlphanumeric(5) + data.getUserMailPostbox();
		VNexBOAddNewUserDialog adduserdialog = usersWebPage.clickAddUserButton();
		adduserdialog.createNewUser(data.getFirstName(), data.getLastName(), userMail, data.getUserPhone());
		Assert.assertTrue(usersWebPage.findUserInTableByUserEmail(userMail));
		userslist.add(userMail);
		VNextBOHeaderPanel headerpanel = PageFactory.initElements(webdriver,
				VNextBOHeaderPanel.class);
		headerpanel.userLogout();
	}
	
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testCreateUserWithWebAccess(String rowID, String description, JSONObject testData) throws Exception {

        VNextBOSmokeData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSmokeData.class);

		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextBOConfigInfo.getInstance().getVNextBOMail(), VNextBOConfigInfo.getInstance().getVNextBOPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOUsersWebPage userswabpage = leftmenu.selectUsersMenu();
		userMail = data.getUserMailPrefix() + RandomStringUtils.randomAlphanumeric(5) + data.getUserMailPostbox();
		VNexBOAddNewUserDialog adduserdialog = userswabpage.clickAddUserButton();
		adduserdialog.createNewUser(data.getFirstName(), data.getLastName(), userMail, data.getUserPhone(), true);
		Assert.assertTrue(userswabpage.findUserInTableByUserEmail(userMail));
		userslist.add(userMail);
		Assert.assertTrue(userswabpage.isRedWarningTrianglePresentForUser(userMail));

		EmailUtils emailUtils = new EmailUtils(EmailHost.GMAIL, userMail,
				VNextFreeRegistrationInfo.getInstance().getR360UserPassword(), EmailFolder.INBOX);
		EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
				.withSubject("ReconPro vNext Dev: REGISTRATION")
				.unreadOnlyMessages(true).maxMessagesToSearch(5);
		String mailmessage = emailUtils.waitForMessageWithSubjectInFolderAndGetMailMessage(mailSearchParameters);

		String confirmationurl = mailmessage.substring(mailmessage.indexOf("'") + 1, mailmessage.lastIndexOf("'"));
		
		VNextBOHeaderPanel headerpanel = PageFactory.initElements(webdriver,
				VNextBOHeaderPanel.class);
		headerpanel.userLogout();
		headerpanel.waitABit(4000);
		webdriver.get(confirmationurl);
		webdriver.navigate().to(confirmationurl);
		headerpanel.waitABit(4000);
		webdriver.get(confirmationurl);
		VNextBOConfirmPasswordWebPage confirmationpswpage = PageFactory.initElements(
				webdriver, VNextBOConfirmPasswordWebPage.class);
		loginpage = confirmationpswpage.confirmNewUserPassword(confirmpsw);

		loginpage.userLogin(userMail, confirmpsw);
		leftmenu.selectServicesMenu();
		headerpanel.userLogout();
	}
	
	@Test(description = "Test Case 41403:vNext: Verify user can change password using link from the email")
	public void testVerifyUserCanChangePasswordUsingLinkFromTheEmail() throws Exception {
		//todo start here
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		VNextBOForgotPasswordWebPage forgotpswpage = loginpage.clickForgotPasswordLink();
        userMail = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
        NadaEMailService nada = new NadaEMailService();
        nada.setEmailId(userMail);
        loginpage = forgotpswpage.sendConfirmationMail(userMail);
        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
                new NadaEMailService.MailSearchParametersBuilder()
                        .withSubject("ReconPro vNext Dev: PASSWORD RESET");
        String mailmessage = nada.getMailMessageBySybjectKeywords(searchParametersBuilder);
        String linkText = "reset your password";

        List<String> allMatches = nada.getUrlsFromMessage(mailmessage, linkText, "https", "'>");
        String confirmationUrl =  allMatches.get(0);
        Assert.assertTrue(!confirmationUrl.isEmpty(), "The requested password reset link is not displayed in the letter");
        nada.deleteMessageWithSubject("ReconPro vNext Dev: PASSWORD RESET");

		loginpage.waitABit(2000);
		webdriver.navigate().to(confirmationUrl);
		loginpage.waitABit(2000);
		webdriver.get(confirmationUrl);
		VNextBOConfirmPasswordWebPage confirmationpswpage = PageFactory.initElements(
				webdriver, VNextBOConfirmPasswordWebPage.class);
		loginpage = confirmationpswpage.confirmNewUserPassword(confirmpsw);
		loginpage.userLogin(userMail, confirmpsw);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		leftmenu.selectUsersMenu();
		VNextBOHeaderPanel headerpanel = PageFactory.initElements(webdriver,
				VNextBOHeaderPanel.class);
		headerpanel.userLogout();
	}
	
	@Test(description = "Test Case 41410:vNext: Verify change password page is not opened when user is logged in")
	@Parameters({ "user.name", "user.psw" })
	public void testVerifyUserCanChangePasswordPageIsNotOpenedWhenUserIsLoggedIn() throws Exception {
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		VNextBOForgotPasswordWebPage forgotpswpage = loginpage.clickForgotPasswordLink();
		loginpage = forgotpswpage.sendConfirmationMail(userMail);
		
		loginpage.userLogin(userMail, confirmpsw);
		
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		leftmenu.selectUsersMenu();

		EmailUtils emailUtils = new EmailUtils(EmailHost.GMAIL, userMail,
		VNextFreeRegistrationInfo.getInstance().getR360UserPassword(), EmailFolder.INBOX);
		EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
				.withSubject("ReconPro vNext Dev: PASSWORD RESET")
				.unreadOnlyMessages(true).maxMessagesToSearch(5);
		String mailmessage = emailUtils.waitForMessageWithSubjectInFolderAndGetMailMessage(mailSearchParameters);
		
		String confirmationurl = mailmessage.substring(mailmessage.indexOf("'")+1, mailmessage.lastIndexOf("'"));

		leftmenu.waitABit(5000);
		
		
		Set<Cookie> cookiesInstance1 = webdriver.manage().getCookies();
		
		for(Cookie cookie : cookiesInstance1)
		{
			webdriver.manage().addCookie(cookie);
		}
		webdriver.navigate().to(confirmationurl);
		
		leftmenu.selectInvoicesMenu();
		VNextBOHeaderPanel headerpanel = PageFactory.initElements(webdriver,
				VNextBOHeaderPanel.class);
		headerpanel.userLogout();
	}
	
	@Test(description = "Test Case 41412:vNext: Verify validation messages on change password page")
	@Parameters({ "user.name", "user.psw" })
	public void testVerifyValidationMessagesOnChangePasswordPage() throws Exception {
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		VNextBOForgotPasswordWebPage forgotpswpage = loginpage.clickForgotPasswordLink();
		loginpage = forgotpswpage.sendConfirmationMail(userMail);

		EmailUtils emailUtils = new EmailUtils(EmailHost.GMAIL, userMail,
				VNextFreeRegistrationInfo.getInstance().getR360UserPassword(), EmailFolder.INBOX);
		EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
				.withSubject("ReconPro vNext Dev: PASSWORD RESET")
				.unreadOnlyMessages(true).maxMessagesToSearch(5);
		String mailmessage = emailUtils.waitForMessageWithSubjectInFolderAndGetMailMessage(mailSearchParameters);

		
		String confirmationurl = confirmationurl = mailmessage.substring(mailmessage.indexOf("'")+1, mailmessage.lastIndexOf("'"));
		loginpage.waitABit(6000);
		webdriver.navigate().to(confirmationurl);
		loginpage.waitABit(4000);
		webdriver.get(confirmationurl);
		VNextBOConfirmPasswordWebPage confirmationpswpage = PageFactory.initElements(
				webdriver, VNextBOConfirmPasswordWebPage.class);
		confirmationpswpage.setUserPasswordFieldValue(confirmpsw);
		confirmationpswpage.clickSubmitButton();
		Assert.assertEquals(confirmationpswpage.getErrorMessageValue(), "Please confirm password!");
		confirmationpswpage.setUserConfirmPasswordFieldValue("222222");
		confirmationpswpage.clickSubmitButton();
		Assert.assertEquals(confirmationpswpage.getErrorMessageValue(), "Passwords do not match!");
	}
	
	@Test(description = "Test Case 41411:vNext: Verify valiation messages on request pasword reset page")
	public void testVerifyValidationMessagesOnRequestPasswordResetPage() {
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		VNextBOForgotPasswordWebPage forgotPasswordPage = loginpage.clickForgotPasswordLink();
		forgotPasswordPage.clickSubmitButton();
		Assert.assertEquals(forgotPasswordPage.getErrorMessageValue(), EMAIL_IS_INVALID);
		forgotPasswordPage.setConfirmationMailFieldValue(confirmpsw);
		forgotPasswordPage.clickSubmitButton();
		Assert.assertEquals(forgotPasswordPage.getErrorMessageValue(), EMAIL_IS_INVALID);
	}
	
	@Test(description = "Test Case 41408:vNext:Verify 'Forgot password' page is opened on click 'Forgot password' link")
	@Parameters({ "user.name", "user.psw" })
	public void testVerifyForgotPasswordPageIsOpenedOnClickForgotPasswordLink() {
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		VNextBOForgotPasswordWebPage forgotpswpage = loginpage.clickForgotPasswordLink();
		Assert.assertTrue(forgotpswpage.isConfirmationMailFieldDisplayed());
		forgotpswpage.clickSubmitButton();
		Assert.assertEquals(forgotpswpage.getErrorMessageValue(), EMAIL_IS_INVALID);
		
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testEditNotConfirmedUserWithWebAccess(String rowID, String description, JSONObject testData) {

        VNextBOSmokeData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSmokeData.class);

        VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextBOConfigInfo.getInstance().getVNextBOMail(), VNextBOConfigInfo.getInstance().getVNextBOPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOUsersWebPage userswabpage = leftmenu.selectUsersMenu();
		userMail = data.getUserMailPrefix() + RandomStringUtils.randomAlphanumeric(5) + data.getUserMailPostbox();
		VNexBOAddNewUserDialog adduserdialog = userswabpage.clickAddUserButton();
		adduserdialog.createNewUser(data.getFirstName(), data.getLastName(), userMail, data.getUserPhone(), true);
		Assert.assertTrue(userswabpage.findUserInTableByUserEmail(userMail));
		userslist.add(userMail);
		Assert.assertTrue(userswabpage.isRedWarningTrianglePresentForUser(userMail));
		adduserdialog = userswabpage.clickEditButtonForUser(userMail);
		Assert.assertTrue(adduserdialog.isEmailFieldDisabled());
		adduserdialog.setUserFirstName(data.getFirstName() + data.getUserEdited());
		adduserdialog.setUserLastName(data.getLastName() + data.getUserEdited());
		adduserdialog.clickSaveButtonAndWait();
		
		Assert.assertTrue(userswabpage.findUserInTableByUserEmail(userMail));
		Assert.assertEquals(userswabpage.getUserName(userMail), data.getFirstName() + data.getUserEdited() + " " + data.getLastName() + data.getUserEdited());
		adduserdialog = userswabpage.clickEditButtonForUser(userMail);
		adduserdialog.setUserPhone(data.getUserNewPhone());
		final String userphonecountrycode = adduserdialog.getSelectedUserPhoneCountryCode();
		adduserdialog.unselectWebAccessCheckbox();
		adduserdialog.clickSaveButtonAndWait();
		Assert.assertTrue(userswabpage.findUserInTableByUserEmail(userMail));
		Assert.assertEquals(userswabpage.getUserPhone(userMail), userphonecountrycode + data.getUserNewPhone());
		Assert.assertFalse(userswabpage.isRedWarningTrianglePresentForUser(userMail));
		Assert.assertEquals(userswabpage.getUserName(userMail), data.getFirstName() + data.getUserEdited() + " " + data.getLastName() + data.getUserEdited());
		
		VNextBOHeaderPanel headerpanel = PageFactory.initElements(webdriver,
				VNextBOHeaderPanel.class);
		headerpanel.userLogout();
	}
	
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testEditUserWithoutWebAccess(String rowID, String description, JSONObject testData) {

        VNextBOSmokeData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSmokeData.class);
        
        VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextBOConfigInfo.getInstance().getVNextBOMail(), VNextBOConfigInfo.getInstance().getVNextBOPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOUsersWebPage userswabpage = leftmenu.selectUsersMenu();
		userMail = data.getUserMailPrefix() + RandomStringUtils.randomAlphanumeric(5) + data.getUserMailPostbox();
		VNexBOAddNewUserDialog adduserdialog = userswabpage.clickAddUserButton();
		adduserdialog.createNewUser(data.getFirstName(), data.getLastName(), userMail, data.getUserPhone(), false);
		Assert.assertTrue(userswabpage.findUserInTableByUserEmail(userMail));
		userslist.add(userMail);
		Assert.assertFalse(userswabpage.isRedWarningTrianglePresentForUser(userMail));
		adduserdialog = userswabpage.clickEditButtonForUser(userMail);
		Assert.assertTrue(adduserdialog.isEmailFieldDisabled());
		adduserdialog.setUserFirstName(data.getFirstName() + data.getUserEdited());
		adduserdialog.setUserLastName(data.getLastName() + data.getUserEdited());
		adduserdialog.clickSaveButtonAndWait();
		
		Assert.assertTrue(userswabpage.findUserInTableByUserEmail(userMail));
		Assert.assertEquals(userswabpage.getUserName(userMail), data.getFirstName() + data.getUserEdited() + " " + data.getLastName() + data.getUserEdited());
		adduserdialog = userswabpage.clickEditButtonForUser(userMail);
		adduserdialog.setUserPhone(data.getUserNewPhone());
		final String userphonecountrycode = adduserdialog.getSelectedUserPhoneCountryCode();
		adduserdialog.clickSaveButtonAndWait();
		Assert.assertTrue(userswabpage.findUserInTableByUserEmail(userMail));
		Assert.assertEquals(userswabpage.getUserPhone(userMail), userphonecountrycode + data.getUserNewPhone());
		Assert.assertFalse(userswabpage.isRedWarningTrianglePresentForUser(userMail));
		Assert.assertEquals(userswabpage.getUserName(userMail), data.getFirstName() + data.getUserEdited() + " " + data.getLastName() + data.getUserEdited());
		
		VNextBOHeaderPanel headerpanel = PageFactory.initElements(webdriver,
				VNextBOHeaderPanel.class);
		headerpanel.userLogout();
	}
	
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCheckPossibilityToResendRegistrationEmail(String rowID, String description, JSONObject testData) throws Exception {

        VNextBOSmokeData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSmokeData.class);

        VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextBOConfigInfo.getInstance().getVNextBOMail(),
                VNextBOConfigInfo.getInstance().getVNextBOPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOUsersWebPage userswebpage = leftmenu.selectUsersMenu();
		userMail = data.getUserMailPrefix() + RandomStringUtils.randomAlphanumeric(7) + data.getUserMailPostbox();
		VNexBOAddNewUserDialog adduserdialog = userswebpage.clickAddUserButton();

		NadaEMailService nada = new NadaEMailService();
        String emailId = nada.getEmailId();
        adduserdialog.createNewUser(data.getFirstName(), data.getLastName(), emailId, data.getUserPhone(), true);
		Assert.assertTrue(userswebpage.findUserInTableByUserEmail(emailId));
		userslist.add(emailId);
		Assert.assertTrue(userswebpage.isRedWarningTrianglePresentForUser(emailId));

		// verify the letter is sent
        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
                new NadaEMailService.MailSearchParametersBuilder()
                        .withSubject("ReconPro vNext Dev: REGISTRATION");
        String mailmessage = nada.getMailMessageBySybjectKeywords(searchParametersBuilder);
        String linkText = "Click here";
        List<String> allMatches = nada.getUrlsFromMessage(mailmessage, linkText);
        String newbourl =  allMatches.get(0).substring(0, allMatches.get(0).indexOf("\" style"));

		Assert.assertTrue(!newbourl.isEmpty(), "The new BO url is not displayed in the letter");
        nada.deleteMessageWithSubject("ReconPro vNext Dev: REGISTRATION");
        int numOfMessages = mailmessage.length();

        // Click the resend letter button and cancel
		userswebpage.clickUserResendButtonAndDisagree(emailId);
        try {
            mailmessage = nada.getMailMessageBySybjectKeywords(searchParametersBuilder);
        } catch (NullPointerException ignored) {}
        int updatedNumOfMessages = mailmessage.length();
        System.out.println(updatedNumOfMessages);
        Assert.assertEquals(numOfMessages, updatedNumOfMessages, "The number of messages differs");

        // Verify email is sent after clicking the Resend button
        userswebpage.clickUserResendButtonAndAgree(emailId);
        mailmessage = nada.getMailMessageBySybjectKeywords(searchParametersBuilder);

        allMatches = nada.getUrlsFromMessage(mailmessage, linkText);
        newbourl =  allMatches.get(0).substring(0, allMatches.get(0).indexOf("\" style"));

        Assert.assertTrue(!newbourl.isEmpty(), "The new BO url is not displayed in the letter");
        nada.deleteMessageWithSubject("ReconPro vNext Dev: REGISTRATION");
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCheckValidationErrorsOnTheCreateNewUserDialog(String rowID, String description, JSONObject testData) {

        VNextBOSmokeData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSmokeData.class);

        VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextBOConfigInfo.getInstance().getVNextBOMail(), VNextBOConfigInfo.getInstance().getVNextBOPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOUsersWebPage userswabpage = leftmenu.selectUsersMenu();
		userMail = data.getUserMailPrefix() + RandomStringUtils.randomAlphanumeric(5) + data.getUserMailPostbox();
		VNexBOAddNewUserDialog adduserdialog = userswabpage.clickAddUserButton();
		adduserdialog.clickSaveButton();
		Assert.assertTrue(adduserdialog.isErrorMessageShown(FIRST_NAME_IS_REQUIRED.getErrorMessage()));
		Assert.assertTrue(adduserdialog.isErrorMessageShown(LAST_NAME_IS_REQUIRED.getErrorMessage()));
		Assert.assertTrue(adduserdialog.isErrorMessageShown(EMAIL_IS_REQUIRED.getErrorMessage()));
		
		adduserdialog.setUserFirstName(data.getFirstName());
		adduserdialog.clickSaveButton();
		Assert.assertFalse(adduserdialog.isErrorMessageShown(FIRST_NAME_IS_REQUIRED.getErrorMessage()));
		Assert.assertTrue(adduserdialog.isErrorMessageShown(LAST_NAME_IS_REQUIRED.getErrorMessage()));
		Assert.assertTrue(adduserdialog.isErrorMessageShown(EMAIL_IS_REQUIRED.getErrorMessage()));
		
		adduserdialog.setUserLastName(data.getLastName());
		adduserdialog.clickSaveButton();
		Assert.assertFalse(adduserdialog.isErrorMessageShown(FIRST_NAME_IS_REQUIRED.getErrorMessage()));
		Assert.assertFalse(adduserdialog.isErrorMessageShown(LAST_NAME_IS_REQUIRED.getErrorMessage()));
		Assert.assertTrue(adduserdialog.isErrorMessageShown(EMAIL_IS_REQUIRED.getErrorMessage()));
		
		adduserdialog.setUserEmail(userMail);
		adduserdialog.clickSaveButtonAndWait();
		Assert.assertTrue(userswabpage.findUserInTableByUserEmail(userMail));
		userslist.add(userMail);
	}
	
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCheckValidationErrorsOnTheSetPasswordPage(String rowID, String description, JSONObject testData) throws Exception {

        VNextBOSmokeData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSmokeData.class);

		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextBOConfigInfo.getInstance().getVNextBOMail(), VNextBOConfigInfo.getInstance().getVNextBOPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOUsersWebPage userswebpage = leftmenu.selectUsersMenu();
        VNexBOAddNewUserDialog adduserdialog = userswebpage.clickAddUserButton();
        NadaEMailService nada = new NadaEMailService();
        String emailId = nada.getEmailId();
        String firstName = data.getFirstName();
        adduserdialog.createNewUser(firstName, data.getLastName(), emailId, data.getUserPhone(), true);
        Assert.assertTrue(userswebpage.findUserBySearch(firstName, emailId));
        Assert.assertTrue(userswebpage.isRedWarningTrianglePresentForUser(emailId));

//todo bug 73134 - fix after bug fixing
        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
                new NadaEMailService.MailSearchParametersBuilder()
                        .withSubject("ReconPro vNext Dev: REGISTRATION");
        String mailmessage = nada.getMailMessageBySybjectKeywords(searchParametersBuilder);
        String linkText = "Click here";
        List<String> allMatches = nada.getUrlsFromMessage(mailmessage, linkText, "https", "\" style");
        String confirmationUrl =  allMatches.get(0);
        System.out.println(mailmessage);
        System.out.println("URL: "+confirmationUrl);


        Assert.assertTrue(!confirmationUrl.isEmpty(), "The new BO url is not displayed in the letter");
        nada.deleteMessageWithSubject("ReconPro vNext Dev: REGISTRATION");


//		EmailUtils emailUtils = new EmailUtils(EmailHost.GMAIL, userMail,
//				VNextConfigInfo.getInstance().getUserCapiUserPassword(), EmailFolder.INBOX);
//		EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
//				.withSubject("ReconPro vNext Dev: REGISTRATION")
//				.unreadOnlyMessages(true).maxMessagesToSearch(5);
//		String mailmessage = emailUtils.waitForMessageWithSubjectInFolderAndGetMailMessage(mailSearchParameters);
//		String confirmationurl = mailmessage.substring(mailmessage.indexOf("'") + 1, mailmessage.lastIndexOf("'"));

		VNextBOHeaderPanel headerpanel = PageFactory.initElements(webdriver,
				VNextBOHeaderPanel.class);
		headerpanel.userLogout();
		headerpanel.waitABit(2000);
		webdriver.navigate().to(confirmationUrl);
		headerpanel.waitABit(4000);
		webdriver.get(confirmationUrl);
		VNextBOConfirmPasswordWebPage confirmationpswpage = PageFactory.initElements(
				webdriver, VNextBOConfirmPasswordWebPage.class);

		confirmationpswpage.clickSubmitButton();
		Assert.assertEquals(confirmationpswpage.getErrorMessageValue(), PLEASE_ENTER_PASSWORD.getErrorMessage());
		confirmationpswpage.setUserPasswordFieldValue(data.getShortPassword());
		confirmationpswpage.clickSubmitButton();
		Assert.assertEquals(confirmationpswpage.getErrorMessageValue(), PASSWORD_SHOULD_BE_LONGER.getErrorMessage());
		confirmationpswpage.setUserPasswordFieldValue(confirmpsw);
		confirmationpswpage.clickSubmitButton();
		Assert.assertEquals(confirmationpswpage.getErrorMessageValue(), PLEASE_CONFIRM_PASSWORD.getErrorMessage());
		loginpage = confirmationpswpage.confirmNewUserPassword(confirmpsw);

		loginpage.userLogin(userMail, confirmpsw);
		leftmenu.selectUsersMenu();
		headerpanel.userLogout();
	}
	
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateUserWithExistingEmailAddress(String rowID, String description, JSONObject testData) {

        VNextBOSmokeData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSmokeData.class);

        VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextBOConfigInfo.getInstance().getVNextBOMail(), VNextBOConfigInfo.getInstance().getVNextBOPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOUsersWebPage userswabpage = leftmenu.selectUsersMenu();
		userMail = data.getUserMailPrefix() + RandomStringUtils.randomAlphanumeric(5) + data.getUserMailPostbox();
		VNexBOAddNewUserDialog adduserdialog = userswabpage.clickAddUserButton();
		adduserdialog.createNewUser(data.getFirstName(), data.getLastName(), userMail, data.getUserPhone(), true);
		Assert.assertTrue(userswabpage.findUserInTableByUserEmail(userMail));
		userslist.add(userMail);
		Assert.assertTrue(userswabpage.isRedWarningTrianglePresentForUser(userMail));
		adduserdialog = userswabpage.clickAddUserButton();
		adduserdialog.setUserFirstName(data.getFirstName());
		adduserdialog.setUserLastName(data.getLastName());
		adduserdialog.setUserEmail(userMail);
		adduserdialog.selectWebAccessCheckbox();
		adduserdialog.clickSaveButton();
		Assert.assertFalse(adduserdialog.isErrorMessageShown(FIRST_NAME_IS_REQUIRED.getErrorMessage()));
		adduserdialog.isErrorMessageShown("E-mail " + userMail + " is occupied");
		adduserdialog.closeadduserDialog();
	}

    //        webdriver = WebdriverInicializator //todo use in other TC to approve the link
//                .getInstance()
//                .initWebDriver(BaseUtils
//                .getBrowserType(VNextBOConfigInfo
//                        .getInstance()
//                        .getDefaultBrowser()));
//        WebDriverUtils.webdriverGotoWebPage(newbourl);
}