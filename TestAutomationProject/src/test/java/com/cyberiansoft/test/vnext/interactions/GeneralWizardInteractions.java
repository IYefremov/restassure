package com.cyberiansoft.test.vnext.interactions;

import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextBaseWizardScreen;

public class GeneralWizardInteractions {
    public static void saveViaMenu() {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        baseWizardScreen.saveInspectionViaMenu();
    }

    public static String getObjectNumber() {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        return baseWizardScreen.getInspectionnumber().getText().trim();
    }
}
