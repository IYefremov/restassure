package com.cyberiansoft.test.vnextbo.steps.clients;

import com.cyberiansoft.test.vnextbo.interactions.clients.VNextBOClientsSearchPanelInteractions;

public class VNextBOClientsSearchSteps {

    public static void searchWithSimpleSearch(String searchParam) {
        final VNextBOClientsSearchPanelInteractions clientsSearchPanelInteractions = new VNextBOClientsSearchPanelInteractions();
        clientsSearchPanelInteractions.setClientsSearchText(searchParam);
        clientsSearchPanelInteractions.clickSearchLoupeIcon();
    }
}