package com.cyberiansoft.test.vnextbo.validations.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOPartsManagementWebPage;
import com.cyberiansoft.test.vnextbo.validations.VNextBOBaseWebPageValidations;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;

public class VNextBOPartsManagementWebPageValidations extends VNextBOBaseWebPageValidations {

    public static void verifyPastDuePartsBoxIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOPartsManagementWebPage().getPastDuePartsButton()),
                "\"Past Due Parts\" box hasn't been displayed");
    }

    public static void verifyInProgressBoxIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOPartsManagementWebPage().getInProgressButton()),
                "\"In Progress\" box hasn't been displayed");
    }

    public static void verifyCompletedBoxIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOPartsManagementWebPage().getCompletedButton()),
                "\"Completed\" box hasn't been displayed");
    }

    public static void verifyPastDuePartsBoxIsHighlighted() {

        Assert.assertEquals(new VNextBOPartsManagementWebPage().getPastDuePartsButton().getAttribute("style"), "border-color: rgb(235, 87, 87);", "\"Past Due Parts\" box hasn't been highlighted with red border");
    }

    public static void verifyInProgressBoxIsHighlighted() {

        Assert.assertEquals(new VNextBOPartsManagementWebPage().getInProgressButton().getAttribute("style"), "border-color: rgb(111, 207, 151);", "\"In Progress\" box hasn't been highlighted with green border");
    }

    public static void verifyCompletedBoxIsHighlighted() {

        Assert.assertEquals(new VNextBOPartsManagementWebPage().getCompletedButton().getAttribute("style"), "border-color: rgb(111, 207, 151);", "\"Completed\" box hasn't been highlighted with green border");
    }

    public static void verifySavedSearchIsPresentedInTheList(String savedSearchName, boolean shouldBeDisplayed) {
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        if (shouldBeDisplayed)
        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOPartsManagementWebPage().savedSearchOptionByName(savedSearchName)),
                "Saved search hasn't been presented in the list with saved searches");
        else if (!shouldBeDisplayed) {
            try {
                boolean isSavedSearchPresentedInTheList = Utils.isElementNotDisplayed(new VNextBOPartsManagementWebPage().savedSearchOptionByName(savedSearchName));
                Assert.assertFalse(isSavedSearchPresentedInTheList, "Saved search has been presented in the list with saved searches");
            } catch (NoSuchElementException ex) {
                Assert.assertFalse(false, "Saved search has been presented in the list with saved searches");
            }
        }
    }
}
