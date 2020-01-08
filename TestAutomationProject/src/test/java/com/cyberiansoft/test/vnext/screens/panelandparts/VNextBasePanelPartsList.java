package com.cyberiansoft.test.vnext.screens.panelandparts;

import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class VNextBasePanelPartsList extends VNextBaseScreen {

    @FindBy(xpath="//a[contains(@class, 'tab-part')]")
    WebElement partstab;

    @FindBy(xpath="//*[@action='save']")
    private WebElement savebtn;

    public VNextBasePanelPartsList(WebDriver appiumdriver) {
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
