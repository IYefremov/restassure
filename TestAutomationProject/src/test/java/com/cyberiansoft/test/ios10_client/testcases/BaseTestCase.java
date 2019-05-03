package com.cyberiansoft.test.ios10_client.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.driverutils.AppiumDriverServiceBuilder;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.types.envtypes.IOSReconproEnvironmentType;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios10_client.utils.TestUser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTestCase {

	protected static String deviceofficeurl;
	protected WebDriver webdriver;
	protected static BrowserType browsertype;
	public static MobilePlatform mobilePlatform;
	public static boolean inspSinglePageMode = false;
	protected TestUser testuser;
	protected static IOSReconproEnvironmentType envType;

	public void initTestUser(String username,  String userpsw) {
		this.testuser = new TestUser(username, userpsw);
	}
	
	public TestUser getTestUser() {
		return testuser;
	}

	@BeforeSuite
	public void setUp() {
		AppiumDriverServiceBuilder.getInstance().buildAppiumService();
		browsertype = BaseUtils.getBrowserType(ReconProIOSStageInfo.getInstance().getDefaultBrowser());
	}

	public static WebElement wait(By locator) {
		return Helpers.wait(locator);
	}

	@AfterSuite
	public void tearDown() {
		if (DriverBuilder.getInstance().getDriver() != null)
				DriverBuilder.getInstance().getDriver().quit();
		if (DriverBuilder.getInstance().getAppiumDriver() != null)
			DriverBuilder.getInstance().getAppiumDriver().quit();
		if (AppiumDriverServiceBuilder.getInstance().getAppiumService() != null) {
			AppiumDriverServiceBuilder.getInstance().getAppiumService().stop();
        }
	}

}
