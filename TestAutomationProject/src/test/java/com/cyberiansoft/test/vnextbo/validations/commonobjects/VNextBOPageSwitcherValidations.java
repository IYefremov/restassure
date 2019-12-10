package com.cyberiansoft.test.vnextbo.validations.commonobjects;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.commonobjects.VNextBOPageSwitcherElements;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOPageSwitcherSteps;
import com.cyberiansoft.test.vnextbo.validations.VNextBOBaseWebPageValidations;
import org.testng.Assert;

public class VNextBOPageSwitcherValidations extends VNextBOBaseWebPageValidations {

    public static void verifyPageNavigationElementsAreDisplayed() {

        VNextBOPageSwitcherElements switcherElements = new VNextBOPageSwitcherElements();
        Assert.assertTrue(Utils.isElementDisplayed(switcherElements.getHeaderNextPageBtn()),
                "Footer Next page \">\" button hasn't been displayed.");
        Assert.assertTrue(Utils.isElementDisplayed(switcherElements.getFooterNextPageBtn()),
                "Header Next page \">\" button hasn't been displayed.");
        Assert.assertTrue(Utils.isElementDisplayed(switcherElements.getHeaderPreviousPageBtn()),
                "Footer Previous page \"<\" button hasn't been displayed.");
        Assert.assertTrue(Utils.isElementDisplayed(switcherElements.getFooterPreviousPageBtn()),
                "Header Previous page \"<\" button hasn't been displayed.");
        Assert.assertTrue(Utils.isElementDisplayed(switcherElements.getHeaderLastPageBtn()),
                "Footer Last page \">>\" button hasn't been displayed.");
        Assert.assertTrue(Utils.isElementDisplayed(switcherElements.getFooterLastPageBtn()),
                "Header Last page \">>\" button hasn't been displayed.");
        Assert.assertTrue(Utils.isElementDisplayed(switcherElements.getHeaderFirstPageBtn()),
                "Footer First page \"<<\" button hasn't been displayed.");
        Assert.assertTrue(Utils.isElementDisplayed(switcherElements.getFooterFirstPageBtn()),
                "Header First page \"<<\" button hasn't been displayed.");
        Assert.assertTrue(Utils.isElementDisplayed(switcherElements.getHeaderItemsPerPageField()),
                "Header Items per page field hasn't been displayed.");
        Assert.assertTrue(Utils.isElementDisplayed(switcherElements.getFooterItemsPerPageField()),
                "Footer Items per page field hasn't been displayed.");
    }

    public static void verifyOpenedPageNumberIsCorrect(String expectedPageNumber) {

        Assert.assertEquals(VNextBOPageSwitcherSteps.getActivePageNumberFromHeaderPager(), expectedPageNumber,
                "Header active page number hasn't been changed.");
        Assert.assertEquals(VNextBOPageSwitcherSteps.getActivePageNumberFromFooterPager(), expectedPageNumber,
                "Footer active page number hasn't been changed.");
    }

    public static boolean verifyHeaderLastPageButtonIsClickable() {

        return Utils.isElementClickable(new VNextBOPageSwitcherElements().getHeaderLastPageBtn());
    }

    public static boolean isFooterLastPageButtonClickable() {

        return Utils.isElementClickable(new VNextBOPageSwitcherElements().getFooterLastPageBtn());
    }

    public static boolean verifyHeaderFirstPageButtonIsClickable() {

        return Utils.isElementClickable(new VNextBOPageSwitcherElements().getHeaderFirstPageBtn());
    }

    public static boolean verifyFooterFirstPageButtonIsClickable() {

        return Utils.isElementClickable(new VNextBOPageSwitcherElements().getFooterFirstPageBtn());
    }

    public static void verifyItemsPerPageNumberIsCorrect(String expectedItemsNumber) {

        Assert.assertTrue(VNextBOPageSwitcherSteps.getItemsPerPageNumberFromTopElement().contains(expectedItemsNumber),
                "Top paging box has had incorrect items per page number.");
        Assert.assertTrue(VNextBOPageSwitcherSteps.getItemsPerPageNumberFromBottomElement().contains(expectedItemsNumber),
                "Bottom paging box has had incorrect items per page number.");
    }

    public static void verifyTopAndBottomPagingElementsHaveSamePageNumber() {

        Assert.assertEquals(VNextBOPageSwitcherSteps.getActivePageNumberFromHeaderPager(),
                VNextBOPageSwitcherSteps.getActivePageNumberFromFooterPager(),
                "Top and bottom active page numbers haven't been synchronized.");
    }
}