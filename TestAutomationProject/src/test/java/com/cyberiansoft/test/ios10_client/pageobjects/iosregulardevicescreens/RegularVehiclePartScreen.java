package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularPriceMatrixScreen;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.WaitOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static io.appium.java_client.touch.offset.ElementOption.element;

public class RegularVehiclePartScreen extends iOSRegularBaseScreen {

    private static String viewMode = "PdrView";

    public RegularVehiclePartScreen() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
    }

    public void waitVehiclePartScreenLoaded() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Vehicle Part")));
    }

    public void setSizeAndSeverity(String size, String severity) {
        appiumdriver.findElementByAccessibilityId("Size").click();
        WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId(size))).click();
        appiumdriver.findElement(MobileBy.AccessibilityId(severity)).click();
        appiumdriver.findElementByAccessibilityId("Save").click();
        viewMode = "PdrView";
    }

    public boolean isNotesExists() {
        return appiumdriver.findElementByAccessibilityId("PriceMatrixItemDetails" + viewMode)
                .findElements(MobileBy.AccessibilityId("Notes")).size() > 0;
    }

    public boolean isTechniciansExists() {
        return appiumdriver.findElementsByAccessibilityId("Technicians").size() > 0;
    }

    public String getTechniciansValue() {
        WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        WebElement technicians = wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElement(MobileBy.iOSNsPredicateString("type = 'XCUIElementTypeCell' and name = 'Technicians'"))));
        return technicians.findElement(By.xpath("//XCUIElementTypeStaticText[2]")).getAttribute("value");
    }

    public void selectDiscaunt(String discaunt) {
        clickDiscaunt(discaunt);
        RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen();
        selectedservicescreen.saveSelectedServiceDetails();
    }

    public void clickDiscaunt(String discaunt) {
        MobileElement table = (MobileElement) appiumdriver.findElementByAccessibilityId("PriceMatrixItemDetails" + viewMode);
        MobileElement elDiscount = (MobileElement) table.findElementByAccessibilityId(discaunt);
        if (!elDiscount.isDisplayed())
            swipeToElement(table.findElement(MobileBy.AccessibilityId(discaunt)));
        table.findElement(MobileBy.AccessibilityId(discaunt)).click();
    }

    public String getPrice() {
        WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        WebElement priceCell = wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("PriceMatrixItemDetailsCellPrice")));
        return priceCell.findElement(MobileBy.className("XCUIElementTypeTextField")).getAttribute("value");
    }

    public String getPriceMatrixVehiclePartSubTotalPrice() {
        waitVehiclePartScreenLoaded();
        return appiumdriver.findElementByClassName("XCUIElementTypeToolbar").findElement(MobileBy.className("XCUIElementTypeStaticText")).getAttribute("value");
    }

    public void clickSave() {
        appiumdriver.findElement(MobileBy.AccessibilityId("Save")).click();
    }

    public void saveVehiclePart() {
        waitVehiclePartScreenLoaded();
        clickSave();
    }

    public RegularSelectedServiceDetailsScreen openTechniciansPopup() {
        clickOnTechnicians();
        return new RegularSelectedServiceDetailsScreen();
    }

    public void clickOnTechnicians() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Technicians"))).click();
    }

    public void switchOffOption(String optionname) {
        appiumdriver.findElementByAccessibilityId("Other").click();
        viewMode = "OtherView";
    }

    public void setPrice(String price) {
        WebElement cell = appiumdriver.findElementByAccessibilityId("PriceMatrixItemDetailsCellPrice")
                .findElement(MobileBy.className("XCUIElementTypeTextField"));
        cell.click();
        cell.clear();
        cell.sendKeys(price + "\n");
    }

    public void setTime(String timevalue) {
        appiumdriver.findElementByAccessibilityId("PriceMatrixItemDetailsCellTime")
                .findElement(MobileBy.className("XCUIElementTypeTextField")).sendKeys(timevalue + "\n");
    }

    public void clickBackButton() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Back")));
        appiumdriver.findElementByAccessibilityId("PriceMatrixItemDetails").findElement(MobileBy.AccessibilityId("Back")).click();
    }

    public String getPriceMatrixTotalPriceValue() {
        MobileElement toolBar = (MobileElement) appiumdriver.findElementByAccessibilityId("Toolbar");
        return toolBar.findElementByClassName("XCUIElementTypeStaticText").getAttribute("value");
    }
}