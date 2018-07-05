package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextCustomersMenuScreen extends VNextBaseScreen {

    @FindBy(xpath="//a[@data-name='edit']")
    private WebElement editinspectionbtn;

    @FindBy(xpath="//body/div[@data-menu='popup']")
    private WebElement customersmenuscreen;

    @FindBy(xpath="//div[@class='close-popup close-actions']")
    private WebElement closebtn;

    public VNextCustomersMenuScreen(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.visibilityOf(customersmenuscreen));
    }

    public VNextNewCustomerScreen clickEditCustomerMenuItem() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.visibilityOf(editinspectionbtn));
        tap(editinspectionbtn);
        return new VNextNewCustomerScreen(appiumdriver);
    }
}
