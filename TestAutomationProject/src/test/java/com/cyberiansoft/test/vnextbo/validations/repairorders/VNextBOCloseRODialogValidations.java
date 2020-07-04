package com.cyberiansoft.test.vnextbo.validations.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBOCloseRODialog;

public class VNextBOCloseRODialogValidations {

    public static boolean isCloseRODialogDisplayed() {
        return Utils.isElementDisplayed(new VNextBOCloseRODialog().getCloseROModal());
    }

    public static boolean isCloseRODialogClosed() {
        return Utils.isElementNotDisplayed(new VNextBOCloseRODialog().getCloseROModal(), 5);
    }
}
