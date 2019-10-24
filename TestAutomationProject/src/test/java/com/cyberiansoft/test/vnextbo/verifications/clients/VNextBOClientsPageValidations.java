package com.cyberiansoft.test.vnextbo.verifications.clients;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.clients.VNextBOClientsWebPage;
import com.cyberiansoft.test.vnextbo.steps.clients.VNextBOClientsPageSteps;
import com.cyberiansoft.test.vnextbo.verifications.VNextBOBaseWebPageValidations;
import org.testng.Assert;

public class VNextBOClientsPageValidations extends VNextBOBaseWebPageValidations {

    public static void isAddNewClientBtnDisplayed()
    {
        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOClientsWebPage().getAddNewClientButton()),
                "\"Add New Client\" button hasn't been displayed.");
    }

    public static void isActiveTabDisplayed()
    {
        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOClientsWebPage().getActiveTab()),
                "\"Active\" tab hasn't been displayed.");
    }

    public static void isArchivedTabDisplayed()
    {
        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOClientsWebPage().getArchivedTab()),
                "\"Archived\" tab hasn't been displayed.");
    }

    public static void isClientsTableDisplayed()
    {
        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOClientsWebPage().getClientsTable()),
                "clients table hasn't been displayed.");
    }

    public static void isCorrectRecordsAmountDisplayed(int expectedRecordsAmount)
    {
        Assert.assertEquals(VNextBOClientsPageSteps.getClientsAmount(), expectedRecordsAmount,
                "Clients table has contained incorrect clients amount.");
    }

    public static void isSearchResultCorrectForColumnWithText(String columnTitle, String expectedValue)
    {
        for (String cellValue : VNextBOClientsPageSteps.getColumnValuesByTitleFromColumnWithText(columnTitle)
             ) {
            Assert.assertTrue(cellValue.toLowerCase().contains(expectedValue.toLowerCase()), "Search result hasn't been correct" );
        }
    }

    public static void isSearchResultCorrectForColumnWithCheckboxes(String columnTitle, boolean shouldBeChecked)
    {
        if (shouldBeChecked)
        {
            for (String cellValue : VNextBOClientsPageSteps.getColumnValuesFromColumnWithCheckBoxes(columnTitle)
            ) {
                Assert.assertEquals(cellValue, "true", "Search result hasn't been correct" );
            }
        }
        else
        {
            for (String cellValue : VNextBOClientsPageSteps.getColumnValuesFromColumnWithCheckBoxes(columnTitle)
            ) {
                Assert.assertEquals(cellValue, null, "Search result hasn't been correct" );
            }
        }

    }

    public static void isClientsNotFoundMessageDisplayed()
    {
        Assert.assertEquals(VNextBOClientsPageSteps.getClientsNotFoundMessage(), "There are no clients to show.",
                "\"There are no clients to show.\" message hasn't been displayed.");
    }
}