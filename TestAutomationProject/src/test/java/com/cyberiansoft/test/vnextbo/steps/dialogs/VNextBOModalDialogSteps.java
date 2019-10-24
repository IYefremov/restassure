package com.cyberiansoft.test.vnextbo.steps.dialogs;

import com.cyberiansoft.test.baseutils.Utils;

import com.cyberiansoft.test.vnextbo.screens.VNextBOModalDialog;

public class VNextBOModalDialogSteps {

    public static String getDialogHeader() {

        return Utils.getText(new VNextBOModalDialog().dialogHeader());
    }

    public static String getDialogInformationMessage() {
        return Utils.getText(new VNextBOModalDialog().dialogInformationMessage());
    }

    public static void clickOkButton() {

        Utils.clickElement(new VNextBOModalDialog().getConfirmOKButton());
    }

    public static void clickCloseButton() {

        Utils.clickElement(new VNextBOModalDialog().getCloseButton());
    }

    public static void clickYesButton() {

        Utils.clickElement(new VNextBOModalDialog().getYesButton());
    }

    public static void clickNoButton() {

        Utils.clickElement(new VNextBOModalDialog().getNoButton());
    }

    public static void clickCancelButton() {

        Utils.clickElement(new VNextBOModalDialog().getCancelButton());
    }
}