package com.cyberiansoft.test.vnext.testcases.r360pro.customers;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.VehicleDataField;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.interactions.GeneralWizardInteractions;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.steps.HomeScreenSteps;
import com.cyberiansoft.test.vnext.steps.ScreenNavigationSteps;
import com.cyberiansoft.test.vnext.steps.SearchSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextWholesaleCustomersTestCases extends BaseTestClass {

	@BeforeClass(description = "Wholesale Customers Test Cases")
	public void beforeClass() {
		JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getWholesaleCustomersTestCasesDataPath();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyUserCantCreateWholesaleCustomersOnTheDevice(String rowID,
																			 String description, JSONObject testData) {

		final String wholesaleCustomerNonExists = "XXXXXXXXXXXX";

        HomeScreenSteps.openCustomers();
		VNextCustomersScreen customersScreen = new VNextCustomersScreen();
		customersScreen.switchToWholesaleMode();
		Assert.assertFalse(customersScreen.isAddCustomerButtonDisplayed());
		SearchSteps.openSearchMenu();
		SearchSteps.fillTextSearch(wholesaleCustomerNonExists);
		SearchSteps.cancelSearch();
		Assert.assertTrue(customersScreen.isNothingFoundCaptionDisplayed());
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyUserCanSelectWholesaleCustomerWhenCreateInspection(String rowID,
														  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		VNextCustomersScreen customersScreen = new VNextCustomersScreen();
		customersScreen.switchToWholesaleMode();
		customersScreen.selectCustomer(testwholesailcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
		//HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN, inspectionData.getVehicleInfo().getVINNumber());
		GeneralWizardInteractions.saveViaMenu();
		ScreenNavigationSteps.pressBackButton();
	}
}
