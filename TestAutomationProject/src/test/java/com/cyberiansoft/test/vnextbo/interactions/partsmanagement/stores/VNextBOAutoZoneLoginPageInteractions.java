package com.cyberiansoft.test.vnextbo.interactions.partsmanagement.stores;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.stores.VNextBOAutoZoneLoginPage;

public class VNextBOAutoZoneLoginPageInteractions {

    public static void clickFirstLocationTableRow() {
        Utils.clickElement(new VNextBOAutoZoneLoginPage().getFirstLocationTableRow());
    }

    public static void clickLoginButton() {
        Utils.clickElement(new VNextBOAutoZoneLoginPage().getLoginButton());
    }
}
