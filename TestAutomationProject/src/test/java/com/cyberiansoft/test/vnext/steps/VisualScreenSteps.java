package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.dataclasses.DamageData;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVisualScreen;
import com.cyberiansoft.test.vnext.steps.services.ServiceDetailsScreenSteps;

public class VisualScreenSteps {

    public static void selectDefaultDamage(DamageData damageData) {
        clickAddService();
        SelectDamagesSteps.selectDefaultDamage(damageData);
    }

    public static void selectNonDefaultDamage(DamageData damageData, String serviceName) {
        clickAddService();
        SelectDamagesSteps.selectNonDefaultDamage(damageData, serviceName);
    }

    public static void addDefaultDamage(DamageData damageData) {
        selectDefaultDamage(damageData);
        addServiceToPicture();
    }

    public static void addNonDefaultDamage(DamageData damageData, String serviceName) {
        selectNonDefaultDamage(damageData, serviceName);
        addServiceToPicture();
    }

    public static void addDamage(DamageData damageData) {
        selectDamage(damageData);
        addServiceToPicture();
    }

    public static void selectDamage(DamageData damageData) {
        if (damageData.getMoneyService() == null) {
            selectDefaultDamage(damageData);
        } else {
            selectNonDefaultDamage(damageData, damageData.getMoneyService().getServiceName());
        }
    }

    public static void clickAddService() {
        VNextVisualScreen visualScreen = new VNextVisualScreen();
        visualScreen.clickAddServiceButton();
    }

    public static void clickDamageCancelEditingButton() {
        VNextVisualScreen visualScreen = new VNextVisualScreen();
        visualScreen.clickDamageCancelEditingButton();
    }

    public static void addServiceToPicture() {
        VNextVisualScreen visualScreen = new VNextVisualScreen();
        visualScreen.clickCarImageOnRandom();
    }

    public static void openEditDamage() {
        VNextVisualScreen visualScreen = new VNextVisualScreen();
        visualScreen.clickCarImageMarker();
    }

    public static void openEditDamage(int markerIndex) {
        VNextVisualScreen visualScreen = new VNextVisualScreen();
        visualScreen.clickCarImageMarker(markerIndex);
    }

    public static int getNumberOfAddedDamages() {
        VNextVisualScreen visualScreen = new VNextVisualScreen();
        return visualScreen.getNumberOfImageMarkers();
    }

    public static void editVisualDamage(String newPrice) {
        openEditDamage();
        ServiceDetailsScreenSteps.changeServicePrice(newPrice);
        ServiceDetailsScreenSteps.closeServiceDetailsScreen();
    }

    public static void removeAllBreakages() {
        VNextVisualScreen visualScreen = new VNextVisualScreen();
        visualScreen.clickRemoveAllBreakagesButton();
        VNextInformationDialog informationDialog = new VNextInformationDialog();
        informationDialog.clickInformationDialogRemoveButton();
    }

    public static void clearAllMarks() {
        VNextVisualScreen visualScreen = new VNextVisualScreen();
        visualScreen.clickRemoveAllBreakagesButton();
        GeneralSteps.confirmDialog();
    }
}
