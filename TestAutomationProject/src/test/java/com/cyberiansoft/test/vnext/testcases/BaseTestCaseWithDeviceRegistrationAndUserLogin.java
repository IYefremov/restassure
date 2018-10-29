package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.config.VNextUserRegistrationInfo;
import com.cyberiansoft.test.vnext.screens.VNextLoginScreen;
import com.cyberiansoft.test.vnext.utils.VNextAppUtils;
import org.testng.annotations.BeforeClass;

import java.io.File;

public class BaseTestCaseWithDeviceRegistrationAndUserLogin extends VNextBaseTestCase {
	
	final static String testEmployee = VNextUserRegistrationInfo.getInstance().getDeviceEmployeeName();
	final static String testEmployeePsw = VNextUserRegistrationInfo.getInstance().getDeviceEmployeePassword();



	@BeforeClass(description = "Setting up new suite")
	public void settingUp() throws Exception {

		setUp();
		AppiumUtils.setNetworkOn();
		VNextAppUtils.resetApp();
		registerDevice();
		//AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);
		//AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);

		employee = JSonDataParser.getTestDataFromJson(new File("src/test/java/com/cyberiansoft/test/vnext/data/free-device-employee.json"), Employee.class);
		VNextLoginScreen loginScreen = new VNextLoginScreen(appiumdriver);
		loginScreen.userLogin(employee.getEmployeeName(), employee.getEmployeePassword());
	}
}
