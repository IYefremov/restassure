package com.cyberiansoft.test.bo.steps.company.clients;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.bo.pageobjects.webpages.company.clients.BOClientsSearchBlock;

public class BOClientsSearchSteps {

    public static void setClientName(String clientName) {
        Utils.clearAndType(new BOClientsSearchBlock().getClientInputField(), clientName);
    }
}
