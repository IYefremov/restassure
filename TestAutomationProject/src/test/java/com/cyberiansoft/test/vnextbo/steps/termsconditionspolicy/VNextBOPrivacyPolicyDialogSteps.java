package com.cyberiansoft.test.vnextbo.steps.termsconditionspolicy;

import com.cyberiansoft.test.vnextbo.interactions.VNextBOFooterPanelInteractions;
import com.cyberiansoft.test.vnextbo.interactions.termsconditionspolicy.VNextBOPrivacyPolicyDialogInteractions;
import com.cyberiansoft.test.vnextbo.validations.termsconditionspolicy.VNextBOPrivacyPolicyDialogValidations;

public class VNextBOPrivacyPolicyDialogSteps {

    public static void openAndRejectPrivacyPolicy() {
        VNextBOFooterPanelInteractions.clickPrivacyPolicyLink();
        VNextBOPrivacyPolicyDialogInteractions.closePrivacyPolicy();
        VNextBOPrivacyPolicyDialogValidations.verifyPrivacyPolicyDialogIsClosed();
    }

    public static void openAndAcceptPrivacyPolicy() {
        VNextBOFooterPanelInteractions.clickPrivacyPolicyLink();
        VNextBOPrivacyPolicyDialogInteractions.scrollPrivacyPolicyDown();
        VNextBOPrivacyPolicyDialogInteractions.acceptPrivacyPolicy();
        VNextBOPrivacyPolicyDialogValidations.verifyPrivacyPolicyDialogIsClosed();
    }
}
