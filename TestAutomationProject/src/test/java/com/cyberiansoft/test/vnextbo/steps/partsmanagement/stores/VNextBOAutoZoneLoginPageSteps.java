package com.cyberiansoft.test.vnextbo.steps.partsmanagement.stores;

import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.stores.VNextBOAutoZoneLoginPageInteractions;

public class VNextBOAutoZoneLoginPageSteps {

    public static void loginToAutoZone() {
        VNextBOAutoZoneLoginPageInteractions.clickFirstLocationTableRow();
        VNextBOAutoZoneLoginPageInteractions.clickLoginButton();
    }
}
