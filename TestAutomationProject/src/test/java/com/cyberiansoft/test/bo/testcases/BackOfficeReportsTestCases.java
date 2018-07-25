package com.cyberiansoft.test.bo.testcases;

import com.automation.remarks.testng.VideoListener;
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
        BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        ReportsWebPage reportsPage = backofficeHeader.clickReportsLink();
		TechnicianCommissionsWebPage technicianCommissionsPage = reportsPage.clickTechnicianCommissionsLink();
		technicianCommissionsPage.setSearchFromDate();
		technicianCommissionsPage.clickSearchBTN();
		Assert.assertTrue(technicianCommissionsPage.checkSortAbility());
		technicianCommissionsPage.clickOnLastSearchResult();
		Assert.assertTrue(technicianCommissionsPage.checkResultsTable());
	}
}
