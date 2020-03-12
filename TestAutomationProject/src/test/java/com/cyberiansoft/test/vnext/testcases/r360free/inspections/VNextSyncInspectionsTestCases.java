package com.cyberiansoft.test.vnext.testcases.r360free.inspections;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.data.r360free.VNextFreeTestCasesDataPaths;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextSettingsScreen;
import com.cyberiansoft.test.vnext.steps.HomeScreenSteps;
import com.cyberiansoft.test.vnext.steps.InspectionSteps;
import com.cyberiansoft.test.vnext.steps.ScreenNavigationSteps;
import com.cyberiansoft.test.vnext.testcases.r360free.BaseTestCaseWithDeviceRegistrationAndUserLogin;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextSyncInspectionsTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {

	@BeforeClass(description="R360 Sync Inspections Test Cases")
	public void beforeClass() {
		JSONDataProvider.dataFile = VNextFreeTestCasesDataPaths.getInstance().getSyncInspectionsTestCasesDataPath();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testManualVerifyOutgoingSyncMessageIsPushedIntoQueueWhenSaveInspection(String rowID,
																					   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String expectedQueue = "1";

        VNextHomeScreen homeScreen = new VNextHomeScreen();
		VNextSettingsScreen settingsScreen = homeScreen.clickSettingsMenuItem();
		homeScreen = settingsScreen.setManualSendOn().clickBackButton();
		HomeScreenSteps.openInspections();
		InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		ScreenNavigationSteps.pressBackButton();
		Assert.assertEquals(homeScreen.getQueueMessageValue(), expectedQueue);
		homeScreen.clickQueueMessageIcon();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testManualVerifyOutgoingSyncMessageIsPushedIntoQueueWhenSaveSeveralInspection(String rowID,
																							  String description, JSONObject testData) {
		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final int expectedQueue = 5;

        VNextHomeScreen homeScreen = new VNextHomeScreen();
		VNextSettingsScreen settingsScreen = homeScreen.clickSettingsMenuItem();
		homeScreen = settingsScreen.setManualSendOn().clickBackButton();
		HomeScreenSteps.openInspections();
		for (int i = 0; i < expectedQueue-1; i++)
			InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		ScreenNavigationSteps.pressBackButton();
		Assert.assertEquals(homeScreen.getQueueMessageValue(), String.valueOf(expectedQueue));
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testSendMessageFromTheQueueInAutomaticModeSuccessPath(String rowID,
																	  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen();
		VNextSettingsScreen settingsScreen = homeScreen.clickSettingsMenuItem();
		homeScreen = settingsScreen.setManualSendOff().clickBackButton();
		HomeScreenSteps.openInspections();
		InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		ScreenNavigationSteps.pressBackButton();
		Assert.assertFalse(homeScreen.isQueueMessageVisible());
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testSendMessageFromTheQueueInManualModeSuccessPath(String rowID,
																   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String expectedQueue = "1";

        VNextHomeScreen homeScreen = new VNextHomeScreen();
		VNextSettingsScreen settingsScreen = homeScreen.clickSettingsMenuItem();
		homeScreen = settingsScreen.setManualSendOn().clickBackButton();
		HomeScreenSteps.openInspections();
		InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		ScreenNavigationSteps.pressBackButton();
		Assert.assertEquals(homeScreen.getQueueMessageValue(), expectedQueue);
		homeScreen.clickQueueMessageIcon();
		homeScreen.waitUntilQueueMessageInvisible();
		Assert.assertFalse(homeScreen.isQueueMessageVisible());
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testSendMessageAutomaticallyFromTheQueueInAutomaticModeAfterReconnectToNetwork(String rowID,
																							   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final int expectedQueue = 5;

        VNextHomeScreen homeScreen = new VNextHomeScreen();
		AppiumUtils.setNetworkOff();
		VNextSettingsScreen settingsScreen = homeScreen.clickSettingsMenuItem();
		homeScreen = settingsScreen.setManualSendOff().clickBackButton();
		HomeScreenSteps.openInspections();
		for (int i = 0; i < expectedQueue-1; i++)
			InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		ScreenNavigationSteps.pressBackButton();
		AppiumUtils.setAndroidNetworkOn();
		homeScreen.waitUntilQueueMessageInvisible();
		Assert.assertFalse(homeScreen.isQueueMessageVisible());
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testSendMessageFromTheQueueInAutomaticModeFailPath(String rowID,
																   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String expectedQueue = "1";

        VNextHomeScreen homeScreen = new VNextHomeScreen();
		AppiumUtils.setNetworkOff();
		VNextSettingsScreen settingsScreen = homeScreen.clickSettingsMenuItem();
		homeScreen = settingsScreen.setManualSendOff().clickBackButton();
		HomeScreenSteps.openInspections();
		InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		ScreenNavigationSteps.pressBackButton();
		homeScreen.clickQueueMessageIcon();
		BaseUtils.waitABit(3000);
		Assert.assertEquals(homeScreen.getQueueMessageValue(), expectedQueue);
		AppiumUtils.setAndroidNetworkOn();
	}

} 