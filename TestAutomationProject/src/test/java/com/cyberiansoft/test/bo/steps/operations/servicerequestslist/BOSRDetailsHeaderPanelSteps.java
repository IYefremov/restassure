package com.cyberiansoft.test.bo.steps.operations.servicerequestslist;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.pageobjects.webpages.operations.servicerequestslist.BOSRDetailsHeaderPanel;

public class BOSRDetailsHeaderPanelSteps {

    public static void waitForButtonsBlockToBeDisplayed() {
        WaitUtilsWebDriver.waitForVisibility(new BOSRDetailsHeaderPanel().getButtonsBlock());
    }
}
