package com.cyberiansoft.test.vnext.listeners;

import com.cyberiansoft.test.baseutils.AllureUtils;
import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataclasses.TargetProcessTestCaseData;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.targetprocessintegration.dto.EntityStatesDTO;
import com.cyberiansoft.test.targetprocessintegration.dto.EntityTypeDTO;
import com.cyberiansoft.test.targetprocessintegration.dto.ProjectDTO;
import com.cyberiansoft.test.targetprocessintegration.enums.TestCaseRunStatus;
import com.cyberiansoft.test.targetprocessintegration.model.TPIntegrationService;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextLoginScreen;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.qameta.allure.Allure;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestServiceListener implements ITestListener, IInvokedMethodListener, IConfigurationListener {

    @Getter
    @Setter
    private static Map<String, String> testToTestRunMap = new HashMap<>();
    @Getter
    @Setter
    private static String testPlanRunId;

    private TPIntegrationService tpIntegrationService = new TPIntegrationService();

    @Override
    public void onTestStart(ITestResult result) {
        if (!testToTestRunMap.isEmpty()) {
            boolean testShouldBeExecuted = false;
            if (getTestCasesData(result) != null && getTestCasesData(result).getTargetProcessTestCaseData() != null) {
                List<String> targetProcessTestCaseIds =
                        this.getTestCasesData(result).getTargetProcessTestCaseData()
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
    public void onTestSuccess(ITestResult result) {
        if (!testToTestRunMap.isEmpty()) {
            if (getTestCasesData(result) != null && getTestCasesData(result).getTargetProcessTestCaseData() != null)
                this.getTestCasesData(result).getTargetProcessTestCaseData()
                    .stream().map(TargetProcessTestCaseData::getTestCaseID)
                    .forEach(id -> {
                        try {
                            if (testToTestRunMap.containsKey(id))
                                tpIntegrationService.setTestCaseRunStatus(testToTestRunMap.get(id), TestCaseRunStatus.PASSED, "Hello from automation :)");
                        } catch (UnirestException | IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

    @Override
    public void onTestFailure(ITestResult testResult) {
        AllureUtils.attachLog(Arrays.toString(testResult.getThrowable().getStackTrace()));
        AllureUtils.attachScreenshot(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        if (!testToTestRunMap.isEmpty()) {
            if (getTestCasesData(testResult) != null && getTestCasesData(testResult).getTargetProcessTestCaseData() != null)
                this.getTestCasesData(testResult).getTargetProcessTestCaseData()
                    .stream().map(TargetProcessTestCaseData::getTestCaseID)
                    .forEach(id -> {
                        try {
                            if (testToTestRunMap.containsKey(id))
                                tpIntegrationService.setTestCaseRunStatus(testToTestRunMap.get(id), TestCaseRunStatus.FAILED, "Hello from automation :)");
                        } catch (UnirestException | IOException e) {
                            e.printStackTrace();
                        }
                    });
        }

        WebDriver chromeDriver = ChromeDriverProvider.INSTANCE.getMobileChromeDriver();
        chromeDriver.navigate().refresh();
        VNextLoginScreen loginscreen = new VNextLoginScreen(chromeDriver);
        Employee employee = BaseTestClass.getEmployee();
        loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeePassword());
        new VNextHomeScreen();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        AllureUtils.attachLog(Arrays.toString(result.getThrowable().getStackTrace()));
        AllureUtils.attachScreenshot(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {
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
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (!testResult.isSuccess())
            Allure.addAttachment("failScreen",
                    "image/png",
                    new ByteArrayInputStream(((TakesScreenshot) ChromeDriverProvider.INSTANCE.getMobileChromeDriver()).getScreenshotAs(OutputType.BYTES)),
                    ".png");
    }

    @Override
    public void onConfigurationSuccess(ITestResult itr) {

    }

    @Override
    public void onConfigurationFailure(ITestResult itr) {
        WebDriver chromeDriver = ChromeDriverProvider.INSTANCE.getMobileChromeDriver();
        chromeDriver.navigate().refresh();
        VNextLoginScreen loginscreen = new VNextLoginScreen(chromeDriver);
        Employee employee = BaseTestClass.getEmployee();
        loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeePassword());
        new VNextHomeScreen();
    }

    @Override
    public void onConfigurationSkip(ITestResult itr) {

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
