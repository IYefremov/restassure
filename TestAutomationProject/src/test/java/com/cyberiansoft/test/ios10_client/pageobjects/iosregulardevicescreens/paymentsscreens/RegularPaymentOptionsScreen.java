package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.paymentsscreens;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.BaseWizardScreen;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegularPaymentOptionsScreen extends BaseWizardScreen {

    @iOSXCUITFindBy(accessibility = "Close")
    private IOSElement closebtn;

    public RegularPaymentOptionsScreen() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
    }

    public void waitPaymentOptionsScreenLoaded() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.iOSNsPredicateString("type = 'XCUIElementTypeNavigationBar' AND name='Payment Options'")));
    }

    public void clickPaymentOptionsCloseButton() {
        waitPaymentOptionsScreenLoaded();
        closebtn.click();
    }
}
