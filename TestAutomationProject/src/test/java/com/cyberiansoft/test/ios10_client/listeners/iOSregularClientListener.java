package com.cyberiansoft.test.ios10_client.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.dataclasses.TargetProcessTestCaseData;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.AppiumInicializator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.extentreportproviders.ExtentManager;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularMainScreen;
import com.cyberiansoft.test.ios10_client.testcases.BaseTestCase;
import com.cyberiansoft.test.ios10_client.utils.TestUser;
import com.cyberiansoft.test.targetprocessintegration.dto.TestCaseRunDTO;
import com.cyberiansoft.test.targetprocessintegration.dto.TestPlanRunDTO;
import com.cyberiansoft.test.targetprocessintegration.enums.TestCaseRunStatus;
import com.cyberiansoft.test.targetprocessintegration.model.TPIntegrationService;
import com.cyberiansoft.test.vnext.listeners.TestNG_ConsoleRunner;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.testng.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.Date;

public class iOSregularClientListener extends TestListenerAdapter implements IInvokedMethodListener {
    private TestPlanRunDTO testPlanRunDTO;
    private ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private TPIntegrationService tpIntegrationService = new TPIntegrationService();

    @Override
    public synchronized void onStart(ITestContext context) {
        if (context.getSuite().getParameter("tp.integration").equalsIgnoreCase("true")) {
            String targetProcessSuiteID = context.getSuite().getParameter("tpsuite.id");
            try {
                testPlanRunDTO = tpIntegrationService.createTestPlanRun(targetProcessSuiteID);
            } catch (UnirestException | IOException e) {
                e.printStackTrace();
            }
        }

        ExtentManager.createInstance(ReconProIOSStageInfo.getInstance().getReportFolder() +
                ReconProIOSStageInfo.getInstance().getReportFileName());
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
    public synchronized void onTestStart(ITestResult testResult) {
        ExtentTest extent;

        if (getTestParams(testResult).isEmpty()) {
            extent = ExtentManager.getInstance().createTest(testResult.getMethod().getMethodName());
        } else {
            if (getTestParams(testResult).split(",")[0].contains(testResult.getMethod().getMethodName())) {
                extent = ExtentManager.getInstance().createTest(getTestParams(testResult).split(",")[0], getTestParams(testResult).split(",")[1]);
            } else {
                extent = ExtentManager.getInstance().createTest(testResult.getMethod().getMethodName(), getTestParams(testResult).split(",")[1]);
            }
        }

        extent.getModel().setStartTime(getTime(testResult.getStartMillis()));

        extentTest.set(extent);
    }

    @Override
    public synchronized void onTestSuccess(ITestResult testResult) {
        setTestCaseAutomatedField(testResult);
        extentTest.get().log(Status.PASS, "<font color=#00af00>" + Status.PASS.toString().toUpperCase() + "</font>");
        extentTest.get().getModel().setEndTime(getTime(testResult.getEndMillis()));
        setTestCaseStatusInTargetProcess(testResult, TestCaseRunStatus.PASSED);
    }

    @Override
    public synchronized void onTestFailure(ITestResult testResult) {
        setTestCaseAutomatedField(testResult);
        setTestCaseStatusInTargetProcess(testResult, TestCaseRunStatus.FAILED);
        extentTest.get().log(Status.FAIL, "<font color=#F7464A>" + Status.FAIL.toString().toUpperCase() + "</font>");
        extentTest.get().log(Status.INFO, "EXCEPTION = [" + testResult.getThrowable().getMessage() + "]");

        try {
            extentTest.get().log(Status.INFO, "SCREENSHOT", MediaEntityBuilder.createScreenCaptureFromPath(AppiumUtils.createScreenshot("report", "fail")).build());
        } catch (org.openqa.selenium.NoSuchSessionException e) {
            AppiumInicializator.getInstance().initAppium(MobilePlatform.IOS_REGULAR);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            AppiumInicializator.getInstance().initAppium(MobilePlatform.IOS_REGULAR);
            e.printStackTrace();
        }
        if (!getTestParams(testResult).isEmpty()) {
            extentTest.get().log(Status.INFO, "STACKTRACE" + getStrackTrace(testResult));
        }
        extentTest.get().getModel().setEndTime(getTime(testResult.getEndMillis()));
        DriverBuilder.getInstance().getAppiumDriver().closeApp();
        DriverBuilder.getInstance().getAppiumDriver().launchApp();
        RegularMainScreen mainscr = new RegularMainScreen();
        TestUser testuser = ((BaseTestCase) testResult.getInstance()).getTestUser();
        mainscr.userLogin(testuser.getTestUserName(), testuser.getTestUserPassword());
    }

    @Override
    public synchronized void onTestSkipped(ITestResult testResult) {
        setTestCaseAutomatedField(testResult);
        setTestCaseStatusInTargetProcess(testResult, TestCaseRunStatus.NOT_RUN);
        extentTest.get().log(Status.SKIP, "<font color=#2196F3>" + Status.SKIP.toString().toUpperCase() + "</font>");
        extentTest.get().log(Status.INFO, "EXCEPTION = [" + testResult.getThrowable().getMessage() + "]");
        extentTest.get().getModel().setEndTime(getTime(testResult.getEndMillis()));
    }

    @Override
    public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult testResult) {

    }

    private String getStrackTrace(ITestResult testResult) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        testResult.getThrowable().printStackTrace(printWriter);

        return "<br/>\n" + writer.toString().replace(System.lineSeparator(), "<br/>\n");
    }

    private String getTestParams(ITestResult testResult) {
        TestNG_ConsoleRunner runner = new TestNG_ConsoleRunner();

        return runner.getTestParams(testResult);
    }

    private void setTestCaseStatusInTargetProcess(ITestResult testResult, TestCaseRunStatus testCaseRunStatus) {
        if (testPlanRunDTO != null) {
            TestCaseData testCaseData = getTestCasesData(testResult);
            if (testCaseData != null) {
                if (testCaseData.getTargetProcessTestCaseData() != null) {
                    for (TargetProcessTestCaseData targetProcessTestCaseData : testCaseData.getTargetProcessTestCaseData())
                        for (TestCaseRunDTO testCaseRunDTO : testPlanRunDTO.getTestCaseRuns().getItems()) {
                            if (targetProcessTestCaseData.getTestCaseID().equals(testCaseRunDTO.getTestCase().getId().toString()))
                                try {
                                    tpIntegrationService.setTestCaseRunStatus(testCaseRunDTO.getId().toString(), testCaseRunStatus, "");
                                    break;
                                } catch (UnirestException | IOException e) {
                                    e.printStackTrace();
                                    break;
                                }
                        }
                }
            }
        }
    }

    private TestCaseData getTestCasesData(ITestResult testResult) {
        Object[] parameters = testResult.getParameters();
        return JSonDataParser.getTestDataFromJson((parameters[parameters.length - 1].toString()), TestCaseData.class);
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);

        return calendar.getTime();
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {

    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

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
}
