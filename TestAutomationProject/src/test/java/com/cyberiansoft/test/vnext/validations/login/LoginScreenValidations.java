package com.cyberiansoft.test.vnext.validations.login;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.VNextLoginScreen;
import org.testng.Assert;

import java.util.List;
import java.util.stream.Collectors;

public class LoginScreenValidations {

    public static void verifyEmployeesSearchResultsAreSortedByLastName() {

        List<String> searchResultEmployeesLastNames = new VNextLoginScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver())
                .getEmployeesNamesList().stream().map(employee -> employee.getText()).collect(Collectors.toList())
                .stream().map(employee -> employee.substring(employee.indexOf(" "))).collect(Collectors.toList());
        Assert.assertEquals(searchResultEmployeesLastNames.stream().sorted().collect(Collectors.toList()), searchResultEmployeesLastNames,
                "Employees search result hasn't been sorted by last name");
    }

    public static void verifySearchResultIsCorrect(String searchText) {

        List<String> searchResultEmployees = new VNextLoginScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver())
                .getEmployeesNamesList().stream().map(employee -> employee.getText()).collect(Collectors.toList());
        Assert.assertTrue(searchResultEmployees.stream().allMatch(employee -> employee.contains(searchText)),
                "Employees search result hasn't been correct");
    }
}
