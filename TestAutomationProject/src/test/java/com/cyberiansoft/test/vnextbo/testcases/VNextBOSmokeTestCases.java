package com.cyberiansoft.test.vnextbo.testcases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cyberiansoft.test.bo.testcases.BaseTestCase;
import com.cyberiansoft.test.ios_client.utils.MailChecker;
import com.cyberiansoft.test.vnext.utils.VNextWebServicesUtils;
import com.cyberiansoft.test.vnextbo.screens.VNextBOHeaderPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;
import com.cyberiansoft.test.vnextbo.utils.VNextBOErrorMessages;
import com.cyberiansoft.test.vnextbo.screens.VNexBOAddNewUserDialog;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNexBOUsersWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOConfirmPasswordWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOForgotPasswordWebPage;

public class VNextBOSmokeTestCases extends BaseTestCase {
	
	String userName = "";
	String userPassword = "";
	String usermail = "";
	final String confirmpsw = "111111";
	ArrayList<String> userslist = new ArrayList<String>();
	
	@BeforeMethod
	@Parameters({ "backoffice.url", "user.name", "user.psw" })
	public void BackOfficeLogin(String backofficeurl,
			String usernm, String userpsw) throws InterruptedException {
		webdriverGotoWebPage(backofficeurl);
		userName = usernm;
		userPassword = userpsw;
	}
	
	@AfterMethod
	public void BackOfficeLogout() throws IOException {
		VNextBOHeaderPanel headerpanel = PageFactory.initElements(webdriver,
				VNextBOHeaderPanel.class);
		if (headerpanel.isLogOutLinkExists())
			headerpanel.userLogout();		
	}
	
