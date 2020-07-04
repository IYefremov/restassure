package com.cyberiansoft.test.vnextbo.validations.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.VNextBOAuditLogDialog;

public class VNextBOAuditLogDialogValidations {

    public static boolean isAuditLogDialogDisplayed() {
        return Utils.isElementDisplayed(new VNextBOAuditLogDialog().getAuditLogDialog(), 10);
    }
}
