package com.cyberiansoft.test.vnextbo.steps.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOPartsDetailsPanel;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOPartsManagementWebPage;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs.VNextBOAdvancedSearchDialog;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;

public class VNextBOPartsManagementWebPageSteps extends VNextBOBaseWebPageSteps {

    public static void clickPastDuePartsButton() {

        Utils.clickElement(new VNextBOPartsManagementWebPage().getPastDuePartsButton());
        waitUntilPartsManagementPageIsLoaded();
    }

    public static void clickInProgressButton() {

        Utils.clickElement(new VNextBOPartsManagementWebPage().getInProgressButton());
        waitUntilPartsManagementPageIsLoaded();
    }

    public static void clickCompletedButton() {

        Utils.clickElement(new VNextBOPartsManagementWebPage().getCompletedButton());
        waitUntilPartsManagementPageIsLoaded();
    }

    public static void openAdvancedSearchForm() {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        Utils.clickElement(new VNextBOPartsManagementWebPage().getSavedSearchListGearIcon());
        WaitUtilsWebDriver.elementShouldBeClickable(new VNextBOAdvancedSearchDialog().getCustomerField(), true, 3);
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    public static void openSavedAdvancedSearch(String searchName) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        Utils.clickElement(new VNextBOPartsManagementWebPage().editSavedSearchButtonByName(searchName));
        WaitUtilsWebDriver.elementShouldBeClickable(new VNextBOAdvancedSearchDialog().getCustomerField(), true, 5);
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    public static void waitUntilPartsManagementPageIsLoaded() {
        WaitUtilsWebDriver.waitForAttributeNotToContain(new VNextBOPartsManagementWebPage().getPartsManagementBlock(), "class", "hidden", 15);
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.elementShouldBeVisible(new VNextBOPartsManagementWebPage().getPartsOrdersList(), true, 2);
        WaitUtilsWebDriver.elementShouldBeVisible(new VNextBOPartsDetailsPanel().getPartsDetailsTable(), true, 2);
        WaitUtilsWebDriver.waitForSpinnerToDisappear(4);
    }
}
