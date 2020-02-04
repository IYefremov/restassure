package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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

    public VNextPayCashCheckScreen(WebDriver appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
    }

    public void setPaymentCheckNumber(String checkNumber) {
        WaitUtils.waitUntilElementIsClickable(paymentCheckNumberfld);
        paymentCheckNumberfld.clear();
        paymentCheckNumberfld.sendKeys(checkNumber);
    }

    public void setPaymentNotes(String paymentNotes) {
        paymentNotesfld.clear();
        paymentNotesfld.sendKeys(paymentNotes);
    }

    public void setAmauntValue(String amauntValue) {
        WaitUtils.waitUntilElementIsClickable(paymentAmountfld);
        BaseUtils.waitABit(1000);
        tap(paymentAmountfld);
        VNextCustomKeyboard keyboard = new VNextCustomKeyboard(appiumdriver);
        String defAmaunt = paymentAmountfld.getAttribute("value");
        keyboard.setFieldValue(defAmaunt, amauntValue);
    }

    public String getAmauntValue() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 90);
        wait.until(ExpectedConditions.visibilityOf(paymentAmountfld));
        BaseUtils.waitABit(1000);
        return paymentAmountfld.getAttribute("value");
    }

    public void clearAmauntValue() {
        WaitUtils.waitUntilElementIsClickable(paymentAmountfld);
        BaseUtils.waitABit(1000);
        tap(paymentAmountfld);
        VNextCustomKeyboard keyboard = new VNextCustomKeyboard(appiumdriver);
        keyboard.clearFieldValue(paymentAmountfld.getAttribute("value"));
        keyboard.clickKeyboardDoneButton();
    }

    public void clickPayButton() {
        WaitUtils.elementShouldBeVisible(paymentAmountfld, true);
        BaseUtils.waitABit(3000);
        tap(paybtn);
    }

    public VNextInvoicesScreen payInvoice() {
        WaitUtils.waitUntilElementIsClickable(paymentAmountfld);
        BaseUtils.waitABit(1000);
        tap(paybtn);
        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        informationDialog.clickInformationDialogOKButtonAndGetMessage();
        return new VNextInvoicesScreen(appiumdriver);
    }

    public void clickScreenBackButton() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-page='payment']")));
        WaitUtils.waitUntilElementIsClickable(paybtn);
        super.clickScreenBackButton();
    }


}
