package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextPayCashCheckScreen extends VNextBaseScreen {

    @FindBy(xpath="//div[@data-page='details']")
    private WebElement paycachcheckscreeen;

    @FindBy(id="paymentCheckNumber")
    private WebElement paymentCheckNumberfld;

    @FindBy(id="paymentNotes")
    private WebElement paymentNotesfld;

    @FindBy(id="paymentAmount")
    private WebElement paymentAmountfld;

    @FindBy(xpath="//*[@action='pay']")
    private WebElement paybtn;

    public VNextPayCashCheckScreen(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
    }

    public void setPaymentCheckNumber(String checkNumber) {
        paymentCheckNumberfld.clear();
        paymentCheckNumberfld.sendKeys(checkNumber);
    }

    public void setPaymentNotes(String paymentNotes) {
        paymentNotesfld.clear();
        paymentNotesfld.sendKeys(paymentNotes);
    }

    public void setAmauntValue(String amauntValue) {
        tap(paymentAmountfld);
        VNextCustomKeyboard keyboard = new VNextCustomKeyboard(appiumdriver);
        String defAmaunt = paymentAmountfld.getAttribute("value");
        keyboard.setFieldValue(defAmaunt, amauntValue);
    }

    public String getAmauntValue() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 90);
        wait.until(ExpectedConditions.visibilityOf(paymentAmountfld));
        BaseUtils.waitABit(500);
        return paymentAmountfld.getAttribute("value");
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
