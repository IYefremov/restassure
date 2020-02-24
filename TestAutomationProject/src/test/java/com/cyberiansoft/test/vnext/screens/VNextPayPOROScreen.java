package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextPayPOROScreen extends VNextBaseScreen {

    @FindBy(xpath="//div[@data-page='payment']")
    private WebElement paycachcheckscreeen;

    @FindBy(id="paymentPORONumber")
    private WebElement poroNumberfld;

    @FindBy(id="paymentNotes")
    private WebElement paymentNotesfld;

    @FindBy(xpath="//*[@action='pay']")
    private WebElement paybtn;

    public VNextPayPOROScreen(WebDriver appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
    }

    public void setPaymentPOROValue(String pOROValue) {
        WaitUtils.waitUntilElementIsClickable(poroNumberfld);
        poroNumberfld.clear();
        poroNumberfld.sendKeys(pOROValue);
    }

    public String getPaymentPOROValue() {
        WaitUtils.waitUntilElementIsClickable(poroNumberfld);
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
        return new VNextInvoicesScreen();
    }
}
