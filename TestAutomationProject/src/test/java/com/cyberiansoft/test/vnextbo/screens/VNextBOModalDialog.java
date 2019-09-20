package com.cyberiansoft.test.vnextbo.screens;

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

    @FindBy(className = "modal-content")
    private WebElement modalDialog;

    @FindBy(xpath = "//button[@data-automation-id='modalConfirmButton']")
    private WebElement confirmOKButton;

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
        new WebDriverWait(driver, 30)
                .until(ExpectedConditions.visibilityOf(driver.findElement(By.className("modal-title"))));
        return driver.findElement(By.className("modal-title")).getText();
    }

    public String getDialogInformationMessage() {
        new WebDriverWait(driver, 30)
                .until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@class='modal-body__content']/div"))));
        return driver.findElement(By.xpath("//div[@class='modal-body__content']/div")).getText();
    }

    public VNextBOLoginScreenWebPage clickOkButton() {
        confirmOKButton.click();
        return PageFactory.initElements(
                driver, VNextBOLoginScreenWebPage.class);
    }
}
