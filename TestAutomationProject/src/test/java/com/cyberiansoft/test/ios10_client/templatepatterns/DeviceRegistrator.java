package com.cyberiansoft.test.ios10_client.templatepatterns;

import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.ActiveDevicesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.CompanyWebPage;
import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.core.IOSHDDeviceInfo;
import com.cyberiansoft.test.core.IOSRegularDeviceInfo;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.driverutils.AppiumInicializator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.LoginScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SelectEnvironmentPopup;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectEnvironmentScreen;
import com.cyberiansoft.test.ios10_client.types.envtypes.IOSReconproEnvironmentType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.Map;

public class DeviceRegistrator {

    private static DeviceRegistrator instance = null;

    private DeviceRegistrator() {
    }

    public static DeviceRegistrator getInstance() {
        if ( instance == null ) {
            instance = new DeviceRegistrator();
        }
        return instance;
    }

    public void installAndRegisterDevice(BrowserType browsertype, MobilePlatform platformType, String backofficeurl,
                                         String userName, String userPassword, String licensename, IOSReconproEnvironmentType environmentType) {
        registerationiOSDdevice(browsertype, platformType, backofficeurl,
                userName, userPassword, licensename, environmentType);

    }

    public String getDeviceRegistrationCode(BrowserType browsertype, String backofficeurl,
                                              String userName, String userPassword, String licensename) {

        WebDriver webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(backofficeurl);

        BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
                BackOfficeLoginWebPage.class);
        loginpage.userLogin(userName, userPassword);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
                BackOfficeHeaderPanel.class);
        CompanyWebPage companyWebPage = backofficeheader.clickCompanyLink();

        ActiveDevicesWebPage devicespage = companyWebPage.clickManageDevicesLink();

        devicespage.setSearchCriteriaByName(licensename);
        String regCode = devicespage.getFirstRegCodeInTable();
        DriverBuilder.getInstance().getDriver().quit();
        return regCode;
    }

    public void registerationiOSDdevice(BrowserType browsertype, MobilePlatform platformType, String backofficeurl,
                                        String userName, String userPassword, String licensename, IOSReconproEnvironmentType environmentType) {

        String deviceBundleId = null;
        if (platformType.equals(MobilePlatform.IOS_REGULAR))
            deviceBundleId = IOSRegularDeviceInfo.getInstance().getDeviceBundleId();
        else
            deviceBundleId = IOSHDDeviceInfo.getInstance().getDeviceBundleId();


        AppiumInicializator.getInstance().initAppium(platformType);


        if (ReconProIOSStageInfo.getInstance().installNewBuild()) {
            if (DriverBuilder.getInstance().getAppiumDriver().isAppInstalled(deviceBundleId)) {
                Map<String, Object> params = new HashMap<>();
                params.put("bundleId", deviceBundleId);
                DriverBuilder.getInstance().getAppiumDriver().executeScript("mobile: removeApp", params);
                DriverBuilder.getInstance().getAppiumDriver().quit();
                AppiumInicializator.getInstance().initAppium(platformType);
            }
            if (platformType.equals(MobilePlatform.IOS_REGULAR)) {
                RegularSelectEnvironmentScreen selectenvscreen = new RegularSelectEnvironmentScreen();
                LoginScreen loginscreen = selectenvscreen.selectEnvironment(environmentType.getEnvironmentTypeName());
                String regCode = getDeviceRegistrationCode(browsertype, backofficeurl, userName, userPassword, licensename);
                loginscreen.registeriOSDevice(regCode);
            } else {
                SelectEnvironmentPopup selectenvscreen = new SelectEnvironmentPopup();
                LoginScreen loginscreen = selectenvscreen.selectEnvironment(environmentType.getEnvironmentTypeName());
                String regCode = getDeviceRegistrationCode(browsertype, backofficeurl, userName, userPassword, licensename);
                loginscreen.registeriOSDevice(regCode);
            }
        }
    }
}
