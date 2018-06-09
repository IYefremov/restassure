package com.cyberiansoft.test.inhouse.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class OrganizationsRulesPage extends RulesPage {

    @FindBy(xpath = "//div[@class='form-dialog active']//div[@class='ms-res-ctn dropdown-menu']")
    private WebElement ruleOrganisationDropDown;

    @FindBy(xpath = "//div[@class='form-dialog active']//div[@class='ms-res-ctn dropdown-menu']/div")
    private List<WebElement> ruleOrganisationOptions;


    public OrganizationsRulesPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
}
