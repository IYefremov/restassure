package com.cyberiansoft.test.ibs.utils;

import com.cyberiansoft.test.baseutils.AllureUtils;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import org.codehaus.plexus.util.FileUtils;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TestListenerAllure extends TestListenerAdapter implements IInvokedMethodListener, IReporter {

    @Override
    public void onTestStart(ITestResult iTestResult) {}

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.getTestMethod().isTest()) {
            System.out.printf("\n***** STARTING TEST *****\nClass: %s\nMethod: %s\n\n",
                    method.getTestMethod().getRealClass(), method.getTestMethod().getMethodName());
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {}

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("FAILED: " + result.getMethod().getMethodName());
        AllureUtils.attachLog(Arrays.toString(result.getThrowable().getStackTrace()));
        AllureUtils.attachScreenshot();
//        AllureUtils.attachVideo();

        DriverBuilder.getInstance().quitDriver();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("SKIPPED: " + result.getMethod().getMethodName());
        AllureUtils.attachLog(Arrays.toString(result.getThrowable().getStackTrace()));
        AllureUtils.attachScreenshot();
//        AllureUtils.attachVideo();

        DriverBuilder.getInstance().quitDriver();
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println("SUCCESS: " + iTestResult.getMethod().getMethodName());
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        File allureResults = new File("./allure-results");
        if (allureResults.exists()) {
            try {
                FileUtils.deleteDirectory(allureResults);
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
    public void onFinish(ITestContext iTestContext) {
        super.onFinish(iTestContext);
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