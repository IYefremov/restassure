package com.cyberiansoft.test.vnextbo.verifications.clients;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.clients.VNextBOClientsAdvancedSearchForm;
import org.testng.Assert;

public class VNextBOClientsAdvancedSearchValidations {

    public static void verifyAdvancedSearchFormIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOClientsAdvancedSearchForm().getAdvancedSearchFormContent()),
                "Advanced search form hasn't been displayed\"");
    }

    public static void verifyAdvancedSearchFormIsNotDisplayed(VNextBOClientsAdvancedSearchForm advancedSearchForm) {

        Assert.assertTrue(Utils.isElementNotDisplayed(advancedSearchForm.getAdvancedSearchFormContent()),
                "Advanced search form hasn't been closed");
    }

    public static void verifyAllElementsDisplayed() {

        VNextBOClientsAdvancedSearchForm advancedSearchForm = new VNextBOClientsAdvancedSearchForm();
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchForm.getSearchButton()),
                "Advanced Search button hasn't been displayed");
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchForm.getNameField()),
                "Name field hasn't been displayed");
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchForm.getAddressField()),
                "Address field hasn't been displayed");
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchForm.getEmailField()),
                "Email field hasn't been displayed");
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchForm.getPhoneField()),
                "Phone field hasn't been displayed");
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchForm.getCloseButton()),
                "Close x-icon button hasn't been displayed");
    }

    public static void verifyNameFieldContainsExpectedText(String expectedText) {

        Assert.assertEquals(new VNextBOClientsAdvancedSearchForm().getNameField().getAttribute("value"), expectedText,
                "Name field hasn't contained expected text");
    }

    public static void verifyEmailFieldContainsExpectedText(String expectedText) {

        Assert.assertEquals(new VNextBOClientsAdvancedSearchForm().getEmailField().getAttribute("value"), expectedText,
                "Email field hasn't contained expected text");
    }

    public static void verifyAddressFieldContainsExpectedText(String expectedText) {

        Assert.assertEquals(new VNextBOClientsAdvancedSearchForm().getAddressField().getAttribute("value"), expectedText,
                "Address field hasn't contained expected text");
    }

    public static void verifyPhoneFieldContainsExpectedText(String expectedText) {

        Assert.assertEquals(new VNextBOClientsAdvancedSearchForm().getPhoneField().getAttribute("value"), expectedText,
                "Phone field hasn't contained expected text");
    }

    public static void verifyTypeFieldContainsExpectedText(String expectedText) {

        Assert.assertEquals(Utils.getText(new VNextBOClientsAdvancedSearchForm().getTypeDropDownField()), expectedText,
                "Type field hasn't contained expected text");
    }
}