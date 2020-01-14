package com.cyberiansoft.test.vnext.screens.menuscreens;

import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.screens.VNextPayCashCheckScreen;
import com.cyberiansoft.test.vnext.screens.VNextPayInvoicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextPayPOROScreen;
import com.cyberiansoft.test.vnext.validations.MenuValidations;
import org.openqa.selenium.WebDriver;
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

    public VNextPayMenu(WebDriver appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
    }

    public VNextPayInvoicesScreen clickPayCreditCardMenuItem() {
        clickMenuItem(paycreditcardbtn);
        return new VNextPayInvoicesScreen(appiumdriver);
    }


    public VNextPayCashCheckScreen clickPayCachCheckMenuItem() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.visibilityOf(paycheckbtn));
        clickMenuItem(paycheckbtn);
        return new VNextPayCashCheckScreen(appiumdriver);
    }

    public VNextPayPOROScreen clickPayPOROMenuItem() {
        clickMenuItem(invoicepayporomenubtn);
        return new VNextPayPOROScreen(appiumdriver);
    }

}
