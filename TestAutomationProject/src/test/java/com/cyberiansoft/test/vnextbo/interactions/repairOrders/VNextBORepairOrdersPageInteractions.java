package com.cyberiansoft.test.vnextbo.interactions.repairOrders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBORepairOrdersWebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class VNextBORepairOrdersPageInteractions {

    public VNextBORepairOrdersWebPage repairOrdersPage;

    public VNextBORepairOrdersPageInteractions() {
        repairOrdersPage = PageFactory.initElements(
                DriverBuilder.getInstance().getDriver(), VNextBORepairOrdersWebPage.class);
    }

    private boolean isWorkOrderDisplayed(String text) {
        try {
            return WaitUtilsWebDriver
                    .getWait()
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.visibilityOf(repairOrdersPage.getTableBody()
                            .findElement(By.xpath(".//strong[contains(text(), \"" + text + "\")]"))))
                    .isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isWorkOrderDisplayedByName(String name) {
        return isWorkOrderDisplayed(name);
    }

    public void clickTechniciansFieldForWO(String woNumber) {
        Utils.clickElement(repairOrdersPage.getTechniciansFieldForWO(woNumber));
    }

    public String getTechniciansValueForWO(String woNumber) {
        return Utils.getText(repairOrdersPage.getTechniciansFieldForWO(woNumber));
    }

    public void clickWoLink(String woNumber) {
        WaitUtilsWebDriver.waitForLoading();
        Utils.clickElement(By.xpath("//a[@class='order-no']/strong[text()='" + woNumber + "']/.."));
        WaitUtilsWebDriver.waitForLoading();
    }
}