package com.cyberiansoft.test.bo.steps.operations.servicerequestslist;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.pageobjects.webpages.operations.servicerequestslist.BOSRDetailsPanel;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BOSRDetailsPanelSteps {

    public static void waitForDetailsPanelToBeOpened() {
        try {
            WaitUtilsWebDriver.getWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
                    new BOSRDetailsPanel().getDetailsPanelFrame()));
        } catch (TimeoutException | StaleElementReferenceException e) {
            WaitUtilsWebDriver.waitABit(2000);
        }
    }
}
