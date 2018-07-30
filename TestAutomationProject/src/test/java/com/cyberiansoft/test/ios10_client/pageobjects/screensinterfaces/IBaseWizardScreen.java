package com.cyberiansoft.test.ios10_client.pageobjects.screensinterfaces;

import com.cyberiansoft.test.ios10_client.types.wizardscreens.WizardScreenTypes;

public interface IBaseWizardScreen {

    //public <T extends IBaseWizardScreen> T selectNextScreen(String screenname, Class<T> type);
    <T extends IBaseWizardScreen> T selectNextScreen(WizardScreenTypes wizardScreenType);
    public <T extends ITypeScreen> T getTypeScreenFromContext();
}
