package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.ActiveDevicesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.CompanyWebPage;
import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.driverutils.AppiumInicializator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.config.VNextEnvironmentInfo;
import com.cyberiansoft.test.vnext.config.VNextTeamRegistrationInfo;
import com.cyberiansoft.test.vnext.config.VNextToolsInfo;
import com.cyberiansoft.test.vnext.config.VNextUserRegistrationInfo;
import com.cyberiansoft.test.vnext.factories.environments.EnvironmentType;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.utils.AppContexts;
import com.cyberiansoft.test.vnext.utils.VNextWebServicesUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static io.appium.java_client.service.local.flags.GeneralServerFlag.LOG_LEVEL;
import static io.appium.java_client.service.local.flags.GeneralServerFlag.SESSION_OVERRIDE;

public class VNextBaseTestCase {
    protected static AppiumDriver<MobileElement> appiumdriver;
    protected WebDriver webdriver;
    protected String regcode = "";
    protected static String deviceOfficeUrl;
    protected static String deviceuser;
    protected static String devicepsw;
    protected static boolean buildproduction;
    protected static BrowserType browsertype;
    protected static MobilePlatform mobilePlatform;
    @Getter
    protected static Employee employee;
    protected static EnvironmentType envType;

    protected static String deviceID;
    protected static String licenseID;
    protected static String appID;
    protected static String appLicenseEntity;

    private ThreadLocal<AppiumDriverLocalService> service = new ThreadLocal<>();

    @BeforeSuite
    @Parameters("appium.path")
    public void initializeSuite(String appiumPath) {
        deviceOfficeUrl = VNextTeamRegistrationInfo.getInstance()
                .getBackOfficeUrlFromEnvType(EnvironmentType.getEnvironmentType(VNextEnvironmentInfo.getInstance().getEnvironmentType()));
        browsertype = BaseUtils.getBrowserType(VNextToolsInfo.getInstance().getDefaultBrowser());
        mobilePlatform = BaseUtils.getMobilePlatform(VNextToolsInfo.getInstance().getDefaultPlatform());
        buildproduction = VNextEnvironmentInfo.getInstance().getBuildProductionAttribute().equals("true");
        envType = EnvironmentType.getEnvironmentType(VNextEnvironmentInfo.getInstance().getEnvironmentType());

        startAppiumServer(appiumPath);
        setupMobileDevice(mobilePlatform);
        setupWebdriver();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        if (DriverBuilder.getInstance().getDriver() != null)
            DriverBuilder.getInstance().getDriver().quit();
        if (DriverBuilder.getInstance().getAppiumDriver() != null)
            DriverBuilder.getInstance().getAppiumDriver().quit();
        if (service.get() != null)
            service.get().stop();
    }

    public String getDeviceRegistrationCode(String deviceOfficeUrl, String deviceUser, String devicePsw, String licenseName) {
        BackOfficeLoginWebPage loginPage = new BackOfficeLoginWebPage(webdriver);
        BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
        CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);
        ActiveDevicesWebPage activeDevicesWebPage = new ActiveDevicesWebPage(webdriver);

