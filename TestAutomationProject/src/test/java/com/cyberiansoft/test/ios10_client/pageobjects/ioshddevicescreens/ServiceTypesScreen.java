package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.ServicesScreen;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ServiceTypesScreen extends iOSHDBaseScreen {


    public ServiceTypesScreen()  {

        super();
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Service Types")));
    }

    public boolean isPanelOrServiceExists(String panelOrServiceName) {
        MobileElement serviceTypesTableView = (MobileElement) appiumdriver.findElementByAccessibilityId("ServiceTypesTableView");

        return serviceTypesTableView.findElementsByAccessibilityId(panelOrServiceName).size() > 0;
    }

    public SelectedServiceDetailsScreen clickOnPanel(String panelName) {
        MobileElement serviceTypesTableView = (MobileElement) appiumdriver.findElementByAccessibilityId("ServiceTypesTableView");
        if (!appiumdriver.findElementByAccessibilityId(panelName).isDisplayed()) {
            swipeTableUp(appiumdriver.
                    findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + panelName + "']/..")), serviceTypesTableView);
        }
        serviceTypesTableView.findElementByAccessibilityId(panelName).click();
        return new SelectedServiceDetailsScreen();
    }

    public ServicesScreen clickSaveButton() {
        MobileElement navBar = (MobileElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'Service Types' and type = 'XCUIElementTypeNavigationBar'"));
        navBar.findElementByAccessibilityId("Save").click();
        return new ServicesScreen();
    }
}
