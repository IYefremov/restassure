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

import io.github.bonigarcia.wdm.ChromeDriverManager;

public class WebDriverFactory {
	
	protected WebDriver webdriver;
	protected DesiredCapabilities webcap;
	
	public WebDriver getDriver(String browserName) {
		switch (browserName) {
		case "firefox":
			webcap = DesiredCapabilities.firefox();
			if (Platform.getCurrent().is(Platform.WINDOWS)) {
				System.setProperty("webdriver.gecko.driver", "./browsers/geckodriver/geckodriver.exe");
			} else {
				System.setProperty("webdriver.gecko.driver", "./browsers/geckodriver/geckodriver");
			}
			webdriver = new FirefoxDriver(webcap);
			break;
		case "ie":
			System.setProperty(InternetExplorerDriverService.IE_DRIVER_EXE_PROPERTY, "./browsers/iedriver/IEDriverServer.exe");
			webcap = DesiredCapabilities.internetExplorer();
			//webcap.setCapability("nativeEvents", false); 
			webcap.setCapability(CapabilityType.HAS_NATIVE_EVENTS, false);	
			webcap.setCapability(CapabilityType.SUPPORTS_APPLICATION_CACHE, false);
			webcap.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "slow");
			webdriver = new InternetExplorerDriver(webcap);
			break;
		case "chrome":
			/*if (Platform.getCurrent().is(Platform.WINDOWS)) {
				System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,
			    		 "./browsers/chromedriver/chromedriver.exe");
			} else {
				System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,
			    		 "./browsers/chromedriver/chromedriver");
			}*/
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
	 
	 public void webdriverInicialize(String browsertype) {
			System.setProperty("webdriver.ie.driver", "C:/iedriver/IEDriverServer.exe");
			/*try {
				webdriver = new RemoteWebDriver(
						new URL("http://127.0.0.1:4444/wd/hub"), webcap);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			 if (browsertype.equalsIgnoreCase("chrome")) {
				/*ChromeDriverService service = new ChromeDriverService.Builder()
			        .usingDriverExecutable(new File("C:/iedriver/IEDriverServer.exe"))
			        .usingPort(9515)
			        .build();
					service.start();*/
					/*try {
						webdriver = new RemoteWebDriver(
								new URL("http://127.0.0.1:9515"), webcap);
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
				 webdriver = new ChromeDriver(webcap);
			 } else if (browsertype.equalsIgnoreCase("firefox")) {
				 webdriver = new FirefoxDriver(webcap);
			 } else if (browsertype.equalsIgnoreCase("ie")) {
				 webdriver = new InternetExplorerDriver(webcap);
				/* try {
					webdriver = new RemoteWebDriver(
								new URL("http://127.0.0.1:5555"), webcap);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			 } else {
				 webdriver = new ChromeDriver();
			 }
			
			
			//webdriver = new InternetExplorerDriver();
			webdriver.manage().window().maximize();
			webdriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			webdriver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			//webdriver.manage().timeouts().implicitlyWait(8000, TimeUnit.SECONDS);
			//webdriver = new ChromeDriver();
		}

}
