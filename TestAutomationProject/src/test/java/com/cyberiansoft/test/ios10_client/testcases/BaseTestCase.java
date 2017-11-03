package com.cyberiansoft.test.ios10_client.testcases;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import static io.appium.java_client.service.local.flags.GeneralServerFlag.SESSION_OVERRIDE;
import static io.appium.java_client.service.local.flags.GeneralServerFlag.LOG_LEVEL;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
import com.cyberiansoft.test.bo.utils.WebDriverInstansiator;
import com.cyberiansoft.test.core.AppiumDriverBuilder;
import com.cyberiansoft.test.core.AppiumDriverBuilder.AndroidDriverBuilder;
import com.cyberiansoft.test.core.AppiumDriverBuilder.IOSDriverBuilder;
import com.cyberiansoft.test.core.IOSRegularDeviceInfo;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class BaseTestCase {

	private ScreenRecorder screenRecorder;
	protected IOSDriver<MobileElement> appiumdriver;
	protected WebDriver webdriver;
	protected DesiredCapabilities appiumcap;
	protected DesiredCapabilities webcap;
	
	protected TestUser testuser;
	protected String userpsw;
	protected static ExtentTest testlogger;
	
	protected static AppiumDriverLocalService service;
	String bundleid = "";
	String buildtype = "";
	
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
		 
		 
		 //service = AppiumDriverLocalService.buildDefaultService();
		/* service = AppiumDriverLocalService
					.buildService(new AppiumServiceBuilder()
							.usingDriverExecutable(new File("/usr/local/bin/node"))
							.withAppiumJS(
									new File(
											"/usr/local/lib/node_modules/appium/build/lib/main.js"))
							.withIPAddress("127.0.0.1").usingPort(4723));

	        service.start();

	        if (service == null || !service.isRunning()) {
	            throw new AppiumServerHasNotBeenStartedLocallyException("An appium server node is not started!");
	        }*/

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
		//appiumdriver = AndroidDriverBuilder.forIOS().againstLocalhost().newInstance();
		 
		 /*service = new AppiumServiceBuilder().withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
				 .usingAnyFreePort().withArgument(SESSION_OVERRIDE)
				 .withArgument(LOG_LEVEL, "error").build();
	        service.start();

	        if (service == null || !service.isRunning()) {
	            throw new AppiumServerHasNotBeenStartedLocallyException("An appium server node is not started!");
	        }*/
		 
		WebDriverInstansiator.setDriver(browser);
		webdriver = WebDriverInstansiator.getDriver();
	}

	public static WebElement wait(By locator) {
		return Helpers.wait(locator);
	}

	public void webdriverInicialize() throws Exception {

		WebDriverInstansiator.setDriver("chrome");
		webdriver = WebDriverInstansiator.getDriver();
		webdriver.manage().window().maximize();
		webdriver.manage().timeouts().implicitlyWait(8000, TimeUnit.SECONDS);
		//webdriver = new ChromeDriver();
	}

	public void appiumdriverInicialize(String buildtype)  {
		/*try {
			appiumdriver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"),
					appiumcap);
		} catch (MalformedURLException e) {			
			e.printStackTrace();
		}*/
		//appiumdriver = new IOSDriver<>(service.getUrl(), appiumcap);
		//appiumdriver = new IOSDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), appiumcap);

		//PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		this.buildtype = buildtype;
		if (buildtype.toLowerCase().equals("hd"))
			//appiumdriver = AndroidDriverBuilder.forIOSHD().againstHost(service.getUrl()).newInstance();
			appiumdriver = AndroidDriverBuilder.forIOSHD().againstLocalhost().newInstance();
		else
			appiumdriver = AndroidDriverBuilder.forIOSRegular().againstHost(service.getUrl()).newInstance();
		appiumdriver.manage().timeouts().implicitlyWait(800, TimeUnit.SECONDS);
		Helpers.init(appiumdriver);
	}
	
	public IOSDriver<MobileElement> getAppiumDriver() {
		return appiumdriver;
	}
	
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

	public void webdriverGotoWebPage(String url) {
		webdriver.get(url);
	}

	public WebDriver getWebDriver() {
		return webdriver;
	}

	@AfterSuite
	public void tearDown() throws Exception {
		if (webdriver != null)
			webdriver.quit();
		if (appiumdriver != null)
			appiumdriver.quit();
		if (service != null) {
            service.stop();
        }
	}
	
	public void resrtartApplication() throws MalformedURLException {
		try {
			appiumdriver.closeApp();
		} catch (NoSuchSessionException e) {
			appiumdriverInicialize(buildtype);
		}
		Helpers.waitABit(7000);
		try {
			appiumdriver.launchApp();
			Helpers.waitABit(7000);
		} catch (WebDriverException e) {
			try {
				Thread.sleep(7000);
			} catch (InterruptedException e1) {
				// do nothing
			}
			appiumdriver.launchApp();
			Helpers.waitABit(2000);
		}
	}

}
