package com.cyberiansoft.test.vnextbo.steps.devicemanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.interactions.VNextBOConfirmationDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.deviceManagement.VNextBOActiveDevicesInteractions;
import com.cyberiansoft.test.vnextbo.interactions.deviceManagement.VNextBODeviceManagementInteractions;
import com.cyberiansoft.test.vnextbo.interactions.deviceManagement.VNextBOEditDeviceDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.deviceManagement.VNextBOPendingRegistrationsInteractions;
import com.cyberiansoft.test.vnextbo.screens.devicemanagement.VNextBOActiveDevicesWebPage;
import com.cyberiansoft.test.vnextbo.screens.devicemanagement.VNextBODeviceManagementWebPage;
import com.cyberiansoft.test.vnextbo.steps.VNextBOBaseWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.verifications.VNextBOPendingRegistrationsValidations;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;
import java.util.stream.Collectors;

public class VNextBODeviceManagementSteps extends VNextBOBaseWebPageSteps {

    public static void deletePendingRegistrationDeviceByUser(String user) {

        VNextBOPendingRegistrationsValidations.verifyPendingRegistrationTabIsOpened();
        new VNextBOPendingRegistrationsInteractions().clickDeleteDeviceButtonForUser(user);
        VNextBOConfirmationDialogInteractions.clickYesButton();
    }

    public static void verifyUserCanUncoverRegistrationCode(String deviceName) {

        final VNextBOActiveDevicesInteractions activeDevicesInteractions = new VNextBOActiveDevicesInteractions();
        activeDevicesInteractions.verifyReplaceButtonIsDisplayedForDevice(deviceName);
        activeDevicesInteractions.clickReplaceButtonByDeviceName(deviceName);
        Assert.assertTrue(!activeDevicesInteractions.getRegistrationNumberForDevice(deviceName).isEmpty(),
                "The registration code hasn't been uncovered for device " + deviceName);
    }

    public static void verifyUserCanHideRegistrationCode(String deviceName) {

        final VNextBOActiveDevicesInteractions activeDevicesInteractions = new VNextBOActiveDevicesInteractions();
        activeDevicesInteractions.verifyActiveDevicesTabIsOpened();
        activeDevicesInteractions.clickRegistrationNumberClearButtonForDevice(deviceName);
        Assert.assertTrue(activeDevicesInteractions.isReplaceButtonDisplayedForDevice(deviceName),
                "The 'Replace' button hasn't been uncovered for device " + deviceName);
    }

    public static void openEditDeviceDialog(String deviceName) {

        final VNextBODeviceManagementInteractions deviceManagementInteractions = new VNextBODeviceManagementInteractions();
        deviceManagementInteractions.clickActiveDevicesTab();

        final VNextBOActiveDevicesWebPage activeDevicesWebPage = new VNextBOActiveDevicesWebPage();

        if (activeDevicesWebPage.getDeviceByName(deviceName) == null) {
            deviceName = activeDevicesWebPage.getDeviceByPartialNameMatch(deviceName).getText();
        }

        final VNextBOActiveDevicesInteractions activeDevicesInteractions = new VNextBOActiveDevicesInteractions();
        activeDevicesInteractions.clickActionsButtonForDevice(deviceName);
        Assert.assertTrue(activeDevicesInteractions.isActionsDropDownMenuDisplayedForDevice(deviceName),
                "The actions dropdown menu hasn't been opened");
        activeDevicesInteractions.clickActionsEditButtonForDevice(deviceName);
        Assert.assertTrue(new VNextBOEditDeviceDialogInteractions().isEditDeviceDialogDisplayed(),
                "The 'Edit device dialog' is not opened");
    }

    public static int getActiveDevicesAmount() {

        return new VNextBOActiveDevicesWebPage().getDeviceRecords().size();
    }

    public static void openActiveDevicesTab() {

        Utils.clickElement(new VNextBODeviceManagementWebPage().getActiveDevicesTab());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void openPendingRegistrationDevicesTab() {

        Utils.clickElement(new VNextBODeviceManagementWebPage().getPendingRegistrationTab());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static String getDevicesNotFoundMessage() {

        return Utils.getText(new VNextBODeviceManagementWebPage().getNoDevicesFoundMessage());
    }


    public static List<String> getColumnValuesByTitleFromColumnWithText(String columnTitle) {

        return new VNextBODeviceManagementWebPage().columnTextCellsByTitle(columnTitle).stream().
                map(WebElement::getText).collect(Collectors.toList());
    }

    public static void searchDevicesByTeam(String teamName) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBODevicesAdvancedSearchSteps.setTeamDropdownField(teamName);
        VNextBODevicesAdvancedSearchSteps.clickSearchButton();
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void searchDevicesByLicense(String license) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBODevicesAdvancedSearchSteps.setLicenseField(license);
        VNextBODevicesAdvancedSearchSteps.clickSearchButton();
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void searchDevicesByName(String name) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBODevicesAdvancedSearchSteps.setNameField(name);
        VNextBODevicesAdvancedSearchSteps.clickSearchButton();
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void searchDevicesByVersion(String version) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBODevicesAdvancedSearchSteps.setVersionField(version);
        VNextBODevicesAdvancedSearchSteps.clickSearchButton();
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void searchDevicesByPlatform(String platformTitle) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBODevicesAdvancedSearchSteps.setPlatformDropdownField(platformTitle);
        VNextBODevicesAdvancedSearchSteps.clickSearchButton();
        WaitUtilsWebDriver.waitForLoading();
    }
}