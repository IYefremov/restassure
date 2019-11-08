package com.cyberiansoft.test.vnextbo.interactions.termsconditionspolicy;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOPrivacyPolicyDialog;
import org.openqa.selenium.WebElement;

public class VNextBOPrivacyPolicyDialogInteractions {

    public static void scrollPrivacyPolicyDown() {
        Utils.scrollToElement(new VNextBOPrivacyPolicyDialog().getLastElement());

    }

    public static void closePrivacyPolicy() {
        clickPrivacyPolicyButton(new VNextBOPrivacyPolicyDialog().getCloseButton());
    }

    public static void acceptPrivacyPolicy() {
        clickPrivacyPolicyButton(new VNextBOPrivacyPolicyDialog().getOkButton());
    }

    private static void clickPrivacyPolicyButton(WebElement element) {
        Utils.clickElement(element);
        try {
            WaitUtilsWebDriver.waitForInvisibility(new VNextBOPrivacyPolicyDialog().getPrivacyPolicyDialog());
        } catch (Exception e) {
            Utils.clickElement(element);
        }
    }
}
