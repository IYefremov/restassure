package com.cyberiansoft.test.vnext.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.VNextSettingsScreen;

public class VNextSyncInspectionsTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {
	
	@Test(testName= "Test Case 36311:vNext - Manual - Verify outgoing sync message is pushed into queue when save inspection", 
			description = "Manual - Verify outgoing sync message is pushed into queue when save inspection")
	public void testManualVerifyOutgoingSyncMessageIsPushedIntoQueueWhenSaveInspection() {
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextSettingsScreen settingsscreen = homescreen.clickSettingsMenuItem();
		homescreen = settingsscreen.setManualSendOn().clickBackButton();
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		inspectionsscreen.createSimpleInspection();
		homescreen = inspectionsscreen.clickBackButton();
		Assert.assertEquals(homescreen.getQueueMessageValue(), "1");
		homescreen.clickQueueMessageIcon();
		homescreen.waitABit(2000);
	}

	@Test(testName= "Test Case 36312:vNext - Manual - Verify outgoing sync message is pushed into queue when save several inspections", 
			description = "Manual - Verify outgoing sync message is pushed into queue when save several inspections")
	public void testManualVerifyOutgoingSyncMessageIsPushedIntoQueueWhenSaveSeveralInspection() {
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextSettingsScreen settingsscreen = homescreen.clickSettingsMenuItem();
		homescreen = settingsscreen.setManualSendOn().clickBackButton();
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		for (int i = 0; i < 4; i++)
			inspectionsscreen.createSimpleInspection();
		homescreen = inspectionsscreen.clickBackButton();
		Assert.assertEquals(homescreen.getQueueMessageValue(), "5");
	}
	
	@Test(testName= "Test Case 36449:vNext - Send message from the queue in Automatic Mode (success path)", 
			description = "Send message from the queue in Automatic Mode (success path)")
	public void testSendMessageFromTheQueueInAutomaticModeSuccessPath() {
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextSettingsScreen settingsscreen = homescreen.clickSettingsMenuItem();
		homescreen = settingsscreen.setManualSendOff().clickBackButton();
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		inspectionsscreen.createSimpleInspection();
		homescreen = inspectionsscreen.clickBackButton();
		Assert.assertEquals(homescreen.getQueueMessageValue(), "");		
	}
	
	@Test(testName= "Test Case 36490:vNext - Send message from the queue in Manual Mode (success path)", 
			description = "Send message from the queue in Manual Mode (success path)")
	public void testSendMessageFromTheQueueInManualModeSuccessPath() {
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextSettingsScreen settingsscreen = homescreen.clickSettingsMenuItem();
		homescreen = settingsscreen.setManualSendOn().clickBackButton();
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		inspectionsscreen.createSimpleInspection();
		homescreen = inspectionsscreen.clickBackButton();
		Assert.assertEquals(homescreen.getQueueMessageValue(), "1");
		homescreen.clickQueueMessageIcon();
		homescreen.waitABit(3000);
		Assert.assertEquals(homescreen.getQueueMessageValue(), "");
	}
	
	@Test(testName= "Test Case 36510:vNext - Send messages automatically from the queue in Automatic Mode after reconnect to network", 
			description = "Send messages automatically from the queue in Automatic Mode after reconnect to network")
	public void testSendMessageAutomaticallyFromTheQueueInAutomaticModeAfterReconnectToNetwork() {
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		setNetworkOff();
		homescreen.waitABit(13000);
		VNextSettingsScreen settingsscreen = homescreen.clickSettingsMenuItem();
		homescreen = settingsscreen.setManualSendOff().clickBackButton();
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		for (int i = 0; i < 4; i++)
			inspectionsscreen.createSimpleInspection();
		homescreen = inspectionsscreen.clickBackButton();
		setNetworkOn();
		homescreen.waitABit(20000);
		Assert.assertEquals(homescreen.getQueueMessageValue(), "");
	}
	
	@Test(testName= "Test Case 36497:vNext - Send message from the queue in Automatic Mode (fail path)", 
			description = "Send message from the queue in Automatic Mode (fail path)")
	public void testSendMessageFromTheQueueInAutomaticModeFailPath() {
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		setNetworkOff();
		VNextSettingsScreen settingsscreen = homescreen.clickSettingsMenuItem();
		homescreen = settingsscreen.setManualSendOff().clickBackButton();
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		inspectionsscreen.createSimpleInspection();
		homescreen = inspectionsscreen.clickBackButton();
		homescreen.clickQueueMessageIcon();
		homescreen.waitABit(3000);
		Assert.assertEquals(homescreen.getQueueMessageValue(), "1");
		setNetworkOn();
	}
	
}
