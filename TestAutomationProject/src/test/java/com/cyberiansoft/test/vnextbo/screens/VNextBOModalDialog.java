package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class VNextBOModalDialog extends VNextBOBaseWebPage {

    @FindBy(className = "modal-content")
    private WebElement modalDialog;

    @FindBy(xpath = "//button[@data-automation-id='modalConfirmButton']")
    private WebElement confirmOKButton;

    @FindBy(xpath = "//div[@class='modal-body__content']/div")
    private WebElement dialogMessage;

    @FindBy(className = "modal-title")
    private WebElement dialogTitle;

    public boolean isDialogDisplayed()  { return modalDialog.isDisplayed(); }

    public boolean isOkButtonDisplayed()  { return confirmOKButton.isDisplayed(); }

    public VNextBOModalDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        new WebDriverWait(driver, 30)
                .until(ExpectedConditions.visibilityOf(confirmOKButton));
    }

    public String getDialogHeader() {
        return Utils.getText(dialogTitle);
    }

    public String getDialogInformationMessage() {
        return Utils.getText(dialogMessage);
    }

    public void clickOkButton() {
        confirmOKButton.click();
    }
}
