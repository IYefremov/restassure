package com.cyberiansoft.test.ios10_client.types.wizardscreens;

public enum WizardScreenTypes {

    VEHICLE_INFO("Vehicle"),
    SERVICES("Services"),
    INVOICE_INFO("Info"),
    CLAIM("Claim"),
    ORDER_SUMMARY("Summary"),
    QUESTIONS("Questions"),
    QUESTION_ANSWER("Questions"),
    ENTERPRISE_BEFORE_DAMAGE("Enterprise Before Damage"),
    PRICE_MATRIX("Price Matrix"),
    VISUAL_EXTERIOR("Exterior"),
    VISUAL("Visual"),
    VISUAL_INTERIOR("Interior");


    private final String screenType;

    WizardScreenTypes(final String screenType) {
        this.screenType = screenType;
    }

    public String getDefaultScreenTypeName() {
        return screenType;
    }
}
