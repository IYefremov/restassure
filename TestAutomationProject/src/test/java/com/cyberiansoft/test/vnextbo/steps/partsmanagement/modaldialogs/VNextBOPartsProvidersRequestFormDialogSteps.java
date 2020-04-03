package com.cyberiansoft.test.vnextbo.steps.partsmanagement.modaldialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs.VNextBOPartsProvidersRequestFormDialogInteractions;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs.VNextBOPartsProvidersRequestFormDialog;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs.VNextBOPartsProvidersDialogValidations;
import org.testng.Assert;

public class VNextBOPartsProvidersRequestFormDialogSteps {

    public static void getQuotesForFirstParts(int partsNumber) {
        VNextBOPartsProvidersRequestFormDialogInteractions.clickFirstPartCheckboxes(partsNumber);
        requestQuote();
    }

    public static void requestQuote() {
        Utils.clickElement(new VNextBOPartsProvidersRequestFormDialog().getRequestQuoteButton());
        Assert.assertTrue(VNextBOPartsProvidersDialogValidations.isPartsProvidersModalDialogOpened(),
                "The Parts Providers modal dialog hasn't been displayed");
    }
}
