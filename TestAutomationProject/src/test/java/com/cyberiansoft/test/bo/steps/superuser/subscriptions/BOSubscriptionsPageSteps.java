package com.cyberiansoft.test.bo.steps.superuser.subscriptions;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.pageobjects.webpages.BOSubscriptionDialog;
import com.cyberiansoft.test.bo.pageobjects.webpages.BOSubscriptionsPage;
import org.openqa.selenium.WebElement;

import java.util.Arrays;

public class BOSubscriptionsPageSteps {

    public static void openEditDialogForSubscription(String name) {
        final BOSubscriptionsPage subscriptionsPage = new BOSubscriptionsPage();
        WaitUtilsWebDriver.waitForElementNotToBeStale(subscriptionsPage.getEditButtonBySubscriptionName(name));
        Utils.clickElement(subscriptionsPage.getEditButtonBySubscriptionName(name));
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.elementShouldBeVisible(new BOSubscriptionDialog().getSubscriptionDialog(), true, 10);
    }

    private static void setMode(WebElement mode) {
        final BOSubscriptionDialog dialog = new BOSubscriptionDialog();
        WaitUtilsWebDriver.elementShouldBeClickable(mode, true, 10);
        Utils.clickElement(mode);
        WaitUtilsWebDriver.waitABit(1000);
        Utils.clickElement(dialog.getOkButton());
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.elementShouldBeVisible(dialog.getSubscriptionDialog(), false, 5);
    }

    public static void setFullMode() {
        setMode(new BOSubscriptionDialog().getFullMode());
    }

    public static void setNoneMode() {
        setMode(new BOSubscriptionDialog().getNoneMode());
    }

    public static void setNoneModeForSubscriptions(String ...subscriptions) {
        Arrays.asList(subscriptions).forEach(subscription -> {
            openEditDialogForSubscription(subscription);
            setNoneMode();
        });
    }

    public static void setFullModeForSubscriptions(String ...subscriptions) {
        Arrays.asList(subscriptions).forEach(subscription -> {
            openEditDialogForSubscription(subscription);
            setFullMode();
        });
    }
}
