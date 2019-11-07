package com.cyberiansoft.test.vnextbo.validations.general;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOFooterPanel;

public class VNextBOFooterPanelValidations {

    public static boolean isFooterDisplayed() {
        return Utils.isElementDisplayed(new VNextBOFooterPanel().getFooter());
    }

    public static boolean isTermsAndConditionsLinkDisplayed() {
        return Utils.elementContainsText(new VNextBOFooterPanel().getTermsAndConditions(), "Terms and Conditions");
    }

    public static boolean isPrivacyPolicyLinkDisplayed() {
        return Utils.elementContainsText(new VNextBOFooterPanel().getPrivacyPolicy(), "Privacy Policy");
    }

    public static boolean isIntercomDisplayed() {
        WaitUtilsWebDriver.waitForVisibility(new VNextBOFooterPanel().getIntercomFrame());
        Utils.switchToFrame(new VNextBOFooterPanel().getIntercomFrame());
        return Utils.isElementDisplayed(new VNextBOFooterPanel().getIntercom());
    }

    public static boolean footerContains(String text) {
        return Utils.elementContainsText(new VNextBOFooterPanel().getFooter(), text);
    }
}
