package com.cyberiansoft.test.ios10_client.regularvalidations;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.ios10_client.enums.ReconProMenuItems;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularMenuScreen;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class RegularMenuValidations {

    public static void menuShouldBePresent(ReconProMenuItems menuItem, boolean shouldPresent) {
        RegularMenuScreen menuScreen = new RegularMenuScreen();
        WebDriverWait wait = new WebDriverWait(DriverBuilder.getInstance().getAppiumDriver(), 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.className("XCUIElementTypeScrollView")));
        if (shouldPresent)
            Assert.assertTrue(menuScreen.isMenuItemExists(menuItem));
        else
            Assert.assertFalse(menuScreen.isMenuItemExists(menuItem));

    }
}
