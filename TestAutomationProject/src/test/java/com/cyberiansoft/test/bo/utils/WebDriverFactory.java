package com.cyberiansoft.test.bo.utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
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
			InternetExplorerDriverManager.getInstance().arch64().setup();
	        DesiredCapabilities IEDesiredCapabilities = DesiredCapabilities.internetExplorer();

//			DesiredCapabilities IEDesiredCapabilities = DesiredCapabilities.internetExplorer();
//	         System.setProperty("webdriver.ie.driver", PATH_TO_IE_DRIVER);

	         IEDesiredCapabilities.setCapability("nativeEvents", false);    
	         IEDesiredCapabilities.setCapability("unexpectedAlertBehaviour", "accept");
	         IEDesiredCapabilities.setCapability("ignoreProtectedModeSettings", true);
	         IEDesiredCapabilities.setCapability("disable-popup-blocking", true);
	         IEDesiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	         IEDesiredCapabilities.setCapability("ignoreZoomSetting", true);
	         IEDesiredCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
	         IEDesiredCapabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, URL);
	         IEDesiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	         IEDesiredCapabilities.setJavascriptEnabled(true);
	         IEDesiredCapabilities.setCapability("requireWindowFocus", false);
	         IEDesiredCapabilities.setCapability("enablePersistentHover", false);
			webcap = DesiredCapabilities.internetExplorer();
			webdriver = new RemoteWebDriver(IEDesiredCapabilities);
			//webdriver = new InternetExplorerDriver(IEDesiredCapabilities);
			//webdriver.findElement(By.tagName("html")).sendKeys(Keys.chord(Keys.CONTROL, "0"));
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
