package com.cyberiansoft.test.vnext.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.extentreportproviders.ExtentManager;
import com.cyberiansoft.test.extentreportproviders.ExtentTestManager;
import com.cyberiansoft.test.vnext.config.VNextConfigInfo;
import com.cyberiansoft.test.vnext.utils.AppContexts;
import com.cyberiansoft.test.vnext.utils.VNextAppUtils;
import org.apache.commons.lang3.StringUtils;
import org.testng.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.Date;

public class ExtentTestNGIReporterListener extends TestListenerAdapter implements IInvokedMethodListener {
	
	
	//private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
	private ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
	
    @Override
	public synchronized void onStart(ITestContext context) {
    	ExtentManager.createInstance(VNextConfigInfo.getInstance().getReportFolderPath() +
    			VNextConfigInfo.getInstance().getReportFileName());
    	//ExtentTest parent = extent.createTest(getClass().getName());
        //parentTest.set(parent);
	}

	@Override
	public synchronized void onFinish(ITestContext context) {
		ExtentManager.getInstance().flush();
	}
	
	@Override
	public synchronized void onTestStart(ITestResult result) {
		ExtentTest extent;
		if (ExtentTestManager.getTest() == null) {
			ExtentTestManager.createTest(result.getTestClass().getName());
		}
		if ( getTestParams(result).isEmpty() ) {
			extent = ExtentTestManager.getTest().createNode(result.getMethod().getMethodName());
        }

        else {
            if ( getTestParams(result).split(",")[0].contains(result.getMethod().getMethodName()) ) {
            	extent = ExtentTestManager.getTest().createNode(getTestParams(result).split(",")[0], getTestParams(result).split(",")[1]);
            }

            else {
            	extent = ExtentTestManager.getTest().createNode(result.getMethod().getMethodName(), getTestParams(result).split(",")[1]);
            }
        }
		
		extent.getModel().setStartTime(getTime(result.getStartMillis()));
		
		extentTest.set(extent);
		//ExtentTest child = parentTest.get().createNode(result.getMethod().getMethodName());
        //test.set(child);
	}

	@Override
	public synchronized void onTestSuccess(ITestResult result) {
		extentTest.get().log(Status.PASS, "<font color=#00af00>" + Status.PASS.toString().toUpperCase() + "</font>");
		extentTest.get().getModel().setEndTime(getTime(result.getEndMillis()));
	}

	@Override
	public synchronized void onTestFailure(ITestResult result) {
		extentTest.get().log(Status.FAIL, "<font color=#F7464A>" + Status.FAIL.toString().toUpperCase() + "</font>");
		extentTest.get().log(Status.INFO, "EXCEPTION = [" + result.getThrowable().getMessage() + "]");
		try {
			AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);
			extentTest.get().log(Status.INFO, "SCREENSHOT", MediaEntityBuilder.createScreenCaptureFromPath(AppiumUtils.createScreenshot(VNextConfigInfo.getInstance().getReportFolderPath(), "fail")).build());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if ( !getTestParams(result).isEmpty() ) {
			extentTest.get().log(Status.INFO, "STACKTRACE" + getStrackTrace(result));
		}
		extentTest.get().getModel().setEndTime(getTime(result.getEndMillis()));
		AppiumUtils.setNetworkOn();
		VNextAppUtils.restartApp();
	}

	@Override
	public synchronized void onTestSkipped(ITestResult result) {
		extentTest.get().log(Status.SKIP, "<font color=#2196F3>" + Status.SKIP.toString().toUpperCase() + "</font>");
		extentTest.get().log(Status.INFO, "EXCEPTION = [" + result.getThrowable().getMessage() + "]");
		extentTest.get().getModel().setEndTime(getTime(result.getEndMillis()));
	}

	@Override
	public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		
	}
	
	private String getStrackTrace(ITestResult result) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        result.getThrowable().printStackTrace(printWriter);

        return "<br/>\n" + writer.toString().replace(System.lineSeparator(), "<br/>\n");
    }
	
	private String getTestParams(ITestResult tr) {
        TestNG_ConsoleRunner runner = new TestNG_ConsoleRunner();

        return runner.getTestParams(tr);
    }
	
	private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        
        return calendar.getTime();
    }

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult result) {
		AfterClass testAnnotation = (AfterClass) result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(AfterClass.class);
		if (testAnnotation != null) {
			ExtentTestManager.getTest().getModel().setEndTime(getTime(result.getEndMillis()));
		}
		
	}

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult result) {
		BeforeClass testAnnotation = (BeforeClass) result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(BeforeClass.class);
		if (testAnnotation != null) {
			if (!StringUtils.isEmpty(method.getTestMethod().getDescription()))
				ExtentTestManager.createTest(method.getTestMethod().getDescription());
			else
				ExtentTestManager.createTest(result.getMethod().getTestClass().getName());
			ExtentTestManager.getTest().getModel().setStartTime(getTime(result.getStartMillis()));
		}
	}
}
