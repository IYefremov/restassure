package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehicleVINHistoryScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextWorkOrdersMenuScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextWorkOrdersTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {

	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/r360-workorders-testcases-data.json";

	@BeforeClass(description="R360 Work Orders Test Cases")
	public void beforeClass() {
		JSONDataProvider.dataFile = DATA_FILE;
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testShowSelectedServicesAfterWOIsSaved(String rowID,
																	   String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
		
		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleinfoscreen.setVIN(workOrderData.getVinNumber());
		//AppiumUtils.clickHardwareBackButton();
		vehicleinfoscreen.clickScreenForwardButton();
		VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleVINHistoryScreen.clickBackButton();
		vehicleinfoscreen.changeScreen("Services");
		VNextAvailableServicesScreen servicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		servicesscreen.selectServices(workOrderData.getServicesList());
		workordersscreen = servicesscreen.saveWorkOrderViaMenu();
		final String wonumber = workordersscreen.getFirstWorkOrderNumber();
		VNextWorkOrdersMenuScreen menuscreen = workordersscreen.clickOnWorkOrderByNumber(wonumber);
		vehicleinfoscreen = menuscreen.clickEditWorkOrderMenuItem();
		vehicleinfoscreen.changeScreen("Services");
		servicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesView();
		for (ServiceData serviceData : workOrderData.getServicesList())
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(serviceData.getServiceName()));
		workordersscreen = servicesscreen.saveWorkOrderViaMenu();
		workordersscreen.clickBackButton();
	}

}
