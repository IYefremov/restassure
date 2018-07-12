package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.config.BOConfigInfo;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.lang.reflect.Method;

public class BaseTestCase {

	private ScreenRecorder screenRecorder;
	protected AppiumDriver<MobileElement> appiumdriver;
	protected WebDriver webdriver;
	protected DesiredCapabilities appiumcap;
	protected DesiredCapabilities webcap;
	protected static BrowserType browsertype;
	protected static MobilePlatform mobilePlatform = MobilePlatform.IOS_REGULAR;
	protected File app;
	String bundleid = "";
	protected static AppiumDriverLocalService service;

//	@BeforeClass
//	@Parameters({ "selenium.browser", "ios.bundleid" })
//	public void setUp(@Optional("chrome") String browser, String bundleid) throws Exception {
//		/*
//		 * GraphicsConfiguration gc = GraphicsEnvironment
//		 * .getLocalGraphicsEnvironment().getDefaultScreenDevice()
//		 * .getDefaultConfiguration();
//		 *
//		 *
//		 * screenRecorder = new ScreenRecorder(gc, new Format(MediaTypeKey,
//		 * MediaType.FILE, MimeTypeKey, MIME_AVI), new Format( MediaTypeKey,
//		 * MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
//		 * CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey,
//		 * (int) 24, FrameRateKey, Rational.valueOf(15), QualityKey, 1.0f,
//		 * KeyFrameIntervalKey, (int) (15 * 60)), new Format(MediaTypeKey,
//		 * MediaType.VIDEO, EncodingKey, "black", FrameRateKey,
//		 * Rational.valueOf(30)), null); screenRecorder.start();
//		 * System.out.println("++++++++++" + screenRecorder.getState().name());
//		 * System.
//		 *
//		 *
//		 *
//		 * out.println("++++++++++" +
//		 * screenRecorder.getCreatedMovieFiles().get(0).getPath());
//		 */
//
////		service = new AppiumServiceBuilder().withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
////				 .usingAnyFreePort().withArgument(SESSION_OVERRIDE)
////				 .withArgument(LOG_LEVEL, "error")
////				 .build();
////	        service.start();
////
////	    if (service == null || !service.isRunning()) {
////	    	throw new AppiumServerHasNotBeenStartedLocallyException("An appium server node is not started!");
////	    }
//	    this.bundleid =  bundleid;
//		browsertype = BaseUtils.getBrowserType(browser);
//
//		DriverBuilder.getInstance().setDriver(browsertype);
//		webdriver = DriverBuilder.getInstance().getDriver();
//		webdriver.navigate().refresh();
//	}

//	@BeforeSuite
//	public void setUp() {
//		browsertype = BaseUtils.getBrowserType(BOConfigInfo.getInstance().getDefaultBrowser());
//		DriverBuilder.getInstance().setDriver(browsertype);
//		webdriver = DriverBuilder.getInstance().getDriver();
//		webdriver.navigate().refresh();
//	}

	public void setDriver() {
        webdriver = DriverBuilder.getInstance().getDriver();
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
	}

    @BeforeMethod
    public void BackOfficeLogin(Method method) throws InterruptedException {
        //todo delete from here after the problem with skips will be solved
        browsertype = BaseUtils.getBrowserType(BOConfigInfo.getInstance().getDefaultBrowser());
        try {
            DriverBuilder.getInstance().setDriver(browsertype);
        } catch (SessionNotCreatedException e) {
            Thread.sleep(10000);
            DriverBuilder.getInstance().setDriver(browsertype);
        }
        webdriver = DriverBuilder.getInstance().getDriver();
        webdriver.navigate().refresh();
        //todo delete from here after the problem with skips will be solved
        WebDriverUtils.webdriverGotoWebPage(BOConfigInfo.getInstance().getBackOfficeURL());
        BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);
        loginpage.UserLogin(BOConfigInfo.getInstance().getUserName(), BOConfigInfo.getInstance().getUserPassword());
    }

    @AfterMethod
    public void BackOfficeLogout(ITestResult result) {
        if (result.isSuccess()) {
            BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
            try {
                backofficeheader.clickLogout();
            } catch (WebDriverException ignored) {}
        }
        //todo delete from here after the problem with skips will be solved
        if (DriverBuilder.getInstance().getDriver() != null) {
            DriverBuilder.getInstance().getDriver().quit();
        }
        //todo delete from here after the problem with skips will be solved
    }
}
