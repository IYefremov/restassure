package com.cyberiansoft.test.ios10_client.regularvalidations;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.paymentsscreens.RegularPaymentScreen;
import org.testng.Assert;

public class RegularPaymentScreenValidations {

    public static void verifyCashCheckAmountValue(String expectedCashCheckAmountValue) {
        RegularPaymentScreen paymentScreen = new RegularPaymentScreen();
        Assert.assertEquals(paymentScreen.getCashCheckAmountValue(), expectedCashCheckAmountValue);
    }
}
