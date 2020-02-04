package com.cyberiansoft.test.vnextbo.steps.partsmanagement.modaldialogs;

import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs.VNextBOPartsProvidersRequestFormDialogInteractions;

public class VNextBOPartsProvidersRequestFormDialogSteps {

    public static void getQuotesForFirstParts(int parts) {
        VNextBOPartsProvidersRequestFormDialogInteractions.clickFirstPartCheckboxes(parts);
        VNextBOPartsProvidersRequestFormDialogInteractions.clickRequestQuoteButton();
    }
}
