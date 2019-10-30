package com.cyberiansoft.test.vnextbo.verifications.invoices;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.VNextBOAdvancedSearchInvoiceForm;
import org.testng.Assert;

public class VNextBOAdvancedSearchInvoiceFormValidations {

    public static void verifyAdvancedSearchDialogIsOpened() {
        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOAdvancedSearchInvoiceForm().getAdvancedSearchForm(), 5),
                "The advanced search dialog is not opened");
    }

    public static void verifyAdvancedSearchDialogIsClosed() {
        Assert.assertTrue(Utils.isElementNotDisplayed(new VNextBOAdvancedSearchInvoiceForm().getAdvancedSearchForm(), 5),
                "The advanced search dialog is not closed");
    }
}
