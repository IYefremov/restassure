package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.ios10_client.enums.ReconProMenuItems;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.BaseWizardScreen;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;

public class MenuScreen extends BaseWizardScreen {

    @iOSXCUITFindBy(accessibility = "Cancel")
    private IOSElement cancelBtn;

    public MenuScreen() {
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
        appiumdriver.findElementByAccessibilityId(menuItem.getMenuItemName().replace("\n", " ")).click();
    }
}
