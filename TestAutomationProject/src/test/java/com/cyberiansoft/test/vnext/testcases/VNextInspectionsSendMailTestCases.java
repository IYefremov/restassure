package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.email.EmailUtils;
import com.cyberiansoft.test.email.emaildata.EmailFolder;
import com.cyberiansoft.test.email.emaildata.EmailHost;
import com.cyberiansoft.test.ios10_client.utils.PDFReader;
import com.cyberiansoft.test.vnext.config.VNextConfigInfo;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

public class VNextInspectionsSendMailTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {
	
	
	
	RetailCustomer testcustomer = new RetailCustomer("Customer", "MailInspection");
	final String customerstateShort = "CL";
	
	@BeforeMethod
	public void initTestCustomer() {
		testcustomer.setCompanyName("CompanyMailInspection");
		testcustomer.setMailAddress("test.cyberiansoft@gmail.com");
		testcustomer.setCustomerAddress1("Test Address Street, 1");
		testcustomer.setCustomerAddress2("Addreess2");
		testcustomer.setCustomerPhone("444-51-09");
		testcustomer.setCustomerCity("Lviv");
		testcustomer.setCustomerCountry("Mexico");
		testcustomer.setCustomerState("Colima");
		testcustomer.setCustomerZip("79031");		
	}
	
	@Test(testName= "Test Case 66998:Verify correct Customer Info is shown on Printing", 
			description = "Verify correct Customer Info is shown on Printing")
	public void testVerifyCorrectCustomerInfoIsShownOnPrinting() throws Exception {

		final String vinnumber = "TEST";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		if (!customersscreen.isCustomerExists(testcustomer)) {
			VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
			newcustomerscreen.createNewCustomer(testcustomer);
		} else
			customersscreen.selectCustomer(testcustomer);
		//VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		//insptypeslist.selectInspectionType(inspType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();		
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		
		VNextEmailScreen emailscreen = inspectionscreen.clickOnInspectionToEmail(inspnumber);
		emailscreen.sentToEmailAddress(VNextConfigInfo.getInstance().getOutlookMail());
		emailscreen.sendEmail();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		inspectionscreen.clickBackButton();

		final String inspectionreportfilenname = inspnumber + ".pdf";
		EmailUtils emailUtils = new EmailUtils(EmailHost.OUTLOOK, VNextConfigInfo.getInstance().getOutlookMail(),
				VNextConfigInfo.getInstance().getUserCapiMailPassword(), EmailFolder.JUNK);
		EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
				.withSubjectAndAttachmentFileName(inspnumber, inspectionreportfilenname).unreadOnlyMessages(true).maxMessagesToSearch(5);
		Assert.assertTrue(emailUtils.waitForMessageWithSubjectAndDownloadAttachment(mailSearchParameters), "Can't find inspection: " + inspnumber);

		File pdfdoc = new File(inspectionreportfilenname);
		String pdftext = PDFReader.getPDFText(pdfdoc);
		Assert.assertTrue(pdftext.contains(testcustomer.getFullName()));
		Assert.assertTrue(pdftext.contains(testcustomer.getCustomerAddress1()));
		Assert.assertTrue(pdftext.contains(testcustomer.getCustomerAddress2()));
		Assert.assertTrue(pdftext.contains(testcustomer.getCustomerCity()));
		Assert.assertTrue(pdftext.contains(testcustomer.getCustomerZip()));
		Assert.assertTrue(pdftext.contains(", " + customerstateShort));
	}
	
