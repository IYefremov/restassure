package com.cyberiansoft.test.vnextbo.steps.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOPartsManagementWebPage;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;

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

}
