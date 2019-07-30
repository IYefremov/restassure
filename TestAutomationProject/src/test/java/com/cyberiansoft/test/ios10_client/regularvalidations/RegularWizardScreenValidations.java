package com.cyberiansoft.test.ios10_client.regularvalidations;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularInspectionToolBar;
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
