package com.cyberiansoft.test.vnextbo.interactions.general;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.commonobjects.VNextBOConfirmationDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class VNextBOConfirmationDialogInteractions {

    public static void clickInvoiceNoButton() {
        clickModalDialogButton(new VNextBOConfirmationDialog().getInvoiceNoButton());
    }

    public static void clickYesButton() {
        clickModalDialogButton(new VNextBOConfirmationDialog().getYesButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded(5);
    }

    public static void clickNoButton() {
        clickModalDialogButton(new VNextBOConfirmationDialog().getNoButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void clickInvoiceYesButton() {
        clickConfirmButton();
    }

    public static void clickConfirmButton() {
        clickModalDialogButton(new VNextBOConfirmationDialog().getConfirmButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void clickInvoiceCloseButton() {
        clickModalDialogButton(new VNextBOConfirmationDialog().getCloseButton());
        WaitUtilsWebDriver.waitABit(2000);
    }

    public static void closeDialogWithXIcon() {
        clickModalDialogButton(new VNextBOConfirmationDialog().getCloseButton());
    }

    public static void clickModalDialogButton(WebElement button) {
        final By dialog = By.id("dialogModal");
        WaitUtilsWebDriver.elementShouldBeVisible(dialog, true, 2);
        Utils.clickElement(button);
        WaitUtilsWebDriver.elementShouldBeVisible(dialog, false, 3);
    }

    public static String getConfirmationDialogMessage() {
        final VNextBOConfirmationDialog dialog = new VNextBOConfirmationDialog();
        WaitUtilsWebDriver.elementShouldBeVisible(dialog.getConfirmDialog(), true, 2);
        return Utils.getText(dialog.getDialogMessagesList())
                .stream().filter(message -> !message.isEmpty()).findFirst().orElse("");
    }
}