	@Test(testName= "Test Case 66999:Verify 'Back' button doesn't save info for Vehicle Part - Printing", 
			description = "Verify 'Back' button doesn't save info for Vehicle Part - Printing")
	public void testVerifyBackButtonDoesntSaveInfoForVehiclePartPrinting() throws Exception {

		final String vinnumber = "TEST";
		
		final String matrixservice = "Hail Dent Repair";
		final String pricematrix = "Progressive";
		final String vehiclepartname = "Left Fender";
		final String vehiclepartname2 = "Trunk Lid";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		if (!customersscreen.isCustomerExists(testcustomer)) {
			VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
			newcustomerscreen.createNewCustomer(testcustomer);
		} else
			customersscreen.selectCustomer(testcustomer);
		//VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		//insptypeslist.selectInspectionType(inspType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();	
		VNextInspectionServicesScreen servicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextPriceMatrixesScreen pricematrixesscreen = servicesscreen.openMatrixServiceDetails(matrixservice);
		VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectPriceMatrix(pricematrix);
		pricematrixesscreen.clickScreenBackButton();
		servicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		VNextSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesView();
		vehiclepartsscreen = selectedServicesScreen.openSelectedMatrixServiceDetails(matrixservice);
		
		VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartname);
		vehiclepartinfoscreen.clickScreenBackButton();
		vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);

		vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartname2);
		vehiclepartinfoscreen.clickScreenBackButton();
		vehiclepartinfoscreen.clickScreenBackButton();
		selectedServicesScreen = new VNextSelectedServicesScreen(appiumdriver);
		inspectionscreen = selectedServicesScreen.saveInspectionViaMenu();
		
		VNextEmailScreen emailscreen = inspectionscreen.clickOnInspectionToEmail(inspnumber);
		emailscreen.sentToEmailAddress(VNextConfigInfo.getInstance().getOutlookMail());
		emailscreen.sendEmail();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		inspectionscreen.clickBackButton();

		final String inspectionreportfilenname = inspnumber + ".pdf";
		EmailUtils emailUtils = new EmailUtils(EmailHost.OUTLOOK, VNextConfigInfo.getInstance().getOutlookMail(),
				VNextConfigInfo.getInstance().getUserCapiMailPassword(), EmailFolder.JUNK);
		EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
				.withSubjectAndAttachmentFileName(inspnumber, inspectionreportfilenname).unreadOnlyMessages(true).maxMessagesToSearch(5);
		Assert.assertTrue(emailUtils.waitForMessageWithSubjectAndDownloadAttachment(mailSearchParameters), "Can't find inspection: " + inspnumber);

		File pdfdoc = new File(inspectionreportfilenname);
		String pdftext = PDFReader.getPDFText(pdfdoc);
		Assert.assertTrue(pdftext.contains(testcustomer.getFullName()));
		Assert.assertTrue(pdftext.contains(testcustomer.getCustomerAddress1()));
		Assert.assertTrue(pdftext.contains(testcustomer.getCustomerAddress2()));
		Assert.assertTrue(pdftext.contains(testcustomer.getCustomerCity()));
		Assert.assertTrue(pdftext.contains(testcustomer.getCustomerZip()));
		Assert.assertTrue(pdftext.contains(", " + customerstateShort));
		Assert.assertTrue(pdftext.contains(matrixservice));
		Assert.assertFalse(pdftext.contains(vehiclepartname));
		Assert.assertFalse(pdftext.contains(vehiclepartname2));

	}
	
	@Test(testName= "Test Case 67000:Verify hardware 'Back' button doesn't save info for Vehicle Part - Printing", 
			description = "Verify hardware 'Back' button doesn't save info for Vehicle Part - Printing")
	public void testVerifyHardwareBackButtonDoesntSaveInfoForVehiclePartPrinting() throws Exception {

		final String vinnumber = "TEST";
		
		final String matrixservice = "Hail Dent Repair";
		final String pricematrix = "Progressive";
		final String vehiclepartname = "Left Fender";
		final String vehiclepartname2 = "Trunk Lid";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		if (!customersscreen.isCustomerExists(testcustomer)) {
			VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
			newcustomerscreen.createNewCustomer(testcustomer);
		} else
			customersscreen.selectCustomer(testcustomer);
		//VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		//insptypeslist.selectInspectionType(inspType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();	
		VNextInspectionServicesScreen servicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextPriceMatrixesScreen pricematrixesscreen = servicesscreen.openMatrixServiceDetails(matrixservice);
		VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectPriceMatrix(pricematrix);
		pricematrixesscreen.clickScreenBackButton();
		servicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		VNextSelectedServicesScreen selectservicesscreen = servicesscreen.switchToSelectedServicesView();
		vehiclepartsscreen = selectservicesscreen.openSelectedMatrixServiceDetails(matrixservice);
		
		VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartname);
		AppiumUtils.clickHardwareBackButton();
		vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);

		vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartname2);
		AppiumUtils.clickHardwareBackButton();
		AppiumUtils.clickHardwareBackButton();
		selectservicesscreen = new VNextSelectedServicesScreen(appiumdriver);
		inspectionscreen = selectservicesscreen.saveInspectionViaMenu();
		
		VNextEmailScreen emailscreen = inspectionscreen.clickOnInspectionToEmail(inspnumber);
		emailscreen.sentToEmailAddress(testcustomer.getMailAddress());
		emailscreen.sendEmail();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		inspectionscreen.clickBackButton();

		final String inspectionreportfilenname = inspnumber + ".pdf";
		EmailUtils emailUtils = new EmailUtils(EmailHost.OUTLOOK, VNextConfigInfo.getInstance().getOutlookMail(),
				VNextConfigInfo.getInstance().getUserCapiMailPassword(), EmailFolder.JUNK);
		EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
				.withSubjectAndAttachmentFileName(inspnumber, inspectionreportfilenname).unreadOnlyMessages(true).maxMessagesToSearch(5);
		Assert.assertTrue(emailUtils.waitForMessageWithSubjectAndDownloadAttachment(mailSearchParameters), "Can't find inspection: " + inspnumber);

		File pdfdoc = new File(inspectionreportfilenname);
		String pdftext = PDFReader.getPDFText(pdfdoc);
		Assert.assertTrue(pdftext.contains(testcustomer.getFullName()));
		Assert.assertTrue(pdftext.contains(testcustomer.getCustomerAddress1()));
		Assert.assertTrue(pdftext.contains(testcustomer.getCustomerAddress2()));
		Assert.assertTrue(pdftext.contains(testcustomer.getCustomerCity()));
		Assert.assertTrue(pdftext.contains(testcustomer.getCustomerZip()));
		Assert.assertTrue(pdftext.contains(", " + customerstateShort));
		Assert.assertTrue(pdftext.contains(matrixservice));
		Assert.assertFalse(pdftext.contains(vehiclepartname));
		Assert.assertFalse(pdftext.contains(vehiclepartname2));
	}

	@Test(testName= "Test Case 67001:PRINT - Validate 'Quantity' column is shown on device printout form for money services", 
			description = "Validate 'Quantity' column is shown on device printout form for money services")
	public void testValidateQuantityColumnIsShownOnDevicePrintoutFormForMoneyServices() throws Exception {

		final String vinnumber = "TEST";
		
		final String[] moneyservices = { "Dent Repair", "Bumper Repair" };
		final String[] moneyservicesprices = { "10", "0.99" };
		final String[] moneyservicesquantities = { "0.01", "0.99" };
		final String[] moneyservicesamounts = { "$0.10", "$0.98" };
		final String total = "$1.08";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		if (!customersscreen.isCustomerExists(testcustomer)) {
			VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
			newcustomerscreen.createNewCustomer(testcustomer);
		} else
			customersscreen.selectCustomer(testcustomer);
		//VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		//insptypeslist.selectInspectionType(inspType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();	
		VNextInspectionServicesScreen servicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		servicesscreen.selectServices(moneyservices);
		VNextSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesView();
		for (int i = 0; i < moneyservices.length; i++) {
			selectedServicesScreen.setServiceAmountValue(moneyservices[i], moneyservicesprices[i]);
			selectedServicesScreen.setServiceQuantityValue(moneyservices[i], moneyservicesquantities[i]);
		}
		
		inspectionscreen = selectedServicesScreen.saveInspectionViaMenu();
		//inspectionscreen.switchToTeamInspectionsView();
		VNextEmailScreen emailscreen = inspectionscreen.clickOnInspectionToEmail(inspnumber);
		emailscreen.sentToEmailAddress(testcustomer.getMailAddress());
		emailscreen.sendEmail();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		inspectionscreen.clickBackButton();

		final String inspectionreportfilenname = inspnumber + ".pdf";
		EmailUtils emailUtils = new EmailUtils(EmailHost.OUTLOOK, VNextConfigInfo.getInstance().getOutlookMail(),
				VNextConfigInfo.getInstance().getUserCapiMailPassword(), EmailFolder.JUNK);
		EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
				.withSubjectAndAttachmentFileName(inspnumber, inspectionreportfilenname).unreadOnlyMessages(true).maxMessagesToSearch(5);
		Assert.assertTrue(emailUtils.waitForMessageWithSubjectAndDownloadAttachment(mailSearchParameters), "Can't find inspection: " + inspnumber);

		File pdfdoc = new File(inspectionreportfilenname);
		String pdftext = PDFReader.getPDFText(pdfdoc);
		for (int i = 0; i < moneyservices.length; i++) {
			Assert.assertTrue(pdftext.contains(moneyservices[i]));
			Assert.assertTrue(pdftext.contains(moneyservicesquantities[i]));
			Assert.assertTrue(pdftext.contains(moneyservicesamounts[i]));
		}
		Assert.assertTrue(pdftext.contains(total));
	}
}
