package com.cyberiansoft.test.bo.validations.company.clients;

import com.cyberiansoft.test.bo.steps.company.clients.BOClientsDialogSteps;

public class BOClientsDialogValidations {

    public static boolean isDefaultAreaDisplayed(String expectedArea) {
        final String defaultArea = BOClientsDialogSteps.getDefaultArea();
        return defaultArea.equals(expectedArea);
    }
}
