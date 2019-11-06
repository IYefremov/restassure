package com.cyberiansoft.test.vnextbo.verifications.devicemanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.devicemanagement.VNextBODeviceManagementWebPage;
import com.cyberiansoft.test.vnextbo.steps.devicemanagement.VNextBODeviceManagementSteps;
import com.cyberiansoft.test.vnextbo.verifications.VNextBOBaseWebPageValidations;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class VNextBODeviceManagementPageValidations extends VNextBOBaseWebPageValidations {

    public static void verifyDevicesTableIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBODeviceManagementWebPage().getDevicesTable()),
                "Devices table hasn't been displayed.");
    }

    public static void verifyCorrectRecordsAmountIsDisplayed(int expectedRecordsAmount) {

        Assert.assertEquals(VNextBODeviceManagementSteps.getActiveDevicesAmount(), expectedRecordsAmount,
                "Devices table has contained incorrect clients amount.");
    }

    public static void verifyAddNewDeviceButtonIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBODeviceManagementWebPage().getAddNewDeviceButton()),
                "\"Add New Device\" button hasn't been displayed.");
    }

    public static void verifyActiveDevicesTabIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBODeviceManagementWebPage().getActiveDevicesTab()),
                "\"Active Devices\" tab hasn't been displayed.");
    }

    public static void verifyPendingRegistrationsTabIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBODeviceManagementWebPage().getPendingRegistrationTab()),
                "\"Pending Registrations\" tab hasn't been displayed.");
    }

    public static void verifySearchResultIsCorrectForColumnWithText(String columnTitle, String expectedValue) {

        for (String cellValue : VNextBODeviceManagementSteps.getColumnValuesByTitleFromColumnWithText(columnTitle)
             ) {
            Assert.assertTrue(cellValue.toLowerCase().contains(expectedValue.toLowerCase()), "Search result hasn't been correct" );
        }
    }

    public static boolean verifyDevicesNotFoundMessageIsDisplayed() {

        return Utils.isElementDisplayed(new VNextBODeviceManagementWebPage().getNoDevicesFoundMessage());
    }

    public static void verifyDevicesNotFoundMessageIsCorrect() {

        Assert.assertEquals(VNextBODeviceManagementSteps.getDevicesNotFoundMessage(), "There are no devices to show.",
                "\"There are no devices to show.\" message hasn't been displayed.");
    }

    public static void verifySearchResultByPlatformIsCorrect(String iconClass, String iconTitle) {

        for (WebElement icon : new VNextBODeviceManagementWebPage().getPlatformColumnIconsList()) {
            Assert.assertEquals(icon.getAttribute("class"), iconClass, "Device icon hasn't been correct");
            Assert.assertEquals(icon.getAttribute("title"), iconTitle, "Device icon hasn't been correct");

        }
    }
}