package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.dataclasses.DamageData;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.screens.VNextSelectDamagesScreen;
import com.cyberiansoft.test.vnext.screens.VNextServiceDetailsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextBaseWizardScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVisualScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVisualServicesScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.testng.Assert;

public class VisualScreenSteps {

    public static void addDamage(DamageData damageData) {
        VNextVisualScreen visualScreen = new VNextVisualScreen();
        VNextSelectDamagesScreen selectDamagesScreen = visualScreen.clickAddServiceButton();
        if (damageData.getMoneyServiceData() == null) {
            selectDamagesScreen.clickDefaultDamageType(damageData.getDamageGroupName());
        }
        else {
            selectDamagesScreen.selectAllDamagesTab();
            VNextVisualServicesScreen visualServicesScreen = selectDamagesScreen.clickCustomDamageType(damageData.getDamageGroupName());
            visualServicesScreen.selectCustomService(damageData.getMoneyServiceData().getServiceName());
        }
        visualScreen.clickCarImage();
    }

    public static void verifyNumberOfAddedDamages(int numberOfExpectedDamages) {
        VNextVisualScreen visualScreen = new VNextVisualScreen();
        Assert.assertEquals(visualScreen.getNumberOfImageMarkers(), numberOfExpectedDamages);
    }

    public static void openEditDamage() {
        VNextVisualScreen visualScreen = new VNextVisualScreen();
        visualScreen.clickCarImageMarker();
    }

    public static void editVisualDamage(String newPrice) {
        openEditDamage();
        ServiceDetailsScreenSteps.changeServicePrice(newPrice);
    }

    public static void verifyInspectionTotalPrice(String expectedPrice) {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        Assert.assertEquals(baseWizardScreen.getInspectionTotalPriceValue(), expectedPrice);
    }
}