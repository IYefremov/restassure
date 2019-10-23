package com.cyberiansoft.test.vnextbo.steps.commonObjects;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.commonObjects.VNextBOSearchPanel;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;

public class VNextBOSearchPanelSteps extends VNextBOBaseWebPageSteps {

    public static String getSearchFilterText()
    {
        VNextBOSearchPanel searchPanel = new VNextBOSearchPanel();
        return Utils.getText(searchPanel.getFilterInfoText());
    }

    public static void clearSearchFilter()
    {
        VNextBOSearchPanel searchPanel = new VNextBOSearchPanel();
        Utils.clickElement(searchPanel.getSearchXIcon());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void openAdvancedSearchForm()
    {
        VNextBOSearchPanel searchPanel = new VNextBOSearchPanel();
        Utils.clickElement(searchPanel.getAdvancedSearchCaret());
    }

    public static void searchByText(String userName)
    {
        VNextBOSearchPanel searchPanel = new VNextBOSearchPanel();
        Utils.clearAndType(searchPanel.getSearchInputField(), userName);
        Utils.clickElement(searchPanel.getSearchLoupeIcon());
        WaitUtilsWebDriver.waitForLoading();
    }
}