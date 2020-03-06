package com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs.VNextBOPartsProvidersRequestFormDialogInteractions;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.modaldialogs.VNextBOPartsProvidersRequestFormDialog;
import com.cyberiansoft.test.vnextbo.utils.VNextBOAlertMessages;
import org.testng.Assert;

import java.util.List;

public class VNextBOPartsProvidersRequestFormDialogValidations {

    public static boolean isRequestFormDialogOpened() {
        return Utils.isElementWithAttributeContainingValueDisplayed(new VNextBOPartsProvidersRequestFormDialog()
                        .getRequestFormDialog(),
                "style", "display: block;", 0);
    }

    public static boolean isRequestQuoteButtonDisabled() {
        return Utils.isElementWithAttributeContainingValueDisplayed(new VNextBOPartsProvidersRequestFormDialog()
                        .getRequestQuoteButton(),
                "disabled", "true", 0);
    }

    public static boolean isRequestFormDialogMessageDisplayed() {
        return !VNextBOPartsProvidersRequestFormDialogInteractions.getRequestFormDialogMessage().isEmpty();
    }

    public static void verifyNoPartsToOrderMessageHasBeenDisplayed() {
        Assert.assertTrue(isRequestFormDialogMessageDisplayed(), "The warning message hasn't been displayed");
        Assert.assertEquals(VNextBOPartsProvidersRequestFormDialogInteractions.getRequestFormDialogMessage(),
                VNextBOAlertMessages.NO_PARTS_TO_ORDER, "The message hasn't been shown correctly");
    }

    public static void verifyRequestQuoteButtonIsDisabled() {
        Assert.assertTrue(isRequestQuoteButtonDisabled(), "The request quote button is not disabled");
    }

    public static void verifyVinIsDisplayedInTitle(String vin, String title) {
        Assert.assertEquals(vin, VNextBOPartsProvidersRequestFormDialogInteractions.getVinFromTitle(title),
                "The request form dialog title doesn't contain the VIN number");
    }

    public static void verifyCarInfoIsDisplayedInTitle(String carInfo, String title) {
        Assert.assertTrue(title.contains(carInfo), "The request form dialog title doesn't contain the vehicle info");
    }

    public static void verifyPartsAreDisplayed(List<String> detailsPanelPartNamesByStatus) {
        final List<String> requestFormPartNames = VNextBOPartsProvidersRequestFormDialogInteractions.getPartNamesList();
        Assert.assertTrue(detailsPanelPartNamesByStatus.containsAll(requestFormPartNames),
                "The parts names on the PM page are not displayed properly in the request form dialog");
        Assert.assertEquals(detailsPanelPartNamesByStatus.size(), requestFormPartNames.size(),
                "The number of parts on the PM page and in the request form dialog differs");
    }
}