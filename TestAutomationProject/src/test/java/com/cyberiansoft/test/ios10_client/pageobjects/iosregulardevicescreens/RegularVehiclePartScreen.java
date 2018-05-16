package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularPriceMatrixScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class RegularVehiclePartScreen extends iOSRegularBaseScreen {

    public RegularVehiclePartScreen(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void setSizeAndSeverity(String size, String severity) {
        new TouchAction(appiumdriver).tap(appiumdriver.findElementByAccessibilityId("Size")).perform();
        WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId(size)));
        appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + size + "']")).click();
        appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + severity + "']")).click();
        appiumdriver.findElementByAccessibilityId("Save").click();
    }

    public boolean isNotesExists() {
        return appiumdriver.findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='Notes']")).isDisplayed();
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
        RegularSelectedServiceDetailsScreen selectedservicescreen = new RegularSelectedServiceDetailsScreen(appiumdriver);
        selectedservicescreen.saveSelectedServiceDetails();
    }

    public void clickDiscaunt(String discaunt) {
        MobileElement elDiscount = (MobileElement) appiumdriver.findElementByAccessibilityId(discaunt);
        if (!elDiscount.isDisplayed()) {
            swipeToElement(appiumdriver.findElement(By.xpath("//XCUIElementTypeStaticText[@name='" + discaunt + "']/..")));
            //appiumdriver.findElementByAccessibilityId(discaunt).click();
        }
        appiumdriver.findElementByAccessibilityId(discaunt).click();
    }

    public String getPrice() {
        WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.visibilityOf(appiumdriver.findElementByAccessibilityId("Price")));
        WebElement par = getTableParentCell("Price");
        return par.findElement(By.xpath("//XCUIElementTypeTextField")).getAttribute("value");
    }

    public String getPriceMatrixVehiclePartSubTotalPrice() {
        return appiumdriver.findElementByClassName("XCUIElementTypeToolbar").findElement(MobileBy.className("XCUIElementTypeStaticText")).getAttribute("value");
    }

    public void clickSave() {
        appiumdriver.findElement(MobileBy.AccessibilityId("Save")).click();
    }

    public RegularPriceMatrixScreen saveVehiclePart() {
        clickSave();
        return new RegularPriceMatrixScreen(appiumdriver);
    }

    public void clickOnTechnicians() {
        appiumdriver.findElementByAccessibilityId("Technicians").click();
    }

    public boolean isDiscauntPresent(String discaunt) {
        return appiumdriver.findElementsByAccessibilityId(discaunt).size() > 0;
    }

    public void switchOffOption(String optionname) {
        if (appiumdriver.findElementByXPath("//XCUIElementTypeSwitch[@name='" + optionname  + "']").getAttribute("value").equals("1"))
            appiumdriver.findElementByXPath("//XCUIElementTypeSwitch[@name='" + optionname  + "']").click();
    }

    public String getDiscauntPriceAndValue(String discaunt) {
        WebElement par = getTableParentCell(discaunt);
        return par.findElement(By.xpath("//XCUIElementTypeStaticText[2]")).getAttribute("value").replaceAll("[^a-zA-Z0-9$.,% ]", " ").trim();
    }

    public void setPrice(String price) {
        WebElement par = getTableParentCell("Price");
        par.findElement(By.xpath("//XCUIElementTypeTextField")).click();
        ((IOSDriver) appiumdriver).getKeyboard().pressKey(price);
        ((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
    }

    public void setTime(String timevalue) throws InterruptedException {
        WebElement par = getTableParentCell("Time");
        par.findElement(By.xpath("//XCUIElementTypeTextField")).sendKeys(timevalue + "\n");
    }

}
