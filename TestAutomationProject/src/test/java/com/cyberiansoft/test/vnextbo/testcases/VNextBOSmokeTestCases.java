package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.testcases.BaseTestCase;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.email.EmailUtils;
import com.cyberiansoft.test.email.emaildata.EmailFolder;
import com.cyberiansoft.test.email.emaildata.EmailHost;
import com.cyberiansoft.test.vnext.config.VNextConfigInfo;
import com.cyberiansoft.test.vnext.utils.VNextWebServicesUtils;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.screens.*;
import com.cyberiansoft.test.vnextbo.utils.VNextBOErrorMessages;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class VNextBOSmokeTestCases extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/VNextBOSmokeData.json";

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

	String usermail = "";
	final String confirmpsw = "111111";
	ArrayList<String> userslist = new ArrayList<String>();
	
	//final String userFromEmail = "ReconPro@cyberiansoft.com";
	final String userFromEmail = "Repair360-qc@cyberianconcepts.com";
	
	@BeforeMethod
	public void BackOfficeLogin() {
        browserType = BaseUtils.getBrowserType(VNextBOConfigInfo.getInstance().getDefaultBrowser());
        try {
            DriverBuilder.getInstance().setDriver(browserType);
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
        webdriver = DriverBuilder.getInstance().getDriver();
        WebDriverUtils.webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOURL());
//        BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);
//        loginpage.UserLogin(BOConfigInfo.getInstance().getUserName(), BOConfigInfo.getInstance().getUserPassword());
	}
	
	@AfterMethod
	public void BackOfficeLogout() {
		VNextBOHeaderPanel headerpanel = PageFactory.initElements(webdriver,
				VNextBOHeaderPanel.class);
		if (headerpanel.logOutLinkExists())
			headerpanel.userLogout();		
	}
	
	@AfterSuite
	public void clearUsers() throws IOException {
		if (userslist.size() > 0)
			for (String usermails : userslist)
				VNextWebServicesUtils.deleteUserByMail(usermails);	
	}
	
	@Test(description = "Test Case 43038:vNext: create user without Web access")
	public void testCreateUserWithoutWebAccess() {
		
		final String firstname = "TestTech";
		final String lastname = "QA";
		final String usermailprefix = "test.cyberiansoft+";
		final String usermailpostbox = "@gmail.com";
		final String userphone = "12345";
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextBOConfigInfo.getInstance().getVNextBOMail(), VNextBOConfigInfo.getInstance().getVNextBOPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOUsersWebPage userswabpage = leftmenu.selectUsersMenu();
		final String usermail = usermailprefix + UUID.randomUUID() + usermailpostbox;
		VNexBOAddNewUserDialog adduserdialog = userswabpage.clickAddUserButton();
		adduserdialog.createNewUser(firstname, lastname, usermail, userphone);
		Assert.assertTrue(userswabpage.findUserInTableByUserEmail(usermail));
		userslist.add(usermail);
		VNextBOHeaderPanel headerpanel = PageFactory.initElements(webdriver,
				VNextBOHeaderPanel.class);
		headerpanel.userLogout();
	}
	
	@Test(description = "Test Case 43046:vNext: create user with Web access")
	public void testCreateUserWithWebAccess() throws Exception {
		
		final String firstname = "TestTech";
		final String lastname = "QA";
		final String usermailprefix = "test.cyberiansoft+";
		final String usermailpostbox = "@gmail.com";
		final String userphone = "12345";
		
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextBOConfigInfo.getInstance().getVNextBOMail(), VNextBOConfigInfo.getInstance().getVNextBOPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOUsersWebPage userswabpage = leftmenu.selectUsersMenu();
		usermail = usermailprefix + UUID.randomUUID() + usermailpostbox;
		VNexBOAddNewUserDialog adduserdialog = userswabpage.clickAddUserButton();
		adduserdialog.createNewUser(firstname, lastname, usermail, userphone, true);
		Assert.assertTrue(userswabpage.findUserInTableByUserEmail(usermail));
		userslist.add(usermail);
		Assert.assertTrue(userswabpage.isRedWarningTrianglePresentForUser(usermail));

		EmailUtils emailUtils = new EmailUtils(EmailHost.GMAIL, usermail,
				VNextConfigInfo.getInstance().getUserCapiUserPassword(), EmailFolder.INBOX);
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

		loginpage.userLogin(usermail, confirmpsw);
		leftmenu.selectServicesMenu();
		headerpanel.userLogout();
	}
	
	@Test(description = "Test Case 41403:vNext: Verify user can change password using link from the email")
	public void testVerifyUserCanChangePasswordUsingLinkFromTheEmail() throws Exception {
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		VNextBOForgotPasswordWebPage forgotpswpage = loginpage.clickForgotPasswordLink();
		loginpage = forgotpswpage.sendConfirmationMail(usermail);

		EmailUtils emailUtils = new EmailUtils(EmailHost.GMAIL, usermail,
				VNextConfigInfo.getInstance().getUserCapiUserPassword(), EmailFolder.INBOX);
		EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
				.withSubject("ReconPro vNext Dev: PASSWORD RESET")
				.unreadOnlyMessages(true).maxMessagesToSearch(5);
		String mailmessage = emailUtils.waitForMessageWithSubjectInFolderAndGetMailMessage(mailSearchParameters);

		String confirmationurl = confirmationurl = mailmessage.substring(mailmessage.indexOf("'")+1, mailmessage.lastIndexOf("'"));

		loginpage.waitABit(6000);
		webdriver.navigate().to(confirmationurl);
		loginpage.waitABit(6000);
		webdriver.get(confirmationurl);
		VNextBOConfirmPasswordWebPage confirmationpswpage = PageFactory.initElements(
				webdriver, VNextBOConfirmPasswordWebPage.class);
		loginpage = confirmationpswpage.confirmNewUserPassword(confirmpsw);
		loginpage.userLogin(usermail, confirmpsw);
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
		loginpage = forgotpswpage.sendConfirmationMail(usermail);
		
		loginpage.userLogin(usermail, confirmpsw);
		
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		leftmenu.selectUsersMenu();

		EmailUtils emailUtils = new EmailUtils(EmailHost.GMAIL, usermail,
				VNextConfigInfo.getInstance().getUserCapiUserPassword(), EmailFolder.INBOX);
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
		loginpage = forgotpswpage.sendConfirmationMail(usermail);

		EmailUtils emailUtils = new EmailUtils(EmailHost.GMAIL, usermail,
				VNextConfigInfo.getInstance().getUserCapiUserPassword(), EmailFolder.INBOX);
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
		Assert.assertEquals(confirmationpswpage.geterrorMessageValue(), "Please confirm password!");
		confirmationpswpage.setUserConfirmPasswordFieldValue("222222");
		confirmationpswpage.clickSubmitButton();
		Assert.assertEquals(confirmationpswpage.geterrorMessageValue(), "Passwords do not match!");
	}
	
	@Test(description = "Test Case 41411:vNext: Verify valiation messages on request pasword reset page")
	@Parameters({ "user.name", "user.psw" })
	public void testVerifyValidationMessagesOnRequestPasswordResetPage() {
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		VNextBOForgotPasswordWebPage forgotpswpage = loginpage.clickForgotPasswordLink();
		forgotpswpage.clickSubmitButton();
		Assert.assertEquals(forgotpswpage.geterrorMessageValue(), VNextBOErrorMessages.EMAIL_IS_INVALID);
		forgotpswpage.setConfirmationMailFieldValue(confirmpsw);
		forgotpswpage.clickSubmitButton();
		Assert.assertEquals(forgotpswpage.geterrorMessageValue(), VNextBOErrorMessages.EMAIL_IS_INVALID);
	}
	
	@Test(description = "Test Case 41408:vNext:Verify 'Forgot password' page is opened on click 'Forgot password' link")
	@Parameters({ "user.name", "user.psw" })
	public void testVerifyForgotPasswordPageIsOpenedOnClickForgotPasswordLink() {
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		VNextBOForgotPasswordWebPage forgotpswpage = loginpage.clickForgotPasswordLink();
		Assert.assertTrue(forgotpswpage.isConfirmationMailFieldDisplayed());
		forgotpswpage.clickSubmitButton();
		Assert.assertEquals(forgotpswpage.geterrorMessageValue(), VNextBOErrorMessages.EMAIL_IS_INVALID);
		
	}

	@Test(description = "Test Case 43165:vNext: Edit not confirmed user with Web access")
	public void testEditNotConfirmedUserWithWebAccess() {
		
		final String firstname = "TestTech";
		final String useredited = " edited";
		final String lastname = "QA";
		final String usermailprefix = "test.cyberiansoft+";
		final String usermailpostbox = "@gmail.com";
		final String userphone = "12345";
		final String usernewphone = "3456789";
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextBOConfigInfo.getInstance().getVNextBOMail(), VNextBOConfigInfo.getInstance().getVNextBOPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOUsersWebPage userswabpage = leftmenu.selectUsersMenu();
		usermail = usermailprefix + UUID.randomUUID() + usermailpostbox;
		VNexBOAddNewUserDialog adduserdialog = userswabpage.clickAddUserButton();
		adduserdialog.createNewUser(firstname, lastname, usermail, userphone, true);
		Assert.assertTrue(userswabpage.findUserInTableByUserEmail(usermail));
		userslist.add(usermail);
		Assert.assertTrue(userswabpage.isRedWarningTrianglePresentForUser(usermail));
		adduserdialog = userswabpage.clickEditButtonForUser(usermail);
		Assert.assertTrue(adduserdialog.isEmailFieldDisabled());
		adduserdialog.setUserFirstName(firstname + useredited);
		adduserdialog.setUserLastName(lastname + useredited);
		adduserdialog.clickSaveButtonAndWait();
		
		Assert.assertTrue(userswabpage.findUserInTableByUserEmail(usermail));
		Assert.assertEquals(userswabpage.getUserName(usermail), firstname + useredited + " " + lastname + useredited);
		adduserdialog = userswabpage.clickEditButtonForUser(usermail);
		adduserdialog.setUserPhone(usernewphone);
		final String userphonecountrycode = adduserdialog.getSelectedUserPhoneCountryCode();
		adduserdialog.unselectWebAccessCheckbox();
		adduserdialog.clickSaveButtonAndWait();
		Assert.assertTrue(userswabpage.findUserInTableByUserEmail(usermail));
		Assert.assertEquals(userswabpage.getUserPhone(usermail), userphonecountrycode + usernewphone);
		Assert.assertFalse(userswabpage.isRedWarningTrianglePresentForUser(usermail));		
		Assert.assertEquals(userswabpage.getUserName(usermail), firstname + useredited + " " + lastname + useredited);
		
		VNextBOHeaderPanel headerpanel = PageFactory.initElements(webdriver,
				VNextBOHeaderPanel.class);
		headerpanel.userLogout();
	}
	
	@Test(description = "Test Case 43371:vNext: Edit user without Web access")
	public void testEditUserWithoutWebAccess() {
		
		final String firstname = "TestTech";
		final String useredited = " edited";
		final String lastname = "QA";
		final String usermailprefix = "test.cyberiansoft+";
		final String usermailpostbox = "@gmail.com";
		final String userphone = "12345";
		final String usernewphone = "3456789";
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextBOConfigInfo.getInstance().getVNextBOMail(), VNextBOConfigInfo.getInstance().getVNextBOPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOUsersWebPage userswabpage = leftmenu.selectUsersMenu();
		usermail = usermailprefix + UUID.randomUUID() + usermailpostbox;
		VNexBOAddNewUserDialog adduserdialog = userswabpage.clickAddUserButton();
		adduserdialog.createNewUser(firstname, lastname, usermail, userphone, false);
		Assert.assertTrue(userswabpage.findUserInTableByUserEmail(usermail));
		userslist.add(usermail);
		Assert.assertFalse(userswabpage.isRedWarningTrianglePresentForUser(usermail));
		adduserdialog = userswabpage.clickEditButtonForUser(usermail);
		Assert.assertTrue(adduserdialog.isEmailFieldDisabled());
		adduserdialog.setUserFirstName(firstname + useredited);
		adduserdialog.setUserLastName(lastname + useredited);
		adduserdialog.clickSaveButtonAndWait();
		
		Assert.assertTrue(userswabpage.findUserInTableByUserEmail(usermail));
		Assert.assertEquals(userswabpage.getUserName(usermail), firstname + useredited + " " + lastname + useredited);
		adduserdialog = userswabpage.clickEditButtonForUser(usermail);
		adduserdialog.setUserPhone(usernewphone);
		final String userphonecountrycode = adduserdialog.getSelectedUserPhoneCountryCode();
		adduserdialog.clickSaveButtonAndWait();
		Assert.assertTrue(userswabpage.findUserInTableByUserEmail(usermail));
		Assert.assertEquals(userswabpage.getUserPhone(usermail), userphonecountrycode + usernewphone);
		Assert.assertFalse(userswabpage.isRedWarningTrianglePresentForUser(usermail));		
		Assert.assertEquals(userswabpage.getUserName(usermail), firstname + useredited + " " + lastname + useredited);
		
		VNextBOHeaderPanel headerpanel = PageFactory.initElements(webdriver,
				VNextBOHeaderPanel.class);
		headerpanel.userLogout();
	}
	
	@Test(description = "Test Case 43372:vNext: check posibility to resend registration email")
	public void testCheckPosibilityToResendRegistrationEmail() throws Exception {
		
		final String firstname = "TestTech";
		final String lastname = "QA";
		final String usermailprefix = "test.cyberiansoft+";
		final String usermailpostbox = "@gmail.com";
		final String userphone = "12345";
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextBOConfigInfo.getInstance().getVNextBOMail(), VNextBOConfigInfo.getInstance().getVNextBOPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOUsersWebPage userswabpage = leftmenu.selectUsersMenu();
		usermail = usermailprefix + UUID.randomUUID() + usermailpostbox;
		VNexBOAddNewUserDialog adduserdialog = userswabpage.clickAddUserButton();
		adduserdialog.createNewUser(firstname, lastname, usermail, userphone, true);
		Assert.assertTrue(userswabpage.findUserInTableByUserEmail(usermail));
		userslist.add(usermail);
		Assert.assertTrue(userswabpage.isRedWarningTrianglePresentForUser(usermail));
		

		EmailUtils emailUtils = new EmailUtils(EmailHost.GMAIL, usermail,
				VNextConfigInfo.getInstance().getUserCapiUserPassword(), EmailFolder.INBOX);
		EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
				.withSubject("ReconPro vNext Dev: REGISTRATION")
				.unreadOnlyMessages(true).maxMessagesToSearch(5);
		String mailmessage = emailUtils.waitForMessageWithSubjectInFolderAndGetMailMessage(mailSearchParameters);

		Assert.assertTrue(mailmessage.length() > 3);
		userswabpage.clickUserResendButtonAndDisagree(usermail);

		emailUtils = new EmailUtils(EmailHost.GMAIL, usermail,
				VNextConfigInfo.getInstance().getUserCapiUserPassword(), EmailFolder.INBOX);
		mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
				.withSubject("ReconPro vNext Dev: REGISTRATION")
				.unreadOnlyMessages(true).maxMessagesToSearch(5);
		mailmessage = emailUtils.waitForMessageWithSubjectInFolderAndGetMailMessage(mailSearchParameters);

		Assert.assertFalse(mailmessage.length() > 3);
		userswabpage.clickUserResendButtonAndAgree(usermail);

		emailUtils = new EmailUtils(EmailHost.GMAIL, usermail,
				VNextConfigInfo.getInstance().getUserCapiUserPassword(), EmailFolder.INBOX);
		mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
				.withSubject("ReconPro vNext Dev: REGISTRATION")
				.unreadOnlyMessages(true).maxMessagesToSearch(5);
		mailmessage = emailUtils.waitForMessageWithSubjectInFolderAndGetMailMessage(mailSearchParameters);

		Assert.assertTrue(mailmessage.length() > 3);
	}
	
	@Test(description = "Test Case 43376:vNext: check validation errors on the Create new user dialog")
	public void testCheckValidationErrorsOnTheCreateNewUserDialog() {
		
		final String firstname = "TestTech";
		final String lastname = "QA";
		final String usermailprefix = "test.cyberiansoft+";
		final String usermailpostbox = "@gmail.com";
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextBOConfigInfo.getInstance().getVNextBOMail(), VNextBOConfigInfo.getInstance().getVNextBOPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOUsersWebPage userswabpage = leftmenu.selectUsersMenu();
		usermail = usermailprefix + UUID.randomUUID() + usermailpostbox;
		VNexBOAddNewUserDialog adduserdialog = userswabpage.clickAddUserButton();
		adduserdialog.clickSaveButton();
		Assert.assertTrue(adduserdialog.isErrorMessageShown(VNextBOErrorMessages.FIRST_NAME_IS_REQUIRED));
		Assert.assertTrue(adduserdialog.isErrorMessageShown(VNextBOErrorMessages.LAST_NAME_IS_REQUIRED));
		Assert.assertTrue(adduserdialog.isErrorMessageShown(VNextBOErrorMessages.EMAIL_IS_REQUIRED));
		
		adduserdialog.setUserFirstName(firstname);
		adduserdialog.clickSaveButton();
		Assert.assertFalse(adduserdialog.isErrorMessageShown(VNextBOErrorMessages.FIRST_NAME_IS_REQUIRED));
		Assert.assertTrue(adduserdialog.isErrorMessageShown(VNextBOErrorMessages.LAST_NAME_IS_REQUIRED));
		Assert.assertTrue(adduserdialog.isErrorMessageShown(VNextBOErrorMessages.EMAIL_IS_REQUIRED));
		
		adduserdialog.setUserLastName(lastname);
		adduserdialog.clickSaveButton();
		Assert.assertFalse(adduserdialog.isErrorMessageShown(VNextBOErrorMessages.FIRST_NAME_IS_REQUIRED));
		Assert.assertFalse(adduserdialog.isErrorMessageShown(VNextBOErrorMessages.LAST_NAME_IS_REQUIRED));
		Assert.assertTrue(adduserdialog.isErrorMessageShown(VNextBOErrorMessages.EMAIL_IS_REQUIRED));
		
		adduserdialog.setUserEmail(usermail);
		adduserdialog.clickSaveButtonAndWait();
		Assert.assertTrue(userswabpage.findUserInTableByUserEmail(usermail));
		userslist.add(usermail);
	}
	
	@Test(description = "Test Case 43377:vNext: check validation errors on the set password page")
	public void testCheckValidationErrorsOnTheSetPasswordPage() throws Exception {
		
		final String firstname = "TestTech";
		final String lastname = "QA";
		final String usermailprefix = "test.cyberiansoft+";
		final String usermailpostbox = "@gmail.com";
		final String userphone = "12345";
		final String shortpsw = "11";
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextBOConfigInfo.getInstance().getVNextBOMail(), VNextBOConfigInfo.getInstance().getVNextBOPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOUsersWebPage userswabpage = leftmenu.selectUsersMenu();
		usermail = usermailprefix + UUID.randomUUID() + usermailpostbox;
		VNexBOAddNewUserDialog adduserdialog = userswabpage.clickAddUserButton();
		adduserdialog.createNewUser(firstname, lastname, usermail, userphone, true);
		Assert.assertTrue(userswabpage.findUserInTableByUserEmail(usermail));
		userslist.add(usermail);
		Assert.assertTrue(userswabpage.isRedWarningTrianglePresentForUser(usermail));

		EmailUtils emailUtils = new EmailUtils(EmailHost.GMAIL, usermail,
				VNextConfigInfo.getInstance().getUserCapiUserPassword(), EmailFolder.INBOX);
		EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
				.withSubject("ReconPro vNext Dev: REGISTRATION")
				.unreadOnlyMessages(true).maxMessagesToSearch(5);
		String mailmessage = emailUtils.waitForMessageWithSubjectInFolderAndGetMailMessage(mailSearchParameters);
		String confirmationurl = mailmessage.substring(mailmessage.indexOf("'") + 1, mailmessage.lastIndexOf("'"));
		
		VNextBOHeaderPanel headerpanel = PageFactory.initElements(webdriver,
				VNextBOHeaderPanel.class);
		headerpanel.userLogout();
		headerpanel.waitABit(2000);
		webdriver.navigate().to(confirmationurl);
		headerpanel.waitABit(4000);
		webdriver.get(confirmationurl);
		VNextBOConfirmPasswordWebPage confirmationpswpage = PageFactory.initElements(
				webdriver, VNextBOConfirmPasswordWebPage.class);
		
		confirmationpswpage.clickSubmitButton();
		Assert.assertEquals(confirmationpswpage.geterrorMessageValue(), VNextBOErrorMessages.PLEASE_ENTER_PASSWORD);
		confirmationpswpage.setUserPasswordFieldValue(shortpsw);
		confirmationpswpage.clickSubmitButton();
		Assert.assertEquals(confirmationpswpage.geterrorMessageValue(), VNextBOErrorMessages.PASSWORD_SHOULD_BE_LONGER);
		confirmationpswpage.setUserPasswordFieldValue(confirmpsw);
		confirmationpswpage.clickSubmitButton();
		Assert.assertEquals(confirmationpswpage.geterrorMessageValue(), VNextBOErrorMessages.PLEASE_CONFIRM_PASSWORD);
		loginpage = confirmationpswpage.confirmNewUserPassword(confirmpsw);
		
		loginpage.userLogin(usermail, confirmpsw);
		userswabpage = leftmenu.selectUsersMenu();
		headerpanel.userLogout();
	}
	
	@Test(description = "Test Case 43373:vNext: create user with existing Email address")
	public void testCreateUserWithExistingEmailAddress() {
		
		final String firstname = "TestTech";
		final String lastname = "QA";
		final String usermailprefix = "test.cyberiansoft+";
		final String usermailpostbox = "@gmail.com";
		final String userphone = "12345";
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(VNextBOConfigInfo.getInstance().getVNextBOMail(), VNextBOConfigInfo.getInstance().getVNextBOPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOUsersWebPage userswabpage = leftmenu.selectUsersMenu();
		usermail = usermailprefix + UUID.randomUUID() + usermailpostbox;
		VNexBOAddNewUserDialog adduserdialog = userswabpage.clickAddUserButton();
		adduserdialog.createNewUser(firstname, lastname, usermail, userphone, true);
		Assert.assertTrue(userswabpage.findUserInTableByUserEmail(usermail));
		userslist.add(usermail);
		Assert.assertTrue(userswabpage.isRedWarningTrianglePresentForUser(usermail));
		adduserdialog = userswabpage.clickAddUserButton();
		adduserdialog.setUserFirstName(firstname);
		adduserdialog.setUserLastName(lastname);
		adduserdialog.setUserEmail(usermail);
		adduserdialog.selectWebAccessCheckbox();
		adduserdialog.clickSaveButton();
		Assert.assertFalse(adduserdialog.isErrorMessageShown(VNextBOErrorMessages.FIRST_NAME_IS_REQUIRED));
		adduserdialog.isErrorMessageShown("E-mail " + usermail + " is occupied");
		userswabpage = adduserdialog.closeadduserDialog();
	}
}
