package com.cyberiansoft.test.vnext.utils;

import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.reporting.ExtentReportFactory;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;


public class VNextTestListener extends TestListenerAdapter implements IInvokedMethodListener  {
	
	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		
	}
	
	@Override
	public void onTestFailure(ITestResult result) {
		String filename = "";
		AppiumDriver<MobileElement> appiumdriver = DriverBuilder.getInstance().getAppiumDriver();
		WebDriver webdriver = DriverBuilder.getInstance().getDriver();
	    if (appiumdriver != null) {
	    	filename = AppiumUtils.createScreenshot("reportvnext/" + ExtentReportFactory.reporttime + "/", "failed" + getTestMethodName(result));
	    }
	    
	    if (webdriver != null) {
	    	webdriver.quit();
	    }
	    
	    ExtentTest testReporter = ExtentReportFactory.getTest();
	    testReporter.log(LogStatus.INFO, "Failed result: " + testReporter.addScreenCapture(filename));
	    testReporter.log(LogStatus.FAIL, getTestMethodName(result));
	    ExtentReportFactory.closeTest(getTestMethodName(result));
	    
	    /*if (appiumdriver.findElements(By.xpath("//div[@data-page='null']")).size() < 1) {
	    	AppiumUtils.setNetworkOn();
	    	((VNextBaseTestCase) currentClass).resetApp();
	    	((VNextBaseTestCase) currentClass).setUp();
	    	//((VNextBaseTestCase) currentClass).setNetworkOn();
	    	try {
	    		((VNextBaseTestCase) currentClass).registerDevice();
	    	} catch (Exception e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}
	    	try {
	    		//((VNextBaseTestCase) currentClass).registerDevice();
	    	} catch (Exception e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}
	    }*/
	    AppiumUtils.setNetworkOn();
	    VNextAppUtils.restartApp();
	    VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
	}
	
	@Override
	public void onTestSkipped (ITestResult result) {
		AppiumDriver<MobileElement> driver = DriverBuilder.getInstance().getAppiumDriver();
	    if (driver != null) {
	    	AppiumUtils.createScreenshot("reportvnext/" + ExtentReportFactory.reporttime + "/", "skipped" + getTestMethodName(result));
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
