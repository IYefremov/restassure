package com.cyberiansoft.test.vnext.testcases;

import org.junit.Assert;
import org.testng.annotations.Test;

import com.cyberiansoft.test.vnext.screens.VNextApproveScreen;
import com.cyberiansoft.test.vnext.screens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextInspectionServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.VNextStatusScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextWorkOrderClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextWorkOrderTypesList;
import com.cyberiansoft.test.vnext.screens.VNextWorkOrdersScreen;

public class VNextTeamWorkOrdersTestCases extends BaseTestCaseTeamEditionRegistration {
	
	@Test(testName= "Test Case 67042:Verify user can delete WO if 'Allow Delete=ON'", 
			description = "Verify user can delete WO if 'Allow Delete=ON'")
	public void testVerifyUserCanDeleteWOIfAllowDeleteON() { 
	
		final String testcustomer = "Test Test";
		final String workorderType = "Kramar_auto";
		final String vinnumber = "TEST";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextCustomersScreen customersscreen = homescreen.clickNewWorkOrderPopupMenu();
		
		//VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		//VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(appiumdriver);
		wotypes.selectWorkOrderType(workorderType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
		VNextWorkOrdersScreen workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
		Assert.assertTrue(workordersscreen.isWorkOrderExists(woNumber));
		VNextInspectionsMenuScreen womenu = workordersscreen.clickOnWorkOrderByNumber(woNumber);
		womenu.clickDeleteWorkOrderMenuButton();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogDontDeleteButton();
		workordersscreen = new VNextWorkOrdersScreen(appiumdriver);
		Assert.assertTrue(workordersscreen.isWorkOrderExists(woNumber));
		womenu = workordersscreen.clickOnWorkOrderByNumber(woNumber);
				
		workordersscreen = womenu.deleteWorkOrder();
		Assert.assertFalse(workordersscreen.isWorkOrderExists(woNumber));
		homescreen = workordersscreen.clickBackButton();
		
		VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
		statusscreen.updateMainDB();
		homescreen = statusscreen.clickBackButton();
		workordersscreen = homescreen.clickWorkOrdersMenuItem();
		Assert.assertFalse(workordersscreen.isWorkOrderExists(woNumber));
		homescreen = workordersscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 67043:Verify user can't delete WO if 'Allow Delete=OFF'", 
			description = "Verify user can't delete WO if 'Allow Delete=OFF'")
	public void testVerifyUserCantDeleteWOIfAllowDeleteOFF() { 
	
		final String testcustomer = "Test Test";
		final String workorderType = "Kramar_auto2";
		final String vinnumber = "TEST";
		final String insuranceCompany = "Miami Beach Insurance";
		final String claimNumber = "4Bc";
		final String policyNumber = "12345";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextCustomersScreen customersscreen = homescreen.clickNewWorkOrderPopupMenu();
		
		//VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		//VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(appiumdriver);
		wotypes.selectWorkOrderType(workorderType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreenLeft();
		VNextInspectionServicesScreen servicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		servicesscreen.swipeScreenLeft();
		VNextWorkOrderClaimInfoScreen claiminfoscreen = new VNextWorkOrderClaimInfoScreen(appiumdriver);
		claiminfoscreen.selectInsuranceCompany(insuranceCompany);
		claiminfoscreen.setClaimNumber(claimNumber);
		claiminfoscreen.setPolicyNumber(policyNumber);
		VNextWorkOrdersScreen workordersscreen = claiminfoscreen.saveWorkOrderViaMenu();
		Assert.assertTrue(workordersscreen.isWorkOrderExists(woNumber));
		VNextInspectionsMenuScreen womenu = workordersscreen.clickOnWorkOrderByNumber(woNumber);
		Assert.assertFalse(womenu.isDeleteWorkOrderMenuButtonExists());
		womenu.clickCloseInspectionMenuButton();
		Assert.assertTrue(workordersscreen.isWorkOrderExists(woNumber));
		workordersscreen.clickBackButton();
		
	}
	
	@Test(testName= "Test Case 67329:Verify Team WO displays in My WO list if WO was created from Team Inspection", 
			description = "Verify Team WO displays in My WO list if WO was created from Team Inspection")
	public void testVerifyTeamWODisplaysInMyWOsListIfWOWasCreatedFromTeamInspection() { 
	
		final String searchtext = "00182";
		final String workorderType = "Kramar_auto";
		final String vinnumber = "TEST";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();		
		inspectionscreen.switchToTeamInspectionsView();
		inspectionscreen.searchInpectionByFreeText(searchtext);
		VNextInspectionsMenuScreen inspectionmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspectionscreen.getFirstInspectionNumber());
		inspectionmenuscreen.clickCreateWorkOrderInspectionMenuItem();
		
		VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(appiumdriver);
		wotypes.selectWorkOrderType(workorderType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
		VNextWorkOrdersScreen workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
		/*vehicleinfoscreen.swipeScreenLeft();
		VNextInspectionServicesScreen servicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		servicesscreen.swipeScreenLeft();
		VNextWorkOrderClaimInfoScreen claiminfoscreen = new VNextWorkOrderClaimInfoScreen(appiumdriver);
		claiminfoscreen.selectInsuranceCompany(insuranceCompany);
		claiminfoscreen.setClaimNumber(claimNumber);
		claiminfoscreen.setPolicyNumber(policyNumber);
		VNextWorkOrdersScreen workordersscreen = claiminfoscreen.saveWorkOrderViaMenu();*/
		Assert.assertTrue(workordersscreen.isWorkOrderExists(woNumber));
		
		workordersscreen.switchToTeamWorkordersView();
		Assert.assertTrue(workordersscreen.isWorkOrderExists(woNumber));
		workordersscreen.switchToMyWorkordersView();
		workordersscreen.clickBackButton();
		
	}
	
	@Test(testName= "Test Case 68376:Verify user can create WO from Team Inspection", 
			description = "Verify user can create WO from Team Inspection")
	public void testVerifyUserCanCreateWOFromTeamInspection() { 
	
		final String inspcustomer = "Test Test";
		final String insptype = "O_Kramar2";
		final String workorderType = "Kramar_auto";
		final String vinnumber = "TEST";
		final String servicesToSelect[] = { "Aluminum Panel", "Damage Service" };
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		inspectionscreen.switchToMyInspectionsView();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(inspcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(insptype);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();		
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		
		inspectionscreen.switchToTeamInspectionsView();
		VNextInspectionsMenuScreen inspectionmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspectionmenuscreen.clickCreateWorkOrderInspectionMenuItem();
		
		VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(appiumdriver);
		wotypes.selectWorkOrderType(workorderType);
		vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreenLeft();
		VNextInspectionServicesScreen servicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		for (String service : servicesToSelect)
			servicesscreen.selectService(service);
		VNextWorkOrdersScreen workordersscreen = servicesscreen.saveWorkOrderViaMenu();
		Assert.assertTrue(workordersscreen.isWorkOrderExists(woNumber));
		
		workordersscreen.switchToTeamWorkordersView();
		Assert.assertTrue(workordersscreen.isWorkOrderExists(woNumber));
		workordersscreen.switchToMyWorkordersView();
		VNextInspectionsMenuScreen womenuscreen = workordersscreen.clickOnWorkOrderByNumber(woNumber);
		vehicleinfoscreen = womenuscreen.clickEditInspectionMenuItem();
		vehicleinfoscreen.swipeScreenLeft();
		servicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		for (String service : servicesToSelect)
			Assert.assertTrue(servicesscreen.isServiceSelected(service));
		servicesscreen.saveWorkOrderViaMenu();
		workordersscreen.clickBackButton();		
	}
	
	@Test(testName= "Test Case 68377:Verify user can create WO only from Approved Team Inspection", 
			description = "Verify user can create WO only from Approved Team Inspection")
	public void testVerifyUserCanCreateWOOnlyFromApprovedTeamInspection() { 
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();		
		
		inspectionscreen.switchToTeamInspectionsView();
		VNextInspectionsMenuScreen inspectionmenuscreen = inspectionscreen.clickOnFirstInspectionWithStatus("New");
		Assert.assertFalse(inspectionmenuscreen.isCreateWorkOrderMenuPresent());
		inspectionmenuscreen.clickCloseInspectionMenuButton();
		
		inspectionmenuscreen = inspectionscreen.clickOnFirstInspectionWithStatus("Approved");
		Assert.assertTrue(inspectionmenuscreen.isCreateWorkOrderMenuPresent());
		inspectionmenuscreen.clickCloseInspectionMenuButton();
		inspectionscreen.switchToMyInspectionsView();
		inspectionscreen.clickBackButton();		
	}
	
	@Test(testName= "Test Case 68379:Verify all selected services from Inspection displays when user create WO", 
			description = "Verify all selected services from Inspection displays when user create WO")
	public void testVerifyAllSelectedServicesFromInspectionDisplaysWhenUserCreateWO() { 
	
		final String inspcustomer = "Test Test";
		final String insptype = "O_Kramar";
		final String workorderType = "Kramar_auto";
		final String vinnumber = "TEST";
		final String servicesToSelect[] = { "Aluminum Panel", "Damage Service" };
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		inspectionscreen.switchToTeamInspectionsView();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(inspcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(insptype);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();		
		vehicleinfoscreen.swipeScreenLeft();
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		claiminfoscreen.selectInsuranceCompany("Test Insurance Company");
		claiminfoscreen.swipeScreenLeft();		
		VNextInspectionServicesScreen inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		for (String service : servicesToSelect)
			inspservicesscreen.selectService(service);
		inspservicesscreen.saveInspection();
		
		VNextInspectionsMenuScreen inspectionmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		VNextApproveScreen approvescreen = inspectionmenuscreen.clickApproveInspectionMenuItem();
		approvescreen.drawSignature();
		approvescreen.clickSaveButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		inspectionmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspectionmenuscreen.clickCreateWorkOrderInspectionMenuItem();
		
		VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(appiumdriver);
		wotypes.selectWorkOrderType(workorderType);
		vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreenLeft();
		VNextInspectionServicesScreen servicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		for (String service : servicesToSelect)
			Assert.assertTrue(servicesscreen.isServiceSelected(service));
		VNextWorkOrdersScreen workordersscreen = servicesscreen.saveWorkOrderViaMenu();
		Assert.assertTrue(workordersscreen.isWorkOrderExists(woNumber));
		workordersscreen.clickBackButton();		
	}

	
	@Test(testName= "Test Case 68380:Verify user can delete WO if this WO was created from Team Inspection screen, "
			+ "Test Case 68378:Verify deleted WO doesn't displays in Team/My WO list", 
			description = "Verify user can delete WO if this WO was created from Team Inspection screen, "
					+ "Verify deleted WO doesn't displays in Team/My WO list")
	public void testVerifyUserCanDeleteWOIfThisWOWasCreatedFromTeamInspectionScreen() { 
	
		final String inspcustomer = "Test Test";
		final String insptype = "O_Kramar";
		final String workorderType = "Kramar_auto";
		final String vinnumber = "TEST";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		inspectionscreen.switchToTeamInspectionsView();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(inspcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(insptype);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();				
		vehicleinfoscreen.saveInspectionViaMenu();
		
		VNextInspectionsMenuScreen inspectionmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		VNextApproveScreen approvescreen = inspectionmenuscreen.clickApproveInspectionMenuItem();
		approvescreen.drawSignature();
		approvescreen.clickSaveButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		inspectionmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspectionmenuscreen.clickCreateWorkOrderInspectionMenuItem();
		
		VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(appiumdriver);
		wotypes.selectWorkOrderType(workorderType);
		vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
		VNextWorkOrdersScreen workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
		
		Assert.assertTrue(workordersscreen.isWorkOrderExists(woNumber));
		workordersscreen.switchToMyWorkordersView();
		inspectionmenuscreen = workordersscreen.clickOnWorkOrderByNumber(woNumber);
		inspectionmenuscreen.clickDeleteWorkOrderMenuButton();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogDeleteButton();
		workordersscreen = new VNextWorkOrdersScreen(appiumdriver);
		Assert.assertFalse(workordersscreen.isWorkOrderExists(woNumber));
		
		workordersscreen.switchToTeamWorkordersView();
		Assert.assertFalse(workordersscreen.isWorkOrderExists(woNumber));
		workordersscreen.switchToMyWorkordersView();
		workordersscreen.clickBackButton();		
	}
}
