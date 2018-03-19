package com.cyberiansoft.test.vnext.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cyberiansoft.test.vnext.screens.VNextApproveScreen;
import com.cyberiansoft.test.vnext.screens.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.utils.VNextCustomer;
import com.cyberiansoft.test.vnext.utils.VNextInspectionStatuses;
import com.cyberiansoft.test.vnext.utils.VNextWholesailCustomer;

public class VNextApproveTeamInspectionsTestCases extends BaseTestCaseTeamEditionRegistration {
	
	@Test(testName= "Test Case 64246:Verify user can create Inspection in status 'New',"
			+ "Test Case 64249:Verify user can approve Inspection after creating,"
			+ "Verify user can create Inspection in status 'New'", 
			description = "Verify user can approve Inspection after creating")
	public void testVerifyUserCanApproveInspectionAfterCreating() {
		
		final VNextWholesailCustomer wholesalecustomer = new VNextWholesailCustomer("001 - Test Company");
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
		
		final VNextWholesailCustomer wholesalecustomer = new VNextWholesailCustomer("001 - Test Company");
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
		
		final VNextWholesailCustomer wholesalecustomer = new VNextWholesailCustomer("001 - Test Company");
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
		inspmenuscreen.clickCloseInspectionMenuButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		homescreen = inspectionscreen.clickBackButton();
	}
	
	public String createSimpleInspection(VNextCustomer inspcustomer, String insptype, String vinnumber) {
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
