package com.cyberiansoft.test.vnextbo.validations.servicerequests;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.servicerequests.VNextBOSRPage;

public class VNextBOSRPageValidations {

    public static boolean isLoadMoreServicesButtonEnabled() {
        return Utils.getAttribute(new VNextBOSRPage().getLoadMoreButton(), "disabled") == null;
    }

    public static boolean isLoadMoreServicesButtonDisabled() {
        return Utils.getAttribute(new VNextBOSRPage().getLoadMoreButton(), "disabled").equals("true");
    }

    public static boolean isLoadMoreServicesButtonDisplayed() {
        try {
            return Utils.isElementDisplayed(new VNextBOSRPage().getLoadMoreButton());
        } catch (Exception e) {
            return false;
        }
    }
}
