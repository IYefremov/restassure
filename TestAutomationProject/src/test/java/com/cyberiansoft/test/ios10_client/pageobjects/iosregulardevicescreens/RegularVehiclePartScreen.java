package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

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
        return appiumdriver.findElementByAccessibilityId("Technicians").isDisplayed();
    }

    public String getTechniciansValue() {
        WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Technicians")));
        WebElement par = getTableParentCell("Technicians");
        return par.findElement(By.xpath("//XCUIElementTypeStaticText[2]")).getAttribute("value");
    }

    public WebElement getTableParentCell(String cellname) {
        return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + cellname + "']/.."));
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
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Price")));
        WebElement par = getTableParentCell("Price");
        return par.findElement(By.xpath("//XCUIElementTypeTextField")).getAttribute("value");
    }

    public String getPriceMatrixVehiclePartSubTotalPrice() {
        waitVehiclePartScreenLoaded();
        return appiumdriver.findElementByClassName("XCUIElementTypeToolbar").findElement(MobileBy.className("XCUIElementTypeStaticText")).getAttribute("value");
    }

    public void clickSave() {
        appiumdriver.findElement(MobileBy.AccessibilityId("Save")).click();
    }

    public RegularPriceMatrixScreen saveVehiclePart() {
        waitVehiclePartScreenLoaded();
        clickSave();
        return new RegularPriceMatrixScreen();
    }

    public RegularSelectedServiceDetailsScreen openTechniciansPopup() {
        clickOnTechnicians();
        return new RegularSelectedServiceDetailsScreen();
    }

    public void clickOnTechnicians() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Technicians"))).click();
       // appiumdriver.findElementByAccessibilityId("Technicians").click();
    }


    public void switchOffOption(String optionname) {
        appiumdriver.findElementByAccessibilityId("Other").click();
        viewMode = "OtherView";
    }

    public void setPrice(String price) {
        WebElement par = getTableParentCell("Price");
        WebElement cell = par.findElement(By.xpath("//XCUIElementTypeTextField"));
        cell.click();
        cell.clear();
        cell.sendKeys(price + "\n");
    }

    public void setTime(String timevalue) {
        WebElement par = getTableParentCell("Time");
        par.findElement(By.xpath("//XCUIElementTypeTextField")).sendKeys(timevalue + "\n");
    }

    public RegularPriceMatrixScreen clickBackButton() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Back")));
        appiumdriver.findElementByAccessibilityId("PriceMatrixItemDetails").findElement(MobileBy.AccessibilityId("Back")).click();
        return new RegularPriceMatrixScreen();
    }

    public String getPriceMatrixTotalPriceValue() {
        MobileElement toolBar = (MobileElement) appiumdriver.findElementByAccessibilityId("Toolbar");
        return toolBar.findElementByClassName("XCUIElementTypeStaticText").getAttribute("value");
    }
}
