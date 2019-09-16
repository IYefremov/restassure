package com.cyberiansoft.test.vnextbo.interactions.deviceManagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.deviceManagement.VNextBOPendingRegistrationWebPage;
import org.openqa.selenium.support.PageFactory;

public class VNextBOPendingRegistrationsInteractions {

    private VNextBOPendingRegistrationWebPage pendingRegistrationWebPage;

    public VNextBOPendingRegistrationsInteractions() {
        pendingRegistrationWebPage = PageFactory.initElements(
                DriverBuilder.getInstance().getDriver(), VNextBOPendingRegistrationWebPage.class);
    }

    public void clickDeleteDeviceButtonForUser(String user) {
        Utils.clickElement(pendingRegistrationWebPage.getDeleteDeviceButton(user));
    }
}