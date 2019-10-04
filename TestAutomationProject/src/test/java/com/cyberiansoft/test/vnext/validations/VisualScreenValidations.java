package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVisualScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.testng.Assert;

public class VisualScreenValidations {
    public static void numberOfMarksShouldBeEqualTo(int expectedMarkCount) {
        VNextVisualScreen visualScreen = new VNextVisualScreen();
        WaitUtils.getGeneralFluentWait().until(driver -> {
            Assert.assertEquals(visualScreen.getNumberOfImageMarkers(), expectedMarkCount);
            return true;
        });
    }
}
