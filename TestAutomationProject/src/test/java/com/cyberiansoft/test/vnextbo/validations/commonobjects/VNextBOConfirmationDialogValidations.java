package com.cyberiansoft.test.vnextbo.validations.commonobjects;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.commonobjects.VNextBOConfirmationDialog;

public class VNextBOConfirmationDialogValidations {

    public static boolean isConfirmationDialogOpened(boolean expected) {
        return WaitUtilsWebDriver.elementShouldBeVisible(new VNextBOConfirmationDialog().getConfirmDialog(), expected, 4);
    }
}
