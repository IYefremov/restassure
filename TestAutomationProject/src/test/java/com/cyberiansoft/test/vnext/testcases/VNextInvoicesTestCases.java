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
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVisualScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextWorkOrderSummaryScreen;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOInvoicesWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;

public class VNextInvoicesTestCases  extends BaseTestCaseWithDeviceRegistrationAndUserLogin {
	
	final RetailCustomer testcustomer = new RetailCustomer("Retail", "Automation");
	final String testVIN = "1FMCU0DG4BK830800";
	
	String invoicenumbertc48094 = "";
	String invoicenumbertc46415 = "";
	final String notetext = "Invoice text note";
	
	@Test(testName= "Test Case 45878:vNext: Create Invoice with matrix service", 
			description = "Create Invoice with matrix service")
	public void testCreateInvoiceWithMatrixService() {
		final String VIN = "19UUA66278A050105";
		final String _make = "Acura";
		final String _model = "TL";
		final String color = "Red";
		final String year = "2008";
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
		final String ponumber = "123po";
		final String invoicequicknote = "Alum Deck";
		final String wopriceexp = "$267.81";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextVehicleInfoScreen vehicleinfoscreen = homescreen.openCreateWOWizard(testcustomer);
		vehicleinfoscreen.populateVehicleInfoDataOnCreateWOWizard(VIN, color);
		
		Assert.assertEquals(vehicleinfoscreen.getMakeInfo(), _make);
		Assert.assertEquals(vehicleinfoscreen.getModelInfo(), _model);
		Assert.assertEquals(vehicleinfoscreen.getYear(), year);
		vehicleinfoscreen.swipeScreenLeft();
		VNextAvailableServicesScreen servicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		servicesscreen.selectService(percservices);
		servicesscreen.selectService(moneyservices);
		VNextPriceMatrixesScreen pricematrixesscreen = servicesscreen.openMatrixServiceDetails(matrixservice);
		VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectPriceMatrix(matrixsubservice);
		VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartname);
		vehiclepartinfoscreen.selectVehiclePartSize(vehiclepartsize);
		vehiclepartinfoscreen.selectVehiclePartSeverity(vehiclepartseverity);
		vehiclepartinfoscreen.selectVehiclePartAdditionalService(additionalservice);
		vehiclepartinfoscreen.clickSaveVehiclePartInfo();
		vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);
		servicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();
		VNextSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesView();
		Assert.assertEquals(selectedServicesScreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixservice), matrixsubservice);

		selectedServicesScreen.setServiceAmountValue(moneyservices, moneyserviceprice);
		selectedServicesScreen.setServiceQuantityValue(moneyservices, moneyservicequant);

		final String wonumber = selectedServicesScreen.getNewInspectionNumber();
		VNextWorkOrdersScreen workordersscreen = selectedServicesScreen.saveWorkOrderViaMenu();
		final String woprice = workordersscreen.getWorkOrderPriceValue(wonumber);
		Assert.assertEquals(woprice, wopriceexp);
		workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.setInvoicePONumber(ponumber);
		invoiceinfoscreen.addQuickNoteToInvoice(invoicequicknote);
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoice();
		Assert.assertEquals(invoicesscreen.getInvoicePriceValue(invoicenumber), woprice);
		homescreen = invoicesscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 46415:vNext: Create Invoice with image and text note", 
			description = "Create Invoice with matrix service")
	public void testCreateInvoiceWithTextNote() {
		final String VIN = "19UUA66278A050105";
		final String _make = "Acura";
		final String _model = "TL";
		final String color = "Red";
		final String year = "2008";
		final String wonote = "Only text Note"; 
		 
		final String ponumber = "123po";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextVehicleInfoScreen vehicleinfoscreen = homescreen.openCreateWOWizard(testcustomer);
		vehicleinfoscreen.populateVehicleInfoDataOnCreateWOWizard(VIN, color);
		
		Assert.assertEquals(vehicleinfoscreen.getMakeInfo(), _make);
		Assert.assertEquals(vehicleinfoscreen.getModelInfo(), _model);
		Assert.assertEquals(vehicleinfoscreen.getYear(), year);
		
		VNextNotesScreen notesscreen = vehicleinfoscreen.clickInspectionNotesOption();
		notesscreen.setNoteText(wonote);
		notesscreen.clickNotesBackButton();
		vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		final String wonumber = vehicleinfoscreen.getNewInspectionNumber();
		VNextWorkOrdersScreen workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
		Assert.assertEquals(workordersscreen.getWorkOrderPriceValue(wonumber), "$0.00");
		workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.setInvoicePONumber(ponumber);
		invoiceinfoscreen.addTextNoteToInvoice(notetext);
		invoicenumbertc46415 = invoiceinfoscreen.getInvoiceNumber();
		VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoice();
		Assert.assertEquals(invoicesscreen.getInvoicePriceValue(invoicenumbertc46415), "$0.00");
		homescreen = invoicesscreen.clickBackButton();
		BaseUtils.waitABit(60*1000);
	}
	
	@Test(testName= "Test Case 48094:vNext mobile: Create Invoice which contains price services with decimal quantity", 
			description = "Create Invoice which contains price services with decimal quantity")
	public void testCreateInvoiceWhichContainsPriceServicesWithDecimalQuantity() throws Exception {
		
		final String dentdamage = "Dent"; 
		final String amountvalue = "0.99"; 
		final String moneyservicename = "Bumper Repair";
		final String insppriceexp = "1.96";
		final String ponumber = "po123";
			
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(testVIN);
		String inspnumbertc = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreenLeft();
		VNextVisualScreen visualscreen = new VNextVisualScreen(appiumdriver);
		visualscreen.clickAddServiceButton();
		visualscreen.clickDefaultDamageType(dentdamage);
		visualscreen.clickCarImage();
		BaseUtils.waitABit(1000);
		VNextServiceDetailsScreen servicedetailsscreen = visualscreen.clickCarImageMarker();
		servicedetailsscreen.setServiceAmountValue(amountvalue);
		Assert.assertEquals(servicedetailsscreen.getServiceAmountValue(), amountvalue);
		servicedetailsscreen.setServiceQuantityValue(amountvalue);
		servicedetailsscreen.clickServiceDetailsDoneButton();
		visualscreen = new VNextVisualScreen(appiumdriver);
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectService(moneyservicename);
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		selectedServicesScreen.setServiceAmountValue(moneyservicename, amountvalue);
		selectedServicesScreen.setServiceQuantityValue(moneyservicename, amountvalue);
		inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();
		Assert.assertEquals(inspectionsscreen.getInspectionPriceValue(inspnumbertc), PricesCalculations.getPriceRepresentation(insppriceexp));
		
		VNextInspectionsMenuScreen inspmenuscreen = inspectionsscreen.clickOnInspectionByInspNumber(inspnumbertc);
		inspmenuscreen.clickCreateWorkOrderInspectionMenuItem();
		vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		VNextWorkOrderSummaryScreen wosummaryscreen = vehicleinfoscreen.goToWorkOrderSummaryScreen();
		wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();

		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.setInvoicePONumber(ponumber);
		invoicenumbertc48094  = invoiceinfoscreen.getInvoiceNumber();
		VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoice();
		Assert.assertEquals(invoicesscreen.getInvoicePriceValue(invoicenumbertc48094), PricesCalculations.getPriceRepresentation(insppriceexp));
		VNextEmailScreen emailscreen = invoicesscreen.clickOnInvoiceToEmail(invoicenumbertc48094);
		if (!emailscreen.getToEmailFieldValue().equals(VNextConfigInfo.getInstance().getOutlookMail()))
			emailscreen.sentToEmailAddress(VNextConfigInfo.getInstance().getOutlookMail());
		
		emailscreen.sendEmail();
		final String inspectionreportfilenname = invoicenumbertc48094 + ".pdf";
		EmailUtils emailUtils = new EmailUtils(EmailHost.OUTLOOK, VNextConfigInfo.getInstance().getOutlookMail(),
				VNextConfigInfo.getInstance().getUserCapiMailPassword(), EmailFolder.JUNK);

		EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
				.withSubjectAndAttachmentFileName(invoicenumbertc48094, inspectionreportfilenname)
				.unreadOnlyMessages(true).maxMessagesToSearch(5);
		Assert.assertTrue(emailUtils.waitForMessageWithSubjectAndDownloadAttachment(mailSearchParameters), "Can't find invoice: " + invoicenumbertc48094);

		File pdfdoc = new File(inspectionreportfilenname);
		String pdftext = PDFReader.getPDFText(pdfdoc);
		Assert.assertTrue(pdftext.contains("Dent Repair \n$0.98"));
		Assert.assertTrue(pdftext.contains("Bumper Repair $0.98 \n$1.96"));

	}
	
	@Test(testName= "Test Case 49787:vNext: Create Invoice with set PO", 
			description = "Create Invoice with set PO")
	public void testCreateInvoiceWithSetPO() {
		final String VIN = "19UUA66278A050105";
		final String _make = "Acura";
		final String _model = "TL";
		final String year = "2008";
		
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
		final String ponumber = "Edyfycydydyftwfxfxhxgdy2555*&*-\"4*-\"&\"-\"55$6'+'&*6";
		final String invoicenote = "Only text Note";
		final String wopriceexp = "$267.81";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextVehicleInfoScreen vehicleinfoscreen = homescreen.openCreateWOWizard(testcustomer);
		vehicleinfoscreen.setVIN(VIN);
		
		Assert.assertEquals(vehicleinfoscreen.getMakeInfo(), _make);
		Assert.assertEquals(vehicleinfoscreen.getModelInfo(), _model);
		Assert.assertEquals(vehicleinfoscreen.getYear(), year);
		vehicleinfoscreen.swipeScreenLeft();
		VNextAvailableServicesScreen servicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		servicesscreen.selectService(percservices);
		servicesscreen.selectService(moneyservices);
		VNextPriceMatrixesScreen pricematrixesscreen = servicesscreen.openMatrixServiceDetails(matrixservice);
		VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectPriceMatrix(matrixsubservice);
		VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartname);
		vehiclepartinfoscreen.selectVehiclePartSize(vehiclepartsize);
		vehiclepartinfoscreen.selectVehiclePartSeverity(vehiclepartseverity);
		vehiclepartinfoscreen.selectVehiclePartAdditionalService(additionalservice);
		vehiclepartinfoscreen.clickSaveVehiclePartInfo();
		vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);
		servicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();
		VNextSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesView();
		Assert.assertEquals(selectedServicesScreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixservice), matrixsubservice);

		selectedServicesScreen.setServiceAmountValue(moneyservices, moneyserviceprice);
		selectedServicesScreen.setServiceQuantityValue(moneyservices, moneyservicequant);

		final String wonumber = selectedServicesScreen.getNewInspectionNumber();
		VNextWorkOrdersScreen workordersscreen = selectedServicesScreen.saveWorkOrderViaMenu();
		final String woprice = workordersscreen.getWorkOrderPriceValue(wonumber);
		Assert.assertEquals(woprice, wopriceexp);
		workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.setInvoicePONumber(ponumber);
		invoiceinfoscreen.addTextNoteToInvoice(invoicenote);
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoice();
		Assert.assertEquals(invoicesscreen.getInvoicePriceValue(invoicenumber), woprice);
		homescreen = invoicesscreen.clickBackButton();
		
	}
	
	@Test(testName= "Test Case 52817:vNext mobile: create and email Invoice with two matrix panel", 
			description = "Create and email Invoice with two matrix panel")
	@Parameters({ "usercapi.mail"})
	public void testCreateAndEmailInvoiceWithTwoMatrixPanel(String usermail) {
		final String VIN = "JT3AC11R4N1023558";
		final String _make = "Toyota";
		final String _model = "Previa";
		final String year = "1992";
		
		final String matrixservice = "Hail Dent Repair";
		final String matrixsubservice = "State Farm";
		final String[] vehiclepartsname = { "Hood", "Roof" };
		final String vehiclepartsize = "Dime";	
		final String vehiclepartseverity = "Light 6 to 15";	
		final String ponumber = "123po";
		final String insppriceexp = "$660.00";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextVehicleInfoScreen vehicleinfoscreen = homescreen.openCreateWOWizard(testcustomer);
		vehicleinfoscreen.setVIN(VIN);
		
		Assert.assertEquals(vehicleinfoscreen.getMakeInfo(), _make);
		Assert.assertEquals(vehicleinfoscreen.getModelInfo(), _model);
		Assert.assertEquals(vehicleinfoscreen.getYear(), year);
		vehicleinfoscreen.swipeScreenLeft();
		VNextAvailableServicesScreen servicesscreen = new VNextAvailableServicesScreen(appiumdriver);;
		VNextPriceMatrixesScreen pricematrixesscreen = servicesscreen.openMatrixServiceDetails(matrixservice);
		VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectPriceMatrix(matrixsubservice);
		for (String vehiclepartname : vehiclepartsname) {
			VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartname);
			vehiclepartinfoscreen.selectVehiclePartSize(vehiclepartsize);
			vehiclepartinfoscreen.selectVehiclePartSeverity(vehiclepartseverity);
			vehiclepartinfoscreen.selectAllAvailableAdditionalServices();
			vehiclepartinfoscreen.clickSaveVehiclePartInfo();
			vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);

		}
		servicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();
		VNextSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesView();
		Assert.assertEquals(selectedServicesScreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixservice), matrixsubservice);
		//final String wonumber = servicesscreen.getNewInspectionNumber();
		selectedServicesScreen.swipeScreenLeft();
		VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
		wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.setInvoicePONumber(ponumber);
		String invoicenumber  = invoiceinfoscreen.getInvoiceNumber();
		VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoice();
		Assert.assertEquals(invoicesscreen.getInvoicePriceValue(invoicenumber), insppriceexp);
		VNextEmailScreen emailscreen = invoicesscreen.clickOnInvoiceToEmail(invoicenumber);
		if (!emailscreen.getToEmailFieldValue().equals(usermail))
			emailscreen.sentToEmailAddress(usermail);
		
		emailscreen.sendEmail();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.clickBackButton();		
	}
	
	@Test(testName= "Test Case 49221:vNext - Verify Invoice can be created from WO Wizard", 
			description = "Verify Invoice can be created from WO Wizard")
	public void testVerifyInvoiceCanBeCreatedFromWOWizard() {
		final String VIN = "19UUA66278A050105";
		final String percservices = "Large Vehicle Upcharge"; 
		final String moneyservices = "Bumper Repair"; 		
		final String ponumber = "123po";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextVehicleInfoScreen vehicleinfoscreen = homescreen.openCreateWOWizard(testcustomer);
		vehicleinfoscreen.setVIN(VIN);
		vehicleinfoscreen.clickScreenForwardButton();
		VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(appiumdriver);
		vehicleVINHistoryScreen.clickBackButton();
		vehicleinfoscreen.swipeScreenLeft();
		VNextAvailableServicesScreen servicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		servicesscreen.selectService(moneyservices);
		servicesscreen.selectService(percservices);

		servicesscreen.swipeScreenLeft();
		VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
		wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
		
		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.setInvoicePONumber(ponumber);		
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
		Assert.assertTrue(invoicesscreen.isInvoiceExists(invoicenumber));
		homescreen = invoicesscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 49436:vNext - Verify Invoice can be created from WO menu", 
			description = "Verify Invoice can be created from WO menu")
	public void testVerifyInvoiceCanBeCreatedFromWOMenu() {
		final String VIN = "19UUA66278A050105";
		final String percservices = "Large Vehicle Upcharge"; 
		final String moneyservices = "Bumper Repair"; 		
		final String ponumber = "123po";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextVehicleInfoScreen vehicleinfoscreen = homescreen.openCreateWOWizard(testcustomer);
		vehicleinfoscreen.setVIN(VIN);
		vehicleinfoscreen.clickScreenForwardButton();
		VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(appiumdriver);
		vehicleVINHistoryScreen.clickBackButton();
		final String wonumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreenLeft();
		VNextAvailableServicesScreen servicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		servicesscreen.selectService(moneyservices);
		servicesscreen.selectService(percservices);
		servicesscreen.swipeScreenLeft();
		VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
		wosummaryscreen.clickWorkOrderSaveButton();
		VNextWorkOrdersScreen workordersscreen = new VNextWorkOrdersScreen(appiumdriver);
		workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.setInvoicePONumber(ponumber);		
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
		Assert.assertTrue(invoicesscreen.isInvoiceExists(invoicenumber));
		homescreen = invoicesscreen.clickBackButton();
	}

	@Test(testName= "Test Case 38638:vNext: Verify Customer Info on Invoice detail", 
			description = "Verify Customer Info on Invoice detail",
	dependsOnMethods = { "testCreateInvoiceWhichContainsPriceServicesWithDecimalQuantity" })
	@Parameters({ "backofficecapi.url", "usercapi.name", "usercapi.psw"})
	public void testVerifyCustomerInfoOnInvoiceDetail(String bourl, String username, String userpsw) {
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(bourl);
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(username, userpsw);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInvoicesWebPage invoiceswebpage = leftmenu.selectInvoicesMenu();
		invoiceswebpage.selectInvoiceInTheList(invoicenumbertc48094);
		Assert.assertEquals(invoiceswebpage.getSelectedInvoiceCustomerName(), testcustomer);		
	}
	
	@Test(testName= "Test Case 38642:vNext: Verify text note on Invoice detail", 
			description = "Verify text note on Invoice detail",
	dependsOnMethods = { "testCreateInvoiceWithTextNote" })
	@Parameters({ "backofficecapi.url", "usercapi.name", "usercapi.psw"})
	public void testVerifyTextNoteOnInvoiceDetail(String bourl, String username, String userpsw) {
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(bourl);
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(username, userpsw);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInvoicesWebPage invoiceswebpage = leftmenu.selectInvoicesMenu();
		invoiceswebpage.selectInvoiceInTheList(invoicenumbertc46415);
		Assert.assertEquals(invoiceswebpage.getSelectedInvoiceNote(), notetext);		
	}
	
	public static String unEscapeString(String s){
	    StringBuilder sb = new StringBuilder();
	    for (int i=0; i<s.length(); i++)
	        switch (s.charAt(i)){
	            case '\n': sb.append("\\n"); break;
	            case '\t': sb.append("\\t"); break;
	            case '\r': sb.append("\\r"); break;
	            case '\f': sb.append("\\f"); break;
	            // ... rest of escape characters
	            default: sb.append(s.charAt(i));
	        }
	    return sb.toString();
	}

}
