package com.cyberiansoft.test.vnextbo.steps.termsconditionspolicy;

import com.cyberiansoft.test.vnextbo.interactions.VNextBOFooterPanelInteractions;
import com.cyberiansoft.test.vnextbo.interactions.termsconditionspolicy.VNextBOTermsAndConditionsDialogInteractions;
import com.cyberiansoft.test.vnextbo.validations.termsconditionspolicy.VNextBOTermsAndConditionsDialogValidations;

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
