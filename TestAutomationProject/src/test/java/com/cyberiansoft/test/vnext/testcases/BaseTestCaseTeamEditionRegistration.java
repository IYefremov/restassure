package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataclasses.WholesailCustomer;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.config.VNextConfigInfo;
import com.cyberiansoft.test.vnext.config.VNextTeamRegistrationInfo;
import com.cyberiansoft.test.vnext.screens.VNextLoginScreen;
import org.testng.annotations.BeforeTest;

import java.io.File;

public class BaseTestCaseTeamEditionRegistration extends VNextBaseTestCase {
	
	protected static RetailCustomer testcustomer;
	protected static WholesailCustomer testwholesailcustomer;
	protected static Employee employee;
	
	@BeforeTest
	public void beforeTest() throws Exception {
		setUp();	
		//VNextAppUtils.resetApp();
		if (VNextConfigInfo.getInstance().installNewBuild()) {
			registerTeamEdition(VNextTeamRegistrationInfo.getInstance().getDeviceDefaultLicenseName());
			employee = JSonDataParser.getTestDataFromJson(new File("src/test/java/com/cyberiansoft/test/vnext/data/team-device-employee.json"), Employee.class);
			VNextLoginScreen loginscreen = new VNextLoginScreen(appiumdriver);
			loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeePassword());
		}
		
		testcustomer = JSonDataParser.getTestDataFromJson(new File("src/test/java/com/cyberiansoft/test/vnext/data/" +
                "test-retail-customer.json"), RetailCustomer.class);
		testwholesailcustomer = JSonDataParser.getTestDataFromJson(new File("src/test/java/com/cyberiansoft/test/vnext/data/test-wholesail-customer.json"), WholesailCustomer.class);
	}

}
