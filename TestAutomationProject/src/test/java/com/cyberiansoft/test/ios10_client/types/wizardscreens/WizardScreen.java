package com.cyberiansoft.test.ios10_client.types.wizardscreens;

public class WizardScreen {

    private WizardScreenTypes screeenType;
    private String screenName = "";

    public WizardScreen(WizardScreenTypes screeenType, String screenName) {
        this.screeenType = screeenType;
        this.screenName = screenName;
    }

    public WizardScreenTypes getWizardCreen() {
        return screeenType;
    }

}
