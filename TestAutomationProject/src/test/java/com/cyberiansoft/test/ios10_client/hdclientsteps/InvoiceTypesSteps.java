package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typespopups.InvoiceTypesPopup;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.IInvoicesTypes;

public class InvoiceTypesSteps {

    public static void selectInvoiceType(IInvoicesTypes iInvoicesType) {
        InvoiceTypesPopup invoiceTypesPopup = new InvoiceTypesPopup();
        invoiceTypesPopup.selectInvoiceType(iInvoicesType);
    }
}
