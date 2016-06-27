package com.cyberiansoft.test.vnext.utils;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.cyberiansoft.test.reporting.ExtentReportFactory;
import com.cyberiansoft.test.vnext.screens.SwipeableWebDriver;
import com.cyberiansoft.test.vnext.testcases.VNextBaseTestCase;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class VNextTestListener extends TestListenerAdapter implements IInvokedMethodListener  {
	private Object currentClass;
	
	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		
		currentClass = testResult.getInstance();
	}
	
	@Override
	public void onTestFailure(ITestResult result) {
		String filename = "";
		SwipeableWebDriver driver = ((VNextBaseTestCase) currentClass).getAppiumDriver();
	    if (driver != null) {
	    	filename = ((VNextBaseTestCase) currentClass).createScreenshot(driver, "reportvnext/" + ExtentReportFactory.reporttime + "/", "failed" + getTestMethodName(result));
	    }
	    ExtentTest testReporter = ExtentReportFactory.getTest();
	    testReporter.log(LogStatus.INFO, "Failed result: " + testReporter.addScreenCapture(filename));
	    testReporter.log(LogStatus.FAIL, getTestMethodName(result));
	    ExtentReportFactory.closeTest(getTestMethodName(result));
	}
	
	@Override
	public void onTestSkipped (ITestResult result) {
		SwipeableWebDriver driver = ((VNextBaseTestCase) currentClass).getAppiumDriver();
	    if (driver != null) {
	    	((VNextBaseTestCase) currentClass).createScreenshot(driver, "reportvnext/" + ExtentReportFactory.reporttime + "/", "skipped" + getTestMethodName(result));
	    }
	    ExtentTest testReporter = ExtentReportFactory.getTest();
	    testReporter.log(LogStatus.SKIP, getTestMethodName(result));
	    ExtentReportFactory.closeTest(getTestMethodName(result));
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		ExtentTest testReporter = ExtentReportFactory.getTest();
	    testReporter.log(LogStatus.PASS, getTestMethodName(result));
	    ExtentReportFactory.closeTest(getTestMethodName(result));
	}

	@Override
	public void afterInvocation(IInvokedMethod arg0, ITestResult arg1) {
		// TODO Auto-generated method stub
		
	}
	
	private static String getTestMethodName(ITestResult result) {
		return result.getMethod().getConstructorOrMethod().getName();
	}
	
	@Override
	public void onTestStart(ITestResult result) {
		ExtentReportFactory.getTest(getTestMethodName(result), getTestName(result));
	}
	
	@Override
	public void onStart(ITestContext context) {
		
	}
	
	@Override
	public void onFinish(ITestContext context) {
		ExtentReportFactory.closeReport();
	}
	
	private static String getTestName(ITestResult result) {
		return result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(org.testng.annotations.Test.class).testName();
	}
	
	private static String getTestDescription(ITestResult result) {
		return result.getMethod().getDescription();
	}

}
