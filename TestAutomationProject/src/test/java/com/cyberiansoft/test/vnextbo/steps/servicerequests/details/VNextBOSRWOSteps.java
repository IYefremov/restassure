package com.cyberiansoft.test.vnextbo.steps.servicerequests.details;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.servicerequests.details.VNextBOSRWO;
import com.cyberiansoft.test.vnextbo.steps.servicerequests.dialogs.VNextBOSRWorkOrdersDialogSteps;

public class VNextBOSRWOSteps {

    public static void openWOEditDialog() {
        final VNextBOSRWO srWOBlock = new VNextBOSRWO();
        WaitUtilsWebDriver.waitForElementNotToBeStale(srWOBlock.getWOEditIcon());
        Utils.clickElement(srWOBlock.getWOEditIcon());
        VNextBOSRWorkOrdersDialogSteps.waitForWOModalDialog();
    }
}
