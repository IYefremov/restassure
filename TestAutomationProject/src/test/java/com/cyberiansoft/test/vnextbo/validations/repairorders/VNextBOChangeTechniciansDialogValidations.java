package com.cyberiansoft.test.vnextbo.validations.repairorders;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOChangeTechnicianDialog;

public class VNextBOChangeTechniciansDialogValidations {

    public static boolean isChangeTechnicianDialogOpened() {
        return WaitUtilsWebDriver.elementShouldBeVisible(
                new VNextBOChangeTechnicianDialog().getChangeOrderTechnicianDialog(), true, 3);
    }

    public static boolean isChangeTechnicianDialogClosed() {
        return WaitUtilsWebDriver.elementShouldBeVisible(
                new VNextBOChangeTechnicianDialog().getChangeOrderTechnicianDialog(), false, 3);
    }
}
