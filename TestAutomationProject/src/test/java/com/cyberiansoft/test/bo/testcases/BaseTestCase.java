package com.cyberiansoft.test.bo.testcases;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.ios_client.utils.Helpers;

public class BaseTestCase {

	private ScreenRecorder screenRecorder;
	protected AppiumDriver appiumdriver;
	protected WebDriver webdriver;
	protected DesiredCapabilities appiumcap;
	protected DesiredCapabilities webcap;
	public BrowserType browsertype;
	protected File app;
	String bundleid = "";
	
	
	@BeforeSuite
	public void cleanScreenShotsFolder() throws IOException{
		FileUtils.cleanDirectory(new File(".//report")); 
	}
	
	@BeforeClass
	@Parameters({ "selenium.browser", "ios.bundleid" })
	public void setUp(String browser, String bundleid) throws Exception {

		// Parameters for WebDriver
		/* System.setProperty("webdriver.chrome.driver",
		 "/Users/Shared/chromedriver");
		 if (browser.equalsIgnoreCase("chrome")) {
			 //ChromeOptions options = new ChromeOptions();
			 //options.addArguments("chrome.switches","--disable-extensions");
			 webcap =  DesiredCapabilities.chrome();
			 //webcap.setCapability(ChromeOptions.CAPABILITY, options);
			 System.setProperty("webdriver.chrome.driver", "C:/chromedriver/chromedriver.exe");
			 browsertype = "chrome";
		 } else if (browser.equalsIgnoreCase("firefox")) {
			 webcap = DesiredCapabilities.firefox();
			 browsertype = "firefox";
		 } else if (browser.equalsIgnoreCase("ie")) {
			 System.setProperty("webdriver.ie.driver", "C:/iedriver/IEDriverServer.exe");
			 browsertype = "ie";
			 webcap = DesiredCapabilities.internetExplorer();
			 //webcap.setCapability("nativeEvents", false); 
			 
			 webcap.setCapability("ignoreZoomSetting", true);
		 } else {
			 System.setProperty("webdriver.chrome.driver", "C:/chromedriver/chromedriver.exe");
			 browsertype = "chrome";
			 webcap = DesiredCapabilities.chrome(); 
		 }
		
		
		
		// parameters for Appium iOS Driver
		// File appDir = new File("/Users/vladimir/Downloads/");
		// File app = new File(appDir, "ReconPro_HD.app");
		appiumcap = new DesiredCapabilities();
		appiumcap.setCapability(CapabilityType.BROWSER_NAME, "");
		//appiumcap.setCapability(CapabilityType.VERSION, "7.1.1");
		
		//File appDir = new File("/Users/Shared/");
	    //File app = new File(appDir, "ReconPro_HD.app");
	    appiumcap.setCapability("deviceName","iPad mini 2");
		appiumcap.setCapability("udid", "e80c0135055f336bd77e60483142facccb22b702");
		appiumcap.setCapability("bundleid", "com.automobiletechnologies.reconprohd");
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

		appiumcap
			.setCapability("app",
						"http://amtqc.cyberiansoft.net/Uploads/ReconPro_HD_0224.app.zip");

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
		 * System.
		 * 
		 * 
		 * 
		 * out.println("++++++++++" +
		 * screenRecorder.getCreatedMovieFiles().get(0).getPath());
		 */
		//webdriverInicialize() ;
		
		appiumcap = new DesiredCapabilities();
		appiumcap.setCapability(MobileCapabilityType.BROWSER_NAME, "");
		appiumcap.setCapability(MobileCapabilityType.DEVICE_NAME,"iPad mini 2");
		appiumcap.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.2");
		appiumcap.setCapability("udid", "e80c0135055f336bd77e60483142facccb22b702");
		appiumcap.setCapability("bundleid", this.bundleid);
		
		appiumcap.setCapability("waitForAppScript", "$.delay(5000); $.acceptAlert();");
		appiumcap.setCapability("newCommandTimeout", "120");
		File appDir = new File("./data/");
		app = new File(appDir, "ReconPro_0810.app.zip");
		appiumcap.setCapability("app", app.getAbsolutePath());
		
		for (BrowserType browserTypeEnum : BrowserType.values()) { 
            if (StringUtils.equalsIgnoreCase(browserTypeEnum.getBrowserTypeString(), browser)) { 
                this.browsertype = browserTypeEnum; 
                return; 
            } 
        } 

		DriverBuilder.getInstance().setDriver(browsertype);
		webdriver = DriverBuilder.getInstance().getDriver();
		webdriver.navigate().refresh();
	}

	public static WebElement wait(By locator) {
		return Helpers.wait(locator);
	}

	/*public void webdriverInicialize() throws Exception {
		System.setProperty("webdriver.ie.driver", "C:/iedriver/IEDriverServer.exe");

		 if (browsertype.equalsIgnoreCase("chrome")) {

			 webdriver = new ChromeDriver(webcap);
		 } else if (browsertype.equalsIgnoreCase("firefox")) {
			 webdriver = new FirefoxDriver();
		 } else if (browsertype.equalsIgnoreCase("ie")) {
			 webdriver = new InternetExplorerDriver();
			 //webdriver = new RemoteWebDriver(
				//		new URL("http://127.0.0.1:5555"), webcap);
		 } else {
			 webdriver = new ChromeDriver();
		 }
		
		
		//webdriver = new InternetExplorerDriver();
		webdriver.manage().window().maximize();
		webdriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		webdriver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		//webdriver.manage().timeouts().implicitlyWait(8000, TimeUnit.SECONDS);
		//webdriver = new ChromeDriver();
	}*/

	public void appiumdriverInicialize() throws Exception {
		appiumdriver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"),
				appiumcap);

		appiumdriver.manage().timeouts().implicitlyWait(800, TimeUnit.SECONDS);
		Helpers.init(appiumdriver);
	}

	public void webdriverGotoWebPage(String url) {
		webdriver.manage().window().maximize();
		webdriver.get(url);
		if (browsertype.equals("ie")) {
			if (webdriver.findElements(By.id("overridelink")).size() > 0) {
				webdriver.navigate().to("javascript:document.getElementById('overridelink').click()");
			}
		}
	}

	public WebDriver getWebDriver() {
		return webdriver;
	}

	@AfterMethod
	public void cookieCleaner(){
		webdriver.get("https://reconpro.cyberianconcepts.com/");
		webdriver.manage().deleteAllCookies();
	}
	
	@AfterClass
	public void tearDown() throws Exception {

		if (webdriver != null)
			webdriver.quit();
		if (appiumdriver != null)
			appiumdriver.quit();
	}

}
