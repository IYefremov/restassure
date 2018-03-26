package com.cyberiansoft.test.ios10_client.testcases;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios_client.utils.TestUser;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.driverutils.AppiumDriverServiceBuilder;
import com.cyberiansoft.test.driverutils.AppiumInicializator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class BaseTestCase {

	private ScreenRecorder screenRecorder;
	protected AppiumDriver<MobileElement> appiumdriver;
	protected WebDriver webdriver;
	protected DesiredCapabilities appiumcap;
	protected DesiredCapabilities webcap;
	protected static BrowserType browsertype;
	protected static MobilePlatform mobilePlatform;
	protected TestUser testuser;
	protected String userpsw;
	protected static ExtentTest testlogger;

	String bundleid = "";
	
	public void setTestLogger(ExtentTest logger) {
		testlogger = logger;
	}
	
	public void initTestUser(String username,  String userpsw) {
		this.testuser = new TestUser(username, userpsw);
	}
	
	public TestUser getTestUser() {
		return testuser;
	}
	
	
	@BeforeSuite
	@Parameters({ "selenium.browser", "ios.bundleid", "ios.build", "ios.udid" })
	public void setUp(String browser, String bundleid, String buildtype, String udid ) throws Exception {

		// Parameters for WebDriver
		 
		 this.bundleid =  bundleid;

		/*
		 * GraphicsConfiguration gc = GraphicsEnvironment
		 * .getLocalGraphicsEnvironment().getDefaultScreenDevice()
		 * .getDefaultConfiguration();
		 * 
		 * 
		 * screenRecorder = new ScreenRecorder(gc, new Format(MediaTypeKey,
		 * MediaType.FILE, MimeTypeKey, MIME_AVI), new Format( MediaTypeKey,
		 * MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
		 * CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey,
		 * (int) 24, FrameRateKey, Rational.valueOf(15), QualityKey, 1.0f,
		 * KeyFrameIntervalKey, (int) (15 * 60)), new Format(MediaTypeKey,
		 * MediaType.VIDEO, EncodingKey, "black", FrameRateKey,
		 * Rational.valueOf(30)), null); screenRecorder.start();
		 * System.out.println("++++++++++" + screenRecorder.getState().name());
		 * System.out.println("++++++++++" +
		 * screenRecorder.getCreatedMovieFiles().get(0).getPath());
		 */
		 
		 AppiumDriverServiceBuilder.getInstance().buildAppiumService();
	     browsertype = BaseUtils.getBrowserType(browser);
	}

	public static WebElement wait(By locator) {
		return Helpers.wait(locator);
	}

	/*public void appiumdriverInicialize(String buildtype)  {
		//appiumdriver = new IOSDriver<>(service.getUrl(), appiumcap);
		//appiumdriver = new IOSDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), appiumcap);

		//PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		this.buildtype = buildtype;
		if (buildtype.toLowerCase().equals("hd"))
			DriverBuilder.getInstance().setAppiumDriver(MobilePlatform.IOS_HD, service.getUrl());
			//appiumdriver = AndroidDriverBuilder.forIOSHD().againstHost(service.getUrl()).newInstance();
			//appiumdriver = AndroidDriverBuilder.forIOSHD().againstLocalhost().newInstance();
		else
			DriverBuilder.getInstance().setAppiumDriver(MobilePlatform.IOS_REGULAR, service.getUrl());
			//appiumdriver = AndroidDriverBuilder.forIOSRegular().againstHost(service.getUrl()).newInstance();
			//appiumdriver = AndroidDriverBuilder.forIOSRegular().againstLocalhost().newInstance();
		appiumdriver = (IOSDriver<MobileElement>) DriverBuilder.getInstance().getAppiumDriver();
		appiumdriver.manage().timeouts().implicitlyWait(800, TimeUnit.SECONDS);
		
		Helpers.init(appiumdriver);
	}*/
	
	public String createScreenshot(WebDriver driver, String loggerdir) {
		WebDriver driver1 = new Augmenter().augment(appiumdriver);
		UUID uuid = UUID.randomUUID();
		File file = ((TakesScreenshot) driver1).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(file , new File(loggerdir + "/myscreen" + uuid + ".jpeg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "myscreen" + uuid + ".jpeg";
	}

	@AfterSuite
	public void tearDown() throws Exception {
		if (DriverBuilder.getInstance().getDriver() != null)
				DriverBuilder.getInstance().getDriver().quit();
		if (DriverBuilder.getInstance().getAppiumDriver() != null)
			DriverBuilder.getInstance().getAppiumDriver().quit();
		if (AppiumDriverServiceBuilder.getInstance().getAppiumService() != null) {
			AppiumDriverServiceBuilder.getInstance().getAppiumService().stop();
        }
	}
	
	public void resrtartApplication() throws MalformedURLException {
		try {
			DriverBuilder.getInstance().getAppiumDriver().closeApp();
		} catch (NoSuchSessionException e) {
			AppiumInicializator.getInstance().initAppium(mobilePlatform);
			Helpers.init(DriverBuilder.getInstance().getAppiumDriver());
		}
		Helpers.waitABit(7000);
		try {
			DriverBuilder.getInstance().getAppiumDriver().launchApp();
			Helpers.waitABit(7000);
		} catch (WebDriverException e) {
			try {
				Thread.sleep(7000);
			} catch (InterruptedException e1) {
				// do nothing
			}
			DriverBuilder.getInstance().getAppiumDriver().launchApp();
			Helpers.waitABit(2000);
		}
	}

}
