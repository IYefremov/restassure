package com.cyberiansoft.test.vnext.testcases.r360pro;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.screens.VNextLoginScreen;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEmployeeSearch;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class VNextSearchEmployeeTestCases extends BaseTestCaseTeamEmployeeSearch {
	
	@Test(testName= "Test Case 69019:R360 client: verify incremental search for Emloyees list", 
			description = "Verify incremental search for Emloyees list")
	public void testVerifyIncrementalSearchForEmloyeesList() {
		
		final String firstSearchCriteria = "ar";
		final ArrayList<String> firstSearchCriteriaEmployees = new ArrayList<String>(
				Arrays.asList("Leo Barton", "Loise Carr Blansh", "Arron Morris", "Perla Wardle")); 
		final String secondSearchCriteria = "arr";
		final ArrayList<String> secondSearchCriteriaEmployees = new ArrayList<String>(
				Arrays.asList("Loise Carr Blansh", "Arron Morris"));
		
		final String thirdSearchCriteria = "arro";
		final ArrayList<String> thirdSearchCriteriaEmployees = new ArrayList<String>(
				Arrays.asList("Arron Morris"));
		
		VNextLoginScreen loginscreen = new VNextLoginScreen(DriverBuilder.getInstance().getAppiumDriver());
		loginscreen.searchEmployee(firstSearchCriteria);
		Assert.assertEquals(firstSearchCriteriaEmployees.size(), loginscreen.getNumberOfEmployeesInTheList());
		for (String employeeName : firstSearchCriteriaEmployees)
			Assert.assertTrue(loginscreen.isEmployeepresentInTheList(employeeName));
		
		loginscreen.searchEmployee(secondSearchCriteria);
		Assert.assertEquals(secondSearchCriteriaEmployees.size(), loginscreen.getNumberOfEmployeesInTheList());
		for (String employeeName : secondSearchCriteriaEmployees)
			Assert.assertTrue(loginscreen.isEmployeepresentInTheList(employeeName));
		
		loginscreen.searchEmployee(thirdSearchCriteria);
		Assert.assertEquals(thirdSearchCriteriaEmployees.size(), loginscreen.getNumberOfEmployeesInTheList());
		for (String employeeName : thirdSearchCriteriaEmployees)
			Assert.assertTrue(loginscreen.isEmployeepresentInTheList(employeeName));
	}
	
	@Test(testName= "Test Case 69014:R360 client: search employee with special symbols", 
			description = "Search employee with special symbols")
	public void testSearchEmployeeWithSpecialSymbols() {
		
		final String firstSearchCriteria = "$%-Symbols";
		final ArrayList<String> firstSearchCriteriaEmployees = new ArrayList<String>(
				Arrays.asList("Special +&* $%-Symbols")); 
	
		
		VNextLoginScreen loginscreen = new VNextLoginScreen(DriverBuilder.getInstance().getAppiumDriver());
		loginscreen.searchEmployee(firstSearchCriteria);
		Assert.assertEquals(firstSearchCriteriaEmployees.size(), loginscreen.getNumberOfEmployeesInTheList());
		for (String employeeName : firstSearchCriteriaEmployees)
			Assert.assertTrue(loginscreen.isEmployeepresentInTheList(employeeName));		
	}
	
	@Test(testName= "Test Case 69015:R360 client: verify employee search doesn't depends on text registr", 
			description = "Verify employee search doesn't depends on text registr")
	public void testVerifyEmployeeSearchDoesntDependsOnTextRegistr() {
		
		final String[] searchCriterias = { "ross", "ROSS", "rOsS", "rosS" };
		final ArrayList<String> searchCriteriaEmployees = new ArrayList<String>(
				Arrays.asList("Merrill Ross")); 

		
		VNextLoginScreen loginscreen = new VNextLoginScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (String searchCriteria : searchCriterias) {
			loginscreen.searchEmployee(searchCriteria);
			Assert.assertEquals(searchCriteriaEmployees.size(), loginscreen.getNumberOfEmployeesInTheList());
			for (String employeeName : searchCriteriaEmployees)
				Assert.assertTrue(loginscreen.isEmployeepresentInTheList(employeeName));
		}
	}
	
	@Test(testName= "Test Case 69016:R360 client: 'Nothing found' placeholder should be displayed if no employee found", 
			description = "'Nothing found' placeholder should be displayed if no employee found")
	public void testNothingFoundPlaceholderShouldBeDisplayedIfNoEmployeeFound() {
		
		final String searchCriteria = "no exist";
		
		VNextLoginScreen loginscreen = new VNextLoginScreen(DriverBuilder.getInstance().getAppiumDriver());
		loginscreen.searchEmployee(searchCriteria);
		Assert.assertTrue(loginscreen.isNothingFoundTextDisplayed());
	}
	
	@Test(testName= "Test Case 69017:R360 client: verify displaying 'Enter your Password' pop-up on selecting employee, "
			+ "Test Case 69018:R360 client: verify displaying error message on entering invalid password", 
			description = "Verify displaying 'Enter your Password' pop-up on selecting employee, "
					+ "Verify displaying error message on entering invalid password")
	public void testVerifyDisplayingEnterYourPasswordPopupOnSelectingEmployee() {
		
		final String searchCriteria = "ross";
		final String searchCriteriaEmployee = "Merrill Ross"; 
		final String employeePass = "1234"; 
	
		VNextLoginScreen loginscreen = new VNextLoginScreen(DriverBuilder.getInstance().getAppiumDriver());
		loginscreen.searchEmployee(searchCriteria);
		loginscreen.incorrectUserLogin(searchCriteriaEmployee, employeePass);		
	}
	
	@Test(testName= "Test Case 69021:R360 client: verify Cancel button closes 'Search', "
			+ "Test Case 69022:R360 client: verify Hardware 'Back' button closes 'Search' pop-up", 
			description = "Cancel button closes 'Search', "
					+ "Verify Hardware 'Back' button closes 'Search' pop-up")
	public void testVerifyCancelButtonClosesSearch() {
		
		final String searchCriteria = "ross";
		final String searchCriteriaEmployee = "Merrill Ross"; 

		VNextLoginScreen loginscreen = new VNextLoginScreen(DriverBuilder.getInstance().getAppiumDriver());
		loginscreen.searchEmployee(searchCriteria);
		loginscreen.selectEmployee(searchCriteriaEmployee);	
		Assert.assertTrue(loginscreen.isUserLoginPasswordDialogVisible());
		loginscreen.tapLoginDialogCancelButton();
		Assert.assertFalse(loginscreen.isUserLoginPasswordDialogVisible());
		
		loginscreen.selectEmployee(searchCriteriaEmployee);	
		Assert.assertTrue(loginscreen.isUserLoginPasswordDialogVisible());
		AppiumUtils.clickHardwareBackButton();
		Assert.assertFalse(loginscreen.isUserLoginPasswordDialogVisible());
	}
	
	@Test(testName= "Test Case 69020:R360 client: Show emplyee search results sorted by LastName", 
			description = "Show emplyee search results sorted by LastName")
	public void testShowEmplyeeSearchResultsSortedByLastName() {
		
		final String firstSearchCriteria = "ar";
		final String secondSearchCriteria = "arr";

		VNextLoginScreen loginscreen = new VNextLoginScreen(DriverBuilder.getInstance().getAppiumDriver());
		loginscreen.searchEmployee(firstSearchCriteria);
		Assert.assertTrue(loginscreen.isEmployeeListSorted());
	
		loginscreen.searchEmployee(secondSearchCriteria);
		Assert.assertTrue(loginscreen.isEmployeeListSorted());
		
	}

}
