package com.cyberiansoft.test.vnextbo.validations.commonobjects;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.commonobjects.VNextBOToasterNotification;
import org.testng.Assert;

public class VNextBOToasterNotificationValidations {

    public static void verifyMessageTextIsCorrect(String expectedText) {
        VNextBOToasterNotification notification = new VNextBOToasterNotification();
        WaitUtilsWebDriver.waitForVisibility(notification.getContent());
        String actualNotificationText = Utils.getText(notification.getContent());
        Assert.assertEquals(actualNotificationText, expectedText,
                "Notification message hasn't been correct");
    }

    public static void verifyMessageContainsText(String expectedText) {
        VNextBOToasterNotification notification = new VNextBOToasterNotification();
        String actualNotificationText = Utils.getText(notification.getMessage());
        Assert.assertTrue(actualNotificationText.contains(expectedText),
                "The notification message '" + actualNotificationText + "' doesn't contain '" + expectedText + " text");
    }
}
