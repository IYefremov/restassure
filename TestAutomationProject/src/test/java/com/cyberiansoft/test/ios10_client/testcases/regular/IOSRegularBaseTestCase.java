package com.cyberiansoft.test.ios10_client.testcases.regular;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.driverutils.AppiumServiceManager;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.listeners.IOSRegularTestCasesListener;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularMainScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularSettingsScreen;
import com.cyberiansoft.test.ios10_client.regularclientsteps.RegularHomeScreenSteps;
import com.cyberiansoft.test.ios10_client.templatepatterns.DeviceRegistrator;
import com.cyberiansoft.test.ios10_client.types.envtypes.IOSReconproEnvironmentType;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios10_client.utils.TestUser;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import com.cyberiansoft.test.targetprocessintegration.model.TPIntegrationService;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.util.Optional;

public class IOSRegularBaseTestCase {

    protected WebDriver webdriver;
    protected static BrowserType browsertype;
    protected static IOSReconproEnvironmentType envType;
    protected static String deviceofficeurl;
    protected static MobilePlatform mobilePlatform;
    protected static TestUser testuser;

    @BeforeSuite
    public void setUp() {

        //Optional<String> testCaseIdFromMaven = Optional.ofNullable(System.getProperty("testPlanId"));
        Optional<String> testCaseIdFromMaven = Optional.ofNullable("88423");
        if (testCaseIdFromMaven.isPresent()) {
            TPIntegrationService tpIntegrationService = new TPIntegrationService();
            String testPlanId = testCaseIdFromMaven.get();
            try {
                IOSRegularTestCasesListener.setTestToTestRunMap(
                        tpIntegrationService.testCaseToTestRunMapRecursevley(
                                tpIntegrationService.createTestPlanRun(testPlanId)));
            } catch (UnirestException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        AppiumServiceManager.startAppium();
        browsertype = BaseUtils.getBrowserType(ReconProIOSStageInfo.getInstance().getDefaultBrowser());
        mobilePlatform = MobilePlatform.IOS_REGULAR;

        envType = IOSReconproEnvironmentType.getEnvironmentType(ReconProIOSStageInfo.getInstance().getEnvironmentType());
        if (envType.equals(IOSReconproEnvironmentType.DEVELOPMENT))
            deviceofficeurl = ReconProIOSStageInfo.getInstance().getBackOfficeDevelopmentURL();
        else if (envType.equals(IOSReconproEnvironmentType.INTEGRATION))
            deviceofficeurl = ReconProIOSStageInfo.getInstance().getBackOfficeStagingURL();
        else if (envType.equals(IOSReconproEnvironmentType.UAT))
            deviceofficeurl = ReconProIOSStageInfo.getInstance().getBackOfficeUATURL();

        initTestUser(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
        DeviceRegistrator.getInstance().installAndRegisterDevice(browsertype, mobilePlatform, deviceofficeurl,
                ReconProIOSStageInfo.getInstance().getUserStageUserName(), ReconProIOSStageInfo.getInstance().getUserStageUserPassword(), "Test_Automation_Regular3",
                envType);

        RegularMainScreen mainScreen = new RegularMainScreen();
        mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
        RegularHomeScreenSteps.navigateToSettingsScreen();
        RegularSettingsScreen settingsScreen = new RegularSettingsScreen();
        settingsScreen.setShowAvailableSelectedServicesOn();
        settingsScreen.clickHomeButton();
    }

    public static WebElement wait(By locator) {
        return Helpers.wait(locator);
    }

    @AfterSuite
    public void tearDown() {
        if (DriverBuilder.getInstance().getDriver() != null)
            DriverBuilder.getInstance().getDriver().quit();
        if (DriverBuilder.getInstance().getAppiumDriver() != null)
            DriverBuilder.getInstance().getAppiumDriver().quit();
        if (AppiumServiceManager.getAppiumService() != null) {
            AppiumServiceManager.getAppiumService().stop();
        }
    }

    public void initTestUser(String username, String userpsw) {
        this.testuser = new TestUser(username, userpsw);
    }

    public TestUser getTestUser() {
        return testuser;
    }
}
