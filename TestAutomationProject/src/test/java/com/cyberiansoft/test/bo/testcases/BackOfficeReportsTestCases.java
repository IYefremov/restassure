package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.ReportsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.TechnicianCommissionsWebPage;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BackOfficeReportsTestCases extends BaseTestCase {

	@Test(testName = "Test Case 16189:Reports - Technician Commissions")
	public void testReportsTechnicianCommissions() throws InterruptedException{
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
