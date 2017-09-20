package com.cyberiansoft.test.bo.utils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;

public class WebDriverFactory {
	
	protected WebDriver webdriver;
	protected DesiredCapabilities webcap;
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
    String PATH_TO_IE_DRIVER = "C:/IEdriver/IEDriverServer.exe";
    String URL = "https://reconpro.cyberianconcepts.com";
	
	public WebDriver getDriver(String browserName) {
		switch (browserName) {
		case "firefox":
			FirefoxDriverManager.getInstance().setup();
			webcap = DesiredCapabilities.firefox();			
			webdriver = new FirefoxDriver(webcap);
			break;
		case "ie":
			File file = new File(PATH_TO_IE_DRIVER);
	         System.setProperty("webdriver.ie.driver", file.getAbsolutePath());

	         DesiredCapabilities IEDesiredCapabilities = DesiredCapabilities.internetExplorer();
	         IEDesiredCapabilities.setCapability("nativeEvents", false);    
	         IEDesiredCapabilities.setCapability("unexpectedAlertBehaviour", "accept");
	         IEDesiredCapabilities.setCapability("ignoreProtectedModeSettings", true);
	         IEDesiredCapabilities.setCapability("disable-popup-blocking", true);
	         IEDesiredCapabilities.setCapability("enablePersistentHover", true);

	         IEDesiredCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
	         IEDesiredCapabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, URL);
	         IEDesiredCapabilities.internetExplorer().setCapability("ignoreProtectedModeSettings", true);
	         IEDesiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	         IEDesiredCapabilities.setJavascriptEnabled(true);
	         IEDesiredCapabilities.setCapability("requireWindowFocus", true);
	         IEDesiredCapabilities.setCapability("enablePersistentHover", false);

			webdriver = new InternetExplorerDriver(IEDesiredCapabilities);
			
//			InternetExplorerDriverManager.getInstance().setup();
//			webcap = DesiredCapabilities.internetExplorer();
//			//webcap.setCapability("nativeEvents", false); 
//			IEDesiredCapabilities.setCapability(CapabilityType.HAS_NATIVE_EVENTS, false);	
//			IEDesiredCapabilities.setCapability(CapabilityType.SUPPORTS_APPLICATION_CACHE, false);
//			IEDesiredCapabilities.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "none");
//			IEDesiredCapabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION,true);
//			webdriver = new InternetExplorerDriver(IEDesiredCapabilities);
			break;
		case "chrome":
			ChromeDriverManager.getInstance().setup();
			webcap =  DesiredCapabilities.chrome();
			webdriver = new ChromeDriver();
			break;
		case "safari":
			SafariOptions safariOpts = new SafariOptions();
		    DesiredCapabilities cap = DesiredCapabilities.safari();

		    safariOpts.setUseCleanSession(true);
		    cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		    cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, "dismiss");
		    cap.setCapability(SafariOptions.CAPABILITY, safariOpts);
		    cap.setBrowserName("safari");
		    cap.setPlatform(Platform.MAC);
		    webdriver = new SafariDriver(cap);
		    break;
		}
		 
		webdriver.manage().window().maximize();
		webdriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		webdriver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
	    return webdriver;
	 }
}
