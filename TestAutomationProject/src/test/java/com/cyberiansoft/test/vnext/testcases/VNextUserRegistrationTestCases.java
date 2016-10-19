package com.cyberiansoft.test.vnext.testcases;

import java.io.IOException;
import java.util.UUID;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cyberiansoft.test.ios_client.utils.MailChecker;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextLoginScreen;
import com.cyberiansoft.test.vnext.screens.VNextRegistrationLineOfBusinessScreen;
import com.cyberiansoft.test.vnext.screens.VNextRegistrationNewUserPersonalInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextRegistrationOverviewLegalInfosScreen;
import com.cyberiansoft.test.vnext.screens.VNextRegistrationOverviewScreen;
import com.cyberiansoft.test.vnext.screens.VNextRegistrationPaymentInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextRegistrationPersonalInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextVerificationScreen;
import com.cyberiansoft.test.vnext.utils.AppContexts;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.utils.VNextWebServicesUtils;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOConfirmPasswordWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;

public class VNextUserRegistrationTestCases extends VNextBaseTestCase {
	
	private String usercapimail = "";
	private String usercapimailpsw = "";
	private String fromEmail = "ReconPro@cyberiansoft.com";
	private String bodySearchText = "Dear Valued Customer,";
	
	final String usermailprefix = "test.cyberiansoft+";
	final String usermailpostbox = "@gmail.com";
	String userregmail = "";
	final String confirmpsw = "111111";
	
	String userregfirstname = "TestTech";
	String userreglastname = "AQA";
	
	
	@BeforeClass(description = "Setting up new suite")
	@Parameters({ "backofficecapi.url", "usercapi.mail", "usercapi.name", "usercapi.psw", "selenium.browser"})	
	public void settingUp(String backofficeurl, String usermail, String userName, String userPassword, String defbrowser) throws IOException {
		usercapimail = usermail;
		usercapimailpsw = userPassword;
		
		final String usermailprefix = "test.cyberiansoft+";
		final String usermailpostbox = "@gmail.com";
		
		/*defaultbrowser = defbrowser;
		initiateWebDriver();
		webdriverGotoWebPage(backofficeurl);
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(userName, userPassword);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNexBOUsersWebPage userswabpage = leftmenu.selectUsersMenu();
		userregmail = usermailprefix + UUID.randomUUID() + usermailpostbox;
		VNexBOAddNewUserDialog adduserdialog = userswabpage.clickAddUserButton();
		adduserdialog.createNewUser(userregfirstname, userreglastname, userregmail, userregphone, true);
		Assert.assertTrue(userswabpage.findUserInTableByUserEmail(userregmail));
		Assert.assertTrue(userswabpage.isRedWarningTrianglePresentForUser(userregmail));
		
		boolean search = false;
		String mailmessage = "";
		for (int i=0; i < 7; i++) {
			if (!MailChecker.searchEmail("test.cyberiansoft@gmail.com", "ZZzz11!!", "ReconPro vNext Dev: REGISTRATION", "ReconPro@cyberiansoft.com", "Please click link below to complete the registration process.")) {
				userswabpage.waitABit(60*1000);
			} else {
				mailmessage = MailChecker.searchEmailAndGetMailMessage("test.cyberiansoft@gmail.com", "ZZzz11!!", "ReconPro vNext Dev: REGISTRATION", "ReconPro@cyberiansoft.com");
				if (mailmessage.length() > 3) {
					search = true;
					break;
				}				
			}
		}
		
		String confirmationurl = "";
		if (search) {
			System.out.println("==========0" + mailmessage);
			confirmationurl = "http://" + mailmessage.substring(mailmessage.indexOf("'")+1, mailmessage.lastIndexOf("'"));
			
		}
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

		loginpage.userLogin(userregmail, confirmpsw);
		userswabpage = leftmenu.selectUsersMenu();
		headerpanel.userLogout();
		*/
		
		
		setUp();
		setNetworkOn();
		
	}
	
	@AfterClass(description = "Setting up new suite")
	public void tearDown() throws Exception {
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		//appiumdriver.resetApp();	
	}
	
	@BeforeMethod(description = "Setting up new suite")
	public void resetApk() {
		resetApp();
		setUp();
	}
	
