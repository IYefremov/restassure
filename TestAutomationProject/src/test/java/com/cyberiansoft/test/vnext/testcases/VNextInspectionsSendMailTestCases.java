package com.cyberiansoft.test.vnext.testcases;

import java.io.File;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cyberiansoft.test.ios_client.utils.MailChecker;
import com.cyberiansoft.test.ios_client.utils.PDFReader;
import com.cyberiansoft.test.vnext.config.VNextConfigInfo;
import com.cyberiansoft.test.vnext.screens.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextEmailScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.VNextNewCustomerScreen;
import com.cyberiansoft.test.vnext.screens.VNextPriceMatrixesScreen;
import com.cyberiansoft.test.vnext.screens.VNextSelectServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartInfoPage;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartsScreen;

public class VNextInspectionsSendMailTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {
	
	final String customerFirstName = "Customer";
	final String customerLastName = "MailInspection";
	final String customerCompanyName = "CompanyMailInspection";
	final String customerAddress1 = "Test Address Street, 1";	
	final String customerAddress2 = "Addreess2";
	final String customereMail = "test.cyberiansoft@gmail.com";
	final String customerPhone = "444-51-09";
	final String customerCity = "Lviv";
	final String customerCountry = "Mexico";
	final String customerState = "Colima";
	final String customerZIP = "79031";
	final String customerstateShort = "CL";
	
	@Test(testName= "Test Case 66998:Verify correct Customer Info is shown on Printing", 
			description = "Verify correct Customer Info is shown on Printing")
	public void testVerifyCorrectCustomerInfoIsShownOnPrinting() throws IOException {

		final String inspType = "anastasia type";
		final String vinnumber = "TEST";
		String customer = customerFirstName + " " + customerLastName;
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		if (!customersscreen.isCustomerExists(customer)) {
			VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
			newcustomerscreen.createNewCustomer(customerFirstName, customerLastName, customerCompanyName, customereMail, 
					customerPhone, customerAddress1, customerAddress2, customerCity, customerCountry, customerState, customerZIP);
		} else
			customersscreen.selectCustomer(customer);
		//VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		//insptypeslist.selectInspectionType(inspType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();		
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		
		VNextEmailScreen emailscreen = inspectionscreen.clickOnInspectionToEmail(inspnumber);
		emailscreen.sentToEmailAddress(customereMail);
		emailscreen.clickSendEmailsButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		inspectionscreen.clickBackButton();
		
	
		boolean search = false;
		final String inspectionreportfilenname = inspnumber + ".pdf";
		for (int i= 0; i < 5; i++) {
			if (!MailChecker.searchEmailAndGetAttachment(VNextConfigInfo.getInstance().getUserCapiMail(),
					VNextConfigInfo.getInstance().getUserCapiUserPassword(), 
					"Estimate #" + inspnumber + " from", "Repair360@cyberiansoft.com", 
					inspectionreportfilenname)) {
				homescreen.waitABit(30*1000); 
			} else {
				
				search = true;
				break;
			}
		}
		if (search) {
			File pdfdoc = new File(inspectionreportfilenname);
			String pdftext = PDFReader.getPDFText(pdfdoc);
			Assert.assertTrue(pdftext.contains(customer));
			Assert.assertTrue(pdftext.contains(customerAddress1));
			Assert.assertTrue(pdftext.contains(customerAddress2));
			Assert.assertTrue(pdftext.contains(customerCity));
			Assert.assertTrue(pdftext.contains(customerZIP));
			Assert.assertTrue(pdftext.contains(", " + customerstateShort));
		} else {
			Assert.assertTrue(search, "Can't find email with " + inspnumber + " inspection");
		}
	}
	
