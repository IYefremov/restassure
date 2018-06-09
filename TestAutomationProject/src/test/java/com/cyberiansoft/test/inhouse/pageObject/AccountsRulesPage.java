package com.cyberiansoft.test.inhouse.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class AccountsRulesPage extends RulesPage {

    @FindBy(xpath = "//div[@class='ms-trigger']")
    private List<WebElement> newRuleAccountsListExpandArrow;

    @FindBy(xpath = "//div[@class='ms-trigger']")
    private List<WebElement> newRuleOrganisationListExpandArrow;


    public AccountsRulesPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
}
