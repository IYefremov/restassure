package com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs.VNextBOPartsProvidersRequestFormDialogInteractions;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs.VNextBOPartsProvidersRequestFormDialog;

public class VNextBOPartsProvidersRequestFormDialogValidations {

    private static VNextBOPartsProvidersRequestFormDialog partsProvidersDialog;

    static {
        partsProvidersDialog = new VNextBOPartsProvidersRequestFormDialog();
    }

    public static boolean isRequestFormDialogOpened() {
        return Utils.isElementWithAttributeContainingValueDisplayed(partsProvidersDialog.getRequestFormDialog(),
                "style", "display: block;", 5);
    }

    public static boolean isRequestQuoteButtonDisabled() {
        return Utils.isElementWithAttributeContainingValueDisplayed(partsProvidersDialog.getRequestQuoteButton(),
                "disabled", "true", 5);
    }

    public static boolean isRequestFormDialogMessageDisplayed() {
        return !VNextBOPartsProvidersRequestFormDialogInteractions.getRequestFormDialogMessage().isEmpty();
    }
}