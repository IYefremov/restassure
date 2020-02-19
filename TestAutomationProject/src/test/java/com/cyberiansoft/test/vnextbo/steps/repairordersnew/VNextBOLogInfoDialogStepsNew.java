package com.cyberiansoft.test.vnextbo.steps.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBOLogInfoDialogNew;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

public class VNextBOLogInfoDialogStepsNew {

    public static void openServicesTab() {

        new Actions(DriverBuilder.getInstance().getDriver()).sendKeys(Keys.F12).perform();
        Utils.clickElement(new VNextBOLogInfoDialogNew().getServicesTab());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void closeDialog() {

        Utils.clickElement(new VNextBOLogInfoDialogNew().getCloseDialogButton());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }
}
