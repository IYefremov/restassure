package com.cyberiansoft.test.vnextbo.steps.devicemanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.devicemanagement.VNextBOActiveDevicesWebPage;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class VNextBOActiveDevicesTabSteps extends VNextBODeviceManagementSteps {

    public static int getActiveDevicesAmount() {

        return new VNextBOActiveDevicesWebPage().getDeviceRecords().size();
    }

    public static String getActiveDevicesNotFoundMessage() {

        return Utils.getText(new VNextBOActiveDevicesWebPage().getNoDevicesFoundMessage());
    }

    public static List<String> getColumnValuesByTitleFromColumnWithText(String columnTitle) {

        return new VNextBOActiveDevicesWebPage().columnTextCellsByTitle(columnTitle).stream().
                map(WebElement::getText).collect(Collectors.toList());
    }

    public static void searchDevicesByTeam(String teamName) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBODevicesAdvancedSearchSteps.setTeamDropdownField(teamName);
        VNextBODevicesAdvancedSearchSteps.clickSearchButton();
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
    }

    public static void searchDevicesByLicense(String license) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBODevicesAdvancedSearchSteps.setLicenseField(license);
        VNextBODevicesAdvancedSearchSteps.clickSearchButton();
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
    }

    public static void searchDevicesByName(String name) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBODevicesAdvancedSearchSteps.setNameField(name);
        VNextBODevicesAdvancedSearchSteps.clickSearchButton();
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
    }

    public static void searchDevicesByVersion(String version) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBODevicesAdvancedSearchSteps.setVersionField(version);
        VNextBODevicesAdvancedSearchSteps.clickSearchButton();
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
    }

    public static void searchDevicesByPlatform(String platformTitle) {

        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBODevicesAdvancedSearchSteps.setPlatformDropdownField(platformTitle);
        VNextBODevicesAdvancedSearchSteps.clickSearchButton();
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
    }

    public static void clickActionsButtonForDevice(String deviceName) {

        Utils.clickElement(new VNextBOActiveDevicesWebPage().actionsDeviceButtonByDeviceName(deviceName));
    }

    public static void clickEditButtonForDevice(String deviceName) {

        Utils.clickElement(new VNextBOActiveDevicesWebPage().editDeviceButtonByDeviceName(deviceName));
    }

    public static void clickDeleteButtonForDevice(String deviceName) {

        Utils.clickElement(new VNextBOActiveDevicesWebPage().deleteDeviceButtonByDeviceName(deviceName));
    }

    public static void clickAuditLogButtonForDevice(String deviceName) {

        Utils.clickElement(new VNextBOActiveDevicesWebPage().auditLogButtonByDeviceName(deviceName));
    }

    public static void openEditDeviceDialog(String deviceName) {

        clickActionsButtonForDevice(deviceName);
        clickEditButtonForDevice(deviceName);
    }

    public static void clickReplaceButtonByDeviceName(String deviceName) {

        Utils.clickElement(new VNextBOActiveDevicesWebPage().replaceButtonByDeviceName(deviceName));
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void hideRegistrationCodeByDeviceName(String deviceName) {

        Utils.clickElement(new VNextBOActiveDevicesWebPage().hideRegistrationCodeXIconByDeviceName(deviceName));
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitABit(1500);
    }
}