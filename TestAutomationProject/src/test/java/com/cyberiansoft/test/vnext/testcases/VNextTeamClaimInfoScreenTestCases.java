package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;

public class VNextTeamClaimInfoScreenTestCases extends BaseTestCaseTeamEditionRegistration {
	
	@BeforeClass(description="Team Claim Info Test Cases")
	public void beforeClass() {
	}
	
	@Test(testName= "Test Case 64814:Verify Claim Info screen visible for Inspection if 'Claim Info = ON'", 
			description = "Verify Claim Info screen visible for Inspection if 'Claim Info = ON'")
	public void testVerifyClaimInfoScreenVisibleForInspectionIfClaimInfoEqualsON() {

		final String vinnumber = "TEST";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
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

		final String vinnumber = "TEST";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR2);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		vehicleinfoscreen.clickScreenTitleCaption();
		Assert.assertFalse(vehicleinfoscreen.isScreenPresentInChangeScreenPopoverList("Claim"));
		AppiumUtils.clickHardwareBackButton();
		
		inspectionscreen = vehicleinfoscreen.cancelInspection();
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 64846:Verify Claim Info screen visible for WO if 'Claim Info = ON'", 
			description = "Verify Claim Info screen visible for WO if 'Claim Info = ON'")
	public void testVerifyClaimInfoScreenVisibleForWOIfClaimInfoEqualsON() {

		final String vinnumber = "TEST";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
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

		final String vinnumber = "TEST";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR2);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		vehicleinfoscreen.clickScreenTitleCaption();
		Assert.assertFalse(vehicleinfoscreen.isScreenPresentInChangeScreenPopoverList("Claim"));
		AppiumUtils.clickHardwareBackButton();
		
		inspectionscreen = vehicleinfoscreen.cancelInspection();
		homescreen = inspectionscreen.clickBackButton();
	}

}
