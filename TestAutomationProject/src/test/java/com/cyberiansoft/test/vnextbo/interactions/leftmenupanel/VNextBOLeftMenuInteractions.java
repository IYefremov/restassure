package com.cyberiansoft.test.vnextbo.interactions.leftmenupanel;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.enums.MainMenuItems;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.devicemanagement.VNextBODeviceManagementWebPage;
import com.cyberiansoft.test.vnextbo.validations.general.VNextBOLeftMenuValidations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class VNextBOLeftMenuInteractions {

    public static void selectInspectionsMenu() {
        selectMenuItem(new VNexBOLeftMenuPanel().getInspectionsMenu(), MainMenuItems.OPERATIONS.getMenu());
    }

    public static void selectInvoicesMenu() {
        selectMenuItem(new VNexBOLeftMenuPanel().getInvoicesMenu(), MainMenuItems.OPERATIONS.getMenu());
    }

    public static void selectPartsManagementMenu() {
        selectMenuItem(new VNexBOLeftMenuPanel().getPartsManagementMenu(), MainMenuItems.OPERATIONS.getMenu());
    }

    public static void selectUsersMenu() {
        selectMenuItem(new VNexBOLeftMenuPanel().getUsersMenu(), MainMenuItems.SETTINGS.getMenu());
    }

    public static void selectRepairOrdersMenu() {
        selectMenuItem(new VNexBOLeftMenuPanel().getRepairOrdersMenu(), MainMenuItems.MONITOR.getMenu());
    }

    public static void selectServicesMenu() {
        selectMenuItem(new VNexBOLeftMenuPanel().getServicesMenu(), MainMenuItems.SETTINGS.getMenu());
    }

    public static void selectQuickNotesMenu() {
        selectMenuItem(new VNexBOLeftMenuPanel().getQuickNotesMenu(), MainMenuItems.SETTINGS.getMenu());
    }

    public static void selectCompanyInfoMenu() {
        selectMenuItem(new VNexBOLeftMenuPanel().getCompanyInfoMenu(), MainMenuItems.SETTINGS.getMenu());
    }

    public static void selectClientsMenu() {
        selectMenuItem(new VNexBOLeftMenuPanel().getClientsMenu(), MainMenuItems.SETTINGS.getMenu());
    }

    public static void selectDeviceManagementMenu() {
        selectMenuItem(new VNexBOLeftMenuPanel().getDeviceManagementMenu(), MainMenuItems.SETTINGS.getMenu());
        final VNextBODeviceManagementWebPage deviceManagementWebPage =
                PageFactory.initElements(DriverBuilder.getInstance().getDriver(), VNextBODeviceManagementWebPage.class);
        WaitUtilsWebDriver.waitForVisibilityIgnoringException(deviceManagementWebPage.getDeviceManagementBreadCrumb(), 5);
    }

    public static void expandMainMenu() {
        if (!VNextBOLeftMenuValidations.isMainMenuExpanded()) {
            final VNexBOLeftMenuPanel leftMenuPanel = new VNexBOLeftMenuPanel();
            Utils.clickElement(leftMenuPanel.getMenuButton());
            WaitUtilsWebDriver.waitForAttributeToContain(
                    leftMenuPanel.getBody(), "class", "left-menu--open", 1);
        }
    }

    public static void collapseMainMenu() {
        if (VNextBOLeftMenuValidations.isMainMenuExpanded()) {
            final VNexBOLeftMenuPanel leftMenuPanel = new VNexBOLeftMenuPanel();
            Utils.clickElement(leftMenuPanel.getMenuButton());
            WaitUtilsWebDriver.waitForAttributeToBe(leftMenuPanel.getBody(), "class", "body-mobile--scroll-hidden");
        }
    }

    private static void clickMainMenuItem(String mainMenu) {
        final WebElement menuElement = new VNexBOLeftMenuPanel()
                .getMainMenu()
                .findElement(By.xpath(".//span[contains(text(), '" + mainMenu + "')]"));
        WaitUtilsWebDriver.waitForElementNotToBeStale(menuElement);
        Utils.clickElement(menuElement);
        WaitUtilsWebDriver.waitABit(500);
    }

    private static void selectMenuItem(WebElement menuitem, String mainMenuItem) {
        expandMainMenu();
        clickMainMenuItem(mainMenuItem);
        Utils.clickElement(menuitem);
        WaitUtilsWebDriver.waitABit(1000);
    }
}