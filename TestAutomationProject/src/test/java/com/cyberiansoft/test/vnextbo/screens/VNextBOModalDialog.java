package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;

@Getter
public class VNextBOModalDialog extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[@id='dialogModal']//div[contains(@class,'modal-content')]")
    private WebElement dialogContent;

    @FindBy(xpath = "//button[@data-automation-id='modalConfirmButton' and text()='OK']")
    private WebElement confirmOKButton;

    @FindBy(xpath = "//button[@data-automation-id='modalConfirmButton' and text()='Yes']")
    private WebElement yesButton;

    @FindBy(xpath = "//button[@data-automation-id='modalCancelButton' and text()='No']")
    private WebElement noButton;

    @FindBy(xpath = "//button[@data-automation-id='modalCancelButton' and text()='Cancel']")
    private WebElement cancelButton;

    @FindBy(xpath = "//div[@id='dialogModal']//button[@data-automation-id='modalCloseButton']")
    private WebElement closeButton;

    public WebElement dialogHeader() {
        return dialogContent.findElement(By.className("modal-title"));
    }

    public WebElement dialogInformationMessage() {
        return dialogContent.findElement(By.className("modal-body__content"));
    }

    public VNextBOModalDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WaitUtilsWebDriver.getWait()
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='dialogModal']//div[contains(@class,'modal-content')]")));
    }
}
