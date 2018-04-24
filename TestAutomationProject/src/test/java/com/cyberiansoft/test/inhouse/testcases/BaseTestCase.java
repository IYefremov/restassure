package com.cyberiansoft.test.inhouse.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.inhouse.config.InHouseConfigInfo;
import com.cyberiansoft.test.inhouse.pageObject.TeamPortalHeader;
import com.cyberiansoft.test.inhouse.pageObject.TeamPortalLoginPage;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;


public class BaseTestCase {

	protected WebDriver webdriver;
	public BrowserType browsertype;
	protected File app;
	
	@BeforeSuite
	public void cleanScreenShotsFolder() throws IOException{
		FileUtils.cleanDirectory(new File(".//report")); 
	}
	
	@BeforeClass
	public void setUp() throws InterruptedException {
        browsertype = BaseUtils.getBrowserType(InHouseConfigInfo.getInstance().getDefaultBrowser());
        try {
            DriverBuilder.getInstance().setDriver(browsertype);
        } catch (SessionNotCreatedException e) {
            Thread.sleep(10000);
            DriverBuilder.getInstance().setDriver(browsertype);
        }
        webdriver = DriverBuilder.getInstance().getDriver();
        webdriver.navigate().refresh();


//		for (BrowserType browserTypeEnum : BrowserType.values()) {
//            if (StringUtils.equalsIgnoreCase(browserTypeEnum.getBrowserTypeString(), browser)) {
//                this.browsertype = browserTypeEnum;
//                return;
//            }
//        }
//
//        webdriver = DriverBuilder.getInstance().getDriver();
//        DriverBuilder.getInstance().setDriver(browsertype);
//		webdriver.navigate().refresh();
	}

    public WebDriver getWebDriver() {
		return webdriver;
	}

//	@AfterMethod
//	public void cookieCleaner(){
//		webdriver.get(InHouseConfigInfo.getInstance().getInHouseURL());
//		webdriver.manage().deleteAllCookies();
//	}
	
	@AfterClass
	public void tearDown() {
		if (webdriver != null)
			webdriver.quit();
	}


    @BeforeMethod
    public void teamPortalLogin(Method method) throws InterruptedException {
        System.out.printf("\n* Starting test : %s Method : %s\n", getClass(), method.getName());
        webdriverGotoWebPage(InHouseConfigInfo.getInstance().getInHouseURL());
        TeamPortalLoginPage loginPage = PageFactory.initElements(webdriver,
                TeamPortalLoginPage.class);
        loginPage.loginByGmail();
        Thread.sleep(2000);
    }

    @AfterMethod
    public void teamPortalLogout() throws InterruptedException {
        TeamPortalHeader headerPanel = PageFactory.initElements(webdriver,
                TeamPortalHeader.class);
        headerPanel.clickLogOutButton();
        Thread.sleep(1000);
        webdriver.get(InHouseConfigInfo.getInstance().getInHouseURL());
        webdriver.manage().deleteAllCookies();
    }

	public void webdriverGotoWebPage(String url) {
		webdriver.manage().window().maximize();
		webdriver.get(url);
	}
}
