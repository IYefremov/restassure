package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.vnext.screens.VNextEmailScreen;

public class EmailSteps {

    public static void sendEmail(String emailAddress) {
        VNextEmailScreen emailScreen = new VNextEmailScreen();
        emailScreen.sentToEmailAddress(emailAddress);
        emailScreen.sendEmail();
    }
}
