package com.cyberiansoft.test.vnextbo.steps.devicemanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.devicemanagement.VNextBOAuditLogDialog;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;

public class VNextBOAuditLogsDialogSteps extends VNextBOBaseWebPageSteps {

    public static void closeDialog() {

        Utils.clickElement(new VNextBOAuditLogDialog().getCloseDialogXIcon());
    }
}