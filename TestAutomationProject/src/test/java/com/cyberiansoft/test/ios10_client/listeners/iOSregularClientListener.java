package com.cyberiansoft.test.ios10_client.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.driverutils.AppiumInicializator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.extentreportproviders.ExtentManager;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularMainScreen;
import com.cyberiansoft.test.ios10_client.testcases.BaseTestCase;
import com.cyberiansoft.test.ios10_client.utils.TestUser;
import com.cyberiansoft.test.vnext.config.VNextConfigInfo;
import com.cyberiansoft.test.vnext.listeners.TestNG_ConsoleRunner;
import org.testng.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.Date;

public class iOSregularClientListener extends TestListenerAdapter implements IInvokedMethodListener

    {


        //private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
        private ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

        @Override
        public synchronized void onStart(ITestContext context) {
        ExtentManager.createInstance("report/" +
                VNextConfigInfo.getInstance().getReportFileName());
            /*try {
                FileUtils.cleanDirectory(new File("report"));
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }

        @Override
        public synchronized void onFinish(ITestContext context) {
        ExtentManager.getInstance().flush();
    }

        @Override
        public synchronized void onTestStart(ITestResult result) {
        ExtentTest extent;

        if ( getTestParams(result).isEmpty() ) {
            extent = ExtentManager.getInstance().createTest(result.getMethod().getMethodName());
        }

        else {
            if ( getTestParams(result).split(",")[0].contains(result.getMethod().getMethodName()) ) {
                extent = ExtentManager.getInstance().createTest(getTestParams(result).split(",")[0], getTestParams(result).split(",")[1]);
            }

            else {
                extent = ExtentManager.getInstance().createTest(result.getMethod().getMethodName(), getTestParams(result).split(",")[1]);
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
            extentTest.get().log(Status.INFO, "SCREENSHOT", MediaEntityBuilder.createScreenCaptureFromPath(AppiumUtils.createScreenshot("report", "fail")).build());
        } catch (org.openqa.selenium.NoSuchSessionException e) {
            AppiumInicializator.getInstance().initAppium(MobilePlatform.IOS_REGULAR);
            //DriverBuilder.getInstance().setAppiumDriver(MobilePlatform.IOS_REGULAR);
            //DriverBuilder.getInstance().getAppiumDriver().launchApp();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            AppiumInicializator.getInstance().initAppium(MobilePlatform.IOS_REGULAR);
            /*DriverBuilder.getInstance().getAppiumDriver().quit();
            DriverBuilder.getInstance().setAppiumDriver(MobilePlatform.IOS_REGULAR);
            DriverBuilder.getInstance().getAppiumDriver().launchApp();*/
            e.printStackTrace();
        }
        if ( !getTestParams(result).isEmpty() ) {
            extentTest.get().log(Status.INFO, "STACKTRACE" + getStrackTrace(result));
        }
        extentTest.get().getModel().setEndTime(getTime(result.getEndMillis()));
        DriverBuilder.getInstance().getAppiumDriver().closeApp();
        DriverBuilder.getInstance().getAppiumDriver().launchApp();
        RegularMainScreen mainscr = new RegularMainScreen();
        TestUser testuser = ((BaseTestCase) result.getInstance()).getTestUser();
        RegularHomeScreen homescreen = mainscr.userLogin(testuser.getTestUserName(), testuser.getTestUserPassword());

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


    }

        @Override
        public void beforeInvocation(IInvokedMethod method, ITestResult result) {

    }
}
