package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typespopups;

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InspectionTypesPopup extends BaseTypePopup {

    final String typeIdentificatorString = "InspectionTypeSelector";

    public InspectionTypesPopup() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(typeIdentificatorString)));
    }

    public void selectInspectionType(String inspectiontype) {
        selectType(typeIdentificatorString, inspectiontype);
    }
}
