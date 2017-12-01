package com.cyberiansoft.test.vnext.testcases;

import org.junit.Assert;
import org.testng.annotations.Test;

import com.cyberiansoft.test.vnext.screens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextInspectionServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextSelectServicesScreen;
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

}
