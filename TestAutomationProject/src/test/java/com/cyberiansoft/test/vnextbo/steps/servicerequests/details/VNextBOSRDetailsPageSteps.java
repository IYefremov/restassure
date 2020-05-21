package com.cyberiansoft.test.vnextbo.steps.servicerequests.details;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.servicerequests.details.VNextBOSRDetailsPage;

public class VNextBOSRDetailsPageSteps {

    public static void waitForSRDetailsPageToBeOpened() {
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.waitForVisibility(new VNextBOSRDetailsPage().getServiceRequestsDetailsView());
    }
}
