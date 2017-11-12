package com.cyberiansoft.test.inhouse.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class TeamPortalCategoriesPage extends  BasePage {
    public TeamPortalCategoriesPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


} 
