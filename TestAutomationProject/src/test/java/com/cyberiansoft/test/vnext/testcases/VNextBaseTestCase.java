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
import com.cyberiansoft.test.driverutils.AppiumServiceManager;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.config.VNextEnvironmentInfo;
import com.cyberiansoft.test.vnext.config.VNextFreeRegistrationInfo;
import com.cyberiansoft.test.vnext.config.VNextTeamRegistrationInfo;
import com.cyberiansoft.test.vnext.config.VNextToolsInfo;
import com.cyberiansoft.test.vnext.factories.environments.EnvironmentType;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.utils.AppContexts;
import com.cyberiansoft.test.vnext.utils.VNextAppUtils;
import com.cyberiansoft.test.vnext.utils.VNextWebServicesUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.concurrent.TimeUnit;

public class VNextBaseTestCase {
    protected String regcode = "";
    protected static String deviceOfficeUrl;
    protected static String deviceuser;
    protected static String devicepsw;
    protected static boolean buildproduction;
    protected static BrowserType browsertype;
    protected static MobilePlatform mobilePlatform;
    @Getter
    protected static Employee employee;
    @Getter
    protected static boolean helpPopupShown;
    protected static EnvironmentType envType;

    protected static String deviceID;
    protected static String licenseID;
    protected static String appID;
    protected static String appLicenseEntity;

    @BeforeSuite
    public void initializeSuite() {
        deviceOfficeUrl = VNextTeamRegistrationInfo.getInstance()
                .getBackOfficeUrlFromEnvType(EnvironmentType.getEnvironmentType(VNextEnvironmentInfo.getInstance().getEnvironmentType()));
        browsertype = BaseUtils.getBrowserType(VNextToolsInfo.getInstance().getDefaultBrowser());
        mobilePlatform = BaseUtils.getMobilePlatform(VNextToolsInfo.getInstance().getDefaultPlatform());
        buildproduction = VNextEnvironmentInfo.getInstance().getBuildProductionAttribute().equals("true");
        envType = EnvironmentType.getEnvironmentType(VNextEnvironmentInfo.getInstance().getEnvironmentType());

        AppiumServiceManager.startAppium();
        DriverBuilder.getInstance().setDriver(browsertype);
        setupMobileDevice(mobilePlatform);
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        if (DriverBuilder.getInstance().getDriver() != null)
            DriverBuilder.getInstance().getDriver().quit();
        if (DriverBuilder.getInstance().getAppiumDriver() != null)
            DriverBuilder.getInstance().getAppiumDriver().quit();
        if (AppiumServiceManager.getAppiumService() != null)
            AppiumServiceManager.getAppiumService().stop();
    }

    public String getDeviceRegistrationCode(String deviceOfficeUrl, String deviceUser, String devicePsw, String licenseName) {
        BackOfficeLoginWebPage loginPage = new BackOfficeLoginWebPage(DriverBuilder.getInstance().getDriver());
        BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(DriverBuilder.getInstance().getDriver());
        CompanyWebPage companyWebPage = new CompanyWebPage(DriverBuilder.getInstance().getDriver());
        ActiveDevicesWebPage activeDevicesWebPage = new ActiveDevicesWebPage(DriverBuilder.getInstance().getDriver());

        DriverBuilder.getInstance().getDriver().get(deviceOfficeUrl);
        loginPage.userLogin(deviceUser, devicePsw);
        backOfficeHeaderPanel.clickCompanyLink();
        companyWebPage.clickManageDevicesLink();
        activeDevicesWebPage.setSearchCriteriaByName(licenseName);
        regcode = activeDevicesWebPage.getFirstRegCodeInTable();
        return regcode;
    }

