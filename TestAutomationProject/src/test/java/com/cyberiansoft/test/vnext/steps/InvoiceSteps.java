package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextBaseWizardScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.InvoiceListElement;

public class InvoiceSteps {

    public static String saveInvoiceAsFinal() {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        String invoiceNumber = baseWizardScreen.getNewInspectionNumber();
        baseWizardScreen.saveInvoiceAsFinal();
        return invoiceNumber;
    }

    public static String saveInvoiceAsDraft() {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        String invoiceNumber = baseWizardScreen.getNewInspectionNumber();
        baseWizardScreen.saveInvoiceAsDraft();
        return invoiceNumber;
    }

    public static String saveInvoice() {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        String invoiceNumber = baseWizardScreen.getNewInspectionNumber();
        baseWizardScreen.clickSaveButton();
        return invoiceNumber;
    }

    public static void cancelInvoice() {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        baseWizardScreen.cancelWizard();
    }

    public static void addTextNoteToInvoice(String noteText) {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        baseWizardScreen.clickMenuButton();
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.setNoteText(noteText);
        ScreenNavigationSteps.pressBackButton();
    }

    public static void openMenu(String invoiceId) {
        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        WaitUtils.getGeneralFluentWait().until((webdriver) -> invoicesScreen.getInvoicesList().size() > 0);
        WaitUtils.waitUntilElementIsClickable(invoicesScreen.getRootElement());
        InvoiceListElement invoiceListElement = invoicesScreen.getInvoiceElement(invoiceId);
        invoiceListElement.openMenu();
    }
}
