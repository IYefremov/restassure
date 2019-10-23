package com.cyberiansoft.test.vnextbo.steps.commonObjects;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.commonObjects.VNextBOPageSwitcherElements;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;

public class VNextBOPageSwitcherSteps extends VNextBOBaseWebPageSteps {

    public static void clickHeaderNextPageButton()
    {
        Utils.clickElement(new VNextBOPageSwitcherElements().getHeaderNextPageBtn());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickFooterNextPageButton()
    {
        Utils.clickElement(new VNextBOPageSwitcherElements().getFooterNextPageBtn());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickHeaderPreviousPageButton()
    {
        Utils.clickElement(new VNextBOPageSwitcherElements().getHeaderPreviousPageBtn());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickFooterPreviousPageButton()
    {
        Utils.clickElement(new VNextBOPageSwitcherElements().getFooterPreviousPageBtn());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickHeaderLastPageButton()
    {
        Utils.clickElement(new VNextBOPageSwitcherElements().getHeaderLastPageBtn());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickFooterLastPageButton()
    {
        Utils.clickElement(new VNextBOPageSwitcherElements().getFooterLastPageBtn());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickHeaderFirstPageButton()
    {
        Utils.clickElement(new VNextBOPageSwitcherElements().getHeaderFirstPageBtn());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickFooterFirstPageButton()
    {
        Utils.clickElement(new VNextBOPageSwitcherElements().getFooterFirstPageBtn());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void openPageByNumber(int pageNumber)
    {
        Utils.clickElement(new VNextBOPageSwitcherElements().specificPageButton(pageNumber));
        WaitUtilsWebDriver.waitForLoading();
    }

    public static String getActivePageNumberFromHeaderPager()
    {
        return Utils.getText(new VNextBOPageSwitcherElements().getActivePageTopPagingElement());
    }

    public static String getActivePageNumberFromFooterPager()
    {
        return Utils.getText(new VNextBOPageSwitcherElements().getActivePageBottomPagingElement());
    }

    public static void changeItemsPerPage(String itemsPerPage)
    {
        VNextBOPageSwitcherElements switcherElements = new VNextBOPageSwitcherElements();
        Utils.clickElement(switcherElements.getHeaderItemsPerPageField());
        Utils.clickWithJS(switcherElements.itemsPerPageOption(itemsPerPage));
        WaitUtilsWebDriver.waitForLoading();
    }

    public static String getItemsPerPageNumberFromTopElement()
    {
        return Utils.getText(new VNextBOPageSwitcherElements().getHeaderItemsPerPageField());
    }

    public static String getItemsPerPageNumberFromBottomElement()
    {
        return Utils.getText(new VNextBOPageSwitcherElements().getFooterItemsPerPageField());
    }
}