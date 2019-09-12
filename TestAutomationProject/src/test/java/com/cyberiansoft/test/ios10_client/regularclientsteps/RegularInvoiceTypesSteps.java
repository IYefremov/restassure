package com.cyberiansoft.test.ios10_client.regularclientsteps;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.IInvoicesTypes;
import com.cyberiansoft.test.ios10_client.utils.SwipeUtils;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegularInvoiceTypesSteps {
    public static void selectInvoiceType(IInvoicesTypes invoicesType) {
        FluentWait<WebDriver> wait = new WebDriverWait(DriverBuilder.getInstance().getAppiumDriver(), 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("InvoiceTypeSelector")));
        IOSElement invoiceTypeTable = (IOSElement) DriverBuilder.getInstance().getAppiumDriver().findElement(MobileBy.iOSNsPredicateString("name = 'InvoiceTypeSelector' and type = 'XCUIElementTypeTable'"));
        if (!invoiceTypeTable.findElementByAccessibilityId(invoicesType.getInvoiceTypeName()).isDisplayed()) {
            SwipeUtils.swipeToElement(invoiceTypeTable.findElementByAccessibilityId(invoicesType.getInvoiceTypeName()));
        }
        DriverBuilder.getInstance().getAppiumDriver().findElement(MobileBy.AccessibilityId(invoicesType.getInvoiceTypeName())).click();
    }
}
