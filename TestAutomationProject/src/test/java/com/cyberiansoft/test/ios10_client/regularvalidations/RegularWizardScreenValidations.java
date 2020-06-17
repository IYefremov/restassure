package com.cyberiansoft.test.ios10_client.regularvalidations;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularInspectionToolBar;
import com.cyberiansoft.test.ios10_client.utils.AppiumWait;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class RegularWizardScreenValidations {

    public static void verifyScreenSubTotalPrice(String expectedPrice) {
        RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
        Assert.assertEquals(inspectionToolBar.getInspectionSubTotalPrice(), expectedPrice);
    }

    public static void verifyScreenTotalPrice(String expectedPrice) {
        RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
        Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), expectedPrice);
    }
}
