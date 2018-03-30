package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.config.BOConfigInfo;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.TimesheetsSectionWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.TimesheetsWebPage;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;


public class BackOfficeTimesheetsTestCases extends BaseTestCase{

    @BeforeMethod
    public void BackOfficeLogin(Method method) {
        System.out.printf("\n* Starting test : %s Method : %s\n", getClass(), method.getName());
        WebDriverUtils.webdriverGotoWebPage(BOConfigInfo.getInstance().getBackOfficeURL());
        BackOfficeLoginWebPage loginPage = PageFactory.initElements(webdriver, BackOfficeLoginWebPage.class);
        loginPage.UserLogin(BOConfigInfo.getInstance().getUserName(), BOConfigInfo.getInstance().getUserPassword());
    }

    @AfterMethod
    public void BackOfficeLogout() {
        BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
        backOfficeHeader.clickLogout();
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
