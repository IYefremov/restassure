package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularServicesScreen;
import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegularServiceTypesScreen extends iOSRegularBaseScreen {

    public RegularServiceTypesScreen()  {

        super();
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);

    }

    public void waitServiceTypesScreenLoaded() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Service Types")));
    }

    public boolean isPanelOrServiceExists(String panelOrServiceName) {
        return appiumdriver.findElementsByAccessibilityId(panelOrServiceName).size() > 0;
    }

    public RegularSelectedServiceDetailsScreen clickOnPanel(String panelName) {
        if (!appiumdriver.findElementByAccessibilityId(panelName).isDisplayed()) {
            swipeToElement(appiumdriver.
                    findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + panelName + "']/..")));
        }
        appiumdriver.findElementByAccessibilityId(panelName).click();
        return new RegularSelectedServiceDetailsScreen();
    }

    public RegularServicesScreen clickSaveButton() {
        appiumdriver.findElementByAccessibilityId("Save").click();
        return new RegularServicesScreen();
    }
}
