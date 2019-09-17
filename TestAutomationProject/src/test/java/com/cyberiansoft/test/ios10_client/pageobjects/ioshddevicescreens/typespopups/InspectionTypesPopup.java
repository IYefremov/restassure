package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typespopups;

import com.cyberiansoft.test.ios10_client.types.inspectionstypes.IInspectionsTypes;
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
    }

    public void selectInspectionType(IInspectionsTypes inspType) {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(typeIdentificatorString)));
        selectType(typeIdentificatorString, inspType.getInspectionTypeName());
    }
}
