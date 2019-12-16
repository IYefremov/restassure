package com.cyberiansoft.test.vnextbo.steps.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOAddLaborPartsDialog;

public class VNextBOAddLaborPartsDialogSteps {

    private static void setLaborServiceField(String laborServiceName) {

        VNextBOAddLaborPartsDialog laborPartsDialog = new VNextBOAddLaborPartsDialog();
        Utils.clearAndType(laborPartsDialog.getSelectLaborServiceField(), laborServiceName);
        WaitUtilsWebDriver.waitForLoading();
        Utils.selectOptionInDropDownWithJs(laborPartsDialog.getLaborServicesDropDown(),
                laborPartsDialog.serviceDropDownOption(laborServiceName));
        WaitUtilsWebDriver.waitForLoading();
    }

    private static void clickAddLaborButton() {

        Utils.clickElement(new VNextBOAddLaborPartsDialog().getAddLaborButton());
    }

    public static void populateLaborServiceField(String laborServiceName) {

        Utils.clearAndType(new VNextBOAddLaborPartsDialog().getSelectLaborServiceField(), laborServiceName);
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void addLaborServiceToPart(String laborServiceName) {

        setLaborServiceField(laborServiceName);
        clickAddLaborButton();
        WaitUtilsWebDriver.waitForLoading();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    public static void closeDialogWithXIcon() {

        Utils.clickElement(new VNextBOAddLaborPartsDialog().getXIconCloseButton());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void closeDialogWithCancelButton() {

        Utils.clickElement(new VNextBOAddLaborPartsDialog().getCancelAddingLaborButton());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clearLabourServiceField() {

        Utils.clickElement(new VNextBOAddLaborPartsDialog().getClearServiceFieldIcon());
        WaitUtilsWebDriver.waitForLoading();
    }
}
