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

    @FindBy(id="paymentAmount")
    private WebElement paymentAmountfld;

    @FindBy(xpath="//*[@action='pay']")
    private WebElement paybtn;

    public VNextPayCashCheckScreen(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
        PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
    }

    public void setAmauntValue(String amauntValue) {
        tap(paymentAmountfld);
        VNextCustomKeyboard keyboard = new VNextCustomKeyboard(appiumdriver);
        String defAmaunt = paymentAmountfld.getAttribute("value");
        keyboard.setFieldValue(defAmaunt, amauntValue);
    }

    public VNextInvoicesScreen clickPayButton() {
        tap(paybtn);
        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        System.out.println("+++++++++" + informationDialog.clickInformationDialogOKButtonAndGetMessage());
        return new VNextInvoicesScreen(appiumdriver);
    }


}
