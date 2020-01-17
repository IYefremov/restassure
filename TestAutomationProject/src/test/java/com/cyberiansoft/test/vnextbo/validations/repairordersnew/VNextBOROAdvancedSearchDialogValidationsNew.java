package com.cyberiansoft.test.vnextbo.validations.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.repairordersnew.VNextBOROAdvancedSearchDialogNew;
import org.testng.Assert;

public class VNextBOROAdvancedSearchDialogValidationsNew {

    public static void verifyCustomerFieldHasCorrectValue(String expectedCustomer) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextBOROAdvancedSearchDialogNew().getCustomerInputField()), expectedCustomer,
                "Customer field has contained incorrect value");
    }

    public static void verifyDialogIsDisplayed(boolean shouldBeDisplayed) {

        if (shouldBeDisplayed) Assert.assertTrue(Utils.isElementDisplayed(new VNextBOROAdvancedSearchDialogNew().getAdvancedSearchDialog()),
                " Advanced search dialog hasn't been displayed");
        else Assert.assertFalse(Utils.isElementDisplayed(new VNextBOROAdvancedSearchDialogNew().getAdvancedSearchDialog()),
                " Advanced search dialog hasn't been closed");
    }
}
