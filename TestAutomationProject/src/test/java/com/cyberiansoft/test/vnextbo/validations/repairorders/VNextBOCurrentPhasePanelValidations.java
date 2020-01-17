package com.cyberiansoft.test.vnextbo.validations.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBOCurrentPhasePanel;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;

public class VNextBOCurrentPhasePanelValidations {

    public static boolean areStartServicesIconsDisplayedForWO(String orderNumber) {
        final List<WebElement> startServicesIcons = new VNextBOCurrentPhasePanel().getStartServicesIcons(orderNumber);
        WaitUtilsWebDriver.getWebDriverWait(4).until((ExpectedCondition<Boolean>) driver ->
                startServicesIcons.size() > 0);
        return startServicesIcons
                .stream()
                .anyMatch(e -> WaitUtilsWebDriver.elementShouldBeVisible(e, true, 3));
    }

    public static boolean areCompleteServicesIconsDisplayedForWO(String orderNumber) {
        final List<WebElement> completeServicesIcons = new VNextBOCurrentPhasePanel().getCompleteServicesIcons(orderNumber);
        WaitUtilsWebDriver.getWebDriverWait(4).until((ExpectedCondition<Boolean>) driver ->
                completeServicesIcons.size() > 0);
        return completeServicesIcons
                .stream()
                .anyMatch(e -> WaitUtilsWebDriver.elementShouldBeVisible(e, true, 3));
    }

    public static boolean isStartPhaseServicesOptionDisplayed(String orderNumber) {
        final WebElement option = new VNextBOCurrentPhasePanel().getStartPhaseServicesOption(orderNumber);
        return WaitUtilsWebDriver.elementShouldBeVisible(option, true, 2);
    }

    public static boolean isCurrentPhasePanelOpened() {
        final WebElement option = new VNextBOCurrentPhasePanel().getCurrentPhasePanel();
        return Utils.isElementDisplayed(option);
    }

    public static boolean isCurrentPhasePanelClosed() {
        final WebElement option = new VNextBOCurrentPhasePanel().getCurrentPhasePanel();
        return !Utils.isElementDisplayed(option);
    }
}
