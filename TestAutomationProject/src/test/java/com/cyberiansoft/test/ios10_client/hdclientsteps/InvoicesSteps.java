package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.InvoiceInfoScreen;
import org.openqa.selenium.Alert;

public class InvoicesSteps {

    public static void cancelCreatingInvoice() {
        WizardScreensSteps.cancelWizard();
        Alert alert = DriverBuilder.getInstance().getAppiumDriver().switchTo().alert();
        alert.accept();
    }

    public static void saveInvoiceAsFinal() {
        InvoiceInfoScreen invoiceInfoScreen = new InvoiceInfoScreen();
        invoiceInfoScreen.clickSaveAsFinal();
    }

    public static void saveInvoiceAsDraft() {
        InvoiceInfoScreen invoiceInfoScreen = new InvoiceInfoScreen();
        invoiceInfoScreen.clickSaveAsDraft();
    }
}
