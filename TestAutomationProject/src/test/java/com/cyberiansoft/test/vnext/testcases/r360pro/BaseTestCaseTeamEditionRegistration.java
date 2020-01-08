package com.cyberiansoft.test.vnext.testcases.r360pro;

import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataclasses.WholesailCustomer;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.config.VNextEnvironmentInfo;
import com.cyberiansoft.test.vnext.screens.VNextLoginScreen;
import com.cyberiansoft.test.vnext.testcases.VNextBaseTestCase;
import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.File;

public class BaseTestCaseTeamEditionRegistration extends VNextBaseTestCase {
    protected static RetailCustomer testcustomer;
    protected static WholesailCustomer testwholesailcustomer;

    @BeforeTest
    @Parameters("device.lecense")
    public void beforeTest(ITestContext context, @Optional("OKramar_android2") String deviceLicense) throws Exception {
        employee = JSonDataParser.getTestDataFromJson(new File("src/test/java/com/cyberiansoft/test/vnext/data/team-device-employee.json"), Employee.class);
        if (VNextEnvironmentInfo.getInstance().installNewBuild())
            registerTeamEdition(deviceLicense);

        VNextLoginScreen loginscreen = new VNextLoginScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeePassword());

        testcustomer = JSonDataParser.getTestDataFromJson(new File("src/test/java/com/cyberiansoft/test/vnext/data/" +
                "test-retail-customer.json"), RetailCustomer.class);
        testwholesailcustomer = JSonDataParser.getTestDataFromJson(new File("src/test/java/com/cyberiansoft/test/vnext/data/test-wholesail-customer.json"), WholesailCustomer.class);
    }
}
