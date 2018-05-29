package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnext.utils.AppContexts;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextPayInvoicesScreen extends VNextBaseScreen {

    @FindBy(xpath="//div[@data-automations-id='pay']")
    private WebElement payscreen;

    @FindBy(xpath="//iframe[@class='invoice-pay-iframe']")
    private WebElement payframe;

    @FindBy(xpath="//input[@id='Number']")
    private WebElement cardnumberfld;

    @FindBy(xpath="//button[@data-id='ExpirationMonth']")
    private WebElement expirationmonthcmb;

    @FindBy(xpath="//button[@data-id='ExpirationYear']")
    private WebElement expirationyearcmb;

    @FindBy(xpath="//input[@id='CVC']")
    private WebElement cvcfld;

    @FindBy(xpath="//input[@id='FullName']")
    private WebElement fullnamefld;

    @FindBy(xpath="//input[@value='Pay']")
    private WebElement paybtn;

    public VNextPayInvoicesScreen(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
        wait.until(ExpectedConditions.visibilityOf(payscreen));
        wait = new WebDriverWait(appiumdriver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//iframe[@class='invoice-pay-iframe']")));
        BaseUtils.waitABit(1000);
        appiumdriver.switchTo().frame(payframe);
    }

    public void setCardNumber(String cardNumber) {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='Number']")));
        cardnumberfld.clear();
        cardnumberfld.sendKeys(cardNumber);
    }

    public void setCVC(String cvcValue) {
        cvcfld.clear();
        cvcfld.sendKeys(cvcValue);
    }

    public void selectExpirationMonth(String expMonth) {
        tap(expirationmonthcmb);
        WebElement parent = (WebElement) ((JavascriptExecutor) appiumdriver)
                .executeScript(
                        "return arguments[0].parentNode;", expirationmonthcmb);
        tap(parent.findElement(By.xpath(".//li/a/span[text()='" + expMonth + "']")));
    }

    public void selectExpirationYear(String expYear) {
        tap(expirationyearcmb);
        WebElement parent = (WebElement) ((JavascriptExecutor) appiumdriver)
                .executeScript(
                        "return arguments[0].parentNode;", expirationyearcmb);
        tap(parent.findElement(By.xpath(".//li/a/span[text()='" + expYear + "']")));
    }

    public void clickPayButton() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@value='Pay']")));

        tap(paybtn);
        appiumdriver.switchTo().defaultContent();
    }

    public boolean isCardNumberRequiredErrorDisplayed() {
        appiumdriver.switchTo().frame(payframe);
        boolean required =  appiumdriver.findElement(By.xpath("//span[@for='Number' and text()='Required']")).isDisplayed();
        return required;
    }

    public boolean isExpirationMonthRequiredErrorDisplayed() {
        boolean required =  appiumdriver.findElement(By.xpath("//span[@for='ExpirationMonth' and text()='Required']")).isDisplayed();
        return required;
    }

    public boolean isExpirationYearRequiredErrorDisplayed() {
        boolean required =  appiumdriver.findElement(By.xpath("//span[@for='ExpirationYear' and text()='Required']")).isDisplayed();
        return required;
    }

    public boolean isCVCRequiredErrorDisplayed() {
        boolean required =  appiumdriver.findElement(By.xpath("//span[@for='CVC' and text()='Required']")).isDisplayed();
        return required;
    }

    public VNextInvoicesScreen clickBackButton() {
        appiumdriver.switchTo().defaultContent();
        clickScreenBackButton();
        return new VNextInvoicesScreen(appiumdriver);
    }
}
