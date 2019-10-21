package com.cyberiansoft.test.vnextbo.steps.clients;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.interactions.clients.VNextBOClientsListViewInteractions;
import com.cyberiansoft.test.vnextbo.screens.clients.clientDetails.VNextBOClientsDetailsViewAccordion;
import org.openqa.selenium.support.PageFactory;

public class VNextBOClientsListViewSteps {

    public static VNextBOClientsDetailsViewAccordion openClientsDetailsPage(String client) {
        final VNextBOClientsListViewInteractions clientsListViewInteractions = new VNextBOClientsListViewInteractions();
        clientsListViewInteractions.clickActionsIcon(client);
        clientsListViewInteractions.clickEditDropMenuButton();
        return PageFactory.initElements(DriverBuilder.getInstance().getDriver(), VNextBOClientsDetailsViewAccordion.class);
    }
}