	@AfterSuite
	public void clearUsers() throws IOException {
		System.out.println("++++++++++++" + userslist.size());
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
		loginpage.userLogin(userName, userPassword);
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
	
	@Test(description = "Test Case 43038:vNext: create user without Web access")
	public void testCreateUserWithWebAccess() throws IOException {
		
		final String firstname = "TestTech";
		final String lastname = "QA";
		final String usermailprefix = "test.cyberiansoft+";
		final String usermailpostbox = "@gmail.com";
		final String userphone = "12345";
		
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(userName, userPassword);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOUsersWebPage userswabpage = leftmenu.selectUsersMenu();
		usermail = usermailprefix + UUID.randomUUID() + usermailpostbox;
		VNexBOAddNewUserDialog adduserdialog = userswabpage.clickAddUserButton();
		adduserdialog.createNewUser(firstname, lastname, usermail, userphone, true);
		Assert.assertTrue(userswabpage.findUserInTableByUserEmail(usermail));
		userslist.add(usermail);
		Assert.assertTrue(userswabpage.isRedWarningTrianglePresentForUser(usermail));
		String confirmationurl = MailChecker.getUserRegistrationURL();
		
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
	public void testVerifyUserCanChangePasswordUsingLinkFromTheEmail() throws IOException {
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		VNextBOForgotPasswordWebPage forgotpswpage = loginpage.clickForgotPasswordLink();
		loginpage = forgotpswpage.sendConfirmationMail(usermail);
		
		
		boolean search = false;
		String mailmessage = "";
		for (int i=0; i < 4; i++) {
			if (!MailChecker.searchEmail("test.cyberiansoft@gmail.com", "ZZzz11!!", "ReconPro vNext Dev: PASSWORD RESET", "ReconPro@cyberiansoft.com", "reset your password")) {
				loginpage.waitABit(60*500);
			} else {
				search = true;
				mailmessage = MailChecker.searchEmailAndGetMailMessage("test.cyberiansoft@gmail.com", "ZZzz11!!", "ReconPro vNext Dev: PASSWORD RESET", "ReconPro@cyberiansoft.com");
				break;
			}
		}
		
		String confirmationurl = "";
		if (search) {
			System.out.println("==========1" + mailmessage);
			confirmationurl = mailmessage.substring(mailmessage.indexOf("'")+1, mailmessage.lastIndexOf("'"));
			System.out.println("+++++++" + confirmationurl);
		}
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
	public void testVerifyUserCanChangePasswordPageIsNotOpenedWhenUserIsLoggedIn(String userName, String userPassword) throws IOException {
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		VNextBOForgotPasswordWebPage forgotpswpage = loginpage.clickForgotPasswordLink();
		loginpage = forgotpswpage.sendConfirmationMail(usermail);
		
		loginpage.userLogin(usermail, confirmpsw);
		
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		leftmenu.selectUsersMenu();
		
		boolean search = false;
		String mailmessage = "";
		for (int i=0; i < 4; i++) {
			if (!MailChecker.searchEmail("test.cyberiansoft@gmail.com", "ZZzz11!!", "ReconPro vNext Dev: PASSWORD RESET", "ReconPro@cyberiansoft.com", "reset your password")) {
				loginpage.waitABit(60*500);
			} else {
				search = true;
				mailmessage = MailChecker.searchEmailAndGetMailMessage("test.cyberiansoft@gmail.com", "ZZzz11!!", "ReconPro vNext Dev: PASSWORD RESET", "ReconPro@cyberiansoft.com");
				break;
			}
		}
		
		String confirmationurl = "";
		if (search) {
			System.out.println("==========2" + mailmessage);
			confirmationurl = mailmessage.substring(mailmessage.indexOf("'")+1, mailmessage.lastIndexOf("'"));
			System.out.println("+++++++" + confirmationurl);
		}
		
		
		System.out.println("++++++++++++" + usermail);
		System.out.println("++++++++++++" + confirmpsw);
		System.out.println("++++++++++++" + confirmationurl);
		//webdriver.navigate().to(confirmationurl);
		//leftmenu.waitABit(4000);
		webdriver.get(confirmationurl);
		leftmenu.selectInvoicesMenu();
		VNextBOHeaderPanel headerpanel = PageFactory.initElements(webdriver,
				VNextBOHeaderPanel.class);
		headerpanel.userLogout();
	}
	
	@Test(description = "Test Case 41412:vNext: Verify validation messages on change password page")
	@Parameters({ "user.name", "user.psw" })
	public void testVerifyValidationMessagesOnChangePasswordPage(String userName, String userPassword) throws IOException {
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		VNextBOForgotPasswordWebPage forgotpswpage = loginpage.clickForgotPasswordLink();
		loginpage = forgotpswpage.sendConfirmationMail(usermail);
		
		
		boolean search = false;
		String mailmessage = "";
		for (int i=0; i < 7; i++) {
			if (!MailChecker.searchEmail("test.cyberiansoft@gmail.com", "ZZzz11!!", "ReconPro vNext Dev: PASSWORD RESET", "ReconPro@cyberiansoft.com", "reset your password")) {
				loginpage.waitABit(60*1000);
			} else {
				search = true;
				mailmessage = MailChecker.searchEmailAndGetMailMessage("test.cyberiansoft@gmail.com", "ZZzz11!!", "ReconPro vNext Dev: PASSWORD RESET", "ReconPro@cyberiansoft.com");
				break;
			}
		}
		
		String confirmationurl = "";
		if (search) {
			System.out.println("==========3" + mailmessage);
			confirmationurl = mailmessage.substring(mailmessage.indexOf("'")+1, mailmessage.lastIndexOf("'"));
			System.out.println("+++++++" + confirmationurl);
		}
		
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
	public void testVerifyValidationMessagesOnRequestPasswordResetPage(String userName, String userPassword) throws IOException {
		
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
	public void testVerifyForgotPasswordPageIsOpenedOnClickForgotPasswordLink(String userName, String userPassword) throws IOException {
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		VNextBOForgotPasswordWebPage forgotpswpage = loginpage.clickForgotPasswordLink();
		Assert.assertTrue(forgotpswpage.isConfirmationMailFieldDisplayed());
		forgotpswpage.clickSubmitButton();
		Assert.assertEquals(forgotpswpage.geterrorMessageValue(), VNextBOErrorMessages.EMAIL_IS_INVALID);
		
	}

	@Test(description = "Test Case 43165:vNext: Edit not confirmed user with Web access")
	public void testEditNotConfirmedUserWithWebAccess() throws IOException {
		
		final String firstname = "TestTech";
		final String useredited = " edited";
		final String lastname = "QA";
		final String usermailprefix = "test.cyberiansoft+";
		final String usermailpostbox = "@gmail.com";
		final String userphone = "12345";
		final String usernewphone = "3456789";
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(userName, userPassword);
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
	
	@Test(description = "Test Case 43165:vNext: Edit user without Web access")
	public void testEditUserWithoutWebAccess() throws IOException {
		
		final String firstname = "TestTech";
		final String useredited = " edited";
		final String lastname = "QA";
		final String usermailprefix = "test.cyberiansoft+";
		final String usermailpostbox = "@gmail.com";
		final String userphone = "12345";
		final String usernewphone = "3456789";
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(userName, userPassword);
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
	public void testCheckPosibilityToResendRegistrationEmail() throws IOException {
		
		final String firstname = "TestTech";
		final String lastname = "QA";
		final String usermailprefix = "test.cyberiansoft+";
		final String usermailpostbox = "@gmail.com";
		final String userphone = "12345";
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(userName, userPassword);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOUsersWebPage userswabpage = leftmenu.selectUsersMenu();
		usermail = usermailprefix + UUID.randomUUID() + usermailpostbox;
		VNexBOAddNewUserDialog adduserdialog = userswabpage.clickAddUserButton();
		adduserdialog.createNewUser(firstname, lastname, usermail, userphone, true);
		Assert.assertTrue(userswabpage.findUserInTableByUserEmail(usermail));
		userslist.add(usermail);
		Assert.assertTrue(userswabpage.isRedWarningTrianglePresentForUser(usermail));
		
		boolean search = false;
		String mailmessage = MailChecker.getUserMailContent();
		if (mailmessage.length() > 3)
			search = true;

		Assert.assertTrue(search);
		userswabpage.clickUserResendButtonAndDisagree(usermail);
		
		search = false;
		mailmessage = MailChecker.getUserMailContent();
		if (mailmessage.length() > 3)
			search = true;
		
		Assert.assertFalse(search);
		userswabpage.clickUserResendButtonAndAgree(usermail);
		
		search = false;
		mailmessage = MailChecker.getUserMailContent();
		if (mailmessage.length() > 3)
			search = true;
		Assert.assertTrue(search);
	}
	
	@Test(description = "Test Case 43376:vNext: check validation errors on the Create new user dialog")
	public void testCheckValidationErrorsOnTheCreateNewUserDialog() throws IOException {
		
		final String firstname = "TestTech";
		final String lastname = "QA";
		final String usermailprefix = "test.cyberiansoft+";
		final String usermailpostbox = "@gmail.com";
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(userName, userPassword);
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
	public void testCheckValidationErrorsOnTheSetPasswordPage() throws IOException {
		
		final String firstname = "TestTech";
		final String lastname = "QA";
		final String usermailprefix = "test.cyberiansoft+";
		final String usermailpostbox = "@gmail.com";
		final String userphone = "12345";
		final String shortpsw = "11";
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(userName, userPassword);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOUsersWebPage userswabpage = leftmenu.selectUsersMenu();
		usermail = usermailprefix + UUID.randomUUID() + usermailpostbox;
		VNexBOAddNewUserDialog adduserdialog = userswabpage.clickAddUserButton();
		adduserdialog.createNewUser(firstname, lastname, usermail, userphone, true);
		Assert.assertTrue(userswabpage.findUserInTableByUserEmail(usermail));
		userslist.add(usermail);
		Assert.assertTrue(userswabpage.isRedWarningTrianglePresentForUser(usermail));
		String confirmationurl = MailChecker.getUserRegistrationURL();
		
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
	public void testCreateUserWithExistingEmailAddress() throws IOException {
		
		final String firstname = "TestTech";
		final String lastname = "QA";
		final String usermailprefix = "test.cyberiansoft+";
		final String usermailpostbox = "@gmail.com";
		final String userphone = "12345";
		
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(userName, userPassword);
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
