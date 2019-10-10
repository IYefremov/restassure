package com.cyberiansoft.test.ios10_client.hdvalidations;


import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.InspectionToolBar;
import org.testng.Assert;

public class WizardScreenValidations {

    public static void verifyScreenSubTotalPrice(String expectedPrice) {
        InspectionToolBar inspectionToolBar = new InspectionToolBar();
        Assert.assertEquals(inspectionToolBar.getInspectionSubTotalPrice(), expectedPrice);
    }

    public static void verifyScreenTotalPrice(String expectedPrice) {
        InspectionToolBar inspectionToolBar = new InspectionToolBar();
        Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), expectedPrice);
    }
}
