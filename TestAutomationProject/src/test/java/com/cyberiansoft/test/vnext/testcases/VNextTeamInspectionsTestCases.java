package com.cyberiansoft.test.vnext.testcases;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.cyberiansoft.test.bo.config.BOConfigInfo;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ClientsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.InspectionsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.OperationsWebPage;
import com.cyberiansoft.test.vnext.config.VNextConfigInfo;
import com.cyberiansoft.test.vnext.screens.VNextApproveScreen;
import com.cyberiansoft.test.vnext.screens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextEmailScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextInspectionServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.VNextInvoiceInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextNotesScreen;
import com.cyberiansoft.test.vnext.screens.VNextSelectServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextServiceDetailsScreen;
import com.cyberiansoft.test.vnext.screens.VNextSettingsScreen;
import com.cyberiansoft.test.vnext.screens.VNextStatusScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextViewScreen;
import com.cyberiansoft.test.vnext.screens.VNextWorkOrderSummaryScreen;
import com.cyberiansoft.test.vnext.utils.VNextInspectionStatuses;


public class VNextTeamInspectionsTestCases extends BaseTestCaseTeamEditionRegistration {
	
	@Test(testName= "Test Case 64494:Verify user can approve Invoice after creating, "
			+ "Test Case 64497:Verify user can create Invoice from Inspection, "
			+ "Test Case 64266:Verify user can create Invoice in status 'New'", 
			description = "Verify user can approve Invoice after creating, "
					+ "Verify user can create Invoice from Inspection, "
					+ "Verify user can create Invoice in status 'New'")
	public void testVerifyUserCanCreateInvoiceFromInspections() {
		
		final String wholesalecustomer = "001 - Test Company";
		final String inspType = "Insp_type_approv_req";
		final String woType = "All_auto_Phases";
		final String invoiceType = "O_Kramar2";
		final String vinnumber = "TEST";
		final String ponumber = "12345";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		final String inspnumber = createSimpleInspection(wholesalecustomer, inspType, vinnumber);
		VNextInspectionsScreen inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), VNextInspectionStatuses.NEW);
		VNextInspectionsMenuScreen inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		VNextApproveScreen approvescreen = inspmenuscreen.clickApproveInspectionMenuItem();
		approvescreen.drawSignature();
		Assert.assertTrue(approvescreen.isClearButtonVisible());
		approvescreen.saveApprovedInspection();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), VNextInspectionStatuses.APPROVED);
		inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspmenuscreen.clickCreateWorkOrderInspectionMenuItem();
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(woType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.changeScreen("Summary");
		VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
		wosummaryscreen.clickCreateInvoiceOption();
		wosummaryscreen.clickWorkOrderSaveButton();
		insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(invoiceType);
		
		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.setInvoicePONumber(ponumber);
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoice();
		Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.NEW);
		
		homescreen = invoicesscreen.clickBackButton();
	}

	
	@Test(testName= "Test Case 66276:Verify Team Inspection displays on the screen", 
			description = "Verify Team Inspection displays on the screen")
	public void testVerifyTeamInspectionDisplaysOnTheScreen() {
		
		final String wholesalecustomer = "Test Test";
		final String inspType = "anastasia type";
		final String vinnumber = "TEST";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(wholesalecustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(inspType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		
		inspectionscreen.switchToTeamInspectionsView();
		Assert.assertTrue(inspectionscreen.isTeamInspectionsViewActive());
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));
		inspectionscreen.switchToMyInspectionsView();
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 66286:Verify when user go back from inspections screen to Home we save last selected mode", 
			description = "Verify when user go back from inspections screen to Home we save last selected mode")
	public void testVerifyWhenUserGoBackFromInspectionsScreenToHomeWeSaveLastSelectedMode() {

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		inspectionscreen.switchToTeamInspectionsView();
		Assert.assertTrue(inspectionscreen.isTeamInspectionsViewActive());
		homescreen = inspectionscreen.clickBackButton();
		inspectionscreen = homescreen.clickInspectionsMenuItem();
		Assert.assertTrue(inspectionscreen.isTeamInspectionsViewActive());
		inspectionscreen.switchToMyInspectionsView();
		Assert.assertTrue(inspectionscreen.isMyInspectionsViewActive());
		homescreen = inspectionscreen.clickBackButton();
		inspectionscreen = homescreen.clickInspectionsMenuItem();
		Assert.assertTrue(inspectionscreen.isMyInspectionsViewActive());
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 66295:Verify user can create Inspection without Team Sharing", 
			description = "Verify user can create Inspection without Team Sharing")
	public void testVerifyUserCanCreateInspectionWithoutTeamSharing() {
		
		final String wholesalecustomer = "001 - Test Company";
		final String inspType = "Anastasia_team";
		final String vinnumber = "TEST";
		final String insuranceCompany = "Oranta";
		final String claimNumber = "123";
		final String policyNumber = "099";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		inspectionscreen.switchToMyInspectionsView();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(wholesalecustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(inspType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreenLeft();
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		claiminfoscreen.selectInsuranceCompany(insuranceCompany);
		claiminfoscreen.setClaimNumber(claimNumber);
		claiminfoscreen.setPolicyNumber(policyNumber);
		
		inspectionscreen = claiminfoscreen.saveInspectionViaMenu();
		
		inspectionscreen.switchToTeamInspectionsView();
		Assert.assertTrue(inspectionscreen.isTeamInspectionsViewActive());
		System.out.println("+++++" + inspnumber);
		Assert.assertFalse(inspectionscreen.isInspectionExists(inspnumber));
		inspectionscreen.switchToMyInspectionsView();
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 66284:Verify only when user tap 'search' button we perform search and refresh team inspections list", 
			description = "Verify only when user tap 'search' button we perform search and refresh team inspections list")
	public void testVerifyOnlyWhenUserTapSearchButtonWePerformSearchAndRefreshTeamInspectionsList() {
		
		final String wholesalecustomer = "001 - Test Company";
		final String inspType = "anastasia type";
		final String vinnumber = "TEST";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(wholesalecustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(inspType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		
		inspectionscreen.switchToTeamInspectionsView();
		Assert.assertTrue(inspectionscreen.isTeamInspectionsViewActive());
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));
		inspectionscreen.searchInpectionByFreeText(wholesalecustomer);
		Assert.assertTrue(inspectionscreen.getNumberOfInspectionsOnTheScreen() <= VNextInspectionsScreen.MAX_NUMBER_OF_INPECTIONS);
		List<WebElement> inspections = inspectionscreen.getInspectionsList();
		for (WebElement inspcell : inspections) {
			Assert.assertEquals(inspectionscreen.getInspectionCustomerValue(inspcell), wholesalecustomer);
		}
		final String inspSubNumber = inspnumber.substring(6, inspnumber.length());
		inspectionscreen.setSearchText(inspSubNumber);
		Assert.assertTrue(inspectionscreen.getNumberOfInspectionsOnTheScreen() <= VNextInspectionsScreen.MAX_NUMBER_OF_INPECTIONS);
		inspections = inspectionscreen.getInspectionsList();
		for (WebElement inspcell : inspections) {
			Assert.assertTrue(inspectionscreen.getInspectionNumberValue(inspcell).contains(inspSubNumber));
		}
		
		inspectionscreen.clickCancelSearchButton();
		Assert.assertTrue(inspectionscreen.getNumberOfInspectionsOnTheScreen() <= VNextInspectionsScreen.MAX_NUMBER_OF_INPECTIONS);
		
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 66858:Verify user can view Team Inspection", 
			description = "Verify user can view Team Inspection")
	public void testVerifyUserCanViewTeamInspection() {
		
		final String wholesalecustomer = "001 - Test Company";
		final String inspType = "anastasia type";
		final String vinnumber = "TEST";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(wholesalecustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(inspType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		
		inspectionscreen.switchToTeamInspectionsView();
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));
		VNextInspectionsMenuScreen inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);		
		VNextViewScreen viewscreen = inspmenuscreen.clickViewInspectionMenuItem();
		viewscreen.clickScreenBackButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 67290:Verify user can create Team Inspection, "
			+ "Test Case 67291:Verify Team Inspection save into mobile device and BO immediately if internet connection is available", 
			description = "Verify user can create Team Inspection, "
					+ "Verify Team Inspection save into mobile device and BO immediately if internet connection is available")
	public void testVerifyUserCanCreateTeamInspection() {
		
		final String wholesalecustomer = "001 - Test Company";
		final String inspType = "O_Kramar";
		final String vinnumber = "123";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();		
		inspectionscreen.switchToTeamInspectionsView();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(wholesalecustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(inspType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));			
		homescreen = inspectionscreen.clickBackButton();
		Assert.assertFalse(homescreen.isQueueMessageVisible());
	}
	
	@Test(testName= "Test Case 67292:Verify Team Inspection saved into mobile deviceand BO later via outgoing message if there is no connection, "
			+ "Test Case 67299:Verify Inspection displays on the list after DB update and after reconnect Internet", 
			description = "Verify Team Inspection saved into mobile deviceand BO later via outgoing message if there is no connection, "
					+ "Verify Inspection displays on the list after DB update and after reconnect Internet")
	public void testVerifyTeamInspectionSavedIntoMobileDeviceAndBOLaterViaOutgoingMessageIfThereIsNoConnection() {
		
		final String wholesalecustomer = "001 - Test Company";
		final String inspType = "O_Kramar";
		final String vinnumber = "123";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		setNetworkOff();
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();		
		inspectionscreen.switchToTeamInspectionsView();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogOKButton();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(wholesalecustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(inspType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.clickSaveInspectionMenuButton();
		informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogOKButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
	
		homescreen = inspectionscreen.clickBackButton();
		Assert.assertEquals(homescreen.getQueueMessageValue(), "1");
		setNetworkOn();
		homescreen.waitUntilQueueMessageInvisible();
		Assert.assertFalse(homescreen.isQueueMessageVisible());
		inspectionscreen = homescreen.clickInspectionsMenuItem();		
		inspectionscreen.switchToTeamInspectionsView();
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));	
		inspectionscreen.switchToMyInspectionsView();
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));	
		homescreen = inspectionscreen.clickBackButton();
		
		VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
		statusscreen.updateMainDB();
		homescreen = statusscreen.clickBackButton();
		
		inspectionscreen = homescreen.clickInspectionsMenuItem();
		inspectionscreen.switchToTeamInspectionsView();
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));
		inspectionscreen.switchToMyInspectionsView();
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 67293:Verify saving team inspection online doesn't affected to Settings > Manual send option", 
			description = "Verify saving team inspection online doesn't affected to Settings > Manual send option")
	public void testVerifySavingTeamInspectionOnlineDoesntAffectedToSettingsManualSendOption() {
		
		final String wholesalecustomer = "001 - Test Company";
		final String inspType = "O_Kramar";
		final String vinnumber = "123";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextSettingsScreen settingsscreen = homescreen.clickSettingsMenuItem();
		homescreen = settingsscreen.setManualSendOn().clickBackButton();		
		
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();		
		inspectionscreen.switchToTeamInspectionsView();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(wholesalecustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(inspType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));
		inspectionscreen.switchToMyInspectionsView();
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));
		homescreen = inspectionscreen.clickBackButton();
		
		VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
		statusscreen.updateMainDB();
		homescreen = statusscreen.clickBackButton();
		
		inspectionscreen = homescreen.clickInspectionsMenuItem();
		inspectionscreen.switchToTeamInspectionsView();
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));
		inspectionscreen.switchToMyInspectionsView();
		homescreen = inspectionscreen.clickBackButton();
	}

	@Test(testName= "Test Case 67330:Verify user can edit Inspection if we have no internet connection", 
			description = "Verify user can edit Inspection if we have no internet connection")
	public void testVerifyUserCanEditInspectionIfWeHaveNoInternetConnection() {
		
		final String wholesalecustomer = "001 - Test Company";
		final String inspType = "O_Kramar";
		final String vinnumber = "123";
		final String newvinnumber = "TEST456";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();		
		inspectionscreen.switchToTeamInspectionsView();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(wholesalecustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(inspType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();		
		VNextInspectionsMenuScreen inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		vehicleinfoscreen = inspmenuscreen.clickEditInspectionMenuItem();
		setNetworkOff();
		vehicleinfoscreen.setVIN(newvinnumber);
		vehicleinfoscreen.clickSaveInspectionMenuButton();
		vehicleinfoscreen.waitABit(4000);
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogOKButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		inspectionscreen.switchToMyInspectionsView();
		homescreen = inspectionscreen.clickBackButton();
		Assert.assertEquals(homescreen.getQueueMessageValue(), "1");
		setNetworkOn();
		homescreen.waitUntilQueueMessageInvisible();
		Assert.assertFalse(homescreen.isQueueMessageVisible());
	}
	
	@Test(testName= "Test Case 67331:Verify user can edit 'My inspections' if we have no internet connection", 
			description = "Verify user can edit 'My inspections' if we have no internet connection")
	public void testVerifyUserCanEditMyInspectionsIfWeHaveNoInternetConnection() {
		
		final String wholesalecustomer = "001 - Test Company";
		final String inspType = "O_Kramar";
		final String vinnumber = "123";
		final String newvinnumber = "TEST456";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();		
		inspectionscreen.switchToTeamInspectionsView();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(wholesalecustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(inspType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		inspectionscreen.switchToMyInspectionsView();
		setNetworkOff();
		
		VNextInspectionsMenuScreen inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		vehicleinfoscreen = inspmenuscreen.clickEditInspectionMenuItem();		
		vehicleinfoscreen.setVIN(newvinnumber);
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		homescreen = inspectionscreen.clickBackButton();
		Assert.assertEquals(homescreen.getQueueMessageValue(), "1");
		setNetworkOn();
		homescreen.waitUntilQueueMessageInvisible();
		Assert.assertFalse(homescreen.isQueueMessageVisible());
	}
	
	@Test(testName= "Test Case 67326:Verify user can edit Team Inspection, "
			+ "Test Case 67332:Verify immediately saving on BO if user edit Team Inspection", 
			description = "Verify user can edit Team Inspection, "
					+ "Verify immediately saving on BO if user edit Team Inspection")
	public void testVerifyUserCanEditTeamInspection() {
		
		final String wholesalecustomer = "001 - Test Company";
		final String inspType = "O_Kramar";
		final String vinnumber = "123";
		
		final String newVIN = "1FMCU0DG4BK830800";
		final String _make = "Ford";
		final String _model = "Escape";
		final String _year = "2011";
		final String licPlate = "123";
		final String mileage = "100000";
		final String stockNumber = "123ABC";
		final String roNumber = "123ASD";
		final String insuranceCompany = "Miami Beach Insurance";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();		
		inspectionscreen.switchToTeamInspectionsView();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(wholesalecustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(inspType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));
		
		vehicleinfoscreen = inspectionscreen.clickOpenInspectionToEdit(inspnumber);
		vehicleinfoscreen.setVIN(newVIN);
		//vehicleinfoscreen.selectModelColor(color);
		vehicleinfoscreen.setLicPlate(licPlate);
		vehicleinfoscreen.setMilage(mileage);
		vehicleinfoscreen.setStockNo(stockNumber);
		vehicleinfoscreen.setRoNo(roNumber);
		vehicleinfoscreen.changeScreen("Claim");
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		claiminfoscreen.selectInsuranceCompany(insuranceCompany);
		claiminfoscreen.saveInspectionViaMenu();
		
		vehicleinfoscreen = inspectionscreen.clickOpenInspectionToEdit(inspnumber);
		Assert.assertEquals(vehicleinfoscreen.getVINFieldValue(), newVIN);
		Assert.assertEquals(vehicleinfoscreen.getMakeInfo(), _make);
		Assert.assertEquals(vehicleinfoscreen.getModelInfo(), _model);
		Assert.assertEquals(vehicleinfoscreen.getYear(), _year);
		Assert.assertEquals(vehicleinfoscreen.getLicPlate(), licPlate);
		Assert.assertEquals(vehicleinfoscreen.getMilage(), mileage);
		Assert.assertEquals(vehicleinfoscreen.getStockNo(), stockNumber);
		Assert.assertEquals(vehicleinfoscreen.getRoNo(), roNumber);
		vehicleinfoscreen.changeScreen("Claim");
		claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		Assert.assertEquals(claiminfoscreen.getInsuranceCompany(), insuranceCompany);
		claiminfoscreen.cancelInspection();
		homescreen = inspectionscreen.clickBackButton();
		
		
		initiateWebDriver();
		webdriverGotoWebPage(BOConfigInfo.getInstance().getBackOfficeURL());
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(BOConfigInfo.getInstance().getUserUserName(), BOConfigInfo.getInstance().getUserUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		InspectionsWebPage inspectionspage = operationspage.clickInspectionsLink();
		inspectionspage.searchInspectionByNumber(inspnumber);
		inspectionspage.verifyVINIsPresentForInspection(inspnumber, newVIN);
		webdriver.quit();
	}
	
	@Test(testName= "Test Case 68042:Verify sending >100 messages after reconnect Internet", 
			description = "Verify sending >100 messages after reconnect Internet")
	public void testVerifySendingMoreThen100MessagesAfterReconnectInternet() {
		
		final String wholesalecustomer = "001 - Test Company";
		final String inspType = "O_Kramar";
		final String vinnumber = "123";
		final int fakeimagescount = 50;
		final String imagesummaryvalue = "+47";
		final String[] services = { "Battery Installation", "Aluminum Panel", "Damage Service" };
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		setNetworkOff();
		homescreen.waitABit(13000);		
		VNextSettingsScreen settingsscreen = homescreen.clickSettingsMenuItem();
		homescreen = settingsscreen.setManualSendOn().clickBackButton();
		
		//VNextCustomersScreen customersscreen = homescreen.clickNewInspectionPopupMenu();
		
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		inspectionsscreen.switchToMyInspectionsView();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(wholesalecustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(inspType);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(vinnumber);
		final String inspnumber = inspinfoscreen.getNewInspectionNumber();
		inspinfoscreen.swipeScreenLeft();
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		claiminfoscreen.selectInsuranceCompany("Test Insurance Company");
		claiminfoscreen.swipeScreenLeft();		
		VNextInspectionServicesScreen inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		VNextSelectServicesScreen selectedservicesscreen = inspservicesscreen.clickAddServicesButton();
		for (String srv : services) {			
			VNextServiceDetailsScreen servicedetailsscreen = selectedservicesscreen.openServiceDetails(srv);
			VNextNotesScreen notesscreen = servicedetailsscreen.clickServiceNotesOption();
			for (int i = 0; i < fakeimagescount; i++)
				notesscreen.addFakeImageNote();
			notesscreen.clickScreenBackButton();
			servicedetailsscreen = new VNextServiceDetailsScreen(appiumdriver);
			servicedetailsscreen.clickServiceDetailsDoneButton();
			selectedservicesscreen = new VNextSelectServicesScreen(appiumdriver);
		}
		selectedservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		//inspservicesscreen.selectAllServices();
		
		inspservicesscreen.saveInspectionViaMenu();
		inspservicesscreen.clickScreenBackButton();
		homescreen = new VNextHomeScreen(appiumdriver);
		VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
		statusscreen.clickUpdateAppdata();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogStartSyncButton();
		waitABit(10000);
		informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogOKButton();
		setNetworkOn();	
		statusscreen.clickUpdateAppdata();	
		informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogStartSyncButton();
		waitABit(10000);
		informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogOKButton();
		
		homescreen = statusscreen.clickBackButton();
		inspectionsscreen = homescreen.clickInspectionsMenuItem();
		Assert.assertTrue(inspectionsscreen.isInspectionExists(inspnumber));
		inspinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnumber);
		inspinfoscreen.swipeScreenLeft();
		claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);

		claiminfoscreen.swipeScreenLeft();		
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		for (String srv : services) {	
			inspservicesscreen.isServiceSelected(srv);
			Assert.assertEquals(inspservicesscreen.getSelectedServiceImageSummaryValue(srv), imagesummaryvalue);
		}
		inspectionsscreen = inspservicesscreen.cancelInspection();
		inspservicesscreen.clickScreenBackButton();
		homescreen = new VNextHomeScreen(appiumdriver);
	}
	
	@Test(testName= "Test Case 67756:Verify message 'Your email message has been added to the queue.' displays after sending", 
			description = "Verify message 'Your email message has been added to the queue.' displays after sending")
	public void testVerifyMessageYourEmailMessageHasBeenAddedtoTheQueueDisplaysAfterSending() {
		
		final String customereMail = "test.cyberiansoft@gmail.com";
		final String wholesalecustomer = "001 - Test Company";
		final String inspType = "Insp_type_approv_req";
		final String vinnumber = "TEST";

		final String inspnumber = createSimpleInspection(wholesalecustomer, inspType, vinnumber);
		VNextInspectionsScreen inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		VNextEmailScreen emailscreen = inspectionscreen.clickOnInspectionToEmail(inspnumber);
		emailscreen.sentToEmailAddress(customereMail);
		emailscreen.sendEmail();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		inspectionscreen.clickBackButton();	
	}
	
	public String createSimpleInspection(String inspcustomer, String insptype, String vinnumber) {
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		inspectionscreen.switchToMyInspectionsView();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(inspcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(insptype);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		return inspnumber;
	}

}
