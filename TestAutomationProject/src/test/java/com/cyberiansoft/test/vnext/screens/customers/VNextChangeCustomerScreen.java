package com.cyberiansoft.test.vnext.screens.customers;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextChangeCustomerScreen extends VNextBaseCustomersScreen {

    @FindBy(xpath="//div[@data-page='orders-change-customer']")
    private WebElement changecustomersscreen;

    public VNextChangeCustomerScreen(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
        wait.until(ExpectedConditions.visibilityOf(changecustomersscreen));
    }

    public boolean isAddCustomerButtonDisplayed() {
        return changecustomersscreen.findElements(By.xpath(".//a[@action='add']")).size() > 0;
    }
}
