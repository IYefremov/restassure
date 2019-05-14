package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.steps.InspectionSteps;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.VNextSettingsScreen;

public class VNextSyncInspectionsTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {

	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/r360-sync-inspections-testcases-data.json";

	@BeforeClass(description="R360 Sync Inspections Test Cases")
	public void beforeClass() {
		JSONDataProvider.dataFile = DATA_FILE;
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testManualVerifyOutgoingSyncMessageIsPushedIntoQueueWhenSaveInspection(String rowID,
																					   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String expectedQueue = "1";

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextSettingsScreen settingsscreen = homescreen.clickSettingsMenuItem();
		homescreen = settingsscreen.setManualSendOn().clickBackButton();
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		homescreen = inspectionsscreen.clickBackButton();
		Assert.assertEquals(homescreen.getQueueMessageValue(), expectedQueue);
		homescreen.clickQueueMessageIcon();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testManualVerifyOutgoingSyncMessageIsPushedIntoQueueWhenSaveSeveralInspection(String rowID,
																							  String description, JSONObject testData) {
		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final int expectedQueue = 5;

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextSettingsScreen settingsscreen = homescreen.clickSettingsMenuItem();
		homescreen = settingsscreen.setManualSendOn().clickBackButton();
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		for (int i = 0; i < expectedQueue-1; i++)
			InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		homescreen = inspectionsscreen.clickBackButton();
		Assert.assertEquals(homescreen.getQueueMessageValue(), String.valueOf(expectedQueue));
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testSendMessageFromTheQueueInAutomaticModeSuccessPath(String rowID,
																	  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextSettingsScreen settingsscreen = homescreen.clickSettingsMenuItem();
		homescreen = settingsscreen.setManualSendOff().clickBackButton();
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		homescreen = inspectionsscreen.clickBackButton();
		Assert.assertFalse(homescreen.isQueueMessageVisible());
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testSendMessageFromTheQueueInManualModeSuccessPath(String rowID,
																   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String expectedQueue = "1";

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextSettingsScreen settingsscreen = homescreen.clickSettingsMenuItem();
		homescreen = settingsscreen.setManualSendOn().clickBackButton();
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		homescreen = inspectionsscreen.clickBackButton();
		Assert.assertEquals(homescreen.getQueueMessageValue(), expectedQueue);
		homescreen.clickQueueMessageIcon();
		homescreen.waitUntilQueueMessageInvisible();
		Assert.assertFalse(homescreen.isQueueMessageVisible());
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testSendMessageAutomaticallyFromTheQueueInAutomaticModeAfterReconnectToNetwork(String rowID,
																							   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final int expectedQueue = 5;

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		AppiumUtils.setNetworkOff();
		VNextSettingsScreen settingsscreen = homescreen.clickSettingsMenuItem();
		homescreen = settingsscreen.setManualSendOff().clickBackButton();
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		for (int i = 0; i < expectedQueue-1; i++)
			InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		homescreen = inspectionsscreen.clickBackButton();
		AppiumUtils.setAndroidNetworkOn();
		homescreen.waitUntilQueueMessageInvisible();
		Assert.assertFalse(homescreen.isQueueMessageVisible());
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testSendMessageFromTheQueueInAutomaticModeFailPath(String rowID,
																   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String expectedQueue = "1";

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		AppiumUtils.setNetworkOff();
		VNextSettingsScreen settingsscreen = homescreen.clickSettingsMenuItem();
		homescreen = settingsscreen.setManualSendOff().clickBackButton();
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		homescreen = inspectionsscreen.clickBackButton();
		homescreen.clickQueueMessageIcon();
		BaseUtils.waitABit(3000);
		Assert.assertEquals(homescreen.getQueueMessageValue(), expectedQueue);
		AppiumUtils.setAndroidNetworkOn();
	}

} 