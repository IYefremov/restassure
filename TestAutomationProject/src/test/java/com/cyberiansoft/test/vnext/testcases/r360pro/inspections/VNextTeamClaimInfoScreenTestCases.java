package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import org.json.simple.JSONObject;
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

	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/team-claim-info-screen-testcases-data.json";

	@BeforeClass(description="Team Claim Info Test Cases")
	public void beforeClass() {
		JSONDataProvider.dataFile = DATA_FILE;
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyClaimInfoScreenVisibleForInspectionIfClaimInfoEqualsON(String rowID,
																		String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.switchToWholesaleMode();
		customersScreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
		vehicleInfoScreen.changeScreen(ScreenType.CLAIM);
		VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertTrue(claimInfoScreen.isInsuranceCompanyFieldVisible());
		Assert.assertTrue(claimInfoScreen.isClaimNumberFieldVisible());
		Assert.assertTrue(claimInfoScreen.isPolicyNumberFieldVisible());
		Assert.assertTrue(claimInfoScreen.isDeductibleFieldVisible());

		inspectionsScreen = claimInfoScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyClaimInfoScreenIsNotVisibleForInspectionIfClaimInfoEqualsOFF(String rowID,
																				 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.switchToWholesaleMode();
		customersScreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR2);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
		vehicleInfoScreen.clickScreenTitleCaption();
		Assert.assertFalse(vehicleInfoScreen.isScreenPresentInChangeScreenPopoverList(ScreenType.CLAIM.name()));
		AppiumUtils.clickHardwareBackButton();

		inspectionsScreen = vehicleInfoScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyClaimInfoScreenVisibleForWOIfClaimInfoEqualsON(String rowID,
																					   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.switchToWholesaleMode();
		customersScreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
		vehicleInfoScreen.changeScreen(ScreenType.CLAIM);
		VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertTrue(claimInfoScreen.isInsuranceCompanyFieldVisible());
		Assert.assertTrue(claimInfoScreen.isClaimNumberFieldVisible());
		Assert.assertTrue(claimInfoScreen.isPolicyNumberFieldVisible());
		Assert.assertTrue(claimInfoScreen.isDeductibleFieldVisible());

		inspectionsScreen = claimInfoScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyClaimInfoScreenIsNotVisibleForWOIfClaimInfoEqualsOFF(String rowID,
																		 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.switchToWholesaleMode();
		customersScreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR2);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
		vehicleInfoScreen.clickScreenTitleCaption();
		Assert.assertFalse(vehicleInfoScreen.isScreenPresentInChangeScreenPopoverList("Claim"));
		AppiumUtils.clickHardwareBackButton();

		inspectionsScreen = vehicleInfoScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

}
