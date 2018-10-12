package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataclasses.WholesailCustomer;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.config.VNextConfigInfo;
import com.cyberiansoft.test.vnext.screens.VNextLoginScreen;
import io.appium.java_client.android.AndroidDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BaseTestCaseTeamEditionRegistration extends VNextBaseTestCase {
	
	protected static RetailCustomer testcustomer;
	protected static WholesailCustomer testwholesailcustomer;

	
	@BeforeTest
	@Parameters("device.lecense")
	public void beforeTest(ITestContext context, String deviceLicense) throws Exception {
		setUp();
		employee = JSonDataParser.getTestDataFromJson(new File("src/test/java/com/cyberiansoft/test/vnext/data/team-device-employee.json"), Employee.class);

		if (VNextConfigInfo.getInstance().installNewBuild()) {
			registerTeamEdition(deviceLicense);

		}

		VNextLoginScreen loginscreen = new VNextLoginScreen(appiumdriver);
		loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeePassword());
		
		testcustomer = JSonDataParser.getTestDataFromJson(new File("src/test/java/com/cyberiansoft/test/vnext/data/" +
                "test-retail-customer.json"), RetailCustomer.class);
		testwholesailcustomer = JSonDataParser.getTestDataFromJson(new File("src/test/java/com/cyberiansoft/test/vnext/data/test-wholesail-customer.json"), WholesailCustomer.class);



		FileWriter fileWriter = new FileWriter("data/memory.csv", true);
		fileWriter.append(getClass().getName() + "_start" + ",");
		HashMap<String, Integer> memoryInfo = getMemoryInfo((AndroidDriver) appiumdriver);
		Iterator it = memoryInfo.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			fileWriter.append(pair.getValue() + ",");
		}
		fileWriter.append("\r\n");
		fileWriter.flush();
		fileWriter.close();
	}

	@AfterTest
	public void beforeTest(ITestContext context) throws Exception {
		FileWriter fileWriter = new FileWriter("data/memory.csv", true);
		fileWriter.append(getClass().getName() + "_end" + ",");
		HashMap<String, Integer> memoryInfo = getMemoryInfo((AndroidDriver) appiumdriver);
		Iterator it = memoryInfo.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			fileWriter.append(pair.getValue() + ",");
		}

		fileWriter.flush();
		fileWriter.close();
	}


	private HashMap<String, Integer> getMemoryInfo(AndroidDriver driver) throws Exception {
		List<List<Object>> data = driver.getPerformanceData("com.automobiletechnologies.ReconProClient", "memoryinfo", 10);
		HashMap<String, Integer> readableData = new HashMap<>();
		for (int i = 0; i < data.get(0).size(); i++) {
			int val;
			if (data.get(1).get(i) == null) {
				val = 0;
			} else {
				val = Integer.parseInt((String) data.get(1).get(i));
			}
			readableData.put((String) data.get(0).get(i), val);
		}
		return readableData;
	}

}
