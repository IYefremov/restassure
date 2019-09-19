package com.cyberiansoft.test.vnextbo.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOHomePageData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.interactions.leftMenuPanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.*;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOHomePageAccessRightsTestCases extends BaseTestCase {
	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/VNextBOUserLoginData.json";

	@BeforeClass
	public void settingUp() {
		JSONDataProvider.dataFile = DATA_FILE;
	}

	private VNextBOLoginScreenWebPage loginPage;
	private VNextBOLeftMenuInteractions leftMenuInteractions;

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
		loginPage = new VNextBOLoginScreenWebPage(webdriver);
	}

	@AfterMethod
	public void BackOfficeLogout() {
		try {
			VNextBOHeaderPanel vNextBOHeaderPanel = new VNextBOHeaderPanel(webdriver);
			if (vNextBOHeaderPanel.logOutLinkExists()) {
				vNextBOHeaderPanel.userLogout();
			}
		} catch (RuntimeException ignored) {
		}
		if (DriverBuilder.getInstance().getDriver() != null) {
			DriverBuilder.getInstance().quitDriver();
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifySettingsTabIsShown(String rowID, String description, JSONObject testData) {
		VNextBOHomePageData data = JSonDataParser.getTestDataFromJson(testData, VNextBOHomePageData.class);
		loginPage.userLogin(data.getLogin(), data.getPassword());
		leftMenuInteractions = new VNextBOLeftMenuInteractions();
		Assert.assertTrue(leftMenuInteractions.isSettingsMenuTabDisplayed(), "Settings tab is not displayed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifySettingsTabIsNotShown(String rowID, String description, JSONObject testData) {
		VNextBOHomePageData data = JSonDataParser.getTestDataFromJson(testData, VNextBOHomePageData.class);
		loginPage.userLogin(data.getLogin(), data.getPassword());
		leftMenuInteractions = new VNextBOLeftMenuInteractions();
		Assert.assertTrue(leftMenuInteractions.isSettingsMenuTabAbsent(), "Settings tab is displayed");
	}
}