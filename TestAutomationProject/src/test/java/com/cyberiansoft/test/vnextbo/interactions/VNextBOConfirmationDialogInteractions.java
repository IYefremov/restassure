package com.cyberiansoft.test.vnextbo.interactions;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOConfirmationDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class VNextBOConfirmationDialogInteractions {

    private static VNextBOConfirmationDialog confirmationDialog;

    static {
        confirmationDialog = new VNextBOConfirmationDialog();
    }

    public static void clickInvoiceNoButton() {
        clickModalDialogButton(new VNextBOConfirmationDialog().getInvoiceNoButton());
    }

    public static void clickYesButton() {
        clickModalDialogButton(confirmationDialog.getYesButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void clickInvoiceYesButton() {
        clickConfirmButton();
    }

    public static void clickConfirmButton() {
        clickModalDialogButton(confirmationDialog.getConfirmButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void clickInvoiceCloseButton() {
        clickModalDialogButton(confirmationDialog.getCloseButton());
        WaitUtilsWebDriver.waitABit(2000);
    }

    private static void clickModalDialogButton(WebElement button) {
        Utils.clickElement(button);
        WaitUtilsWebDriver.elementShouldBeVisible(By.id("dialogModal"), false, 5);
    }

    public static String getConfirmationDialogMessage() {
        final VNextBOConfirmationDialog dialog = confirmationDialog;
        WaitUtilsWebDriver.elementShouldBeVisible(dialog.getConfirmDialog(), true, 3);
        return Utils.getText(dialog.getDialogMessagesList())
                .stream().filter(message -> !message.isEmpty()).findFirst().orElse("");
    }
}
