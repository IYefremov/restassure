package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextPayPOROScreen extends VNextBaseScreen {

    @FindBy(xpath="//div[@data-page='details']")
    private WebElement paycachcheckscreeen;

    @FindBy(id="paymentPORONumber")
    private WebElement poroNumberfld;

    @FindBy(id="paymentNotes")
    private WebElement paymentNotesfld;

    @FindBy(xpath="//*[@action='pay']")
    private WebElement paybtn;

    public VNextPayPOROScreen(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
    }

    public void setPaymentPOROValue(String pOROValue) {
        WaitUtils.waitUntilElementIsClickable(poroNumberfld);
        poroNumberfld.clear();
        poroNumberfld.sendKeys(pOROValue);
    }

    public String getPaymentPOROValue() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.visibilityOf(poroNumberfld));
        return poroNumberfld.getAttribute("value");

    }

    public void setPaymentNotes(String notes) {
        paymentNotesfld.clear();
        paymentNotesfld.sendKeys(notes);
    }

    public void clickPayButton() {
        WaitUtils.waitUntilElementIsClickable(paycachcheckscreeen);
        tap(paybtn);
    }

    public VNextInvoicesScreen payForInvoice() {
        clickPayButton();
        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        informationDialog.clickInformationDialogOKButtonAndGetMessage();
        return new VNextInvoicesScreen(appiumdriver);
    }
}
