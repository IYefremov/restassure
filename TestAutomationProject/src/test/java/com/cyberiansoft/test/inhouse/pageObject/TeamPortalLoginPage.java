package com.cyberiansoft.test.inhouse.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TeamPortalLoginPage extends BasePage {

    @FindBy(id = "Google")
    WebElement loginGmailBTN;

    @FindBy(id = "identifierId")
    WebElement emailField;

    @FindBy(id = "identifierNext")
    WebElement loginNextBTN;

    @FindBy(id = "passwordNext")
    WebElement passwordNextBTN;

    @FindBy(id = "password")
    WebElement passwordBlock;

    public TeamPortalLoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void loginByGmail() {
        loginGmailBTN.click();
        emailField.sendKeys("vozniuk@cyberiansoft.com");
        loginNextBTN.click();
        passwordBlock.findElement(By.tagName("input")).sendKeys("Viperpiper1");
        
        
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        passwordNextBTN.click();
    }
}
