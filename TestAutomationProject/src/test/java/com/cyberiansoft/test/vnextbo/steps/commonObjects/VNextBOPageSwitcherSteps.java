package com.cyberiansoft.test.vnextbo.steps.commonObjects;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.commonObjects.VNextBOPageSwitcherElements;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;

public class VNextBOPageSwitcherSteps extends VNextBOBaseWebPageSteps {

    public static void clickHeaderNextPageButton()
    {
        VNextBOPageSwitcherElements switcherElements = new VNextBOPageSwitcherElements();
        Utils.clickElement(switcherElements.getHeaderNextPageBtn());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickFooterNextPageButton()
    {
        VNextBOPageSwitcherElements switcherElements = new VNextBOPageSwitcherElements();
        Utils.clickElement(switcherElements.getFooterNextPageBtn());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickHeaderPreviousPageButton()
    {
        VNextBOPageSwitcherElements switcherElements = new VNextBOPageSwitcherElements();
        Utils.clickElement(switcherElements.getHeaderPreviousPageBtn());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickFooterPreviousPageButton()
    {
        VNextBOPageSwitcherElements switcherElements = new VNextBOPageSwitcherElements();
        Utils.clickElement(switcherElements.getFooterPreviousPageBtn());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickHeaderLastPageButton()
    {
        VNextBOPageSwitcherElements switcherElements = new VNextBOPageSwitcherElements();
        Utils.clickElement(switcherElements.getHeaderLastPageBtn());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickFooterLastPageButton()
    {
        VNextBOPageSwitcherElements switcherElements = new VNextBOPageSwitcherElements();
        Utils.clickElement(switcherElements.getFooterLastPageBtn());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickHeaderFirstPageButton()
    {
        VNextBOPageSwitcherElements switcherElements = new VNextBOPageSwitcherElements();
        Utils.clickElement(switcherElements.getHeaderFirstPageBtn());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickFooterFirstPageButton()
    {
        VNextBOPageSwitcherElements switcherElements = new VNextBOPageSwitcherElements();
        Utils.clickElement(switcherElements.getFooterFirstPageBtn());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void openPageByNumber(int pageNumber)
    {
        VNextBOPageSwitcherElements switcherElements = new VNextBOPageSwitcherElements();
        Utils.clickElement(switcherElements.specificPageButton(pageNumber));
        WaitUtilsWebDriver.waitForLoading();
    }

    public static String getActivePageNumberFromHeaderPager()
    {
        VNextBOPageSwitcherElements switcherElements = new VNextBOPageSwitcherElements();
        return Utils.getText(switcherElements.getActivePageTopPagingElement());
    }

    public static String getActivePageNumberFromFooterPager()
    {
        VNextBOPageSwitcherElements switcherElements = new VNextBOPageSwitcherElements();
        return Utils.getText(switcherElements.getActivePageBottomPagingElement());
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
        VNextBOPageSwitcherElements switcherElements = new VNextBOPageSwitcherElements();
        return Utils.getText(switcherElements.getHeaderItemsPerPageField());
    }

    public static String getItemsPerPageNumberFromBottomElement()
    {
        VNextBOPageSwitcherElements switcherElements = new VNextBOPageSwitcherElements();
        return Utils.getText(switcherElements.getFooterItemsPerPageField());
    }
}