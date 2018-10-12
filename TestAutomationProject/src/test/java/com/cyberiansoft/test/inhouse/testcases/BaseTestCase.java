package com.cyberiansoft.test.inhouse.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.inhouse.config.InHouseConfigInfo;
import com.cyberiansoft.test.inhouse.pageObject.webpages.LoginPage;
import com.cyberiansoft.test.inhouse.pageObject.webpages.TeamPortalHeader;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;


public class BaseTestCase {

	protected WebDriver webdriver;
	public static BrowserType browsertype;
	protected File app;

//	@BeforeClass
//	public void setUp() throws InterruptedException {
//        browserType = BaseUtils.getBrowserType(InHouseConfigInfo.getInstance().getDefaultBrowser());
//        try {
//            DriverBuilder.getInstance().setDriver(browserType);
//        } catch (SessionNotCreatedException e) {
//            Thread.sleep(10000);
//            DriverBuilder.getInstance().setDriver(browserType);
//        }
//        webdriver = DriverBuilder.getInstance().getDriver();
//        webdriver.navigate().refresh();
//	}

	@AfterSuite
	public void tearDown() {
        DriverBuilder.getInstance().quitDriver();
	}


    @BeforeMethod
    public void teamPortalLogin() throws InterruptedException {
        browsertype = BaseUtils.getBrowserType(InHouseConfigInfo.getInstance().getDefaultBrowser());
        try {
            DriverBuilder.getInstance().setDriver(browsertype);
        } catch (SessionNotCreatedException e) {
            Thread.sleep(10000);
            DriverBuilder.getInstance().setDriver(browsertype);
        }
        webdriver = DriverBuilder.getInstance().getDriver();
        webdriver.navigate().refresh();
        try {
            goToWebPage(InHouseConfigInfo.getInstance().getInHouseURL());
            LoginPage loginPage = PageFactory.initElements(webdriver, LoginPage.class);
            loginPage.loginByGmail();
        } catch (Exception e) {
            Assert.fail("The login attempt has failed", e);
        }

    }

    @AfterMethod
    public void teamPortalLogout() {
        try {
            TeamPortalHeader headerPanel = PageFactory.initElements(webdriver, TeamPortalHeader.class);
            headerPanel.clickLogoutButton();
            webdriver.get(InHouseConfigInfo.getInstance().getInHouseURL());
            webdriver.manage().deleteAllCookies();
        } catch (Exception ignored) {}
        DriverBuilder.getInstance().quitDriver();
    }

	public void goToWebPage(String url) {
		webdriver.manage().window().maximize();
		webdriver.get(url);
	}

    public void setDriver() {
        webdriver = DriverBuilder.getInstance().getDriver();
    }
}
