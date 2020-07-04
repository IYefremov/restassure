package com.cyberiansoft.test.ios10_client.generalvalidations;

import com.cyberiansoft.test.ios10_client.utils.Helpers;
import org.testng.Assert;

public class AlertsValidations {

    public static void cancelAlertAndValidateAlertMessage(String expectedMessage) {
        Assert.assertEquals(Helpers.getAlertTextAndCancel().replace("\n", " ").replace("  ", " "), expectedMessage);
    }

    public static void acceptAlertAndValidateAlertMessage(String expectedMessage) {
        Assert.assertEquals(Helpers.getAlertTextAndAccept().replace("\n", " ").replace("  ", " "), expectedMessage);
    }

}
