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

    private VNextBOConfirmationDialog confirmationDialog;

    public VNextBOConfirmationDialogInteractions() {
        confirmationDialog = new VNextBOConfirmationDialog();
    }

    public void clickNoButton() {
        clickModalDialogButton(confirmationDialog.getConfirmDialog()
                .findElement(By.xpath(".//button[@data-automation-id='modalCancelButton']")));
    }

    public void clickInvoiceNoButton() {
        clickModalDialogButton(confirmationDialog.getInvoiceNoButton());
    }

    public void clickYesButton() {
        clickModalDialogButton(confirmationDialog.getConfirmDialog()
                .findElement(By.xpath(".//button[@data-automation-id='modalConfirmButton']")));
        WaitUtilsWebDriver.waitForLoading();
    }

    public void clickInvoiceYesButton() {
        clickConfirmButton();
    }

    public void clickConfirmButton() {
        clickModalDialogButton(confirmationDialog.getConfirmButton());
        WaitUtilsWebDriver.waitForLoading();
    }

    public void clickInvoiceRejectButton() {
        clickModalDialogButton(confirmationDialog.getRejectButton());
        WaitUtilsWebDriver.waitABit(2000);
    }

    private void clickModalDialogButton(WebElement button) {
        Utils.clickElement(button);
        try {
            WaitUtilsWebDriver.waitForInvisibility(By.id("dialogModal"));
        } catch (TimeoutException e) {
            Utils.clickWithJS(button);
        }
    }

    private String getConfirmationDialogMessage() {
        final List<WebElement> dialogMessagesList = confirmationDialog.getDialogMessagesList();
        WaitUtilsWebDriver.waitForVisibilityOfAllOptionsIgnoringException(dialogMessagesList, 7);
        for (WebElement message : dialogMessagesList)
            if (!message.getText().equals("")) {
                return message.getText();
            }
        return null;
    }

    public String clickYesAndGetConfirmationDialogMessage() {
        final String msg = Objects.requireNonNull(getConfirmationDialogMessage());
        clickYesButton();
        return msg;
    }

    public String clickNoAndGetConfirmationDialogMessage() {
        final String msg = Objects.requireNonNull(getConfirmationDialogMessage());
        clickNoButton();
        return msg;
    }
}
