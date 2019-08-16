package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.BaseAppScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.BaseWizardScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.ServicesScreen;
import com.cyberiansoft.test.ios10_client.types.wizardscreens.WizardScreenTypes;

public class NavigationSteps {

    public static void navigateToServicesScreen() {
        BaseWizardScreen baseWizardScreen = new BaseWizardScreen();
        baseWizardScreen.selectNextScreen(WizardScreenTypes.SERVICES);
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.waitServicesScreenLoaded();
    }

    public static void navigateToClaimScreen() {
        BaseWizardScreen baseWizardScreen = new BaseWizardScreen();
        baseWizardScreen.selectNextScreen(WizardScreenTypes.CLAIM);
    }

    public static void navigateToPriceMatrixScreen(String screenName) {
        BaseWizardScreen baseWizardScreen = new BaseWizardScreen();
        baseWizardScreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX, screenName);
    }

    public static void navigateToOrderSummaryScreen() {
        BaseWizardScreen baseWizardScreen = new BaseWizardScreen();
        baseWizardScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
        OrderSummaryScreenSteps.waitOrderSummaryScreenLoaded();
    }

    public static void navigateBackScreen() {
        BaseAppScreen baseAppScreen = new BaseAppScreen();
        baseAppScreen.clickHomeButton();
    }
}
