package com.cyberiansoft.test.vnext.testcases;

import org.testng.annotations.Test;

import com.cyberiansoft.test.baseutils.AppiumAndroidUtils;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextLoginScreen;


public class InstallUninstallUpdate extends BaseTestCaseWithDeviceRegistration {
	
	final static String testEmployee = "Test User";
	final static String testEmployeePsw = "1111";
	
	@Test(testName= "Test Case 35900:vNext - Verify 'Password' dialog is shown when user selects an employee from the list, Test Case 35903:vNext - Close Log in dialog", 
			description = "Verify 'Password' dialog is shown when user selects an employee from the list, Test Case 35903:vNext - Close Log in dialog")
	public void testVerifyPasswordDialogIsShownWhenUserSelectsAnEmployeeFromTheList() {
		VNextLoginScreen loginscreen = new VNextLoginScreen(appiumdriver);
		loginscreen.selectEmployee(testEmployee);
		loginscreen.tapLoginDialogCancelButton();	
	}
	
	@Test(testName= "Test Case 35902:vNext - Log in with incorrect password", description = "Log in with incorrect password")
	public void testLogInWithIncorrectPassword() {
		
		final String testEmployeeWrongPsw = "111222";
		
		VNextLoginScreen loginscreen = new VNextLoginScreen(appiumdriver);
		loginscreen.incorrectUserLogin(testEmployee, testEmployeeWrongPsw);
	}
	
	@Test(testName= "Test Case 35904:vNext - Close Log in dialog with Hardware 'Back' button", description = "Close Log in dialog with Hardware 'Back' button")
	public void testCloseLogInDialogWithHardwareBackButton() {		
		VNextLoginScreen loginscreen = new VNextLoginScreen(appiumdriver);
		loginscreen.selectEmployee(testEmployee);
		AppiumAndroidUtils.clickHardwareBackButton();
		loginscreen.waitUserListVisibility();
	}
	
	@Test(testName= "Test Case 35901:vNext - Log in from the list of available users (success case)", description = "Log in from the list of available users (success case)")
	public void testLogInSuccessCase() {
		
		new VNextHomeScreen(appiumdriver);
	}

}
