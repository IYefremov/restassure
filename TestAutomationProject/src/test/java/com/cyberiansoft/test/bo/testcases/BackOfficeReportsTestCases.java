package com.cyberiansoft.test.bo.testcases;

import com.automation.remarks.testng.VideoListener;
import com.cyberiansoft.test.bo.pageobjects.webpages.APADStatementWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.ReportsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.TechnicianCommissionsWebPage;
import com.cyberiansoft.test.dataclasses.bo.BOReportsData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(VideoListener.class)
public class BackOfficeReportsTestCases extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/bo/data/BOReportsData.json";

    @BeforeClass()
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testReportsTechnicianCommissions(String rowID, String description, JSONObject testData) {

        BOReportsData data = JSonDataParser.getTestDataFromJson(testData, BOReportsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        ReportsWebPage reportsPage = backOfficeHeader.clickReportsLink();
		TechnicianCommissionsWebPage technicianCommissionsPage = reportsPage.clickTechnicianCommissionsLink();
		technicianCommissionsPage.setSearchFromDate();
		technicianCommissionsPage.clickSearchBTN();
		Assert.assertTrue(technicianCommissionsPage.checkSortAbility());
		technicianCommissionsPage.clickOnLastSearchResult();
		Assert.assertTrue(technicianCommissionsPage.checkResultsTable());
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyAPADStatementReportIsGeneratedForDefaultSearchValues(String rowID, String description, JSONObject testData) {

        BOReportsData data = JSonDataParser.getTestDataFromJson(testData, BOReportsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        APADStatementWebPage apadStatementPage = backOfficeHeader
                .clickReportsLink()
                .clickAPADStatementLink()
                .clickSearchButton();
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
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        APADStatementWebPage apadStatementPage = backOfficeHeader
                .clickReportsLink()
                .clickAPADStatementLink()
                .selectArea(data.getArea())
                .selectTeam(data.getTeam())
                .selectEmployee(data.getEmployee())
                .selectCustomer(data.getCustomer())
                .clickSearchButton();
        Assert.assertTrue(apadStatementPage.isReportGenerated(), "The report has not been generated");
        Assert.assertTrue(apadStatementPage.areReportValuesDisplayed(data.getArea(), data.getTeam(),
                data.getCustomer(), data.getEmployee()),
                "The specified values have been not displayed");
        Assert.assertTrue(apadStatementPage.areReportColumnsDisplayed(data.getInvoice(), data.getYear(),
                data.getStock(), data.getVIN(), data.getRO(), data.getService(), data.getAmount(), data.getBilled()),
                "The report columns have been not displayed");
	}
}
