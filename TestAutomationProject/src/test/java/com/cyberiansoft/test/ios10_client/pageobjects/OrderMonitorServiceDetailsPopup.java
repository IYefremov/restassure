package com.cyberiansoft.test.ios10_client.pageobjects;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.OrderMonitorScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.TechniciansPopup;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.iOSHDBaseScreen;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrderMonitorServiceDetailsPopup extends iOSHDBaseScreen {

    @iOSXCUITFindBy(accessibility = "MonitorServiceDetails")
    private IOSElement monitorservicedetailstable;

    public OrderMonitorServiceDetailsPopup() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(monitorservicedetailstable));
    }

    public TechniciansPopup clickTech() {
        monitorservicedetailstable.findElementByAccessibilityId("MonitorDetailsCell_Technician").click();
        return new TechniciansPopup();
    }

    public String getTechnicianValue() {
        return monitorservicedetailstable.findElementByAccessibilityId("MonitorDetailsCell_Technician").
                findElementsByClassName("XCUIElementTypeStaticText").get(1).getAttribute("value");
    }

    public OrderMonitorScreen clickServiceDetailsDoneButton() {
        appiumdriver.findElementByAccessibilityId("NavigationBarItemDone").click();
        return new OrderMonitorScreen();
    }

    public OrderMonitorScreen clickServiceDetailsCancelButton() {
        appiumdriver.findElementByAccessibilityId("NavigationBarItemClose").click();
        return new OrderMonitorScreen();
    }

    public boolean isServiceStartDateExists() {
        return monitorservicedetailstable.findElementsByAccessibilityId("MonitorDetailsCell_StartDate").size() > 0;
    }

    public OrderMonitorScreen clickStartService() {
        appiumdriver.findElementByAccessibilityId("Start Service").click();
        return new OrderMonitorScreen();
    }

    public boolean isStartServiceButtonPresent() {
        return appiumdriver.findElementByAccessibilityId("Start Service").isDisplayed();
    }

    public OrderMonitorScreen setCompletedServiceStatus() {
        clickServiceStatusCell();
        appiumdriver.findElementByAccessibilityId("Completed").click();
        return new OrderMonitorScreen();
    }

    public void clickServiceStatusCell() {
        monitorservicedetailstable.findElementByAccessibilityId("Service Status").click();
    }

    public boolean isStartPhaseButtonPresent() {
        return monitorservicedetailstable.findElementsByAccessibilityId("btnStartReset").size() > 0;
    }

    public String getServiceDetailsPriceValue() {
        return monitorservicedetailstable.findElementByAccessibilityId("MonitorDetailsCell_Amount")
                .findElementByAccessibilityId("AmountTextControl").getAttribute("value");
    }

}
