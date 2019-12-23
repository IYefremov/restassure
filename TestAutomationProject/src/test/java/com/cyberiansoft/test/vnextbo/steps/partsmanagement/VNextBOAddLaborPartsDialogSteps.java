package com.cyberiansoft.test.vnextbo.steps.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import com.cyberiansoft.test.vnextbo.screens.partsmanagement.VNextBOAddLaborPartsDialog;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.nio.file.WatchEvent;

public class VNextBOAddLaborPartsDialogSteps {

    private static void setLaborServiceField(String laborServiceName) {

        VNextBOAddLaborPartsDialog laborPartsDialog = new VNextBOAddLaborPartsDialog();
        Utils.clearAndType(laborPartsDialog.getSelectLaborServiceField(), laborServiceName);
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        Utils.selectOptionInDropDownWithJs(laborPartsDialog.getLaborServicesDropDown(),
                laborPartsDialog.serviceDropDownOption(laborServiceName));
    }

    private static void clickAddLaborButton() {

        Utils.clickElement(new VNextBOAddLaborPartsDialog().getAddLaborButton());
    }

    public static void populateLaborServiceField(String laborServiceName) {

        Utils.clearAndType(new VNextBOAddLaborPartsDialog().getSelectLaborServiceField(), laborServiceName);
    }

    public static void addLaborServiceToPart(String laborServiceName) {

        setLaborServiceField(laborServiceName);
        clickAddLaborButton();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    public static void closeDialogWithXIcon() {
        WebElement xIconCloseButton = new VNextBOAddLaborPartsDialog().getXIconCloseButton();
        Utils.clickElement(xIconCloseButton);
        WaitUtilsWebDriver.getShortWait().until(ExpectedConditions.invisibilityOf(xIconCloseButton));
    }

    public static void closeDialogWithCancelButton() {
        WebElement cancelButton = new VNextBOAddLaborPartsDialog().getCancelAddingLaborButton();
        Utils.clickElement(cancelButton);
        WaitUtilsWebDriver.getShortWait().until(ExpectedConditions.invisibilityOf(cancelButton));
    }

    public static void clearLaborServiceField() {
        WebElement clearServiceFieldIcon = new VNextBOAddLaborPartsDialog().getClearServiceFieldIcon();
        Utils.clickElement(clearServiceFieldIcon);
        WaitUtilsWebDriver.getShortWait().until(ExpectedConditions.invisibilityOf(clearServiceFieldIcon));
    }
}
