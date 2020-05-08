package com.cyberiansoft.test.vnextbo.steps.partsmanagement.modaldialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs.VNextBOChangePartsDialog;

public class VNextBOChangePartsDialogSteps {

    public static String getSelectStatusFieldOption() {
        return Utils.getText(new VNextBOChangePartsDialog().getSelectStatusFieldOption());
    }

    public static String getSelectStatusErrorNotification() {
        return Utils.getText(new VNextBOChangePartsDialog().getSelectStatusError());
    }

    public static void submit() {
        final VNextBOChangePartsDialog changePartsDialog = new VNextBOChangePartsDialog();
        Utils.clickElement(changePartsDialog.getSubmitButton());
        WaitUtilsWebDriver.waitForInvisibility(changePartsDialog.getChangeStatusDialog(), 3);
        WaitUtilsWebDriver.waitForPageToBeLoaded(1);
    }

    public static void cancel() {
        final VNextBOChangePartsDialog changePartsDialog = new VNextBOChangePartsDialog();
        Utils.clickElement(changePartsDialog.getCancelButton());
        WaitUtilsWebDriver.waitForInvisibility(changePartsDialog.getChangeStatusDialog(), 3);
        WaitUtilsWebDriver.waitForPageToBeLoaded(1);
    }

    public static void setStatus(String status) {
        final VNextBOChangePartsDialog changePartsDialog = new VNextBOChangePartsDialog();
        Utils.clickElement(changePartsDialog.getSelectStatusFieldOption());
        Utils.selectOptionInDropDown(changePartsDialog.getStatusDropDown(),
                changePartsDialog.getStatusDropDownOptions(), status, true);
    }
}
