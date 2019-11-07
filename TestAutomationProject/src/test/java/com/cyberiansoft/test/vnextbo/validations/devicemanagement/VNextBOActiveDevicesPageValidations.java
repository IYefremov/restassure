package com.cyberiansoft.test.vnextbo.validations.devicemanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.deviceManagement.VNextBOActiveDevicesWebPage;
import com.cyberiansoft.test.vnextbo.steps.clients.VNextBOClientsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.deviceManagement.VNextBODeviceManagementSteps;
import com.cyberiansoft.test.vnextbo.validations.VNextBOBaseWebPageValidations;
import org.testng.Assert;

public class VNextBOActiveDevicesPageValidations extends VNextBOBaseWebPageValidations {

    public static void isDevicesTableDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOActiveDevicesWebPage().getDevicesTable()),
                "Devices table hasn't been displayed.");
    }

    public static void isCorrectRecordsAmountDisplayed(int expectedRecordsAmount) {

        Assert.assertEquals(VNextBODeviceManagementSteps.getDevicesAmount(), expectedRecordsAmount,
                "Devices table has contained incorrect clients amount.");
    }

    public static void isAddNewDeviceButtonDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOActiveDevicesWebPage().getAddNewDeviceButton()),
                "\"Add New Device\" button hasn't been displayed.");
    }

    public static void isActiveDevicesTabDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOActiveDevicesWebPage().getActiveDevicesTab()),
                "\"Active Devices\" tab hasn't been displayed.");
    }

    public static void isPendingRegistrationsTabDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOActiveDevicesWebPage().getPendingRegistrationTab()),
                "\"Pending Registrations\" tab hasn't been displayed.");
    }

    public static void isSearchResultCorrectForColumnWithText(String columnTitle, String expectedValue) {

        for (String cellValue : VNextBOClientsPageSteps.getColumnValuesByTitleFromColumnWithText(columnTitle)
             ) {
            Assert.assertTrue(cellValue.toLowerCase().contains(expectedValue.toLowerCase()), "Search result hasn't been correct" );
        }
    }

    public static void isSearchResultCorrectForColumnWithCheckboxes(String columnTitle, boolean shouldBeChecked) {

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

    public static void isClientsNotFoundMessageDisplayed() {

        Assert.assertEquals(VNextBOClientsPageSteps.getClientsNotFoundMessage(), "There are no clients to show.",
                "\"There are no clients to show.\" message hasn't been displayed.");
    }
}