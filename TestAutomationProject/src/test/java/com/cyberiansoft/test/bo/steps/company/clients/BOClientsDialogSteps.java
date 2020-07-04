package com.cyberiansoft.test.bo.steps.company.clients;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.pageobjects.webpages.company.clients.BOClientsDialog;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.selectComboboxValue;

public class BOClientsDialogSteps {

    public static void setDefaultArea(String area) {
        final BOClientsDialog clientsDialog = new BOClientsDialog();
        selectComboboxValue(clientsDialog.getDefaultAreaCmb(), clientsDialog.getDefaultAreaDd(), area);
    }

    public static String getDefaultArea() {
        return Utils.getInputFieldValue(new BOClientsDialog().getDefaultAreaCmb().getWrappedElement());
    }

    public static String getFirstName() {
        return Utils.getInputFieldValue(new BOClientsDialog().getFirstName());
    }

    public static String getLastName() {
        return Utils.getInputFieldValue(new BOClientsDialog().getLastName());
    }

    public static String getEmail() {
        return Utils.getInputFieldValue(new BOClientsDialog().getEmail());
    }

    public static void submit() {
        final BOClientsDialog clientsDialog = new BOClientsDialog();
        Utils.clickElement(clientsDialog.getOkButton());
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.waitForInvisibilityIgnoringException(clientsDialog.getClientDialog(), 3);
    }

    public static void cancel() {
        final BOClientsDialog clientsDialog = new BOClientsDialog();
        Utils.clickElement(clientsDialog.getCancelButton());
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.waitForInvisibilityIgnoringException(clientsDialog.getClientDialog(), 2);
    }
}
