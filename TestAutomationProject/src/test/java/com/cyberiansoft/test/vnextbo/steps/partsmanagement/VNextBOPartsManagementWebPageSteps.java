package com.cyberiansoft.test.vnextbo.steps.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOAdvancedSearchDialog;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOPartsDetailsPanel;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOPartsManagementWebPage;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
        WaitUtilsWebDriver.getWebDriverWait(3).until(ExpectedConditions.elementToBeClickable(new VNextBOAdvancedSearchDialog().getCustomerField()));
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    public static void openSavedAdvancedSearch(String searchName) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        Utils.clickElement(new VNextBOPartsManagementWebPage().editSavedSearchButtonByName(searchName));
        WaitUtilsWebDriver.getShortWait().until(ExpectedConditions.elementToBeClickable(new VNextBOAdvancedSearchDialog().getCustomerField()));
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    public static void waitUntilPartsManagementPageIsLoaded() {
        WaitUtilsWebDriver.getWait().until(ExpectedConditions.visibilityOf(new VNextBOPartsManagementWebPage().getPartsOrdersList()));
        WaitUtilsWebDriver.getWait().until(ExpectedConditions.visibilityOf(new VNextBOPartsDetailsPanel().getPartsDetailsTable()));
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }
}
