package com.cyberiansoft.test.vnextbo.interactions.clients;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.clients.clientDetails.VNextBOEmailOptionsBlock;

public class VNextBOEmailOptionsBlockInteractions {

    public static void setDefaultRecipient(String value) {
        Utils.clearAndType(new VNextBOEmailOptionsBlock().getDefaultRecipientInputField(), value);
    }

    public static void setCc(String value) {
        Utils.clearAndType(new VNextBOEmailOptionsBlock().getCcInputField(), value);
    }

    public static void setBcc(String value) {
        Utils.clearAndType(new VNextBOEmailOptionsBlock().getBccInputField(), value);
    }

    public static void clickInvoicesCheckbox() {
        Utils.clickElement(new VNextBOEmailOptionsBlock().getInvoicesCheckbox());
    }

    public static void clickIInspectionsCheckbox() {
        Utils.clickElement(new VNextBOEmailOptionsBlock().getInspectionsCheckbox());
    }

    public static void clickIncludeInspectionCheckbox() {
        Utils.clickElement(new VNextBOEmailOptionsBlock().getIncludeInspectionCheckbox());
    }

    public static boolean isInvoicesCheckboxClickable() {
        return Utils.isElementClickable(new VNextBOEmailOptionsBlock().getInvoicesCheckbox());
    }

    public static boolean isInspectionsCheckboxClickable() {
        return Utils.isElementClickable(new VNextBOEmailOptionsBlock().getInspectionsCheckbox());
    }

    public static boolean isIncludeInspectionCheckboxClickable() {
        return Utils.isElementClickable(new VNextBOEmailOptionsBlock().getIncludeInspectionCheckbox());
    }
}