package com.cyberiansoft.test.bo.steps.search;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.pageobjects.webpages.BOSearchPanel;
import com.cyberiansoft.test.bo.validations.search.SearchValidations;

public class BOSearchSteps {

    public static void expandSearchTab() {
        final BOSearchPanel searchPanel = new BOSearchPanel();
        WaitUtilsWebDriver.elementShouldBeVisible(searchPanel.getSearchTogglerButton(), true);
        WaitUtilsWebDriver.waitForAttributeToContain(searchPanel.getSearchPanel(), "class", "slider");
        if (!SearchValidations.isSearchExpanded()) {
            Utils.clickElement(searchPanel.getSearchTogglerButton());
            WaitUtilsWebDriver.waitForAttributeToContain(searchPanel.getSearchPanel(), "class", "open");
        }
    }

    public static void search() {
        Utils.clickElement(new BOSearchPanel().getSearchButton());
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.waitABit(1000);
    }
}
