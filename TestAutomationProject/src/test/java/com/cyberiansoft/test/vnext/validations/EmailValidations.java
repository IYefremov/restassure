package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.vnext.screens.VNextEmailScreen;
import org.testng.Assert;

public class EmailValidations {

    public static void validateToEmailFieldValue(String expectedEmail) {
        VNextEmailScreen emailScreen = new VNextEmailScreen();
        Assert.assertEquals(emailScreen.getToEmailFieldValue(), expectedEmail);
    }

    public static void validateCCEmailFieldValue(String expectedEmail) {
        VNextEmailScreen emailScreen = new VNextEmailScreen();
        Assert.assertEquals(emailScreen.getCCEmailFieldValue(), expectedEmail);
    }

    public static void validateBCCEmailFieldValue(String expectedEmail) {
        VNextEmailScreen emailScreen = new VNextEmailScreen();
        Assert.assertEquals(emailScreen.getBCCEmailFieldValue(), expectedEmail);
    }
}
