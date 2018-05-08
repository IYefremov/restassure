package com.cyberiansoft.test.inhouse.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

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

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void loginByGmail() {
        loginGmailBTN.click();
        if (driver.findElements(By.id("identifierId")).size() > 0) {
        	emailField.sendKeys("test.cyberiansoft@gmail.com");
        	loginNextBTN.click();
        	passwordBlock.findElement(By.tagName("input")).sendKeys("ZZzz11!!");

        	try {
        		Thread.sleep(2000);
        	} catch (InterruptedException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
        	passwordNextBTN.click();
        }
    }
}