package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextWorkOrderSummaryScreen;

public class WorkOrderSummarySteps {

    public static void createInvoiceOptionAndSaveWO() {
        VNextWorkOrderSummaryScreen  workOrderSummaryScreen = new VNextWorkOrderSummaryScreen();
        workOrderSummaryScreen.getAutoInvoiceCreateoption().click();
        WizardScreenSteps.saveAction();
    }
}
