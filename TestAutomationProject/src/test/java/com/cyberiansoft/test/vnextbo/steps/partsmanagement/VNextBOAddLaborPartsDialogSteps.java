package com.cyberiansoft.test.vnextbo.steps.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOAddLaborPartsDialog;

public class VNextBOAddLaborPartsDialogSteps {

    private static void setLaborServiceField(String laborServiceName) {

        VNextBOAddLaborPartsDialog laborPartsDialog = new VNextBOAddLaborPartsDialog();
        Utils.clearAndType(laborPartsDialog.getSelectLaborServiceField(), laborServiceName);
        Utils.selectOptionInDropDownWithJs(laborPartsDialog.getLaborServicesDropDown(),
                laborPartsDialog.serviceDropDownOption(laborServiceName));
    }

    private static void clickAddLaborButton() {

        Utils.clickElement(new VNextBOAddLaborPartsDialog().getAddLaborButton());
    }

    public static void addLaborServiceToPart(String laborServiceName) {

        setLaborServiceField(laborServiceName);
        clickAddLaborButton();
        WaitUtilsWebDriver.waitForLoading();
    }
}
