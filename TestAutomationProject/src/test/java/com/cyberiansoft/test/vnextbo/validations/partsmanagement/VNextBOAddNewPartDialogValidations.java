package com.cyberiansoft.test.vnextbo.validations.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOAddNewPartDialog;
import org.testng.Assert;

public class VNextBOAddNewPartDialogValidations {

    private static VNextBOAddNewPartDialog addNewPartDialog;

    static {
        addNewPartDialog = new VNextBOAddNewPartDialog();
    }

    public static void verifyDialogIsDisplayed(boolean dialogDisplayed) {

        WaitUtilsWebDriver.elementShouldBeVisible(addNewPartDialog.getDialogContent(), dialogDisplayed, 5);
        Assert.assertEquals(Utils.isElementDisplayed(addNewPartDialog.getDialogContent()), dialogDisplayed,
                "Add new part dialog hasn't been displayed");
    }

    public static void verifyServiceFieldIsCorrect(String serviceName) {

        Assert.assertEquals(Utils.getInputFieldValue(addNewPartDialog.getServiceField()), serviceName,
                "Service field has contained incorrect value");
    }

    public static void verifySelectedPartsCounterValueIsCorrect(String expectedCounterValue) {

        Assert.assertEquals(Utils.getText(addNewPartDialog.getSelectedPartsCounter()), expectedCounterValue,
                "Selected parts counter value hasn't been correct");
    }
}
