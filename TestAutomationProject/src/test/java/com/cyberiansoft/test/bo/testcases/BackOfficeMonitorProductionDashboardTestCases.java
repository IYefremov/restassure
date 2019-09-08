package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.MonitorWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ProductionDashboardWebPage;
import com.cyberiansoft.test.dataclasses.bo.BOMonitorProductionDashboardData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BackOfficeMonitorProductionDashboardTestCases extends BaseTestCase {

	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/bo/data/BOMonitorProductionDashboardData.json";

	@BeforeClass
	public void settingUp() {
		JSONDataProvider.dataFile = DATA_FILE;
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyProductionDashboardReportIsDisplayed(String rowID, String description, JSONObject testData) {

		BOMonitorProductionDashboardData data = JSonDataParser.getTestDataFromJson(testData, BOMonitorProductionDashboardData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);
		MonitorWebPage monitorWebPage = new MonitorWebPage(webdriver);
		ProductionDashboardWebPage productionDashboardWebPage = new ProductionDashboardWebPage(webdriver);

		backOfficeHeader.clickMonitorLink();
		monitorWebPage.clickProductionDashboardLink();
		productionDashboardWebPage.selectLocation(data.getLocation());
		productionDashboardWebPage.selectCustomer(data.getCustomer());
		productionDashboardWebPage.clickApplyButton();
		productionDashboardWebPage.verifyDashboardTableColumnsAreVisible();
	}
}
