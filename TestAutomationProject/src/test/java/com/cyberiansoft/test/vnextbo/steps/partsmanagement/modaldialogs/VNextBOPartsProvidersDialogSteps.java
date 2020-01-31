package com.cyberiansoft.test.vnextbo.steps.partsmanagement.modaldialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs.VNextBOPartsProvidersDialogInteractions;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs.VNextBOPartsProvidersRequestFormDialogValidations;
import org.testng.Assert;

public class VNextBOPartsProvidersDialogSteps {

    public static void openRequestFormDialogWithGetNewQuoteButton(String provider) {
        VNextBOPartsProvidersDialogInteractions.clickGetNewQuoteButton(provider);
        Assert.assertTrue(VNextBOPartsProvidersRequestFormDialogValidations.isRequestFormDialogOpened(),
                "The request form dialog hasn't been opened");
    }

    public static String openStore(String provider) {
        WaitUtilsWebDriver.waitABit(1000);
        final String mainWindow = DriverBuilder.getInstance().getDriver().getWindowHandle();
        VNextBOPartsProvidersDialogInteractions.clickGetNewQuoteButton(provider);
        WaitUtilsWebDriver.waitForNewTab();
        Utils.getNewTab(mainWindow);
        return mainWindow;
    }

    public static void closePartsProvidersDialog() {
        VNextBOPartsProvidersDialogInteractions.clickCloseButton();
        VNextBOPartsProvidersDialogInteractions.waitForPartsProvidersModalDialogToBeClosed();
    }
}
