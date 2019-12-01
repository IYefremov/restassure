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
import org.testng.annotations.*;

import java.io.File;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class BaseTestCase {

	protected WebDriver webdriver;
	protected static BrowserType browserType;
	protected File app;
    private final String userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
    private final String userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();

	public void setDriver() {
        webdriver = DriverBuilder.getInstance().getDriver();
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

	@BeforeClass
    public void login() {
        webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOCompanionappURL());
        final String userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
        final String userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();
        VNextBOLoginSteps.userLogin(userName, userPassword);
    }

    @AfterClass
    public void logout() {
        VNextBOHeaderPanelSteps.logout();
    }

    //@BeforeMethod
    //public void setUp() {
    //    webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOCompanionappURL());
    //    VNextBOLoginSteps.userLogin(userName, userPassword);
    //}
}