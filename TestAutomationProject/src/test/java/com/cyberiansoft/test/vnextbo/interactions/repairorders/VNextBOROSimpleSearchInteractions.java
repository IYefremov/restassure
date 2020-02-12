package com.cyberiansoft.test.vnextbo.interactions.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.repairorders.VNextBOROWebPage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

public class VNextBOROSimpleSearchInteractions {

    public static void setRepairOrdersSearchText(String repairOrderText) {
        final VNextBOROWebPage repairOrdersPage = new VNextBOROWebPage();
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        WaitUtilsWebDriver.waitForElementNotToBeStale(repairOrdersPage.getRepairOrdersSearchTextField(), 3);
        WaitUtilsWebDriver.elementShouldBeVisible(repairOrdersPage.getRepairOrdersSearchTextField(), true);
        Utils.clearAndType(repairOrdersPage.getRepairOrdersSearchTextField(), repairOrderText);
        WaitUtilsWebDriver.waitABit(500);
    }

    public static void waitForLoading() {
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.elementShouldBeVisible(new VNextBOROWebPage().getAppProgressSpinner(), true, 3);
        WaitUtilsWebDriver.elementShouldBeVisible(new VNextBOROWebPage().getAppProgressSpinner(), false, 10);
        WaitUtilsWebDriver.waitABit(1000);
    }

    public static void clickSearchIcon() {
        Utils.clickElement(new VNextBOROWebPage().getSearchIcon());
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    public static void clickEnterToSearch() {
        new Actions(DriverBuilder.getInstance().getDriver()).sendKeys(new VNextBOROWebPage().getSearchInput(), Keys.ENTER).build().perform();
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }
}