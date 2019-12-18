package com.cyberiansoft.test.vnextbo.steps.commonobjects;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.commonobjects.VNextBOSearchPanel;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;

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
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
    }

    public static void searchByText(String searchText) {

        VNextBOSearchPanel searchPanel = new VNextBOSearchPanel();
        WaitUtilsWebDriver.waitForElementToBeClickable(searchPanel.getSearchInputField());
        Utils.clearAndType(searchPanel.getSearchInputField(), searchText);
        Utils.clickElement(searchPanel.getSearchLoupeIcon());
    }
}