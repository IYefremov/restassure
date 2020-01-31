package com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs.VNextBOPartsProvidersDialog;

public class VNextBOPartsProvidersDialogValidations {

    public static boolean isPartsProvidersModalDialogOpened() {
        return Utils.isElementWithAttributeContainingValueDisplayed(new VNextBOPartsProvidersDialog()
                .getPartsProvidersModal(), "style", "display: block;", 5);
    }
}
