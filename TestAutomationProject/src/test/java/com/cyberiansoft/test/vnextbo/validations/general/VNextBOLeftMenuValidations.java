package com.cyberiansoft.test.vnextbo.validations.general;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;

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
        DriverBuilder.getInstance().getDriver().switchTo().defaultContent();
        final VNexBOLeftMenuPanel leftMenuPanel = new VNexBOLeftMenuPanel();
        WaitUtilsWebDriver.waitForVisibility(leftMenuPanel.getBody());
        return Utils.isElementWithAttributeContainingValueDisplayed(
                leftMenuPanel.getBody(), "class", "left-menu--open", 10);
    }
}
