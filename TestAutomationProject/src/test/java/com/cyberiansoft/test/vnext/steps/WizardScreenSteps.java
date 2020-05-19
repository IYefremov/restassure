package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.interactions.ListSelectPageInteractions;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextBaseWizardScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class WizardScreenSteps {
    public static void navigateToWizardScreen(ScreenType screenType) {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        baseWizardScreen.changeScreen(screenType);
        WaitUtils.getGeneralFluentWait().until(webDriver -> {
                    WaitUtils.elementShouldBeVisible(baseWizardScreen.getShowTopBarPopover(), true);
                    return baseWizardScreen.getShowTopBarPopover().getText().contains(screenType.getScreenIdentificator());
                }
        );
        WaitUtils.elementShouldBeVisible(baseWizardScreen.getRootElement(), true);
        WaitUtils.waitUntilElementIsClickable(baseWizardScreen.getRootElement());
        switch (screenType) {
            case VISUAL:
                HelpingScreenInteractions.dismissHelpingScreenIfPresent();
                break;
        }
    }

    public static void navigateToWizardScreen(ScreenType screenType, Integer index) {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        baseWizardScreen.changeScreen(screenType, index);
        WaitUtils.getGeneralFluentWait().until(webDriver -> {
                    WaitUtils.elementShouldBeVisible(baseWizardScreen.getShowTopBarPopover(), true);
                    return baseWizardScreen.getShowTopBarPopover().getText().contains(screenType.getScreenIdentificator());
                }
        );
        WaitUtils.elementShouldBeVisible(baseWizardScreen.getRootElement(), true);
        WaitUtils.waitUntilElementIsClickable(baseWizardScreen.getRootElement());
        //HelpingScreenInteractions.dismissHelpingScreenIfPresent();
    }

    public static void saveAction() {
        ListSelectPageInteractions.saveListPage();
    }

    public static void selectWizardScreen(ScreenType screenType) {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        baseWizardScreen.selectScreen(screenType);
    }

    public static void clickCancelMenuItem() {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        baseWizardScreen.clickCancelMenuItem();
    }

    public static void clcikSaveViaMenuAsFinal() {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        baseWizardScreen.clcikSaveViaMenuAsFinal();
    }

    public static void clickNotesMenuButton() {
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        baseWizardScreen.clickInspectionNotesOption();
    }
}
