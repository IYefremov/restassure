package com.cyberiansoft.test.bo.steps.company.workordertypes;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.pageobjects.webpages.company.workordertypes.BOWorkOrderTypesPage;

import java.util.List;

public class BOWorkOrderTypesPageSteps {

    public static void openEditWOTypeDialogByType(String type) {
        final BOWorkOrderTypesPage woTypesPage = new BOWorkOrderTypesPage();
        WaitUtilsWebDriver.waitForVisibilityIgnoringException(woTypesPage.getWoTypesTable().getWrappedElement(), 2);
        final List<String> woTypesList = Utils.getText(woTypesPage.getWOTypeList());
        for (int i = 0; i < woTypesList.size(); i++) {
            if (woTypesList.get(i).equals(type)) {
                Utils.clickElement(woTypesPage.getEditButtonsList().get(i));
                WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
                WaitUtilsWebDriver.waitForPendingRequestsToComplete();
                BOWorkOrderTypeDialogSteps.waitForWOTypeDialogToBeOpened();
            }
        }
    }
}
