package com.cyberiansoft.test.inhouse.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class TeamPortalAgreementApprovePage extends BasePage{
    public TeamPortalAgreementApprovePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
} 
