package com.cyberiansoft.test.inhouse.testcases;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.driverutils.DriverBuilder;

import java.io.File;
import java.io.IOException;


public class BaseTestCase {

	protected WebDriver webdriver;
	public BrowserType browsertype;
	protected File app;
	
	@BeforeSuite
	public void cleanScreenShotsFolder() throws IOException{
		FileUtils.cleanDirectory(new File(".//report")); 
	}
	
	@BeforeClass
	@Parameters({ "selenium.browser" })
	public void setUp(String browser) throws Exception {
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

	public WebDriver getWebDriver() {
		return webdriver;
	}

	@AfterMethod
	public void cookieCleaner(){
		webdriver.get("https://sc.cyberianconcepts.com");
		webdriver.manage().deleteAllCookies();
	}
	
	@AfterClass
	public void tearDown() throws Exception {

		if (webdriver != null)
			webdriver.quit();
	}

	public void webdriverGotoWebPage(String url) {
		webdriver.manage().window().maximize();
		webdriver.get(url);
	}
}