	@Test(testName= "Test Case 66999:Verify 'Back' button doesn't save info for Vehicle Part - Printing", 
			description = "Verify 'Back' button doesn't save info for Vehicle Part - Printing")
	public void testVerifyBackButtonDoesntSaveInfoForVehiclePartPrinting() throws IOException {

		final String vinnumber = "TEST";
		String customer = customerFirstName + " " + customerLastName;
		
		final String matrixservice = "Hail Dent Repair";
		final String pricematrix = "Progressive";
		final String vehiclepartname = "Left Fender";
		final String vehiclepartname2 = "Trunk Lid";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		if (!customersscreen.isCustomerExists(customer)) {
			VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
			newcustomerscreen.createNewCustomer(customerFirstName, customerLastName, customerCompanyName, customereMail, 
					customerPhone, customerAddress1, customerAddress2, customerCity, customerCountry, customerState, customerZIP);
		} else
			customersscreen.selectCustomer(customer);
		//VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		//insptypeslist.selectInspectionType(inspType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();	
		VNextInspectionServicesScreen servicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = servicesscreen.clickAddServicesButton();
		VNextPriceMatrixesScreen pricematrixesscreen = selectservicesscreen.openMatrixServiceDetails(matrixservice);
		VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectPriceMatrix(pricematrix);
		pricematrixesscreen.clickScreenBackButton();
		selectservicesscreen = new VNextSelectServicesScreen(appiumdriver);
		vehiclepartsscreen = selectservicesscreen.openSelectedMatrixServiceDetails(matrixservice);
		
		VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartname);
		vehiclepartinfoscreen.clickScreenBackButton();
		vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);

		vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartname2);
		vehiclepartinfoscreen.clickScreenBackButton();
		vehiclepartinfoscreen.clickScreenBackButton();
		selectservicesscreen = new VNextSelectServicesScreen(appiumdriver);
		selectservicesscreen.clickSaveSelectedServicesButton();
		servicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inspectionscreen = servicesscreen.saveInspectionViaMenu();
		
		VNextEmailScreen emailscreen = inspectionscreen.clickOnInspectionToEmail(inspnumber);
		emailscreen.sentToEmailAddress(customereMail);
		emailscreen.clickSendEmailsButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		inspectionscreen.clickBackButton();
		
		boolean search = false;
		final String inspectionreportfilenname = inspnumber + ".pdf";
		for (int i= 0; i < 5; i++) {
			if (!MailChecker.searchEmailAndGetAttachment(VNextConfigInfo.getInstance().getUserCapiMail(),
					VNextConfigInfo.getInstance().getUserCapiUserPassword(), 
					"Estimate #" + inspnumber + " from", "Repair360@cyberiansoft.com", 
					inspectionreportfilenname)) {
				homescreen.waitABit(30*1000); 
			} else {
				
				search = true;
				break;
			}
		}
		if (search) {
			File pdfdoc = new File(inspectionreportfilenname);
			String pdftext = PDFReader.getPDFText(pdfdoc);
			Assert.assertTrue(pdftext.contains(customer));
			Assert.assertTrue(pdftext.contains(customerAddress1));
			Assert.assertTrue(pdftext.contains(customerAddress2));
			Assert.assertTrue(pdftext.contains(customerCity));
			Assert.assertTrue(pdftext.contains(customerZIP));
			Assert.assertTrue(pdftext.contains(", " + customerstateShort));
			Assert.assertTrue(pdftext.contains(matrixservice));
			Assert.assertFalse(pdftext.contains(vehiclepartname));
			Assert.assertFalse(pdftext.contains(vehiclepartname2));
		} else {
			Assert.assertTrue(search, "Can't find email with " + inspnumber + " inspection");
		}
	}
	
	@Test(testName= "Test Case 67000:Verify hardware 'Back' button doesn't save info for Vehicle Part - Printing", 
			description = "Verify hardware 'Back' button doesn't save info for Vehicle Part - Printing")
	public void testVerifyHardwareBackButtonDoesntSaveInfoForVehiclePartPrinting() throws IOException {

		final String vinnumber = "TEST";
		String customer = customerFirstName + " " + customerLastName;
		
		final String matrixservice = "Hail Dent Repair";
		final String pricematrix = "Progressive";
		final String vehiclepartname = "Left Fender";
		final String vehiclepartname2 = "Trunk Lid";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		if (!customersscreen.isCustomerExists(customer)) {
			VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
			newcustomerscreen.createNewCustomer(customerFirstName, customerLastName, customerCompanyName, customereMail, 
					customerPhone, customerAddress1, customerAddress2, customerCity, customerCountry, customerState, customerZIP);
		} else
			customersscreen.selectCustomer(customer);
		//VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		//insptypeslist.selectInspectionType(inspType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();	
		VNextInspectionServicesScreen servicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = servicesscreen.clickAddServicesButton();
		VNextPriceMatrixesScreen pricematrixesscreen = selectservicesscreen.openMatrixServiceDetails(matrixservice);
		VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectPriceMatrix(pricematrix);
		pricematrixesscreen.clickScreenBackButton();
		selectservicesscreen = new VNextSelectServicesScreen(appiumdriver);
		vehiclepartsscreen = selectservicesscreen.openSelectedMatrixServiceDetails(matrixservice);
		
		VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartname);
		vehiclepartinfoscreen.clickHardwareBackButton();
		vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);

		vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartname2);
		vehiclepartinfoscreen.clickHardwareBackButton();
		vehiclepartinfoscreen.clickHardwareBackButton();
		selectservicesscreen = new VNextSelectServicesScreen(appiumdriver);
		selectservicesscreen.clickSaveSelectedServicesButton();
		servicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inspectionscreen = servicesscreen.saveInspectionViaMenu();
		
		VNextEmailScreen emailscreen = inspectionscreen.clickOnInspectionToEmail(inspnumber);
		emailscreen.sentToEmailAddress(customereMail);
		emailscreen.clickSendEmailsButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		inspectionscreen.clickBackButton();
		
		boolean search = false;
		final String inspectionreportfilenname = inspnumber + ".pdf";
		for (int i= 0; i < 5; i++) {
			if (!MailChecker.searchEmailAndGetAttachment(VNextConfigInfo.getInstance().getUserCapiMail(),
					VNextConfigInfo.getInstance().getUserCapiUserPassword(), 
					"Estimate #" + inspnumber + " from", "Repair360@cyberiansoft.com", 
					inspectionreportfilenname)) {
				homescreen.waitABit(30*1000); 
			} else {
				
				search = true;
				break;
			}
		}
		if (search) {
			File pdfdoc = new File(inspectionreportfilenname);
			String pdftext = PDFReader.getPDFText(pdfdoc);
			Assert.assertTrue(pdftext.contains(customer));
			Assert.assertTrue(pdftext.contains(customerAddress1));
			Assert.assertTrue(pdftext.contains(customerAddress2));
			Assert.assertTrue(pdftext.contains(customerCity));
			Assert.assertTrue(pdftext.contains(customerZIP));
			Assert.assertTrue(pdftext.contains(", " + customerstateShort));
			Assert.assertTrue(pdftext.contains(matrixservice));
			Assert.assertFalse(pdftext.contains(vehiclepartname));
			Assert.assertFalse(pdftext.contains(vehiclepartname2));
		} else {
			Assert.assertTrue(search, "Can't find email with " + inspnumber + " inspection");
		}
	}

	@Test(testName= "Test Case 67001:PRINT - Validate 'Quantity' column is shown on device printout form for money services", 
			description = "Validate 'Quantity' column is shown on device printout form for money services")
	public void testValidateQuantityColumnIsShownOnDevicePrintoutFormForMoneyServices() throws IOException {

		final String vinnumber = "TEST";
		String customer = customerFirstName + " " + customerLastName;
		
		final String[] moneyservices = { "Dent Repair", "Bumper Repair" };
		final String[] moneyservicesprices = { "10", "0.99" };
		final String[] moneyservicesquantities = { "0.01", "0.99" };
		final String[] moneyservicesamounts = { "$0.10", "$0.98" };
		final String total = "$1.08";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		if (!customersscreen.isCustomerExists(customer)) {
			VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
			newcustomerscreen.createNewCustomer(customerFirstName, customerLastName, customerCompanyName, customereMail, 
					customerPhone, customerAddress1, customerAddress2, customerCity, customerCountry, customerState, customerZIP);
		} else
			customersscreen.selectCustomer(customer);
		//VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		//insptypeslist.selectInspectionType(inspType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();	
		VNextInspectionServicesScreen servicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = servicesscreen.clickAddServicesButton();
		selectservicesscreen.selectServices(moneyservices);
		selectservicesscreen.clickSaveSelectedServicesButton();
		
		servicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		for (int i = 0; i < moneyservices.length; i++) {
			servicesscreen.setServiceAmountValue(moneyservices[i], moneyservicesprices[i]);
			servicesscreen.setServiceQuantityValue(moneyservices[i], moneyservicesquantities[i]);
		}
		
		inspectionscreen = servicesscreen.saveInspectionViaMenu();
		//inspectionscreen.switchToTeamInspectionsView();
		VNextEmailScreen emailscreen = inspectionscreen.clickOnInspectionToEmail(inspnumber);
		emailscreen.sentToEmailAddress(customereMail);
		emailscreen.clickSendEmailsButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		inspectionscreen.clickBackButton();
		
		boolean search = false;
		final String inspectionreportfilenname = inspnumber + ".pdf";
		for (int i= 0; i < 5; i++) {
			if (!MailChecker.searchEmailAndGetAttachment(VNextConfigInfo.getInstance().getUserCapiMail(),
					VNextConfigInfo.getInstance().getUserCapiUserPassword(), 
					"Estimate #" + inspnumber + " from", "Repair360@cyberiansoft.com", 
					inspectionreportfilenname)) {
				homescreen.waitABit(30*1000); 
			} else {
				
				search = true;
				break;
			}
		}
		if (search) {
			File pdfdoc = new File(inspectionreportfilenname);
			String pdftext = PDFReader.getPDFText(pdfdoc);
			for (int i = 0; i < moneyservices.length; i++) {
				Assert.assertTrue(pdftext.contains(moneyservices[i]));
				Assert.assertTrue(pdftext.contains(moneyservicesquantities[i]));
				Assert.assertTrue(pdftext.contains(moneyservicesamounts[i]));
			}
			Assert.assertTrue(pdftext.contains(total));
		} else {
			Assert.assertTrue(search, "Can't find email with " + inspnumber + " inspection");
		}
	}
}