package com.cyberiansoft.test.vnextbo.validations.services;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.services.VNextBOServiceDialog;
import com.cyberiansoft.test.vnextbo.validations.VNextBOBaseWebPageValidations;
import org.testng.Assert;

public class VNextBOServiceDialogValidations extends VNextBOBaseWebPageValidations {

    public static void verifyErrorMessageIsCorrect(String errorMessage) {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOServiceDialog().getServiceNameErrorMessage()),
                "Error message hasn't been displayed.");
        Assert.assertEquals(Utils.getText(new VNextBOServiceDialog().getServiceNameErrorMessage()), errorMessage,
                "Error message hasn't been correct.");
    }
}