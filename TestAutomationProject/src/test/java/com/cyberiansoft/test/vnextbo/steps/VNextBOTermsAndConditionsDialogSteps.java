package com.cyberiansoft.test.vnextbo.steps;

import com.cyberiansoft.test.vnextbo.interactions.VNextBOFooterPanelInteractions;
import com.cyberiansoft.test.vnextbo.interactions.VNextBOTermsAndConditionsDialogInteractions;
import com.cyberiansoft.test.vnextbo.verifications.VNextBOTermsAndConditionsDialogValidations;

public class VNextBOTermsAndConditionsDialogSteps {

    public static void openAndRejectTermsAndConditions() {
        VNextBOFooterPanelInteractions.clickTermsAndConditionsLink();
        VNextBOTermsAndConditionsDialogInteractions.closeTermsAndConditions();
        VNextBOTermsAndConditionsDialogValidations.verifyTermsAndConditionsDialogIsClosed();
    }

    public static void openAndAcceptTermsAndConditions() {
        VNextBOFooterPanelInteractions.clickTermsAndConditionsLink();
        VNextBOTermsAndConditionsDialogInteractions.scrollTermsAndConditionsDown();
        VNextBOTermsAndConditionsDialogInteractions.acceptTermsAndConditions();
        VNextBOTermsAndConditionsDialogValidations.verifyTermsAndConditionsDialogIsClosed();
    }
}
