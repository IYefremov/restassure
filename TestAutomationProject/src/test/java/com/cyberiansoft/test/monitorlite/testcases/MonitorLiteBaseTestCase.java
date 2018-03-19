package com.cyberiansoft.test.monitorlite.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;


public class MonitorLiteBaseTestCase {
	
	protected WebDriver webdriver;
	public String browsertype;
	
	@BeforeClass
	@Parameters({ "selenium.browser", "ios.bundleid" })
	public void setUp(String browser, String bundleid) throws Exception {
		
		browsertype = browser;
		//WebDriverInstansiator.setDriver(browser);
		//webdriver = WebDriverInstansiator.getDriver();
	}
	
	public WebDriver getWebDriver() {
		return webdriver;
	}
	
	public void webdriverGotoWebPage(String url) {
		webdriver.get(url);
	}
	
	@AfterClass
	public void tearDown() throws Exception {

		if (webdriver != null)
			webdriver.quit();
	}

}
