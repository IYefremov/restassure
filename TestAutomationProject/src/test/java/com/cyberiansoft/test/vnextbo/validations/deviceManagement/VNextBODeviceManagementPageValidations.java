package com.cyberiansoft.test.vnextbo.validations.deviceManagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.devicemanagement.VNextBODeviceManagementWebPage;
import com.cyberiansoft.test.vnextbo.steps.devicemanagement.VNextBODeviceManagementSteps;
import com.cyberiansoft.test.vnextbo.validations.VNextBOBaseWebPageValidations;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class VNextBODeviceManagementPageValidations extends VNextBOBaseWebPageValidations {

    public static void isDevicesTableDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBODeviceManagementWebPage().getDevicesTable()),
                "Devices table hasn't been displayed.");
    }

    public static void isCorrectRecordsAmountDisplayed(int expectedRecordsAmount) {

        Assert.assertEquals(VNextBODeviceManagementSteps.getActiveDevicesAmount(), expectedRecordsAmount,
                "Devices table has contained incorrect clients amount.");
    }

    public static void isAddNewDeviceButtonDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBODeviceManagementWebPage().getAddNewDeviceButton()),
                "\"Add New Device\" button hasn't been displayed.");
    }

    public static void isActiveDevicesTabDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBODeviceManagementWebPage().getActiveDevicesTab()),
                "\"Active Devices\" tab hasn't been displayed.");
    }

    public static void isPendingRegistrationsTabDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBODeviceManagementWebPage().getPendingRegistrationTab()),
                "\"Pending Registrations\" tab hasn't been displayed.");
    }

    public static void isSearchResultCorrectForColumnWithText(String columnTitle, String expectedValue) {

        for (String cellValue : VNextBODeviceManagementSteps.getColumnValuesByTitleFromColumnWithText(columnTitle)
                ) {
            Assert.assertTrue(cellValue.toLowerCase().contains(expectedValue.toLowerCase()), "Search result hasn't been correct" );
        }
    }

    public static boolean isDevicesNotFoundMessageDisplayed() {

        return Utils.isElementDisplayed(new VNextBODeviceManagementWebPage().getNoDevicesFoundMessage());
    }

    public static void isDevicesNotFoundMessageCorrect() {

        Assert.assertEquals(VNextBODeviceManagementSteps.getDevicesNotFoundMessage(), "There are no devices to show.",
                "\"There are no devices to show.\" message hasn't been displayed.");
    }

    private static void verifyPlatformColumnContainsCorrectIcons(String iconClass, String iconTitle) {

        for (WebElement icon : new VNextBODeviceManagementWebPage().getPlatformColumnIconsList()) {
            Assert.assertEquals(icon.getAttribute("class"), iconClass, "Device icon hasn't been correct");
            Assert.assertEquals(icon.getAttribute("title"), iconTitle, "Device icon hasn't been correct");

        }
    }

    public static void verifySearchResultByPlatformIsCorrect(String iconClass, String iconTitle) {

        for (WebElement icon : new VNextBODeviceManagementWebPage().getPlatformColumnIconsList()) {
            Assert.assertEquals(icon.getAttribute("class"), iconClass, "Device icon hasn't been correct");
            Assert.assertEquals(icon.getAttribute("title"), iconTitle, "Device icon hasn't been correct");

        }
    }
}