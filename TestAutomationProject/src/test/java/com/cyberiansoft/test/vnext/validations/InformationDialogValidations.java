package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import org.testng.Assert;

public class InformationDialogValidations {

    public static void clickOKAndVerifyMessage(String expectedMessage) {
        VNextInformationDialog informationDialog = new VNextInformationDialog();
        String msg = informationDialog.clickInformationDialogOKButtonAndGetMessage();
        Assert.assertEquals(msg, expectedMessage);
    }
}
