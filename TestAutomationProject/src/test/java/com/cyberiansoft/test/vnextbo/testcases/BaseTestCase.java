package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.core.BrowserType;
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
    public void setUp() throws IOException, UnirestException {
        Optional<String> testCaseIdFromMaven = Optional.ofNullable(System.getProperty("testPlanId"));
        Optional<String> releaseIdFromMaven = Optional.ofNullable(System.getProperty("releaseId"));
        TPIntegrationService tpIntegrationService = new TPIntegrationService();
        TestPlanRunDTO testPlanRun = new TestPlanRunDTO();

        if (releaseIdFromMaven.isPresent()) {
            testPlanRun = tpIntegrationService.getAllTestPlanRuns().getItems().stream().
                    filter(run -> run.getName().contains(releaseIdFromMaven.get())).
                    findAny().orElse(null);
        }

        if (testCaseIdFromMaven.isPresent()) {
            String testPlanId = testCaseIdFromMaven.get();
            try {
                TestListenerAllure.setTestToTestRunMap(
                        tpIntegrationService.testCaseToTestRunMapRecursevley(testPlanRun.getName() != null ? testPlanRun :
                                tpIntegrationService.createTestPlanRun(testPlanId)));
            } catch (UnirestException | IOException e) {
                e.printStackTrace();
            }
        }

        Optional<String> testEnv = Optional.ofNullable(System.getProperty("testEnv"));
        if (testEnv.isPresent())
            backOfficeURL = VNextEnvironmentUtils.getBackOfficeURL(TestEnvironments.valueOf(testEnv.get()));
        else
            backOfficeURL = EnvironmentsData.getInstance().getVNextIntegrationBackOfficeURL();
    }

    @BeforeClass
    public void login(ITestContext context) {
        browserType = BaseUtils.getBrowserType(VNextBOConfigInfo.getInstance().getDefaultBrowser());
        DriverBuilder.getInstance().setDriver(browserType);
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