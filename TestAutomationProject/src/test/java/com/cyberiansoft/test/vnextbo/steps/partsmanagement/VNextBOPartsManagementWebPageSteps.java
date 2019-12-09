package com.cyberiansoft.test.vnextbo.steps.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOPartsManagementWebPage;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.google.inject.internal.cglib.core.$DefaultGeneratorStrategy;

public class VNextBOPartsManagementWebPageSteps extends VNextBOBaseWebPageSteps {

    public static void clickPastDuePartsButton() {

        Utils.clickElement(new VNextBOPartsManagementWebPage().getPastDuePartsButton());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickInProgressButton() {

        Utils.clickElement(new VNextBOPartsManagementWebPage().getInProgressButton());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickCompletedButton() {

        Utils.clickElement(new VNextBOPartsManagementWebPage().getCompletedButton());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void openAdvancedSearchForm() {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        Utils.clickElement(new VNextBOPartsManagementWebPage().getSavedSearchListGearIcon());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void openSavedAdvancedSearch(String searchName) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        Utils.clickElement(new VNextBOPartsManagementWebPage().editSavedSearchButtonByName(searchName));
        WaitUtilsWebDriver.waitForLoading();
    }

}
