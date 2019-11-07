package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.steps.VNextBOHeaderPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.login.VNextBOLoginSteps;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class BaseTestCase {

	protected WebDriver webdriver;
	protected static BrowserType browserType;
	protected File app;

	public void setDriver() {
        webdriver = DriverBuilder.getInstance().getDriver();
    }

	public static WebElement wait(By locator) {
		return Helpers.wait(locator);
	}

	@BeforeSuite
    public void init() {
        browserType = BaseUtils.getBrowserType(VNextBOConfigInfo.getInstance().getDefaultBrowser());
        DriverBuilder.getInstance().setDriver(browserType);
        webdriver = DriverBuilder.getInstance().getDriver();
    }

	@AfterSuite
	public void tearDown() {
		if (DriverBuilder.getInstance().getDriver() != null)
			DriverBuilder.getInstance().quitDriver();
	}

	@BeforeMethod
    public void login() {
        webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOCompanionappURL());
        final String userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
        final String userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();

        VNextBOLoginSteps.userLogin(userName, userPassword);
    }

    @AfterMethod
    public void logout() {
        VNextBOHeaderPanelSteps.logout();
    }
}