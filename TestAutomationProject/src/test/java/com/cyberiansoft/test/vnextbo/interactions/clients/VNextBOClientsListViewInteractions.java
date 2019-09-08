package com.cyberiansoft.test.vnextbo.interactions.clients;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.clients.clientDetails.VNextBOClientsDetailsViewAccordion;
import com.cyberiansoft.test.vnextbo.screens.clients.VNextBOClientsListView;
import com.cyberiansoft.test.baseutils.Utils;
import org.openqa.selenium.support.PageFactory;

public class VNextBOClientsListViewInteractions {

    private VNextBOClientsListView clientsListView;

    public VNextBOClientsListViewInteractions() {
        clientsListView = PageFactory.initElements(DriverBuilder.getInstance().getDriver(), VNextBOClientsListView.class);
    }

    public boolean isClientsTableDisplayed() {
        return Utils.isElementDisplayed(clientsListView.getClientsTable());
    }

    public void clickActionsIcon(String client) {
        Utils.clickElement(clientsListView.getActionsIconForClient(client));
    }

    public VNextBOClientsDetailsViewAccordion clickEditDropMenuButton() {
        Utils.clickElement(clientsListView.getEditDropMenuButton());
        waitForSpinnerToDisappear();
        return PageFactory.initElements(DriverBuilder.getInstance().getDriver(), VNextBOClientsDetailsViewAccordion.class);
    }

    void waitForSpinnerToDisappear() {
        WaitUtilsWebDriver.waitForInvisibilityIgnoringException(clientsListView.getProgressSpinner());
    }

    public VNextBOClientsDetailsViewAccordion clickArchiveDropMenuButton() {
        Utils.clickElement(clientsListView.getArchiveDropMenuButton());
        return PageFactory.initElements(DriverBuilder.getInstance().getDriver(), VNextBOClientsDetailsViewAccordion.class);
    }
}