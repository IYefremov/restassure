package com.cyberiansoft.test.vnext.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cyberiansoft.test.vnext.screens.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.VNextSelectServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextWorkOrdersScreen;

public class VNextWorkOrdersTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {
	
	final String testcustomer = "Oksana Osmak";
	final String testVIN = "1FMCU0DG4BK830800";
	
	@Test(testName= "Test Case 43334:vNext - Show selected services after WO is saved", 
			description = "Show selected services after WO is saved")
	public void testShowSelectedServicesAfterWOIsSaved() { 
	
		final String[] services = { "Bumper Repair", "Other" }; 
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(testVIN);
		vehicleinfoscreen.swipeScreenLeft();
		VNextInspectionServicesScreen servicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		VNextSelectServicesScreen selectservicesscreen = servicesscreen.clickAddServicesButton();
		selectservicesscreen.selectServices(services);
		selectservicesscreen.clickSaveSelectedServicesButton();	
		servicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		workordersscreen = servicesscreen.saveWorkOrderViaMenu();
		final String wonumber = workordersscreen.getFirstWorkOrderNumber();
		VNextInspectionsMenuScreen menuscreen = workordersscreen.clickOnWorkOrderByInspNumber(wonumber);
		vehicleinfoscreen = menuscreen.clickEditInspectionMenuItem();
		vehicleinfoscreen.swipeScreenLeft();
		servicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		for (String servicename : services)
			Assert.assertTrue(servicesscreen.isServiceAdded(servicename));
		workordersscreen = servicesscreen.saveWorkOrderViaMenu();
		homescreen = workordersscreen.clickBackButton();
	}

}
