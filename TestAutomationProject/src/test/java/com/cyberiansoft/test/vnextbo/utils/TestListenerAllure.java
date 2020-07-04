package com.cyberiansoft.test.vnextbo.utils;

import com.cyberiansoft.test.baseutils.AllureUtils;
import com.cyberiansoft.test.dataclasses.TargetProcessTestCaseData;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.targetprocessintegration.dto.EntityStatesDTO;
import com.cyberiansoft.test.targetprocessintegration.dto.EntityTypeDTO;
import com.cyberiansoft.test.targetprocessintegration.dto.ProjectDTO;
import com.cyberiansoft.test.targetprocessintegration.enums.TestCaseRunStatus;
import com.cyberiansoft.test.targetprocessintegration.model.TPIntegrationService;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.plexus.util.FileUtils;
import org.openqa.selenium.NoSuchElementException;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestListenerAllure extends TestListenerAdapter implements IInvokedMethodListener, IReporter {

    @Getter
    @Setter
    private static Map<String, String> testToTestRunMap = new HashMap<>();
    @Getter
    @Setter
    private static String testPlanRunId;

    private TPIntegrationService tpIntegrationService = new TPIntegrationService();

    @Override
    public void onTestStart(ITestResult testResult) {
        if (!testToTestRunMap.isEmpty()) {
            boolean testShouldBeExecuted = false;
            if (getTestCasesData(testResult) != null && getTestCasesData(testResult).getTargetProcessTestCaseData() != null) {
                List<String> targetProcessTestCaseIds =
                        this.getTestCasesData(testResult).getTargetProcessTestCaseData()
                                .stream().map(TargetProcessTestCaseData::getTestCaseID)
                                .collect(Collectors.toList());
                for (String id : targetProcessTestCaseIds) {
                    if (testToTestRunMap.containsKey(id)) {
                        testShouldBeExecuted = true;
                        break;
                    }
                }
            }

            if (!testShouldBeExecuted)
                throw new SkipException("Test is not in desired suite");
        }

    }

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
    public void onTestFailure(ITestResult testResult) {
        System.out.println("FAILED: " + testResult.getMethod().getMethodName());
        AllureUtils.attachLog(Arrays.toString(testResult.getThrowable().getStackTrace()));
        AllureUtils.attachScreenshot();

        if (!testToTestRunMap.isEmpty()) {
            if (getTestCasesData(testResult) != null && getTestCasesData(testResult).getTargetProcessTestCaseData() != null)
                this.getTestCasesData(testResult).getTargetProcessTestCaseData()
                        .stream().map(TargetProcessTestCaseData::getTestCaseID)
                        .forEach(id -> {
                            try {
                                tpIntegrationService.setTestCaseRunStatus(testToTestRunMap.get(id), TestCaseRunStatus.FAILED, "Hello from automation :)");
                            } catch (UnirestException | IOException e) {
                                e.printStackTrace();
                            }
                        });
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("SKIPPED: " + result.getMethod().getMethodName());
        AllureUtils.attachLog(Arrays.toString(result.getThrowable().getStackTrace()));
        AllureUtils.attachScreenshot();
        //setTestCaseAutomatedField(result);
    }

    @Override
    public void onTestSuccess(ITestResult testResult) {
        //setTestCaseAutomatedField(result);
        System.out.println("SUCCESS: " + testResult.getMethod().getMethodName());

        if (!testToTestRunMap.isEmpty()) {
            if (getTestCasesData(testResult) != null && getTestCasesData(testResult).getTargetProcessTestCaseData() != null)
                this.getTestCasesData(testResult).getTargetProcessTestCaseData()
                        .stream().map(TargetProcessTestCaseData::getTestCaseID)
                        .forEach(id -> {
                            try {
                                tpIntegrationService.setTestCaseRunStatus(testToTestRunMap.get(id), TestCaseRunStatus.PASSED, "Hello from automation :)");
                            } catch (UnirestException | IOException e) {
                                e.printStackTrace();
                            }
                        });
        }
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
        if (!StringUtils.isEmpty(testPlanRunId)) {
            TPIntegrationService tpIntegrationService = new TPIntegrationService();
            try {
                ProjectDTO projectDTO = tpIntegrationService.getTestPlanProject(testPlanRunId);
                EntityStatesDTO entityStatesDTO = tpIntegrationService.getTestPlansEntityStates(projectDTO.getProject().getProcess().getId().toString());
                EntityTypeDTO entityTypeDTO = entityStatesDTO.getItems()
                        .stream()
                        .filter(item -> item.getName().equals("Done"))
                        .findAny()
                        .orElseThrow(() -> new NoSuchElementException("Can't find test suite run status 'Done'"));
                tpIntegrationService.setTestPlanRunStatus(testPlanRunId, entityTypeDTO.getId());
            } catch (UnirestException | IOException e) {
                e.printStackTrace();
            }
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

    private void setTestCaseAutomatedField(ITestResult testResult) {
        TestCaseData testCaseData = getTestCasesData(testResult);
        if (testCaseData != null) {
            if (testCaseData.getTargetProcessTestCaseData() != null) {
                for (TargetProcessTestCaseData targetProcessTestCaseData : testCaseData.getTargetProcessTestCaseData()) {
                    try {
                        tpIntegrationService.setTestCaseAutomatedField(targetProcessTestCaseData.getTestCaseID());
                    } catch (UnirestException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private TestCaseData getTestCasesData(ITestResult testResult) {
        try {
            Object[] parameters = testResult.getParameters();
            return JSonDataParser.getTestDataFromJson((parameters[parameters.length - 1].toString()), TestCaseData.class);
        } catch (Exception ex) {
            return null;
        }
    }
}