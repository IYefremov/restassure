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
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.MainScreen;
import com.cyberiansoft.test.ios10_client.testcases.BaseTestCase;
import com.cyberiansoft.test.ios10_client.testcases.hd.IOSHDBaseTestCase;
import com.cyberiansoft.test.ios10_client.utils.TestUser;
import com.cyberiansoft.test.targetprocessintegration.enums.TestCaseRunStatus;
import com.cyberiansoft.test.targetprocessintegration.model.TPIntegrationService;
import com.cyberiansoft.test.vnext.listeners.TestNG_ConsoleRunner;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.qameta.allure.Allure;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.*;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class iOSHDClientListener implements ITestListener, IInvokedMethodListener, IConfigurationListener {


	@Getter
	@Setter
	private static Map<String, String> testToTestRunMap = new HashMap<>();
	private TPIntegrationService tpIntegrationService = new TPIntegrationService();
	
    @Override
	public synchronized void onStart(ITestContext context) {

	}

	@Override
	public synchronized void onFinish(ITestContext context) {
		ExtentManager.getInstance().flush();
	}
	
	@Override
	public synchronized void onTestStart(ITestResult testResult) {
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
	public synchronized void onTestSuccess(ITestResult testResult) {
		if (!testToTestRunMap.isEmpty()) {
			if (getTestCasesData(testResult) != null && getTestCasesData(testResult).getTargetProcessTestCaseData() != null)
				this.getTestCasesData(testResult).getTargetProcessTestCaseData()
						.stream().map(TargetProcessTestCaseData::getTestCaseID)
						.forEach(id -> {
							try {
								tpIntegrationService.setTestCaseRunStatus(testToTestRunMap.get(id), TestCaseRunStatus.PASSED, "Hello from automation :)");
							} catch (UnirestException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
						});
		}
	}

	@Override
	public synchronized void onTestFailure(ITestResult testResult) {
		if (!testToTestRunMap.isEmpty()) {
			if (getTestCasesData(testResult) != null && getTestCasesData(testResult).getTargetProcessTestCaseData() != null)
				this.getTestCasesData(testResult).getTargetProcessTestCaseData()
						.stream().map(TargetProcessTestCaseData::getTestCaseID)
						.forEach(id -> {
							try {
								tpIntegrationService.setTestCaseRunStatus(testToTestRunMap.get(id), TestCaseRunStatus.FAILED, "Hello from automation :)");
							} catch (UnirestException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
						});
		}
		DriverBuilder.getInstance().getAppiumDriver().closeApp();
		DriverBuilder.getInstance().getAppiumDriver().launchApp();

		MainScreen mainscr = new MainScreen();
		TestUser testuser = ((IOSHDBaseTestCase) testResult.getInstance()).getTestUser();
		mainscr.userLogin(testuser.getTestUserName(), testuser.getTestUserPassword());

	}

	@Override
	public synchronized void onTestSkipped(ITestResult testResult) {
	}

	@Override
	public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult testResult) {
		
	}

	private TestCaseData getTestCasesData(ITestResult testResult) {
		try {
			Object[] parameters = testResult.getParameters();
			return JSonDataParser.getTestDataFromJson((parameters[parameters.length - 1].toString()), TestCaseData.class);
		} catch (Exception ex) {
			return null;
		}
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {

		if (!testResult.isSuccess()) {
			try {
				AppiumUtils.createScreenshot("report", "fail");
				Allure.addAttachment("failScreen",
						"image/png",
						new ByteArrayInputStream(((TakesScreenshot) DriverBuilder.getInstance().getAppiumDriver()).getScreenshotAs(OutputType.BYTES)),
						".png");
			} catch (org.openqa.selenium.NoSuchSessionException e) {
				AppiumInicializator.getInstance().initAppium(MobilePlatform.IOS_HD);
			} catch (RuntimeException e) {
				AppiumInicializator.getInstance().initAppium(MobilePlatform.IOS_HD);
				e.printStackTrace();
			}
		}
	}

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

	}

	@Override
	public void onConfigurationSuccess(ITestResult testResult) {

	}

	@Override
	public void onConfigurationFailure(ITestResult testResult) {
		DriverBuilder.getInstance().getAppiumDriver().closeApp();
		DriverBuilder.getInstance().getAppiumDriver().launchApp();

		MainScreen mainscr = new MainScreen();
		TestUser testuser = ((BaseTestCase) testResult.getInstance()).getTestUser();
		mainscr.userLogin(testuser.getTestUserName(), testuser.getTestUserPassword());

	}

	@Override
	public void onConfigurationSkip(ITestResult testResult) {

	}
}
