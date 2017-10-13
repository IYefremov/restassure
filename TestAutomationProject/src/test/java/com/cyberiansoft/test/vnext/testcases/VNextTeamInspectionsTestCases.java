package com.cyberiansoft.test.vnext.testcases;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cyberiansoft.test.vnext.screens.VNextApproveScreen;
import com.cyberiansoft.test.vnext.screens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.VNextInvoiceInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextWorkOrderSummaryScreen;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.utils.VNextInspectionStatuses;


public class VNextTeamInspectionsTestCases extends BaseTestCaseTeamEditionRegistration {
	
	@Test(testName= "Test Case 64246:Verify user can create Inspection in status 'New',"
			+ "Test Case 64249:Verify user can approve Inspection after creating,"
			+ "Verify user can create Inspection in status 'New'", 
			description = "Verify user can approve Inspection after creating")
	public void testVerifyUserCanApproveInspectionAfterCreating() {
		
		final String wholesalecustomer = "001 - Test Company";
		final String inspType = "Insp_type_approv_req";
		final String vinnumber = "TEST";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		final String inspnumber = createSimpleInspection(wholesalecustomer, inspType, vinnumber);
		VNextInspectionsScreen inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), VNextInspectionStatuses.NEW);
		VNextInspectionsMenuScreen inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		Assert.assertFalse(inspmenuscreen.isCreateWorkOrderMenuPresent());
		VNextApproveScreen approvescreen = inspmenuscreen.clickApproveInspectionMenuItem();
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), VNextInspectionStatuses.APPROVED);
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 64250:Verify 'Clean' icon work correctly", 
			description = "Verify 'Clean' icon work correctly")
	public void testVerifyCleanIconWorkCorrectly() {
		
		final String wholesalecustomer = "001 - Test Company";
		final String inspType = "Insp_type_approv_req";
		final String vinnumber = "TEST";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		final String inspnumber = createSimpleInspection(wholesalecustomer, inspType, vinnumber);
		VNextInspectionsScreen inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), VNextInspectionStatuses.NEW);
		VNextInspectionsMenuScreen inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		VNextApproveScreen approvescreen = inspmenuscreen.clickApproveInspectionMenuItem();
		approvescreen.drawSignature();
		approvescreen.clickClearSignatureButton();
		approvescreen.clickSaveButton();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		Assert.assertEquals(informationdlg.clickInformationDialogOKButtonAndGetMessage(), VNextAlertMessages.PLEASE_DOMT_LEAVE_SIGNATURE_FIELD_EMPTY);
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), VNextInspectionStatuses.APPROVED);
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 64264:Verify user can create WO only for Approved Inspections", 
			description = "Verify user can create WO only for Approved Inspections")
	public void testVerifyUserCanCreateWOOnlyForApprovedInspections() {
		
		final String wholesalecustomer = "001 - Test Company";
		final String inspType = "Insp_type_approv_req";
		final String vinnumber = "TEST";

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
		Assert.assertFalse(inspmenuscreen.isApproveMenuPresent());
		Assert.assertTrue(inspmenuscreen.isCreateWorkOrderMenuPresent());
		inspectionscreen = inspmenuscreen.clickCloseInspectionMenuButton();
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 64497:Verify user can create Invoice from Inspection", 
			description = "Verify user can create Invoice from Inspection")
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
		Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.APPROVED);
		
		homescreen = invoicesscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 64814:Verify Claim Info screen visible for Inspection if 'Claim Info = ON'", 
			description = "Verify Claim Info screen visible for Inspection if 'Claim Info = ON'")
	public void testVerifyClaimInfoScreenVisibleForInspectionIfClaimInfoEqualsON() {
		
		final String wholesalecustomer = "001 - Test Company";
		final String inspType = "O_Kramar";
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
		vehicleinfoscreen.changeScreen("Claim Info");
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		Assert.assertTrue(claiminfoscreen.isInsuranceCompanyFieldVisible());
		Assert.assertTrue(claiminfoscreen.isClaimNumberFieldVisible());
		Assert.assertTrue(claiminfoscreen.isPolicyNumberFieldVisible());
		Assert.assertTrue(claiminfoscreen.isDeductibleFieldVisible());
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
		Assert.assertEquals(claiminfoscreen.getAccidentDateValue(), now.format(formatter));
		
		inspectionscreen = claiminfoscreen.cancelInspection();
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 64845:Verify Claim Info screen is not visible for Inspection if 'Claim Info = OFF'", 
			description = "Verify Claim Info screen is not visible for Inspection if 'Claim Info = OFF'")
	public void testVerifyClaimInfoScreenIsNotVisibleForInspectionIfClaimInfoEqualsOFF() {
		
		final String wholesalecustomer = "001 - Test Company";
		final String inspType = "O_Kramar2";
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
		vehicleinfoscreen.clickScreenTitleCaption();
		Assert.assertFalse(vehicleinfoscreen.isScreenPresentInChangeScreenPopoverList("Claim Info"));
		vehicleinfoscreen.clickHardwareBackButton();
		
		inspectionscreen = vehicleinfoscreen.cancelInspection();
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 64846:Verify Claim Info screen visible for WO if 'Claim Info = ON'", 
			description = "Verify Claim Info screen visible for WO if 'Claim Info = ON'")
	public void testVerifyClaimInfoScreenVisibleForWOIfClaimInfoEqualsON() {
		
		final String wholesalecustomer = "001 - Test Company";
		final String inspType = "O_Kramar";
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
		vehicleinfoscreen.changeScreen("Claim Info");
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		Assert.assertTrue(claiminfoscreen.isInsuranceCompanyFieldVisible());
		Assert.assertTrue(claiminfoscreen.isClaimNumberFieldVisible());
		Assert.assertTrue(claiminfoscreen.isPolicyNumberFieldVisible());
		Assert.assertTrue(claiminfoscreen.isDeductibleFieldVisible());
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
		Assert.assertEquals(claiminfoscreen.getAccidentDateValue(), now.format(formatter));
		
		inspectionscreen = claiminfoscreen.cancelInspection();
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 64847:Verify Claim Info screen is not visible for WO if 'Claim Info = OFF'", 
			description = "Verify Claim Info screen is not visible for WO if 'Claim Info = OFF'")
	public void testVerifyClaimInfoScreenIsNotVisibleForWOIfClaimInfoEqualsOFF() {
		
		final String wholesalecustomer = "001 - Test Company";
		final String inspType = "O_Kramar2";
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
		vehicleinfoscreen.clickScreenTitleCaption();
		Assert.assertFalse(vehicleinfoscreen.isScreenPresentInChangeScreenPopoverList("Claim Info"));
		vehicleinfoscreen.clickHardwareBackButton();
		
		inspectionscreen = vehicleinfoscreen.cancelInspection();
		homescreen = inspectionscreen.clickBackButton();
	}
	
	public String createSimpleInspection(String inspcustomer, String insptype, String vinnumber) {
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
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
