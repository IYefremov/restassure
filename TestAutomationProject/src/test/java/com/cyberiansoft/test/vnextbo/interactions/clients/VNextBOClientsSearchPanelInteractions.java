package com.cyberiansoft.test.vnextbo.interactions.clients;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.commonobjects.VNextBOSearchPanel;
import org.openqa.selenium.support.PageFactory;

public class VNextBOClientsSearchPanelInteractions {

    private VNextBOSearchPanel clientsSearchPanel;

    public VNextBOClientsSearchPanelInteractions() {
        clientsSearchPanel = PageFactory.initElements(DriverBuilder.getInstance().getDriver(),
                VNextBOSearchPanel.class);
    }

    public void setClientsSearchText(String value) {
        Utils.clearAndType(clientsSearchPanel.getSearchInputField(), value);
        WaitUtilsWebDriver.waitABit(500);
    }

    public void clickSearchLoupeIcon() {
        Utils.clickElement(clientsSearchPanel.getSearchLoupeIcon());
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
    }

    public void clickSearchXIcon() {
        Utils.clickElement(clientsSearchPanel.getSearchXIcon());
        WaitUtilsWebDriver.waitForLoading();
    }
}