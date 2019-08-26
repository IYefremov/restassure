package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularEmailScreen;

public class RegularEmailScreenSteps {

    public static void sendEmailToAddress(String eMail) {
        RegularEmailScreen emailScreen = new RegularEmailScreen();
        emailScreen.clickAddMailButton();
        emailScreen.enterEmailAddress(eMail);
        emailScreen.clickSendButton();
    }
}
