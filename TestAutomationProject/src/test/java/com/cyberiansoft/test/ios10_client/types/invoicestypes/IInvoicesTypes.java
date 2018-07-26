package com.cyberiansoft.test.ios10_client.types.invoicestypes;

import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.IBaseWizardScreen;

public interface IInvoicesTypes {

    public String getInvoiceTypeName();
    public <T extends IBaseWizardScreen>T getFirstVizardScreen();
}
