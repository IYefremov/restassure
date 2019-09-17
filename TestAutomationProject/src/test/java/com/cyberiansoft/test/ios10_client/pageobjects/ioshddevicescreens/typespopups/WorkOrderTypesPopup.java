package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typespopups;

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WorkOrderTypesPopup extends BaseTypePopup {

    final String typeIdentificatorString = "OrderTypeSelector";

    public WorkOrderTypesPopup() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);

    }

    public void selectWorkOrderType(String workOrderType) {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(typeIdentificatorString)));
        selectType(typeIdentificatorString, workOrderType);
    }
}
