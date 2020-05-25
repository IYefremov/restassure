package com.cyberiansoft.test.vnextbo.validations.servicerequests;

import com.cyberiansoft.test.vnextbo.screens.servicerequests.VNextBOSRTable;
import com.cyberiansoft.test.vnextbo.steps.servicerequests.VNextBOSRTableSteps;
import com.cyberiansoft.test.vnextbo.utils.VNextBOAlertMessages;
import org.testng.Assert;

public class VNextBOSRTableValidations {

    public static boolean isNotFoundNotificationDisplayed() {
        return VNextBOSRTableSteps.getEmptyTableNotification().equals(VNextBOAlertMessages.NO_RECORDS_FOUND);
    }

    public static void verifyNotFoundNotificationIsDisplayed() {
        Assert.assertEquals(VNextBOSRTableSteps.getEmptyTableNotification(), VNextBOAlertMessages.NO_RECORDS_FOUND,
                "'No records found' notification hasn't been displayed");
    }

    public static void verifyEitherNotificationOrSRsListIsDisplayed() {
        if (new VNextBOSRTable().getSrNumbersList().size() == 0) {
            verifyNotFoundNotificationIsDisplayed();
        } else {
            Assert.assertTrue(new VNextBOSRTable().getSrNumbersList().size() > 0, "The SRs haven't been displayed");
        }
    }

    public static void verifySRContainingValueIsDisplayed(String actual, String expected) {
        Assert.assertTrue(actual.contains(expected), "The SR hasn't been found");
    }
}
