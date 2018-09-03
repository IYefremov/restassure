package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextBasicMenuScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class VNextPayPOROScreen extends VNextBasicMenuScreen {

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
        PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
    }

    public void setPaymentPOROValue(String pOROValue) {
        poroNumberfld.clear();
        poroNumberfld.sendKeys(pOROValue);
    }

    public void setPaymentNotes(String notes) {
        paymentNotesfld.clear();
        paymentNotesfld.sendKeys(notes);
    }

    public VNextInvoicesScreen clickPayButton() {
        tap(paybtn);
        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        System.out.println("+++++++++" + informationDialog.clickInformationDialogOKButtonAndGetMessage());
        return new VNextInvoicesScreen(appiumdriver);
    }
}
