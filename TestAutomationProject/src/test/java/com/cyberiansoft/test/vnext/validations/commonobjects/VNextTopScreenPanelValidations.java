package com.cyberiansoft.test.vnext.validations.commonobjects;

import com.cyberiansoft.test.baseutils.ConditionWaiter;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnext.screens.commonobjects.VNextTopScreenPanel;
import org.testng.Assert;

public class VNextTopScreenPanelValidations {

    public static void verifyBackIconIsDisplayed() {

        ConditionWaiter.create(__ -> !new VNextTopScreenPanel().getBackButton().isDisplayed()).execute();
        ConditionWaiter.create(__ -> new VNextTopScreenPanel().getBackButton().isDisplayed()).execute();
        Assert.assertTrue(Utils.isElementDisplayed(new VNextTopScreenPanel().getBackButton()),
                "'Back' button hasn't been displayed");
    }

    public static void verifySearchFieldContainsCorrectValue(String expectedVale) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextTopScreenPanel().getSearchField()), expectedVale,
                "Search field hasn't had expected value");
    }

    public static void verifySearchIconIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextTopScreenPanel().getSearchButton()),
                "'Search' button hasn't been displayed");
    }

    public static void verifyClearIconIsDisplayed(boolean shouldBeDisplayed) {

        if (shouldBeDisplayed)
            Assert.assertTrue(Utils.isElementDisplayed(new VNextTopScreenPanel().getClearSearchIcon()), "Clear icon hasn't been displayed");
        else
            Assert.assertFalse(Utils.isElementDisplayed(new VNextTopScreenPanel().getClearSearchIcon()), "Clear icon has been displayed");
    }

    public static void verifyCloseButtonIsDisplayed(boolean shouldBeDisplayed) {

        if (shouldBeDisplayed)
            Assert.assertTrue(Utils.isElementDisplayed(new VNextTopScreenPanel().getCancelSearchButton()), "Close button hasn't been displayed");
        else
            Assert.assertFalse(Utils.isElementDisplayed(new VNextTopScreenPanel().getCancelSearchButton()), "Close button has been displayed");
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
