package com.cyberiansoft.test.vnextbo.steps.dialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOUserProfileDialog;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class VNextBOUserProfileDialogSteps
{
    public static void closeDialog() {

        WebElement closeButton = new VNextBOUserProfileDialog().getXButton();
        Utils.clickElement(closeButton);
        WaitUtilsWebDriver.getWebDriverWait(3).until(ExpectedConditions.invisibilityOf(closeButton));
    }
}
