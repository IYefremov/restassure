package com.cyberiansoft.test.vnext.utils;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import org.openqa.selenium.WebDriver;
import org.testng.*;


public class VNextTestListener extends TestListenerAdapter implements IInvokedMethodListener  {
	
	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		
	}
	
	@Override
	public void onTestFailure(ITestResult result) {
		String filename = "";
        WebDriver appiumdriver = ChromeDriverProvider.INSTANCE.getMobileChromeDriver();
		WebDriver webdriver = DriverBuilder.getInstance().getDriver();
	    if (appiumdriver != null) {
	    	//filename = AppiumUtils.createScreenshot("reportvnext/" + ExtentReportFactory.reporttime + "/", "failed" + getTestMethodName(result));
	    }
	    
	    if (webdriver != null) {
	    	webdriver.quit();
	    }
	    
	    /*if (appiumdriver.findElements(By.xpath("//div[@data-page='null']")).size() < 1) {
	    	AppiumUtils.setAndroidNetworkOn();
	    	((VNextBaseTestCase) currentClass).resetApp();
	    	((VNextBaseTestCase) currentClass).setUp();
	    	//((VNextBaseTestCase) currentClass).setAndroidNetworkOn();
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
	    AppiumUtils.setAndroidNetworkOn();
	    VNextAppUtils.restartApp();
	}
	
	@Override
	public void onTestSkipped (ITestResult result) {
        WebDriver driver = ChromeDriverProvider.INSTANCE.getMobileChromeDriver();
	    if (driver != null) {
	    	//AppiumUtils.createScreenshot("reportvnext/" + ExtentReportFactory.reporttime + "/", "skipped" + getTestMethodName(result));
	    }
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {

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
		//ExtentReportFactory.getTest(getTestMethodName(result), getTestName(result));
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

}
