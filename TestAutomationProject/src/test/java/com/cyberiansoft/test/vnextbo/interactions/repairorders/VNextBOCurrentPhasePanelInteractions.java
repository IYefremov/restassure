package com.cyberiansoft.test.vnextbo.interactions.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBOCurrentPhasePanel;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.testng.Assert;

import java.util.List;
import java.util.stream.Collectors;

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

    public static void clickCompleteCurrentPhaseOption(String orderNumber) {
        final WebElement option = new VNextBOCurrentPhasePanel().getCompleteCurrentPhaseOption(orderNumber);
        if (WaitUtilsWebDriver.elementShouldBeVisible(option, true, 2)) {
            Utils.clickElement(option);
            waitForCurrentPhasePanelToBeClosed();
            WaitUtilsWebDriver.waitForPageToBeLoaded();
        } else {
            Assert.fail("The 'Complete current phase' option hasn't been displayed");
        }
    }

    public static String clickRandomPhaseStatusOption(String orderNumber) {
        List<WebElement> services = new VNextBOCurrentPhasePanel().getChangePhaseStatusOptions(orderNumber);
        final int index = RandomUtils.nextInt(0, services.size());
        final WebElement service = services.get(index);
        Utils.clickElement(service);
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        return Utils.getText(service).trim();
    }

    public static void clickStartPhaseServices(String orderNumber) {
        final WebElement option = new VNextBOCurrentPhasePanel().getStartPhaseServicesOption(orderNumber);
        if (WaitUtilsWebDriver.elementShouldBeVisible(option, true, 2)) {
            Utils.clickElement(option);
            waitForCurrentPhasePanelToBeClosed();
            WaitUtilsWebDriver.waitForPageToBeLoaded();
        } else {
            Assert.fail("The 'Start phase services' option hasn't been displayed");
        }
    }

    public static void waitForCurrentPhasePanelToBeClosed() {
        WaitUtilsWebDriver.getWebDriverWait(3)
                .until((ExpectedCondition<Boolean>) driver ->
                        !Utils.isElementDisplayed(new VNextBOCurrentPhasePanel().getCurrentPhasePanel()));
    }

    public static void waitForCurrentPhasePanelToBeOpened() {
        WaitUtilsWebDriver.getWebDriverWait(3)
                .until((ExpectedCondition<Boolean>) driver ->
                        Utils.isElementDisplayed(new VNextBOCurrentPhasePanel().getCurrentPhasePanel()));
    }

    public static int getDisplayedStartedServicesIconsNumber(String orderNumber) {
        return (int) new VNextBOCurrentPhasePanel().getStartServicesIcons(orderNumber)
                .stream()
                .map(WaitUtilsWebDriver::waitForElementNotToBeStale)
                .filter(Utils::isElementDisplayed)
                .count();
    }

    public static int getDisplayedCompletedServicesIconsNumber(String orderNumber) {
        return (int) new VNextBOCurrentPhasePanel().getCompleteServicesIcons(orderNumber)
                .stream()
                .map(WaitUtilsWebDriver::waitForElementNotToBeStale)
                .filter(Utils::isElementDisplayed)
                .count();
    }

    public static List<String> getStartedServicesValues(String orderNumber) {
        return Utils.getText(new VNextBOCurrentPhasePanel().getChangePhaseStatusOptions(orderNumber))
                .stream()
                .map(String::trim)
                .collect(Collectors.toList());
    }

    public static List<String> getServices() {
        return Utils.getText(new VNextBOCurrentPhasePanel().getServices())
                .stream()
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
