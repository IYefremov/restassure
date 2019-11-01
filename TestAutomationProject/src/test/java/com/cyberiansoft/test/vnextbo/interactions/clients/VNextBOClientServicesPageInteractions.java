package com.cyberiansoft.test.vnextbo.interactions.clients;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.clients.clientdetails.VNextBOClientsClientServicesPage;
import org.openqa.selenium.support.PageFactory;

public class VNextBOClientServicesPageInteractions {

    private VNextBOClientsClientServicesPage clientServicesPage;

    public VNextBOClientServicesPageInteractions() {

        clientServicesPage = PageFactory.initElements(DriverBuilder.getInstance().getDriver(),
                VNextBOClientsClientServicesPage.class);
    }

    public void clickClientServicesBackButton() {

        Utils.clickElement(clientServicesPage.getClientServicesBackButton());
        WaitUtilsWebDriver.waitForLoading();
    }

    public void setServicePackage(String packageName) {

        Utils.clickElement(clientServicesPage.getServicesPackageDropDownField());
        Utils.selectOptionInDropDown(clientServicesPage.getServicesPackageDropDownField(),
                clientServicesPage.getServicesPackageDropDownOptions(), packageName, true);
        WaitUtilsWebDriver.waitForLoading();
    }
}