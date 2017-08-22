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
import com.cyberiansoft.test.vnext.config.VNextConfigInfo;
import com.cyberiansoft.test.vnext.screens.VNextEmailMismatchDialog;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextLoginScreen;
import com.cyberiansoft.test.vnext.screens.VNextPhoneMismatchDialog;
import com.cyberiansoft.test.vnext.screens.VNextRegistrationLineOfBusinessScreen;
import com.cyberiansoft.test.vnext.screens.VNextRegistrationNewUserPersonalInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextRegistrationOverviewLegalInfosScreen;
import com.cyberiansoft.test.vnext.screens.VNextRegistrationOverviewScreen;
import com.cyberiansoft.test.vnext.screens.VNextRegistrationPaymentInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextRegistrationPersonalInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextRegistrationScreensModalDialog;
import com.cyberiansoft.test.vnext.screens.VNextVerificationScreen;
import com.cyberiansoft.test.vnext.utils.AppContexts;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.utils.VNextWebServicesUtils;
import com.cyberiansoft.test.vnextbo.screens.VNexBOAddNewUserDialog;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNexBOUsersWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOApproveAccountWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOConfirmPasswordWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOHeaderPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;

public class VNextUserRegistrationTestCases extends VNextBaseTestCase {
	
	private String fromEmail = "ReconPro@cyberiansoft.com";
	private String bodySearchText = "Dear ";
	
	final String usermailprefix = "test.cyberiansoft+";
	final String usermailpostbox = "@gmail.com";
	String userregmail = "";
	final String confirmpsw = "111111";
	
	final String userregphone = "6267477803";
	final String  userregphoneformatted = "(626) 747-7803";
	final String userphonecountrycode = "1";
	
	@BeforeClass(description = "Setting up new suite")
	public void settingUp() throws IOException {		
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
			description = "Verify creating BO with JumpStart Edition (PDR)")
	public void testVerifyCreatingBOWithJumpStartEdition_PDR() throws IOException {
		
		final String userfirstname = "QA";
		final String userlastname = "QA";
		final String boeditionname = "Repair360 Free";
		final String bolineofbusiness = "PDR";
		final String userstate = "California";
		
		//userregmail = usermailprefix + UUID.randomUUID() + usermailpostbox;
		userregmail = usermailprefix + "99999111" + usermailpostbox;
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		VNextRegistrationPersonalInfoScreen regscreen = new VNextRegistrationPersonalInfoScreen(appiumdriver);
		regscreen.setUserRegistrationInfo(userfirstname, userlastname , userphonecountrycode, userregphone, userregmail);
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		regscreen.clickClearUserButton();
		VNextRegistrationScreensModalDialog registrationinformationdlg = new VNextRegistrationScreensModalDialog(appiumdriver);		
		Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "User " + userregmail + " has been deleted");
		
