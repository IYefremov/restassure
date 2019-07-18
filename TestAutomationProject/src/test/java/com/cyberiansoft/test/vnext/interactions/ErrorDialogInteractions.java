package com.cyberiansoft.test.vnext.interactions;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.ErrorDialog;

public class ErrorDialogInteractions {
    public static String getErrorDialogText() {
        ErrorDialog errorDialog = new ErrorDialog();
        WaitUtils.elementShouldBeVisible(errorDialog.getRootElement(), true);
        return errorDialog.getErrorText().getText();
    }

    public static void clickOkButton() {
        ErrorDialog errorDialog = new ErrorDialog();
        WaitUtils.elementShouldBeVisible(errorDialog.getOkButton(), true);
        WaitUtils.click(errorDialog.getOkButton());
    }
}
