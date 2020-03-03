package com.cyberiansoft.test.vnextbo.testcases.homepage;

import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOHomePageData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOHeaderPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.login.VNextBOLoginSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.general.VNextBOLeftMenuValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOHomePageAccessRightsTestCases extends BaseTestCase {

	@Override
	@BeforeClass
	public void login() {

        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getHomePageAccessRightsTD();
		webdriverGotoWebPage(BaseTestCase.getBackOfficeURL());
	}

	@AfterMethod
	public void logOut() {

		VNextBOHeaderPanelSteps.logout();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifySettingsTabIsShown(String rowID, String description, JSONObject testData) {
		VNextBOHomePageData data = JSonDataParser.getTestDataFromJson(testData, VNextBOHomePageData.class);
		VNextBOLoginSteps.userLogin(data.getLogin(), data.getPassword());
		Assert.assertTrue(VNextBOLeftMenuValidations.isSettingsMenuTabDisplayed(), "Settings tab is not displayed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifySettingsTabIsNotShown(String rowID, String description, JSONObject testData) {
		VNextBOHomePageData data = JSonDataParser.getTestDataFromJson(testData, VNextBOHomePageData.class);
		VNextBOLoginSteps.userLogin(data.getLogin(), data.getPassword());
		Assert.assertFalse(VNextBOLeftMenuValidations.isSettingsMenuTabDisplayed(), "Settings tab is displayed");
	}
}