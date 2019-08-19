package com.cyberiansoft.test.vnext.screens.menuscreens;

import com.cyberiansoft.test.vnext.screens.VNextPayCashCheckScreen;
import com.cyberiansoft.test.vnext.screens.VNextPayInvoicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextPayPOROScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextPayMenu extends VNextBasicMenuScreen {

    @FindBy(xpath="//div[@data-parent-action='payMulti']")
    private WebElement paymenuscreeen;

    @FindBy(xpath = "//*[@data-name='payCreditCard']")
    private WebElement paycreditcardbtn;

    @FindBy(xpath = "//*[@data-name='payCashCheck']")
    private WebElement paycheckbtn;

    @FindBy(xpath = "//*[@data-name='payPOROChildren']")
    private WebElement invoicepayporomenubtn;

    public VNextPayMenu(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
    }

    public VNextPayInvoicesScreen clickPayCreditCardMenuItem() {
        clickMenuItem(paycreditcardbtn);
        return new VNextPayInvoicesScreen(appiumdriver);
    }

    public boolean isInvoicePayPOROMenuItemExists() {
        return invoicepayporomenubtn.isDisplayed();
    }

    public VNextPayCashCheckScreen clickPayCachCheckMenuItem() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.visibilityOf(paycheckbtn));
        clickMenuItem(paycheckbtn);
        return new VNextPayCashCheckScreen(appiumdriver);
    }

    public boolean isInvoicePayCashCheckMenuItemExists() {
        return paycheckbtn.isDisplayed();
    }

    public VNextPayPOROScreen clickPayPOROMenuItem() {
        clickMenuItem(invoicepayporomenubtn);
        return new VNextPayPOROScreen(appiumdriver);
    }

}
