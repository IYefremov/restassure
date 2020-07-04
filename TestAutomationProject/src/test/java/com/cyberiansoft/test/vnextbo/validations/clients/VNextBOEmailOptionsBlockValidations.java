package com.cyberiansoft.test.vnextbo.validations.clients;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.clients.clientdetails.VNextBOEmailOptionsBlock;
import org.testng.Assert;

public class VNextBOEmailOptionsBlockValidations {

    public static boolean isInvoicesCheckboxClickable(boolean expected) {
        return WaitUtilsWebDriver.elementShouldBeClickable(new VNextBOEmailOptionsBlock().getInvoicesCheckbox(), expected, 5);
    }

    public static boolean isInspectionsCheckboxClickable(boolean expected) {
        return WaitUtilsWebDriver.elementShouldBeClickable(new VNextBOEmailOptionsBlock().getInspectionsCheckbox(), expected, 5);
    }

    public static boolean isIncludeInspectionCheckboxClickable(boolean expected) {
        return WaitUtilsWebDriver.elementShouldBeClickable(new VNextBOEmailOptionsBlock().getIncludeInspectionCheckbox(), expected, 5);
    }

    public static void verifyCheckboxesAreClickable(boolean expected) {
        Assert.assertTrue(isInvoicesCheckboxClickable(expected), "The invoices checkbox is (not) clickable");
        Assert.assertTrue(isInspectionsCheckboxClickable(expected), "The inspections checkbox is (not) clickable");
        Assert.assertTrue(isIncludeInspectionCheckboxClickable(expected),
                "The include inspections checkbox is (not) clickable");
    }
}
