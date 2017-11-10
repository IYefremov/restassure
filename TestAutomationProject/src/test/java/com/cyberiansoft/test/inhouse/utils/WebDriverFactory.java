package com.cyberiansoft.test.inhouse.utils;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
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

import java.util.concurrent.TimeUnit;

public class WebDriverFactory {
	
	protected WebDriver webdriver;
	protected DesiredCapabilities webcap;
    String URL = "https://reconpro.cyberianconcepts.com";
	
	public WebDriver getDriver(String browserName) {
		switch (browserName) {

		case "chrome":
			ChromeDriverManager.getInstance().setup();
			webcap =  DesiredCapabilities.chrome();
			webdriver = new ChromeDriver();
			break;
		}
		 
		webdriver.manage().window().maximize();
		webdriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		webdriver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
	    return webdriver;
	 }
}
