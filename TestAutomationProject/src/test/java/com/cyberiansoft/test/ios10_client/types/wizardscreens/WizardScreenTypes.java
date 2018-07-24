package com.cyberiansoft.test.ios10_client.types.wizardscreens;

public enum WizardScreenTypes {

    VEHICLEINFO("Vehicle"),
    SERVICES("Services");

    private final String screenType;

    WizardScreenTypes(final String screenType) {
        this.screenType = screenType;
    }

    public String getDefaultScreenTypeName() {
        return screenType;
    }
}
