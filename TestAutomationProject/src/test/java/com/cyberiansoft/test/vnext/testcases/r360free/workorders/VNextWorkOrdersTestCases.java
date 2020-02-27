package com.cyberiansoft.test.vnext.testcases.r360free.workorders;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360free.VNextFreeTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehicleVINHistoryScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.steps.CustomersScreenSteps;
import com.cyberiansoft.test.vnext.steps.MenuSteps;
import com.cyberiansoft.test.vnext.steps.VehicleInfoScreenSteps;
import com.cyberiansoft.test.vnext.steps.WorkOrderSteps;
import com.cyberiansoft.test.vnext.testcases.r360free.BaseTestCaseWithDeviceRegistrationAndUserLogin;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextWorkOrdersTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {

	@BeforeClass(description="R360 Work Orders Test Cases")
	public void beforeClass() {
		JSONDataProvider.dataFile = VNextFreeTestCasesDataPaths.getInstance().getWorkOrdersTestCasesDataPath();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testShowSelectedServicesAfterWOIsSaved(String rowID,
																	   String description, JSONObject testData) {

		WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
		WorkOrderSteps.clickAddWorkOrderButton();
		CustomersScreenSteps.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
		VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		vehicleVINHistoryScreen.clickBackButton();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		availableServicesScreen.selectServices(workOrderData.getServicesList());
		workOrdersScreen = availableServicesScreen.saveWorkOrderViaMenu();
		final String workOrderNumber = workOrdersScreen.getFirstWorkOrderNumber();
		WorkOrderSteps.openMenu(workOrderNumber);
		MenuSteps.selectMenuItem(MenuItems.EDIT);
		WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		for (ServiceData serviceData : workOrderData.getServicesList())
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(serviceData.getServiceName()));
		workOrdersScreen = availableServicesScreen.saveWorkOrderViaMenu();
		workOrdersScreen.clickBackButton();
	}

}
