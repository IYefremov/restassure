package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.screens.*;
import com.cyberiansoft.test.vnextbo.steps.VNextBOHeaderPanelSteps;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOClientsTestCases extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/VNextBOClientsData.json";

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    @BeforeMethod
    public void BackOfficeLogin() {
        browserType = BaseUtils.getBrowserType(VNextBOConfigInfo.getInstance().getDefaultBrowser());
        try {
            DriverBuilder.getInstance().setDriver(browserType);
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
        webdriver = DriverBuilder.getInstance().getDriver();

        webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOCompanionappURL());
        final String userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
        final String userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();

        VNextBOLoginScreenWebPage loginPage = PageFactory.initElements(webdriver, VNextBOLoginScreenWebPage.class);
        loginPage.userLogin(userName, userPassword);
    }

    @AfterMethod
    public void BackOfficeLogout() {
        VNextBOHeaderPanelSteps.logout();

        if (DriverBuilder.getInstance().getDriver() != null) {
            DriverBuilder.getInstance().quitDriver();
        }
    }

}