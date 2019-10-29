package com.cyberiansoft.test.vnextbo.interactions;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOConfirmationDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Objects;

public class VNextBOConfirmationDialogInteractions {

    public static void clickNoButton() {
        clickModalDialogButton(new VNextBOConfirmationDialog().getConfirmDialog()
                .findElement(By.xpath(".//button[@data-automation-id='modalCancelButton']")));
    }

    public static void clickInvoiceNoButton() {
        clickModalDialogButton(new VNextBOConfirmationDialog().getInvoiceNoButton());
    }

    public static void clickYesButton() {
        clickModalDialogButton(new VNextBOConfirmationDialog().getConfirmDialog()
                .findElement(By.xpath(".//button[@data-automation-id='modalConfirmButton']")));
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickInvoiceYesButton() {
        clickConfirmButton();
    }

    public static void clickConfirmButton() {
        clickModalDialogButton(new VNextBOConfirmationDialog().getConfirmButton());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickInvoiceRejectButton() {
        clickModalDialogButton(new VNextBOConfirmationDialog().getRejectButton());
        WaitUtilsWebDriver.waitABit(2000);
    }

    private static void clickModalDialogButton(WebElement button) {
        Utils.clickElement(button);
        try {
            WaitUtilsWebDriver.waitForInvisibility(By.id("dialogModal"));
        } catch (TimeoutException e) {
            Utils.clickWithJS(button);
        }
    }

    private static String getConfirmationDialogMessage() {
        final List<WebElement> dialogMessagesList = new VNextBOConfirmationDialog().getDialogMessagesList();
        WaitUtilsWebDriver.waitForVisibilityOfAllOptionsIgnoringException(dialogMessagesList, 7);
        for (WebElement message : dialogMessagesList)
            if (!message.getText().equals("")) {
                return message.getText();
            }
        return null;
    }

    public static String clickYesAndGetConfirmationDialogMessage() {
        final String msg = Objects.requireNonNull(getConfirmationDialogMessage());
        clickYesButton();
        return msg;
    }

    public static String clickNoAndGetConfirmationDialogMessage() {
        final String msg = Objects.requireNonNull(getConfirmationDialogMessage());
        clickNoButton();
        return msg;
    }
}
