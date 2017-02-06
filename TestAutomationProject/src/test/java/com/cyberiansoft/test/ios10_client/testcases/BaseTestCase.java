package com.cyberiansoft.test.ios10_client.testcases;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
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
	protected File app;
	
	protected TestUser testuser;
	protected String userpsw;
	protected static ExtentTest testlogger;
	
	protected static AppiumDriverLocalService service;
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
	public void setUp(String browser, String bundleid, String buildfilename, String udid ) throws Exception {

		// Parameters for WebDriver
		 
		 this.bundleid =  bundleid;
		 /*File appDir = new File("/Users/kolin/Documents");
		    File app = new File(appDir, "ReconPro_HD.app");
		    appiumcap = new DesiredCapabilities();
		    appiumcap.setCapability(MobileCapabilityType.BROWSER_NAME, "");
		    appiumcap.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.3");
		    appiumcap.setCapability(MobileCapabilityType.DEVICE_NAME, "iPad 2");
		    appiumcap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());*/
		
		// parameters for Appium iOS Driver
		// File appDir = new File("/Users/vladimir/Downloads/");
		// File app = new File(appDir, "ReconPro_HD.app");
		    
		/* service = AppiumDriverLocalService.buildDefaultService();
	        service.start();

	        if (service == null || !service.isRunning()) {
	            throw new RuntimeException("An appium server node is not started!");
	        }   
		  */  
		appiumcap = new DesiredCapabilities();
		appiumcap.setCapability(MobileCapabilityType.BROWSER_NAME, "");
		appiumcap.setCapability(MobileCapabilityType.DEVICE_NAME,"iPad Air");
		//appiumcap.setCapability(MobileCapabilityType.DEVICE_NAME,"iPhone 7 Plus");
		//appiumcap.setCapability(MobileCapabilityType.DEVICE_NAME,"iPhone 7");
		appiumcap.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10.2");
		appiumcap.setCapability(MobileCapabilityType.FULL_RESET, true);
		appiumcap.setCapability(MobileCapabilityType.NO_RESET, true);
		appiumcap.setCapability("nativeWebTap", true);
		appiumcap.setCapability("automationName", "XCUITest"); 
		appiumcap.setCapability("appiumVersion", "1.6.1");
		//appiumcap.setCapability("udid", "02a22d694f78075036f90c22f5aa89ba6fa35b93");
		appiumcap.setCapability("bundleid", this.bundleid);
		
		appiumcap.setCapability("waitForAppScript", "$.delay(5000); $.acceptAlert();");
		appiumcap.setCapability("newCommandTimeout", "120");
		//appiumcap.setCapability(CapabilityType.VERSION, "7.1.1");
		/*appiumcap.setCapability("platformVersion", "7.1");
		appiumcap.setCapability(CapabilityType.PLATFORM, "Mac");
		appiumcap.setCapability("platformName", "iOS");
		appiumcap.setCapability("newCommandTimeout", "120");
		appiumcap.setCapability("device", "iPad");		
		appiumcap.setCapability("deviceName", "iPad Air");*/
		
		File appDir = new File("./data/");
	    //app = new File(appDir, "ReconPro_1124.app.zip");
		//app = new File(appDir, "ReconPro_1208.app.zip");
		app = new File(appDir, buildfilename);
		appiumcap.setCapability("app", app.getAbsolutePath());
	
		//appiumcap.setCapability("app", app.getAbsolutePath());
		
		// appiumcap.setCapability("app", app.getAbsolutePath());
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		final String appfileversion = dateFormat.format(cal.getTime())
				.substring(5, 7)
				+ dateFormat.format(cal.getTime()).substring(8, 10);

		// appiumcap.setCapability("app",
		//"http://amtqc.cyberiansoft.net/Uploads/ReconPro_HD_" + appfileversion + ".app.zip");

		//appiumcap
		//	.setCapability("app",
		//				"http://amtqc.cyberiansoft.net/Uploads/ReconPro_HD_0413.app.zip");

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

	public void appiumdriverInicialize() throws MalformedURLException {
		/*try {
			appiumdriver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"),
					appiumcap);
		} catch (MalformedURLException e) {			
			e.printStackTrace();
		}*/
		//appiumdriver = new IOSDriver<>(service.getUrl(), appiumcap);
		//appiumdriver = new IOSDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), appiumcap);

		//PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		appiumdriver = AndroidDriverBuilder.forIOS().againstLocalhost().newInstance();
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
		// this.screenRecorder.stop();
	}
	
	public void resrtartApplication() throws MalformedURLException {
		try {
			appiumdriver.closeApp();
		} catch (NoSuchSessionException e) {
			appiumdriverInicialize();
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
