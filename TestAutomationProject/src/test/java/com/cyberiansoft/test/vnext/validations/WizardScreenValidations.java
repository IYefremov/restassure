package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextBaseWizardScreen;
import org.testng.Assert;

public class WizardScreenValidations {

    public static void validateTotalPriceValue(String expectedPrice) {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        Assert.assertEquals(baseWizardScreen.getInspectionTotalPriceValue(), expectedPrice);
    }
}
