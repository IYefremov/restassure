package com.cyberiansoft.test.ios10_client.regularvalidations;

import com.cyberiansoft.test.ios10_client.enums.ReconProMenuItems;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularMenuScreen;
import org.testng.Assert;

public class RegularMenuValidations {

    public static void menuShouldBePresent(ReconProMenuItems menuItem, boolean shouldPresent) {
        RegularMenuScreen menuScreen = new RegularMenuScreen();
        if (shouldPresent)
            Assert.assertTrue(menuScreen.isMenuItemExists(menuItem));
        else
            Assert.assertFalse(menuScreen.isMenuItemExists(menuItem));

    }
}
