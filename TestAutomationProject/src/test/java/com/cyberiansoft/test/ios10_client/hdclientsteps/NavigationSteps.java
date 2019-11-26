package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.BaseAppScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.BaseWizardScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.ServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VehicleScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VisualInteriorScreen;
import com.cyberiansoft.test.ios10_client.types.wizardscreens.WizardScreenTypes;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

    public static void navigateToPriceMatrixScreen() {
        BaseWizardScreen baseWizardScreen = new BaseWizardScreen();
        baseWizardScreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX);
    }

    public static void navigateToOrderSummaryScreen() {
        BaseWizardScreen baseWizardScreen = new BaseWizardScreen();
        baseWizardScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
        OrderSummaryScreenSteps.waitOrderSummaryScreenLoaded();
    }

    public static void navigateToVehicleInfoScreen() {
        BaseWizardScreen baseWizardScreen = new BaseWizardScreen();
        baseWizardScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.waitVehicleScreenLoaded();
    }

    public static void navigateToVisualInteriorScreen() {
        BaseWizardScreen baseWizardScreen = new BaseWizardScreen();
        baseWizardScreen.selectNextScreen(WizardScreenTypes.VISUAL_INTERIOR);
        VisualInteriorScreen visualInteriorScreen = new VisualInteriorScreen();
        visualInteriorScreen.waitVisualScreenLoaded(WizardScreenTypes.VISUAL_INTERIOR.getDefaultScreenTypeName());
    }

    public static void navigateToVisualExteriorScreen() {
        BaseWizardScreen baseWizardScreen = new BaseWizardScreen();
        baseWizardScreen.selectNextScreen(WizardScreenTypes.VISUAL_EXTERIOR);
        VisualInteriorScreen visualInteriorScreen = new VisualInteriorScreen();
        visualInteriorScreen.waitVisualScreenLoaded(WizardScreenTypes.VISUAL_EXTERIOR.getDefaultScreenTypeName());
    }

    public static void navigateToInvoiceInfoScreen() {
        BaseWizardScreen baseWizardScreen = new BaseWizardScreen();
        baseWizardScreen.selectNextScreen(WizardScreenTypes.INVOICE_INFO);
    }

    public static void navigateToQuestionsScreen() {
        BaseWizardScreen baseWizardScreen = new BaseWizardScreen();
        baseWizardScreen.selectNextScreen(WizardScreenTypes.QUESTIONS);
    }

    public static void navigateToScreen(String screenName) {
        BaseWizardScreen baseWizardScreen = new BaseWizardScreen();
        baseWizardScreen.selectNextScreen(screenName);
        //WebDriverWait wait = new WebDriverWait(DriverBuilder.getInstance().getAppiumDriver(), 10);
        //wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(screenName)));
    }

    public static void navigateBackScreen() {
        BaseAppScreen baseAppScreen = new BaseAppScreen();
        baseAppScreen.clickHomeButton();
    }
}
