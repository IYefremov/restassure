package com.cyberiansoft.test.vnextbo.validations.servicerequests.dialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.servicerequests.dialogs.VNextBOSRAdvancedSearchDialog;
import com.cyberiansoft.test.vnextbo.steps.servicerequests.dialogs.VNextBOSRAdvancedSearchDialogSteps;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.Map;

public class VNextBOSRAdvancedSearchDialogValidations {

    public static void verifyFieldsWithAutoCompleteContainPlaceholders() {
        final String placeholder = "Type to search";
        Map<String, WebElement> elements = new VNextBOSRAdvancedSearchDialog().getElementsWithPlaceholder();
        elements.forEach((key, value) ->
                Assert.assertTrue(Utils.attributeEquals(value, "placeholder", placeholder),
                "The '" + key + "' field doesn't contain the placeholder '" + placeholder + "'"));
    }

    public static void verifyAdvancedSearchDialogIsClosed() {
        Assert.assertTrue(Utils.isElementInvisible(new VNextBOSRAdvancedSearchDialog().getAdvancedSearchForm()),
                "The advanced search dialog hasn't been closed");
    }

    public static void verifyCustomersOptionsContainText(String text) {
        final String value = text.toLowerCase();
        VNextBOSRAdvancedSearchDialogSteps.getCustomersList().forEach(customer -> {
            Assert.assertTrue(customer.toLowerCase().contains(value),
                    "The customer in dropdown doesn't contain the '" + text + "' text");
        });
    }

    public static void verifySelectedTeamStartsWithLetter(String letter) {
        final Select select = new Select(new VNextBOSRAdvancedSearchDialog().getTeamField());
        final String firstLetter = letter.substring(0, 1);
        Assert.assertTrue(select.getFirstSelectedOption().getText().startsWith(firstLetter),
                "The selected team " + select.getFirstSelectedOption().getText() +
                        " is not started with the letter '" + firstLetter + "'");
    }

    public static void verifySelectedSRTypeStartsWithLetter(String letter) {
        final Select select = new Select(new VNextBOSRAdvancedSearchDialog().getSrTypeField());
        final String firstLetter = letter.substring(0, 1);
        Assert.assertTrue(select.getFirstSelectedOption().getText().startsWith(firstLetter),
                "The selected SR type " + select.getFirstSelectedOption().getText() +
                        " is not started with the letter '" + firstLetter + "'");
    }

    public static void verifyOwnersOptionsContainText(String text) {
        final String value = text.toLowerCase();
        VNextBOSRAdvancedSearchDialogSteps.getOwnersList().forEach(owner -> {
            Assert.assertTrue(owner.toLowerCase().contains(value),
                    "The owner in dropdown doesn't contain the '" + text + "' text");
        });
    }

    public static void verifyAdvisorsOptionsContainText(String text) {
        final String value = text.toLowerCase();
        VNextBOSRAdvancedSearchDialogSteps.getAdvisorsList().forEach(advisor -> {
            Assert.assertTrue(advisor.toLowerCase().contains(value),
                    "The advisor in dropdown doesn't contain the '" + text + "' text");
        });
    }

    public static void verifyEmployeesOptionsContainText(String text) {
        final String value = text.toLowerCase();
        VNextBOSRAdvancedSearchDialogSteps.getEmployeesList().forEach(employee -> {
            Assert.assertTrue(employee.toLowerCase().contains(value),
                    "The employee in dropdown doesn't contain the '" + text + "' text");
        });
    }

    public static void verifyVinErrorMessageIsDisplayed(String expected) {
        Assert.assertEquals(VNextBOSRAdvancedSearchDialogSteps.getVinErrorMessage(), expected,
                "The VIN error message hasn't been displayed");
    }
}
