package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens;

import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceDetailsScreen;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class RegularSelectedServicesScreen extends RegularBaseServicesScreen {

    final static String defaultServiceValue = "Test Tax";

    @iOSFindBy(accessibility = "SelectedServicesView")
    private IOSElement selectedservicestable;

    public RegularSelectedServicesScreen() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("SelectedServicesView")));
    }

    public boolean checkServiceIsSelected(String servicename) {

        return selectedservicestable.findElementsByAccessibilityId(servicename).size() > 0;
    }

    public void removeSelectedService(String servicename) {
        searchServiceByName(servicename);
        selectedservicestable.findElementByAccessibilityId(servicename).findElementByClassName("XCUIElementTypeButton").click();
        selectedservicestable.findElementByAccessibilityId(servicename).findElementByAccessibilityId("Delete").click();
    }

    public void searchServiceByName(String servicename) {
        clearSearchServiceParameters();
        appiumdriver.findElementByClassName("XCUIElementTypeSearchField").click();
        appiumdriver.findElementByClassName("XCUIElementTypeSearchField").sendKeys(servicename + "\n");

    }

    public void clearSearchServiceParameters() {
        appiumdriver.findElementByClassName("XCUIElementTypeSearchField").clear();
    }

    public RegularSelectedServiceDetailsScreen openCustomServiceDetails(String servicename) {
        if (elementExists("Clear text"))
            appiumdriver.findElementByAccessibilityId("XCUIElementTypeSearchField").clear();
        if (!selectedservicestable.findElementByAccessibilityId(servicename).isDisplayed())
            if (appiumdriver.findElementsByAccessibilityId("XCUIElementTypeSearchField").size() > 0)
                searchServiceByName(servicename);
        selectedservicestable.findElementByAccessibilityId(servicename).click();

        return new RegularSelectedServiceDetailsScreen();
    }

    public void openServiceDetailsByIndex(String servicename, int servicedetailindex) {
        MobileElement serviceCell = ((MobileElement) selectedservicestable.findElementsByAccessibilityId(servicename).get(servicedetailindex));
        if (serviceCell.isDisplayed())
            serviceCell.click();
        else {
            swipeToElement((MobileElement) selectedservicestable.findElementsByAccessibilityId(servicename).get(servicedetailindex));
            ((MobileElement) selectedservicestable.findElementsByAccessibilityId(servicename).get(servicedetailindex)).click();
        }
    }


    public boolean isServiceIsSelectedWithServiceValues(String servicename, String pricevalue) {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(servicename)));
        boolean selected = false;
        List<MobileElement> services = selectedservicestable.findElements(MobileBy.className("XCUIElementTypeCell"));
        for (MobileElement srvc : services)
            if (srvc.getAttribute("name").contains(servicename))
                if ((srvc.
                        findElements(MobileBy.className("XCUIElementTypeStaticText")).get(2).getText().replaceAll("[^a-zA-Z0-9$.%]", "").equals(
                        pricevalue.replaceAll(" ", "")))) {
                    selected = true;
                    break;
                }
        return selected;
    }

    public String getSelectedServicePriceValue(String servicename) {
        List<MobileElement> services = selectedservicestable.findElements(MobileBy.className("XCUIElementTypeCell"));
        for (MobileElement srvc : services)
            if (srvc.getAttribute("name").contains(servicename))
                return srvc.findElementByXPath("//XCUIElementTypeStaticText[3]").getAttribute("value").replaceAll("[^a-zA-Z0-9$.%]", "");
        return null;
    }

    public int getNumberOfServiceSelectedItems(String servicename) {
        return selectedservicestable.findElements(MobileBy.iOSNsPredicateString("name = '" +
                servicename + "' and type = 'XCUIElementTypeCell'")).size();
    }

    public RegularSelectedServiceDetailsScreen openSelectedServiceDetails(String service) {
        List<MobileElement> selectedservices = selectedservicestable.findElementsByAccessibilityId(service);
        if (selectedservices.size() > 1) {
            ((WebElement) selectedservicestable.findElementsByAccessibilityId(service).get(1)).click();
        } else {
            selectedservices.get(0).click();
        }
        return new RegularSelectedServiceDetailsScreen();
    }

    public boolean isNotesIconPresentForSelectedService(String servicename) {
        List<MobileElement> services = selectedservicestable.findElements(MobileBy.className("XCUIElementTypeCell"));
        boolean present = false;
        for (MobileElement srvc : services)
            if (srvc.getAttribute("name").contains(servicename))
                if (srvc.
                        findElements(MobileBy.AccessibilityId("ESTIMATION_NOTES")).size() > 0) {
                    present = true;
                    break;
                }

        return present;
    }

    public boolean isNotesIconPresentForSelectedWorkOrderService(String servicename) {
        return selectedservicestable.findElementByAccessibilityId(servicename).findElementsByAccessibilityId("ORDER_NOTES").size() > 0;
    }

    public int getNumberOfSelectedServices() {
        return selectedservicestable.findElementsByClassName("XCUIElementTypeTable").size();
    }

    public boolean isServiceWithSubSrviceSelected(String servicename, String servicesubsrvicename) {
        return selectedservicestable.findElements(MobileBy.xpath("//XCUIElementTypeCell[@name='" + servicename + "']/XCUIElementTypeStaticText[@name='" + servicesubsrvicename + "']")).size() > 0;
    }

    public boolean isServiceApproved(String srvname) {
        return selectedservicestable.findElement(MobileBy.iOSNsPredicateString("name CONTAINS '" +
                srvname + "' and type = 'XCUIElementTypeCell'")).findElements(MobileBy.AccessibilityId("SelectedServiceIcon_Selected")).size() > 0;
    }

    public boolean isServiceDeclinedSkipped(String srvname) {
        return selectedservicestable.findElement(MobileBy.iOSNsPredicateString("name CONTAINS '" +
                srvname + "' and type = 'XCUIElementTypeCell'")).findElements(MobileBy.AccessibilityId("SelectedServiceIcon_Declined")).size() > 0;
    }

    public boolean isDefaultServiceIsSelected() {
        return selectedservicestable.findElement(MobileBy.iOSNsPredicateString("name = '" +
                defaultServiceValue + "' and type = 'XCUIElementTypeCell'")).isDisplayed();
    }

}
