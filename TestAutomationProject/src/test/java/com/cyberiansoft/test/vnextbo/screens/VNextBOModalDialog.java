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

    @FindBy(xpath = "//div[@class='modal-content']//button[@data-automation-id='modalCloseButton']//ancestor::div[@class='modal-content']")
    public WebElement dialogContent;

    @FindBy(xpath = "//button[@data-automation-id='modalConfirmButton' and text()='OK']")
    public WebElement confirmOKButton;

    @FindBy(xpath = "//button[@data-automation-id='modalConfirmButton' and text()='Yes']")
    public WebElement confirmYesButton;

    @FindBy(xpath = "//button[@data-automation-id='modalCancelButton' and text()='No']")
    public WebElement cancelNoButton;

    @FindBy(xpath = "//div[@class='modal-content']//button[@data-automation-id='modalCloseButton']")
    public WebElement closeButton;

    public WebElement dialogHeader() {
        return dialogContent.findElement(By.className("modal-title"));
    }

    public WebElement dialogInformationMessage() {
        return dialogContent.findElement(By.className("modal-body__content"));
    }

    public VNextBOModalDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        new WebDriverWait(driver, 30)
                .until(ExpectedConditions.visibilityOf(closeButton));
    }
}
