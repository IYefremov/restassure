package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class VNextBOTermsAndConditionsDialog extends VNextBOBaseWebPage {

    @FindBy(id = "dialogModal")
    private WebElement termsAndConditionsDialog;

    @FindBy(xpath = "//section[@id='termsAndConditionsDoc']/ol[last()]/li[last()]")
    private WebElement lastElement;

    @FindBy(xpath = "//div[@id='dialogModal']//button[@data-automation-id='modalCloseButton']")
    private WebElement rejectButton;

    @FindBy(xpath = "//div[@id='dialogModal']//button[@data-automation-id='modalConfirmButton']")
    private WebElement okButton;

    public VNextBOTermsAndConditionsDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        wait.until(ExpectedConditions.visibilityOf(termsAndConditionsDialog));
    }

    public VNextBOTermsAndConditionsDialog scrollTermsAndConditionsDown() {
        scrollToElement(lastElement);
        return this;
    }

    public VNextBORepairOrdersWebPage rejectTermsAndConditions() {
        return clickTermsAndConditionsButton(rejectButton);
    }

    public VNextBORepairOrdersWebPage acceptTermsAndConditions() {
        return clickTermsAndConditionsButton(okButton);
    }

    public VNextBORepairOrdersWebPage clickTermsAndConditionsButton(WebElement element) {
        try {
            wait
                    .ignoring(WebDriverException.class)
                    .until(ExpectedConditions.elementToBeClickable(element))
                    .click();
        } catch (Exception e) {
            waitABit(1500);
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        }
        try {
            wait.until(ExpectedConditions.invisibilityOf(termsAndConditionsDialog));
        } catch (TimeoutException e) {
            clickWithJS(element);
            waitABit(1000);
            Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOf(termsAndConditionsDialog)),
                    "The Terms and Conditions dialog hasn't been closed");
        }
        return PageFactory.initElements(driver, VNextBORepairOrdersWebPage.class);
    }
}