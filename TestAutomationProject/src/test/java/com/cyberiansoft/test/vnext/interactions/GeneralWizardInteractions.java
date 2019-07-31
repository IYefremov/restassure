package com.cyberiansoft.test.vnext.interactions;

import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextBaseWizardScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class GeneralWizardInteractions {
    public static void saveViaMenu() {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        baseWizardScreen.saveInspectionViaMenu();
    }

    public static String getObjectNumber() {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        return baseWizardScreen.getInspectionnumber().getText().trim();
    }

    public static void openSearchFilter() {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        WaitUtils.click(baseWizardScreen.getSearchIcon());
    }

    public static void setSearchText(String searchText) {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        WaitUtils.click(baseWizardScreen.getSearchInput());
        baseWizardScreen.getSearchInput().clear();
        baseWizardScreen.getSearchInput().sendKeys(searchText);
    }
}
