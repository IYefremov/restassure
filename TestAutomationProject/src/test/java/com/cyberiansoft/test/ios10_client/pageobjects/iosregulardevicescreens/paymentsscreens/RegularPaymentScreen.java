package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.paymentsscreens;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.BaseWizardScreen;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegularPaymentScreen extends BaseWizardScreen {

    @iOSXCUITFindBy(accessibility = "Cancel")
    private IOSElement cancelbtn;

    @iOSXCUITFindBy(accessibility = "Pay")
    private IOSElement paybtn;

    @iOSXCUITFindBy(accessibility = "Payment_Tab_PO")
    private IOSElement poropayoption;

    @iOSXCUITFindBy(accessibility = "Payment_Tab_Cash")
    private IOSElement cashcheckpayoption;

    @iOSXCUITFindBy(accessibility = "Payment_Tab_CreditCard")
    private IOSElement creditcardpayoption;

    @iOSXCUITFindBy(accessibility = "Payment_Cash_CheckNumber")
    private IOSElement cashchecknumberfld;

    @iOSXCUITFindBy(accessibility = "Payment_Cash_Amount")
    private IOSElement cashamountfld;

    @iOSXCUITFindBy(accessibility = "Payment_Cash_Notes")
    private IOSElement cashnotestfld;

    public RegularPaymentScreen() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
    }

    public void waitPaymentScreenLoaded() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.iOSNsPredicateString("type = 'XCUIElementTypeButton' AND name='viewTitle'")));
    }

    public void selectCashCheckPayOption() {
        cashcheckpayoption.click();
    }

    public void setCashCheckAmountValue(String amount) {
        cashamountfld.clear();
        cashamountfld.sendKeys(amount);
    }

    public void setCashCheckNumberValue(String cashCheckNumber) {
        cashchecknumberfld.clear();
        cashchecknumberfld.sendKeys(cashCheckNumber);
    }

    public String getCashCheckAmountValue() {
        return cashamountfld.getAttribute("value");
    }

    public void payForInvoice() {
        paybtn.click();
    }
}
