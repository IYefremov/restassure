package com.cyberiansoft.test.vnextbo.validations.general;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Objects;

public class VNextBOLeftMenuValidations {

    public static boolean isMenuButtonDisplayed() {
        return Utils.isElementDisplayed(new VNexBOLeftMenuPanel().getMenuButton(), 10);
    }

    public static boolean isSettingsMenuTabDisplayed() {
        return Utils.isElementDisplayed(new VNexBOLeftMenuPanel().getSettingsMenuTab(), 10);
    }

    public static boolean isSettingsMenuTabAbsent() {
        return Utils.isElementNotDisplayed(new VNexBOLeftMenuPanel().getSettingsMenuTab(), 5);
    }

    public static boolean isMainMenuExpanded() {
        final VNexBOLeftMenuPanel leftMenuPanel = new VNexBOLeftMenuPanel();
        WaitUtilsWebDriver.getShortWait().until(ExpectedConditions.visibilityOf(leftMenuPanel.getBody()));
        return Utils.isElementDisplayed(leftMenuPanel.getBody());
    }

    public static boolean isMainMenuCollapsed() {
        final VNexBOLeftMenuPanel leftMenuPanel = new VNexBOLeftMenuPanel();
        try {
            WaitUtilsWebDriver.getShortWait().until(ExpectedConditions.invisibilityOf(leftMenuPanel.getBody()));
            return Utils.isElementDisplayed(leftMenuPanel.getBody());
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean isMainMenuItemCollapsed(String mainMenuItemName) {
        final WebElement mainMenu = new VNexBOLeftMenuPanel().mainMenuItemByName(mainMenuItemName);
        WaitUtilsWebDriver.elementShouldBeVisible(mainMenu, true, 4);
        final String attribute = mainMenu.getAttribute("aria-expanded");
        return Objects.isNull(attribute) || attribute.equals("false");
    }
}
