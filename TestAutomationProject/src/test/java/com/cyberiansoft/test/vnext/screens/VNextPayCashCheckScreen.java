package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class VNextPayCashCheckScreen extends VNextBaseScreen {

    @FindBy(xpath="//div[@data-page='details']")
    private WebElement paycachcheckscreeen;

    @FindBy(id="paymentCheckNumber")
    private WebElement paymentCheckNumberfld;

    @FindBy(id="paymentAmount")
    private WebElement paymentAmountfld;

    @FindBy(xpath="//*[@action='pay']")
    private WebElement paybtn;

    public VNextPayCashCheckScreen(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
    }

    public void setPaymentCheckNumber(String checkNumber) {
        paymentCheckNumberfld.clear();
        paymentCheckNumberfld.sendKeys(checkNumber);
    }

    public void setAmauntValue(String amauntValue) {
        tap(paymentAmountfld);
        VNextCustomKeyboard keyboard = new VNextCustomKeyboard(appiumdriver);
        String defAmaunt = paymentAmountfld.getAttribute("value");
        keyboard.setFieldValue(defAmaunt, amauntValue);
    }

    public void clearAmauntValue() {
        tap(paymentAmountfld);
        VNextCustomKeyboard keyboard = new VNextCustomKeyboard(appiumdriver);
        keyboard.clearFieldValue(paymentAmountfld.getAttribute("value"));
        keyboard.clickKeyboardDoneButton();
    }

    public void clickPayButton() {
        tap(paybtn);
    }

    public VNextInvoicesScreen payInvoice() {
        tap(paybtn);
        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        informationDialog.clickInformationDialogOKButtonAndGetMessage();
        return new VNextInvoicesScreen(appiumdriver);
    }


}
