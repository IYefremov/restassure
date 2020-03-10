package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.interactions.ErrorDialogInteractions;
import com.cyberiansoft.test.vnext.screens.VNextLoginScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.ConfirmationDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class GeneralSteps {

    public static void confirmDialog() {
        ConfirmationDialog confirmationDialog = new ConfirmationDialog();
        WaitUtils.elementShouldBeVisible(confirmationDialog.getConfirmDialogButton(), true);
        WaitUtils.click(confirmationDialog.getConfirmDialogButton());
        WaitUtils.elementShouldBeVisible(confirmationDialog.getConfirmDialogButton(), false);
    }

    public static void acceptDialog() {
        ConfirmationDialog confirmationDialog = new ConfirmationDialog();
        WaitUtils.elementShouldBeVisible(confirmationDialog.getCancelDialogButton(), true);
        WaitUtils.click(confirmationDialog.getCancelDialogButton());
    }

    public static void declineDialog() {
        ConfirmationDialog confirmationDialog = new ConfirmationDialog();
        WaitUtils.elementShouldBeVisible(confirmationDialog.getCancelDialogButton(), true);
        WaitUtils.click(confirmationDialog.getCancelDialogButton());
    }

    public static void closeErrorDialog() {
        ErrorDialogInteractions.clickOkButton();
    }

    public static void logIn(Employee employee) {
        VNextLoginScreen loginscreen = new VNextLoginScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        SearchSteps.textSearch(employee.getEmployeeName());
        loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeePassword());
    }

    public static void skipGuide() {
        WebDriver webDriver = ChromeDriverProvider.INSTANCE.getMobileChromeDriver();
        webDriver.switchTo().frame("wfx-frame-guidedPopup");
        JavascriptExecutor executor = (JavascriptExecutor) webDriver;
        executor.executeScript("arguments[0].click();", webDriver.findElement(By.xpath("//button[text()='SKIP']")));
        webDriver.switchTo().defaultContent();
    }
}