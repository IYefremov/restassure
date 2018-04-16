package com.cyberiansoft.test.bo.utils;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.config.BOConfigInfo;
import com.cyberiansoft.test.bo.testcases.BaseTestCase;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


public class TestListener extends TestListenerAdapter  implements IInvokedMethodListener  {
	private Object currentClass;
	//private ExtentReports extentreport;
	//private ExtentTest testlogger;
	
	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		currentClass = testResult.getInstance();
	}
	
	@Override
	public void onTestFailure(ITestResult result) {
        if (DriverBuilder.getInstance().getDriver() != null) {
            createScreenshot(DriverBuilder.getInstance().getDriver(), "report/", getTestMethodName(result));
            DriverBuilder.getInstance().getDriver().quit();
            DriverBuilder.getInstance().setDriver(BaseUtils
                    .getBrowserType(BOConfigInfo.getInstance().getDefaultBrowser()));
        }
        ((BaseTestCase) currentClass).setDriver();
	}
	
	@Override
	public void onTestSkipped (ITestResult result) {
        if (DriverBuilder.getInstance().getDriver() != null) {
            createScreenshot(DriverBuilder.getInstance().getDriver(), "report/", getTestMethodName(result));
            DriverBuilder.getInstance().getDriver().quit();
            DriverBuilder.getInstance().setDriver(BaseUtils
                    .getBrowserType(BOConfigInfo.getInstance().getDefaultBrowser()));
        }
        ((BaseTestCase) currentClass).setDriver();
	}
	
	public String createScreenshot(WebDriver driver, String loggerdir, String testcasename) {
		WebDriver driver1 = new Augmenter().augment(driver);
		UUID uuid = UUID.randomUUID();
		File file = ((TakesScreenshot) driver1).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(file , new File(loggerdir + "\\" + testcasename + uuid + ".jpeg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "myscreen" + uuid + ".jpeg";
	}

	@Override
	public void afterInvocation(IInvokedMethod arg0, ITestResult arg1) {
		// TODO Auto-generated method stub
		
	}
	
	private static String getTestMethodName(ITestResult result) {
		return result.getMethod().getConstructorOrMethod().getName();
	}
	
	

}

