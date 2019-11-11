package com.cyberiansoft.test.vnextbo.validations.inspections;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.inspections.VNextBOInspectionsApprovalWebPage;

public class VNextBOInspectionsApprovalPageValidations {

    public static boolean verifyApprovePrintPageButtonIsDisplayed() {
        return Utils.isElementDisplayed(new VNextBOInspectionsApprovalWebPage().getApproveServiceButton());
    }

    public static boolean verifyNotesTextAreaIsDisplayed() {
        return Utils.isElementDisplayed(new VNextBOInspectionsApprovalWebPage().getNotesTextArea());
    }

    public static boolean verifyPrintPageInspectionStatusIsDisplayed() {
        return Utils.isElementDisplayed(new VNextBOInspectionsApprovalWebPage().getInspectionStatus());
    }
}
