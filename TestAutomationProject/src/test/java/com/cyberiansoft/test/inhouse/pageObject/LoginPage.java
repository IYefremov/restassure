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
    private WebElement loginNextButton;

    @FindBy(id = "passwordNext")
    private WebElement passwordNextButton;

    @FindBy(id = "idvanyphonecollectNext")
    private WebElement confirmPhoneNextButton;

    @FindBy(id = "phoneNumberId")
    private WebElement phoneNumberTextField;

    @FindBy(id = "password")
    private WebElement passwordBlock;

    @FindBy(xpath = "//img[@id='captchaimg']")
    private WebElement captcha;

    @FindBy(xpath = "//li[@class='C5uAFc']/div[@data-challengeindex='2']")
    private WebElement recoveryEmailConfirmationButton;

    @FindBy(id = "knowledge-preregistered-email-response")
    private WebElement recoveryEmailInputField;

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void loginByGmail() {
        wait.until(ExpectedConditions.elementToBeClickable(loginGmailBTN)).click();
        if (!driver.getCurrentUrl().contains("Dashboard")) {
            emailField.sendKeys(InHouseConfigInfo.getInstance().getUserEmail());
            wait.until(ExpectedConditions.elementToBeClickable(loginNextButton)).click();
            try {
                wait.until(ExpectedConditions.visibilityOf(passwordBlock.findElement(By.tagName("input"))))
                        .sendKeys(InHouseConfigInfo.getInstance().getUserPassword());
                wait.until(ExpectedConditions.elementToBeClickable(passwordNextButton)).click();
            } catch (Exception e) {
                if (!captcha.isDisplayed()) {
                    confirmMainGmail();
                } else {
                    refreshPage();
                    wait.until(ExpectedConditions.visibilityOf(emailField))
                            .sendKeys(InHouseConfigInfo.getInstance().getUserEmail2());
                    wait.until(ExpectedConditions.elementToBeClickable(loginNextButton)).click();
                    wait.until(ExpectedConditions.visibilityOf(passwordBlock.findElement(By.tagName("input"))))
                            .sendKeys(InHouseConfigInfo.getInstance().getUserPassword2());
                    wait.until(ExpectedConditions.elementToBeClickable(passwordNextButton)).click();
                    try {
                        wait.until(ExpectedConditions.urlContains("Dashboard"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    private void confirmMainGmail() {
        wait.until(ExpectedConditions.visibilityOf(recoveryEmailConfirmationButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(recoveryEmailInputField)).clear();
        recoveryEmailInputField.sendKeys(InHouseConfigInfo.getInstance().getEmailVerificationAddress());
        // nextButton.click
        //todo add locator for the "Next" button. It's not reproducible now.
    }
}
