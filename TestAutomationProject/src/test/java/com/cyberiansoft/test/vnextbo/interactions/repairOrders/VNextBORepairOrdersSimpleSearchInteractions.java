package com.cyberiansoft.test.vnextbo.interactions.repairOrders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBORepairOrdersWebPage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.PageFactory;

public class VNextBORepairOrdersSimpleSearchInteractions {

    public VNextBORepairOrdersWebPage repairOrdersPage;

    public VNextBORepairOrdersSimpleSearchInteractions() {
        repairOrdersPage = PageFactory.initElements(
                DriverBuilder.getInstance().getDriver(), VNextBORepairOrdersWebPage.class);
    }

    public void setRepairOrdersSearchText(String repairOrderText) {
        WaitUtilsWebDriver.waitForVisibility(repairOrdersPage.getRepairOdersSearchTextField());
        Utils.clearAndType(repairOrdersPage.getRepairOdersSearchTextField(), repairOrderText);
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