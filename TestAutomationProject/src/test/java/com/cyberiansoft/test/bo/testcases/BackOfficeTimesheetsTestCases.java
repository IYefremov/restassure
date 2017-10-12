package com.cyberiansoft.test.bo.testcases;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.TimesheetsSectionWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.TimesheetsWebPage;


public class BackOfficeTimesheetsTestCases extends BaseTestCase{

	@BeforeMethod
	@Parameters({ "backoffice.url", "user.name", "user.psw" })
	public void BackOfficeLogin(String backofficeurl,
			String userName, String userPassword) throws InterruptedException {
		webdriverGotoWebPage(backofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(userName, userPassword);
		Thread.sleep(2000);
	}
	
	@AfterMethod
	public void BackOfficeLogout() throws InterruptedException {
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		
		backofficeheader.clickLogout();
	}
	
	@Test(testName = "Test Case 65561:Timesheets - Timesheets Monday Sunday switch")
	public void checkMondaySundaySwitch(){
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		TimesheetsWebPage timesheetspage = backofficeheader.clickTimesheetsLink();
		TimesheetsSectionWebPage timesheetsSectionPage = timesheetspage.clickTimesheets();
		timesheetsSectionPage.setFromDate("10/02/17");
		timesheetsSectionPage.setToDate("10/08/17");
		timesheetsSectionPage.setTeam("01_TimeRep_team");
		timesheetsSectionPage.clickFindButton();
		timesheetsSectionPage.expandFirstEmployee();
		Assert.assertTrue(timesheetsSectionPage.checkStartingDay("Mon"));
		timesheetsSectionPage.setFromDate("10/03/17");
		timesheetsSectionPage.clickFindButton();
		timesheetsSectionPage.expandFirstEmployee();
		Assert.assertTrue(timesheetsSectionPage.checkStartingDay("Tue"));
	}
}
