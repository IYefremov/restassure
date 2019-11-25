package com.cyberiansoft.test.vnextbo.interactions.general;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOFooterPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOPrivacyPolicyDialog;
import com.cyberiansoft.test.vnextbo.screens.VNextBOTermsAndConditionsDialog;
import org.openqa.selenium.WebElement;

public class VNextBOFooterPanelInteractions {

    public static void clickTermsAndConditionsLink() {
        clickFooterPanelElement(new VNextBOFooterPanel().getTermsAndConditions());
        WaitUtilsWebDriver.waitForVisibility(new VNextBOTermsAndConditionsDialog().getTermsAndConditionsDialog());
    }

    public static void clickPrivacyPolicyLink() {
        clickFooterPanelElement(new VNextBOFooterPanel().getPrivacyPolicy());
        WaitUtilsWebDriver.waitForVisibility(new VNextBOPrivacyPolicyDialog().getPrivacyPolicyDialog());
    }

    private static void clickFooterPanelElement(WebElement element) {
        WaitUtilsWebDriver.waitABit(1000);
        WaitUtilsWebDriver.waitForVisibility(element);
        Utils.scrollToElement(element);
        Utils.clickElement(element);
        WaitUtilsWebDriver.waitForLoading();
    }
}
