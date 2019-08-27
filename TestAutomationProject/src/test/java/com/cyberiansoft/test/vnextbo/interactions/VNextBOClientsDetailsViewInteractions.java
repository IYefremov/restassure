package com.cyberiansoft.test.vnextbo.interactions;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.clients.clientDetails.VNextBOClientsDetailsViewAccordion;
import com.cyberiansoft.test.baseutils.Utils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class VNextBOClientsDetailsViewInteractions {

    private VNextBOClientsDetailsViewAccordion detailsViewPage;

    public VNextBOClientsDetailsViewInteractions() {
        detailsViewPage = PageFactory.initElements(DriverBuilder.getInstance().getDriver(),
                VNextBOClientsDetailsViewAccordion.class);
    }

    public boolean isTabMinimized(WebElement tab) {
        WaitUtilsWebDriver.waitForVisibility(tab, 5);
        try {
            return tab.getAttribute("aria-expanded").equals("false");
        } catch (Exception ignored) {
            return false;
        }
    }

    private void collapseTab(WebElement tab) {
        if (!isTabMinimized(tab)) {
            Utils.clickElement(tab);
        }
        waitForTabToBeCollapsed(tab);
    }

    private void expandTab(WebElement tab) {
        if (isTabMinimized(tab)) {
            Utils.clickElement(tab);
        }
        waitForTabToBeExpanded(tab);
    }

    private void waitForTabToBeExpanded(WebElement tab) {
        try {
            WaitUtilsWebDriver.getShortWait().until(driver -> tab.getAttribute("aria-expanded").equals("true"));
        } catch (Exception ignored) {}
    }

    private void waitForTabToBeCollapsed(WebElement tab) {
        try {
            WaitUtilsWebDriver.getShortWait().until(driver -> tab.getAttribute("aria-expanded").equals("false"));
        } catch (NullPointerException ignored) {}
    }

    private void clickTab(WebElement tab) {
        Utils.clickElement(tab);
    }

    public void clickClientsInfoTab() {
        clickTab(detailsViewPage.getClientsInfo());
    }

    public void clickAccountInfoTab() {
        clickTab(detailsViewPage.getAccountInfo());
    }

    public void clickAddressTab() {
        clickTab(detailsViewPage.getAddress());
    }

    public void clickEmailOptionsTab() {
        clickTab(detailsViewPage.getEmailOptions());
    }

    public void clickPreferencesTab() {
        clickTab(detailsViewPage.getPreferences());
    }

    public void clickMiscellaneousTab() {
        clickTab(detailsViewPage.getMiscellaneous());
    }
}