	@Test(testName= "Test Case 44318:vNext: verify creating BO with JumpStart Edition (PDR)", 
			description = "verify creating BO with JumpStart Edition (PDR)")
	@Parameters({ "backoffice.url", "user.name", "user.psw", "device.license" })	
	public void testVerifyCreatingBOWithJumpStartEdition_PDR(String deviceofficeurl, String deviceuser, String devicepsw, String licensename) throws IOException {
		
		final String boeditionname = "JumpStart";
		final String bolineofbusiness = "PDR";
		String userregphone = "111116789";
		
		userregmail = usermailprefix + UUID.randomUUID() + usermailpostbox;
		appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		VNextRegistrationPersonalInfoScreen regscreen = new VNextRegistrationPersonalInfoScreen(appiumdriver);
		regscreen.setUserRegistrationInfo("QA", "QA" , "380", userregphone, userregmail);
		appiumdriver.switchTo().defaultContent();
		regscreen.waitABit(5000);
		appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		VNextRegistrationNewUserPersonalInfoScreen newuserpersonalinfoscreen =  new VNextRegistrationNewUserPersonalInfoScreen(appiumdriver);
		newuserpersonalinfoscreen.setNewUserPersonaInfo(boeditionname, boeditionname, boeditionname);
		newuserpersonalinfoscreen.clickDoneButton();
		VNextRegistrationLineOfBusinessScreen reglineofbusinessscreen = new VNextRegistrationLineOfBusinessScreen(appiumdriver);
		reglineofbusinessscreen.selectEdition(boeditionname);
		reglineofbusinessscreen.selectLineOfBusiness(bolineofbusiness);
		reglineofbusinessscreen.clickDoneButton();
		VNextRegistrationOverviewScreen registrationoverviewscreen = new VNextRegistrationOverviewScreen(appiumdriver);
		Assert.assertEquals(registrationoverviewscreen.getUserFirstNameValue(), boeditionname);
		Assert.assertEquals(registrationoverviewscreen.getUserLastNameValue(), boeditionname);
		Assert.assertEquals(registrationoverviewscreen.getUserCompanyNameValue(), boeditionname);
		Assert.assertEquals(registrationoverviewscreen.getUserEmailValue(), userregmail);
		Assert.assertEquals(registrationoverviewscreen.getUserPhoneValue(), userregphone);
		registrationoverviewscreen.clickDoneButton();
		VNextRegistrationOverviewLegalInfosScreen registrationoverviewlegalinfoscreen = 
				new VNextRegistrationOverviewLegalInfosScreen(appiumdriver);
		registrationoverviewlegalinfoscreen.agreetermsAndconditions();
		registrationoverviewlegalinfoscreen.clickSubmitButton();
		VNextVerificationScreen verificationscreen = new VNextVerificationScreen(appiumdriver);
		String regcode = VNextWebServicesUtils.getDeviceRegistrationCode(userregmail).replaceAll("\"", "");
		verificationscreen.setDeviceRegistrationCode(regcode);
		verificationscreen.clickVerifyButton();
		verificationscreen.waitABit(10000);
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		Assert.assertEquals(informationdlg.clickInformationDialogOKButtonAndGetMessage(), VNextAlertMessages.ALL_DATABASES_ARE_DOWNLOADED_SECCESSFULY);
		//VNextLoginScreen loginscreen = new VNextLoginScreen(appiumdriver);
			
		String mailmessage = MailChecker.getMailMessage(usercapimail, usercapimailpsw, "380" + userregphone + ": REGISTRATION", fromEmail, bodySearchText);
		
		String newbourl = "";
		if (!mailmessage.equals("")) {
			System.out.println("==========0" + mailmessage);
			newbourl = "http://" + mailmessage.substring(mailmessage.indexOf("'")+1, mailmessage.lastIndexOf("'"));		
		} else {
			Assert.assertTrue(false, "Mail message is empty");
		}
		initiateWebDriver();
		webdriverGotoWebPage(newbourl);
		VNextBOConfirmPasswordWebPage confirmationpswpage = PageFactory.initElements(
				webdriver, VNextBOConfirmPasswordWebPage.class);
		VNextBOLoginScreenWebPage loginpage = confirmationpswpage.confirmNewUserPassword(confirmpsw);

		loginpage.userLogin(userregmail, confirmpsw);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		Assert.assertFalse(leftmenu.isUsersMenuItemExists());
		
	}
	
