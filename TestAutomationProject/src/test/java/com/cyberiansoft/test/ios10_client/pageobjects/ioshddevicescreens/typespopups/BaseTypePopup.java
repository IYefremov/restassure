package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typespopups;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.iOSHDBaseScreen;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;

public abstract class BaseTypePopup extends iOSHDBaseScreen {

    public BaseTypePopup() {
        super();
        //PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void selectType(String typeIdentificatorString, String typeItemName) {
        IOSElement typetable = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = '" +
                typeIdentificatorString + "' and type = 'XCUIElementTypeTable'"));
        if (!typetable.findElementByAccessibilityId(typeItemName).isDisplayed()) {
            swipeTableUp(typetable.findElementByAccessibilityId(typeItemName),
                    typetable);
        }
        appiumdriver.findElementByAccessibilityId(typeItemName).click();
    }

}
