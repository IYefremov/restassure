package com.cyberiansoft.test.vnext.screens.panelandparts;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import com.cyberiansoft.test.vnext.screens.VNextLaborServiceDetailsScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class VNextBasePanelPartsList extends VNextBaseScreen {

    @FindBy(xpath="//a[contains(@class, 'tab-part')]")
    WebElement partstab;

    public VNextBasePanelPartsList(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
    }

    public void clickBackButton() {
        clickScreenBackButton();
    }

    public boolean isPartsTabEnabled() {
        return partstab.getAttribute("class").contains("active");
    }
}
