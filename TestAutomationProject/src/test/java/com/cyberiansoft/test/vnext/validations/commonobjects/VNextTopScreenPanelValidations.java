package com.cyberiansoft.test.vnext.validations.commonobjects;

import com.cyberiansoft.test.baseutils.ConditionWaiter;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnext.screens.commonobjects.VNextTopScreenPanel;
import org.testng.Assert;

public class VNextTopScreenPanelValidations {

    public static void verifyBackIconIsDisplayed() {

        ConditionWaiter.create(__ -> !new VNextTopScreenPanel().getBackButton().isDisplayed()).execute();
        ConditionWaiter.create(__ -> new VNextTopScreenPanel().getBackButton().isDisplayed()).execute();
        Assert.assertTrue(new VNextTopScreenPanel().getBackButton().isDisplayed(),
                "'Back' button hasn't been displayed");
    }

    public static void verifySearchFieldContainsCorrectValue(String expectedVale) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextTopScreenPanel().getSearchField()), expectedVale,
                "Search field hasn't had expected value");
    }

    public static void verifySearchIconIsDisplayed() {

        Assert.assertTrue(new VNextTopScreenPanel().getSearchButton().isDisplayed(),
                "'Search' button hasn't been displayed");
    }

    public static void verifyClearIconIsDisplayed(boolean shouldBeDisplayed) {

        if (shouldBeDisplayed)
            Assert.assertTrue(new VNextTopScreenPanel().getClearSearchIcon().isDisplayed(), "Clear icon hasn't been displayed");
        else
            Assert.assertFalse(new VNextTopScreenPanel().getClearSearchIcon().isDisplayed(), "Clear icon has been displayed");
    }

    public static void verifyCloseButtonIsDisplayed(boolean shouldBeDisplayed) {

        if (shouldBeDisplayed)
            Assert.assertTrue(new VNextTopScreenPanel().getCancelSearchButton().isDisplayed(), "Close button hasn't been displayed");
        else
            Assert.assertFalse(new VNextTopScreenPanel().getCancelSearchButton().isDisplayed(), "Close button has been displayed");
    }

    public static void verifySearchFieldIsDisplayed(boolean shouldBeDisplayed) {

        if (shouldBeDisplayed)
            Assert.assertTrue(Utils.isElementDisplayed(new VNextTopScreenPanel().getSearchField()), "Search field hasn't been displayed");
        else
            Assert.assertFalse(Utils.isElementDisplayed(new VNextTopScreenPanel().getSearchField()), "Search field has been displayed");
    }

    public static void verifySearchIsExpanded(boolean shouldBeExpanded) {

        if (shouldBeExpanded) {
            verifyClearIconIsDisplayed(true);
            verifyCloseButtonIsDisplayed(true);
            verifySearchFieldIsDisplayed(true);
        } else {
            verifyClearIconIsDisplayed(false);
            verifyCloseButtonIsDisplayed(false);
            verifySearchFieldIsDisplayed(false);
        }
    }
}
