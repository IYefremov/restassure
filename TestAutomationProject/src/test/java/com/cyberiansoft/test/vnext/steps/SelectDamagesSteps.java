package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.dataclasses.DamageData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.vnext.screens.VNextSelectDamagesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVisualServicesScreen;

public class SelectDamagesSteps {

    public static void selectDefaultDamage(DamageData damageData) {
        VNextSelectDamagesScreen selectDamagesScreen = new VNextSelectDamagesScreen();
        selectDamagesScreen.clickDefaultDamageType(damageData.getDamageGroupName());
    }

    public static void selectNonDefaultDamage(DamageData damageData, ServiceData serviceData) {
        VNextSelectDamagesScreen selectDamagesScreen = new VNextSelectDamagesScreen();
        selectDamagesScreen.selectAllDamagesTab();
        selectDamagesScreen.clickCustomDamageType(damageData.getDamageGroupName());
        VNextVisualServicesScreen visualServicesScreen = new VNextVisualServicesScreen();
        visualServicesScreen.selectCustomService(serviceData.getServiceName());
    }
}
