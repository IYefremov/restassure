package com.cyberiansoft.test.vnextbo.steps.commonobjects;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.commonobjects.VNextBOSearchPanel;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

public class VNextBOSearchPanelSteps extends VNextBOBaseWebPageSteps {

    public static String getSearchFilterText() {

        return Utils.getText(new VNextBOSearchPanel().getFilterInfoText());
    }

    public static void clearSearchFilterWithSpinnerLoading() {

        clearSearchFilter();
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
    }

    public static void clearSearchFilter() {
        Utils.clickElement(new VNextBOSearchPanel().getSearchXIcon());
    }

    public static void openAdvancedSearchForm() {

        Utils.clickElement(new VNextBOSearchPanel().getAdvancedSearchCaret());
    }

    public static void searchByTextWithSpinnerLoading(String searchText) {

        VNextBOSearchPanel searchPanel = new VNextBOSearchPanel();
        WaitUtilsWebDriver.waitForElementToBeClickable(searchPanel.getSearchInputField());
        Utils.clearAndType(searchPanel.getSearchInputField(), searchText);
        Utils.clickElement(searchPanel.getSearchLoupeIcon());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        WaitUtilsWebDriver.waitABit(1000);
    }

    public static void searchByTextWithEnterButton(String searchText) {

        VNextBOSearchPanel searchPanel = new VNextBOSearchPanel();
        WaitUtilsWebDriver.waitForElementToBeClickable(searchPanel.getSearchInputField());
        Utils.clearAndType(searchPanel.getSearchInputField(), searchText);
        new Actions(DriverBuilder.getInstance().getDriver()).sendKeys(searchPanel.getSearchInputField(), Keys.ENTER).build().perform();
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void searchByText(String searchText) {

        VNextBOSearchPanel searchPanel = new VNextBOSearchPanel();
        WaitUtilsWebDriver.waitForElementToBeClickable(searchPanel.getSearchInputField());
        Utils.clearAndType(searchPanel.getSearchInputField(), searchText);
        Utils.clickElement(searchPanel.getSearchLoupeIcon());
    }
}