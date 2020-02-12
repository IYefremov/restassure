package com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOAddNewPartDialog;

public class VNextBOAddNewPartDialogInteractions {

    public static int getPartsListSize() {
        try {
            final VNextBOAddNewPartDialog addNewPartDialog = new VNextBOAddNewPartDialog();
            WaitUtilsWebDriver.waitForVisibilityOfAllOptionsIgnoringException(addNewPartDialog.getPartsList(), 2);
            return addNewPartDialog.getPartsList().size();
        } catch (Exception e) {
            return 0;
        }
    }

    public static String getSelectedPartsCounter() {
        return Utils.getText(new VNextBOAddNewPartDialog().getSelectedPartsCounter());
    }

    public static void clickSelectAllPartsButton() {
        Utils.clickElement(new VNextBOAddNewPartDialog().getSelectAllPartsButton());
    }

    public static void clickUnSelectAllPartsButton() {
        Utils.clickElement(new VNextBOAddNewPartDialog().getUnSelectAllPartsButton());
    }

    public static void clickSubmitButton() {
        Utils.clickElement(new VNextBOAddNewPartDialog().getSubmitButton());
    }
}
