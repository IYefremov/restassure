package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;

import java.io.File;

public class BaseTestCase {

	private ScreenRecorder screenRecorder;
	protected WebDriver webdriver;
	protected DesiredCapabilities webcap;
	protected static BrowserType browserType;
	protected File app;

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
	}

//    @BeforeMethod
//    public void BackOfficeLogin() {
//        browserType = BaseUtils.getBrowserType(VNextBOConfigInfo.getInstance().getDefaultBrowser());
//        try {
//            DriverBuilder.getInstance().setDriver(browserType);
//        } catch (WebDriverException e) {
//            e.printStackTrace();
////            await().atMost(30, TimeUnit.SECONDS).ignoreExceptions().until(() -> DriverBuilder.getInstance().setDriver(browserType));
//        }
//        webdriver = DriverBuilder.getInstance().getDriver();
//        WebDriverUtils.webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOURL());
//        BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);
//        loginpage.UserLogin(VNextBOConfigInfo.getInstance().getVNextBONadaMail(), VNextBOConfigInfo.getInstance().getVNextBOPassword());
//    }

    public void setBrowser() {
        DriverBuilder.getInstance().setDriver(browserType);
    }

//    @AfterMethod
//    public void BackOfficeLogout(ITestResult result) {
//        if (result.isSuccess()) {
//            BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
//            try {
//                backOfficeHeader.clickLogout();
//            } catch (WebDriverException ignored) {}
//        }
//        DriverBuilder.getInstance().quitDriver();
//    }
}