package com.cyberiansoft.test.vnextbo.steps.addOns;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.enums.addons.IntegrationStatus;
import com.cyberiansoft.test.vnextbo.screens.addons.VNextBOAddOnsPage;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import com.cyberiansoft.test.vnextbo.validations.addons.VNextBOAddOnsPageValidations;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.testng.Assert;

public class VNextBOAddOnsPageSteps {

    public static void waitForAddOnsPageToBeLoaded() {
        WaitUtilsWebDriver.waitForAttributeNotToContain(new VNextBOAddOnsPage().getAddonsBlock(), "class", "hidden", 15);
    }

    public static void turnOffAddOnByName(String addOn) {
        final VNextBOAddOnsPage addOnsPage = new VNextBOAddOnsPage();
        if (Utils.getText(addOnsPage.getIntegrationStatus(addOn)).equals(IntegrationStatus.ON.name())) {
            Utils.clickElement(addOnsPage.getAddOnsTurnOffButton(addOn));
            confirmAddOnChange(addOn);
        } else {
            Assert.fail("The add on cannot be turned off. Current status is " +
                    Utils.getText(addOnsPage.getIntegrationStatus(addOn)));
        }
    }

    public static void turnOnAddOnByName(String addOn) {
        final VNextBOAddOnsPage addOnsPage = new VNextBOAddOnsPage();
        if (Utils.getText(addOnsPage.getIntegrationStatus(addOn)).equals(IntegrationStatus.OFF.name())) {
            Utils.clickElement(addOnsPage.getAddOnsTurnOnButton(addOn));
            confirmAddOnChange(addOn);
        } else {
            Assert.fail("The add on cannot be turned on. Current status is " +
                    Utils.getText(addOnsPage.getIntegrationStatus(addOn)));
        }
    }

    private static void confirmAddOnChange(String addOn) {
        WaitUtilsWebDriver.waitABit(1000);
        VNextBOModalDialogSteps.clickYesButton();
        WaitUtilsWebDriver.waitABit(1000);
        VNextBOModalDialogSteps.clickOkButton();
        WaitUtilsWebDriver.getWebDriverWait(3).until((ExpectedCondition<Boolean>) driver ->
                Utils.getText(new VNextBOAddOnsPage().getIntegrationStatus(addOn)).equals(IntegrationStatus.PENDING.name()));
        VNextBOAddOnsPageValidations.verifyAddOnIntegrationStatus(addOn, IntegrationStatus.PENDING);
    }
}
