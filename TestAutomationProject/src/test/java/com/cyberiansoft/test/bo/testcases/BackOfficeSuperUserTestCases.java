package com.cyberiansoft.test.bo.testcases;

import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.dataclasses.bo.BOSuperUserData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

//@Listeners(VideoListener.class)
public class BackOfficeSuperUserTestCases extends BaseTestCase {

	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/bo/data/BOSuperUserData.json";

	@BeforeClass
	public void settingUp() {
		JSONDataProvider.dataFile = DATA_FILE;
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testAllUsersSearch(String rowID, String description, JSONObject testData) {

		BOSuperUserData data = JSonDataParser.getTestDataFromJson(testData, BOSuperUserData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		SuperUserWebPage superUserPage = new SuperUserWebPage(webdriver);
		backOfficeHeader.clickSuperUserLink();
		AllUsersWebPage allUsersPage = new AllUsersWebPage(webdriver);
		superUserPage.clickAllUsersLink();
		allUsersPage.verifyAllUsersTableColumnsAreVisible();

		Assert.assertEquals("1", allUsersPage.getCurrentlySelectedPageNumber());
		Assert.assertEquals("1", allUsersPage.getGoToPageFieldValue());

		allUsersPage.setPageSize("1");
		Assert.assertEquals(1, allUsersPage.getAllUsersTableRowCount());

		String lastpagenumber = allUsersPage.getLastPageNumber();
		allUsersPage.clickGoToLastPage();
		Assert.assertEquals(lastpagenumber, allUsersPage.getGoToPageFieldValue().replaceAll(",", ""));

		allUsersPage.clickGoToFirstPage();
		Assert.assertEquals("1", allUsersPage.getGoToPageFieldValue());

		allUsersPage.clickGoToNextPage();
		Assert.assertEquals("2", allUsersPage.getGoToPageFieldValue());

		allUsersPage.clickGoToPreviousPage();
		Assert.assertEquals("1", allUsersPage.getGoToPageFieldValue());

		allUsersPage.setPageSize("999");
		Assert.assertEquals(allUsersPage.MAX_TABLE_ROW_COUNT_VALUE, allUsersPage.getAllUsersTableRowCount());

		allUsersPage.makeSearchPanelVisible();
		allUsersPage.setSearchUserParameter(data.getUserName().substring(0, 4));
		allUsersPage.selectSearchApplication(data.getAppName());
		allUsersPage.checkSuperUserCheckBox();
		allUsersPage.clickFindButton();

		Assert.assertEquals(1, allUsersPage.getAllUsersTableRowCount());
		allUsersPage.userExists(data.getUserName());
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testAllEmployeesSearch(String rowID, String description, JSONObject testData) {

		BOSuperUserData data = JSonDataParser.getTestDataFromJson(testData, BOSuperUserData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		SuperUserWebPage superUserPage = new SuperUserWebPage(webdriver);
		backOfficeHeader.clickSuperUserLink();
		AllEmployeesWebPage allEmployeesPage = new AllEmployeesWebPage(webdriver);
		superUserPage.clickAllEmployeesLink();
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
		allEmployeesPage.setSearchEmployeeParameter(data.getEmployee());
		allEmployeesPage.selectSearchApplication(data.getAppName());
		allEmployeesPage.clickFindButton();

		allEmployeesPage.verifySearchResultsByApplication(data.getAppName());
		allEmployeesPage.verifyProfilesLinkWorks();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testSuperUserApplications(String rowID, String description, JSONObject testData) {

		BOSuperUserData data = JSonDataParser.getTestDataFromJson(testData, BOSuperUserData.class);
		BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

		SuperUserWebPage superUserPage = new SuperUserWebPage(webdriver);
		backOfficeHeader.clickSuperUserLink();
		ApplicationsWebPage applicationsPage = new ApplicationsWebPage(webdriver);
		superUserPage.clickApplicationsLink();
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

		applicationsPage.selectSearchApplication(data.getAppName());
		applicationsPage.selectSearchStatus(data.getStatus());
		applicationsPage.setSearchUsername(data.getUserName());

		applicationsPage.clickFindButton();

		Assert.assertEquals(1, applicationsPage.getApplicationsTableRowCount());
		applicationsPage.verifySearchResultsByApplication(data.getAppName());
	}
}
