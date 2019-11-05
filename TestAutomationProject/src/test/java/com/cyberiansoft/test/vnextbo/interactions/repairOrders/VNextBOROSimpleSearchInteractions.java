package com.cyberiansoft.test.vnextbo.interactions.repairOrders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBOROWebPage;
import org.openqa.selenium.Keys;

public class VNextBOROSimpleSearchInteractions {

    private VNextBOROWebPage repairOrdersPage;

    public VNextBOROSimpleSearchInteractions() {
        repairOrdersPage = new VNextBOROWebPage();
    }

    public void setRepairOrdersSearchText(String repairOrderText) {
        WaitUtilsWebDriver.waitForVisibility(repairOrdersPage.getRepairOrdersSearchTextField());
        Utils.clearAndType(repairOrdersPage.getRepairOrdersSearchTextField(), repairOrderText);
        WaitUtilsWebDriver.waitABit(500);
    }

    public void clickSearchIcon() {
        Utils.clickElement(repairOrdersPage.getSearchIcon());
        WaitUtilsWebDriver.waitForLoading();
    }

    public void clickEnterToSearch() {
        Utils.getActions().sendKeys(repairOrdersPage.getSearchInput(), Keys.ENTER).build().perform();
        WaitUtilsWebDriver.waitForLoading();
    }
}