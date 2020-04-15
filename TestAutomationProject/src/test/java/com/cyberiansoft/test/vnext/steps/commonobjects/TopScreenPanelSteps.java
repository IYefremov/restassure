package com.cyberiansoft.test.vnext.steps.commonobjects;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.ConditionWaiter;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnext.screens.commonobjects.VNextTopScreenPanel;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class TopScreenPanelSteps {

    public static void goToThePreviousScreen() {

        Utils.clickElement(new VNextTopScreenPanel().getBackButton());
    }

    public static void openSearchPanel() {

        ConditionWaiter.create(__ -> new VNextTopScreenPanel().getSearchButton().isDisplayed()).execute();
        WaitUtilsWebDriver.waitABit(1000);
        Utils.clickElement(new VNextTopScreenPanel().getSearchButton());
    }

    public static void cancelSearch() {

        Utils.clickElement(new VNextTopScreenPanel().getCancelSearchButton());
    }

    public static void clearSearchField() {

        Utils.clickElement(new VNextTopScreenPanel().getClearSearchIcon());
    }

    public static void saveChanges() {

        Utils.clickElement(new VNextTopScreenPanel().getSaveIcon());
    }

    public static void fillSearchField(String searchText) {

        VNextTopScreenPanel topScreenPanel = new VNextTopScreenPanel();
        WaitUtils.waitUntilElementIsClickable(topScreenPanel.getSearchField());
        Utils.clickElement(topScreenPanel.getSearchField());
        topScreenPanel.getSearchField().sendKeys(searchText);
        BaseUtils.waitABit(1000);
    }
}
