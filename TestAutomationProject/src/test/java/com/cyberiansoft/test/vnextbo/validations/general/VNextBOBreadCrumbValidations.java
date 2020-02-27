package com.cyberiansoft.test.vnextbo.validations.general;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBreadCrumbPanel;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class VNextBOBreadCrumbValidations {

    public static boolean isBreadCrumbClickable() {
        return Utils.isElementClickable(new VNextBOBreadCrumbPanel().getBreadCrumbsLink());
    }

    public static boolean isLastBreadCrumbDisplayed() {
        return Utils.isElementDisplayed(new VNextBOBreadCrumbPanel().getLastBreadCrumb());
    }

    public static boolean isLocationSet(String location) {
        return Utils.isTextDisplayed(new VNextBOBreadCrumbPanel().getLocationName(), location);
    }

    public static boolean isLocationSet(String location, int timeOut) {
        return Utils.isTextDisplayed(new VNextBOBreadCrumbPanel().getLocationName(), location, timeOut);
    }

    public static boolean isLocationSelected(String location) {
        try {
            WaitUtilsWebDriver.getWait()
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions
                            .presenceOfElementLocated(By
                                    .xpath("//div[@class='add-notes-item menu-item active']//label[text()='" + location + "']")));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public static boolean isLocationExpanded() {
        return WaitUtilsWebDriver.elementShouldBeVisible(new VNextBOBreadCrumbPanel().getLocationExpanded(), true, 2);
    }

    public static boolean isLocationCollapsed() {
        return WaitUtilsWebDriver.elementShouldBeVisible(new VNextBOBreadCrumbPanel().getLocationExpanded(), false, 2);
    }

    public static boolean isLocationSearched(String searchLocation) {
        final VNextBOBreadCrumbPanel breadCrumbPanel = new VNextBOBreadCrumbPanel();
        try {
            WaitUtilsWebDriver.waitForVisibility(breadCrumbPanel.getLocationExpanded(), 3);
        } catch (Exception e) {
            VNextBOBreadCrumbInteractions.clickLocationName();
        }
        final int locationsNum = VNextBOBreadCrumbInteractions.clearAndTypeLocation(searchLocation);
        try {
            WaitUtilsWebDriver.getShortWait().until((ExpectedCondition<Boolean>) driver -> breadCrumbPanel
                    .getLocationLabels()
                    .size() != locationsNum);
        } catch (Exception e) {
            WaitUtilsWebDriver.waitABit(2000);
        }

        return breadCrumbPanel.getLocationLabels()
                .stream()
                .allMatch(label -> label
                        .getText()
                        .toLowerCase()
                        .contains(searchLocation.toLowerCase()));
    }
}
