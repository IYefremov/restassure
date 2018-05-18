package com.cyberiansoft.test.inhouse.pageObject;

import com.cyberiansoft.test.inhouse.config.InHouseConfigInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    @FindBy(id = "Google")
    private WebElement loginGmailBTN;

    @FindBy(id = "identifierId")
    private WebElement emailField;

    @FindBy(id = "identifierNext")
    private WebElement loginNextBTN;

    @FindBy(id = "passwordNext")
    private WebElement passwordNextBTN;

    @FindBy(id = "password")
    private WebElement passwordBlock;

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void loginByGmail() {
        wait.until(ExpectedConditions.elementToBeClickable(loginGmailBTN)).click();
        if (driver.findElements(By.id("identifierId")).size() > 0) {
        	wait.until(ExpectedConditions.visibilityOf(emailField))
                    .sendKeys(InHouseConfigInfo.getInstance().getUserEmail());
            loginNextBTN.click();
            wait.until(ExpectedConditions.visibilityOf(passwordBlock.findElement(By.tagName("input"))))
                    .sendKeys(InHouseConfigInfo.getInstance().getUserPassword());
            passwordNextBTN.click();
        }
    }
}
