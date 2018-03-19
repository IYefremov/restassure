package com.cyberiansoft.test.vnext.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cyberiansoft.test.vnext.screens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.utils.VNextWholesailCustomer;

public class VNextTeamClaimInfotestCases extends BaseTestCaseTeamEditionRegistration {
	
	@Test(testName= "Test Case 64814:Verify Claim Info screen visible for Inspection if 'Claim Info = ON'", 
			description = "Verify Claim Info screen visible for Inspection if 'Claim Info = ON'")
	public void testVerifyClaimInfoScreenVisibleForInspectionIfClaimInfoEqualsON() {
		
		final VNextWholesailCustomer wholesalecustomer = new VNextWholesailCustomer("001 - Test Company");
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
		vehicleinfoscreen.changeScreen("Claim");
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		Assert.assertTrue(claiminfoscreen.isInsuranceCompanyFieldVisible());
		Assert.assertTrue(claiminfoscreen.isClaimNumberFieldVisible());
		Assert.assertTrue(claiminfoscreen.isPolicyNumberFieldVisible());
		Assert.assertTrue(claiminfoscreen.isDeductibleFieldVisible());
		//LocalDate now = LocalDate.now();
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
		//Assert.assertEquals(claiminfoscreen.getAccidentDateValue(), now.format(formatter));
		
		inspectionscreen = claiminfoscreen.cancelInspection();
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 64845:Verify Claim Info screen is not visible for Inspection if 'Claim Info = OFF'", 
			description = "Verify Claim Info screen is not visible for Inspection if 'Claim Info = OFF'")
	public void testVerifyClaimInfoScreenIsNotVisibleForInspectionIfClaimInfoEqualsOFF() {
		
		final VNextWholesailCustomer wholesalecustomer = new VNextWholesailCustomer("001 - Test Company");
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
		Assert.assertFalse(vehicleinfoscreen.isScreenPresentInChangeScreenPopoverList("Claim"));
		vehicleinfoscreen.clickHardwareBackButton();
		
		inspectionscreen = vehicleinfoscreen.cancelInspection();
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 64846:Verify Claim Info screen visible for WO if 'Claim Info = ON'", 
			description = "Verify Claim Info screen visible for WO if 'Claim Info = ON'")
	public void testVerifyClaimInfoScreenVisibleForWOIfClaimInfoEqualsON() {
		
		final VNextWholesailCustomer wholesalecustomer = new VNextWholesailCustomer("001 - Test Company");
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
		vehicleinfoscreen.changeScreen("Claim");
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		Assert.assertTrue(claiminfoscreen.isInsuranceCompanyFieldVisible());
		Assert.assertTrue(claiminfoscreen.isClaimNumberFieldVisible());
		Assert.assertTrue(claiminfoscreen.isPolicyNumberFieldVisible());
		Assert.assertTrue(claiminfoscreen.isDeductibleFieldVisible());
		//LocalDate now = LocalDate.now();
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
		//Assert.assertEquals(claiminfoscreen.getAccidentDateValue(), now.format(formatter));
		
		inspectionscreen = claiminfoscreen.cancelInspection();
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 64847:Verify Claim Info screen is not visible for WO if 'Claim Info = OFF'", 
			description = "Verify Claim Info screen is not visible for WO if 'Claim Info = OFF'")
	public void testVerifyClaimInfoScreenIsNotVisibleForWOIfClaimInfoEqualsOFF() {
		
		final VNextWholesailCustomer wholesalecustomer = new VNextWholesailCustomer("001 - Test Company");
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
		Assert.assertFalse(vehicleinfoscreen.isScreenPresentInChangeScreenPopoverList("Claim"));
		vehicleinfoscreen.clickHardwareBackButton();
		
		inspectionscreen = vehicleinfoscreen.cancelInspection();
		homescreen = inspectionscreen.clickBackButton();
	}

}
