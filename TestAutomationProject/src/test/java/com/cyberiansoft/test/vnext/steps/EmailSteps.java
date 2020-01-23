package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.vnext.screens.VNextEmailScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;

public class EmailSteps {

    public static void sendEmail(String emailAddress) {
        VNextEmailScreen emailScreen = new VNextEmailScreen();
        setToMailAddressField(emailAddress);
        emailScreen.sendEmail();
    }

    public static void sendMails(String emailAddress, String ccMailAddress, String bccMailAddress) {
        setToMailAddressField(emailAddress);
        setToCCMailAddressField(ccMailAddress);
        setToBCCMailAddressField(bccMailAddress);
        clickSendEmails();
    }

    public static void setToMailAddressField(String emailAddress) {
        VNextEmailScreen emailScreen = new VNextEmailScreen();
        emailScreen.sentToEmailAddress(emailAddress);
    }

    public static void setToCCMailAddressField(String emailAddress) {
        VNextEmailScreen emailScreen = new VNextEmailScreen();
        emailScreen.sentToCCEmailAddress(emailAddress);
    }

    public static void setToBCCMailAddressField(String emailAddress) {
        VNextEmailScreen emailScreen = new VNextEmailScreen();
        emailScreen.sentToBCCEmailAddress(emailAddress);
    }

    public static void clickSendEmails() {
        VNextEmailScreen emailScreen = new VNextEmailScreen();
        emailScreen.clickSendEmailsButton();
    }
}
