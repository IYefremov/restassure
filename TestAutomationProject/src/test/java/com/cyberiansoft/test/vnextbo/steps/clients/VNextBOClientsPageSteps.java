package com.cyberiansoft.test.vnextbo.steps.clients;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.clients.VNextBOClientsWebPage;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;

public class VNextBOClientsPageSteps extends VNextBOBaseWebPageSteps {

    public static void clickAddNewClientButton()
    {
        VNextBOClientsWebPage clientsWebPage = new VNextBOClientsWebPage();
        Utils.clickElement(clientsWebPage.getAddNewClientButton());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void openActiveTab()
    {
        VNextBOClientsWebPage clientsWebPage = new VNextBOClientsWebPage();
        Utils.clickElement(clientsWebPage.getActiveTab());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void openArchivedTab()
    {
        VNextBOClientsWebPage clientsWebPage = new VNextBOClientsWebPage();
        Utils.clickElement(clientsWebPage.getArchivedTab());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static int getClientsAmount()
    {
        VNextBOClientsWebPage clientsWebPage = new VNextBOClientsWebPage();
        return clientsWebPage.getClientRecords().size();
    }
}