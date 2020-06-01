package com.cyberiansoft.test.vnextbo.validations.servicerequests;

import com.cyberiansoft.test.enums.ServiceRequestStatus;
import com.cyberiansoft.test.vnextbo.steps.servicerequests.VNextBOSRTableSteps;
import com.cyberiansoft.test.vnextbo.utils.VNextBOAlertMessages;
import org.testng.Assert;

public class VNextBOSRTableValidations {

    private final static String ALL_ACTIVE = "All Active (all not closed)";

    public static boolean isNotFoundNotificationDisplayed() {
        return VNextBOSRTableSteps.getEmptyTableNotification().equals(VNextBOAlertMessages.NO_RECORDS_FOUND);
    }

    public static void verifyNotFoundNotificationIsDisplayed() {
        Assert.assertEquals(VNextBOSRTableSteps.getEmptyTableNotification(), VNextBOAlertMessages.NO_RECORDS_FOUND,
                "'No records found' notification hasn't been displayed");
    }

    public static void verifyEitherNotificationOrSRsListIsDisplayed() {
        if (VNextBOSRTableSteps.getSRListSize() == 0) {
            verifyNotFoundNotificationIsDisplayed();
        } else {
            Assert.assertTrue(VNextBOSRTableSteps.getSRListSize() > 0, "The SRs haven't been displayed");
        }
    }

    public static void verifySRContainingValueIsDisplayed(String actual, String expected) {
        Assert.assertTrue(actual.contains(expected), "The SR hasn't been found");
    }

    public static void verifySRStatusIsNotClosed(String actual) {
        Assert.assertNotEquals(actual, ServiceRequestStatus.CLOSED,
                "The SR status has been '" + ServiceRequestStatus.CLOSED + "'");
    }

    public static void verifySRStatusIsDisplayed(String actual, String expected) {
        if (expected.equals(ALL_ACTIVE)) {
            verifySRStatusIsNotClosed(actual);
        } else {
            Assert.assertEquals(actual, expected, "The SR status hasn't been found");
        }
    }

    public static void verifyMoreServiceRequestsHaveBeenLoaded(int previousSRListSize) {
        Assert.assertTrue(VNextBOSRTableSteps.getSRListSize() > previousSRListSize,
                "The SR list size han't been increased after clicking the 'Load 20 more items' button");
    }
}
