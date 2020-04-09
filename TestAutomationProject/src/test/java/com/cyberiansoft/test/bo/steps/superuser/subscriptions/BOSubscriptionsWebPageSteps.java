package com.cyberiansoft.test.bo.steps.superuser.subscriptions;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.pageobjects.webpages.SubscriptionDialog;
import com.cyberiansoft.test.bo.pageobjects.webpages.SubscriptionsWebPage;
import org.openqa.selenium.WebElement;

import java.util.Arrays;

public class BOSubscriptionsWebPageSteps {

    public static void openEditDialogForSubscription(String name) {
        Utils.clickElement(new SubscriptionsWebPage().getEditButtonBySubscriptionName(name));
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.elementShouldBeVisible(new SubscriptionDialog().getSubscriptionDialog(), true, 10);
    }

    private static void setMode(WebElement mode) {
        final SubscriptionDialog dialog = new SubscriptionDialog();
        WaitUtilsWebDriver.elementShouldBeClickable(mode, true, 10);
        Utils.clickElement(mode);
        WaitUtilsWebDriver.waitABit(1000);
        Utils.clickElement(dialog.getOkButton());
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.elementShouldBeVisible(dialog.getSubscriptionDialog(), false, 5);
    }

    public static void setFullMode() {
        setMode(new SubscriptionDialog().getFullMode());
    }

    public static void setNoneMode() {
        setMode(new SubscriptionDialog().getNoneMode());
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
