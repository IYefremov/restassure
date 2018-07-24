package com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces;

public interface IBaseWizardScreen {

    public <T extends IBaseWizardScreen> T selectNextScreen(String screenname, Class<T> type);
    public <T extends ITypeScreen> T getTypeScreenFromContext();
}
