package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

public class RegularSummaryScreen extends iOSRegularBaseScreen {

    public RegularSummaryScreen() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
    }

    public boolean isSummaryPDFExists() {
        return appiumdriver.findElementsByAccessibilityId("Generating PDF file...").size() > 0;
    }
}
