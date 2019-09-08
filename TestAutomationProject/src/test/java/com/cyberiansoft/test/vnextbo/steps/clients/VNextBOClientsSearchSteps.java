package com.cyberiansoft.test.vnextbo.steps.clients;

import com.cyberiansoft.test.vnextbo.interactions.clients.VNextBOClientsSearchPanelInteractions;

public class VNextBOClientsSearchSteps {

    private VNextBOClientsSearchPanelInteractions searchPanelInteractions;

    public VNextBOClientsSearchSteps() {
        searchPanelInteractions = new VNextBOClientsSearchPanelInteractions();
    }

    public void searchWithSimpleSearch(String searchParam) {
        searchPanelInteractions.setClientsSearchText(searchParam);
        searchPanelInteractions.clickSearchLoupeIcon();
    }
}