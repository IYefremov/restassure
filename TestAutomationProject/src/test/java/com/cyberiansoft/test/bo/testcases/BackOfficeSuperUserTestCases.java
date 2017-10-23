package com.cyberiansoft.test.bo.testcases;

import org.junit.Assert;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cyberiansoft.test.bo.pageobjects.webpages.AllEmployeesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.AllUsersWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ApplicationsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.SuperUserWebPage;

public class BackOfficeSuperUserTestCases extends BaseTestCase {
	
	@BeforeMethod
	@Parameters({ "backoffice.url", "user.name", "user.psw" })
	public void BackOfficeLogin(String backofficeurl,
			String userName, String userPassword) throws InterruptedException {
		webdriverGotoWebPage(backofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		try{
		loginpage.UserLogin(userName, userPassword);
		Thread.sleep(2000);
		}catch(Exception e){}
	}
	
//	@AfterMethod
//	public void BackOfficeLogout() throws InterruptedException {
//		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
//				BackOfficeHeaderPanel.class);
//		backofficeheader.clickLogout();
//	}	

	@Test(description = "Test Case 15135:All Users - Search")
	public void testAllUsersSearch() throws Exception {

		final String username = "Oleksandr Kramar";
		final String appname = "380505460134";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		SuperUserWebPage superuserpage = backofficeheader.clickSuperUserLink();

		AllUsersWebPage alluserspage = superuserpage.clickAllUsersLink();

		alluserspage.verifyAllUsersTableColumnsAreVisible();
		
		Assert.assertEquals("1", alluserspage.getCurrentlySelectedPageNumber());
		Assert.assertEquals("1", alluserspage.getGoToPageFieldValue());
		
		alluserspage.setPageSize("1");
		Assert.assertEquals(1, alluserspage.getAllUsersTableRowCount());
		
		String lastpagenumber = alluserspage.getLastPageNumber();
		alluserspage.clickGoToLastPage();
		Assert.assertEquals(lastpagenumber, alluserspage.getGoToPageFieldValue());
		
		alluserspage.clickGoToFirstPage();
		Assert.assertEquals("1", alluserspage.getGoToPageFieldValue());
		
		alluserspage.clickGoToNextPage();
		Assert.assertEquals("2", alluserspage.getGoToPageFieldValue());
		
		alluserspage.clickGoToPreviousPage();
		Assert.assertEquals("1", alluserspage.getGoToPageFieldValue());

		alluserspage.setPageSize("999");
		Assert.assertEquals(alluserspage.MAX_TABLE_ROW_COUNT_VALUE, Integer.valueOf(alluserspage.getAllUsersTableRowCount()));
		
		alluserspage.makeSearchPanelVisible();
		alluserspage.setSearchUserParameter(username.substring(0, 4));
		alluserspage.selectSearchApplication(appname);
		alluserspage.checkSuperUserCheckBox();
		alluserspage.clickFindButton();
		
		Assert.assertEquals(Integer.valueOf(1), Integer.valueOf(alluserspage.getAllUsersTableRowCount()));
		alluserspage.isUserExists(username);
	}
	
	@Test(description = "Test Case 15156:All Employees - Search")
	public void testAllEmployeesSearch() throws Exception {

		final String employee = "Aaron Naber";
		final String appname = "Dent Wizard International";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		SuperUserWebPage superuserpage = backofficeheader.clickSuperUserLink();

		AllEmployeesWebPage allemployeespage = superuserpage.clickAllEmployeesLink();

		allemployeespage.verifyAllEmployeesTableColumnsAreVisible();
		
		/*Assert.assertEquals("1", allemployeespage.getCurrentlySelectedPageNumber());
		Assert.assertEquals("1", allemployeespage.getGoToPageFieldValue());
		
		allemployeespage.setPageSize("1");
		Assert.assertEquals(1, allemployeespage.getAllEmployeesTableRowCount());
		
		String lastpagenumber = allemployeespage.getLastPageNumber();
		allemployeespage.clickGoToLastPage();
		Assert.assertEquals(lastpagenumber, allemployeespage.getGoToPageFieldValue());
		
		allemployeespage.clickGoToFirstPage();
		Assert.assertEquals("1", allemployeespage.getGoToPageFieldValue());
		
		allemployeespage.clickGoToNextPage();
		Assert.assertEquals("2", allemployeespage.getGoToPageFieldValue());
		
		allemployeespage.clickGoToPreviousPage();
		Assert.assertEquals("1", allemployeespage.getGoToPageFieldValue());

		allemployeespage.setPageSize("999");
		Assert.assertEquals(allemployeespage.MAX_TABLE_ROW_COUNT_VALUE, Integer.valueOf(allemployeespage.getAllEmployeesTableRowCount()));
		*/
		allemployeespage.makeSearchPanelVisible();
		allemployeespage.setSearchEmployeeParameter(employee);
		allemployeespage.selectSearchApplication(appname);
		allemployeespage.clickFindButton();
	
		allemployeespage.verifySearchResultsByApplication(appname);
		allemployeespage.verifyProfilesLinkWorks();
	}
	
	@Test(description = "Test Case 17283:Super User - Applications")
	public void testSuperUserApplications() throws Exception {

		final String status = "Terminated";
		final String appname = "20141006102411";
		final String username = "kramar";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		SuperUserWebPage superuserpage = backofficeheader.clickSuperUserLink();

		ApplicationsWebPage applicationspage = superuserpage.clickApplicationsLink();

		applicationspage.verifyApplicationsTableColumnsAreVisible();
		
		Assert.assertEquals("1", applicationspage.getCurrentlySelectedPageNumber());
		Assert.assertEquals("1", applicationspage.getGoToPageFieldValue());
		
		applicationspage.setPageSize("1");
		Assert.assertEquals(1, applicationspage.getApplicationsTableRowCount());
		
		String lastpagenumber = applicationspage.getLastPageNumber();
		applicationspage.clickGoToLastPage();
		Assert.assertEquals(lastpagenumber, applicationspage.getGoToPageFieldValue());
		
		applicationspage.clickGoToFirstPage();
		Assert.assertEquals("1", applicationspage.getGoToPageFieldValue());
		
		applicationspage.clickGoToNextPage();
		Assert.assertEquals("2", applicationspage.getGoToPageFieldValue());
		
		applicationspage.clickGoToPreviousPage();
		Assert.assertEquals("1", applicationspage.getGoToPageFieldValue());

		applicationspage.setPageSize("999");
		Assert.assertEquals(applicationspage.MAX_TABLE_ROW_COUNT_VALUE, Integer.valueOf(applicationspage.getApplicationsTableRowCount()));
		
		applicationspage.makeSearchPanelVisible();
				
		applicationspage.selectSearchApplication(appname);
		applicationspage.selectSearchStatus(status);
		applicationspage.setSearchUsername(username);
		
		applicationspage.clickFindButton();
	
		Assert.assertEquals(Integer.valueOf(1), Integer.valueOf(applicationspage.getApplicationsTableRowCount()));
		applicationspage.verifySearchResultsByApplication(appname);
	}

}
