package com.cyberiansoft.test.vnextbo.interactions.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBOCurrentPhasePanel;
import org.openqa.selenium.WebElement;

import java.util.List;

public class VNextBOCurrentPhasePanelInteractions {

    public static void completeWorkOrderServiceStatus(String orderNumber, String serviceName) {
        List<WebElement> services = new VNextBOCurrentPhasePanel().getChangePhaseStatusOptions(orderNumber);
        for (WebElement service : services)
            if (service.getText().trim().contains(serviceName)) {
                service.click();
                break;
            }
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void completeCurrentPhase(String orderNumber) {
        final WebElement option = new VNextBOCurrentPhasePanel().getCompleteCurrentPhaseOption(orderNumber);
        if (WaitUtilsWebDriver.elementShouldBeVisible(option, true)) {
            Utils.clickElement(option);
        }
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void startPhaseServices(String orderNumber) {
        final WebElement option = new VNextBOCurrentPhasePanel().getStartPhaseServicesOption(orderNumber);
        if (WaitUtilsWebDriver.elementShouldBeVisible(option, true)) {
            Utils.clickElement(option);
        }
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }
}
