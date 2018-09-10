package com.cyberiansoft.test.vnext.screens.panelandparts;

import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class VNextBasePanelPartsList extends VNextBaseScreen {

    @FindBy(xpath="//a[contains(@class, 'tab-part')]")
    WebElement partstab;

    @FindBy(xpath="//span[@action='save']")
    private WebElement savebtn;

    public VNextBasePanelPartsList(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
    }

    public void clickBackButton() {
        clickScreenBackButton();
    }

    public void clickSaveButton() {
        tap(savebtn);
    }

    public boolean isPartsTabEnabled() {
        return partstab.getAttribute("class").contains("active");
    }
}
