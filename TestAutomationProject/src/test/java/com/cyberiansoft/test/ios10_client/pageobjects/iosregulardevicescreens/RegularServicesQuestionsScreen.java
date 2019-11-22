package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularBaseServicesScreen;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;

public class RegularServicesQuestionsScreen extends RegularBaseServicesScreen {

    @iOSXCUITFindBy(accessibility = "ServiceGroupServicesTable")
    private IOSElement serviceGroupServicesTable;

    public RegularServicesQuestionsScreen() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
    }

    public boolean isServiceDeclinedSkipped(String serviceName) {
        return serviceGroupServicesTable.findElement(MobileBy.iOSNsPredicateString("name CONTAINS '" +
                serviceName + "' and type = 'XCUIElementTypeCell'")).findElements(MobileBy.AccessibilityId("declined")).size() > 0;
    }

    public boolean isServiceApproved(String serviceName) {
        return serviceGroupServicesTable.findElement(MobileBy.iOSNsPredicateString("name CONTAINS '" +
                serviceName + "' and type = 'XCUIElementTypeCell'")).findElements(MobileBy.AccessibilityId("selected")).size() > 0;
    }
}
