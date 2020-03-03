package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.enums.TestEnvironments;
import com.cyberiansoft.test.globalutils.EnvironmentsData;
import com.cyberiansoft.test.targetprocessintegration.model.TPIntegrationService;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.steps.login.VNextBOLoginSteps;
import com.cyberiansoft.test.vnextbo.utils.TestListenerAllure;
import com.cyberiansoft.test.vnextbo.utils.VNextEnvironmentUtils;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
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
        Optional<String> testCaseIdFromMaven = Optional.ofNullable(System.getProperty("testPlanId"));
        if (testCaseIdFromMaven.isPresent()) {
            TPIntegrationService tpIntegrationService = new TPIntegrationService();
            String testPlanId = testCaseIdFromMaven.get();
            try {
                TestListenerAllure.setTestToTestRunMap(
                        tpIntegrationService.testCaseToTestRunMapRecursevley(
                                tpIntegrationService.createTestPlanRun(testPlanId)));
            } catch (UnirestException | IOException e) {
                e.printStackTrace();
            }
        }

        Optional<String> testEnv = Optional.ofNullable(System.getProperty("testEnv"));
        if (testEnv.isPresent())
            backOfficeURL = VNextEnvironmentUtils.getBackOfficeURL(TestEnvironments.valueOf(testEnv.get()));
        else
            backOfficeURL =  EnvironmentsData.getInstance().getVNextIntegrationBackOfficeURL();
    }

    @BeforeClass
    public void login() {
        browserType = BaseUtils.getBrowserType(VNextBOConfigInfo.getInstance().getDefaultBrowser());
        DriverBuilder.getInstance().setDriver(browserType);
        webdriver = DriverBuilder.getInstance().getDriver();
        webdriverGotoWebPage(BaseTestCase.getBackOfficeURL());
        final String userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
        final String userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();
        VNextBOLoginSteps.userLogin(userName, userPassword);
    }

    @AfterClass
    public void tearDown() {
        if (DriverBuilder.getInstance().getDriver() != null)
            DriverBuilder.getInstance().quitDriver();
    }
}