        webdriver.get(deviceOfficeUrl);
        loginPage.userLogin(deviceUser, devicePsw);
        backOfficeHeaderPanel.clickCompanyLink();
        companyWebPage.clickManageDevicesLink();
        activeDevicesWebPage.setSearchCriteriaByName(licenseName);
        regcode = activeDevicesWebPage.getFirstRegCodeInTable();
        return regcode;
    }

    public void registerDevice() throws Exception {
        String phoneCountryCode;
        String phoneNumber;
        String userRegMail = VNextUserRegistrationInfo.getInstance().getDeviceRegistrationUserMail();
        EnvironmentType envType = EnvironmentType.getEnvironmentType(VNextEnvironmentInfo.getInstance().getEnvironmentType());
        if (buildproduction) {
            phoneCountryCode = VNextUserRegistrationInfo.getInstance().getProductionDeviceRegistrationUserPhoneCountryCode();
            phoneNumber = phoneCountryCode;
        } else {
            phoneCountryCode = VNextUserRegistrationInfo.getInstance().getDeviceRegistrationUserPhoneCountryCode();
            phoneNumber = VNextUserRegistrationInfo.getInstance().getDeviceRegistrationUserPhoneNumber();
        }

        AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
        VNextEditionsScreen editionsScreen = new VNextEditionsScreen(appiumdriver);
        VNextEnvironmentSelectionScreen environmentSelectionScreen = editionsScreen.selectEdition("Repair360");
        environmentSelectionScreen.selectEnvironment(envType);

        BaseUtils.waitABit(15 * 1000);
        AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);
        AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);


        VNextRegistrationPersonalInfoScreen regscreen = new VNextRegistrationPersonalInfoScreen(appiumdriver);

        regscreen.setUserRegistrationInfoAndSend(VNextUserRegistrationInfo.getInstance().getDeviceRegistrationUserFirstName(),
                VNextUserRegistrationInfo.getInstance().getDeviceRegistrationUserLastName(),
                phoneCountryCode, phoneNumber, userRegMail);
        BaseUtils.waitABit(15000);
        VNextVerificationScreen verificationscreen = new VNextVerificationScreen(DriverBuilder.getInstance().getAppiumDriver());
        if (buildproduction)
            verificationscreen.setDeviceRegistrationCode(VNextWebServicesUtils.getProdRegCode(phoneNumber));
        else
            verificationscreen.setDeviceRegistrationCode(VNextWebServicesUtils.getDevicePhoneVerificationCode(userRegMail).replaceAll("\"", ""));
        verificationscreen.clickVerifyButton();

        //VNextRegistrationScreensModalDialog registrationinformationdlg = new VNextRegistrationScreensModalDialog(DriverBuilder.getInstance().getAppiumDriver());
        //Assert.assertEquals(registrationinformationdlg.clickInformationDialogOKButtonAndGetMessage(), "Your phone has been verified");
        BaseUtils.waitABit(25 * 1000);
        if (DriverBuilder.getInstance().getMobilePlatform().equals(MobilePlatform.ANDROID)) {
            AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);
            DriverBuilder.getInstance().getAppiumDriver().closeApp();
            DriverBuilder.getInstance().getAppiumDriver().launchApp();
            AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
        }

        WebDriverWait wait = new WebDriverWait(DriverBuilder.getInstance().getAppiumDriver(), 340);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Data has been successfully downloaded']")));
        VNextInformationDialog informationdlg = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        informationdlg.clickInformationDialogOKButton();
    }

    public void registerTeamEdition(String licenseName) {
        VNextEditionsScreen editionsScreen = new VNextEditionsScreen(appiumdriver);
        VNextEnvironmentSelectionScreen environmentSelectionScreen = new VNextEnvironmentSelectionScreen(appiumdriver);
        VNextTeamEditionVerificationScreen verificationScreen = new VNextTeamEditionVerificationScreen(appiumdriver);
        VNextDownloadDataScreen downloadDataScreen = new VNextDownloadDataScreen(appiumdriver);
        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);

        final String regCode = getDeviceRegistrationCode(deviceOfficeUrl,
                VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserName(),
                VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserPassword(),
                licenseName);

        ActiveDevicesWebPage activeDevicesWebPage = new ActiveDevicesWebPage(webdriver);
        deviceID = activeDevicesWebPage.getDeviceID();
        licenseID = activeDevicesWebPage.getLicenseID();
        appID = activeDevicesWebPage.getApplicationID();
        appLicenseEntity = activeDevicesWebPage.getLicenseEntityName();

        AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);
        AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
        editionsScreen.selectEdition("ReconPro Starter");
        environmentSelectionScreen.selectEnvironment(envType);
        verificationScreen.setDeviceRegistrationCode(regCode);
        verificationScreen.clickVerifyButton();
        downloadDataScreen.waitUntilDatabasesDownloaded();
        informationDialog.clickInformationDialogOKButton();
    }

    private void startAppiumServer(String appiumPath) {
        service.set(new AppiumServiceBuilder()
                .withAppiumJS(new File(appiumPath))
                .usingAnyFreePort()
                .withArgument(SESSION_OVERRIDE)
                .withArgument(LOG_LEVEL, "error")
                .build());
        service.get().start();
        if (service.get() == null || !service.get().isRunning())
            throw new AppiumServerHasNotBeenStartedLocallyException("An appium server node is not started!");
    }

    private void setupMobileDevice(MobilePlatform mobilePlatform) {
        if (mobilePlatform.getMobilePlatformString().contains("ios"))
            DriverBuilder.getInstance().setAppiumDriver(MobilePlatform.IOS_REGULAR);
        else {
            appiumdriver = AppiumInicializator.getInstance().initAppium(MobilePlatform.ANDROID, service.get().getUrl());
            appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            AppiumUtils.setAndroidNetworkOn();

            if (VNextEnvironmentInfo.getInstance().installNewBuild()) {
                String appAbsolutePath = (String) appiumdriver.getCapabilities().getCapability(MobileCapabilityType.APP);
                appiumdriver.removeApp("com.automobiletechnologies.ReconProClient");
                appiumdriver.installApp(appAbsolutePath);
                appiumdriver.launchApp();
            }
        }
    }

    private void setupWebdriver() {
        DriverBuilder.getInstance().setDriver(browsertype);
        webdriver = DriverBuilder.getInstance().getDriver();
    }
}
