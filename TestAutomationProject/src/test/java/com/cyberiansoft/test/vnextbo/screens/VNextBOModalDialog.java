package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class VNextBOModalDialog extends VNextBOBaseWebPage {

    private WebElement dialogContent;

    @FindBy(xpath = "//button[@data-automation-id='modalConfirmButton' and text()='OK']")
    private WebElement confirmOKButton;

    @FindBy(xpath = "//button[@data-automation-id='modalConfirmButton' and text()='Yes']")
    private WebElement confirmYesButton;

    @FindBy(xpath = "//button[@data-automation-id='modalCancelButton' and text()='No']")
    private WebElement cancelNoButton;

    @FindBy(xpath = "//div[@class='modal-content']//button[@data-automation-id='modalCloseButton']")
    private WebElement closeButton;

    public VNextBOModalDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        new WebDriverWait(driver, 30)
                .until(ExpectedConditions.visibilityOf(closeButton));
    }

    public boolean isDialogDisplayed()  { return  Utils.isElementDisplayed(getDialogContent()); }

    public boolean isDialogClosed()  { return  Utils.isElementNotDisplayed(getDialogContent()); }

    public boolean isOkButtonDisplayed()  { return Utils.isElementDisplayed(confirmOKButton); }

    public boolean isCloseButtonDisplayed()  { return Utils.isElementDisplayed(closeButton); }

    public boolean isYesButtonDisplayed()  { return Utils.isElementDisplayed(confirmYesButton); }

    public boolean isNoButtonDisplayed()  { return Utils.isElementDisplayed(cancelNoButton); }

    private WebElement getDialogContent() {

        dialogContent = driver.findElement(By.xpath("//div[@class='modal-content']//button[@data-automation-id='modalCloseButton']//ancestor::div[@class='modal-content']"));
        return dialogContent;
    }

    public String getDialogHeader() {
        return Utils.getText(getDialogContent().findElement(By.className("modal-title")));
    }

    public String getDialogInformationMessage() {
        return Utils.getText(getDialogContent().findElement(By.className("modal-body__content")));
    }

    public void clickOkButton() {
        Utils.clickElement(confirmOKButton);
    }

    public void clickCloseButton() {
        Utils.clickElement(closeButton);
    }

    public void clickYesButton() { Utils.clickElement(confirmYesButton); }

    public void clickNoButton() {
        Utils.clickElement(cancelNoButton);
    }
}
