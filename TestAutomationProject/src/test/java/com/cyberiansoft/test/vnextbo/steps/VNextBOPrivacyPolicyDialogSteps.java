package com.cyberiansoft.test.vnextbo.steps;

import com.cyberiansoft.test.vnextbo.interactions.VNextBOFooterPanelInteractions;
import com.cyberiansoft.test.vnextbo.interactions.VNextBOPrivacyPolicyDialogInteractions;
import com.cyberiansoft.test.vnextbo.verifications.VNextBOPrivacyPolicyDialogValidations;

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
