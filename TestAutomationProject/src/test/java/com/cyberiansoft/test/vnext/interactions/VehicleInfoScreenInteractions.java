package com.cyberiansoft.test.vnext.interactions;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.enums.VehicleDataField;
import com.cyberiansoft.test.vnext.screens.VNextCustomKeyboard;
import com.cyberiansoft.test.vnext.screens.VNextVehicleModelsScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehiclemakesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.utils.ControlUtils;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
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
                    value);
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
        ((JavascriptExecutor) ChromeDriverProvider.INSTANCE.getMobileChromeDriver()).executeScript("arguments[0].scrollIntoView(true);", vehicleInfoScreen.getColorSectionExpandButton());
        vehicleInfoScreen.getColorSectionExpandButton().click();
        WaitUtils.click(By.xpath("//*[@action='select-item' and @data-id='" + color + "']"));
    }

    public static void selectMakeAndModel(String vehicleMake, String vehicleModel) {
        VehicleInfoScreenInteractions.waitPageLoaded();
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        vehicleInfoScreen
                .getDataFieldList()
                .stream()
                .filter(element -> element.getAttribute("name").contains(VehicleDataField.VIN.getValue()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Vehicle info data not found " + VehicleDataField.VIN.getValue())).sendKeys(Keys.TAB);
        WaitUtils.click(vehicleInfoScreen.getMakeSectionExpandButton());
        VNextVehiclemakesScreen vehicleMakesScreen = new VNextVehiclemakesScreen();
        VNextVehicleModelsScreen vehicleModelsScreen = vehicleMakesScreen.selectVehicleMake(vehicleMake);
        vehicleModelsScreen.selectVehicleModel(vehicleModel);
    }

    public static String getCustomerContextValue() {
        return new VNextVehicleInfoScreen().getCustomerContextField().getAttribute("value");
    }

    public static String getOwnerValue() {
        BaseUtils.waitABit(2000);
        return new VNextVehicleInfoScreen().getOwnderField().getAttribute("value");
    }

    //TODO: Refactor and decorations for year spinner required
    public static void setYear(String yearValue) {
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.waitUntilElementIsClickable(By.name("Vehicle.Year"));
        WaitUtils.click(vehicleInfoScreen.getYearField());
        WaitUtils.getGeneralFluentWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-picker-value='" + yearValue + "']")));
        BaseUtils.waitABit(2000);
        Actions act = new Actions(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        act.moveToElement(ChromeDriverProvider.INSTANCE.getMobileChromeDriver().findElement(By.xpath("//div[@data-picker-value='" + yearValue + "']"))).click()
                .perform();
        List<WebElement> closebtns = ChromeDriverProvider.INSTANCE.getMobileChromeDriver().findElements(By.xpath("//a[@class='link close-picker']"));
        for (WebElement closebtn : closebtns)
            if (closebtn.isDisplayed()) {
                WaitUtils.click(closebtn);
                break;
            }
    }

    //todo rewrite
    public static void clickSelectOwnerCell() {
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        JavascriptExecutor je = (JavascriptExecutor) ChromeDriverProvider.INSTANCE.getMobileChromeDriver();
        je.executeScript("arguments[0].scrollIntoView(true);", vehicleInfoScreen.getSelectOwnerButton());
        WaitUtils.click(vehicleInfoScreen.getSelectOwnerButton());
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
        VNextCustomKeyboard keyboard = new VNextCustomKeyboard(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
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
