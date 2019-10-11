package com.cyberiansoft.test.vnextbo.steps.dialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.driverutils.DriverBuilder;

import com.cyberiansoft.test.vnextbo.screens.VNextBOModalDialog;

public class VNextBOModalDialogSteps {

    public static String getDialogHeader() {
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog(DriverBuilder.getInstance().getDriver());
        return Utils.getText(confirmationDialog.dialogHeader());
    }

    public static String getDialogInformationMessage() {
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog(DriverBuilder.getInstance().getDriver());
        return Utils.getText(confirmationDialog.dialogInformationMessage());
    }

    public static void clickOkButton() {
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(confirmationDialog.confirmOKButton);
    }

    public static void clickCloseButton() {
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(confirmationDialog.closeButton);
    }

    public static void clickYesButton() {
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(confirmationDialog.confirmYesButton);
    }

    public static void clickNoButton() {
        VNextBOModalDialog confirmationDialog = new VNextBOModalDialog(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(confirmationDialog.cancelNoButton);
    }
}