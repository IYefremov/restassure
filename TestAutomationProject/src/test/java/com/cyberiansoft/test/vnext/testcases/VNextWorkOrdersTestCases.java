package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehicleVINHistoryScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextWorkOrdersMenuScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextWorkOrdersTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {

	@BeforeClass(description="R360 Work Orders Test Cases")
	public void beforeClass() {
	}
	final String testVIN = "1FMCU0DG4BK830800";
	
	@Test(testName= "Test Case 43334:vNext - Show selected services after WO is saved", 
			description = "Show selected services after WO is saved")
	public void testShowSelectedServicesAfterWOIsSaved() { 
	
		final String[] services = { "Bumper Repair", "Other" }; 
		
		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleinfoscreen.setVIN(testVIN);
		//AppiumUtils.clickHardwareBackButton();
		vehicleinfoscreen.clickScreenForwardButton();
		VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleVINHistoryScreen.clickBackButton();
		vehicleinfoscreen.changeScreen("Services");
		VNextAvailableServicesScreen servicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		servicesscreen.selectServices(services);
		workordersscreen = servicesscreen.saveWorkOrderViaMenu();
		final String wonumber = workordersscreen.getFirstWorkOrderNumber();
		VNextWorkOrdersMenuScreen menuscreen = workordersscreen.clickOnWorkOrderByNumber(wonumber);
		vehicleinfoscreen = menuscreen.clickEditWorkOrderMenuItem();
		vehicleinfoscreen.changeScreen("Services");
		servicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesView();
		for (String servicename : services)
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(servicename));
		workordersscreen = servicesscreen.saveWorkOrderViaMenu();
		homescreen = workordersscreen.clickBackButton();
	}

}
