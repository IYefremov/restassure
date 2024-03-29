package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.bo.pageobjects.webpages.APADStatementWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.ReportsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.TechnicianCommissionsWebPage;
import com.cyberiansoft.test.dataclasses.bo.BOReportsData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

//@Listeners(VideoListener.class)
public class BackOfficeReportsTestCases extends BaseTestCase {

	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/bo/data/BOReportsData.json";

	@BeforeClass
	public void settingUp() {
		JSONDataProvider.dataFile = DATA_FILE;
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testReportsTechnicianCommissions(String rowID, String description, JSONObject testData) {

		BOReportsData data = JSonDataParser.getTestDataFromJson(testData, BOReportsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

        backOfficeHeader.clickReportsLink();
        ReportsWebPage reportsPage = new ReportsWebPage(webdriver);
        reportsPage.clickTechnicianCommissionsLink();
        TechnicianCommissionsWebPage technicianCommissionsPage = new TechnicianCommissionsWebPage(webdriver);
		technicianCommissionsPage.setSearchFromDate();
		technicianCommissionsPage.clickSearchBTN();
		Assert.assertTrue(technicianCommissionsPage.checkSortAbility());
		technicianCommissionsPage.clickOnLastSearchResult();
		Assert.assertTrue(technicianCommissionsPage.checkResultsTable());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyAPADStatementReportIsGeneratedForDefaultSearchValues(String rowID, String description, JSONObject testData) {

		BOReportsData data = JSonDataParser.getTestDataFromJson(testData, BOReportsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

        backOfficeHeader.clickReportsLink();
        ReportsWebPage reportsWebPage = new ReportsWebPage(webdriver);
        reportsWebPage.clickAPADStatementLink();
        APADStatementWebPage apadStatementPage = new APADStatementWebPage(webdriver);

		apadStatementPage.clickSearchButton();
		Assert.assertTrue(apadStatementPage.isReportGenerated(), "The report has not been generated");
		Assert.assertTrue(apadStatementPage.areDefaultReportValuesDisplayed(data.getAreaParameter(), data.getTeamParameter(),
				data.getCustomerParameter(), data.getEmployeeParameter(), data.getDefaultValue()),
				"The default values have been not displayed");
		Assert.assertTrue(apadStatementPage.areReportColumnsDisplayed(data.getInvoice(), data.getYear(),
				data.getStock(), data.getVIN(), data.getRO(), data.getService(), data.getAmount(), data.getBilled()),
				"The report columns have been not displayed");
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void verifyAPADStatementReportIsGeneratedForSpecifiedSearchValues(String rowID, String description, JSONObject testData) {

		BOReportsData data = JSonDataParser.getTestDataFromJson(testData, BOReportsData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

        backOfficeHeader.clickReportsLink();
        ReportsWebPage reportsWebPage = new ReportsWebPage(webdriver);
        reportsWebPage.clickAPADStatementLink();
        APADStatementWebPage apadStatementPage = new APADStatementWebPage(webdriver);

		apadStatementPage.selectArea(data.getArea());
		apadStatementPage.selectTeam(data.getTeam());
		apadStatementPage.selectEmployee(data.getEmployee());
		apadStatementPage.selectCustomer(data.getCustomer());
		apadStatementPage.clickSearchButton();
		Assert.assertTrue(apadStatementPage.isReportGenerated(), "The report has not been generated");
		Assert.assertTrue(apadStatementPage.areReportValuesDisplayed(data.getArea(), data.getTeam(),
				data.getCustomer(), data.getEmployee()),
				"The specified values have been not displayed");
		Assert.assertTrue(apadStatementPage.areReportColumnsDisplayed(data.getInvoice(), data.getYear(),
				data.getStock(), data.getVIN(), data.getRO(), data.getService(), data.getAmount(), data.getBilled()),
				"The report columns have been not displayed");
	}
}
