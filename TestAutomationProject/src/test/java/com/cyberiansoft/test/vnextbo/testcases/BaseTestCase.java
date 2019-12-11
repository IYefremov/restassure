package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.steps.VNextBOHeaderPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.login.VNextBOLoginSteps;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.io.File;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class BaseTestCase {

    protected WebDriver webdriver;
    protected static BrowserType browserType;
    protected File app;

    public void setDriver() {
        webdriver = DriverBuilder.getInstance().getDriver();
    }

	@BeforeClass
    public void login() {

        browserType = BaseUtils.getBrowserType(VNextBOConfigInfo.getInstance().getDefaultBrowser());
        DriverBuilder.getInstance().setDriver(browserType);
        webdriver = DriverBuilder.getInstance().getDriver();
        webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOCompanionappURL());
        final String userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
        final String userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();
        VNextBOLoginSteps.userLogin(userName, userPassword);
    }

    @AfterClass
    public void logout() {

        VNextBOHeaderPanelSteps.logout();
        if (DriverBuilder.getInstance().getDriver() != null)
            DriverBuilder.getInstance().quitDriver();
    }
}