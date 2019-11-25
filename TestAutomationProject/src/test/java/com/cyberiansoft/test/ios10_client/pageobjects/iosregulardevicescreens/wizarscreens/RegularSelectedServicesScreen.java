package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceBundleScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceDetailsScreen;
import com.cyberiansoft.test.ios10_client.utils.PricesCalculations;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class RegularSelectedServicesScreen extends RegularBaseServicesScreen {

    @iOSXCUITFindBy(accessibility = "SelectedServicesView")
    private IOSElement selectedservicestable;

    public RegularSelectedServicesScreen() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
    }

    public void waitSelectedServicesScreenLoaded() {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("SelectedServicesView")));
        WaitUtils.elementShouldBeVisible(selectedservicestable, true);
    }

    public boolean checkServiceIsSelected(String serviceName) {

        return selectedservicestable.findElementsByAccessibilityId(serviceName).size() > 0;
    }

    public void removeSelectedService(String serviceName) {
        searchServiceByName(serviceName);
        selectedservicestable.findElementByAccessibilityId(serviceName).findElementByClassName("XCUIElementTypeButton").click();
        selectedservicestable.findElementByAccessibilityId(serviceName).findElementByAccessibilityId("Delete").click();
    }

    public void searchServiceByName(String serviceName) {
        clearSearchServiceParameters();
        appiumdriver.findElementByClassName("XCUIElementTypeSearchField").click();
        appiumdriver.findElementByClassName("XCUIElementTypeSearchField").sendKeys(serviceName + "\n");

    }

    public void clearSearchServiceParameters() {
        appiumdriver.findElementByClassName("XCUIElementTypeSearchField").clear();
    }

    public RegularSelectedServiceDetailsScreen openCustomServiceDetails(String serviceName) {
        WebElement searchFild = appiumdriver.findElementByClassName("XCUIElementTypeSearchField");
        if (!(searchFild.getAttribute("value") == null))
            searchFild.clear();

        IOSElement servicecell = (IOSElement) selectedservicestable.
                findElement(MobileBy.AccessibilityId(serviceName));
        if (!servicecell.isDisplayed()) {
            searchFild.sendKeys(serviceName + "\n");
        }
        selectedservicestable.findElement(MobileBy.AccessibilityId(serviceName)).click();

        return new RegularSelectedServiceDetailsScreen();
    }

    public void openCustomServiceDetails(String serviceName, VehiclePartData vehiclePartData) {
        selectedservicestable.findElementByXPath("//XCUIElementTypeCell[@name='" + serviceName +
                "']/XCUIElementTypeStaticText[@name='" + vehiclePartData.getVehiclePartName() +"']").click();
    }

    public void openServiceDetailsByIndex(String servicename, int serviceDetailIndex) {
        MobileElement serviceCell = ((MobileElement) selectedservicestable.findElementsByAccessibilityId(servicename).get(serviceDetailIndex));
        if (serviceCell.isDisplayed())
            serviceCell.click();
        else {
            swipeToElement((MobileElement) selectedservicestable.findElementsByAccessibilityId(servicename).get(serviceDetailIndex));
            ((MobileElement) selectedservicestable.findElementsByAccessibilityId(servicename).get(serviceDetailIndex)).click();
        }
    }

    public String getSelectedServicePriceValue(String serviceName) {
        return selectedservicestable.findElement(MobileBy.AccessibilityId(serviceName + "_SelectedServiceIconSelected"))
                .findElements(MobileBy.className("XCUIElementTypeStaticText")).get(2).getAttribute("value")
                .replaceAll("[^a-zA-Z0-9$.%]", " ");
    }

    public String getSelectedServicePriceValue(String serviceName, String vehiclePart) {
        String serviceNameFull = serviceName + "_SelectedServiceIconSelected";
        WebElement serviceCell = selectedservicestable.
                findElement(MobileBy.xpath("//XCUIElementTypeCell[@name='" + serviceNameFull + "']/XCUIElementTypeStaticText[@name='" + vehiclePart + "']/.."));
        return serviceCell.findElements(MobileBy.xpath("XCUIElementTypeStaticText")).get(2).getAttribute("value")
                .replaceAll("[^a-zA-Z0-9$.%]", " ");
    }


    public boolean isServiceIsSelectedWithServiceValues(String serviceName, String priceValue) {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(serviceName)));
        boolean selected = false;
        List<MobileElement> services = selectedservicestable.findElements(MobileBy.className("XCUIElementTypeCell"));
        for (MobileElement srvc : services)
            if (srvc.getAttribute("name").contains(serviceName))
                if ((srvc.
                        findElements(MobileBy.className("XCUIElementTypeStaticText")).get(2).getText().replaceAll("[^a-zA-Z0-9$.%]", "").equals(
                        priceValue.replaceAll(" ", "")))) {
                    selected = true;
                    break;
                }
        return selected;
    }

    public boolean isServiceIsSelectedWithServiceValues(ServiceData serviceData) {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(serviceData.getServiceName())));
        boolean selected = false;
        List<MobileElement> services = selectedservicestable.findElements(MobileBy.className("XCUIElementTypeCell"));
        String priceValue = PricesCalculations.getPriceRepresentation(serviceData.getServicePrice()) + " x " + serviceData.getServiceQuantity();

        for (MobileElement srvc : services)
            if (srvc.getAttribute("name").contains(serviceData.getServiceName()))
                if ((srvc.
                        findElements(MobileBy.className("XCUIElementTypeStaticText")).get(2).getText().replaceAll("[^a-zA-Z0-9$.%]", "").equals(
                        priceValue.replaceAll(" ", "")))) {
                    selected = true;
                    break;
                }
        return selected;
    }

    public int getNumberOfServiceSelectedItems(String serviceName) {
        return selectedservicestable.findElements(MobileBy.iOSNsPredicateString("name = '" +
                serviceName + "' and type = 'XCUIElementTypeCell'")).size();
    }

    public RegularSelectedServiceDetailsScreen openSelectedServiceDetails(String serviceName) {
        clickOnSelectedService(serviceName);
        return new RegularSelectedServiceDetailsScreen();
    }

    public void clickOnSelectedService(String serviceName) {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(serviceName))).click();
    }

    public void clickCustomServiceDetailsButton(String serviceName) {
        WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(serviceName)))
                .findElement(MobileBy.AccessibilityId("custom detail button")).click();
    }

    public RegularSelectedServiceBundleScreen openSelectBundleServiceDetails(String serviceName) {
        selectedservicestable.findElementByAccessibilityId(serviceName).click();

        return new RegularSelectedServiceBundleScreen();
    }

    public boolean isNotesIconPresentForSelectedService(String serviceName) {
        List<MobileElement> services = selectedservicestable.findElements(MobileBy.className("XCUIElementTypeCell"));
        boolean present = false;
        for (MobileElement srvc : services)
            if (srvc.getAttribute("name").contains(serviceName))
                if (srvc.
                        findElements(MobileBy.AccessibilityId("ESTIMATION_NOTES")).size() > 0) {
                    present = true;
                    break;
                }

        return present;
    }

    public boolean isNotesIconPresentForSelectedWorkOrderService(String serviceName) {
        return selectedservicestable.findElementByAccessibilityId(serviceName).findElementsByAccessibilityId("ORDER_NOTES").size() > 0;
    }

    public int getNumberOfSelectedServices() {
        return selectedservicestable.findElementsByClassName("XCUIElementTypeTable").size();
    }

    public boolean isServiceWithSubSrviceSelected(String serviceName, String serviceSubsrviceName) {
        return selectedservicestable.findElements(MobileBy.xpath("//XCUIElementTypeCell[@name='" + serviceName + "']/XCUIElementTypeStaticText[@name='" + serviceSubsrviceName + "']")).size() > 0;
    }

    public boolean isServiceApproved(String serviceName) {
        return appiumdriver.findElementByClassName("XCUIElementTypeTable").findElement(MobileBy.iOSNsPredicateString("name CONTAINS '" +
                serviceName + "' and type = 'XCUIElementTypeCell'")).findElements(MobileBy.AccessibilityId("SelectedServiceIcon_Selected")).size() > 0;
    }

    public boolean isServiceDeclinedSkipped(String serviceName) {
        return appiumdriver.findElementByClassName("XCUIElementTypeTable").findElement(MobileBy.iOSNsPredicateString("name CONTAINS '" +
                serviceName + "' and type = 'XCUIElementTypeCell'")).findElements(MobileBy.AccessibilityId("SelectedServiceIcon_Declined")).size() > 0;
    }

}
