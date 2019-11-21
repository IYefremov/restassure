package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularBaseAppScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.*;
import com.cyberiansoft.test.ios10_client.types.wizardscreens.WizardScreenTypes;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;

import java.time.Duration;

public class RegularNavigationSteps {

    public static void navigateToServicesScreen() {
        RegularBaseWizardScreen baseWizardScreen = new RegularBaseWizardScreen();
        baseWizardScreen.selectNextScreen(WizardScreenTypes.SERVICES);
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.waitServicesScreenLoaded();
    }

    public static void navigateToVehicleInfoScreen() {
        RegularBaseWizardScreen baseWizardScreen = new RegularBaseWizardScreen();
        baseWizardScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
    }

    public static void navigateToClaimScreen() {
        RegularBaseWizardScreen baseWizardScreen = new RegularBaseWizardScreen();
        baseWizardScreen.selectNextScreen(WizardScreenTypes.CLAIM);
    }

    public static void navigateToOrderSummaryScreen() {
        RegularBaseWizardScreen baseWizardScreen = new RegularBaseWizardScreen();
        baseWizardScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
        RegularWorkOrderSummaryScreenSteps.waitWorkOrderSummaryScreenLoad();
    }

    public static void navigateToInvoiceInfoScreen() {
        RegularBaseWizardScreen baseWizardScreen = new RegularBaseWizardScreen();
        baseWizardScreen.selectNextScreen(WizardScreenTypes.INVOICE_INFO);
    }

    public static void navigateToPriceMatrixScreen() {
        RegularBaseWizardScreen baseWizardScreen = new RegularBaseWizardScreen();
        baseWizardScreen.selectNextScreen(WizardScreenTypes.PRICE_MATRIX);
    }

    public static void navigateToVisualScreen(WizardScreenTypes wizardScreenType) {
        RegularBaseWizardScreen baseWizardScreen = new RegularBaseWizardScreen();
        baseWizardScreen.selectNextScreen(wizardScreenType);
        RegularVisualScreenSteps.waitVisualScreenLoaded(wizardScreenType.getDefaultScreenTypeName());
    }

    public static void navigateToScreen(String screenName) {
        RegularBaseWizardScreen baseWizardScreen = new RegularBaseWizardScreen();
        baseWizardScreen.selectNextScreen(screenName);
        //baseWizardScreen.waitScreenLoaded(screenName);
    }

    public static void navigateToPriceMatrixScreen(String screenName) {
        RegularBaseWizardScreen baseWizardScreen = new RegularBaseWizardScreen();
        baseWizardScreen.selectNextScreen(screenName);
    }

    public static void navigateBackScreen() {
        RegularBaseAppScreen baseAppScreen = new RegularBaseAppScreen();
        baseAppScreen.clickHomeButton();
    }

    public static void swipeScreenRight() {
        Dimension size = DriverBuilder.getInstance().getAppiumDriver().manage().window().getSize();
        int startx = (int) (size.width * 0.20);
        int endx = (int) (size.width * 0.80);
        int starty = (int) size.height / 2;
        TouchAction swipe = new TouchAction(DriverBuilder.getInstance().getAppiumDriver()).press(PointOption.point(endx, starty))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2))).moveTo(PointOption.point(startx, starty)).release();
        swipe.perform();
    }

    public static void swipeScreenLeft() {
        Dimension size = DriverBuilder.getInstance().getAppiumDriver().manage().window().getSize();
        int startx = (int) (size.width * 0.20);
        int endx = (int) (size.width * 0.80);
        int starty = size.height / 3;

        TouchAction swipe = new TouchAction(DriverBuilder.getInstance().getAppiumDriver()).press(PointOption.point(startx, starty))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2))).moveTo(PointOption.point(endx, starty)).release();
        swipe.perform();
    }
}
