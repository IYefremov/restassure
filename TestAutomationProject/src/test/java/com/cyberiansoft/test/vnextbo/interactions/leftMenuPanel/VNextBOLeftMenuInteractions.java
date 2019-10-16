package com.cyberiansoft.test.vnextbo.interactions.leftMenuPanel;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.enums.MainMenuItems;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.deviceManagement.VNextBODeviceManagementWebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class VNextBOLeftMenuInteractions {

    private VNexBOLeftMenuPanel leftMenuPanel;

    public VNextBOLeftMenuInteractions() {
        leftMenuPanel = PageFactory.initElements(DriverBuilder.getInstance().getDriver(), VNexBOLeftMenuPanel.class);
    }

    public void selectInspectionsMenu() {
        selectMenuItem(leftMenuPanel.getInspectionsMenu(), MainMenuItems.OPERATIONS.getMenu());
    }

    public void selectInvoicesMenu() {
        selectMenuItem(leftMenuPanel.getInvoicesMenu(), MainMenuItems.OPERATIONS.getMenu());
    }

    public void selectPartsManagementMenu() {
        selectMenuItem(leftMenuPanel.getPartsManagementMenu(), MainMenuItems.OPERATIONS.getMenu());
    }

    public void selectUsersMenu() {
        selectMenuItem(leftMenuPanel.getUsersMenu(), MainMenuItems.SETTINGS.getMenu());
    }

    public void selectRepairOrdersMenu() {
        selectMenuItem(leftMenuPanel.getRepairOrdersMenu(), MainMenuItems.MONITOR.getMenu());
    }

    public void selectServicesMenu() {
        selectMenuItem(leftMenuPanel.getServicesMenu(), MainMenuItems.SETTINGS.getMenu());
    }

    public void selectQuickNotesMenu() {
        selectMenuItem(leftMenuPanel.getQuickNotesMenu(), MainMenuItems.SETTINGS.getMenu());
    }

    public void selectCompanyInfoMenu() {
        selectMenuItem(leftMenuPanel.getCompanyInfoMenu(), MainMenuItems.SETTINGS.getMenu());
    }

    public void selectClientsMenu() {
        selectMenuItem(leftMenuPanel.getClientsMenu(), MainMenuItems.SETTINGS.getMenu());
    }

    public void selectDeviceManagementMenu() {
        selectMenuItem(leftMenuPanel.getDeviceManagementMenu(), MainMenuItems.SETTINGS.getMenu());
        final VNextBODeviceManagementWebPage deviceManagementWebPage =
                PageFactory.initElements(DriverBuilder.getInstance().getDriver(), VNextBODeviceManagementWebPage.class);
        WaitUtilsWebDriver.waitForVisibilityIgnoringException(deviceManagementWebPage.getDeviceManagementBreadCrumb(), 5);
    }

    public boolean isUsersMenuItemExists() {
        if (!isMainMenuExpanded()) {
            expandMainMenu();
        }
        return DriverBuilder.getInstance().getDriver().findElement(By.xpath("//*[@data-automation-id='users']")).isDisplayed();
    }

    public boolean isMainMenuExpanded() {
        DriverBuilder.getInstance().getDriver().switchTo().defaultContent();
        WaitUtilsWebDriver.waitForVisibility(leftMenuPanel.getBody());
        return Utils.isElementWithAttributeContainingValueDisplayed(leftMenuPanel.getBody(), "class", "left-menu--open", 10);
    }

    public void expandMainMenu() {
        if (!isMainMenuExpanded()) {
            Utils.clickElement(leftMenuPanel.getMenuButton());
            WaitUtilsWebDriver.waitForAttributeToContain(leftMenuPanel.getBody(), "class", "left-menu--open");
        }
    }

    public void collapseMainMenu() {
        if (isMainMenuExpanded()) {
            Utils.clickElement(leftMenuPanel.getMenuButton());
            WaitUtilsWebDriver.waitForAttributeToBe(leftMenuPanel.getBody(), "class", "body-mobile--scroll-hidden");
        }
    }

    private void clickMainMenuItem(String mainMenu) {
        final WebElement menuElement = leftMenuPanel
                .getMainMenu()
                .findElement(By.xpath(".//span[contains(text(), '" + mainMenu + "')]"));
        WaitUtilsWebDriver.waitForElementNotToBeStale(menuElement);
        Utils.clickElement(menuElement);
        WaitUtilsWebDriver.waitABit(1000);
    }

    private void selectMenuItem(WebElement menuitem, String mainMenuItem) {
        try {
            DriverBuilder.getInstance().getDriver().switchTo().frame(leftMenuPanel.getTutorialFrame());
            WaitUtilsWebDriver.waitABit(1000);
            Utils.clickElement(leftMenuPanel.getTutorialSkipButton());
        } catch (Exception ignored) {}

        DriverBuilder.getInstance().getDriver().switchTo().defaultContent();
        expandMainMenu();
        clickMainMenuItem(mainMenuItem);
        Utils.clickElement(menuitem);
        WaitUtilsWebDriver.waitForLoading();
    }

    public boolean isMenuButtonDisplayed() {
        return Utils.isElementDisplayed(leftMenuPanel.getMenuButton(), 10);
    }

    public boolean isSettingsMenuTabDisplayed() {
        return Utils.isElementDisplayed(leftMenuPanel.getSettingsMenuTab(), 10);
    }

    public boolean isSettingsMenuTabAbsent() {
        return Utils.isElementNotDisplayed(leftMenuPanel.getSettingsMenuTab(), 5);
    }
}