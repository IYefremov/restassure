package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.InvoiceData;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.factories.invoicestypes.InvoiceTypes;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInvoiceTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextBaseWizardScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.InvoiceListElement;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class InvoiceSteps {

    public static String saveInvoiceAsFinal() {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        String invoiceNumber = baseWizardScreen.getNewInspectionNumber();
        baseWizardScreen.saveInvoiceAsFinal();
        BaseUtils.waitABit(10000);
        return invoiceNumber;
    }

    public static String saveInvoiceAsDraft() {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        String invoiceNumber = baseWizardScreen.getNewInspectionNumber();
        baseWizardScreen.saveInvoiceAsDraft();
        BaseUtils.waitABit(10000);
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
        waitInvoicesScreenLoaded();
        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        InvoiceListElement invoiceListElement = invoicesScreen.getInvoiceElement(invoiceId);
        invoiceListElement.openMenu();
    }

    public static void selectInvoice(String invoiceId) {
        waitInvoicesScreenLoaded();
        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        InvoiceListElement invoiceListElement = invoicesScreen.getInvoiceElement(invoiceId);
        invoiceListElement.select();
    }

    public static void createInvoice(InvoiceTypes invoiceType) {
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList();
        invoiceTypesScreen.selectInvoiceType(invoiceType);
    }

    public static void createInvoice(InvoiceTypes invoiceType, InvoiceData invoiceData) {
        createInvoice(invoiceType);
        InvoiceInfoSteps.setInvoicePONumber(invoiceData.getPoNumber());
    }

    public static void switchToTeamInvoicesView() {
        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        invoicesScreen.switchToTeamInvoicesView();
    }

    public static void switchToMyInvoicesView() {
        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        invoicesScreen.switchToMyInvoicesView();
    }

    public static void waitInvoicesScreenLoaded() {
        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        WaitUtils.getGeneralFluentWait().until((webdriver) -> invoicesScreen.getInvoicesList().size() > 0);
        WaitUtils.waitUntilElementIsClickable(invoicesScreen.getRootElement());
    }

    public static void refreshPictures(String invoiceId) {
        openMenu(invoiceId);
        MenuSteps.selectMenuItem(MenuItems.REFRESH_PICTURES);
        VNextInformationDialog informationdlg = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        informationdlg.clickInformationDialogOKButton();
    }

    public static void clickMultiSelectActionsSendEmail() {
        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        invoicesScreen.getMultiselectActionsSendEmail().click();
    }

    public static void unSelectAllSelectedInvoices() {
        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        if (invoicesScreen.getCancelSelectedInvoices().isDisplayed())
            invoicesScreen.getCancelSelectedInvoices().click();
    }

    public static void clickAddInvoice() {
        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        invoicesScreen.clickAddInvoiceButton();
    }

    public static void approveInvoice(String invoiceId) {

        openMenu(invoiceId);
        MenuSteps.selectMenuItem(MenuItems.APPROVE);
        ApproveSteps.drawSignature();
        ApproveSteps.saveApprove();
    }

    public static void viewInvoice(String invoiceId) {

        openMenu(invoiceId);
        MenuSteps.selectMenuItem(MenuItems.VIEW);
        WaitUtils.waitLoadDialogDisappears();
        try {
            WaitUtils.getGeneralFluentWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='notifier-contaier']")));
            WaitUtils.getGeneralFluentWait().until(ExpectedConditions.invisibilityOf(
                    ChromeDriverProvider.INSTANCE.getMobileChromeDriver().findElement(By.xpath("//div[@class='notifier-contaier']"))
            ));
        } catch (TimeoutException ex) {
        }
    }
}
