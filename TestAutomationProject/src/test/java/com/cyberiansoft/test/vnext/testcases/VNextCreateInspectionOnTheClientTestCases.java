package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.email.EmailUtils;
import com.cyberiansoft.test.email.emaildata.EmailFolder;
import com.cyberiansoft.test.email.emaildata.EmailHost;
import com.cyberiansoft.test.ios10_client.utils.PDFReader;
import com.cyberiansoft.test.ios10_client.utils.PricesCalculations;
import com.cyberiansoft.test.vnext.config.VNextConfigInfo;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOInspectionInfoWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;

public class VNextCreateInspectionOnTheClientTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {
	
	final RetailCustomer testcustomer = new RetailCustomer("Retail", "Automation");
	final String VIN = "19UUA66278A050105";
	
	String inspnumbertc47229 = "";
	String inspnumbertc46975 = "";
	String inspnumbertc47233 = "";
	
	@AfterMethod
	public void tearDown() throws Exception {
		if (webdriver != null)
			webdriver.quit();
	}
	
	@Test(testName= "Test Case 46975:vNext: Check Approved ammount is calculated correctly for Approved inspection", 
			description = "Check Approved ammount is calculated correctly for Approved inspection")
	public void testCheckApprovedAmmountIsCalculatedCorrectlyForApprovedInspection() throws Exception {
		
		final String _make = "Acura";
		final String _model = "TL";
		final String _year = "2008";
		final String percservices = "Aluminum Upcharge"; 
		final String moneyservices = "Dent Repair"; 
		final String matrixservice = "Hail Dent Repair";
		final String matrixsubservice = "State Farm";
		final String moneyserviceprice = "58";
		final String moneyservicequant = "1";
		final String vehiclepartname = "Hood";
		final String vehiclepartsize = "Dime";	
		final String vehiclepartseverity = "Light 6 to 15";	
		final String additionalservice = "Aluminum Upcharge";
		final String insppriceexp = "267.81";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(VIN);
		BaseUtils.waitABit(1000);
		Assert.assertEquals(vehicleinfoscreen.getMakeInfo(), _make);
		Assert.assertEquals(vehicleinfoscreen.getModelInfo(), _model);
		Assert.assertEquals(vehicleinfoscreen.getYear(), _year);
		inspnumbertc46975 = vehicleinfoscreen.getNewInspectionNumber();
		VNextInspectionServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectService(percservices);
		inspservicesscreen.selectService(moneyservices);
		VNextPriceMatrixesScreen pricematrixesscreen = inspservicesscreen.openMatrixServiceDetails(matrixservice);
		VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectPriceMatrix(matrixsubservice);
		VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartname);
		vehiclepartinfoscreen.selectVehiclePartSize(vehiclepartsize);
		vehiclepartinfoscreen.selectVehiclePartSeverity(vehiclepartseverity);
		vehiclepartinfoscreen.selectVehiclePartAdditionalService(additionalservice);
		vehiclepartinfoscreen.clickSaveVehiclePartInfo();
		vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);
		inspservicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		Assert.assertEquals(selectedServicesScreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixservice), matrixsubservice);

		selectedServicesScreen.setServiceAmountValue(moneyservices, moneyserviceprice);
		selectedServicesScreen.setServiceQuantityValue(moneyservices, moneyservicequant);

		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		Assert.assertEquals(inspectionsscreen.getInspectionPriceValue(inspnumbertc46975), PricesCalculations.getPriceRepresentation(insppriceexp));
		VNextEmailScreen emailscreen = inspectionsscreen.clickOnInspectionToEmail(inspnumbertc46975);
		if (!emailscreen.getToEmailFieldValue().equals(VNextConfigInfo.getInstance().getOutlookMail()))
			emailscreen.sentToEmailAddress(VNextConfigInfo.getInstance().getOutlookMail());
		
		emailscreen.sendEmail();

		final String inspectionreportfilenname = inspnumbertc46975 + ".pdf";
		EmailUtils emailUtils = new EmailUtils(EmailHost.OUTLOOK, VNextConfigInfo.getInstance().getOutlookMail(),
				VNextConfigInfo.getInstance().getUserCapiMailPassword(), EmailFolder.JUNK);
		EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
				.withSubjectAndAttachmentFileName(inspnumbertc46975, inspectionreportfilenname).unreadOnlyMessages(true).maxMessagesToSearch(5);
		Assert.assertTrue(emailUtils.waitForMessageWithSubjectAndDownloadAttachment(mailSearchParameters), "Can't find inspection: " + inspnumbertc46975);

		File pdfdoc = new File(inspectionreportfilenname);
		String pdftext = PDFReader.getPDFText(pdfdoc);
		Assert.assertTrue(pdftext.contains(VIN));
		Assert.assertTrue(pdftext.contains(percservices));
		Assert.assertTrue(pdftext.contains(moneyservices));
		Assert.assertTrue(pdftext.contains(insppriceexp));
	}
	
	@Test(testName= "Test Case 47229:vNext mobile: Create Inspection which contains breakage service with big quantity", 
			description = "Create Inspection which contains breakage service with big quantity")
	public void testCreateInspectionWhichContainsBreakageServiceWithBigQuantity() {
		
		
		final String selectdamage = "Dent Repair";
		final String amountvalue = "1577.20";
		final String finalprice = "$1733.45";
		
		final String matrixservice = "Hail Dent Repair";
		final String pricematrix = "State Farm";
		final String vehiclepartname = "Hood";
		final String vehiclepartsize = "Dime";	
		final String vehiclepartseverity = "Light 6 to 15";	
		final String additionalservice = "Aluminum Upcharge";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(VIN);
		inspnumbertc47229 = inspinfoscreen.getNewInspectionNumber();
		inspinfoscreen.swipeScreenLeft();
		VNextVisualScreen visualscreen = new VNextVisualScreen(appiumdriver);
		visualscreen.clickAddServiceButton();
		visualscreen.clickDefaultDamageType(selectdamage);
		visualscreen.clickCarImage();
		BaseUtils.waitABit(1000);
		VNextServiceDetailsScreen servicedetailsscreen = visualscreen.clickCarImageMarker();
		servicedetailsscreen.setServiceAmountValue(amountvalue);
		Assert.assertEquals(servicedetailsscreen.getServiceAmountValue(), amountvalue);
		servicedetailsscreen.setServiceQuantityValue("1");
		servicedetailsscreen.clickServiceDetailsDoneButton();
		visualscreen = new VNextVisualScreen(appiumdriver);
		VNextInspectionServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
		VNextPriceMatrixesScreen pricematrixesscreen = inspservicesscreen.openMatrixServiceDetails(matrixservice);
		VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectPriceMatrix(pricematrix);
		VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartname);
		vehiclepartinfoscreen.selectVehiclePartSize(vehiclepartsize);
		vehiclepartinfoscreen.selectVehiclePartSeverity(vehiclepartseverity);
		vehiclepartinfoscreen.selectVehiclePartAdditionalService(additionalservice);
		vehiclepartinfoscreen.clickSaveVehiclePartInfo();
		vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);
		inspservicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		Assert.assertTrue(selectedServicesScreen.isServiceSelected(matrixservice));
		Assert.assertEquals(selectedServicesScreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixservice), pricematrix);

		inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();
		Assert.assertEquals(inspectionsscreen.getInspectionPriceValue(inspnumbertc47229), finalprice);
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 47231:vNext mobile: Create Inspection with full populated customer info", 
			description = "Create Inspection with full populated customer info")
	public void testCreateInspectionWhithFullPopulatedCustomerInfo() {
		
		RetailCustomer testcustomer = new RetailCustomer("CustomerFirstName", "CustomerLastName");
		testcustomer.setMailAddress("osmak.oksana+408222@gmail.com");
		testcustomer.setCustomerPhone("978385064");
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
		newcustomerscreen.createNewCustomer(testcustomer);
		
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(VIN);
		final String inspectionnumber = inspinfoscreen.getNewInspectionNumber();
		
		inspectionsscreen = inspinfoscreen.saveInspectionViaMenu();
		Assert.assertEquals(inspectionsscreen.getInspectionPriceValue(inspectionnumber), "$0.00");
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 47233:vNext mobile: Create Inspection with customer with First name only", 
			description = "Create Inspection with customer with First name only")
	public void testCreateInspectionWhithCustomerWithFirstNameOnly() {
		
		RetailCustomer testcustomer = new RetailCustomer();
		testcustomer.setFirstName("CustomerFirstName");	
		testcustomer.setMailAddress("osmak.oksana+408222@gmail.com");
		testcustomer.setCustomerPhone("978385064");
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
		newcustomerscreen.createNewCustomer(testcustomer);
		
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(VIN);
		inspnumbertc47233 = inspinfoscreen.getNewInspectionNumber();
		
		inspectionsscreen = inspinfoscreen.saveInspectionViaMenu();
		Assert.assertEquals(inspectionsscreen.getInspectionPriceValue(inspnumbertc47233), "$0.00");
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 52818:vNext mobile: create and email Inspection with two matrix panel", 
			description = "Create and email Inspection with two matrix panel")
	@Parameters({ "usercapi.mail"})
	public void testCreateAndEmailInspectionWithTtwoMatrixPanel(String usermail) {
		
		final String VIN = "JT3AC11R4N1023558";
		final String _make = "Toyota";
		final String _model = "Previa";
		final String _year = "1992";
		final String matrixservice = "Hail Dent Repair";
		final String matrixsubservice = "State Farm";
		final String[] vehiclepartsname = { "Hood", "Roof" };
		final String vehiclepartsize = "Dime";	
		final String vehiclepartseverity = "Light 6 to 15";	
		final String finalprice = "$660.00";	
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(VIN);
		BaseUtils.waitABit(1000);
		Assert.assertEquals(vehicleinfoscreen.getMakeInfo(), _make);
		Assert.assertEquals(vehicleinfoscreen.getModelInfo(), _model);
		Assert.assertEquals(vehicleinfoscreen.getYear(), _year);
		String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		VNextInspectionServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextPriceMatrixesScreen pricematrixesscreen = inspservicesscreen.openMatrixServiceDetails(matrixservice);

		VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectPriceMatrix(matrixsubservice);
		for (String vehiclepartname : vehiclepartsname) {
			VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartname);
			vehiclepartinfoscreen.selectVehiclePartSize(vehiclepartsize);
			vehiclepartinfoscreen.selectVehiclePartSeverity(vehiclepartseverity);
			vehiclepartinfoscreen.selectAllAvailableAdditionalServices();
			vehiclepartinfoscreen.clickSaveVehiclePartInfo();
			vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);

		}
		inspservicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		Assert.assertTrue(selectedServicesScreen.isServiceSelected(matrixservice));

		inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();
		Assert.assertEquals(inspectionsscreen.getInspectionPriceValue(inspnumber), finalprice);
		VNextEmailScreen emailscreen = inspectionsscreen.clickOnInspectionToEmail(inspnumber);
		if (!emailscreen.getToEmailFieldValue().equals(usermail))
			emailscreen.sentToEmailAddress(usermail);
		
		emailscreen.sendEmail();
		inspectionsscreen = new VNextInspectionsScreen(appiumdriver);
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 40794:vNext: verify displaying inspection which contains breakage service with big quantity", 
			description = "Verify displaying inspection which contains breakage service with big quantity",
			dependsOnMethods = { "testCreateInspectionWhichContainsBreakageServiceWithBigQuantity" })
	@Parameters({ "backofficecapi.url", "usercapi.name", "usercapi.psw"})
	public void testVerifyDisplayingInspectionWhichContainsBreakageServiceWithBigQuantity(String bourl, String username, String userpsw) {
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(bourl);
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(username, userpsw);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage inspectionspage = leftmenu.selectInspectionsMenu();
		inspectionspage.selectInspectionInTheList(inspnumbertc47229);
		Assert.assertEquals(inspectionspage.getSelectedInspectionCustomerName(), testcustomer);
		Assert.assertTrue(inspectionspage.isServicePresentForSelectedInspection("Dent Repair"));
		Assert.assertTrue(inspectionspage.isServicePresentForSelectedInspection("Hail Dent Repair"));
		Assert.assertTrue(inspectionspage.isImageLegendContainsBreakageIcon("Hail Damage"));
		Assert.assertTrue(inspectionspage.isImageLegendContainsBreakageIcon("Dent"));
	}
	
	@Test(testName= "Test Case 47236:vNext: verify displaying approved amount for Inspection", 
			description = "Verify displaying approved amount for Inspection",
			dependsOnMethods = { "testCheckApprovedAmmountIsCalculatedCorrectlyForApprovedInspection" })
	@Parameters({ "backofficecapi.url", "usercapi.name", "usercapi.psw"})
	public void testVerifyDisplayingApprovedAmountForInspection(String bourl, String username, String userpsw) {
		
		final String insppriceexp = "$ 267.81";
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(bourl);
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(username, userpsw);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage inspectionspage = leftmenu.selectInspectionsMenu();
		inspectionspage.selectInspectionInTheList(inspnumbertc46975);
		Assert.assertEquals(inspectionspage.getSelectedInspectionTotalAmauntValue(), insppriceexp);
		String mainWindowHandle = webdriver.getWindowHandle();	
		
		VNextBOInspectionInfoWebPage inspectioninfopage = inspectionspage.clickSelectedInspectionPrintIcon();
		inspectioninfopage.closeNewTab(mainWindowHandle);
	}
	
	@Test(testName= "Test Case 47237:vNext: verify displaying Inspection with customer with First name only", 
			description = "Verify displaying inspection with customer with First name only",
			dependsOnMethods = { "testCreateInspectionWhithCustomerWithFirstNameOnly" })
	@Parameters({ "backofficecapi.url", "usercapi.name", "usercapi.psw"})
	public void testVerifyDisplayingInspectionWithCustomerWithFirstNameOnly(String bourl, String username, String userpsw) {
		final String firstname = "CustomerFirstName";
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(bourl);
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(username, userpsw);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage inspectionspage = leftmenu.selectInspectionsMenu();
		inspectionspage.selectInspectionInTheList(inspnumbertc47233);
		Assert.assertEquals(inspectionspage.getSelectedInspectionCustomerName(), firstname);
	}

}
