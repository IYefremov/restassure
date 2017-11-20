package com.cyberiansoft.test.inhouse.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class TeamPortalAgreementApprovePage extends BasePage {

    @FindBy(id = "FirstName")
    WebElement firstNameField;

    @FindBy(id = "LastName")
    WebElement lastNameField;

    @FindBy(id = "CompanyName")
    WebElement companyNameField;

    @FindBy(className = "btn-term-and-conditions")
    WebElement termsAndCoditions;

    @FindBy(id = "btnDialogClose")
    WebElement okButtonTermsAndConditions;

    @FindBy(xpath = "//button[@class='btn clearfix btn-link']")
    WebElement agreeWithTermsCheckBox;

    @FindBy(xpath = "//button[@class='button btn-accept']")
    WebElement acceptAgreementButton;

    @FindBy(id = "btnDialogClose")
    WebElement acceptThankYouPMessage;

    @FindBy(id = "Number")
    WebElement cardNumberField;

    @FindBy(xpath = "//button[data-id='ExpirationYear']")
    WebElement expirationYearList;

    @FindBy(xpath = "//button[data-id='ExpirationMonth']")
    WebElement expirationMonthList;

    @FindBy(id = "CVC")
    WebElement cvcField;

    @FindBy(xpath = "//button[@class='button payment-form-submit']")
    WebElement payButton;

    @FindBy(id="btnDialogCancel")
    WebElement cancelPayButton;

    @FindBy(id = "btnDialogPay")
    WebElement approvePayButton;

    public TeamPortalAgreementApprovePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void fillClientInfo(String firstName, String lastName, String companyName) {
        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.visibilityOf(firstNameField));
        setFirstName(firstName);
        setLastName(lastName);
        setCompanyName(companyName);
    }

    public void setFirstName(String name) {
        firstNameField.sendKeys(name);
    }

    public void setLastName(String name) {
        lastNameField.sendKeys(name);
    }

    public void setCompanyName(String name) {
        firstNameField.sendKeys(name);
    }

    public boolean checkTermsAndConditions() {
        try {
            termsAndCoditions.click();
            Thread.sleep(1000);
            wait.until(ExpectedConditions.elementToBeClickable(okButtonTermsAndConditions)).click();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickAgreeWithTermsAndConditionsBTN() {
        wait.until(ExpectedConditions.elementToBeClickable(agreeWithTermsCheckBox)).click();
    }

    public void clickAcceptAgreementBTN() {
        wait.until(ExpectedConditions.elementToBeClickable(acceptAgreementButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(acceptThankYouPMessage)).click();
    }

    public void fillFeesPayment(String cardNumber, String expirationDateMonth,String expirationDateYear, String cvc) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("Number")));
        wait.until(ExpectedConditions.visibilityOf(cardNumberField));
        cardNumberField.sendKeys(cardNumber);
        expirationMonthList.click();
        List<WebElement> options = driver.findElements(By.xpath("//ul[@class='dropdown-menu inner']")).get(2).findElements(By.xpath("//li[data-original-index]"));
        options.stream().filter(e -> e.findElement(By.tagName("a")).findElement(By.tagName("span")).getText().equals(expirationDateMonth)).findFirst().get().click();
        expirationYearList.click();
        options = driver.findElements(By.xpath("//ul[@class='dropdown-menu inner']")).get(3).findElements(By.xpath("//li[data-original-index]"));
        options.stream().filter(e -> e.findElement(By.tagName("a")).findElement(By.tagName("span")).getText().equals(expirationDateYear)).findFirst().get().click();
        cvcField.sendKeys(cvc);
    }

    public void clickPayBTN(){
        payButton.click();
    }

    public void clickCancelPayBTN(){
        wait.until(ExpectedConditions.visibilityOf(cancelPayButton));
        cancelPayButton.click();
    }


    public void clickApprovePayBTN(){
        wait.until(ExpectedConditions.visibilityOf(approvePayButton));
        approvePayButton.click();
    }

    public boolean checkPayConfirmationMessage(String totalAmount ,String cardNumber){
        WebElement confirmationMessage = driver.findElements(By.className("dialog-payment-details")).get(5);
        return confirmationMessage.findElement(By.tagName("b")).getText().equals(totalAmount)&&
                confirmationMessage.findElements(By.tagName("b")).get(1).getText().equals(cardNumber);
    }
}
