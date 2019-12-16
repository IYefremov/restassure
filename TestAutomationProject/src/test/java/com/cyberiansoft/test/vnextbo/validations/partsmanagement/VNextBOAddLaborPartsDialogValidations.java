package com.cyberiansoft.test.vnextbo.validations.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOAddLaborPartsDialog;
import org.testng.Assert;

public class VNextBOAddLaborPartsDialogValidations {

    public static void verifyDialogIsDisplayed(boolean dialogDisplayed) {

        if (dialogDisplayed) Assert.assertTrue(Utils.isElementDisplayed(new VNextBOAddLaborPartsDialog().getDialogContent()),
                "Dialog hasn't been displayed");
        else Assert.assertTrue(Utils.isElementNotDisplayed(new VNextBOAddLaborPartsDialog().getDialogContent()),
                "Dialog hasn't been displayed");
    }

    public static void verifyClearLaborServiceFieldIconIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOAddLaborPartsDialog().getClearServiceFieldIcon()),
                "Clear icon hasn't been displayed");
    }

    public static void verifyLaborServiceDropDownIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOAddLaborPartsDialog().getLaborServicesDropDown()),
                "Dropdown hasn't been displayed");
    }

    public static void verifyLaborServiceFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBOAddLaborPartsDialog().getSelectLaborServiceField()), expectedValue,
                "Labor service field has contained incorrect value");
    }
}
