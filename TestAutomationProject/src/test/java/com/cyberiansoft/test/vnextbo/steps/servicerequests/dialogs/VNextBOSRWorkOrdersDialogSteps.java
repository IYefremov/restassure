package com.cyberiansoft.test.vnextbo.steps.servicerequests.dialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.servicerequests.dialogs.VNextBOSRWorkOrdersDialog;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;

public class VNextBOSRWorkOrdersDialogSteps {

    public static void waitForWOModalDialog() {
        WaitUtilsWebDriver.waitForVisibility(new VNextBOSRWorkOrdersDialog().getWoDialog());
    }

    public static List<String> getWONumbersList() {
        return Utils.getText(new VNextBOSRWorkOrdersDialog().getWoNumbersList());
    }

    public static String getRandomWONumber() {
        return getWONumbersList().get(RandomUtils.nextInt(0, getWONumbersList().size()));
    }
}
