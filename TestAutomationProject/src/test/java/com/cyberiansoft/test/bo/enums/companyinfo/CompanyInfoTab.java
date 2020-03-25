package com.cyberiansoft.test.bo.enums.companyinfo;

import lombok.Getter;

@Getter
public enum CompanyInfoTab {

    COMPANY_SETTINGS("Company Settings"),
    COMPANY_PROFILE("Company Profile"),
    NOTIFICATION_SETTINGS("Notification Settings"),
    INTEGRATION_SETTINGS("Integration Settings"),
    SPLASH_SCREEN("Splash Screen");

    private String tab;

    CompanyInfoTab(String tab) {
        this.tab = tab;
    }
}
