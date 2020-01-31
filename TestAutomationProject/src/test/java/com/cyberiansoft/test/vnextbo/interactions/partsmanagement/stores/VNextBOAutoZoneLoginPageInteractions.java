package com.cyberiansoft.test.vnextbo.interactions.partsmanagement.stores;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.stores.VNextBOAutoZoneLoginPage;

public class VNextBOAutoZoneLoginPageInteractions {

    private static VNextBOAutoZoneLoginPage autoZoneLoginPage;

    static {
        autoZoneLoginPage = new VNextBOAutoZoneLoginPage();
    }

    public static void clickFirstLocationTableRow() {
        Utils.clickElement(autoZoneLoginPage.getFirstLocationTableRow());
    }

    public static void clickLoginButton() {
        Utils.clickElement(autoZoneLoginPage.getLoginButton());
    }
}
