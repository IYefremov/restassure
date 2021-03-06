package com.cyberiansoft.test.vnextbo.steps.dialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOModalDialog;

public class VNextBOModalDialogSteps {

    public static String getDialogHeader() {

        return Utils.getText(new VNextBOModalDialog().dialogHeader());
    }

    public static String getDialogInformationMessage() {

        return Utils.getText(new VNextBOModalDialog().dialogInformationMessage());
    }

    public static void clickOkButton() {

        VNextBOModalDialog modalDialog = new VNextBOModalDialog();
        WaitUtilsWebDriver.waitForElementNotToBeStale(modalDialog.getConfirmOKButton());
        WaitUtilsWebDriver.waitForVisibility(modalDialog.getConfirmOKButton(), 4);
        Utils.clickElement(modalDialog.getConfirmOKButton());
        WaitUtilsWebDriver.waitForInvisibility(modalDialog.getConfirmOKButton(), 4);
    }

    public static void clickCloseButton() {

        VNextBOModalDialog modalDialog = new VNextBOModalDialog();
        WaitUtilsWebDriver.waitForVisibility(modalDialog.getCloseButton(), 4);
        Utils.clickElement(modalDialog.getCloseButton());
        WaitUtilsWebDriver.waitForInvisibility(modalDialog.getCloseButton(), 4);
    }

    public static void clickYesButton() {

        VNextBOModalDialog modalDialog = new VNextBOModalDialog();
        WaitUtilsWebDriver.waitForVisibility(modalDialog.getYesButton(), 4);
        Utils.clickElement(modalDialog.getYesButton());
        WaitUtilsWebDriver.waitForInvisibility(modalDialog.getYesButton(), 4);
    }

    public static void clickNoButton() {

        VNextBOModalDialog modalDialog = new VNextBOModalDialog();
        WaitUtilsWebDriver.waitForVisibility(modalDialog.getNoButton(), 4);
        Utils.clickElement(modalDialog.getNoButton());
        WaitUtilsWebDriver.waitForInvisibility(modalDialog.getNoButton(), 4);
    }

    public static void clickCancelButton() {

        VNextBOModalDialog modalDialog = new VNextBOModalDialog();
        WaitUtilsWebDriver.waitForVisibility(modalDialog.getCancelButton(), 4);
        Utils.clickElement(modalDialog.getCancelButton());
        WaitUtilsWebDriver.waitForInvisibility(modalDialog.getCancelButton(), 4);
    }
}