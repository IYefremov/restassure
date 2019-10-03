package com.cyberiansoft.test.ios10_client.regularvalidations;

import com.cyberiansoft.test.ios10_client.utils.Helpers;
import org.testng.Assert;

public class RegularAlertValidations {

    public static void acceprAlertAndVerifyAlertMessage(String expectedAlertMessage) {
        Assert.assertEquals(Helpers.getAlertTextAndAccept(), expectedAlertMessage);
    }
}
