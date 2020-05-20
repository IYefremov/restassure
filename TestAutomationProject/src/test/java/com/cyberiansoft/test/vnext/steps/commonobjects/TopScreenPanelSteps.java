package com.cyberiansoft.test.vnext.steps.commonobjects;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.ConditionWaiter;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnext.screens.commonobjects.VNextTopScreenPanel;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.Keys;

public class TopScreenPanelSteps {

    public static void goToThePreviousScreen() {

        new VNextTopScreenPanel().getBackButton().click();
        WaitUtilsWebDriver.waitABit(500);
    }

    public static void goToTheNextScreen() {

        new VNextTopScreenPanel().getForwardButton().click();
        WaitUtilsWebDriver.waitABit(500);
    }

    public static void openSearchPanel() {

        ConditionWaiter.create(__ -> new VNextTopScreenPanel().getSearchButton().isDisplayed()).execute();
        WaitUtilsWebDriver.waitABit(1000);
        new VNextTopScreenPanel().getSearchButton().click();
    }

    public static void cancelSearch() {

        new VNextTopScreenPanel().getCancelSearchButton().click();
    }

    public static void clearSearchField() {

        new VNextTopScreenPanel().getClearSearchIcon().click();
    }

    public static void saveChanges() {

        new VNextTopScreenPanel().getSaveButton().click();
    }

    public static void fillSearchField(String searchText) {

        VNextTopScreenPanel topScreenPanel = new VNextTopScreenPanel();
        WaitUtils.waitUntilElementIsClickable(topScreenPanel.getSearchField());
        Utils.clickElement(topScreenPanel.getSearchField());
        topScreenPanel.getSearchField().sendKeys(searchText);
        topScreenPanel.getSearchField().sendKeys(Keys.ENTER);
        BaseUtils.waitABit(1000);
    }

    public static void searchData(String searchText) {

        TopScreenPanelSteps.openSearchPanel();
        if (new VNextTopScreenPanel().getClearSearchIcon().isDisplayed())
            TopScreenPanelSteps.clearSearchField();
        TopScreenPanelSteps.fillSearchField(searchText);
    }

    public static void resetSearch() {

        openSearchPanel();
        clearSearchField();
        cancelSearch();
    }
}
