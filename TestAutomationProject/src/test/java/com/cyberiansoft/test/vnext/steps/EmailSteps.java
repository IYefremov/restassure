package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.vnext.screens.VNextEmailScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

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

    public static void clickAddMoreCCEmailsButton() {
        VNextEmailScreen emailScreen = new VNextEmailScreen();
        WaitUtils.getGeneralFluentWait()
                .until(driver -> {
                    emailScreen.getCcEmailPanel().findElement(emailScreen.getAddMoreBtn()).click();
                    return true;
                });
    }

    public static void setSecondToCCMailAddressField(String emailAddress) {
        clickAddMoreCCEmailsButton();
        VNextEmailScreen emailScreen = new VNextEmailScreen();
        WaitUtils.getGeneralFluentWait()
                .until(driver -> {
                    emailScreen.getCcEmailPanel().findElements(emailScreen.getToEmailXpath()).get(1).clear();
                    return true;
                });
        emailScreen.getCcEmailPanel().findElements(emailScreen.getToEmailXpath()).get(1).sendKeys(emailAddress);
    }

    public static void clickRemoveEmailAddressButton() {
        VNextEmailScreen emailScreen = new VNextEmailScreen();
        emailScreen.getCcEmailPanel().findElement(emailScreen.getAddMoreBtn()).click();
        emailScreen.getToEmailPanel().findElement(emailScreen.getRemoveMailBtn()).click();
    }

    public static void clickRemoveCCEmailAddressButton() {
        VNextEmailScreen emailScreen = new VNextEmailScreen();
        WaitUtils.getGeneralFluentWait()
                .until(driver -> {
                    emailScreen.getCcEmailPanel().findElement(emailScreen.getRemoveMailBtn()).click();
                    return true;
                });
    }

    public static void clickRemoveBCCEmailAddressButton() {
        VNextEmailScreen emailScreen = new VNextEmailScreen();
        emailScreen.getBccEmailPanel().findElement(emailScreen.getRemoveMailBtn()).click();
    }
}
