package com.cyberiansoft.test.vnextbo.utils;

import com.cyberiansoft.test.baseutils.AllureUtils;
import com.cyberiansoft.test.dataclasses.TargetProcessTestCaseData;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.targetprocessintegration.model.TPIntegrationService;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.codehaus.plexus.util.FileUtils;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TestListenerAllure extends TestListenerAdapter implements IInvokedMethodListener, IReporter {

    private TPIntegrationService tpIntegrationService = new TPIntegrationService();

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
        //setTestCaseAutomatedField(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("SKIPPED: " + result.getMethod().getMethodName());
        AllureUtils.attachLog(Arrays.toString(result.getThrowable().getStackTrace()));
        AllureUtils.attachScreenshot();
        //setTestCaseAutomatedField(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        //setTestCaseAutomatedField(result);
        System.out.println("SUCCESS: " + result.getMethod().getMethodName());
        //setTestCaseAutomatedField(result);
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

    private void setTestCaseAutomatedField(ITestResult testResult) {
        TestCaseData testCaseData = getTestCasesData(testResult);
        if (testCaseData != null) {
            if (testCaseData.getTargetProcessTestCaseData() != null) {
                for (TargetProcessTestCaseData targetProcessTestCaseData : testCaseData.getTargetProcessTestCaseData()) {
                    try {
                        tpIntegrationService.setTestCaseAutomatedField(targetProcessTestCaseData.getTestCaseID());
                    } catch (UnirestException e) {
                        e.printStackTrace();
                    } catch (UnrecognizedPropertyException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private TestCaseData getTestCasesData(ITestResult testResult) {
        Object[] parameters = testResult.getParameters();
        return JSonDataParser.getTestDataFromJson((parameters[parameters.length - 1].toString()), TestCaseData.class);
    }
}