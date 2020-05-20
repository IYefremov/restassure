package com.cyberiansoft.test.vnextbo.steps.commonobjects;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.commonobjects.VNextBOReactSearchPanel;

public class VNextBOReactSearchPanelSteps {

    public static void clearSearchFilter() {
        Utils.clickElement(new VNextBOReactSearchPanel().getSearchXIcon());
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    public static String getSearchInputFieldValue() {
        return Utils.getText(new VNextBOReactSearchPanel().getSearchInputField());
    }

    public static void searchByText(String searchText) {
        VNextBOReactSearchPanel searchPanel = new VNextBOReactSearchPanel();
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.waitForElementToBeClickable(searchPanel.getSearchInputField());
        Utils.clearAndType(searchPanel.getSearchInputField(), searchText);
        Utils.clickElement(searchPanel.getSearchLoupeIcon());
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    public static void searchByTextWithEnterButton(String searchText) {
        VNextBOReactSearchPanel searchPanel = new VNextBOReactSearchPanel();
        WaitUtilsWebDriver.waitForElementToBeClickable(searchPanel.getSearchInputField());
        Utils.sendKeysWithEnter(searchPanel.getSearchInputField(), searchText);
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }
}
