package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyInvoicesScreen;
import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class SummaryScreen extends iOSHDBaseScreen {

    public SummaryScreen() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Summary")));
    }

    public MyInvoicesScreen clickBackButton() {
        appiumdriver.findElementByAccessibilityId("Back").click();
        return new MyInvoicesScreen();
    }

    public boolean isSummaryPDFExists() {
        return appiumdriver.findElementsByAccessibilityId("Generating PDF file...").size() > 0;
    }

}
