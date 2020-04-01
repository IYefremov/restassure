package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.config.BOConfigInfo;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.validations.ServiceRequestsListVerifications;
import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

import java.io.File;

public class BaseTestCase {

	protected AppiumDriver<MobileElement> appiumdriver;
	protected WebDriver webdriver;
	protected DesiredCapabilities appiumcap;
	protected DesiredCapabilities webcap;
	protected static BrowserType browserType;
	protected static MobilePlatform mobilePlatform = MobilePlatform.IOS_REGULAR;
	protected File app;
	protected static AppiumDriverLocalService service;
    protected ServiceRequestsListVerifications serviceRequestsListVerifications;


    public void setDriver() {
        webdriver = DriverBuilder.getInstance().getDriver();
    }

	public static WebElement wait(By locator) {
		return Helpers.wait(locator);
	}

	@AfterSuite
	public void tearDown() {
		if (DriverBuilder.getInstance().getDriver() != null)
			DriverBuilder.getInstance().quitDriver();
		if (DriverBuilder.getInstance().getAppiumDriver() != null)
			DriverBuilder.getInstance().getAppiumDriver().quit();
	}

    @BeforeMethod
    public void BackOfficeLogin() {
        browserType = BaseUtils.getBrowserType(BOConfigInfo.getInstance().getDefaultBrowser());
        try {
            DriverBuilder.getInstance().setDriver(browserType);
        } catch (WebDriverException e) {
            e.printStackTrace();
//            await().atMost(30, TimeUnit.SECONDS).ignoreExceptions().until(() -> DriverBuilder.getInstance().setDriver(browserType));
        }
        webdriver = DriverBuilder.getInstance().getDriver();
        WebDriverUtils.webdriverGotoWebPage(BOConfigInfo.getInstance().getBackOfficeURL());
        BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);
        loginpage.userLogin(BOConfigInfo.getInstance().getUserNadaName(), BOConfigInfo.getInstance().getUserNadaPassword());
        serviceRequestsListVerifications = new ServiceRequestsListVerifications();
    }

    @AfterMethod
    public void BackOfficeLogout(ITestResult result) {
        if (result.isSuccess()) {
            BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
            try {
                backOfficeHeader.clickLogout();
            } catch (WebDriverException ignored) {}
        }
        DriverBuilder.getInstance().quitDriver();
    }
}