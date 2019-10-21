package com.cyberiansoft.test.vnextbo.steps.clients;

import com.cyberiansoft.test.vnextbo.interactions.clients.VNextBOClientsListViewInteractions;

public class VNextBOClientsListViewSteps {

    public static void openClientsDetailsPage(String client) {
        final VNextBOClientsListViewInteractions clientsListViewInteractions = new VNextBOClientsListViewInteractions();
        clientsListViewInteractions.clickActionsIcon(client);
        clientsListViewInteractions.clickEditDropMenuButton();
    }
}