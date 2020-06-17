package com.cyberiansoft.test.vnextbo.validations.devicemanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.devicemanagement.VNextBOActiveDevicesWebPage;
import com.cyberiansoft.test.vnextbo.screens.devicemanagement.VNextBODeviceManagementWebPage;
import com.cyberiansoft.test.vnextbo.steps.devicemanagement.VNextBOActiveDevicesTabSteps;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VNextBOActiveDevicesTabValidations extends VNextBODeviceManagementWebPage {

    public static void verifyDevicesTableIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOActiveDevicesWebPage().getDevicesTable()),
                "Devices table hasn't been displayed.");
    }

    public static void verifyCorrectRecordsAmountIsDisplayed(int expectedRecordsAmount) {

        WaitUtilsWebDriver.waitForNumberOfElementsToBe(By.xpath("//tbody[@data-template='devices-view-row-template']/tr"), expectedRecordsAmount);
        Assert.assertEquals(VNextBOActiveDevicesTabSteps.getActiveDevicesAmount(), expectedRecordsAmount,
                "Devices table has contained incorrect clients amount.");
    }

    public static void verifySearchResultIsCorrectForColumnWithText(String columnTitle, String expectedValue) {

        for (String cellValue : VNextBOActiveDevicesTabSteps.getColumnValuesByTitleFromColumnWithText(columnTitle)) {
            System.out.println(VNextBOActiveDevicesTabSteps.getColumnValuesByTitleFromColumnWithText(columnTitle));
            System.out.println(cellValue);
            Assert.assertTrue(cellValue.toLowerCase().contains(expectedValue.toLowerCase()), "Search result hasn't been correct" );
        }
    }

    public static boolean verifyActiveDevicesNotFoundMessageIsDisplayed() {

        return Utils.isElementDisplayed(new VNextBOActiveDevicesWebPage().getNoDevicesFoundMessage());
    }

    public static void verifyActiveDevicesNotFoundMessageIsCorrect() {

        Assert.assertEquals(VNextBOActiveDevicesTabSteps.getActiveDevicesNotFoundMessage(), "There are no devices to show.",
                "\"There are no devices to show.\" message hasn't been displayed.");
    }

    public static void verifySearchResultByPlatformIsCorrect(String iconClass, String iconTitle) {

        for (WebElement icon : new VNextBOActiveDevicesWebPage().getPlatformColumnIconsList()) {
            Assert.assertEquals(icon.getAttribute("class"), iconClass, "Device icon hasn't been correct");
            Assert.assertEquals(icon.getAttribute("title"), iconTitle, "Device icon hasn't been correct");

        }
    }

    public static void verifyDevicesTableContainsCorrectColumns() {

        List<String> expectedColumnsList = Arrays.asList("Team", "Platform", "Name", "Reg Date/Change Date", "License",
                "Time Zone", "Reg Code", "Actions");
        List<String> actualColumnsList = new ArrayList<>();

        for (WebElement columnTitle : new VNextBOActiveDevicesWebPage().getColumnsTitlesList()) {
            actualColumnsList.add(Utils.getText(columnTitle));
        }

        Assert.assertEquals(expectedColumnsList, actualColumnsList, "Not all columns have been displayed");
    }

    public static void verifyReplaceButtonIsDisplayedForDevice(String deviceName) {
        final VNextBOActiveDevicesWebPage activeDevicesWebPage = new VNextBOActiveDevicesWebPage();
        final WebElement button = WaitUtilsWebDriver.waitForElementNotToBeStale(
                activeDevicesWebPage.replaceButtonByDeviceName(deviceName));
        WaitUtilsWebDriver.waitForVisibility(button, 3);
        Assert.assertTrue(Utils.isElementDisplayed(button), "Replace button hasn't been displayed");
    }

    public static boolean isReplaceButtonDisplayedForDevice(String deviceName) {
        try {
            return Utils.isElementDisplayed(new VNextBOActiveDevicesWebPage().replaceButtonByDeviceName(deviceName));
        } catch (Exception e) {
            return false;
        }
    }

    public static void verifyReplaceButtonByDeviceNameIsClickable(String deviceName) {
        if (!isReplaceButtonDisplayedForDevice(deviceName)) {
            VNextBOActiveDevicesTabSteps.hideRegistrationCodeByDeviceName(deviceName);
        }
    }

    public static void verifyRegistrationNumberIsDisplayedForDevice(String deviceName) {

        final VNextBOActiveDevicesWebPage devicesWebPage = new VNextBOActiveDevicesWebPage();
        Assert.assertTrue(Utils.isElementDisplayed(devicesWebPage.registrationCodeByDeviceName(deviceName)),
                "Registration code hasn't been displayed");
        Assert.assertNotEquals("", Utils.getText(devicesWebPage.registrationCodeByDeviceName(deviceName)),
                "Registration code has been empty");
    }
}