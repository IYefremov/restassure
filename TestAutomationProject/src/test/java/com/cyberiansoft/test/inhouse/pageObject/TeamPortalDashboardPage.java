package com.cyberiansoft.test.inhouse.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class TeamPortalDashboardPage extends BasePage {

    public TeamPortalDashboardPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
}
