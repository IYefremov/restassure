package com.cyberiansoft.test.bo.utils;

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
	
	public WebDriver getDriver(String browserName) {
		switch (browserName) {
		case "firefox":
			FirefoxDriverManager.getInstance().setup();
			webcap = DesiredCapabilities.firefox();			
			webdriver = new FirefoxDriver(webcap);
			break;
		case "ie":
			InternetExplorerDriverManager.getInstance().setup();
			webcap = DesiredCapabilities.internetExplorer();
			//webcap.setCapability("nativeEvents", false); 
			webcap.setCapability(CapabilityType.HAS_NATIVE_EVENTS, false);	
			webcap.setCapability(CapabilityType.SUPPORTS_APPLICATION_CACHE, false);
			webcap.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "none");
			webcap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION,true);
			webdriver = new InternetExplorerDriver(webcap);
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
