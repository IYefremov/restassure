package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
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

    public VNextPayInvoicesScreen(WebDriver appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
        BaseUtils.waitABit(3000);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
        wait.until(ExpectedConditions.visibilityOf(payscreen));
        BaseUtils.waitABit(2000);
        wait = new WebDriverWait(appiumdriver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//iframe[@class='invoice-pay-iframe']")));

        appiumdriver.switchTo().frame(payframe);
    }

    public void setCardNumber(String cardNumber) {

        //WaitUtils.getGeneralFluentWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='Number']")));
        //WaitUtils.waitUntilElementIsClickable(cardnumberfld);
        appiumdriver.switchTo().defaultContent();
        appiumdriver.switchTo().frame(payframe);
        ((JavascriptExecutor) appiumdriver).executeScript("arguments[0].setAttribute('value', arguments[1])", cardnumberfld, cardNumber);
    }

    public void setCVC(String cvcValue) {
        cvcfld.clear();
        cvcfld.sendKeys(cvcValue);
    }

    public void selectExpirationMonth(String expMonth) {
        ((JavascriptExecutor) ChromeDriverProvider.INSTANCE.getMobileChromeDriver())
                .executeScript("arguments[0].click();", expirationmonthcmb);

        WebElement parent = (WebElement) ((JavascriptExecutor) appiumdriver)
                .executeScript(
                        "return arguments[0].parentNode;", expirationmonthcmb);
        ((JavascriptExecutor) ChromeDriverProvider.INSTANCE.getMobileChromeDriver())
                .executeScript("arguments[0].click();", parent.findElement(By.xpath(".//li/a/span[text()='" + expMonth + "']")));
        ((JavascriptExecutor) ChromeDriverProvider.INSTANCE.getMobileChromeDriver())
                .executeScript("arguments[0].click();", expirationmonthcmb);
        BaseUtils.waitABit(1000);
    }

    public void selectExpirationYear(String expYear) {
        ((JavascriptExecutor) ChromeDriverProvider.INSTANCE.getMobileChromeDriver())
                .executeScript("arguments[0].click();", expirationyearcmb);
        WebElement parent = (WebElement) ((JavascriptExecutor) appiumdriver)
                .executeScript(
                        "return arguments[0].parentNode;", expirationyearcmb);
        BaseUtils.waitABit(1000);
        ((JavascriptExecutor) ChromeDriverProvider.INSTANCE.getMobileChromeDriver())
                .executeScript("arguments[0].click();", parent.findElement(By.xpath(".//li/a/span[text()='" + expYear + "']")));
        ((JavascriptExecutor) ChromeDriverProvider.INSTANCE.getMobileChromeDriver())
                .executeScript("arguments[0].click();", expirationyearcmb);
        BaseUtils.waitABit(1000);
    }

    public void clickPayButton() {
        WaitUtils.waitUntilElementIsClickable(paybtn);
        JavascriptExecutor executor = (JavascriptExecutor) ChromeDriverProvider.INSTANCE.getMobileChromeDriver();
        executor.executeScript("arguments[0].click();", paybtn);
        appiumdriver.switchTo().defaultContent();
        BaseUtils.waitABit(1000);
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
        return new VNextInvoicesScreen();
    }

    public String clickInformationDialogOKButtonAndGetMessage() {
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        String msg = informationDialog.getInformationDialogMessage();
        ((JavascriptExecutor) ChromeDriverProvider.INSTANCE.getMobileChromeDriver())
                .executeScript("arguments[0].click();", appiumdriver.findElement(By.xpath(".//span[text()='OK']")));
        return msg;
    }
}
