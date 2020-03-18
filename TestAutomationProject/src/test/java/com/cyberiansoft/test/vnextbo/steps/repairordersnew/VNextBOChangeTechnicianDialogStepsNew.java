package com.cyberiansoft.test.vnextbo.steps.repairordersnew;

import com.cyberiansoft.test.baseutils.BusyWait;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBOChangeTechnicianDialogNew;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class VNextBOChangeTechnicianDialogStepsNew {

    public static void setVendor(String vendor) {

        VNextBOChangeTechnicianDialogNew changeTechnicianDialog = new VNextBOChangeTechnicianDialogNew();
        WaitUtilsWebDriver.waitABit(3000);
        BusyWait.create(10000, 500, __ -> changeTechnicianDialog.getVendorDropDown().isEnabled());
        Utils.clickWithJS(changeTechnicianDialog.getVendorDropDown());
        BusyWait.create(10000, 500, __ -> changeTechnicianDialog.dropDownOption(vendor).isEnabled());
        Utils.clickWithJS(changeTechnicianDialog.dropDownOption(vendor));
        WaitUtilsWebDriver.waitABit(2000);
    }

    public static void setTechnician(String technician) {

        VNextBOChangeTechnicianDialogNew changeTechnicianDialog = new VNextBOChangeTechnicianDialogNew();
        Utils.clickElement(changeTechnicianDialog.getTechnicianDropDown());
        WaitUtilsWebDriver.getShortWait().until(ExpectedConditions.elementToBeClickable(changeTechnicianDialog.dropDownOption(technician)));
        Utils.clickWithJS(changeTechnicianDialog.dropDownOption(technician));
    }

    public static void changeTechnicianAndNotSaveXIcon(String vendor, String technician) {

        setVendor(vendor);
        setTechnician(technician);
        Utils.clickElement(new VNextBOChangeTechnicianDialogNew().getCloseDialogXIcon());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void changeTechnicianAndNotSaveCancelButton(String vendor, String technician) {

        setVendor(vendor);
        setTechnician(technician);
        Utils.clickElement(new VNextBOChangeTechnicianDialogNew().getCancelButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void changeTechnicianAndSave(String vendor, String technician) {

        setVendor(vendor);
        setTechnician(technician);
        Utils.clickElement(new VNextBOChangeTechnicianDialogNew().getOkButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }
}
