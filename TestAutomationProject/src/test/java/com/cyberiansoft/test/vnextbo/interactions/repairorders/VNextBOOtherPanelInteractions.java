package com.cyberiansoft.test.vnextbo.interactions.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBOOtherPanel;
import org.openqa.selenium.WebElement;

public class VNextBOOtherPanelInteractions {

    public static void waitForOtherPanelToBeOpened(String orderNumber) {
        final WebElement otherPanel = new VNextBOOtherPanel().getOtherPanel(orderNumber);
        WaitUtilsWebDriver.waitForElementNotToBeStale(otherPanel);
        WaitUtilsWebDriver.elementShouldBeVisible(otherPanel, true, 2);
    }

    public static void waitForOtherPanelToBeClosed(String orderNumber) {
        final WebElement otherPanel = new VNextBOOtherPanel().getOtherPanel(orderNumber);
        WaitUtilsWebDriver.elementShouldBeVisible(otherPanel, false, 2);
    }

    private static void clickOtherPanelButton(String orderNumber, WebElement button) {
        Utils.clickElement(button);
        waitForOtherPanelToBeClosed(orderNumber);
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    private static void clickPriorityButton(String orderNumber, WebElement priorityButton) {
        clickOtherPanelButton(orderNumber, priorityButton);
    }

    public static void clickFlagButton(String orderNumber, String flagColor) {
        clickOtherPanelButton(orderNumber, new VNextBOOtherPanel().getFlagButton(orderNumber, flagColor));
    }

    public static void clickRedPriorityButton(String orderNumber) {
        clickPriorityButton(orderNumber, new VNextBOOtherPanel().getRedPriorityButton(orderNumber));
    }

    public static void clickGreenPriorityButton(String orderNumber) {
        clickPriorityButton(orderNumber, new VNextBOOtherPanel().getGreenPriorityButton(orderNumber));
    }

    public static void clickOrangePriorityButton(String orderNumber) {
        clickPriorityButton(orderNumber, new VNextBOOtherPanel().getOrangePriorityButton(orderNumber));
    }
}
