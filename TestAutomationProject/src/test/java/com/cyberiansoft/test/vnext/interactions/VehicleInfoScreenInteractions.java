package com.cyberiansoft.test.vnext.interactions;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.enums.VehicleDataField;
import com.cyberiansoft.test.vnext.screens.VNextCustomKeyboard;
import com.cyberiansoft.test.vnext.screens.VNextVehicleModelsScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehiclemakesScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.utils.ControlUtils;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class VehicleInfoScreenInteractions {
    public static void setDataFiled(VehicleDataField dataField, String value) {
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.collectionSizeIsGreaterThan(vehicleInfoScreen.getDataFieldList(), 0);
        //todo: temporary fix for hide keyboard
        WaitUtils.getGeneralFluentWait().until(driver -> {
            ControlUtils.setValue(vehicleInfoScreen
                            .getDataFieldList()
                            .stream()
                            .filter(element -> element.getAttribute("name").contains(dataField.getValue()))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Vehicle info data not found " + dataField.getValue())),
                    value + Keys.TAB);
            return true;
        });
    }

    public static String getDataFieldValue(VehicleDataField dataField) {
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.collectionSizeIsGreaterThan(vehicleInfoScreen.getDataFieldList(), 0);
        return WaitUtils.getGeneralFluentWait().until(driver -> ControlUtils.getElementValue(vehicleInfoScreen
                .getDataFieldList()
                .stream()
                .filter(element -> element.getAttribute("name").contains(dataField.getValue()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Vehicle info data not found " + dataField.getValue()))
        ));
    }

    public static void selectColor(String color) {
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        vehicleInfoScreen.getColorSectionExpandButton().click();
        WaitUtils.click(By.xpath("//*[@action='select-item' and @data-id='" + color + "']"));
    }

    public static void selectMakeAndModel(String vehicleMake, String vehicleModel) {
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.click(vehicleInfoScreen.getMakeSectionExpandButton());
        VNextVehiclemakesScreen vehiclemakesScreen = new VNextVehiclemakesScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextVehicleModelsScreen vehicleModelsScreen = vehiclemakesScreen.selectVehicleMake(vehicleMake);
        vehicleModelsScreen.selectVehicleModel(vehicleModel);
    }

    public static String getCustomerContextValue() {
        return new VNextVehicleInfoScreen().getCustomerContextField().getAttribute("value");
    }

    public static String getOwnerValue() {
        return new VNextVehicleInfoScreen().getOwnderField().getAttribute("value");
    }

    //TODO: Refactor and decorations for year spinner required
    public static void setYear(String yearValue) {
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.waitUntilElementIsClickable(By.name("Vehicle.Year"));
        WaitUtils.click(vehicleInfoScreen.getYearField());
        WaitUtils.getGeneralFluentWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-picker-value='" + yearValue + "']")));
        WebElement elem = DriverBuilder.getInstance().getAppiumDriver().findElement(By.xpath("//div[@data-picker-value='" + yearValue + "']"));
        JavascriptExecutor je = (JavascriptExecutor) DriverBuilder.getInstance().getAppiumDriver();
        je.executeScript("arguments[0].scrollIntoView(true);", elem);
        WaitUtils.click(By.xpath("//div[@data-picker-value='" + yearValue + "']"));
        List<MobileElement> closebtns = DriverBuilder.getInstance().getAppiumDriver().findElements(By.xpath("//a[@class='link close-picker']"));
        for (WebElement closebtn : closebtns)
            if (closebtn.isDisplayed()) {
                WaitUtils.click(closebtn);
                break;
            }
    }

    public static VNextCustomersScreen clickSelectOwnerCell() {
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        JavascriptExecutor je = (JavascriptExecutor) DriverBuilder.getInstance().getAppiumDriver();
        je.executeScript("arguments[0].scrollIntoView(true);", vehicleInfoScreen.getSelectOwnerButton());

        WaitUtils.click(vehicleInfoScreen.getSelectOwnerButton());

        try {
            if (DriverBuilder.getInstance().getAppiumDriver().findElements(By.xpath("//*[@action='select-owner']")).size() > 0)
                WaitUtils.click(vehicleInfoScreen.getSelectOwnerButton());
        } catch (TimeoutException e) {
            //do nothing
        }
        return new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
    }

    public static void setMileage(String milage) {
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WebElement milageField = vehicleInfoScreen
                .getDataFieldList()
                .stream()
                .filter(element -> element.getAttribute("name").contains(VehicleDataField.MILAGE.getValue()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Vehicle info data not found " + VehicleDataField.MILAGE.getValue()));

        milageField.click();
        VNextCustomKeyboard keyboard = new VNextCustomKeyboard(DriverBuilder.getInstance().getAppiumDriver());
        keyboard.setFieldValue(milageField.getAttribute("value"), milage);
    }

    public static void openTechnicianList() {
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.collectionSizeIsGreaterThan(vehicleInfoScreen.getDataFieldList(), 0);
        WaitUtils.getGeneralFluentWait().until(driver -> {
                    vehicleInfoScreen
                            .getDataFieldList()
                            .stream()
                            .filter(element -> element.getAttribute("name").contains(VehicleDataField.VEHICLE_TECH.getValue()))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Vehicle info data not found " + VehicleDataField.VEHICLE_TECH.getValue())).click();
                    return true;
                }
        );
    }

    public static void waitPageLoaded() {
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        WaitUtils.collectionSizeIsGreaterThan(vehicleInfoScreen.getDataFieldList(), 0);
    }
}
