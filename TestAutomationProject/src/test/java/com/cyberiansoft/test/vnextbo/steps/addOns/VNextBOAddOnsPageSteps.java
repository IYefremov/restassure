package com.cyberiansoft.test.vnextbo.steps.addOns;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.enums.addons.IntegrationStatus;
import com.cyberiansoft.test.vnextbo.interactions.general.VNextBOConfirmationDialogInteractions;
import com.cyberiansoft.test.vnextbo.screens.addons.VNextBOAddOnsPage;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import com.cyberiansoft.test.vnextbo.validations.addons.VNextBOAddOnsPageValidations;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.time.Duration;

public class VNextBOAddOnsPageSteps {

    public static void waitForAddOnsPageToBeLoaded() {
        WaitUtilsWebDriver.waitForAttributeNotToContain(new VNextBOAddOnsPage().getAddonsBlock(), "class", "hidden", 15);
    }

    public static void turnOffAddOnByName(String addOn) {
        final VNextBOAddOnsPage addOnsPage = new VNextBOAddOnsPage();
        WaitUtilsWebDriver.waitForElementNotToBeStale(addOnsPage.getIntegrationStatus(addOn));
        final String addOnDisplayed = Utils.getText(addOnsPage.getIntegrationStatus(addOn));
        if (addOnDisplayed.equals(IntegrationStatus.PENDING.name())) {
            refreshPageWhileAddOnIsChangedFromPendingStatusToOnOrOffStatus(addOn);
        }
        if (addOnDisplayed.equals(IntegrationStatus.ON.name())) {
            WaitUtilsWebDriver.waitForElementNotToBeStale(addOnsPage.getAddOnsTurnOffButton(addOn));
            Utils.clickElement(addOnsPage.getAddOnsTurnOffButton(addOn));
            confirmAddOnChange(addOn);
        }
    }

    public static void turnOnAddOnByName(String addOn) {
        final VNextBOAddOnsPage addOnsPage = new VNextBOAddOnsPage();
        if (Utils.getText(new VNextBOAddOnsPage().getIntegrationStatus(addOn)).equals(IntegrationStatus.PENDING.name())) {
            refreshPageWhileAddOnIsChangedFromPendingStatusToOnOrOffStatus(addOn);
        }
        if (Utils.getText(addOnsPage.getIntegrationStatus(addOn)).equals(IntegrationStatus.OFF.name())) {
            WaitUtilsWebDriver.waitForElementNotToBeStale(addOnsPage.getAddOnsTurnOnButton(addOn), 5);
            WaitUtilsWebDriver.waitABit(1000);
            Utils.clickElement(addOnsPage.getAddOnsTurnOnButton(addOn));
            confirmAddOnChange(addOn);
        }
    }

    private static void confirmAddOnChange(String addOn) {
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.waitABit(1000);
        VNextBOConfirmationDialogInteractions.clickYesButton();
        WaitUtilsWebDriver.waitABit(1000);
        VNextBOModalDialogSteps.clickOkButton();
        WaitUtilsWebDriver.getWebDriverWait(3).until((ExpectedCondition<Boolean>) driver ->
                Utils.getText(new VNextBOAddOnsPage().getIntegrationStatus(addOn)).equals(IntegrationStatus.PENDING.name()));
        VNextBOAddOnsPageValidations.verifyAddOnIntegrationStatus(addOn, IntegrationStatus.PENDING);
    }

    public static String getAddOnStatus(String addOn) {
        return Utils.getText(new VNextBOAddOnsPage().getIntegrationStatus(addOn));
    }

    public static void checkAddOnIsChangedToStatus(String addOn, IntegrationStatus status) {
        refreshPageWhileAddOnIsChangedFromPendingStatusToOnOrOffStatus(addOn);
        VNextBOAddOnsPageValidations.verifyAddOnIntegrationStatus(addOn, status);
    }

    private static void refreshPageWhileAddOnIsChangedFromPendingStatusToOnOrOffStatus(String addOn) {
        WaitUtilsWebDriver.getFluentWait(Duration.ofSeconds(30), Duration.ofMinutes(11))
                .until(driver -> {
                            Utils.refreshPage();
                            return !getAddOnStatus(addOn).equals(IntegrationStatus.PENDING.name());
                        }
                );
    }
}
