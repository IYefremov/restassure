package com.cyberiansoft.test.vnext.screens.typeselectionlists;

import com.cyberiansoft.test.vnext.factories.invoicestypes.InvoiceTypes;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class VNextInvoiceTypesList extends VNextBaseTypeSelectionList {

    @FindBy(xpath="//div[@data-page='entity-types']")
    private WebElement rootElement;

    public VNextInvoiceTypesList(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
        //PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
    }

    public void selectInvoiceType(InvoiceTypes invoiceType) {
        WaitUtils.elementShouldBeVisible(rootElement, true);
        WaitUtils.waitUntilElementIsClickable(rootElement);
        selectType(invoiceType.getInvoiceTypeName());
    }
}
