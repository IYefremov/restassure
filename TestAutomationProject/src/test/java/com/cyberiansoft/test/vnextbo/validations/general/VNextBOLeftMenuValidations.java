package com.cyberiansoft.test.vnextbo.validations.general;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import org.openqa.selenium.WebElement;

import java.util.Objects;

public class VNextBOLeftMenuValidations {

    public static boolean isMenuButtonDisplayed() {
        return Utils.isElementDisplayed(new VNexBOLeftMenuPanel().getMenuButton());
    }

    public static boolean isSettingsMenuTabDisplayed() {
        try {
            return Utils.isElementDisplayed(new VNexBOLeftMenuPanel().getSettingsMenuTab());
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean isMainMenuExpanded() {
        return new VNextBOBaseWebPage(DriverBuilder.getInstance().getDriver()).getBodyElement().getAttribute("class").contains("left-menu--open");
    }

    public static boolean isMainMenuItemCollapsed(String mainMenuItemName) {
        final WebElement mainMenu = new VNexBOLeftMenuPanel().mainMenuItemByName(mainMenuItemName);
        WaitUtilsWebDriver.elementShouldBeVisible(mainMenu, true, 4);
        final String attribute = mainMenu.getAttribute("aria-expanded");
        return Objects.isNull(attribute) || attribute.equals("false");
    }
}
