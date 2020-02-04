package com.cyberiansoft.test.vnextbo.validations.commonobjects;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.commonobjects.VNextBOToasterNotification;
import org.testng.Assert;

public class VNextBOToasterNotificationValidations {

    public static void verifyMessageTextIsCorrect(String expectedText) {
        VNextBOToasterNotification notification = new VNextBOToasterNotification();
        WaitUtilsWebDriver.waitForVisibility(notification.getMessage());
        String actualNotificationText = Utils.getText(notification.getMessage());
        Assert.assertEquals(actualNotificationText, expectedText,
                "Notification message hasn't been correct");
    }
}
