package com.cyberiansoft.test.inhouse.pageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class AgreementApprovePage extends BasePage {

    @FindBy(id = "FirstName")
    private WebElement firstNameField;

    @FindBy(id = "LastName")
    private WebElement lastNameField;

    @FindBy(id = "CompanyName")
    private WebElement companyNameField;

    @FindBy(className = "btn-term-and-conditions")
    private WebElement termsAndConditions;

    @FindBy(xpath = "//div[@class='pgwModal dialog text-center terms-conditions']")
    private WebElement termsAndConditionsDialog;

    @FindBy(xpath = "//div[@class='pgwModal dialog text-center']")
    private WebElement thankYouDialog;

    @FindBy(id = "btnDialogClose")
    private WebElement termsAndConditionsOkButton;

    @FindBy(xpath = "//button[@class='btn clearfix btn-link']")
    private WebElement agreeWithTermsCheckBox;

    @FindBy(xpath = "//button[@class='button btn-accept']")
    private WebElement acceptAgreementButton;

    @FindBy(id = "btnAcceptedDialogClose")
    private WebElement acceptThankYouMessage;

    @FindBy(id = "btnDialogClose")
    private WebElement successfulPaymentDialogOkButton;

    @FindBy(xpath = "//div[@id='pgwModal']//div[contains(text(),'Payment Successful!')]")
    private WebElement successfulPaymentDialog;

    @FindBy(id = "Number")
    private WebElement cardNumberField;

    @FindBy(xpath = "//button[@data-id='ExpirationYear']")
    private WebElement yearExpirationButton;

    @FindBy(xpath = "//button[@data-id='ExpirationMonth']")
    private WebElement monthExpirationButton;

    @FindBy(id = "CVC")
    private WebElement cvcField;

    @FindBy(xpath = "//button[@class='button payment-form-submit']")
    private WebElement payButton;

    @FindBy(id="btnDialogCancel")
    private WebElement cancelPayButton;

    @FindBy(id = "btnDialogPay")
    private WebElement approvePayButton;

    @FindBy(className = "dialog-payment-details")
    private WebElement paymentDetailsDialog;

    public AgreementApprovePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step
    public void openAgreementLinkFromGmail(String link) {
        driver.get(link);
    }

    @Step
    public boolean isAgreementPageOpened() {
        return wait.until(driver -> driver.getTitle().contains("Client agreement"));
    }

    @Step
    public void fillClientInfo(String firstName, String lastName, String companyName) {
        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.visibilityOf(firstNameField));
        setFirstName(firstName);
        setLastName(lastName);
        setCompanyName(companyName);
    }

    @Step
    public void setFirstName(String name) {
        wait.until(ExpectedConditions.elementToBeClickable(firstNameField)).sendKeys(name);
    }

    @Step
    public void setLastName(String name) {
        wait.until(ExpectedConditions.elementToBeClickable(lastNameField)).sendKeys(name);
    }

    @Step
    public void setCompanyName(String name) {
        wait.until(ExpectedConditions.elementToBeClickable(companyNameField)).sendKeys(name);
    }

    @Step
    public boolean checkTermsAndConditions() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(termsAndConditions)).click();
            wait.until(ExpectedConditions.visibilityOf(termsAndConditionsDialog));
            wait.until(ExpectedConditions.elementToBeClickable(termsAndConditionsOkButton)).click();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Step
    public AgreementApprovePage clickAgreeWithTermsAndConditionsButton() {
        wait.until(ExpectedConditions.elementToBeClickable(agreeWithTermsCheckBox)).click();
        return this;
    }

    @Step
    public AgreementApprovePage clickAcceptAgreementButton() {
        wait.until(ExpectedConditions.elementToBeClickable(acceptAgreementButton)).click();
        wait.until(ExpectedConditions.attributeContains(thankYouDialog, "style", "display: block;"));
        waitABit(2000);
        wait.until(ExpectedConditions.elementToBeClickable(acceptThankYouMessage)).click();
        return this;
    }

    @Step
    public AgreementApprovePage clickSuccessfulPaymentOkButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(successfulPaymentDialogOkButton)).click();
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Step
    public AgreementApprovePage fillSetupFeePayment(String cardNumber, String monthExpiration, String yearExpiration, String cvc) {
        fillCardNumberField(cardNumber);
        setMonthExpirationDate(monthExpiration);
        setYearExpirationDate(yearExpiration);
        fillCvcField(cvc);
        return this;
    }

    @Step
    private void fillCardNumberField(String cardNumber) {
        wait.until(ExpectedConditions.elementToBeClickable(cardNumberField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(cardNumberField)).sendKeys(cardNumber);
    }

    @Step
    private void setMonthExpirationDate(String monthExpiration) {
        wait.until(ExpectedConditions.elementToBeClickable(monthExpirationButton)).click();
        List<WebElement> options = monthExpirationButton.findElements(By.xpath("//following-sibling::div/ul[@class='dropdown-menu inner']/li"));
        options.stream().filter(e -> e.findElement(By.tagName("a"))
                .findElement(By.tagName("span"))
                .getText()
                .equals(monthExpiration))
                .findFirst()
                .ifPresent(WebElement::click);
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[@data-original-index='" +
                    monthExpiration + "' and contains(@class, 'selected')]")));
        } catch (Exception ignored) {
            waitABit(2000);
        }
    }

    @Step
    private void setYearExpirationDate(String yearExpiration) {
        wait.until(ExpectedConditions.elementToBeClickable(yearExpirationButton)).click();
        List<WebElement> options = driver.findElements(By.xpath("//following-sibling::div/ul[@class='dropdown-menu inner']/li"));
        options.stream().filter(e -> e.findElement(By.tagName("a"))
                .findElement(By.tagName("span"))
                .getText()
                .equals(yearExpiration))
                .findFirst()
                .ifPresent(WebElement::click);
        waitABit(500);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//li[contains(@class, 'selected')]//span[@title='" + yearExpiration + "']")));
    }

    @Step
    private void fillCvcField(String cvc) {
        wait.until(ExpectedConditions.elementToBeClickable(cvcField)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(cvcField)).sendKeys(cvc);
    }

    @Step
    public void clickPayButton(){
        wait.until(ExpectedConditions.elementToBeClickable(payButton)).click();
    }

    @Step
    public AgreementApprovePage clickCancelPayButton(){
        wait.until(ExpectedConditions.visibilityOf(cancelPayButton));
        wait.until(ExpectedConditions.elementToBeClickable(cancelPayButton)).click();
        return this;
    }


    @Step
    public AgreementApprovePage clickApprovePayButton(){
        wait.until(ExpectedConditions.visibilityOf(approvePayButton));
        wait.until(ExpectedConditions.elementToBeClickable(approvePayButton)).click();
        waitForLoading();
        return this;
    }

    @Step
    public boolean checkPayConfirmationMessage(String totalAmount, String cardNumber){
        wait.until(ExpectedConditions.visibilityOf(paymentDetailsDialog));
        List<WebElement> paymentDetails = paymentDetailsDialog.findElements(By.tagName("b"));
        String sum = paymentDetails.get(0).getText().replaceAll("[,.]", "");
        String price = paymentDetails.get(1).getText().replaceAll("\"", "");
        System.out.println(sum);
        System.out.println(price);
        String totalAmountWithRegEx = totalAmount.replaceAll("[,.]", "");
        String cardNumberWithRegEx = cardNumber.substring(12);
        System.out.println(totalAmountWithRegEx);
        System.out.println(cardNumberWithRegEx);
        return sum.contains(totalAmountWithRegEx) && price.equals(cardNumberWithRegEx);
    }
}
