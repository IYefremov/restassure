package com.cyberiansoft.test.vnextbo.verifications.devicemanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.devicemanagement.VNextBOAuditLogDialog;
import org.testng.Assert;

public class VNextBOAuditLogsDialogValidations {

    public static void verifyDialogIsDisplayed(boolean shouldBeDisplayed) {

        VNextBOAuditLogDialog auditLogsDialog = new VNextBOAuditLogDialog();
        if (shouldBeDisplayed) Assert.assertTrue(Utils.isElementDisplayed(auditLogsDialog.getDialogContent()),
                "Modal dialog hasn't been opened");
        else Assert.assertFalse(Utils.isElementDisplayed(auditLogsDialog.getDialogContent()),
                "Modal dialog hasn't been opened");
    }
}