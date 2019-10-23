package com.cyberiansoft.test.vnextbo.verifications.clients;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.clients.VNextBOClientsWebPage;
import com.cyberiansoft.test.vnextbo.steps.clients.VNextBOClientsPageSteps;
import com.cyberiansoft.test.vnextbo.verifications.VNextBOBaseWebPageValidations;
import org.testng.Assert;

public class VNextBOClientsPageValidations extends VNextBOBaseWebPageValidations {

    public static void isAddNewClientBtnDisplayed()
    {
        VNextBOClientsWebPage vNextBOClientsWebPage = new VNextBOClientsWebPage();
        Assert.assertTrue(Utils.isElementDisplayed(vNextBOClientsWebPage.getAddNewClientButton()),
                "\"Add New Client\" button hasn't been displayed.");
    }

    public static void isActiveTabDisplayed()
    {
        VNextBOClientsWebPage vNextBOClientsWebPage = new VNextBOClientsWebPage();
        Assert.assertTrue(Utils.isElementDisplayed(vNextBOClientsWebPage.getActiveTab()),
                "\"Active\" tab hasn't been displayed.");
    }

    public static void isArchivedTabDisplayed()
    {
        VNextBOClientsWebPage vNextBOClientsWebPage = new VNextBOClientsWebPage();
        Assert.assertTrue(Utils.isElementDisplayed(vNextBOClientsWebPage.getArchivedTab()),
                "\"Archived\" tab hasn't been displayed.");
    }

    public static void isClientsTableDisplayed()
    {
        VNextBOClientsWebPage vNextBOClientsWebPage = new VNextBOClientsWebPage();
        Assert.assertTrue(Utils.isElementDisplayed(vNextBOClientsWebPage.getClientsTable()),
                "clients table hasn't been displayed.");
    }

    public static void isCorrectRecordsAmountDisplayed(int expectedRecordsAmount)
    {
        VNextBOClientsWebPage vNextBOClientsWebPage = new VNextBOClientsWebPage();
        Assert.assertEquals(VNextBOClientsPageSteps.getClientsAmount(), expectedRecordsAmount,
                "Clients table has contained incorrect clients amount.");
    }
}