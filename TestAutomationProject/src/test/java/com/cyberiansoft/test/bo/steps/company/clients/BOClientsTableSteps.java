package com.cyberiansoft.test.bo.steps.company.clients;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.pageobjects.webpages.company.clients.BOClientsDialog;
import com.cyberiansoft.test.bo.pageobjects.webpages.company.clients.BOClientsPage;

public class BOClientsTableSteps {

    public static void openEditDialogForClient(String name) {
        Utils.clickElement(new BOClientsPage().getEditButtonByClientName(name));
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.waitForVisibilityIgnoringException(new BOClientsDialog().getClientDialog(), 10);
    }
}
