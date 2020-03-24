package com.cyberiansoft.test.vnext.testcases.r360free.registrationandnavigation;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.VNextLoginScreen;
import com.cyberiansoft.test.vnext.steps.ScreenNavigationSteps;
import com.cyberiansoft.test.vnext.testcases.r360free.BaseTestCaseWithDeviceRegistrationAndUserLogin;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class InstallUninstallUpdate extends BaseTestCaseWithDeviceRegistrationAndUserLogin {

	@BeforeClass(description="R360 Install/Uninstall/Update Test Cases")
	public void beforeClass() {
	}
	
	final static String testEmployee = "Test User";
	final static String testEmployeePsw = "1111";
	
	@Test(testName= "Test Case 35900:vNext - Verify 'Password' dialog is shown when user selects an employee from the list, Test Case 35903:vNext - Close Log in dialog", 
			description = "Verify 'Password' dialog is shown when user selects an employee from the list, Test Case 35903:vNext - Close Log in dialog")
	public void testVerifyPasswordDialogIsShownWhenUserSelectsAnEmployeeFromTheList() {
		VNextLoginScreen loginscreen = new VNextLoginScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		loginscreen.selectEmployee(testEmployee);
		loginscreen.tapLoginDialogCancelButton();	
	}
	
	@Test(testName= "Test Case 35902:vNext - Log in with incorrect password", description = "Log in with incorrect password")
	public void testLogInWithIncorrectPassword() {
		
		final String testEmployeeWrongPsw = "111222";

		VNextLoginScreen loginscreen = new VNextLoginScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		loginscreen.incorrectUserLogin(testEmployee, testEmployeeWrongPsw);
	}
	
	@Test(testName= "Test Case 35904:vNext - Close Log in dialog with Hardware 'Back' button", description = "Close Log in dialog with Hardware 'Back' button")
	public void testCloseLogInDialogWithHardwareBackButton() {
		VNextLoginScreen loginscreen = new VNextLoginScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		loginscreen.selectEmployee(testEmployee);
        ScreenNavigationSteps.pressBackButton();
		loginscreen.waitUserListVisibility();
	}

}
