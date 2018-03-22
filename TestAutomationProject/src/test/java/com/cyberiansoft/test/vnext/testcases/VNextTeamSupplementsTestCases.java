package com.cyberiansoft.test.vnext.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cyberiansoft.test.vnext.screens.VNextApproveScreen;
import com.cyberiansoft.test.vnext.screens.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.utils.VNextRetailCustomer;

public class VNextTeamSupplementsTestCases extends BaseTestCaseTeamEditionRegistration {
	
	@Test(testName= "Test Case 71996:Verify user can add supplement if Allow Supplements=ON", 
			description = "Verify user can add supplement if Allow Supplements=ON")
	public void testVerifyUserCanAddSupplementIfAllowSupplementsSetToON() {
		
		final VNextRetailCustomer testcustomer = new VNextRetailCustomer("Retail", "Automation");
		final String inspType = "O_Kramar";
		final String vinnumber = "TEST";
		final String newvinnumber = "TESTNEW";
		 		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(inspType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		

		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		vehicleinfoscreen = inspmenu.clickAddSupplementInspectionMenuItem();
		vehicleinfoscreen.setVIN(newvinnumber);
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 71997:Verify user can't create supplement if allow supplement=OFF", 
			description = "Verify user can't create supplement if allow supplement=OFF")
	public void testVerifyUserCantCreateSupplementIfAllowSupplementsSetToOff() {
		
		final VNextRetailCustomer testcustomer = new VNextRetailCustomer("Retail", "Automation");
		final String inspType = "O_Kramar2";
		final String vinnumber = "TEST";
		 		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(inspType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		Assert.assertFalse(inspmenu.isAddSupplementInspectionMenuItemPresent());
		inspmenu.clickCloseInspectionMenuButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 72585:Verify user can't add supplement when edit Inspection", 
			description = "Verify user can't add supplement when edit Inspection")
	public void testVerifyUserCanAddSupplementWhenEditInspection() {
		
		final VNextRetailCustomer testcustomer = new VNextRetailCustomer("Retail", "Automation");
		final String inspType = "O_Kramar";
		final String vinnumber = "TEST";
		final String serviceName1 = "Battery Installation";
		final String serviceName2 = "Labor";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(inspType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreensLeft(2);
		VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inpsctionservicesscreen.selectService(serviceName1);	
		inspectionscreen = inpsctionservicesscreen.saveInspectionViaMenu();
		
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		vehicleinfoscreen = inspmenu.clickEditInspectionMenuItem();
		vehicleinfoscreen.swipeScreensLeft(2);
		inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inpsctionservicesscreen.setServiceAmountValue(serviceName1, "10");
		
		inpsctionservicesscreen.selectService(serviceName2);
		inpsctionservicesscreen.setServiceAmountValue(serviceName1, "20");
		inpsctionservicesscreen.setServiceQuantityValue(serviceName1, "2");
		inspectionscreen = inpsctionservicesscreen.saveInspectionViaMenu();
		inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		Assert.assertTrue(inspmenu.isAddSupplementInspectionMenuItemPresent());
		inspmenu.clickCloseInspectionMenuButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		homescreen = inspectionscreen.clickBackButton();
	}

	@Test(testName= "Test Case 72586:Verify user can add supplement after approve Inspection", 
			description = "Verify user can add supplement after approve Inspection")
	public void testVerifyUserCanAddSupplementAfterApproveInspection() {
		
		final VNextRetailCustomer testcustomer = new VNextRetailCustomer("Retail", "Automation");
		final String inspType = "O_Kramar";
		final String vinnumber = "TEST";
		final String serviceName = "Battery Installation";;
		 		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(inspType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		VNextApproveScreen approvescreen = inspmenu.clickApproveInspectionMenuItem();
		approvescreen.drawSignature();
		approvescreen.clickSaveButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
	
		inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		vehicleinfoscreen = inspmenu.clickAddSupplementInspectionMenuItem();
		vehicleinfoscreen.swipeScreensLeft(2);
		VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inpsctionservicesscreen.selectService(serviceName);

		inspectionscreen = inpsctionservicesscreen.saveInspectionViaMenu();
		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), "New");
		homescreen = inspectionscreen.clickBackButton();
	}
}
