package com.cyberiansoft.test.vnextbo.steps.servicerequests.details;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.servicerequests.details.VNextBOSRHeader;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBOPartsManagementWebPageSteps;

public class VNextBOSRHeaderSteps {

    public static void returnToSrPage() {
        Utils.clickElement(new VNextBOSRHeader().getBackButton());
        VNextBOPartsManagementWebPageSteps.waitUntilServiceRequestsPageIsLoaded();
    }
}
