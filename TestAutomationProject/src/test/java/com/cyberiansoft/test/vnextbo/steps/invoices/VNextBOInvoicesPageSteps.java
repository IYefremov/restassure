package com.cyberiansoft.test.vnextbo.steps.invoices;

import com.cyberiansoft.test.vnextbo.interactions.VNextBOConfirmationDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.invoices.VNextBOInvoicesPageInteractions;

public class VNextBOInvoicesPageSteps {

    public static void voidFirstInvoice() {
        new VNextBOInvoicesPageInteractions().clickFirstInvoice();
        new VNextBOInvoicesPageInteractions().clickVoidButton();
    }

    public static void confirmVoidingFirstInvoice() {
        voidFirstInvoice();
        new VNextBOConfirmationDialogInteractions().clickInvoiceYesButton();
    }

    public static void cancelVoidingFirstInvoice() {
        voidFirstInvoice();
        new VNextBOConfirmationDialogInteractions().clickInvoiceNoButton();
    }

    public static void rejectVoidingFirstInvoice() {
        voidFirstInvoice();
        new VNextBOConfirmationDialogInteractions().clickInvoiceRejectButton();
    }

    public static void unvoidFirstInvoice() {
        new VNextBOInvoicesPageInteractions().clickFirstInvoice();
        new VNextBOInvoicesPageInteractions().clickUnvoidButton();
    }

    public static void confirmUnvoidingFirstInvoice() {
        unvoidFirstInvoice();
        new VNextBOConfirmationDialogInteractions().clickInvoiceYesButton();
    }

    public static void unvoidSelectedInvoices(String ...invoices) {
        final VNextBOInvoicesPageInteractions invoicesPageInteractions = new VNextBOInvoicesPageInteractions();
        invoicesPageInteractions.clickCheckbox(invoices);
        invoicesPageInteractions.clickHeaderIconUnvoidButton();
        new VNextBOConfirmationDialogInteractions().clickInvoiceYesButton();
    }
}