    public void registerDevice() throws Exception {
        helpPopupShown = VNextFreeRegistrationInfo.getInstance().isHelpPopupShown();
        AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
        VNextEditionsScreen editionsScreen = new VNextEditionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextEnvironmentSelectionScreen environmentSelectionScreen = editionsScreen.selectEdition("Repair360");
        environmentSelectionScreen.selectEnvironment(envType);

        BaseUtils.waitABit(15 * 1000);
        AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);
        AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);


        VNextRegistrationPersonalInfoScreen regscreen = new VNextRegistrationPersonalInfoScreen(DriverBuilder.getInstance().getAppiumDriver());

        regscreen.setUserRegistrationInfoAndSend(employee.getEmployeeFirstName(), employee.getEmployeeLastName(),
                employee.getEmployeePhoneCountryCode(), employee.getEmployeePhoneNumber(), employee.getEmployeeEmail());
        BaseUtils.waitABit(15000);
        VNextVerificationScreen verificationscreen = new VNextVerificationScreen(DriverBuilder.getInstance().getAppiumDriver());
        if (buildproduction)
            verificationscreen.setDeviceRegistrationCode(VNextWebServicesUtils.getProdRegCode(employee.getEmployeePhoneNumber()));
        else
            verificationscreen.setDeviceRegistrationCode(VNextWebServicesUtils.getDevicePhoneVerificationCode(employee.getEmployeeEmail()).replaceAll("\"", ""));
        verificationscreen.clickVerifyButton();

        BaseUtils.waitABit(25 * 1000);
        if (DriverBuilder.getInstance().getMobilePlatform().equals(MobilePlatform.ANDROID)) {
            VNextAppUtils.restartApp();
        }

        WebDriverWait wait = new WebDriverWait(DriverBuilder.getInstance().getAppiumDriver(), 340);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Data has been successfully downloaded']")));
        VNextInformationDialog informationdlg = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        informationdlg.clickInformationDialogOKButton();
    }

    public void registerTeamEdition(String licenseName) {
        helpPopupShown = VNextTeamRegistrationInfo.getInstance().isHelpPopupShown();
        VNextEditionsScreen editionsScreen = new VNextEditionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextEnvironmentSelectionScreen environmentSelectionScreen = new VNextEnvironmentSelectionScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextTeamEditionVerificationScreen verificationScreen = new VNextTeamEditionVerificationScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextDownloadDataScreen downloadDataScreen = new VNextDownloadDataScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());

        final String regCode = getDeviceRegistrationCode(deviceOfficeUrl,
                VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserName(),
                VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserPassword(),
                licenseName);

        ActiveDevicesWebPage activeDevicesWebPage = new ActiveDevicesWebPage(DriverBuilder.getInstance().getDriver());
        //deviceID = activeDevicesWebPage.getDeviceID();
        //licenseID = activeDevicesWebPage.getLicenseID();
        //appID = activeDevicesWebPage.getApplicationID();
        //appLicenseEntity = activeDevicesWebPage.getLicenseEntityName();
        DriverBuilder.getInstance().getDriver().quit();
        AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);
        AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
        editionsScreen.selectEdition("ReconPro Starter");
        environmentSelectionScreen.selectEnvironment(envType);
        verificationScreen.setDeviceRegistrationCode(regCode);
        verificationScreen.clickVerifyButton();
        downloadDataScreen.waitUntilDatabasesDownloaded();
        informationDialog.clickInformationDialogOKButton();
    }

    private void setupMobileDevice(MobilePlatform mobilePlatform) {
        if (mobilePlatform.getMobilePlatformString().contains("ios"))
            DriverBuilder.getInstance().setAppiumDriver(MobilePlatform.IOS_REGULAR);
        else {
            AppiumInicializator.getInstance().initAppium(MobilePlatform.ANDROID, AppiumServiceManager.getAppiumService().getUrl());
            DriverBuilder.getInstance().getAppiumDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            AppiumUtils.setAndroidNetworkOn();

            if (VNextEnvironmentInfo.getInstance().installNewBuild()) {
                AppiumDriver driver = DriverBuilder.getInstance().getAppiumDriver();
                String appAbsolutePath = (String) driver.getCapabilities().getCapability(MobileCapabilityType.APP);
                driver.removeApp("com.automobiletechnologies.ReconProClient");
                driver.installApp(appAbsolutePath);
                driver.launchApp();
            }
        }
    }
}
