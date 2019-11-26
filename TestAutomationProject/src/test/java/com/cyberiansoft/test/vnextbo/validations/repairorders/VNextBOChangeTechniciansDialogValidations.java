package com.cyberiansoft.test.vnextbo.validations.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOChangeTechnicianDialog;

public class VNextBOChangeTechniciansDialogValidations {

    public static boolean isChangeTechnicianDialogDisplayed() {
        WaitUtilsWebDriver.waitForLoading();
        return Utils.isElementDisplayed(new VNextBOChangeTechnicianDialog().getChangeOrderServicesTechnicianDialog());
    }
}
