package com.cyberiansoft.test.vnext.validations.commonobjects;

import com.cyberiansoft.test.vnext.screens.commonobjects.VNextNothingFoundScreen;
import org.testng.Assert;

public class VNextNothingFoundScreenValidations {

    public static void verifyScreenWasOpenedWithCorrectMessage() {

        VNextNothingFoundScreen nextNothingFoundScreen = new VNextNothingFoundScreen();
        Assert.assertTrue(nextNothingFoundScreen.getRootElement().isDisplayed(),
                "'Nothing found' screen hasn't been displayed");
        Assert.assertEquals(nextNothingFoundScreen.getContentMessage().getText(), "Nothing found",
                "'Nothing found' screen has contained incorrect message");
    }
}
