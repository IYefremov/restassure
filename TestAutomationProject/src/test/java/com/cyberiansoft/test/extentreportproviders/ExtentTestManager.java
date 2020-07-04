package com.cyberiansoft.test.extentreportproviders;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManager {

	protected static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<>();
	//protected static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
    private static ExtentReports extent = ExtentManager.getInstance();
    
    public synchronized static ExtentTest getTest() {
        return parentTest.get();
    }
    
    public synchronized static ExtentTest createTest(String name) {
        ExtentTest test = extent.createTest(name);

        parentTest.set(test);
        return getTest();
    }
    
    public synchronized static ExtentTest createTest(String name, String description) {
        ExtentTest test = extent.createTest(name);

        parentTest.set(test);
        return getTest();
    }
}
