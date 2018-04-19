package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BackOfficeSuperUserTestCases extends BaseTestCase {

	@Test(description = "Test Case 15135:All Users - Search")
	public void testAllUsersSearch() throws Exception {

		final String username = "Oleksandr Kramar";
		final String appname = "380505460134";
		
		BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		SuperUserWebPage superUserPage = backofficeHeader.clickSuperUserLink();

		AllUsersWebPage allUsersPage = superUserPage.clickAllUsersLink();

		allUsersPage.verifyAllUsersTableColumnsAreVisible();
		
		Assert.assertEquals("1", allUsersPage.getCurrentlySelectedPageNumber());
		Assert.assertEquals("1", allUsersPage.getGoToPageFieldValue());
		
		allUsersPage.setPageSize("1");
		Assert.assertEquals(1, allUsersPage.getAllUsersTableRowCount());
		
		String lastpagenumber = allUsersPage.getLastPageNumber();
		allUsersPage.clickGoToLastPage();
		Assert.assertEquals(lastpagenumber, allUsersPage.getGoToPageFieldValue());
		
		allUsersPage.clickGoToFirstPage();
		Assert.assertEquals("1", allUsersPage.getGoToPageFieldValue());
		
		allUsersPage.clickGoToNextPage();
		Assert.assertEquals("2", allUsersPage.getGoToPageFieldValue());
		
		allUsersPage.clickGoToPreviousPage();
		Assert.assertEquals("1", allUsersPage.getGoToPageFieldValue());

		allUsersPage.setPageSize("999");
		Assert.assertEquals(allUsersPage.MAX_TABLE_ROW_COUNT_VALUE, allUsersPage.getAllUsersTableRowCount());
		
		allUsersPage.makeSearchPanelVisible();
		allUsersPage.setSearchUserParameter(username.substring(0, 4));
		allUsersPage.selectSearchApplication(appname);
		allUsersPage.checkSuperUserCheckBox();
		allUsersPage.clickFindButton();
		
		Assert.assertEquals(1, allUsersPage.getAllUsersTableRowCount());
		allUsersPage.userExists(username);
	}
	
	@Test(description = "Test Case 15156:All Employees - Search")
	public void testAllEmployeesSearch() throws Exception {

		final String employee = "Aaron Naber";
		final String appname = "Dent Wizard International";
		
		BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		SuperUserWebPage superUserPage = backofficeHeader.clickSuperUserLink();

		AllEmployeesWebPage allEmployeesPage = superUserPage.clickAllEmployeesLink();

		allEmployeesPage.verifyAllEmployeesTableColumnsAreVisible();
		
		/*Assert.assertEquals("1", allEmployeesPage.getCurrentlySelectedPageNumber());
		Assert.assertEquals("1", allEmployeesPage.getGoToPageFieldValue());
		
		allEmployeesPage.setPageSize("1");
		Assert.assertEquals(1, allEmployeesPage.getAllEmployeesTableRowCount());
		
		String lastpagenumber = allEmployeesPage.getLastPageNumber();
		allEmployeesPage.clickGoToLastPage();
		Assert.assertEquals(lastpagenumber, allEmployeesPage.getGoToPageFieldValue());
		
		allEmployeesPage.clickGoToFirstPage();
		Assert.assertEquals("1", allEmployeesPage.getGoToPageFieldValue());
		
		allEmployeesPage.clickGoToNextPage();
		Assert.assertEquals("2", allEmployeesPage.getGoToPageFieldValue());
		
		allEmployeesPage.clickGoToPreviousPage();
		Assert.assertEquals("1", allEmployeesPage.getGoToPageFieldValue());

		allEmployeesPage.setPageSize("999");
		Assert.assertEquals(allEmployeesPage.MAX_TABLE_ROW_COUNT_VALUE, Integer.valueOf(allEmployeesPage.getAllEmployeesTableRowCount()));
		*/
		allEmployeesPage.makeSearchPanelVisible();
		allEmployeesPage.setSearchEmployeeParameter(employee);
		allEmployeesPage.selectSearchApplication(appname);
		allEmployeesPage.clickFindButton();
	
		allEmployeesPage.verifySearchResultsByApplication(appname);
		allEmployeesPage.verifyProfilesLinkWorks();
	}
	
	@Test(description = "Test Case 17283:Super User - Applications")
	public void testSuperUserApplications() throws Exception {

		final String status = "Terminated";
		final String appname = "20141006102411";
		final String username = "kramar";
		
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		SuperUserWebPage superUserPage = backofficeheader.clickSuperUserLink();

		ApplicationsWebPage applicationsPage = superUserPage.clickApplicationsLink();

		applicationsPage.verifyApplicationsTableColumnsAreVisible();
		
		Assert.assertEquals("1", applicationsPage.getCurrentlySelectedPageNumber());
		Assert.assertEquals("1", applicationsPage.getGoToPageFieldValue());
		
		applicationsPage.setPageSize("1");
		Assert.assertEquals(1, applicationsPage.getApplicationsTableRowCount());
		
		String lastPageNumber = applicationsPage.getLastPageNumber();
		applicationsPage.clickGoToLastPage();
		Assert.assertEquals(lastPageNumber, applicationsPage.getGoToPageFieldValue());
		
		applicationsPage.clickGoToFirstPage();
		Assert.assertEquals("1", applicationsPage.getGoToPageFieldValue());
		
		applicationsPage.clickGoToNextPage();
		Assert.assertEquals("2", applicationsPage.getGoToPageFieldValue());
		
		applicationsPage.clickGoToPreviousPage();
		Assert.assertEquals("1", applicationsPage.getGoToPageFieldValue());

		applicationsPage.setPageSize("999");
		Assert.assertEquals(applicationsPage.MAX_TABLE_ROW_COUNT_VALUE,
                applicationsPage.getApplicationsTableRowCount());
		
		applicationsPage.makeSearchPanelVisible();
				
		applicationsPage.selectSearchApplication(appname);
		applicationsPage.selectSearchStatus(status);
		applicationsPage.setSearchUsername(username);
		
		applicationsPage.clickFindButton();
	
		Assert.assertEquals(1, applicationsPage.getApplicationsTableRowCount());
		applicationsPage.verifySearchResultsByApplication(appname);
	}
}
