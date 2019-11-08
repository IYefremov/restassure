package com.cyberiansoft.test.vnextbo.validations.deviceManagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.deviceManagement.VNextBODevicesAdvancedSearchForm;
import org.testng.Assert;

public class VNextBODevicesAdvancedSearchValidations {

    public static void isAdvancedSearchFormDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBODevicesAdvancedSearchForm().getAdvancedSearchFormContent()),
                "Advanced search form hasn't been displayed\"");
    }

    public static void isAdvancedSearchFormNotDisplayed(VNextBODevicesAdvancedSearchForm advancedSearchForm) {

        Assert.assertTrue(Utils.isElementNotDisplayed(advancedSearchForm.getAdvancedSearchFormContent()),
                "Advanced search form hasn't been closed");
    }

    public static void verifyAllElementsAreDisplayed() {

        VNextBODevicesAdvancedSearchForm advancedSearchForm = new VNextBODevicesAdvancedSearchForm();
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchForm.getSearchButton()),
                "Search button hasn't been displayed");
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchForm.getNameField()),
                "Name field hasn't been displayed");
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchForm.getLicenseField()),
                "License field hasn't been displayed");
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchForm.getPlatformDropDownField()),
                "Platform field hasn't been displayed");
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchForm.getTeamDropDownField()),
                "Team field hasn't been displayed");
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchForm.getVersionField()),
                "Version field hasn't been displayed");
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchForm.getCloseButton()),
                "Close x-icon button hasn't been displayed");
    }
}