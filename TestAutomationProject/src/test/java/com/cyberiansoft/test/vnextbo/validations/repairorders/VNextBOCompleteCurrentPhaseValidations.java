package com.cyberiansoft.test.vnextbo.validations.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBOCompleteCurrentPhaseDialog;

public class VNextBOCompleteCurrentPhaseValidations {

    public static boolean isCompleteCurrentPhaseDialogDisplayed() {
        return Utils.isElementDisplayed(new VNextBOCompleteCurrentPhaseDialog().getCompleteCurrentPhaseDialog());
    }

    public static boolean isServiceNameGridDisplayed() {
        return Utils.isElementDisplayed(new VNextBOCompleteCurrentPhaseDialog().getServiceNameGrid());
    }

    public static boolean isProblemReasonGridDisplayed() {
        return Utils.isElementDisplayed(new VNextBOCompleteCurrentPhaseDialog().getProblemReasonGrid());
    }

    public static boolean isProblemDescriptionGridDisplayed() {
        return Utils.isElementDisplayed(new VNextBOCompleteCurrentPhaseDialog().getProblemDescriptionGrid());
    }

    public static boolean isActionGridDisplayed() {
        return Utils.isElementDisplayed(new VNextBOCompleteCurrentPhaseDialog().getActionGrid());
    }

    public static boolean isCancelButtonDisplayed() {
        return Utils.isElementDisplayed(new VNextBOCompleteCurrentPhaseDialog().getCancelButton());
    }

    public static boolean isCompleteCurrentPhaseButtonDisplayed() {
        return Utils.isElementDisplayed(new VNextBOCompleteCurrentPhaseDialog().getCompleteCurrentPhaseButton());
    }
}
