package com.cyberiansoft.test.vnext.interactions;

import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextTechnicianScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.TechnicialListElement;

public class TechnicianScreenInteractions {
    public static void selectEvenlyOption() {
        VNextTechnicianScreen technicianScreen = new VNextTechnicianScreen();
        WaitUtils.elementShouldBeVisible(technicianScreen.getRootElement(), true);
        technicianScreen.getEvenlyButton().click();
    }

    public static TechnicialListElement getTechnicianElement(String technicianName) {
        VNextTechnicianScreen technicianScreen = new VNextTechnicianScreen();
        WaitUtils.elementShouldBeVisible(technicianScreen.getRootElement(), true);
        return technicianScreen.getTechList().stream()
                .filter(technicianElement ->
                        technicianElement.getTechnicianName().contains(technicianName)
                )
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Technician with name not found " + technicianName));
    }

    public static void selectTechnician(String technicianName) {
        WaitUtils.getGeneralWebdriverWait().until(driver -> {
            getTechnicianElement(technicianName).checkElement();
            return getTechnicianElement(technicianName).isElementChecked();
        });
    }

    public static void deselectTechnician(String technicianName) {
        WaitUtils.getGeneralWebdriverWait().until(driver -> {
            getTechnicianElement(technicianName).checkElement();
            return !getTechnicianElement(technicianName).isElementChecked();
        });
    }

    public static void acceptScreen() {
        WaitUtils.click(new VNextTechnicianScreen().getAcceptButton());
    }
}