		regscreen.clickDoneButton();
		VNextVerificationScreen verificationscreen = new VNextVerificationScreen(appiumdriver);
		verificationscreen.setDeviceRegistrationCode(VNextWebServicesUtils.getVerificationCodeByPhone(userphonecountrycode + userregphone).replaceAll("\"", ""));
		verificationscreen.clickVerifyButton();
		registrationinformationdlg = new VNextRegistrationScreensModalDialog(appiumdriver);
		Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "Your phone has been verified");
		
		waitABit(2000);
		appiumdriver.switchTo().defaultContent();
		regscreen.waitABit(5000);
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		VNextRegistrationNewUserPersonalInfoScreen newuserpersonalinfoscreen =  new VNextRegistrationNewUserPersonalInfoScreen(appiumdriver);
		newuserpersonalinfoscreen.setNewUserPersonaInfo(boeditionname, userstate);
		newuserpersonalinfoscreen.clickDoneButton();
		VNextRegistrationLineOfBusinessScreen reglineofbusinessscreen = new VNextRegistrationLineOfBusinessScreen(appiumdriver);
		reglineofbusinessscreen.selectEdition(boeditionname);
		reglineofbusinessscreen.selectLineOfBusiness(bolineofbusiness);
		reglineofbusinessscreen.clickDoneButton();
		VNextRegistrationOverviewScreen registrationoverviewscreen = new VNextRegistrationOverviewScreen(appiumdriver);
		Assert.assertEquals(registrationoverviewscreen.getUserFirstNameValue(), userfirstname);
		Assert.assertEquals(registrationoverviewscreen.getUserLastNameValue(), userlastname);
		Assert.assertEquals(registrationoverviewscreen.getUserCompanyNameValue(), boeditionname);
		Assert.assertEquals(registrationoverviewscreen.getUserEmailValue(), userregmail);
		Assert.assertEquals(registrationoverviewscreen.getUserPhoneValue(), userregphoneformatted);
		registrationoverviewscreen.clickDoneButton();
		VNextRegistrationOverviewLegalInfosScreen registrationoverviewlegalinfoscreen = 
				new VNextRegistrationOverviewLegalInfosScreen(appiumdriver);
		registrationoverviewlegalinfoscreen.agreetermsAndconditions();
		registrationoverviewlegalinfoscreen.clickSubmitButton();
		/*verificationscreen = new VNextVerificationScreen(appiumdriver);
		String regcode = VNextWebServicesUtils.getDeviceRegistrationCode(userregmail).replaceAll("\"", "");
		verificationscreen.setDeviceRegistrationCode(regcode);
		verificationscreen.clickVerifyButton();*/
		registrationoverviewlegalinfoscreen.waitABit(60*1000);			
		switchToWebViewContext();
				
		String mailmessage = MailChecker.getMailMessage(VNextConfigInfo.getInstance().getUserCapiMail(),
				VNextConfigInfo.getInstance().getUserCapiUserPassword(), "Repair360 Free: REGISTRATION", fromEmail, bodySearchText + userfirstname + " " + userlastname);
		
		String newbourl = "";
		if (!mailmessage.equals("")) {
			System.out.println("==========0" + mailmessage);
			newbourl = mailmessage.substring(mailmessage.indexOf("'")+1, mailmessage.lastIndexOf("'"));		
		} else {
			Assert.assertTrue(false, "Mail message is empty");
		}
		
		initiateWebDriver();
		webdriverGotoWebPage(newbourl);
		VNextBOApproveAccountWebPage approvedaccountwebpage = PageFactory.initElements(
				webdriver, VNextBOApproveAccountWebPage.class);
		VNextBOLoginScreenWebPage loginpage = approvedaccountwebpage.clickLoginLink();
		
		loginpage.userLogin(userregmail, confirmpsw);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		Assert.assertFalse(leftmenu.isUsersMenuItemExists());
		webdriver.quit();
		
	}
	
	@Test(testName= "Test Case 44329:vNext: verify creating BO with Technician Edition (PDR)", 
			description = "Verify creating BO with Technician Edition (PDR)")
	public void testVerifyCreatingBOWithTechnicianEdition_PDR() throws IOException {
		
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
		
		final String boeditionname = "Repair360";
		final String bolineofbusiness = "PDR";
		

		userregmail = usermailprefix + "99999111" + usermailpostbox;
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		VNextRegistrationPersonalInfoScreen regscreen = new VNextRegistrationPersonalInfoScreen(appiumdriver);
		regscreen.setUserRegistrationInfo(newuserfirstname, newuserlastname , userphonecountrycode, userregphone, userregmail);
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		regscreen.clickClearUserButton();
		VNextRegistrationScreensModalDialog registrationinformationdlg = new VNextRegistrationScreensModalDialog(appiumdriver);		
		Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "User " + userregmail + " has been deleted");
		regscreen.clickDoneButton();
		waitABit(3000);
		VNextVerificationScreen verificationscreen = new VNextVerificationScreen(appiumdriver);
		verificationscreen.setDeviceRegistrationCode(VNextWebServicesUtils.getVerificationCodeByPhone(userphonecountrycode + userregphone).replaceAll("\"", ""));
		verificationscreen.clickVerifyButton();
		registrationinformationdlg = new VNextRegistrationScreensModalDialog(appiumdriver);
		Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "Your phone has been verified");
		
		waitABit(2000);
		appiumdriver.switchTo().defaultContent();
		regscreen.waitABit(5000);
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		VNextRegistrationNewUserPersonalInfoScreen newuserpersonalinfoscreen =  new VNextRegistrationNewUserPersonalInfoScreen(appiumdriver);
		newuserpersonalinfoscreen.setNewUserPersonaInfo(newusercompanyname,
				newuseraddress1, newuseraddress2, newusercity, newuserzip, newusercountry, newuserstate);
		newuserpersonalinfoscreen.clickDoneButton();
		VNextRegistrationLineOfBusinessScreen reglineofbusinessscreen = new VNextRegistrationLineOfBusinessScreen(appiumdriver);
		reglineofbusinessscreen.selectEdition(boeditionname);
		reglineofbusinessscreen.selectLineOfBusiness(bolineofbusiness);
		reglineofbusinessscreen.clickDoneButton();
		appiumdriver.switchTo().defaultContent();
		regscreen.waitABit(2000);
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
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
		Assert.assertEquals(registrationoverviewscreen.getUserPhoneValue(), userregphoneformatted);
		registrationoverviewscreen.clickDoneButton();
		registrationoverviewscreen.waitABit(10000);
		VNextRegistrationOverviewLegalInfosScreen registrationoverviewlegalinfoscreen = 
				new VNextRegistrationOverviewLegalInfosScreen(appiumdriver);
		registrationoverviewlegalinfoscreen.agreetermsAndconditions();
		registrationoverviewlegalinfoscreen.agreePaymentTerms();
		Assert.assertEquals(registrationoverviewlegalinfoscreen.getPaymentPriceValue(), "$ 25.00");
		registrationoverviewlegalinfoscreen.clickPayNowButton();
		/*
		verificationscreen = new VNextVerificationScreen(appiumdriver);
		String regcode = VNextWebServicesUtils.getDeviceRegistrationCode(userregmail).replaceAll("\"", "");
		verificationscreen.setDeviceRegistrationCode(regcode);
		verificationscreen.clickVerifyButton();*/
		registrationoverviewlegalinfoscreen.waitABit(60*1000);
			
		String mailmessage = MailChecker.getMailMessage(VNextConfigInfo.getInstance().getUserCapiMail(), 
				VNextConfigInfo.getInstance().getUserCapiUserPassword(), "PDR: REGISTRATION", fromEmail, bodySearchText + newuserfirstname + " " + newuserlastname);
		
		String newbourl = "";
		if (!mailmessage.equals("")) {
			System.out.println("==========0" + mailmessage);
			newbourl = mailmessage.substring(mailmessage.indexOf("'")+1, mailmessage.lastIndexOf("'"));		
		} else {
			Assert.assertTrue(false, "Mail message is empty");
		}

		initiateWebDriver();
		webdriverGotoWebPage(newbourl);
		VNextBOApproveAccountWebPage approvedaccountwebpage = PageFactory.initElements(
				webdriver, VNextBOApproveAccountWebPage.class);
		VNextBOLoginScreenWebPage loginpage = approvedaccountwebpage.clickLoginLink();
		
		loginpage.userLogin(userregmail, confirmpsw);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		Assert.assertTrue(leftmenu.isUsersMenuItemExists());
		webdriver.quit();
	}
	
	@Test(testName= "Test Case 44272:vNext: verify 'Phone doesn't match this email. Email me my phone number' error for non-existing phone", 
			description = "Verify 'Phone doesn't match this email. Email me my phone number' error for non-existing phone")
	public void testVerifyPhoneDoesntMatchThisEmailEmailMeMyPhoneNumberErrorForNonExistingPhone() throws IOException {
		
		final String userfirstname = "QA";
		final String userlastname = "QA";
		final String boeditionname = "Repair360 Free";
		final String bolineofbusiness = "PDR";
		final String userstate = "California";
		
		//userregmail = usermailprefix + UUID.randomUUID() + usermailpostbox;
		userregmail = usermailprefix + "99999111" + usermailpostbox;
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		VNextRegistrationPersonalInfoScreen regscreen = new VNextRegistrationPersonalInfoScreen(appiumdriver);
		regscreen.setUserRegistrationInfo(userfirstname, userlastname , userphonecountrycode, userregphone, userregmail);
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		regscreen.clickClearUserButton();
		VNextRegistrationScreensModalDialog registrationinformationdlg = new VNextRegistrationScreensModalDialog(appiumdriver);		
		Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "User " + userregmail + " has been deleted");
		
		regscreen.clickDoneButton();
		VNextVerificationScreen verificationscreen = new VNextVerificationScreen(appiumdriver);
		verificationscreen.setDeviceRegistrationCode(VNextWebServicesUtils.getVerificationCodeByPhone(userphonecountrycode + userregphone).replaceAll("\"", ""));
		verificationscreen.clickVerifyButton();
		registrationinformationdlg = new VNextRegistrationScreensModalDialog(appiumdriver);
		Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "Your phone has been verified");
		
		waitABit(2000);
		appiumdriver.switchTo().defaultContent();
		regscreen.waitABit(5000);
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		VNextRegistrationNewUserPersonalInfoScreen newuserpersonalinfoscreen =  new VNextRegistrationNewUserPersonalInfoScreen(appiumdriver);
		newuserpersonalinfoscreen.setNewUserPersonaInfo(boeditionname, userstate);
		newuserpersonalinfoscreen.clickDoneButton();
		VNextRegistrationLineOfBusinessScreen reglineofbusinessscreen = new VNextRegistrationLineOfBusinessScreen(appiumdriver);
		reglineofbusinessscreen.selectEdition(boeditionname);
		reglineofbusinessscreen.selectLineOfBusiness(bolineofbusiness);
		reglineofbusinessscreen.clickDoneButton();
		VNextRegistrationOverviewScreen registrationoverviewscreen = new VNextRegistrationOverviewScreen(appiumdriver);
		Assert.assertEquals(registrationoverviewscreen.getUserFirstNameValue(), userfirstname);
		Assert.assertEquals(registrationoverviewscreen.getUserLastNameValue(), userlastname);
		Assert.assertEquals(registrationoverviewscreen.getUserCompanyNameValue(), boeditionname);
		Assert.assertEquals(registrationoverviewscreen.getUserEmailValue(), userregmail);
		Assert.assertEquals(registrationoverviewscreen.getUserPhoneValue(), userregphoneformatted);
		registrationoverviewscreen.clickDoneButton();
		VNextRegistrationOverviewLegalInfosScreen registrationoverviewlegalinfoscreen = 
				new VNextRegistrationOverviewLegalInfosScreen(appiumdriver);
		registrationoverviewlegalinfoscreen.agreetermsAndconditions();
		registrationoverviewlegalinfoscreen.clickSubmitButton();

		registrationoverviewlegalinfoscreen.waitABit(60*1000);
		resetApp();
		waitABit(10*1000);

		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		regscreen = new VNextRegistrationPersonalInfoScreen(appiumdriver);
		regscreen.setFirstName(userfirstname);	
		regscreen.setLastName(userlastname);	
		regscreen.setEmail(userregmail);
		regscreen.setPhoneNumber("3182324555");
		regscreen.clickDoneButton();
		VNextPhoneMismatchDialog phonemismatchdlg = new VNextPhoneMismatchDialog(appiumdriver);
		Assert.assertTrue(phonemismatchdlg.getInformationDialogBodyMessage().contains("Phone doesn't match this email"));
		phonemismatchdlg.clickEmailMeMyPhoneButton();
		String mailmessage = MailChecker.getMailMessage(VNextConfigInfo.getInstance().getUserCapiMail(), 
				VNextConfigInfo.getInstance().getUserCapiUserPassword(), "Phone Number Reminder", fromEmail, "Your Phone Number is");
		
		String userphone = "";
		if (!mailmessage.equals("")) {
			System.out.println("==========0" + mailmessage);
			userphone = mailmessage.substring(mailmessage.indexOf("+")+1, mailmessage.lastIndexOf(".<br>"));		
		} else {
			Assert.assertTrue(false, "Mail message is empty");
		}
		Assert.assertEquals(userphone, userphonecountrycode + userregphone);
	}
	
	@Test(testName= "Test Case 44273:vNext: verify 'Email doesn't match this phone' error for non-existing email", 
			description = "Verify 'Email doesn't match this phone' error for non-existing email")
	public void testVerifyEmailDoesntMatchThisPhoneErrorForNonExistingEmail() throws IOException {
		
		final String userfirstname = "QA";
		final String userlastname = "QA";
		final String boeditionname = "Repair360 Free";
		final String bolineofbusiness = "PDR";
		final String userstate = "California";
		
		//userregmail = usermailprefix + UUID.randomUUID() + usermailpostbox;
		userregmail = usermailprefix + "99999111" + usermailpostbox;
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		VNextRegistrationPersonalInfoScreen regscreen = new VNextRegistrationPersonalInfoScreen(appiumdriver);
		regscreen.setUserRegistrationInfo(userfirstname, userlastname , userphonecountrycode, userregphone, userregmail);
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		regscreen.clickClearUserButton();
		VNextRegistrationScreensModalDialog registrationinformationdlg = new VNextRegistrationScreensModalDialog(appiumdriver);		
		Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "User " + userregmail + " has been deleted");
		
		regscreen.clickDoneButton();
		VNextVerificationScreen verificationscreen = new VNextVerificationScreen(appiumdriver);
		verificationscreen.setDeviceRegistrationCode(VNextWebServicesUtils.getVerificationCodeByPhone(userphonecountrycode + userregphone).replaceAll("\"", ""));
		verificationscreen.clickVerifyButton();
		registrationinformationdlg = new VNextRegistrationScreensModalDialog(appiumdriver);
		Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "Your phone has been verified");
		
		waitABit(2000);
		appiumdriver.switchTo().defaultContent();
		regscreen.waitABit(5000);
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		VNextRegistrationNewUserPersonalInfoScreen newuserpersonalinfoscreen =  new VNextRegistrationNewUserPersonalInfoScreen(appiumdriver);
		newuserpersonalinfoscreen.setNewUserPersonaInfo(boeditionname, userstate);
		newuserpersonalinfoscreen.clickDoneButton();
		VNextRegistrationLineOfBusinessScreen reglineofbusinessscreen = new VNextRegistrationLineOfBusinessScreen(appiumdriver);
		reglineofbusinessscreen.selectEdition(boeditionname);
		reglineofbusinessscreen.selectLineOfBusiness(bolineofbusiness);
		reglineofbusinessscreen.clickDoneButton();
		VNextRegistrationOverviewScreen registrationoverviewscreen = new VNextRegistrationOverviewScreen(appiumdriver);
		Assert.assertEquals(registrationoverviewscreen.getUserFirstNameValue(), userfirstname);
		Assert.assertEquals(registrationoverviewscreen.getUserLastNameValue(), userlastname);
		Assert.assertEquals(registrationoverviewscreen.getUserCompanyNameValue(), boeditionname);
		Assert.assertEquals(registrationoverviewscreen.getUserEmailValue(), userregmail);
		Assert.assertEquals(registrationoverviewscreen.getUserPhoneValue(), userregphoneformatted);
		registrationoverviewscreen.clickDoneButton();
		VNextRegistrationOverviewLegalInfosScreen registrationoverviewlegalinfoscreen = 
				new VNextRegistrationOverviewLegalInfosScreen(appiumdriver);
		registrationoverviewlegalinfoscreen.agreetermsAndconditions();
		registrationoverviewlegalinfoscreen.clickSubmitButton();

		registrationoverviewlegalinfoscreen.waitABit(60*1000);		
		resetApp();
		waitABit(10*1000);
		switchToWebViewContext();
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		regscreen = new VNextRegistrationPersonalInfoScreen(appiumdriver);
		regscreen.setFirstName(userfirstname);	
		regscreen.setLastName(userlastname);	
		regscreen.setEmail("nonexistent@gmail.com");
		regscreen.setPhoneNumber(userregphone);
		regscreen.clickDoneButton();
		VNextEmailMismatchDialog mailmismatchdlg = new VNextEmailMismatchDialog(appiumdriver);
		Assert.assertTrue(mailmismatchdlg.getInformationDialogBodyMessage().contains("Email doesn't match this phone"));
		mailmismatchdlg.clickTextEmailAddressButton();
	}
	
	@Test(testName= "Test Case 44316:vNext: verify 'Phone number or email address doesn't match the user's account information.' error for existing email_phone", 
			description = "Verify 'Phone number or email address doesn't match the user's account information.' error for existing email_phone")
	public void testVerifyPhoneNumberOrEmailAddressDoesntMatchTheUsersAccountInformationErrorForExistingEmailPhone() throws IOException {
		
		final String userfirstname = "QA";
		final String userlastname = "QA";
		final String boeditionname = "Repair360 Free";
		final String bolineofbusiness = "PDR";
		final String userstate = "California";
		
		//userregmail = usermailprefix + UUID.randomUUID() + usermailpostbox;
		userregmail = usermailprefix + "99999111" + usermailpostbox;
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		VNextRegistrationPersonalInfoScreen regscreen = new VNextRegistrationPersonalInfoScreen(appiumdriver);
		regscreen.setUserRegistrationInfo(userfirstname, userlastname , userphonecountrycode, userregphone, userregmail);
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		regscreen.clickClearUserButton();
		VNextRegistrationScreensModalDialog registrationinformationdlg = new VNextRegistrationScreensModalDialog(appiumdriver);		
		Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "User " + userregmail + " has been deleted");
		
		regscreen.clickDoneButton();
		VNextVerificationScreen verificationscreen = new VNextVerificationScreen(appiumdriver);
		verificationscreen.setDeviceRegistrationCode(VNextWebServicesUtils.getVerificationCodeByPhone(userphonecountrycode + userregphone).replaceAll("\"", ""));
		verificationscreen.clickVerifyButton();
		registrationinformationdlg = new VNextRegistrationScreensModalDialog(appiumdriver);
		Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "Your phone has been verified");
		
		waitABit(2000);
		appiumdriver.switchTo().defaultContent();
		regscreen.waitABit(5000);
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		VNextRegistrationNewUserPersonalInfoScreen newuserpersonalinfoscreen =  new VNextRegistrationNewUserPersonalInfoScreen(appiumdriver);
		newuserpersonalinfoscreen.setNewUserPersonaInfo(boeditionname, userstate);
		newuserpersonalinfoscreen.clickDoneButton();
		VNextRegistrationLineOfBusinessScreen reglineofbusinessscreen = new VNextRegistrationLineOfBusinessScreen(appiumdriver);
		reglineofbusinessscreen.selectEdition(boeditionname);
		reglineofbusinessscreen.selectLineOfBusiness(bolineofbusiness);
		reglineofbusinessscreen.clickDoneButton();
		VNextRegistrationOverviewScreen registrationoverviewscreen = new VNextRegistrationOverviewScreen(appiumdriver);
		Assert.assertEquals(registrationoverviewscreen.getUserFirstNameValue(), userfirstname);
		Assert.assertEquals(registrationoverviewscreen.getUserLastNameValue(), userlastname);
		Assert.assertEquals(registrationoverviewscreen.getUserCompanyNameValue(), boeditionname);
		Assert.assertEquals(registrationoverviewscreen.getUserEmailValue(), userregmail);
		Assert.assertEquals(registrationoverviewscreen.getUserPhoneValue(), userregphoneformatted);
		registrationoverviewscreen.clickDoneButton();
		VNextRegistrationOverviewLegalInfosScreen registrationoverviewlegalinfoscreen = 
				new VNextRegistrationOverviewLegalInfosScreen(appiumdriver);
		registrationoverviewlegalinfoscreen.agreetermsAndconditions();
		registrationoverviewlegalinfoscreen.clickSubmitButton();

		registrationoverviewlegalinfoscreen.waitABit(60*1000);
		resetApp();
		waitABit(10*1000);
		switchToWebViewContext();
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		regscreen = new VNextRegistrationPersonalInfoScreen(appiumdriver);
		regscreen.setFirstName(userfirstname);	
		regscreen.setLastName(userlastname);	
		regscreen.setEmail("mpstart@gmail.com");
		regscreen.setPhoneNumber("3182324584");
		regscreen.clickDoneButton();
		VNextEmailMismatchDialog mailmismatchdlg = new VNextEmailMismatchDialog(appiumdriver);
		Assert.assertTrue(mailmismatchdlg.getInformationDialogBodyMessage().contains("Email doesn't match this phone"));
		mailmismatchdlg.clickTextEmailAddressButton();
		
		mailmismatchdlg.waitABit(1000*15);
		regscreen.setEmail(userregmail);
		regscreen.setPhoneNumber("3127641152");
		regscreen.clickDoneButton();
		registrationinformationdlg = new VNextRegistrationScreensModalDialog(appiumdriver);		
		Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "Phone number or email address doesn't match the user's account information.");
		
	}
}
