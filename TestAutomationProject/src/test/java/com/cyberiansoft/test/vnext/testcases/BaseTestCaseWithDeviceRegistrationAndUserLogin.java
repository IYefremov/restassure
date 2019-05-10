package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.config.VNextEnvironmentInfo;
import com.cyberiansoft.test.vnext.config.VNextFreeRegistrationInfo;;
import com.cyberiansoft.test.vnext.factories.environments.EnvironmentType;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextLoginScreen;
import com.cyberiansoft.test.vnext.utils.VNextAppUtils;
import org.testng.annotations.BeforeTest;

import java.io.File;

public class BaseTestCaseWithDeviceRegistrationAndUserLogin extends VNextBaseTestCase {

	@BeforeTest(description = "Setting up new suite")
	public void settingUp() throws Exception {

		deviceuser = VNextFreeRegistrationInfo.getInstance().getR360UserUserName();
		devicepsw = VNextFreeRegistrationInfo.getInstance().getR360UserPassword();

		if (envType.equals(EnvironmentType.DEVELOPMENT))
			deviceOfficeUrl = VNextFreeRegistrationInfo.getInstance().getR360BackOfficeStagingURL();
		else if (envType.equals(EnvironmentType.INTEGRATION))
			deviceOfficeUrl = VNextFreeRegistrationInfo.getInstance().getR360BackOfficeIntegrationURL();
		employee = JSonDataParser.getTestDataFromJson(new File("src/test/java/com/cyberiansoft/test/vnext/data/free-device-employee.json"), Employee.class);

		if (VNextEnvironmentInfo.getInstance().installNewBuild()) {
			VNextAppUtils.resetApp();
			registerDevice();

			VNextLoginScreen loginScreen = new VNextLoginScreen(appiumdriver);
			loginScreen.userLogin(employee.getEmployeeName(), employee.getEmployeePassword());
		}
		new VNextHomeScreen(appiumdriver);
	}
}
