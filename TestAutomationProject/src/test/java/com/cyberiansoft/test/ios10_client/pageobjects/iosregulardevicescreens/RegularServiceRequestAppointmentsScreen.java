package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.enums.ServiceRequestStatus;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularBaseTypeScreen;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class RegularServiceRequestAppointmentsScreen extends RegularBaseTypeScreen {

    @iOSXCUITFindBy(accessibility = "Add")
    private IOSElement addbtn;

    public RegularServiceRequestAppointmentsScreen() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
    }

    public void waitForAppointmentsScreenLoad() {
        FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.name("Appointments")));
    }

    public boolean isAppointmentExists() {
       return appiumdriver.findElementByClassName("XCUIElementTypeTable").findElements(MobileBy.AccessibilityId(" " + ServiceRequestStatus.SCHEDULED.toString())).size() > 0;
    }
}
