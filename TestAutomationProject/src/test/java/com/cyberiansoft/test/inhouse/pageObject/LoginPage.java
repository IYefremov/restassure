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

    @FindBy(xpath = "//img[@id='captchaimg']")
    private WebElement captcha;

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void loginByGmail() {
        wait.until(ExpectedConditions.elementToBeClickable(loginGmailBTN)).click();
        if (!driver.getCurrentUrl().contains("Dashboard")) {
            emailField.sendKeys(InHouseConfigInfo.getInstance().getUserEmail());
            wait.until(ExpectedConditions.elementToBeClickable(loginNextBTN)).click();
            try {
                wait.until(ExpectedConditions.visibilityOf(passwordBlock.findElement(By.tagName("input"))))
                        .sendKeys(InHouseConfigInfo.getInstance().getUserPassword());
                wait.until(ExpectedConditions.elementToBeClickable(passwordNextBTN)).click();
            } catch (Exception e) {
                if (!captcha.isDisplayed()) {
                    e.printStackTrace();
                } else {
                    refreshPage();
                    wait.until(ExpectedConditions.visibilityOf(emailField))
                            .sendKeys(InHouseConfigInfo.getInstance().getUserEmail2());
                    wait.until(ExpectedConditions.elementToBeClickable(loginNextBTN)).click();
                    wait.until(ExpectedConditions.visibilityOf(passwordBlock.findElement(By.tagName("input"))))
                            .sendKeys(InHouseConfigInfo.getInstance().getUserPassword2());
                    wait.until(ExpectedConditions.elementToBeClickable(passwordNextBTN)).click();
                }
            }
        }
    }
}
