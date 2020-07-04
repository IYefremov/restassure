package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.steps.HomeScreenSteps;
import com.cyberiansoft.test.vnext.steps.InspectionSteps;
import com.cyberiansoft.test.vnext.steps.ScreenNavigationSteps;
import com.cyberiansoft.test.vnext.steps.WizardScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.ClaimInfoScreenValidations;
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
		ClaimInfoScreenValidations.validateInsuranceCompanyVisible(true);
		ClaimInfoScreenValidations.validateClaimVisible(true);
		ClaimInfoScreenValidations.validatePolicyNumberVisible(true);
		ClaimInfoScreenValidations.validateDeductibleVisible(true);

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
		ClaimInfoScreenValidations.validateInsuranceCompanyVisible(true);
		ClaimInfoScreenValidations.validateClaimVisible(true);
		ClaimInfoScreenValidations.validatePolicyNumberVisible(true);
		ClaimInfoScreenValidations.validateDeductibleVisible(true);

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
