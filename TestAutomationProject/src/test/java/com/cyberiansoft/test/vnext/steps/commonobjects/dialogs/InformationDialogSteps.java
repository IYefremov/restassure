package com.cyberiansoft.test.vnext.steps.commonobjects.dialogs;

import com.cyberiansoft.test.vnext.screens.commonobjects.dialogs.VNextInformationDialog;

public class InformationDialogSteps {

    public static void clickOkButton() {

        try {
            new VNextInformationDialog().getOkButton().click();
        } catch (Exception ex) {
        }

    }
}
