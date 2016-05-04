package com.cyberiansoft.test.vnext.testcases;

import org.testng.annotations.Test;

import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextLoginScreen;


public class InstallUninstallUpdate extends BaseTestCaseWithDeviceRegistration {
	
	final static String testEmployee = "VD_Employee VD_Employee";
	final static String testEmployeePsw = "1111";
	
	@Test(testName= "Test Case 35900:vNext - Verify 'Password' dialog is shown when user selects an employee from the list, Test Case 35903:vNext - Close Log in dialog", 
			description = "Verify 'Password' dialog is shown when user selects an employee from the list, Test Case 35903:vNext - Close Log in dialog")
	public void testVerifyPasswordDialogIsShownWhenUserSelectsAnEmployeeFromTheList() {
		VNextLoginScreen loginscreen = new VNextLoginScreen(appiumdriver);
		loginscreen.selectEmployee(testEmployee);
		loginscreen.tapCancelButton();	
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
		tapHardwareBackButton();
		loginscreen.waitUserListVisibility();
	}
	
	@Test(testName= "Test Case 35901:vNext - Log in (success case)", description = "Log in (success case)")
	public void testLogInSuccessCase() {
		
		VNextLoginScreen loginscreen = new VNextLoginScreen(appiumdriver);
		VNextHomeScreen homescreen = loginscreen.userLogin(testEmployee, testEmployeePsw);
		loginscreen = homescreen.clickLogoutButton();
		loginscreen.waitUserListVisibility();
	}

}
