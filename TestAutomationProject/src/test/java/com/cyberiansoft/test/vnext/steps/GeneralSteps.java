package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.interactions.ErrorDialogInteractions;
import com.cyberiansoft.test.vnext.screens.VNextLoginScreen;
import com.cyberiansoft.test.vnext.utils.AppContexts;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.ConfirmationDialog;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class GeneralSteps {

    public static void confirmDialog() {
        ConfirmationDialog confirmationDialog = new ConfirmationDialog();
        WaitUtils.elementShouldBeVisible(confirmationDialog.getConfirmDialogButton(), true);
        confirmationDialog.getConfirmDialogButton().click();
    }

    public static void declineDialog() {
        ConfirmationDialog confirmationDialog = new ConfirmationDialog();
        WaitUtils.elementShouldBeVisible(confirmationDialog.getCancelDialogButton(), true);
        confirmationDialog.getCancelDialogButton().click();
    }

    public static void closeErrorDialog() {
        ErrorDialogInteractions.clickOkButton();
    }

    public static void takeCameraPicture() {
        By takePictureButtonSelector = By.xpath("//android.widget.ImageView[contains(@resource-id,'shutter_button')]");
        By doneButtonSelector = By.xpath("//android.widget.ImageButton[contains(@resource-id,'done_button')]");
        By permissionAllowButton = By.xpath("//android.widget.Button[contains(@resource-id,'permission_allow_button')]");
        AppiumDriver webDriver = DriverBuilder.getInstance().getAppiumDriver();

        AppiumUtils.switchApplicationContext(AppContexts.NATIVE_CONTEXT);
        //TODO: Hack to allow permission, as AUTO_ALLOW_PERMISSION capability not working
        BaseUtils.waitABit(3000);
        if (webDriver.findElements(permissionAllowButton).size() > 0) {
            webDriver.findElement(permissionAllowButton).click();
            BaseUtils.waitABit(3000);
            webDriver.findElement(permissionAllowButton).click();
        }


        WaitUtils.waitUntilElementIsClickable(takePictureButtonSelector);
        webDriver.findElement(takePictureButtonSelector).click();
        WaitUtils.waitUntilElementIsClickable(doneButtonSelector);
        webDriver.findElement(doneButtonSelector).click();
        AppiumUtils.switchApplicationContext(AppContexts.WEBVIEW_CONTEXT);
    }

    public static void logIn(Employee employee) {
        VNextLoginScreen loginscreen = new VNextLoginScreen(DriverBuilder.getInstance().getAppiumDriver());
        loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeePassword());
    }
}