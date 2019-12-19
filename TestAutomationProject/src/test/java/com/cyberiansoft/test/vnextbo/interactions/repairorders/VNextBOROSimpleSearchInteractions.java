package com.cyberiansoft.test.vnextbo.interactions.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBOROWebPage;
import org.openqa.selenium.Keys;

public class VNextBOROSimpleSearchInteractions {

    public static void setRepairOrdersSearchText(String repairOrderText) {
        final VNextBOROWebPage repairOrdersPage = new VNextBOROWebPage();
        WaitUtilsWebDriver.waitForElementNotToBeStale(repairOrdersPage.getRepairOrdersSearchTextField(), 3);
        WaitUtilsWebDriver.elementShouldBeVisible(repairOrdersPage.getRepairOrdersSearchTextField(), true);
        Utils.clearAndType(repairOrdersPage.getRepairOrdersSearchTextField(), repairOrderText);
        WaitUtilsWebDriver.waitABit(500);
    }

    public static void waitForLoading() {
        WaitUtilsWebDriver.waitForVisibilityIgnoringException(new VNextBOROWebPage().getAppProgressSpinner(), 3);
        WaitUtilsWebDriver.waitForInvisibilityIgnoringException(new VNextBOROWebPage().getAppProgressSpinner(), 3);
        WaitUtilsWebDriver.waitABit(1000);
    }

    public static void clickSearchIcon() {
        Utils.clickElement(new VNextBOROWebPage().getSearchIcon());
        waitForLoading();
    }

    public static void clickEnterToSearch() {
        Utils.getActions().sendKeys(new VNextBOROWebPage().getSearchInput(), Keys.ENTER).build().perform();
        waitForLoading();
    }
}