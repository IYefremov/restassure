package com.cyberiansoft.test.vnext.steps.commonobjects.dialogs;

import com.cyberiansoft.test.vnext.screens.commonobjects.dialogs.VNextWarningDialog;

public class WarningDialogSteps {

    public static void clickDontSaveButton() {

        new VNextWarningDialog().getDontSaveButton().click();
    }

    public static void clickSaveButton() {

        new VNextWarningDialog().getSaveButton().click();
    }

    public static void clickYesButton() {

        new VNextWarningDialog().getYesButton().click();
    }

    public static void clickOkButton() {

        new VNextWarningDialog().getOkButton().click();
    }
}
