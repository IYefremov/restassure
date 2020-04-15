package com.cyberiansoft.test.vnext.steps.commonobjects.dialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnext.screens.commonobjects.dialogs.VNextWarningDialog;

public class WarningDialogSteps {

    public static void clickDontSaveButton() {

        Utils.clickElement(new VNextWarningDialog().getDontSaveButton());
    }

    public static void clickSaveButton() {

        Utils.clickElement(new VNextWarningDialog().getSaveButton());
    }
}
