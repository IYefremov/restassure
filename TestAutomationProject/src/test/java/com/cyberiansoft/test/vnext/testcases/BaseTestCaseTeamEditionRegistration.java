package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataclasses.WholesailCustomer;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.config.VNextTeamRegistrationInfo;
import com.cyberiansoft.test.vnext.screens.VNextLoginScreen;
import org.testng.annotations.BeforeTest;

public class BaseTestCaseTeamEditionRegistration extends VNextBaseTestCase {
	
	protected static RetailCustomer testcustomer;
	protected static WholesailCustomer testwholesailcustomer;
	
	@BeforeTest
	public void beforeTest() throws Exception {
		setUp();	
		//VNextAppUtils.resetApp();
		registerTeamEdition(VNextTeamRegistrationInfo.getInstance().getDeviceDefaultLicenseName());
		Employee employee = JSonDataParser.getTestDataFromJson("src/test/java/com/cyberiansoft/test/vnext/data/team-device-employee.json", Employee.class);
		VNextLoginScreen loginscreen = new VNextLoginScreen(appiumdriver);
		loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeePassword());
		
		testcustomer = JSonDataParser.getTestDataFromJson("src/test/java/com/cyberiansoft/test/vnext/data/" +
                "test-retail-customer.json", RetailCustomer.class);
		testwholesailcustomer = JSonDataParser.getTestDataFromJson("src/test/java/com/cyberiansoft/test/vnext/data/test-wholesail-customer.json", WholesailCustomer.class);
	}

}
