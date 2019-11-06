package com.cyberiansoft.test.vnextbo.steps.devicemanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.devicemanagement.VNextBODevicesAdvancedSearchForm;

public class VNextBODevicesAdvancedSearchSteps {

    public static void clickSearchButton() {

        Utils.clickElement(new VNextBODevicesAdvancedSearchForm().getSearchButton());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void clickCloseButton() {

        Utils.clickElement(new VNextBODevicesAdvancedSearchForm().getCloseButton());
        WaitUtilsWebDriver.waitForLoading();
    }

    public static void setNameField(String name) {

        Utils.clearAndType(new VNextBODevicesAdvancedSearchForm().getNameField(), name);
    }

    public static void setLicenseField(String license) {

        Utils.clearAndType(new VNextBODevicesAdvancedSearchForm().getLicenseField(), license);
    }

    public static void setVersionField(String email) {

        Utils.clearAndType(new VNextBODevicesAdvancedSearchForm().getVersionField(), email);
    }

    public static void setTeamDropdownField(String teamName) {

        VNextBODevicesAdvancedSearchForm advancedSearchForm = new VNextBODevicesAdvancedSearchForm();
        Utils.clearAndType(advancedSearchForm.getTeamDropDownField(), teamName);
        Utils.selectOptionInDropDownWithJs(advancedSearchForm.getTeamDropDownOptionsList(),
                advancedSearchForm.teamDropDownFieldOption(teamName));
    }

    public static void setPlatformDropdownField(String platformName) {

        VNextBODevicesAdvancedSearchForm advancedSearchForm = new VNextBODevicesAdvancedSearchForm();
        Utils.clickElement(advancedSearchForm.getPlatformDropDownField());
        Utils.selectOptionInDropDownWithJs(advancedSearchForm.getPlatformDropDownOptionsList(),
                advancedSearchForm.platformDropDownFieldOption(platformName));
    }
}