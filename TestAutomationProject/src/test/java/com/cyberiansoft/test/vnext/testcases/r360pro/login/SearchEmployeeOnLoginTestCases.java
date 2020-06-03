package com.cyberiansoft.test.vnext.testcases.r360pro.login;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.steps.HomeScreenSteps;
import com.cyberiansoft.test.vnext.steps.commonobjects.TopScreenPanelSteps;
import com.cyberiansoft.test.vnext.steps.login.LoginScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.commonobjects.VNextNothingFoundScreenValidations;
import com.cyberiansoft.test.vnext.validations.login.LoginScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SearchEmployeeOnLoginTestCases extends BaseTestClass {

    @BeforeClass(description = "Search Employee on Login Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getSearchEmployeeOnLoginTestCasesDataPath();
        BaseUtils.waitABit(2000);
        HomeScreenSteps.logOut();
    }

    @AfterMethod
    public void afterTest() {

        TopScreenPanelSteps.clearSearchField();
        TopScreenPanelSteps.cancelSearch();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyEmployeeSearchResultsSortedByLastName(String rowID, String description, JSONObject testData) {

        TopScreenPanelSteps.searchData("uk");
        LoginScreenValidations.verifySearchResultIsCorrect("uk");
        LoginScreenValidations.verifyEmployeesSearchResultsAreSortedByLastName();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyErrorMessageOnEnteringInvalidPassword(String rowID, String description, JSONObject testData) {

        LoginScreenSteps.incorrectUserLogin(employee.getEmployeeName(), "sddsf");
        LoginScreenValidations.verifyEnteredPasswordIsIncorrectAndPressOk();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyDisplayingEnterYourPasswordPopUpOnSelectingEmployee(String rowID, String description, JSONObject testData) {

        LoginScreenSteps.openLoginDialog(employee.getEmployeeName());
        LoginScreenValidations.verifyEmployeeNameIsCorrectInPasswordDialog(employee.getEmployeeName());
        LoginScreenSteps.cancelLogin();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyNothingFoundPlaceholderIsDisplayedIfNoEmployeeFound(String rowID, String description, JSONObject testData) {

        TopScreenPanelSteps.searchData("no exist");
        VNextNothingFoundScreenValidations.verifyScreenWasOpenedWithCorrectMessage();
    }
}

