package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class TechniciansPopup extends iOSHDBaseScreen {

    public TechniciansPopup() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
    }

    public String saveTechnociansViewWithAlert() {
        appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'Technicians' and type = 'XCUIElementTypeNavigationBar'"))
                .findElement(MobileBy.AccessibilityId("Save")).click();
        return Helpers.getAlertTextAndAccept();
    }

    public void selectTechniciansCustomView() {
        appiumdriver.findElementByClassName("XCUIElementTypeSegmentedControl").findElement(MobileBy.AccessibilityId("Custom")).click();
    }

    public boolean isCustomTabSelected() {
        return appiumdriver.findElementByClassName("XCUIElementTypeSegmentedControl").findElement(MobileBy.AccessibilityId("Custom")).getAttribute("value") != null;
    }

    public void selectTechniciansEvenlyView() {
        appiumdriver.findElementByClassName("XCUIElementTypeSegmentedControl").findElement(MobileBy.AccessibilityId("Evenly")).click();
    }

    public boolean isEvenlyTabSelected() {
        return appiumdriver.findElementByClassName("XCUIElementTypeSegmentedControl").findElement(MobileBy.AccessibilityId("Evenly")).getAttribute("value") != null;
    }

    public void cancelTechViewDetails() {
        WebDriverWait wait = new WebDriverWait(appiumdriver,10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Cancel")));
        IOSElement navBar = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'Technicians' and type = 'XCUIElementTypeNavigationBar'"));

        navBar.findElementByAccessibilityId("Cancel").click();
    }

    public void saveTechViewDetails() {
        IOSElement navBar = (IOSElement) appiumdriver.findElement(MobileBy.iOSNsPredicateString("name = 'Technicians' and type = 'XCUIElementTypeNavigationBar'"));

        navBar.findElementByAccessibilityId("Save").click();
    }

    public String getCustomTechnicianPercentage(String technician) {
        IOSElement techsplittable =  getTechnicianSplitTable();
        String techitianlabel = techsplittable.findElementByXPath("//XCUIElementTypeCell[contains(@name, '"
                + technician + "')]").getAttribute("label");

        return techitianlabel.substring(techitianlabel.lastIndexOf("%"), techitianlabel.indexOf(")"));
    }

    public void setTechnicianCustomPercentageValue(String technician,
                                                   String percentage) {
        FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);

        MobileElement techCell = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//XCUIElementTypeCell[contains(@name, '"
                + technician + "')]")));
        techCell.click();
        if (techCell.findElements(MobileBy.AccessibilityId("Clear text")).size() > 0)
            techCell.findElement(MobileBy.AccessibilityId("Clear text")).click();
        techCell.findElement(MobileBy.className("XCUIElementTypeTextField")).sendKeys(percentage);
        techCell.findElement(MobileBy.className("XCUIElementTypeTextField")).sendKeys("\n");
    }

    public void selecTechnician(String technician) {
        FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Technicians")));

        IOSElement techsplittable =  getTechnicianSplitTable();
        scrollTable(techsplittable, technician);
        techsplittable.findElement(By.xpath("//XCUIElementTypeCell[contains(@name, '" + technician + "')]/XCUIElementTypeButton[@name='unselected']")).click();
    }

    public void searchTechnician(String technician) {
        appiumdriver.findElementByXPath("//XCUIElementTypeNavigationBar[@name='Technicians']/XCUIElementTypeButton[@name='Search']").click();
        if (appiumdriver.findElementsByAccessibilityId("Clear text").size() > 0)
            appiumdriver.findElementByAccessibilityId("Clear text").click();
        appiumdriver.findElementByClassName("XCUIElementTypeSearchField").sendKeys(technician);
    }

    public void cancelSearchTechnician() {
        appiumdriver.findElement(MobileBy.iOSNsPredicateString("name == 'Cancel' AND visible == 1")).click();
    }

    public void unselecTechnician(String technician) {
        IOSElement techsplittable =  getTechnicianSplitTable();
        techsplittable.findElement(By.xpath("//XCUIElementTypeCell[contains(@name, '" + technician + "')]/XCUIElementTypeButton[@name='selected']")).click();
    }

    public String getTechnicianPrice(String technician) {
        IOSElement techsplittable =  getTechnicianSplitTable();

        return techsplittable.findElementByXPath("//XCUIElementTypeCell[contains(@name, '"
                + technician + "')]/XCUIElementTypeTextField[1]").getAttribute("value");
    }

    public String getTechnicianPercentage(String technician) {
        IOSElement techsplittable =  getTechnicianSplitTable();

        return techsplittable.findElementByXPath("//XCUIElementTypeCell[contains(@name, '"
                + technician + "')]/XCUIElementTypeTextField[1]").getAttribute("value");
    }

    public boolean isTechnicianIsSelected(String technician) {
        IOSElement techsplittable =  getTechnicianSplitTable();
        return techsplittable.findElementsByXPath("//XCUIElementTypeCell[contains(@name, '"
                + technician + "')]/XCUIElementTypeButton[@name='selected']").size() > 0;
    }

    public IOSElement getTechnicianSplitTable() {
        IOSElement techsplittable =  null;
        if (appiumdriver.findElementsByAccessibilityId("TechnicianSplitsView").size() > 0) {
            List<IOSElement> techviews = appiumdriver.findElementsByAccessibilityId("TechnicianSplitsView");
            for (IOSElement techview : techviews)
                if (techview.getAttribute("type").equals("XCUIElementTypeTable")) {
                    techsplittable = techview;
                    break;
                }
        } else if (appiumdriver.findElementsByAccessibilityId("TechnicianSplitsSingleSelectionView").size() > 0) {
            List<IOSElement> techviews = appiumdriver.findElementsByAccessibilityId("TechnicianSplitsSingleSelectionView");
            for (IOSElement techview : techviews)
                if (techview.getAttribute("type").equals("XCUIElementTypeTable")) {
                    techsplittable = techview;
                    break;
                }
        } else {
            List<IOSElement> techviews = appiumdriver.findElementsByAccessibilityId("DefaultEmployeeSelectorView");
            for (IOSElement techview : techviews)
                if (techview.getAttribute("type").equals("XCUIElementTypeTable")) {
                    techsplittable = techview;
                    break;
                }
        }
        return techsplittable;
    }

    public boolean isTechnicianIsNotSelected(String technician) {
        IOSElement techsplittable =  getTechnicianSplitTable();
        return techsplittable.findElementsByXPath("//XCUIElementTypeCell[contains(@name, '"
                + technician + "')]/XCUIElementTypeButton[@name='unselected']").size() > 0;
    }

    public void setTechnicianCustomPriceValue(String technician,
                                              String _quantity) {

        IOSElement techsplittable =  getTechnicianSplitTable();

        techsplittable.findElementByXPath("//XCUIElementTypeCell[contains(@name, '"
                + technician + "')]/XCUIElementTypeStaticText[1]").click();
        Helpers.waitABit(500);
        if (techsplittable.findElementsByXPath("//XCUIElementTypeCell[contains(@name, '"
                + technician + "')]/XCUIElementTypeTextField[1]").size() > 0)
            techsplittable.findElementByXPath("//XCUIElementTypeCell[contains(@name, '"
                    + technician + "')]/XCUIElementTypeTextField[1]").clear();
        techsplittable.findElementByXPath("//XCUIElementTypeCell[contains(@name, '"
                + technician + "')]/XCUIElementTypeTextField[1]").sendKeys(_quantity + "\n");
        //((IOSDriver) appiumdriver).getKeyboard().pressKey(_quantity);
        //((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
    }
}
