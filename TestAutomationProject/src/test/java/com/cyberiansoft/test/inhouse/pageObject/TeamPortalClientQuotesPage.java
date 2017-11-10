package com.cyberiansoft.test.inhouse.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TeamPortalClientQuotesPage extends BasePage {

    @FindBy(xpath = "//button[@class='btn btn-sm blue btn-add-potential-client']")
    WebElement addClientBTN;

    @FindBy(id = "ClientName")
    WebElement newClientName;

    public TeamPortalClientQuotesPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    public void clickAddClientBTN() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@class='btn btn-sm blue btn-add-potential-client']")));
        addClientBTN.click();
    }

    public void setNewClientName(String name){
        newClientName.sendKeys(name);
    }

    public void fillNewClientProfile(String name, String nickname, String address, String address2, String zip,
   String country, String state, String city, String businessPhone, String cellPhone, String firstName,
                                     String lastName, String title, String email) {
        setNewClientName(name);
        setNewClientName(name);
        setNewClientName(name);
        setNewClientName(name);
        setNewClientName(name);
        setNewClientName(name);
        setNewClientName(name);
        setNewClientName(name);
        setNewClientName(name);

    }
}
