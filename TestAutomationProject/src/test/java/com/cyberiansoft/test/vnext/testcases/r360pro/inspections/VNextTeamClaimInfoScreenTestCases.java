package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamClaimInfoScreenTestCases extends BaseTestClass {

	@BeforeClass(description="Team Claim Info Test Cases")
	public void beforeClass() {
		JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getClaimInfoScreenTestCasesDataPath();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyClaimInfoScreenVisibleForInspectionIfClaimInfoEqualsON(String rowID,
																		String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR, inspectionData);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.CLAIM);
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		Assert.assertTrue(claimInfoScreen.isInsuranceCompanyFieldVisible());
		Assert.assertTrue(claimInfoScreen.isClaimNumberFieldVisible());
		Assert.assertTrue(claimInfoScreen.isPolicyNumberFieldVisible());
		Assert.assertTrue(claimInfoScreen.isDeductibleFieldVisible());

		InspectionSteps.cancelInspection();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyClaimInfoScreenIsNotVisibleForInspectionIfClaimInfoEqualsOFF(String rowID,
																				 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR2, inspectionData);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		vehicleInfoScreen.clickScreenTitleCaption();
		Assert.assertFalse(vehicleInfoScreen.isScreenPresentInChangeScreenPopoverList(ScreenType.CLAIM.name()));
		WizardScreenSteps.selectWizardScreen(ScreenType.VEHICLE_INFO);


		InspectionSteps.cancelInspection();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyClaimInfoScreenVisibleForWOIfClaimInfoEqualsON(String rowID,
																					   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR, inspectionData);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.CLAIM);
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		Assert.assertTrue(claimInfoScreen.isInsuranceCompanyFieldVisible());
		Assert.assertTrue(claimInfoScreen.isClaimNumberFieldVisible());
		Assert.assertTrue(claimInfoScreen.isPolicyNumberFieldVisible());
		Assert.assertTrue(claimInfoScreen.isDeductibleFieldVisible());

		InspectionSteps.cancelInspection();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyClaimInfoScreenIsNotVisibleForWOIfClaimInfoEqualsOFF(String rowID,
																		 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR2, inspectionData);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		vehicleInfoScreen.clickScreenTitleCaption();
		Assert.assertFalse(vehicleInfoScreen.isScreenPresentInChangeScreenPopoverList("Claim"));
		WizardScreenSteps.selectWizardScreen(ScreenType.VEHICLE_INFO);

		InspectionSteps.cancelInspection();
		ScreenNavigationSteps.pressBackButton();
	}

}
