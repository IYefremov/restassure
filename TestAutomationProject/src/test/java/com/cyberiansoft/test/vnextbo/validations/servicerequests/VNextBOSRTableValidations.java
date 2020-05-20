package com.cyberiansoft.test.vnextbo.validations.servicerequests;

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
}
