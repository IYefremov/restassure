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
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.File;


public class BaseTestCase {

	protected WebDriver webdriver;
	public BrowserType browsertype;
	protected File app;

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
	}

    public WebDriver getWebDriver() {
		return webdriver;
	}

	@AfterClass
	public void tearDown() {
        DriverBuilder.getInstance().quitDriver();
	}


    @BeforeMethod
    public void teamPortalLogin() throws InterruptedException {
        goToWebPage(InHouseConfigInfo.getInstance().getInHouseURL());
        LoginPage loginPage = PageFactory.initElements(webdriver, LoginPage.class);
        loginPage.loginByGmail();
        Thread.sleep(2000);
    }

    @AfterMethod
    public void teamPortalLogout() {
        TeamPortalHeader headerPanel = PageFactory.initElements(webdriver,
                TeamPortalHeader.class);
        try {
            headerPanel.clickLogoutButton();
            Thread.sleep(1000);
            webdriver.get(InHouseConfigInfo.getInstance().getInHouseURL());
            webdriver.manage().deleteAllCookies();
        } catch (Exception ignored) {}
    }

	public void goToWebPage(String url) {
		webdriver.manage().window().maximize();
		webdriver.get(url);
	}

    public void setDriver() {
        webdriver = DriverBuilder.getInstance().getDriver();
    }
}
