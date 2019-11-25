package com.cyberiansoft.test.vnextbo.validations.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOAddNewPartDialog;
import org.testng.Assert;

public class VNextBOAddNewPartDialogValidations {

    public static void verifyDialogIsDisplayed(boolean dialogDisplayed) {

        Assert.assertEquals(Utils.isElementDisplayed(new VNextBOAddNewPartDialog().getDialogContent()), dialogDisplayed,
                "Dialog hasn't been displayed");
    }

    public static void verifyServiceFieldIsCorrect(String serviceName) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBOAddNewPartDialog().getServiceField()), serviceName,
                "Service field has contained incorrect value");
    }

    public static void verifySelectedPartsCounterValueIsCorrect(String expectedCounterValue) {

        Assert.assertEquals(Utils.getText(new VNextBOAddNewPartDialog().getSelectedPartsCounter()), expectedCounterValue,
                "Selected parts counter value hasn't been correct");
    }
}
