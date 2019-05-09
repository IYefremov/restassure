package com.cyberiansoft.test.vnext.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.extentreportproviders.ExtentManager;
import com.cyberiansoft.test.extentreportproviders.ExtentTestManager;
import com.cyberiansoft.test.vnext.config.VNextEnvironmentInfo;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.utils.AppContexts;
import com.cyberiansoft.test.vnext.utils.VNextAppUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.plexus.util.FileUtils;
import org.testng.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.xml.XmlSuite;

import java.io.*;
import java.util.*;

public class ExtentTestNGIReporterListenerJS extends TestListenerAdapter implements IInvokedMethodListener, IReporter {


    //private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
    private ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

    @Override
    public synchronized void onStart(ITestContext context) {
        ExtentManager.createInstance(VNextEnvironmentInfo.getInstance().getReportFolderPath() +
                VNextEnvironmentInfo.getInstance().getReportFileName());


        File allureResults = new File("./allure-results");
        if (allureResults.exists()) {
            try {
                FileUtils.deleteDirectory(allureResults);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File allureResultsXml = new File("./target/allure-results");
        if (allureResultsXml.exists()) {
            try {
                FileUtils.deleteDirectory(allureResultsXml);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File videoFolder = new File("./video");
        if (videoFolder.exists()) {
            try {
                FileUtils.deleteDirectory(videoFolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


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
        AppiumUtils.attachAllureLog(Arrays.toString(result.getThrowable().getStackTrace()));
        try {
            AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);
            AppiumUtils.attachAllureScreenshot();
            extentTest.get().log(Status.INFO, "SCREENSHOT", MediaEntityBuilder.createScreenCaptureFromPath(AppiumUtils.createScreenshot(VNextEnvironmentInfo.getInstance().getReportFolderPath(), "fail")).build());
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
        new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
    }

    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        extentTest.get().log(Status.SKIP, "<font color=#2196F3>" + Status.SKIP.toString().toUpperCase() + "</font>");
        extentTest.get().log(Status.INFO, "EXCEPTION = [" + result.getThrowable().getMessage() + "]");
        AppiumUtils.attachAllureLog(Arrays.toString(result.getThrowable().getStackTrace()));
        try {
            AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);
            AppiumUtils.attachAllureScreenshot();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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


    @Override
    public void generateReport(List<XmlSuite> list, List<ISuite> suites, String s) {
        for (ISuite suite : suites) {
            String suiteName = suite.getName();

            Map<String, ISuiteResult> suiteResults = suite.getResults();
            for (ISuiteResult sr : suiteResults.values()) {
                ITestContext testContext = sr.getTestContext();
                System.out.println("Suite: " + suiteName);
                System.out.println("Passed tests: " + testContext.getPassedTests().getAllResults().size());
                System.out.println("Failed tests: " + testContext.getFailedTests().getAllResults().size());
                System.out.println("Skipped tests: " + testContext.getSkippedTests().getAllResults().size());
            }
        }
    }
}
