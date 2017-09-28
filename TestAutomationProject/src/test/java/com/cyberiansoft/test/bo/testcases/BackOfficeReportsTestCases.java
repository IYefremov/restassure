package com.cyberiansoft.test.bo.testcases;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ReportsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.TechnicianCommissionsWebPage;

import org.junit.Assert;

public class BackOfficeReportsTestCases extends BaseTestCase {

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
		Thread.sleep(3000);
		
	}
	
	@Test(testName = "Test Case 16189:Reports - Technician Commissions")
	public void testReportsTechnicianCommissions() throws InterruptedException{
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		ReportsWebPage reportsPage = backofficeheader.clickReportsLink();
		TechnicianCommissionsWebPage technishialCommissionsPage = reportsPage.clickTechnicianCommissionsLink();
		technishialCommissionsPage.clickSearchBTN();
		Assert.assertTrue(technishialCommissionsPage.checkSortAbility());
		technishialCommissionsPage.clickOnLastSearchResult();
		Assert.assertTrue(technishialCommissionsPage.checkResultsTable());
	}	
}
