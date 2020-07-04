package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.vnext.screens.VNextApproveScreen;
import org.junit.Assert;

public class ApproveValidations {

    public static void verifyApprovePriceValue(String expectedPrice) {
        VNextApproveScreen approveScreen = new VNextApproveScreen();
        Assert.assertEquals(approveScreen.getApprovePriceValue(), expectedPrice);
    }

    public static void verifyClearButtonVisible(boolean isVisible) {
        VNextApproveScreen approveScreen = new VNextApproveScreen();
        if (isVisible)
            Assert.assertTrue(approveScreen.isClearButtonVisible());
        else
            Assert.assertFalse(approveScreen.isClearButtonVisible());
    }
}
