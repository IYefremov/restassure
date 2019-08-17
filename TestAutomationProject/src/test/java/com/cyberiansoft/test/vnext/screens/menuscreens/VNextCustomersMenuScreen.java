package com.cyberiansoft.test.vnext.screens.menuscreens;

import com.cyberiansoft.test.vnext.screens.VNextNewCustomerScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class VNextCustomersMenuScreen extends VNextBasicMenuScreen {

    @FindBy(xpath = "//*[@data-name='edit']")
    private WebElement editCustomerbtn;

    @FindBy(xpath = "//*[@data-name='presetCustomer']")
    private WebElement presetCustomerbtn;

    public VNextCustomersMenuScreen(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
        //WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        //wait.until(ExpectedConditions.visibilityOf(customersmenuscreen));
    }

    public VNextNewCustomerScreen clickEditCustomerMenuItem() {
        clickMenuItem(editCustomerbtn);
        return new VNextNewCustomerScreen(appiumdriver);
    }

    public VNextCustomersScreen clickSetCustomerAsDefault() {
        clickMenuItem(presetCustomerbtn);
        return new VNextCustomersScreen(appiumdriver);
    }
}
