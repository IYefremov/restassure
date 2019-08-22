package com.cyberiansoft.test.ios10_client.types.servicerequeststypes;

import com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces.IBaseWizardScreen;

public interface IServiceRequestTypes {

    public String getServiceRequestTypeName();
    public <T extends IBaseWizardScreen>T getFirstVizardScreen();
}