	@Test(testName= "Test Case 44329:vNext: verify creating BO with Technician Edition (PDR)", 
			description = "verify creating BO with Technician Edition (PDR)")
	@Parameters({ "backoffice.url", "user.name", "user.psw", "device.license" })	
	public void testVerifyCreatingBOWithTechnicianEdition_PDR(String deviceofficeurl, String deviceuser, String devicepsw, String licensename) throws IOException {
		
		final String newuserfirstname = "TestTech";
		final String newuserlastname = "User";
		final String newusercompanyname = "PDR";
		final String newuseraddress1 = "Address1";
		final String newuseraddress2 = "Address2";
		final String newusercity = "Lviv";
		final String newuserzip = "79000";
		final String newusercountry = "Canada";
		final String newuserstate = "Alberta";
		final String userpaymentname = newuserfirstname + " " + newuserlastname;
		final String  usercardnumber = "4242424242424242";
		final String  securitycode = "123";
		final String  expmonth = "11";
		final String  expyear = "2019";
		
		final String boeditionname = "Technician";
		final String bolineofbusiness = "PDR";
		String userregphone = "111111789";
		
		userregmail = usermailprefix + UUID.randomUUID() + usermailpostbox;
		appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		VNextRegistrationPersonalInfoScreen regscreen = new VNextRegistrationPersonalInfoScreen(appiumdriver);
		regscreen.setUserRegistrationInfo("QA", "QA", "380", userregphone, userregmail);
		appiumdriver.switchTo().defaultContent();
		regscreen.waitABit(5000);
		appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		VNextRegistrationNewUserPersonalInfoScreen newuserpersonalinfoscreen =  new VNextRegistrationNewUserPersonalInfoScreen(appiumdriver);
		newuserpersonalinfoscreen.setNewUserPersonaInfo(newuserfirstname, newuserlastname, newusercompanyname,
				newuseraddress1, newuseraddress2, newusercity, newuserzip, newusercountry, newuserstate);
		newuserpersonalinfoscreen.clickDoneButton();
		VNextRegistrationLineOfBusinessScreen reglineofbusinessscreen = new VNextRegistrationLineOfBusinessScreen(appiumdriver);
		reglineofbusinessscreen.selectEdition(boeditionname);
		reglineofbusinessscreen.selectLineOfBusiness(bolineofbusiness);
		reglineofbusinessscreen.clickDoneButton();
		appiumdriver.switchTo().defaultContent();
		regscreen.waitABit(2000);
		appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		VNextRegistrationPaymentInfoScreen registrationpaymentinfoscreen = new VNextRegistrationPaymentInfoScreen(appiumdriver);
		registrationpaymentinfoscreen.setUserPaiymentInfo(userpaymentname, usercardnumber,
				expmonth, expyear, securitycode);
		registrationpaymentinfoscreen.clickUseRegistrationAddress();
		
		Assert.assertEquals(registrationpaymentinfoscreen.getUserAddress1Value(), newuseraddress1);
		Assert.assertEquals(registrationpaymentinfoscreen.getUserAddress2Value(), newuseraddress2);
		Assert.assertEquals(registrationpaymentinfoscreen.getUserCityValue(), newusercity);
		Assert.assertEquals(registrationpaymentinfoscreen.getUserZipValue(), newuserzip);
		Assert.assertEquals(registrationpaymentinfoscreen.getUserCountryValue(), newusercountry);
		Assert.assertEquals(registrationpaymentinfoscreen.getUserStateValue(), newuserstate);
		registrationpaymentinfoscreen.clickDoneButton();
		VNextRegistrationOverviewScreen registrationoverviewscreen = new VNextRegistrationOverviewScreen(appiumdriver);
		Assert.assertEquals(registrationoverviewscreen.getUserFirstNameValue(), newuserfirstname);
		Assert.assertEquals(registrationoverviewscreen.getUserLastNameValue(), newuserlastname);
		Assert.assertEquals(registrationoverviewscreen.getUserCompanyNameValue(), newusercompanyname);
		Assert.assertEquals(registrationoverviewscreen.getUserEmailValue(), userregmail);
		Assert.assertEquals(registrationoverviewscreen.getUserPhoneValue(), userregphone);
		registrationoverviewscreen.clickDoneButton();
		registrationoverviewscreen.waitABit(3000);
		VNextRegistrationOverviewLegalInfosScreen registrationoverviewlegalinfoscreen = 
				new VNextRegistrationOverviewLegalInfosScreen(appiumdriver);
		registrationoverviewlegalinfoscreen.agreetermsAndconditions();
		registrationoverviewlegalinfoscreen.agreePaymentTerms();
		Assert.assertEquals(registrationoverviewlegalinfoscreen.getPaymentPriceValue(), "$ 25.00");
		registrationoverviewlegalinfoscreen.clickPayNowButton();
		VNextVerificationScreen verificationscreen = new VNextVerificationScreen(appiumdriver);
		String regcode = VNextWebServicesUtils.getDeviceRegistrationCode(userregmail).replaceAll("\"", "");
		verificationscreen.setDeviceRegistrationCode(regcode);
		verificationscreen.clickVerifyButton();
		verificationscreen.waitABit(10000);
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		Assert.assertEquals(informationdlg.clickInformationDialogOKButtonAndGetMessage(), VNextAlertMessages.ALL_DATABASES_ARE_DOWNLOADED_SECCESSFULY);
		//VNextLoginScreen loginscreen = new VNextLoginScreen(appiumdriver);
			
		String mailmessage = MailChecker.getMailMessage(usercapimail, usercapimailpsw, "380" + userregphone + ": REGISTRATION", fromEmail, bodySearchText);
		
		String newbourl = "";
		if (!mailmessage.equals("")) {
			System.out.println("==========0" + mailmessage);
			newbourl = "http://" + mailmessage.substring(mailmessage.indexOf("'")+1, mailmessage.lastIndexOf("'"));		
		} else {
			Assert.assertTrue(false, "Mail message is empty");
		}
		initiateWebDriver();
		webdriverGotoWebPage(newbourl);
		VNextBOConfirmPasswordWebPage confirmationpswpage = PageFactory.initElements(
				webdriver, VNextBOConfirmPasswordWebPage.class);
		VNextBOLoginScreenWebPage loginpage = confirmationpswpage.confirmNewUserPassword(confirmpsw);

		loginpage.userLogin(userregmail, confirmpsw);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		Assert.assertTrue(leftmenu.isUsersMenuItemExists());
	}
}
