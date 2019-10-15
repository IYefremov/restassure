package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.ios10_client.enums.ReconProMenuItems;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.BaseWizardScreen;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class RegularMenuScreen extends BaseWizardScreen {

    @iOSXCUITFindBy(accessibility = "Cancel")
    private IOSElement cancelBtn;

    public RegularMenuScreen() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
    }

    public boolean isMenuItemExists(ReconProMenuItems menuItem) {
        return appiumdriver.findElementsByAccessibilityId(menuItem.getMenuItemName()).size() > 0;
    }

    public void closeMenuScreen() {
        cancelBtn.click();
    }

    public void clickMenuItem(ReconProMenuItems menuItem) {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(menuItem.getMenuItemName())));
        appiumdriver.findElementByAccessibilityId(menuItem.getMenuItemName()).click();
    }
}
