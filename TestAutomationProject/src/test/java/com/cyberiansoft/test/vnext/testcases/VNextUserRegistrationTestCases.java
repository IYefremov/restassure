package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.email.getnada.NadaEMailService;
import com.cyberiansoft.test.ibs.pageobjects.webpages.IBSLoginWebPage;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.utils.AppContexts;
import com.cyberiansoft.test.vnext.utils.VNextAppUtils;
import com.cyberiansoft.test.vnext.utils.VNextWebServicesUtils;
import com.cyberiansoft.test.vnextbo.screens.*;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class VNextUserRegistrationTestCases extends VNextBaseTestCase {


	private String userregmail = "test.cyberiansoft@amail.club";
	final private String confirmpsw = "111111";
	
	final private String userregphone = "6267477836";
	final private String userphonecountrycode = "1";
	
	@BeforeClass(description = "Setting up new suite")
	public void settingUp() {
		setUp();
		AppiumUtils.setNetworkOn();		
	}
	
	@AfterClass(description = "Setting up new suite")
	public void tearDown()  {
		AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		//appiumdriver.resetApp();	
	}
	
	@BeforeMethod(description = "Setting up new suite")
	public void resetApk() throws IOException, UnirestException {
        NadaEMailService nada = new NadaEMailService();
        nada.setEmailId(userregmail);
        nada.deleteAllMessages();
		VNextAppUtils.resetApp();
		setUp();
	}
	
	@Test(testName= "Test Case 44318:vNext: verify creating BO with JumpStart Edition (PDR)", 
			description = "Verify creating BO with JumpStart Edition (PDR)")
	public void testVerifyCreatingBOWithJumpStartEdition_PDR() throws Exception {
		
		final String userfirstname = "QA";
		final String userlastname = "QA";
		final String boeditionname = "Repair360 Free";
		final String bolineofbusiness = "PDR";
		final String userstate = "California";
		
		//userregmail = usermailprefix + UUID.randomUUID() + usermailpostbox;
		//userregmail = usermailprefix + "99999111" + usermailpostbox;

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
		//registrationinformationdlg = new VNextRegistrationScreensModalDialog(appiumdriver);
		//Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "Your phone has been verified");
		
		/*BaseUtils.waitABit(2000);
		appiumdriver.switchTo().defaultContent();
		BaseUtils.waitABit(5000);*/
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		BaseUtils.waitABit(2000);
		VNextRegistrationNewUserPersonalInfoScreen newuserpersonalinfoscreen =  new VNextRegistrationNewUserPersonalInfoScreen(appiumdriver);
		newuserpersonalinfoscreen.setNewUserPersonaInfo(boeditionname, userstate);
		BaseUtils.waitABit(2000);
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
		//Assert.assertEquals(registrationoverviewscreen.getUserPhoneValue(), userregphoneformatted);
		registrationoverviewscreen.clickDoneButton();
		VNextRegistrationOverviewLegalInfosScreen registrationoverviewlegalinfoscreen = 
				new VNextRegistrationOverviewLegalInfosScreen(appiumdriver);
		registrationoverviewlegalinfoscreen.agreetermsAndconditions();
		registrationoverviewlegalinfoscreen.clickSubmitButton();
		/*verificationscreen = new VNextVerificationScreen(appiumdriver);
		String regcode = VNextWebServicesUtils.getDeviceRegistrationCode(userregmail).replaceAll("\"", "");
		verificationscreen.setDeviceRegistrationCode(regcode);
		verificationscreen.clickVerifyButton();*/
		AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
		NadaEMailService nada = new NadaEMailService();
		nada.setEmailId(userregmail);
		NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
				new NadaEMailService.MailSearchParametersBuilder()
				.withSubject("Repair360 Free: REGISTRATION");
		String mailmessage =  nada.getMailMessageBySybjectKeywords(searchParametersBuilder);
        nada.deleteMessageWithSubject("Repair360 Free: REGISTRATION");
		String linkText= "Click here";
		List<String> allMatches = nada.getUrlsFromMessage(mailmessage, linkText);

		String newbourl =  allMatches.get(0).substring(0, allMatches.get(0).indexOf("\" style"));

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(newbourl);
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
	public void testVerifyCreatingBOWithTechnicianEdition_PDR() throws Exception {
		
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
		

		//userregmail = usermailprefix + "99999111" + usermailpostbox;
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		VNextRegistrationPersonalInfoScreen regscreen = new VNextRegistrationPersonalInfoScreen(appiumdriver);
		regscreen.setUserRegistrationInfo(newuserfirstname, newuserlastname , userphonecountrycode, userregphone, userregmail);
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		regscreen.clickClearUserButton();
		VNextRegistrationScreensModalDialog registrationinformationdlg = new VNextRegistrationScreensModalDialog(appiumdriver);		
		Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "User " + userregmail + " has been deleted");
		regscreen.clickDoneButton();
		BaseUtils.waitABit(3000);
		VNextVerificationScreen verificationscreen = new VNextVerificationScreen(appiumdriver);
		verificationscreen.setDeviceRegistrationCode(VNextWebServicesUtils.getVerificationCodeByPhone(userphonecountrycode + userregphone).replaceAll("\"", ""));
		verificationscreen.clickVerifyButton();
		//registrationinformationdlg = new VNextRegistrationScreensModalDialog(appiumdriver);
		//Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "Your phone has been verified");
		
		BaseUtils.waitABit(2000);
		appiumdriver.switchTo().defaultContent();
		BaseUtils.waitABit(5000);

		VNextRegistrationNewUserPersonalInfoScreen newuserpersonalinfoscreen =  new VNextRegistrationNewUserPersonalInfoScreen(appiumdriver);
		newuserpersonalinfoscreen.setNewUserPersonaInfo(newusercompanyname,
				newuseraddress1, newuseraddress2, newusercity, newuserzip, newusercountry, newuserstate);
		newuserpersonalinfoscreen.clickDoneButton();
		VNextRegistrationLineOfBusinessScreen reglineofbusinessscreen = new VNextRegistrationLineOfBusinessScreen(appiumdriver);
		reglineofbusinessscreen.selectEdition(boeditionname);
		reglineofbusinessscreen.selectLineOfBusiness(bolineofbusiness);
		reglineofbusinessscreen.clickDoneButton();
		appiumdriver.switchTo().defaultContent();
		BaseUtils.waitABit(2000);
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
		//Assert.assertEquals(registrationoverviewscreen.getUserPhoneValue(), userregphoneformatted);
		registrationoverviewscreen.clickDoneButton();
		//registrationoverviewscreen.waitABit(10000);
		VNextRegistrationOverviewLegalInfosScreen registrationoverviewlegalinfoscreen = 
				new VNextRegistrationOverviewLegalInfosScreen(appiumdriver);
		registrationoverviewlegalinfoscreen.agreetermsAndconditions();
		registrationoverviewlegalinfoscreen.agreePaymentTerms();
		BaseUtils.waitABit(1000);
		Assert.assertEquals(registrationoverviewlegalinfoscreen.getPaymentPriceValue(), "$ 60.00");
		registrationoverviewlegalinfoscreen.clickPayNowButton();


        NadaEMailService nada = new NadaEMailService();
        nada.setEmailId(userregmail);
        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
                new NadaEMailService.MailSearchParametersBuilder()
                        .withSubject("PDR: REGISTRATION");
        String mailmessage =  nada.getMailMessageBySybjectKeywords(searchParametersBuilder);

        String linkText= "Click here";
        List<String> allMatches = nada.getUrlsFromMessage(mailmessage, linkText);


        String newbourl =  allMatches.get(0).substring(0, allMatches.get(0).indexOf("\" style"));

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(newbourl);
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
	public void testVerifyPhoneDoesntMatchThisEmailEmailMeMyPhoneNumberErrorForNonExistingPhone() throws Exception {
		
		final String userfirstname = "QA";
		final String userlastname = "QA";
		final String boeditionname = "Repair360 Free";
		final String bolineofbusiness = "PDR";
		final String userstate = "California";
		
		//userregmail = usermailprefix + UUID.randomUUID() + usermailpostbox;
		//userregmail = usermailprefix + "99999111" + usermailpostbox;
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
		//registrationinformationdlg = new VNextRegistrationScreensModalDialog(appiumdriver);
		//Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "Your phone has been verified");
		
		BaseUtils.waitABit(2000);
		appiumdriver.switchTo().defaultContent();
		BaseUtils.waitABit(5000);
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
		//Assert.assertEquals(registrationoverviewscreen.getUserPhoneValue(), userregphoneformatted);
		registrationoverviewscreen.clickDoneButton();
		VNextRegistrationOverviewLegalInfosScreen registrationoverviewlegalinfoscreen = 
				new VNextRegistrationOverviewLegalInfosScreen(appiumdriver);
		registrationoverviewlegalinfoscreen.agreetermsAndconditions();
		registrationoverviewlegalinfoscreen.clickSubmitButton();

		BaseUtils.waitABit(60*1000);
		VNextAppUtils.resetApp();
		BaseUtils.waitABit(10*1000);

		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		regscreen = new VNextRegistrationPersonalInfoScreen(appiumdriver);
		regscreen.setFirstName(userfirstname);	
		regscreen.setLastName(userlastname);
		regscreen.setPhoneNumber("3182324555");
        regscreen.setEmail(userregmail);
		regscreen.clickDoneButton();
		VNextPhoneMismatchDialog phonemismatchdlg = new VNextPhoneMismatchDialog(appiumdriver);
		Assert.assertTrue(phonemismatchdlg.getInformationDialogBodyMessage().contains("Phone doesn't match this email"));
		phonemismatchdlg.clickEmailMeMyPhoneButton();


		NadaEMailService nada = new NadaEMailService();
		nada.setEmailId(userregmail);
		NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
				new NadaEMailService.MailSearchParametersBuilder()
						.withSubject("Phone Number Reminder");
		String mailmessage =  nada.getMailMessageBySybjectKeywords(searchParametersBuilder);
		//nada.deleteMessageWithSubject("Phone Number Reminder");

		String userphone = "";
		if (!mailmessage.equals(""))
			userphone = mailmessage.substring(mailmessage.indexOf("+")+1, mailmessage.lastIndexOf(".<br>"));
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
		//userregmail = usermailprefix + "99999111" + usermailpostbox;
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
		//registrationinformationdlg = new VNextRegistrationScreensModalDialog(appiumdriver);
		//Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "Your phone has been verified");
		
		BaseUtils.waitABit(2000);
		appiumdriver.switchTo().defaultContent();
		BaseUtils.waitABit(5000);
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
		//Assert.assertEquals(registrationoverviewscreen.getUserPhoneValue(), userregphoneformatted);
		registrationoverviewscreen.clickDoneButton();
		VNextRegistrationOverviewLegalInfosScreen registrationoverviewlegalinfoscreen = 
				new VNextRegistrationOverviewLegalInfosScreen(appiumdriver);
		registrationoverviewlegalinfoscreen.agreetermsAndconditions();
		registrationoverviewlegalinfoscreen.clickSubmitButton();

		BaseUtils.waitABit(60*1000);		
		VNextAppUtils.resetApp();
		BaseUtils.waitABit(10*1000);
		AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		regscreen = new VNextRegistrationPersonalInfoScreen(appiumdriver);
		regscreen.setFirstName(userfirstname);	
		regscreen.setLastName(userlastname);
		regscreen.setPhoneNumber(userregphone);
		regscreen.setEmail("nonexistent@gmail.com");
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
		//userregmail = usermailprefix + "99999111" + usermailpostbox;
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
		//registrationinformationdlg = new VNextRegistrationScreensModalDialog(appiumdriver);
		//Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "Your phone has been verified");
		
		BaseUtils.waitABit(2000);
		appiumdriver.switchTo().defaultContent();
		BaseUtils.waitABit(5000);
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
		//Assert.assertEquals(registrationoverviewscreen.getUserPhoneValue(), userregphoneformatted);
		registrationoverviewscreen.clickDoneButton();
		VNextRegistrationOverviewLegalInfosScreen registrationoverviewlegalinfoscreen = 
				new VNextRegistrationOverviewLegalInfosScreen(appiumdriver);
		registrationoverviewlegalinfoscreen.agreetermsAndconditions();
		registrationoverviewlegalinfoscreen.clickSubmitButton();

		BaseUtils.waitABit(60*1000);
		VNextAppUtils.resetApp();
		BaseUtils.waitABit(10*1000);
		AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		regscreen = new VNextRegistrationPersonalInfoScreen(appiumdriver);
		regscreen.setFirstName(userfirstname);	
		regscreen.setLastName(userlastname);
		regscreen.setPhoneNumber("3182324584");
		regscreen.setEmail("mpstart@gmail.com");
		regscreen.clickDoneButton();
		VNextEmailMismatchDialog mailmismatchdlg = new VNextEmailMismatchDialog(appiumdriver);
		Assert.assertTrue(mailmismatchdlg.getInformationDialogBodyMessage().contains("Email doesn't match this phone"));
		mailmismatchdlg.clickTextEmailAddressButton();
		
		BaseUtils.waitABit(1000*15);
		regscreen.setEmail(userregmail);
		regscreen.setPhoneNumber("3127641152");
		regscreen.clickDoneButton();
		VNextPhoneMismatchDialog phonemismatchdialog = new VNextPhoneMismatchDialog(appiumdriver);		
		Assert.assertEquals(phonemismatchdialog.getInformationDialogBodyMessage(), "Warning\nPhone doesn't match this email Email me my phone number");	
		phonemismatchdialog.clickEmailMeMyPhoneButton();
	}
	
	@Test(testName= "Test Case 54272:vNext: User can't create password for IBS after creating password for vNext BO", 
			description = "User can't create password for IBS after creating password for vNext BO")
	public void testUserCantCreatePasswordForIBSAfterCreatingPasswordForVNextBO() throws Exception {
		
		final String userfirstname = "QA";
		final String userlastname = "QA";
		final String boeditionname = "Repair360 Free";
		final String bolineofbusiness = "PDR";
		final String userstate = "California";
		final String ibsStartSearchPhrase = "Please use your Repair360 login to access the <a href=";
		final String ibsEndSearchPhrase = " title=\"Client profile";
		
		//userregmail = usermailprefix + UUID.randomUUID() + usermailpostbox;
		//userregmail = usermailprefix + "99999111" + usermailpostbox;
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
		//registrationinformationdlg = new VNextRegistrationScreensModalDialog(appiumdriver);
		//Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "Your phone has been verified");
		
		BaseUtils.waitABit(2000);
		appiumdriver.switchTo().defaultContent();
		BaseUtils.waitABit(5000);
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
		//Assert.assertEquals(registrationoverviewscreen.getUserPhoneValue(), userregphoneformatted);
		registrationoverviewscreen.clickDoneButton();
		VNextRegistrationOverviewLegalInfosScreen registrationoverviewlegalinfoscreen = 
				new VNextRegistrationOverviewLegalInfosScreen(appiumdriver);
		registrationoverviewlegalinfoscreen.agreetermsAndconditions();
		registrationoverviewlegalinfoscreen.clickSubmitButton();
		AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);


        NadaEMailService nada = new NadaEMailService();
        nada.setEmailId(userregmail);
        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
                new NadaEMailService.MailSearchParametersBuilder()
                        .withSubject("Repair360 Free: REGISTRATION");
        String mailmessage =  nada.getMailMessageBySybjectKeywords(searchParametersBuilder);
        String linkText= "Click here";
        List<String> allMatches = nada.getUrlsFromMessage(mailmessage, linkText);

        String newbourl =  allMatches.get(0).substring(0, allMatches.get(0).indexOf("\" style"));


		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(newbourl);
		VNextBOApproveAccountWebPage approvedaccountwebpage = PageFactory.initElements(
				webdriver, VNextBOApproveAccountWebPage.class);
		VNextBOLoginScreenWebPage loginpage = approvedaccountwebpage.clickLoginLink();
		
		loginpage.userLogin(userregmail, confirmpsw);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		Assert.assertFalse(leftmenu.isUsersMenuItemExists());
		webdriver.quit();


        searchParametersBuilder =
                new NadaEMailService.MailSearchParametersBuilder()
                        .withSubject("Welcome to Client Portal");
        mailmessage =  nada.getMailMessageBySybjectKeywords(searchParametersBuilder);


		String ibsurl = mailmessage.substring(mailmessage.indexOf(ibsStartSearchPhrase) + ibsStartSearchPhrase.length() + 1, mailmessage.indexOf(ibsEndSearchPhrase) - 1);
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(ibsurl);
		IBSLoginWebPage ibsloginpage = PageFactory.initElements(
				webdriver, IBSLoginWebPage.class);
		ibsloginpage.UserLogin(userregmail, confirmpsw);
		webdriver.quit();
	}
	
	
	@Test(testName= "Test Case 63558:Verify user can create Repair 360 edition,"
			+ "Test Case 63590:Verify Repair360 user has ability to choose matrix type", 
			description = "Verify user can create Repair 360 edition,"
					+ "Verify Repair360 user has ability to choose matrix type")
	public void testVerifyUserCanCreateRepair360Edition() throws IOException {
		
		final String newuserfirstname = "TestTech";
		final String newuserlastname = "User";
		final String newusercompanyname = "PDR";
		final String newuseraddress1 = "Address1";
		final String newuseraddress2 = "Address2";
		final String newusercity = "LA";
		final String newuserzip = "5214BA63";
		final String newusercountry = "United States of America";
		final String newusercountryselected = "United States";
		final String newuserstate = "California";
		final String userpaymentname = newuserfirstname + " " + newuserlastname;
		final String  usercardnumber = "4242424242424242";
		final String  securitycode = "123";
		final String  expmonth = "11";
		final String  expyear = "2019";
		
		final String boeditionname = "Repair360";
		final String bolineofbusiness = "PDR";
		
		final String firstname = "Eric";
		final String lastname = "Burn";
		final String customeraddress = "Stryis'ka, 223";
		final String customercity = "L'viv";
		final String customerzip = "79051";
		final String customeremail = "test.cyberiansoft+408222@gmail.com";
		
		final String testVIN = "1FMCU0DG4BK830800";
		final String matrixservice = "Hail Dent Repair";
		final String[] availablepricematrixes = { "Nationwide Insurance", "Progressive", "State Farm" };
		final String vehiclepartname = "Hood";
		final String vehiclepartsize = "Dime";	
		final String[] vehiclepartseverities = { "6-15", "Light 6 to 15", "Light 6 to 15" };	
		
		//userregmail = usermailprefix + "99999111" + usermailpostbox;
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		VNextRegistrationPersonalInfoScreen regscreen = new VNextRegistrationPersonalInfoScreen(appiumdriver);
		regscreen.setUserRegistrationInfo(newuserfirstname, newuserlastname , userphonecountrycode, userregphone, userregmail);
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		regscreen.clickClearUserButton();
		VNextRegistrationScreensModalDialog registrationinformationdlg = new VNextRegistrationScreensModalDialog(appiumdriver);		
		Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "User " + userregmail + " has been deleted");
		regscreen.clickDoneButton();
		BaseUtils.waitABit(3000);
		VNextVerificationScreen verificationscreen = new VNextVerificationScreen(appiumdriver);
		verificationscreen.setDeviceRegistrationCode(VNextWebServicesUtils.getVerificationCodeByPhone(userphonecountrycode + userregphone).replaceAll("\"", ""));
		verificationscreen.clickVerifyButton();
		//registrationinformationdlg = new VNextRegistrationScreensModalDialog(appiumdriver);
		//Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "Your phone has been verified");
		
		BaseUtils.waitABit(2000);
		appiumdriver.switchTo().defaultContent();
		BaseUtils.waitABit(5000);
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
		BaseUtils.waitABit(2000);
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		VNextRegistrationPaymentInfoScreen registrationpaymentinfoscreen = new VNextRegistrationPaymentInfoScreen(appiumdriver);
		registrationpaymentinfoscreen.setUserPaiymentInfo(userpaymentname, usercardnumber,
				expmonth, expyear, securitycode);
		registrationpaymentinfoscreen.clickUseRegistrationAddress();
		
		Assert.assertEquals(registrationpaymentinfoscreen.getUserAddress1Value(), newuseraddress1);
		Assert.assertEquals(registrationpaymentinfoscreen.getUserAddress2Value(), newuseraddress2);
		Assert.assertEquals(registrationpaymentinfoscreen.getUserCityValue(), newusercity);
		Assert.assertEquals(registrationpaymentinfoscreen.getUserZipValue(), newuserzip);
		Assert.assertEquals(registrationpaymentinfoscreen.getUserCountryValue(), newusercountryselected);
		Assert.assertEquals(registrationpaymentinfoscreen.getUserStateValue(), newuserstate);
		registrationpaymentinfoscreen.clickDoneButton();
		VNextRegistrationOverviewScreen registrationoverviewscreen = new VNextRegistrationOverviewScreen(appiumdriver);
		Assert.assertEquals(registrationoverviewscreen.getUserFirstNameValue(), newuserfirstname);
		Assert.assertEquals(registrationoverviewscreen.getUserLastNameValue(), newuserlastname);
		Assert.assertEquals(registrationoverviewscreen.getUserCompanyNameValue(), newusercompanyname);
		Assert.assertEquals(registrationoverviewscreen.getUserEmailValue(), userregmail);
		//Assert.assertEquals(registrationoverviewscreen.getUserPhoneValue(), userregphoneformatted);
		registrationoverviewscreen.clickDoneButton();
		//registrationoverviewscreen.waitABit(10000);
		VNextRegistrationOverviewLegalInfosScreen registrationoverviewlegalinfoscreen = 
				new VNextRegistrationOverviewLegalInfosScreen(appiumdriver);
		registrationoverviewlegalinfoscreen.agreetermsAndconditions();
		registrationoverviewlegalinfoscreen.agreePaymentTerms();
		Assert.assertEquals(registrationoverviewlegalinfoscreen.getPaymentPriceValue(), "$ 60.00");
		registrationoverviewlegalinfoscreen.clickPayNowButton();
		BaseUtils.waitABit(25000);
		
		VNextAppUtils.restartApp();
		
		WebDriverWait wait = new WebDriverWait(appiumdriver, 240);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Data has been successfully downloaded']")));
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogOKButton();
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		VNextNewCustomerScreen newcustomerscreen = customersscreen. clickAddCustomerButton();
		newcustomerscreen.setCustomerFirstName(firstname);
		newcustomerscreen.setCustomerLastName(lastname);
		newcustomerscreen.setCustomerEmail(customeremail);
		newcustomerscreen.setCustomerAddress(customeraddress);
		newcustomerscreen.setCustomerCity(customercity);
		newcustomerscreen.setCustomerZIP(customerzip);
		newcustomerscreen.clickSaveCustomerButton();
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(testVIN);
		vehicleinfoscreen.swipeScreenLeft();
		VNextClaimInfoScreen claimscren = new VNextClaimInfoScreen(appiumdriver);
		claimscren.selectInsuranceCompany("Test Insurance Company");		
		vehicleinfoscreen.swipeScreensLeft(2);		
		VNextAvailableServicesScreen inspservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		for (int i = 0; i < availablepricematrixes.length; i++) {
			VNextPriceMatrixesScreen pricematrixesscreen = inspservicesscreen.openMatrixServiceDetails(matrixservice);
			VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectPriceMatrix(availablepricematrixes[i]);
			VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartname);
			vehiclepartinfoscreen.selectVehiclePartSize(vehiclepartsize);
			vehiclepartinfoscreen.selectVehiclePartSeverity(vehiclepartseverities[i]);
			vehiclepartinfoscreen.clickSaveVehiclePartInfo();
			vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);
			inspservicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();
			VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
			Assert.assertTrue(selectedServicesScreen.isPriceMatrixValuePresentForSelectedServicesByName(matrixservice, availablepricematrixes[i]));
			inspservicesscreen = selectedServicesScreen.switchToAvalableServicesView();
		}
		inspectionsscreen = inspservicesscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 63612:Verify user can create Repair Free Edition, "
			+ "Test Case 63613:Verify Repair 360 Free Edition user can select only one matrix type",
			description = "Verify user can create Repair Free Edition, "
					+ "Verify Repair 360 Free Edition user can select only one matrix type")
	public void testVerifyUserCanCreateRepair360FreeEdition() throws IOException {
		
		final String newuserfirstname = "TestTech";
		final String newuserlastname = "User";
		final String newusercompanyname = "PDR";
		final String newuseraddress1 = "Address1";
		final String newuseraddress2 = "Address2";
		final String newusercity = "LA";
		final String newuserzip = "5214BA63";
		final String newusercountry = "United States of America";
		final String newusercountryselected = "United States";
		final String newuserstate = "California";
		
		final String boeditionname = "Repair360 Free";
		final String bolineofbusiness = "PDR";
		
		final String firstname = "Eric";
		final String lastname = "Burn";
		final String customeraddress = "Stryis'ka, 223";
		final String customercity = "L'viv";
		final String customerzip = "79051";
		final String customeremail = "test.cyberiansoft+408222@gmail.com";
		
		final String testVIN = "1FMCU0DG4BK830800";
		final String matrixservice = "Hail Dent Repair";
		final String availablepricematrix = "State Farm";
		
		
		//userregmail = usermailprefix + "99999111" + usermailpostbox;
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		VNextRegistrationPersonalInfoScreen regscreen = new VNextRegistrationPersonalInfoScreen(appiumdriver);
		regscreen.setUserRegistrationInfo(newuserfirstname, newuserlastname , userphonecountrycode, userregphone, userregmail);
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		regscreen.clickClearUserButton();
		VNextRegistrationScreensModalDialog registrationinformationdlg = new VNextRegistrationScreensModalDialog(appiumdriver);		
		Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "User " + userregmail + " has been deleted");
		regscreen.clickDoneButton();
		BaseUtils.waitABit(3000);
		VNextVerificationScreen verificationscreen = new VNextVerificationScreen(appiumdriver);
		verificationscreen.setDeviceRegistrationCode(VNextWebServicesUtils.getVerificationCodeByPhone(userphonecountrycode + userregphone).replaceAll("\"", ""));
		verificationscreen.clickVerifyButton();
		//registrationinformationdlg = new VNextRegistrationScreensModalDialog(appiumdriver);
		//Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "Your phone has been verified");
		
		BaseUtils.waitABit(2000);
		appiumdriver.switchTo().defaultContent();
		BaseUtils.waitABit(5000);
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

		VNextRegistrationOverviewScreen registrationoverviewscreen = new VNextRegistrationOverviewScreen(appiumdriver);
		Assert.assertEquals(registrationoverviewscreen.getUserFirstNameValue(), newuserfirstname);
		Assert.assertEquals(registrationoverviewscreen.getUserLastNameValue(), newuserlastname);
		Assert.assertEquals(registrationoverviewscreen.getUserCompanyNameValue(), newusercompanyname);
		Assert.assertEquals(registrationoverviewscreen.getUserEmailValue(), userregmail);
		//Assert.assertEquals(registrationoverviewscreen.getUserPhoneValue(), userregphoneformatted);
		Assert.assertEquals(registrationoverviewscreen.getAddress1Value(), newuseraddress1);
		Assert.assertEquals(registrationoverviewscreen.getAddress2Value(), newuseraddress2);
		Assert.assertEquals(registrationoverviewscreen.getCityValue(), newusercity);
		Assert.assertEquals(registrationoverviewscreen.getZipValue(), newuserzip);
		Assert.assertEquals(registrationoverviewscreen.getStateValue(), newuserstate);
		Assert.assertEquals(registrationoverviewscreen.getCountryValue(), newusercountryselected);
		registrationoverviewscreen.clickDoneButton();
		VNextRegistrationOverviewLegalInfosScreen registrationoverviewlegalinfoscreen = 
				new VNextRegistrationOverviewLegalInfosScreen(appiumdriver);
		registrationoverviewlegalinfoscreen.agreetermsAndconditions();
		registrationoverviewlegalinfoscreen.clickSubmitButton();

		BaseUtils.waitABit(10000);
		VNextAppUtils.restartApp();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 90);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Data has been successfully downloaded']")));
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogOKButton();
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		Assert.assertTrue(homescreen.isUpgrateToProBannerVisible());


		//todo: Test Case 63613:Verify Repair 360 Free Edition user can select only one matrix type not valid anymore
		/*VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
		newcustomerscreen.setCustomerFirstName(firstname);
		newcustomerscreen.setCustomerLastName(lastname);
		newcustomerscreen.setCustomerEmail(customeremail);
		newcustomerscreen.setCustomerAddress(customeraddress);
		newcustomerscreen.setCustomerCity(customercity);
		newcustomerscreen.setCustomerZIP(customerzip);
		newcustomerscreen.clickSaveCustomerButton();
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(testVIN);
		vehicleinfoscreen.swipeScreenLeft();
		VNextClaimInfoScreen claimscren = new VNextClaimInfoScreen(appiumdriver);
		claimscren.selectInsuranceCompany("Test Insurance Company");		
		vehicleinfoscreen.swipeScreensLeft(2);			
		VNextAvailableServicesScreen inspservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		inspservicesscreen.selectMatrixService(matrixservice);
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		Assert.assertEquals(selectedServicesScreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixservice), availablepricematrix);

		Assert.assertTrue(selectedServicesScreen.isServiceSelected(matrixservice));
		Assert.assertEquals(selectedServicesScreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixservice), availablepricematrix);
		
		inspectionsscreen = selectedServicesScreen.cancelInspection();
		inspectionsscreen.clickBackButton();*/
	}
	
	@Test(testName= "Test Case 64228:R360: Submit Customer Feedback from Repair360 Free ediition", 
			description = "Submit Customer Feedback from Repair360 Free ediition")
	public void testSubmitCustomerFeedbackFromRepair360FreeEdiition() throws Exception {
		
		final String userfirstname = "QA";
		final String userlastname = "QA";
		final String boeditionname = "Repair360 Free";
		final String bolineofbusiness = "PDR";
		final String userstate = "California";
		
		final String feedbackType = "Feature Request";
		final String feedbackArea = "Customers";
		final String subArea = "Create/Edit customer";
		final String feedbackSubject = "Test Feedback Repair360";
		final String feedbackDesc = "Testing kayako ticket creation from feedback send from Repair360 Free";
		
		
		//userregmail = usermailprefix + UUID.randomUUID() + usermailpostbox;
		//userregmail = usermailprefix + "99999111" + usermailpostbox;
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		VNextRegistrationPersonalInfoScreen regscreen = new VNextRegistrationPersonalInfoScreen(appiumdriver);
		regscreen.setUserRegistrationInfo(userfirstname, userlastname , userphonecountrycode, userregphone, userregmail);
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		regscreen.clickClearUserButton();
		VNextRegistrationScreensModalDialog registrationinformationdlg = new VNextRegistrationScreensModalDialog(appiumdriver);		
		Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "User " + userregmail + " has been deleted");
		
		regscreen.clickDoneButton();
		BaseUtils.waitABit(4000);
		VNextVerificationScreen verificationscreen = new VNextVerificationScreen(appiumdriver);
		verificationscreen.setDeviceRegistrationCode(VNextWebServicesUtils.getVerificationCodeByPhone(userphonecountrycode + userregphone).replaceAll("\"", ""));
		verificationscreen.clickVerifyButton();
		//registrationinformationdlg = new VNextRegistrationScreensModalDialog(appiumdriver);
		//Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "Your phone has been verified");
		
		BaseUtils.waitABit(2000);
		appiumdriver.switchTo().defaultContent();
		BaseUtils.waitABit(5000);
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
		//Assert.assertEquals(registrationoverviewscreen.getUserPhoneValue(), userregphoneformatted);
		registrationoverviewscreen.clickDoneButton();
		VNextRegistrationOverviewLegalInfosScreen registrationoverviewlegalinfoscreen = 
				new VNextRegistrationOverviewLegalInfosScreen(appiumdriver);
		registrationoverviewlegalinfoscreen.agreetermsAndconditions();
		registrationoverviewlegalinfoscreen.clickSubmitButton();
		
		BaseUtils.waitABit(15000);
		VNextAppUtils.restartApp();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 90);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Data has been successfully downloaded']")));
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogOKButton();
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
		VNextFeedbackScreen feedbackscreen = statusscreen.clickFeedbackButton();
		feedbackscreen.selectFeedbackType(feedbackType);
		feedbackscreen.selectArea(feedbackArea, subArea);
		feedbackscreen.setFeedbackSubject(feedbackSubject);
		feedbackscreen.setFeedbackDescription(feedbackDesc);
		feedbackscreen.clickSendButton();

		NadaEMailService nada = new NadaEMailService();
		nada.setEmailId(userregmail);
		NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
				new NadaEMailService.MailSearchParametersBuilder()
						.withSubject("Test Feedback Repair360");
		Assert.assertTrue(nada.waitForMessage(searchParametersBuilder));

	}
	
	@Test(testName= "Test Case 64407:R360: submit Customer Feedback from Repair360 ediition (upgraded from free)", 
			description = "Submit Customer Feedback from Repair360 ediition (upgraded from free)")
	public void testSubmitCustomerFeedbackFromRepair360FreeEdiitionUpgratedFromFree() throws Exception {
		
		final String userfirstname = "QA";
		final String userlastname = "QA";
		final String boeditionname = "Repair360 Free";
		final String bolineofbusiness = "PDR";
		final String userstate = "California";
		
		
		final String edition = "Repair360";
		final String userfullname = "Oleksandr Kramar";
		final String cardnumber = "4242424242424242";
		final String expirationmonth = "09";
		final String expirationyear = "2021";
		final String cvccode = "122";
		final String billindaddressline1 = "First street 21/13";
		final String billindcity = "New York";
		final String billindzip = "79031";
		final String billingcountry = "United States of America";
		final String billingstate = "California";
		
		final String feedbackType = "Feature Request";
		final String feedbackArea = "Customers";
		final String subArea = "Create/Edit customer";
		final String feedbackSubject = "Test Feedback Repair360";
		final String feedbackDesc = "Testing kayako ticket creation from feedback send from Repair360 Free";
		
		
		//userregmail = usermailprefix + UUID.randomUUID() + usermailpostbox;
		//userregmail = usermailprefix + "99999111" + usermailpostbox;
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		VNextRegistrationPersonalInfoScreen regscreen = new VNextRegistrationPersonalInfoScreen(appiumdriver);
		regscreen.setUserRegistrationInfo(userfirstname, userlastname , userphonecountrycode, userregphone, userregmail);
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		regscreen.clickClearUserButton();
		VNextRegistrationScreensModalDialog registrationinformationdlg = new VNextRegistrationScreensModalDialog(appiumdriver);		
		Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "User " + userregmail + " has been deleted");
		
		regscreen.clickDoneButton();
		BaseUtils.waitABit(4000);
		VNextVerificationScreen verificationscreen = new VNextVerificationScreen(appiumdriver);
		verificationscreen.setDeviceRegistrationCode(VNextWebServicesUtils.getVerificationCodeByPhone(userphonecountrycode + userregphone).replaceAll("\"", ""));
		verificationscreen.clickVerifyButton();
		//registrationinformationdlg = new VNextRegistrationScreensModalDialog(appiumdriver);
		//Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "Your phone has been verified");
		
		BaseUtils.waitABit(2000);
		appiumdriver.switchTo().defaultContent();
		BaseUtils.waitABit(5000);
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
		//Assert.assertEquals(registrationoverviewscreen.getUserPhoneValue(), userregphoneformatted);
		registrationoverviewscreen.clickDoneButton();
		VNextRegistrationOverviewLegalInfosScreen registrationoverviewlegalinfoscreen = 
				new VNextRegistrationOverviewLegalInfosScreen(appiumdriver);
		registrationoverviewlegalinfoscreen.agreetermsAndconditions();
		registrationoverviewlegalinfoscreen.clickSubmitButton();
		
		BaseUtils.waitABit(25000);
		VNextAppUtils.restartApp();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 90);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Data has been successfully downloaded']")));
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogOKButton();
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		//homescreen.clickUpgrateToProBanner();
		VNextStatusScreen statuscsreen = homescreen.clickStatusMenuItem();
		VNextEmailVerificationScreen emailverificationscren = statuscsreen.goToBackOfficeButton();
		emailverificationscren.setEmailAddress(userregmail);
		emailverificationscren.clickActivateButton();

		NadaEMailService nada = new NadaEMailService();
		nada.setEmailId(userregmail);
		NadaEMailService.MailSearchParametersBuilder searchParametersBuilder =
				new NadaEMailService.MailSearchParametersBuilder()
						.withSubject("Repair360 Free: REGISTRATION");
		String mailmessage =  nada.getMailMessageBySybjectKeywords(searchParametersBuilder);
		nada.deleteMessageWithSubject("Repair360 Free: REGISTRATION");
		String linkText= "Click here";
		List<String> allMatches = nada.getUrlsFromMessage(mailmessage, linkText);

		String newbourl =  allMatches.get(0).substring(0, allMatches.get(0).indexOf("\" style"));
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(newbourl);
		VNextBOApproveAccountWebPage approvedaccountwebpage = PageFactory.initElements(
				webdriver, VNextBOApproveAccountWebPage.class);
		VNextBOLoginScreenWebPage loginpage = approvedaccountwebpage.clickLoginLink();
		
		loginpage.userLogin(userregmail, confirmpsw);
		VNextBOHeaderPanel vnextboheaderpanel = PageFactory.initElements(webdriver,
				VNextBOHeaderPanel.class);
		VNextUpgradeInfoWebPage upgradeinfopage = vnextboheaderpanel.clickUpgradeNowBanner();
		VNextPaymentInfoWebPage paymentinfopage = upgradeinfopage.clickUnlockRepair360EditionButton();
		
		paymentinfopage.setUserPaymentsInfo(edition, userfullname, cardnumber, 
				expirationmonth, expirationyear, cvccode, billindaddressline1, 
				billindcity, billindzip, billingcountry, billingstate);

		paymentinfopage.clickSaveAndCloseCongratsModal();
		webdriver.quit();

		VNextStatusScreen statusscreen = new VNextStatusScreen(appiumdriver);
		VNextFeedbackScreen feedbackscreen = statusscreen.clickFeedbackButton();
		feedbackscreen.selectFeedbackType(feedbackType);
		feedbackscreen.selectArea(feedbackArea, subArea);
		feedbackscreen.setFeedbackSubject(feedbackSubject);
		feedbackscreen.setFeedbackDescription(feedbackDesc);
		statusscreen = feedbackscreen.clickSendButton();
		statusscreen.clickBackButton();

		nada = new NadaEMailService();
		nada.setEmailId(userregmail);
		searchParametersBuilder =
				new NadaEMailService.MailSearchParametersBuilder()
						.withSubject("Test Feedback Repair360").waitTimeForMessage(Duration.ofMinutes(10), Duration.ofSeconds(30));
		Assert.assertTrue(nada.waitForMessage(searchParametersBuilder));
	}
	
	@Test(testName= "Test Case 64408:R360: submit Customer Feedback from Repair360 ediition", 
			description = "Submit Customer Feedback from Repair360 ediition")
	public void testSubmitCustomerFeedbackFromRepair360Ediition() throws IOException {
		
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
		
		final String feedbackType = "Feature Request";
		final String feedbackArea = "Customers";
		final String subArea = "Create/Edit customer";
		final String feedbackSubject = "Test Feedback Repair360";
		final String feedbackDesc = "Testing kayako ticket creation from feedback send from Repair360 Free";

		//userregmail = usermailprefix + "99999111" + usermailpostbox;
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		VNextRegistrationPersonalInfoScreen regscreen = new VNextRegistrationPersonalInfoScreen(appiumdriver);
		regscreen.setUserRegistrationInfo(newuserfirstname, newuserlastname , userphonecountrycode, userregphone, userregmail);
		//appiumdriver.switchTo().frame(appiumdriver.findElement(By.xpath("//iframe")));
		regscreen.clickClearUserButton();
		VNextRegistrationScreensModalDialog registrationinformationdlg = new VNextRegistrationScreensModalDialog(appiumdriver);		
		Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "User " + userregmail + " has been deleted");
		regscreen.clickDoneButton();
		BaseUtils.waitABit(3000);
		VNextVerificationScreen verificationscreen = new VNextVerificationScreen(appiumdriver);
		verificationscreen.setDeviceRegistrationCode(VNextWebServicesUtils.getVerificationCodeByPhone(userphonecountrycode + userregphone).replaceAll("\"", ""));
		verificationscreen.clickVerifyButton();
		//registrationinformationdlg = new VNextRegistrationScreensModalDialog(appiumdriver);
		//Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "Your phone has been verified");
		
		BaseUtils.waitABit(2000);
		appiumdriver.switchTo().defaultContent();
		BaseUtils.waitABit(5000);
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
		BaseUtils.waitABit(2000);
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
		//Assert.assertEquals(registrationoverviewscreen.getUserPhoneValue(), userregphoneformatted);
		registrationoverviewscreen.clickDoneButton();
		//registrationoverviewscreen.waitABit(10000);
		VNextRegistrationOverviewLegalInfosScreen registrationoverviewlegalinfoscreen = 
				new VNextRegistrationOverviewLegalInfosScreen(appiumdriver);
		registrationoverviewlegalinfoscreen.agreetermsAndconditions();
		registrationoverviewlegalinfoscreen.agreePaymentTerms();
		Assert.assertEquals(registrationoverviewlegalinfoscreen.getPaymentPriceValue(), "$ 19.00");
		registrationoverviewlegalinfoscreen.clickPayNowButton();
		
		BaseUtils.waitABit(10000);
		VNextAppUtils.restartApp();
		
		WebDriverWait wait = new WebDriverWait(appiumdriver, 90);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Data has been successfully downloaded']")));
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogOKButton();
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
		VNextFeedbackScreen feedbackscreen = statusscreen.clickFeedbackButton();
		feedbackscreen.selectFeedbackType(feedbackType);
		feedbackscreen.selectArea(feedbackArea, subArea);
		feedbackscreen.setFeedbackSubject(feedbackSubject);
		feedbackscreen.setFeedbackDescription(feedbackDesc);
		statusscreen = feedbackscreen.clickSendButton();
		statusscreen.clickBackButton();
	}
}
