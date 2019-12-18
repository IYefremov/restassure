package com.cyberiansoft.test.vnextbo.validations.commonobjects;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
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

        WaitUtilsWebDriver.waitForTextToBePresentInElement(new VNextBOPageSwitcherElements().getHeaderActivePageNumber(), expectedPageNumber);
        Assert.assertEquals(VNextBOPageSwitcherSteps.getActivePageNumberFromHeaderPager(), expectedPageNumber,
                "Header active page number hasn't been changed.");
        WaitUtilsWebDriver.waitForTextToBePresentInElement(new VNextBOPageSwitcherElements().getFooterActivePageNumber(), expectedPageNumber);
        Assert.assertEquals(VNextBOPageSwitcherSteps.getActivePageNumberFromFooterPager(), expectedPageNumber,
                "Footer active page number hasn't been changed.");
    }

    public static boolean isHeaderLastPageButtonClickable() {

        return Utils.isElementClickable(new VNextBOPageSwitcherElements().getHeaderLastPageBtn(), 1);
    }

    public static boolean isFooterLastPageButtonClickable() {

        return Utils.isElementClickable(new VNextBOPageSwitcherElements().getFooterLastPageBtn(), 1);
    }

    public static boolean isHeaderFirstPageButtonClickable() {

        return Utils.isElementClickable(new VNextBOPageSwitcherElements().getHeaderFirstPageBtn(), 1);
    }

    public static boolean isFooterFirstPageButtonClickable() {

        return Utils.isElementClickable(new VNextBOPageSwitcherElements().getFooterFirstPageBtn(), 1);
    }

    public static boolean isHeaderPreviousPageButtonClickable() {

        return Utils.isElementClickable(new VNextBOPageSwitcherElements().getHeaderPreviousPageBtn(), 1);
    }

    public static boolean isFooterPreviousPageButtonClickable() {
        return Utils.isElementClickable(new VNextBOPageSwitcherElements().getFooterPreviousPageBtn(), 1);
    }

    public static boolean isHeaderNextPageButtonClickable() {
        return Utils.isElementClickable(new VNextBOPageSwitcherElements().getHeaderNextPageBtn(), 1);
    }

    public static boolean isFooterNextPageButtonClickable() {
        return Utils.isElementClickable(new VNextBOPageSwitcherElements().getFooterNextPageBtn(), 1);
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