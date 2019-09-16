package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBOROWebPage;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class VNextBOPrivacyPolicyDialog extends VNextBOBaseWebPage {

    @FindBy(id = "dialogModal")
    private WebElement privacyPolicyDialog;

    @FindBy(xpath = "//div[@id='dialogModal']//div[@class='modal-body__content']/div/p[last()]")
    private WebElement lastElement;

    @FindBy(xpath = "//div[@id='dialogModal']//button[@data-automation-id='modalCloseButton']")
    private WebElement rejectButton;

    @FindBy(xpath = "//div[@id='dialogModal']//button[@data-automation-id='modalConfirmButton']")
    private WebElement okButton;

    public VNextBOPrivacyPolicyDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        wait.until(ExpectedConditions.visibilityOf(privacyPolicyDialog));
    }

    public VNextBOPrivacyPolicyDialog scrollPrivacyPolicyDown() {
        scrollToElement(lastElement);
        return this;
    }

    public VNextBOROWebPage rejectPrivacyPolicy() {
        return clickPrivacyPolicyButton(rejectButton);
    }

    public VNextBOROWebPage acceptPrivacyPolicy() {
        return clickPrivacyPolicyButton(okButton);
    }

    public VNextBOROWebPage clickPrivacyPolicyButton(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        try {
            wait.until(ExpectedConditions.invisibilityOf(privacyPolicyDialog));
        } catch (TimeoutException e) {
            clickWithJS(element);
            waitABit(1000);
            Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOf(privacyPolicyDialog)),
                    "The Privacy Policy dialog hasn't been closed");
        }
        return PageFactory.initElements(driver, VNextBOROWebPage.class);
    }
}