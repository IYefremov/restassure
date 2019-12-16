package com.cyberiansoft.test.vnextbo.steps.invoices;

import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOOperationsInvoicesData;
import com.cyberiansoft.test.vnextbo.interactions.VNextBOConfirmationDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.invoices.VNextBOInvoicesPageInteractions;
import com.cyberiansoft.test.vnextbo.validations.invoices.VNextBOInvoicesPageValidations;

import java.util.Arrays;

public class VNextBOInvoicesPageSteps {

    public static void voidFirstInvoice() {
        VNextBOInvoicesPageInteractions.clickFirstInvoice();
        VNextBOInvoicesPageInteractions.clickVoidButton();
    }

    public static void confirmVoidingFirstInvoice() {
        voidFirstInvoice();
        VNextBOConfirmationDialogInteractions.clickInvoiceYesButton();
    }

    public static void cancelVoidingFirstInvoice() {
        voidFirstInvoice();
        VNextBOConfirmationDialogInteractions.clickInvoiceNoButton();
    }

    public static void rejectVoidingFirstInvoice() {
        voidFirstInvoice();
        VNextBOConfirmationDialogInteractions.clickInvoiceCloseButton();
    }

    public static void unvoidFirstInvoice() {
        VNextBOInvoicesPageInteractions.clickFirstInvoice();
        VNextBOInvoicesPageInteractions.clickUnvoidButton();
    }

    public static void confirmUnvoidingFirstInvoice() {
        unvoidFirstInvoice();
        VNextBOConfirmationDialogInteractions.clickInvoiceYesButton();
    }

    public static void unvoidSelectedInvoices(String ...invoices) {
        VNextBOInvoicesPageInteractions.clickCheckbox(invoices);
        VNextBOInvoicesPageInteractions.clickHeaderIconUnvoidButton();
        VNextBOConfirmationDialogInteractions.clickInvoiceYesButton();
    }

    public static void voidSelectedInvoices(String ...invoices) {
        VNextBOInvoicesPageInteractions.clickCheckbox(invoices);
        VNextBOInvoicesPageInteractions.clickHeaderIconVoidButton();
        VNextBOConfirmationDialogInteractions.clickInvoiceYesButton();
    }

    public static void cancelApprovingFirstInvoiceWithButton() {
        VNextBOInvoicesPageInteractions.clickFirstInvoice();
        VNextBOInvoicesPageInteractions.clickApproveInvoiceButton();
        VNextBOConfirmationDialogInteractions.clickInvoiceNoButton();
    }

    public static void approveInvoiceWithIcon() {
        VNextBOInvoicesPageInteractions.clickApproveInvoiceIcon();
        VNextBOConfirmationDialogInteractions.clickInvoiceYesButton();
    }

    public static void cancelFirstInvoiceRollbackApprovalWithIcon() {
        VNextBOInvoicesPageInteractions.clickFirstInvoice();
        VNextBOInvoicesPageInteractions.clickRollbackApprovalButton();
        VNextBOConfirmationDialogInteractions.clickInvoiceNoButton();
    }

    public static void approveInvoiceRollbackApprovalWithIcon() {
        VNextBOInvoicesPageInteractions.clickRollbackApprovalIcon();
        VNextBOConfirmationDialogInteractions.clickInvoiceYesButton();
    }

    public static void unvoidFirstInvoices(String[] invoices, VNextBOOperationsInvoicesData data) {
        System.out.println("Selected invoices: " + Arrays.toString(invoices));

        if (invoices != null) {
            VNextBOInvoicesPageSteps.unvoidSelectedInvoices(invoices);
            VNextBOInvoicesPageValidations.verifyInvoicesAreUnvoided(invoices, data);
        }
    }

    public static void voidFirstInvoices(String[] invoices, VNextBOOperationsInvoicesData data) {
        System.out.println("Selected invoices: " + Arrays.toString(invoices));

        if (invoices != null) {
            VNextBOInvoicesPageSteps.voidSelectedInvoices(invoices);
        }
    }
}
