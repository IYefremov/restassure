package com.cyberiansoft.test.bo.testcases;

import com.automation.remarks.testng.VideoListener;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.TimesheetsSectionWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.TimesheetsWebPage;
import com.cyberiansoft.test.dataclasses.bo.BOTimesheetsData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


//@Listeners(VideoListener.class)
public class BackOfficeTimesheetsTestCases extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/bo/data/BOTimesheetsData.json";

    @BeforeClass()
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void checkMondaySundaySwitch(String rowID, String description, JSONObject testData) {

        BOTimesheetsData data = JSonDataParser.getTestDataFromJson(testData, BOTimesheetsData.class);
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);

        TimesheetsWebPage timesheetspage = backOfficeHeader.clickTimesheetsLink();
		TimesheetsSectionWebPage timesheetsSectionPage = timesheetspage.clickTimesheets();
		timesheetsSectionPage.setFromDate(data.getFromDate());
		timesheetsSectionPage.setToDate(data.getToDate());
		timesheetsSectionPage.setTeam(data.getTeam());
		timesheetsSectionPage.clickFindButton();
		timesheetsSectionPage.expandFirstEmployee();
		Assert.assertTrue(timesheetsSectionPage.checkStartingDay(data.getStartingDayMon()));
		timesheetsSectionPage.setFromDate(data.getFromDate2());
		timesheetsSectionPage.clickFindButton();
		timesheetsSectionPage.expandFirstEmployee();
		Assert.assertTrue(timesheetsSectionPage.checkStartingDay(data.getStartingDayTue()));
	}
}
