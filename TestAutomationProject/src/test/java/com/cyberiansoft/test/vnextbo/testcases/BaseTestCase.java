package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.core.WebDriverConfigInfo;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.enums.TestEnvironments;
import com.cyberiansoft.test.globalutils.EnvironmentsData;
import com.cyberiansoft.test.targetprocessintegration.dto.TestPlanRunDTO;
import com.cyberiansoft.test.targetprocessintegration.model.TPIntegrationService;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.steps.login.VNextBOLoginSteps;
import com.cyberiansoft.test.vnextbo.utils.TestListenerAllure;
import com.cyberiansoft.test.vnextbo.utils.VNextEnvironmentUtils;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.util.Optional;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class BaseTestCase {

    protected WebDriver webdriver;
    protected static BrowserType browserType;
    @Getter
    protected static String backOfficeURL;

    public void setDriver() {
        webdriver = DriverBuilder.getInstance().getDriver();
    }

    @BeforeSuite
    public void setUp() {
        Optional<String> testPlanIdFromMaven = Optional.ofNullable(System.getProperty("testPlanId"));
        Optional<String> releaseIdFromMaven = Optional.ofNullable(System.getProperty("releaseId"));
        TPIntegrationService tpIntegrationService = new TPIntegrationService();

        if (testPlanIdFromMaven.isPresent()) {
            String testPlanId = testPlanIdFromMaven.get();
            try {
                if (releaseIdFromMaven.isPresent()) {
                    TestPlanRunDTO testPlanRun = tpIntegrationService.getAllTestPlanRuns().getItems().stream().
                            filter(run -> run.getName().contains(releaseIdFromMaven.get())).
                            findAny().orElse(null);
                    if (testPlanRun != null) {
                        testPlanRun = tpIntegrationService.getTestPlanRunInfo(testPlanRun.getId().toString());
                        if (testPlanRun.getTestCaseRuns() != null)
                            TestListenerAllure.setTestToTestRunMap(tpIntegrationService.testCaseToTestRunMapRecursively(testPlanRun));
                    }
                    else {
                        TestListenerAllure.setTestToTestRunMap(
                                tpIntegrationService.testCaseToTestRunMapRecursively(tpIntegrationService.createTestPlanRun(testPlanId, releaseIdFromMaven.get())));
                    }
                } else {
                    TestPlanRunDTO testPlanRunDTO = tpIntegrationService.createTestPlanRun(testPlanId);
                    TestListenerAllure.setTestPlanRunId(testPlanRunDTO.getId().toString());
                    TestListenerAllure.setTestToTestRunMap(
                            tpIntegrationService.testCaseToTestRunMapRecursively(testPlanRunDTO));
                }
            } catch (UnirestException | IOException e) {
                e.printStackTrace();
            }
        }
        Optional<String> boURLParam = Optional.ofNullable(System.getProperty("testNewBOURL"));
        if (boURLParam.isPresent())
            backOfficeURL = boURLParam.get();
        else {
            Optional<String> testEnv = Optional.ofNullable(System.getProperty("testEnv"));
            if (testEnv.isPresent())
                backOfficeURL = VNextEnvironmentUtils.getBackOfficeURL(TestEnvironments.valueOf(testEnv.get()));
            else
                backOfficeURL = EnvironmentsData.getInstance().getVNextIntegrationBackOfficeURL();
        }
    }

    @BeforeClass
    public void login(ITestContext context) {
        browserType = BaseUtils.getBrowserType(WebDriverConfigInfo.getInstance().getDefaultBrowser());
        DriverBuilder.getInstance()
                .setBrowserType(browserType)
                .setRemoteWebDriverURL(WebDriverConfigInfo.getInstance().getAzureURL())
                .setDriver();
        webdriver = DriverBuilder.getInstance().getDriver();
        webdriverGotoWebPage(BaseTestCase.getBackOfficeURL());
        final String userName = context.getCurrentXmlTest().getParameter("userName") != null ?
                context.getCurrentXmlTest().getParameter("userName") : VNextBOConfigInfo.getInstance().getVNextBONadaMail();
        final String userPassword = context.getCurrentXmlTest().getParameter("userPassword") != null ?
                context.getCurrentXmlTest().getParameter("userPassword") : VNextBOConfigInfo.getInstance().getVNextBOPassword();
        VNextBOLoginSteps.userLogin(userName, userPassword);
    }

    @AfterClass
    public void tearDown() {
        if (DriverBuilder.getInstance().getDriver() != null)
            DriverBuilder.getInstance().quitDriver();
    }
}