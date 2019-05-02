package com.cyberiansoft.test.ios10_client.utils;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.driverutils.AppiumInicializator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.MainScreen;
import com.cyberiansoft.test.ios10_client.testcases.BaseTestCase;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.testng.*;

import java.time.Instant;

public class SuperListener extends TestListenerAdapter  implements IInvokedMethodListener  {
	private Object currentClass;
	Instant star = Instant.now();
	
	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		
		currentClass = testResult.getInstance();

		star = Instant.now();

	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {    
        if ((method.getTestMethod().getConstructorOrMethod().getMethod().isAnnotationPresent(org.testng.annotations.BeforeClass.class)) 
        		&& (method.getTestResult().getStatus()==2)) {
        	AppiumDriver<MobileElement> appiumdriver = DriverBuilder.getInstance().getAppiumDriver();
        	
        	if (appiumdriver != null) {
        		appiumdriver.quit();

        	} else {

        	}
        	
        }
	}
	
	@Override
	public void onTestFailure(ITestResult result) {
		AppiumDriver<MobileElement> appiumdriver = DriverBuilder.getInstance().getAppiumDriver();
	        if (appiumdriver != null) {
	        	try {
					AppiumUtils.createScreenshot("report", result.getMethod().getMethodName());
	        		//testlogger.log(LogStatus.FAIL, LogAssertions.stepMessage, testlogger.addScreenCapture(((BaseTestCase) currentClass).createScreenshot(appiumdriver, iOSLogger.loggerdir)));
	        	} catch (Exception e) {
	        		e.printStackTrace();
	        		AppiumInicializator.getInstance().initAppium(MobilePlatform.IOS_HD);
	        	}
	        	
	        }
		DriverBuilder.getInstance().getAppiumDriver().closeApp();
		DriverBuilder.getInstance().getAppiumDriver().launchApp();

		MainScreen mainscr = new MainScreen();
		TestUser testuser = ((BaseTestCase) currentClass).getTestUser();
		mainscr.userLogin(testuser.getTestUserName(), testuser.getTestUserPassword());

	}
	
	@Override
	public void onTestSkipped(ITestResult result) {

	}
	
	@Override
	public void onTestSuccess(ITestResult result) {

	}

	@Override
	public void onTestStart(ITestResult result) {

	}
	
	@Override
	public void onStart(ITestContext context) {

	}
	
	@Override
	public void onFinish(ITestContext context) {

	}
	
	private static String getTestName(ITestResult result) {
		return result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(org.testng.annotations.Test.class).testName();
	}
	
	private static String getTestDescription(ITestResult result) {
		return result.getMethod().getDescription();
	}
	
	private static String getTestMethodName(ITestResult result) {
		return result.getMethod().getConstructorOrMethod() .getName();
	}
}

