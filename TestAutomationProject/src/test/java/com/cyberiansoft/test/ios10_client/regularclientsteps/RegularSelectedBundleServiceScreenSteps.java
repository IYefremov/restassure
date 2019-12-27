package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceBundleScreen;

public class RegularSelectedBundleServiceScreenSteps {

    public static void changeAmountOfBundleService(String newAmount) {
        RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
        selectedServiceBundleScreen.changeAmountOfBundleService(newAmount);
    }
}
