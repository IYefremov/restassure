package com.cyberiansoft.test.vnextbo.validations.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOAddNewServiceMonitorDialog;
import org.openqa.selenium.WebElement;

import java.util.List;

public class VNextBOAddNewServiceMonitorDialogValidations {

    public static boolean isNewServicePopupDisplayed() {
        return Utils.isElementDisplayed(new VNextBOAddNewServiceMonitorDialog().getNewServicePopup());
    }

    public static boolean isPartDescriptionDisplayed(String description) {
        Utils.refreshPage();
        final List<WebElement> partDescriptions = new VNextBOAddNewServiceMonitorDialog().getPartDescriptions();
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(partDescriptions);
        return partDescriptions
                .stream()
                .anyMatch(e -> {
                    final String option = e.getText().replaceFirst(".+(, )", "");
                    System.out.println(option);
                    return option.equals(description);
                });
    }

    public static boolean arePartsOptionsDisplayed() {
        return WaitUtilsWebDriver
                .waitForVisibilityOfAllOptions(new VNextBOAddNewServiceMonitorDialog().getPartsOptions(), 5).isEmpty();
    }
}
