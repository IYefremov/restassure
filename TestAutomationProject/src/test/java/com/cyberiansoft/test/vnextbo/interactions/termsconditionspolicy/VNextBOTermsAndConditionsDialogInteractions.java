package com.cyberiansoft.test.vnextbo.interactions.termsconditionspolicy;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOTermsAndConditionsDialog;
import org.openqa.selenium.WebElement;

public class VNextBOTermsAndConditionsDialogInteractions {

    public static void scrollTermsAndConditionsDown() {
        Utils.scrollToElement(new VNextBOTermsAndConditionsDialog().getLastElement());
    }

    public static void closeTermsAndConditions() {
        clickTermsAndConditionsButton(new VNextBOTermsAndConditionsDialog().getCloseButton());
    }

    public static void acceptTermsAndConditions() {
        clickTermsAndConditionsButton(new VNextBOTermsAndConditionsDialog().getOkButton());
    }

    private static void clickTermsAndConditionsButton(WebElement element) {
        Utils.clickElement(element);
        WaitUtilsWebDriver.waitForInvisibilityIgnoringException(
                new VNextBOTermsAndConditionsDialog().getTermsAndConditionsDialog());
    }
}
