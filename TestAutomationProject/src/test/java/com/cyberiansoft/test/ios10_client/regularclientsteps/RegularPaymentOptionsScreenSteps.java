package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.paymentsscreens.RegularPaymentOptionsScreen;

public class RegularPaymentOptionsScreenSteps {

    public static void closePaymentOptions() {
        RegularPaymentOptionsScreen paymentOptionsScreen = new RegularPaymentOptionsScreen();
        paymentOptionsScreen.clickPaymentOptionsCloseButton();
    }
}
