package com.cyberiansoft.test.vnextbo.validations.devicemanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.devicemanagement.VNextBOAuditLogDialog;
import org.testng.Assert;

public class VNextBOAuditLogsDialogValidations {

    public static void verifyDialogIsDisplayed() {

        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOAuditLogDialog().getDialogContent()),
                "Modal dialog hasn't been opened");
    }

    public static void verifyDialogIsClosed(VNextBOAuditLogDialog auditLogsDialog) {

        WaitUtilsWebDriver.waitForInvisibility(auditLogsDialog.getDialogContent(), 2);
        Assert.assertFalse(Utils.isElementDisplayed(auditLogsDialog.getDialogContent()),
                "Modal dialog hasn't been closed");
    }
}