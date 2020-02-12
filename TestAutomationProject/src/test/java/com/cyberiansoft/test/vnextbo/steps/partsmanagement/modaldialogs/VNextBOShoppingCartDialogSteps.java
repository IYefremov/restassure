package com.cyberiansoft.test.vnextbo.steps.partsmanagement.modaldialogs;

import com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement.VNextBOPartsData;
import com.cyberiansoft.test.vnextbo.interactions.VNextBOConfirmationDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.VNextBOPartsDetailsPanelInteractions;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.modaldialogs.VNextBOShoppingCartDialogInteractions;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.modaldialogs.VNextBOShoppingCartDialogValidations;
import org.testng.Assert;

public class VNextBOShoppingCartDialogSteps {

    public static void completeOrder(VNextBOPartsData partData, String price, String corePrice) {
        VNextBOPartsDetailsPanelInteractions.clickShoppingCartButton();
        Assert.assertTrue(VNextBOShoppingCartDialogValidations.isShoppingCartDialogDisplayed(true),
                "The shopping cart dialog hasn't been opened");
        VNextBOShoppingCartDialogValidations.verifyPriceAndCorePriceByPartName(partData.getPartNames()[0], price, corePrice);

        VNextBOShoppingCartDialogInteractions.selectPartByPartService("AutoZone", partData.getPartNames()[0]);
        VNextBOShoppingCartDialogInteractions.clickOrderButton();
        VNextBOConfirmationDialogInteractions.clickYesButton();
    }
}
