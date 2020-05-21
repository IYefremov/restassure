package com.cyberiansoft.test.bo.validations.company.clients;

import com.cyberiansoft.test.bo.steps.company.clients.BOClientsDialogSteps;
import org.testng.Assert;

public class BOClientsTableValidations {

    public static void verifyTheClientsFirstName(String expected) {
        Assert.assertEquals(BOClientsDialogSteps.getFirstName(), expected,
                "The client's first name doesn't contain the search first name parameter");
    }

    public static void verifyTheClientsLastName(String expected) {
        Assert.assertEquals(BOClientsDialogSteps.getLastName(), expected,
                "The client's last name doesn't contain the search last name parameter");
    }

    public static void verifyTheCustomersEmail(String expected) {
        Assert.assertEquals(BOClientsDialogSteps.getEmail(), expected,
                "The customer's email doesn't contain the search email parameter");
    }
}
