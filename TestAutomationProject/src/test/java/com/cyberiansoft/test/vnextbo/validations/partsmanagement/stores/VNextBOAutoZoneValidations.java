package com.cyberiansoft.test.vnextbo.validations.partsmanagement.stores;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.testng.Assert;

public class VNextBOAutoZoneValidations {

    public static void verifyAutoZonePageIsOpened(String title) {
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        Boolean opened = false;
        try {
            opened = WaitUtilsWebDriver.getWait().until((ExpectedCondition<Boolean>) driver ->
                    DriverBuilder.getInstance().getDriver().getTitle().contains(title));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertTrue(opened, "The 'AutoZone' page " + title + " store hasn't been opened");
    }
}
