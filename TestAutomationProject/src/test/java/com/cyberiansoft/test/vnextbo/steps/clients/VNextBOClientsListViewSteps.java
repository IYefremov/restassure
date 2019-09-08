package com.cyberiansoft.test.vnextbo.steps.clients;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.interactions.clients.VNextBOClientsListViewInteractions;
import com.cyberiansoft.test.vnextbo.screens.clients.clientDetails.VNextBOClientsDetailsViewAccordion;
import org.openqa.selenium.support.PageFactory;

public class VNextBOClientsListViewSteps {

    private VNextBOClientsListViewInteractions listViewInteractions;

    public VNextBOClientsListViewSteps() {
        listViewInteractions = new VNextBOClientsListViewInteractions();
    }

    public VNextBOClientsDetailsViewAccordion openClientsDetailsPage(String client) {
        listViewInteractions.clickActionsIcon(client);
        listViewInteractions.clickEditDropMenuButton();
        return PageFactory.initElements(DriverBuilder.getInstance().getDriver(), VNextBOClientsDetailsViewAccordion.class);
    }
}